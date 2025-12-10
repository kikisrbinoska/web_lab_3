package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.exceptions.DishNotFoundException;
import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;
    private final ChefService chefService;

    public DishController(DishService dishService, ChefService chefService) {
        this.dishService = dishService;
        this.chefService = chefService;
    }

    @GetMapping
    public String getDishesPage(@RequestParam(required = false) String error,
                                @RequestParam(required = false) Long chefId,
                                Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }

        List<Dish> dishes;
        if (chefId != null) {
            dishes = dishService.findByChefId(chefId);
        } else {
            dishes = dishService.listDishes();
        }

        List<Chef> chefs = chefService.listChefs();

        model.addAttribute("dishes", dishes);
        model.addAttribute("chefs", chefs);
        model.addAttribute("selectedChefId", chefId);

        return "dishesList";
    }

    @GetMapping("/dish-form")
    public String getAddDishPage(Model model) {
        List<Chef> chefs = chefService.listChefs();
        model.addAttribute("dish", new Dish());
        model.addAttribute("chefs", chefs);
        return "dish-form";
    }

    @PostMapping("/add")
    public String saveDish(@RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime,
                           @RequestParam Long chefId) {
        dishService.create(name, cuisine, preparationTime, chefId);
        return "redirect:/dishes";
    }

    @GetMapping("/dish-form/{id}")
    public String getEditDishForm(@PathVariable Long id, Model model) {
        try {
            Dish dish = dishService.findById(id);
            List<Chef> chefs = chefService.listChefs();
            model.addAttribute("dish", dish);
            model.addAttribute("chefs", chefs);
            return "dish-form";
        } catch (DishNotFoundException e) {
            return "redirect:/dishes?error=DishNotFound";
        }
    }

    @PostMapping("/edit/{id}")
    public String editDish(@PathVariable Long id,
                           @RequestParam String name,
                           @RequestParam String cuisine,
                           @RequestParam int preparationTime,
                           @RequestParam Long chefId) {
        dishService.update(id, name, cuisine, preparationTime, chefId);
        return "redirect:/dishes";
    }

    @PostMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.delete(id);
        return "redirect:/dishes";
    }
}