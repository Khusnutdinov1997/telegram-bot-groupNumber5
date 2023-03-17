package pro.sky.telegrambot.exception;

public class VolunteerNotFoundException extends RuntimeException {

    private long id;

    public VolunteerNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}

