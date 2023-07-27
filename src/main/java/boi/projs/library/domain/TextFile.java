package boi.projs.library.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name="text_file")
public class TextFile extends BaseEntity{

    private UUID bookId;
    private byte[] content;

    @Builder
    public TextFile(UUID id, UUID bookId, byte[] content) {
        super(id);
        this.bookId = bookId;
        this.content = content;
    }

    @Override
    public String toString() {
        return getString(getClass().getName(), bookId.toString());
    }
}
