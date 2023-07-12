package boi.projs.library.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
public class User extends BaseEntity {

    private String login;
    private byte[] password;

    @OneToMany(mappedBy = "user")
    private Set<Author> authors;

    @Builder
    public User(Long id, String login, byte[] password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return getString(this.getClass().getName(), login);
    }
}
