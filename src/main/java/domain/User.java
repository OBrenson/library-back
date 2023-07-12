package domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @NonNull
    private String login;
    @NonNull
    private byte[] password;

    @Builder
    public User(Long id, @NonNull String login, @NonNull byte[] password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    @Override
    public String toString() {
        return getString(this.getClass().getName(), login);
    }
}
