package org.example.tacocloud.controller;


import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.example.tacocloud.data.IngredientRepository;
import org.example.tacocloud.data.TacoRepository;
import org.example.tacocloud.data.UserRepository;
import org.example.tacocloud.entity.Ingredient;
import org.example.tacocloud.entity.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.example.tacocloud.entity.Ingredient.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {


    @Bean
    public CommandLineRunner dataLoader(
            IngredientRepository repo,
            UserRepository userRepo,
            PasswordEncoder encoder,
            TacoRepository tacoRepo) {
        return args -> {
            Ingredient flourTortilla = new Ingredient(
                    "FLTO", "Flour Tortilla", Type.WRAP);
            Ingredient cornTortilla = new Ingredient(
                    "COTO", "Corn Tortilla", Type.WRAP);
            Ingredient groundBeef = new Ingredient(
                    "GRBF", "Ground Beef", Type.PROTEIN);
            Ingredient carnitas = new Ingredient(
                    "CARN", "Carnitas", Type.PROTEIN);
            Ingredient tomatoes = new Ingredient(
                    "TMTO", "Diced Tomatoes", Type.VEGGIES);
            Ingredient lettuce = new Ingredient(
                    "LETC", "Lettuce", Type.VEGGIES);
            Ingredient cheddar = new Ingredient(
                    "CHED", "Cheddar", Type.CHEESE);
            Ingredient jack = new Ingredient(
                    "JACK", "Monterrey Jack", Type.CHEESE);
            Ingredient salsa = new Ingredient(
                    "SLSA", "Salsa", Type.SAUCE);
            Ingredient sourCream = new Ingredient(
                    "SRCR", "Sour Cream", Type.SAUCE);

            repo.save(flourTortilla);
            repo.save(cornTortilla);
            repo.save(groundBeef);
            repo.save(carnitas);
            repo.save(tomatoes);
            repo.save(lettuce);
            repo.save(cheddar);
            repo.save(jack);
            repo.save(salsa);
            repo.save(sourCream);

            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Arrays.asList(
                    flourTortilla, groundBeef, carnitas,
                    sourCream, salsa, cheddar));
            tacoRepo.save(taco1);

            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Arrays.asList(
                    cornTortilla, groundBeef, cheddar,
                    jack, sourCream));
            tacoRepo.save(taco2);

            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Arrays.asList(
                    flourTortilla, cornTortilla, tomatoes,
                    lettuce, salsa));
            tacoRepo.save(taco3);
        };
    }
    private TacoRepository tacoRepo;
    private final IngredientRepository ingredientRepo;
    @Autowired
    public DesignTacoController(IngredientRepository ingredientRepository, TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
        this.ingredientRepo = ingredientRepository;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        Iterable<Ingredient> ingredients = ingredientRepo.findAll();
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType((List<Ingredient>) ingredients, type));
        }
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("taco", new Taco());
        return "design";
    }
    @PostMapping
    public String processTaco(@Valid @ModelAttribute("taco") Taco taco, Errors errors) {
        if(errors.hasErrors()) {
            return "design";
        }
        tacoRepo.save(taco);
        log.info("Processing taco: " + taco);
        return "redirect:/orders/current";
    }
    private Iterable<Ingredient> filterByType(
            List<Ingredient> ingredients, Type type) {
        return ingredients
                .stream()
                .filter(x -> x.getType().equals(type))
                .collect(Collectors.toList());
    }
}
