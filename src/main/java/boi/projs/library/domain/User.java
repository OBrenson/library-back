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
@Table(name="lib_user", indexes = @Index(name = "uniqueIndex", columnList = "login", unique = true))
public class User extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private byte[] password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Author> authors;

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
}
