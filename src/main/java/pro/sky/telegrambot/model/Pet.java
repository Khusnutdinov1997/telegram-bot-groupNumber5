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
    private Long id;

    private String nickName;
    private PetType petType;
    private PetColor petColor;
    private long avatarId;
    private long adopterId;

    public Pet() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Pet(Long id, String nickName, PetType petType, PetColor petColor,long avatarId,long adopterId) {
        this.id = id;
        this.nickName = nickName;
        this.petType = petType;
        this.petColor = petColor;
        this.avatarId = avatarId;
        this.adopterId = adopterId;
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

    public long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(long avatarId) {
        this.avatarId = avatarId;
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


    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", nickName='" + nickName + '\'' +
                ", petType=" + petType +
                ", petColor=" + petColor +
                '}';
    }
}
