package domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Book extends BaseEntity {

    @NonNull
    private String title;
    private Author author;
    private Long fileId;
    private int page;

    @Builder
    public Book(Long id, @NonNull String title, Author author, Long fileId, int page) {
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
