package com.haptm.todoapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subtask")
public class Subtask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtask_id")
    private Long subtaskId;

    @Size(max = 50)
    @NotNull
    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "task_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Task task;
}
