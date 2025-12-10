package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.exceptions.ChefNotFoundException;
import mk.ukim.finki.wp.lab.exceptions.DishNotFoundException;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.repository.ChefRepository;
import mk.ukim.finki.wp.lab.repository.DishRepository;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DishServiceImpl implements DishService {
    private final DishRepository dishRepository;
    private final ChefRepository chefRepository;

    public DishServiceImpl(DishRepository dishRepository, ChefRepository chefRepository) {
        this.dishRepository = dishRepository;
        this.chefRepository = chefRepository;
    }

    @Override
    public List<Dish> listDishes() {
        return dishRepository.findAll();
    }

    @Override
    public Dish findByDishId(String dishId) {
        if (dishId == null || dishId.isEmpty()) {
            throw new IllegalArgumentException("Dish ID cannot be null or empty");
        }

        Dish dish = dishRepository.findByDishId(dishId);
        if (dish == null) {
            throw new DishNotFoundException();
        }
        return dish;
    }

    @Override
    public Dish findById(Long id) {
        return dishRepository.findById(id).orElseThrow(DishNotFoundException::new);
    }

    @Override
    public Dish create(String name, String cuisine, int preparationTime, Long chefId) {
        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(ChefNotFoundException::new);

        // Auto-generate a unique dishId using UUID
        String dishId = UUID.randomUUID().toString();

        Dish dish = new Dish(dishId, name, cuisine, preparationTime);
        dish.setChef(chef);
        return dishRepository.save(dish);
    }

    @Override
    public Dish update(Long id, String name, String cuisine, int preparationTime, Long chefId) {
        Dish dish = findById(id);
        Chef chef = chefRepository.findById(chefId)
                .orElseThrow(ChefNotFoundException::new);

        // Keep the existing dishId, don't update it
        dish.setName(name);
        dish.setCuisine(cuisine);
        dish.setPreparationTime(preparationTime);
        dish.setChef(chef);

        return dishRepository.save(dish);
    }

    @Override
    public void delete(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public List<Dish> findByChefId(Long chefId) {
        return dishRepository.findAllByChef_Id(chefId);
    }
}