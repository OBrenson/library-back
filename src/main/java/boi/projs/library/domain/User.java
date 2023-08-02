package boi.projs.library.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name="lib_user", indexes = @Index(name = "uniqueIndex", columnList = "login", unique = true))
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private byte[] password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Author> authors;

    @Builder
    public User(UUID id, String login, byte[] password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return getString(this.getClass().getName(), login);
    }

    @Override
    public boolean equals(Object o) {
        return checkEquals(o,
                () -> o instanceof User,
                () -> id == ((User) o).getId() && login.equals(((User) o).getLogin()));
    }
}
