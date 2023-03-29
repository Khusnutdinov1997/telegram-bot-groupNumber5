package pro.sky.telegrambot.exception;

public class PetAvatarNotFoundException extends RuntimeException {

    private final long id;

    public PetAvatarNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
