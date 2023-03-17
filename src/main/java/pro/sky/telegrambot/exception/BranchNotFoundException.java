package pro.sky.telegrambot.exception;

public class BranchNotFoundException extends RuntimeException {
    private long id;

    public BranchNotFoundException(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
