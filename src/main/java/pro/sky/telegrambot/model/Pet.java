package pro.sky.telegrambot.model;

import pro.sky.telegrambot.constants.PetColor;
import pro.sky.telegrambot.constants.PetType;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "pet")

public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nickName;
    private PetType petType;
    private PetColor petColor;
    private byte[] photo;
    private long adopterId;


    public Pet() {
    }

    public Pet(long id, String nickName, PetType petType, PetColor petColor,byte[] photo,long adopterId) {
        this.id = id;
        this.nickName = nickName;
        this.petType = petType;
        this.petColor = petColor;
        this.photo = photo;
        this.adopterId = adopterId;
        }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public PetType getPetType() {
        return petType;
    }

    public void setPetType(PetType petType) {
        this.petType = petType;
    }

    public PetColor getPetColor() {
        return petColor;
    }

    public void setPetColor(PetColor petColor) {
        this.petColor = petColor;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public long getAdopterId() {
        return adopterId;
    }

    public void setAdopterId(long adopterId) {
        this.adopterId = adopterId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet)) return false;
        Pet pet = (Pet) o;
        return getId() == pet.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
