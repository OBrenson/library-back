package boi.projs.library.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    protected UUID id;

    protected String getString(String clazzName, String field) {
        return String.format("%s: %s %s", clazzName, id, field);
    }
}
