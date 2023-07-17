package boi.projs.library.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "author", indexes = @Index(name = "author_unique_index", columnList = "user_id, name", unique = true))
public class Author extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private Set<Book> books;

    @Builder
    public Author(@NonNull UUID id, String name, User user) {
        super(id);
        this.name = name;
        this.user = user;
    }

    @Override
    public String toString() {
        return getString(this.getClass().getName(), name);
    }
}
