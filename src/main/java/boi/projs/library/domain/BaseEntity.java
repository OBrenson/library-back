package boi.projs.library.domain;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BooleanSupplier;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    public BaseEntity(UUID id) {
        this.id = Objects.requireNonNullElseGet(id, UUID::randomUUID);
    }

    @Id
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    protected UUID id;

    protected boolean checkEquals(Object obj, BooleanSupplier typeCheck, BooleanSupplier customCheck) {
        if(this == obj) {
            return true;
        }
        if(!typeCheck.getAsBoolean()) {
            return false;
        }
        return customCheck.getAsBoolean();
    }

    protected String getString(String clazzName, String field) {
        return String.format("%s: %s %s", clazzName, id, field);
    }
}
