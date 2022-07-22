package ua.intellistart.quiz.model;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "reg_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first")
    private String firstName;

    @Column(name = "last")
    private String lastName;

    @Column(name = "amount", precision = 19, scale = 2)
    private BigDecimal amountOfMoney;

    public User() {
    }

    public User(Long id, String firstName, String lastName, BigDecimal amountOfMoney) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.amountOfMoney = amountOfMoney;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAmountOfMoney(BigDecimal amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public BigDecimal getAmountOfMoney() {
        return amountOfMoney;
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }
}