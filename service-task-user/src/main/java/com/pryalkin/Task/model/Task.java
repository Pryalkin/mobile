package com.pryalkin.Task.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tasks")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @EqualsAndHashCode.Include
    private String heading;
    @EqualsAndHashCode.Include
    private String description;
    @EqualsAndHashCode.Include
    private String status;
    @EqualsAndHashCode.Include
    private String priority;
    @EqualsAndHashCode.Include
    private Long creatorId;
    @EqualsAndHashCode.Include
    private Long executorId;
    @OneToMany(
            mappedBy = "task",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Comment> comments = new ArrayList<>();

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setTask(this);
    }

}
