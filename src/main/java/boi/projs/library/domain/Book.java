package boi.projs.library.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(indexes = @Index(name = "book_unique", columnList = "author_id, title", unique = true))
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="author_id")
    private Author author;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID fileId;

    @ColumnDefault("1")
    private int page;

    @Builder
    public Book(UUID id, String title, Author author, UUID fileId, int page) {
        super(id);
        this.title = title;
        this.author = author;
        this.fileId = fileId;
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        return checkEquals(o,
                () -> o instanceof Book,
                () -> id.equals(
                        ((Book)o).getId())
                        && title.equals(((Book)o).getTitle())
                        && author.getId().equals(((Book)o).getAuthor().id)
                        && fileId.equals(((Book)o).getFileId())
                        && page == ((Book)o).page
        );
    }

    @Override
    public String toString() {
        return getString(getClass().getName(), title);
    }
}
