package mk.ukim.finki.wp.lab.exceptions;

public class DishNotFoundException extends RuntimeException{
    public DishNotFoundException() {
        super("Dish not found");
    }
}
