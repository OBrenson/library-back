package boi.projs.library.domain;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "author", indexes = @Index(name = "author_unique_index", columnList = "user_id, name", unique = true))
public class Author extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Book> books;

    @Builder
    public Author(UUID id, String name, User user) {
        super(id);
        this.name = name;
        this.user = user;
    }

    @Override
    public String toString() {
        return getString(this.getClass().getName(), name);
    }

    @Override
    public boolean equals(Object o) {
        return checkEquals(o,
                () -> o instanceof Author,
                () -> id.equals(((Author) o).getId()) && name.equals(((Author) o).getName())
                        && user.getId().equals(((Author) o).getUser().getId()));
    }
}
