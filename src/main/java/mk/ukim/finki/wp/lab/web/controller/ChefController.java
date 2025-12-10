package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Chef;
import mk.ukim.finki.wp.lab.model.Dish;
import mk.ukim.finki.wp.lab.service.ChefService;
import mk.ukim.finki.wp.lab.service.DishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/chefs")
public class ChefController {
    private final ChefService chefService;
    private final DishService dishService;

    public ChefController(ChefService chefService, DishService dishService) {
        this.chefService = chefService;
        this.dishService = dishService;
    }

    @GetMapping
    public String listChefs(Model model) {
        List<Chef> chefs = chefService.listChefs();
        model.addAttribute("chefs", chefs);
        return "listChefs";
    }

    @GetMapping("/{id}")
    public String getChefDetails(@PathVariable Long id, Model model) {
        Chef chef = chefService.findById(id);
        List<Dish> dishes = dishService.findByChefId(id);
        model.addAttribute("chef", chef);
        model.addAttribute("dishes", dishes);
        return "chefDetails";
    }

    @PostMapping("/selectChef")
    public String selectChef(@RequestParam Long chefId) {
        return "redirect:/dishes?chefId=" + chefId;
    }

    @PostMapping("/addDish")
    public String addDishToChef(@RequestParam Long chefId,
                                @RequestParam String dishId,
                                Model model) {
        Chef updatedChef = chefService.addDishToChef(chefId, dishId);
        model.addAttribute("chef", updatedChef);
        return "chefDetails";
    }
}