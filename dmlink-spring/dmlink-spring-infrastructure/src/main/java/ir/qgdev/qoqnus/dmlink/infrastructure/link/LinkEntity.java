package ir.qgdev.qoqnus.dmlink.infrastructure.link;

import ir.qgdev.qoqnus.dmlink.domain.url.HttpUrl;
import ir.qgdev.qoqnus.dmlink.infrastructure.user.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "link")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LinkEntity {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    Long id;

    @Column(unique = true, nullable = false)
    String name;

    @Column(nullable = false)
    HttpUrl target;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    UserEntity owner;
}
