package com.linetime.backend.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.util.Comparator;
import java.util.Set;

import lombok.Data;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy="owner", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<Timeline> timelines;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof User))
            return false;
        User other = (User) o;

        return
                this.getEmail() == other.getEmail() &&
                this.getId() == other.getId() &&
                this.getName() == other.getName() &&
                this.getPassword() == other.getPassword();
    }
}