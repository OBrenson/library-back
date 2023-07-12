package domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TextFile extends BaseEntity{

    @NonNull
    private Long bookId;
    private byte[] content;

    @Builder
    public TextFile(@NonNull Long id, @NonNull Long bookId, byte[] content) {
        super(id);
        this.bookId = bookId;
        this.content = content;
    }

    @Override
    public String toString() {
        return getString(getClass().getName(), bookId.toString());
    }
}
