package boi.projs.library.domain;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Book extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author author;
    private UUID fileId;
    private int page;

    @Builder
    public Book(@NonNull UUID id, String title, Author author, UUID fileId, int page) {
        super(id);
        this.title = title;
        this.author = author;
        this.fileId = fileId;
        this.page = page;
    }

    @Override
    public String toString() {
        return getString(getClass().getName(), title);
    }
}
