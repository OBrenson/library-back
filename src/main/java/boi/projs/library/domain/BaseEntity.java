package boi.projs.library.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
    protected UUID id;

    protected String getString(String clazzName, String field) {
        return String.format("%s: %s %s", clazzName, id, field);
    }
}
