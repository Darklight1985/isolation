package ru.kolesnev.domain;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import ru.kolesnev.dto.UserLoginDto;

@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntity {
    @Username
    public String username;
    @Password
    public String password;
    @Roles
    public String role;

    /**
     * Adds a new user to the database
     * @param username the username
     * @param password the unencrypted password (it is encrypted with bcrypt)
     * @param role the comma-separated roles
     */
    public static void add(String username, String password, String role) {
        User user = new User();
        user.username = username;
        user.password = BcryptUtil.bcryptHash(password);
        user.role = role;
        user.persist();
    }

    public static boolean checkAdmin() {
        User user = find("role", "admin").firstResult();
        if (user != null) {
            return true;
        }
        return false;
    }

    public static boolean existUser(UserLoginDto dto) {
        User user = find("select u from User u where u.username = :username",
                Parameters.with("username", dto.getUsername())).firstResult();
        if (user != null) {
            if (BcryptUtil.matches(dto.getPassword(), user.password)) {
                return true;
            }
        }
        return false;
    }
}
