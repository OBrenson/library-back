package boi.projs.library.domain;

import lombok.*;

import javax.persistence.Column;
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

    @Column(nullable = false)
    private UUID bookId;
    @Column(nullable = false)
    private byte[] content;

    @Builder
    public TextFile(UUID id, UUID bookId, byte[] content) {
        super(id);
        this.bookId = bookId;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        return checkEquals(o,
                () -> o instanceof TextFile,
                () -> id.equals(((TextFile)o).getId())
                        && bookId.equals(((TextFile)o).getBookId())
                        && content.length == ((TextFile)o).getContent().length
        );
    }

    @Override
    public String toString() {
        return getString(getClass().getName(), bookId.toString());
    }
}
