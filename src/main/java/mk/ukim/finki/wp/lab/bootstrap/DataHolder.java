package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import org.springframework.stereotype.Component;

@Component
public class DataHolder {
    private final ChefRepository chefRepository;
    private final DishRepository dishRepository;

    public DataHolder(ChefRepository chefRepository, DishRepository dishRepository) {
        this.chefRepository = chefRepository;
        this.dishRepository = dishRepository;
    }

    @PostConstruct
    public void init() {
        // Save chefs to database
        chefRepository.save(new Chef("Kiki", "Srbinoska", "Pro chef"));
        chefRepository.save(new Chef("Gordon", "Ramsay", "Famous for Hell's Kitchen, MasterChef, and multiple Michelin-starred restaurants"));
        chefRepository.save(new Chef("Jamie", "Oliver", "Known for healthy cooking and popular TV shows"));
        chefRepository.save(new Chef("Nusret", "Gokce", "Renowned for his steakhouses and signature salt-sprinkling style"));
        chefRepository.save(new Chef("Alain", "Ducasse", "One of the chefs with the most Michelin stars in the world"));

        // Save dishes to database
        dishRepository.save(new Dish("1", "Pineapple pizza", "Italian", 15));
        dishRepository.save(new Dish("2", "Sushi Roll", "Japanese", 20));
        dishRepository.save(new Dish("3", "Tacos al Pastor", "Mexican", 12));
        dishRepository.save(new Dish("4", "Croissant", "French", 8));
        dishRepository.save(new Dish("5", "Chicken Biryani", "Indian", 18));
    }
}