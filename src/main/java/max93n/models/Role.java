package max93n.models;

import max93n.models.enums.RoleEnum;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="role_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum role;


    public Role() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
