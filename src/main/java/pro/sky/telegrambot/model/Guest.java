package pro.sky.telegrambot.model;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "guest")
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "chat_id")
    private long chatId;
     private Timestamp lastVisit;
    private int lastMenu;

    public Guest() {
    }

    public Guest(long chatId, Timestamp lastVisit, int lastMenu) {
        this.chatId = chatId;
        this.lastVisit = lastVisit;
        this.lastMenu = lastMenu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public Timestamp getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Timestamp lastVisit) {
        this.lastVisit = lastVisit;
    }

    public int getLastMenu() {
        return lastMenu;
    }

    public void setLastMenu(int lastMenu) {
        this.lastMenu = lastMenu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Guest)) return false;
        Guest guest = (Guest) o;
        return getId() == guest.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
