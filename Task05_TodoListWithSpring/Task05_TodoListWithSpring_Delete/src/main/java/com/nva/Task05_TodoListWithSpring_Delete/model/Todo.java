package com.nva.Task05_TodoListWithSpring_Delete.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "task")
@Data
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Title")
    private String title;

    @Column(name = "Status")
    private boolean status;

    @Column(name = "UserID", columnDefinition = "int")
    private int UserId;

    public Todo() {
    }


    public Todo(int id, String title, boolean status, int userId) {
        this.id = id;
        this.title = title;
        this.status = status;
        UserId = userId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", UserId=" + UserId +
                '}';
    }
}
