package pro.sky.telegrambot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity


public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String telegramId;
    private byte[] photo;

    public Volunteer(Long volunteer, String name, String telegramId, byte[] photo) {
        this.id = volunteer;
        this.name = name;
        this.telegramId = telegramId;
        this.photo = photo;
    }

    public Volunteer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(String telegram) {
        this.telegramId = telegramId;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Volunteer)) return false;
        Volunteer volunteer = (Volunteer) o;
        return getId() == volunteer.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
