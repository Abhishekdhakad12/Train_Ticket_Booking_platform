package com.railconnect.entities;


import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Done , update

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "users")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int roleid;

    @NotBlank(message = "Role cannot be blank")
    @Size(min = 2, max = 6, message = "Role must be between 2 and 6 characters")
    @Column(name = "role_name", nullable = false, unique = true)
    private String rolename;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("roles")  // ignore the 'roles' field inside User
    @ToString.Exclude
    private Set<Users> users = new HashSet<>();

}

