package pro.sky.telegrambot.model;
import javax.persistence.*;

import java.util.Objects;

@Entity

public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String passport;
    private int age;
    private String phone1;
    private String email;
    @Column(name = "chat_id")
    private long chatId;

    private int volunteerId;
    private boolean onProbation;
    private boolean active;


    public Adopter(String firstName, String lastName, String passport, int age, String phone1, String email, long chatId, int volunteerId, boolean onProbation, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.age = age;
        this.phone1 = phone1;
        this.email = email;
        this.chatId = chatId;
        this.volunteerId = volunteerId;
        this.onProbation = onProbation;
        this.active = active;
    }

    public Adopter() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(int volunteerId) {
        this.volunteerId = volunteerId;
    }

    public boolean isOnProbation() {
        return onProbation;
    }

    public void setOnProbation(boolean onProbation) {
        this.onProbation = onProbation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Adopter)) return false;
        Adopter adopter = (Adopter) o;
        return getId().equals(adopter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
