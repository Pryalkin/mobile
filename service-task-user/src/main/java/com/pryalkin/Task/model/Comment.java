//package com.pryalkin.Task.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Table(name = "tasks")
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//public class Comment {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String message;
//    private String author;
//    private String date;
//    @ManyToOne(fetch = FetchType.LAZY)
//    private Task task;
//}
