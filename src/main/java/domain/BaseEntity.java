package domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public abstract class BaseEntity {
    protected Long id;

    protected String getString(String clazzName, String field) {
        return String.format("%s: %d %s", clazzName, id, field);
    }
}
