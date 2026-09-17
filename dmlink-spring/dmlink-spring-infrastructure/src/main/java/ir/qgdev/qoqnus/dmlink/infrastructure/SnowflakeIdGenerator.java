package ir.qgdev.qoqnus.dmlink.infrastructure;

import ir.qgdev.qoqnus.dmlink.application.IdGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Snowflake ID Generator <br>
 * Modified version of: <a href="https://github.com/HMWCS/java-snowflake-id-generator">HMWCS/java-snowflake-id-generator</a>
 */
@Component
public class SnowflakeIdGenerator implements IdGenerator {
    public static final int DATA_CENTER_ID_BITS = 5;
    public static final int MACHINE_ID_BITS = 5;
    public static final int MAX_DATA_CENTER_ID = ~(-1 << DATA_CENTER_ID_BITS);
    public static final int MAX_MACHINE_ID = ~(-1 << MACHINE_ID_BITS);
    public static final int SEQUENCE_BITS = 12;
    public static final int MACHINE_ID_SHIFT = SEQUENCE_BITS;
    public static final int DATA_CENTER_ID_SHIFT = MACHINE_ID_SHIFT + MACHINE_ID_BITS;
    public static final int TIMESTAMP_LEFT_SHIFT = DATA_CENTER_ID_SHIFT + DATA_CENTER_ID_BITS;
    public static final int SEQUENCE_MASK = ~(-1 << SEQUENCE_BITS);
    public static final int CLOCK_BACKWARD_TOLERANCE = 50;

    private final int dataCenterId;
    private final int machineId;
    private final long epoch;

    private final AtomicReference<State> atomicState = new AtomicReference<>(new State(-1L, 0));

    /**
     * Constructor to initialize the Snowflake ID generator with a custom epoch.
     *
     * @param dataCenterId The ID of the data center (0-31). Cannot be null.
     * @param machineId The ID of the machine (0-31). Cannot be null.
     * @param customEpoch The custom epoch to use for generating IDs
     * @throws IllegalArgumentException if the dataCenterId or machineId is out of range
     */
    public SnowflakeIdGenerator(
            @Value("${dmlink.snowflake.datacenter:0}") int dataCenterId,
            @Value("${dmlink.snowflake.machine:0}") int machineId,
            @Value("${dmlink.snowflake.epoch:0}") long customEpoch
    ) {
        if (customEpoch > System.currentTimeMillis())
            throw new IllegalArgumentException("Custom epoch cannot be in the future");
        if (customEpoch < 0)
            throw new IllegalArgumentException("Custom epoch cannot be before the Unix epoch (1970-01-01)");
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0)
            throw new IllegalArgumentException(
                    String.format("Data Center ID (%d) must be between 0 and %d", dataCenterId, MAX_DATA_CENTER_ID));
        if (machineId > MAX_MACHINE_ID || machineId < 0)
            throw new IllegalArgumentException(
                    String.format("Machine ID (%d) must be between 0 and %d", machineId, MAX_MACHINE_ID));

        this.dataCenterId = dataCenterId;
        this.machineId = machineId;
        this.epoch = customEpoch;
    }

    /**
     * Generates a unique Snowflake ID.
     *
     * @return A unique 64-bit Snowflake ID
     */
    @Override
    public long generate() {
        while (true) {
            State currentState = atomicState.get();
            long lastTimestamp = currentState.timestamp;
            int sequence = currentState.sequence;

            long timestamp = System.currentTimeMillis();
            checkClockTolerance(lastTimestamp, timestamp);
            if (timestamp < lastTimestamp) timestamp = lastTimestamp;

            if (timestamp == lastTimestamp) {
                sequence = (sequence + 1) & SEQUENCE_MASK;
                if (sequence == 0) timestamp = waitNextMillis(timestamp);
            } else sequence = 0;
            State newState = new State(timestamp, sequence);

            if (atomicState.compareAndSet(currentState, newState))
                return generateId(timestamp, sequence);
            Thread.onSpinWait();
        }
    }

    private long generateId(long timestamp, int sequence) {
        return ((timestamp - epoch) << TIMESTAMP_LEFT_SHIFT) |
                ((long) dataCenterId << DATA_CENTER_ID_SHIFT) |
                ((long) machineId << MACHINE_ID_SHIFT) |
                (sequence & SEQUENCE_MASK);
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp;
        do {
            Thread.onSpinWait();
            timestamp = System.currentTimeMillis();
            checkClockTolerance(lastTimestamp, timestamp);
        } while (timestamp <= lastTimestamp);

        return timestamp;
    }

    private void checkClockTolerance(long lastTimestamp, long currentTimestamp) {
        if (currentTimestamp + CLOCK_BACKWARD_TOLERANCE < lastTimestamp)
            throw new RuntimeException(String.format("Clock moved backward (last=%s, current=%s)", lastTimestamp, currentTimestamp));
    }

    private record State(long timestamp, int sequence) { }
}
