package com.example.DemoJPA.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user")
@Data
public class User {
    private static final long serialVersionUID = -297553281792804396L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hp")
    private int hp;
    @Column(name = "stamina")
    private int stamina;

    private int atk;
    private int def;
    private int agi;

    public User() {
    }

    public User(Long id, int hp, int stamina, int atk, int def, int agi) {
        this.id = id;
        this.hp = hp;
        this.stamina = stamina;
        this.atk = atk;
        this.def = def;
        this.agi = agi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getStamina() {
        return stamina;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getAgi() {
        return agi;
    }

    public void setAgi(int agi) {
        this.agi = agi;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", hp=" + hp +
                ", stamina=" + stamina +
                ", atk=" + atk +
                ", def=" + def +
                ", agi=" + agi +
                '}';
    }
}
