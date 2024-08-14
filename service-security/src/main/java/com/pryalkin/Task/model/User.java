package com.pryalkin.Task.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    private String email;
    @EqualsAndHashCode.Include
    private String password;
    @EqualsAndHashCode.Include
    private String name;
    @EqualsAndHashCode.Include
    private String surname;
    @EqualsAndHashCode.Include
    private String role;
    @EqualsAndHashCode.Include
    private String[] authorities;

}
