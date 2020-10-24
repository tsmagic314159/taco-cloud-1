package Tacos.data;

import org.springframework.data.repository.CrudRepository;

import Tacos.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String>{
	
}
