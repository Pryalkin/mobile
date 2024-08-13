package com.pryalkin.Task.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "servers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serverName;
    private String serverPassword;

}
