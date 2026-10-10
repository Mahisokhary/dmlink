package ir.qgdev.qoqnus.dmlink.domain.role;

public class RoleParentCycleDetectedException extends RuntimeException {
    public RoleParentCycleDetectedException() {
        super("Cyclic parents relations");
    }

}
