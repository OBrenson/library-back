package boi.projs.library.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class Author extends BaseEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "author")
    private Set<Book> books;

    @Builder
    public Author(Long id, String name, User user) {
        super(id);
        this.name = name;
        this.user = user;
    }

    @Override
    public String toString() {
        return getString(this.getClass().getName(), name);
    }
}
