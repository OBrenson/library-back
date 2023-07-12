package boi.projs.library.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Book extends BaseEntity {

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="author_id")
    private Author author;
    private Long fileId;
    private int page;

    @Builder
    public Book(Long id, String title, Author author, Long fileId, int page) {
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
