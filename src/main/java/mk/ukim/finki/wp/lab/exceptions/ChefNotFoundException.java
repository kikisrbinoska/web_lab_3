package mk.ukim.finki.wp.lab.exceptions;

public class ChefNotFoundException extends RuntimeException {
    public ChefNotFoundException() {
        super("Chef not found");
    }
}
