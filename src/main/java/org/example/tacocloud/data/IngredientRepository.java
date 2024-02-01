package org.example.tacocloud.data;

import org.example.tacocloud.entity.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository
        extends CrudRepository<Ingredient, String> {
}
