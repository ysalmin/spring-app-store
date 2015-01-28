package com.shoppingcart.dao.entity;

import com.shoppingcart.enums.UserRole;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Created by ysalmin on 21.07.2014.
 * DTO for user credentials.
 */
@NamedQueries({
        @NamedQuery(
                name = "getUserByName",
                query = "select uc from UserCredential uc where uc.login = :ucLogin"
        )
})
@Entity
public class UserCredential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    @Size(min = 5, max = 30)
    private String login;

    @NotEmpty
    @Size(min = 6, max = 30)
    private String pass;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public UserRole getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserCredential that = (UserCredential) o;

        if (!login.equals(that.login)) return false;
        if (!pass.equals(that.pass)) return false;
        if (role != that.role) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.login, this.pass, this.role);
    }
}
