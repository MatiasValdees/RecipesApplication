package com.example.recetario_app.infrastructure.controllers;

import com.example.recetario_app.domain.entities.recipe.IngredientEntity;
import com.example.recetario_app.infrastructure.dtos.ingredient.IngredientRequest;
import com.example.recetario_app.services.ingredient.IIngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IIngredientService ingredientService;
    private final String VIEW_INGREDIENT_FORM="ingredients/form";
    @GetMapping
    public String listIngredients(Model model) {
        model.addAttribute("ingredients", ingredientService.findAll());
        return "ingredients/list";
    }

    @GetMapping("/create")
    public String createIngredient(Model model) {
        model.addAttribute("ingredient", new IngredientEntity());
        return VIEW_INGREDIENT_FORM;
    }

    @PostMapping("/save")
    public String saveIngredient(@Valid @ModelAttribute("ingredient") IngredientRequest ingredient,
                                 BindingResult result,
                                 Model model) {

        if (result.hasErrors()) {
            return VIEW_INGREDIENT_FORM;
        }

        ingredientService.create(ingredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit/{id}")
    public String editIngredient(@PathVariable Integer id, Model model) {
        model.addAttribute("ingredient", ingredientService.findById(id));
        return VIEW_INGREDIENT_FORM;
    }

    @GetMapping("/delete/{id}")
    public String deleteIngredient(@PathVariable Integer id) {
        ingredientService.delete(id);
        return "redirect:/ingredients";
    }
}
