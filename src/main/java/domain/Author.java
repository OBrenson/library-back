package domain;


import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Author extends BaseEntity {

    @NonNull
    private String name;
    private User user;

    @Builder
    public Author(Long id, @NonNull String name, User user) {
        super(id);
        this.name = name;
        this.user = user;
    }

    @Override
    public String toString() {
        return getString(this.getClass().getName(), name);
    }
}
