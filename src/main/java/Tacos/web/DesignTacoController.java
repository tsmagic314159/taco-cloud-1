package Tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import Tacos.Ingredient;
import Tacos.Taco;
import Tacos.Ingredient.Type;
import Tacos.Order;
import Tacos.data.IngredientRepository;
import Tacos.data.TacoRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {
	
	private final IngredientRepository ingredientRepo;
	
	private TacoRepository designRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
		this.ingredientRepo = ingredientRepo;
		this.designRepo = designRepo;
	}
		
	
	@GetMapping
	public String showDesignForm(Model model) {
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
		Type[] types = Ingredient.Type.values();
		for(Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
					filterbyType(ingredients, type));
		}
		
		model.addAttribute("design", new Taco());
		
		return "design";
	}
	
	private List<Ingredient> filterbyType(List<Ingredient> ingredients, Type type){
		//此处为一个lambda表达式，用来筛选出其中type相同的元素
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@PostMapping
	//这里的@ModelAttribute表明Order的值是来自模型的，SpringMVC不会尝试将请求参数绑定到它上边。
	public String processDesign(@Valid Taco design, Errors errors, @ModelAttribute Order order) {
		
		
		if (errors.hasErrors()) {
			return "redirect:/design";
		}

		Taco saved = designRepo.save(design);
		order.addDesign(saved);
		
		log.info("Processing design: " + design);
		
		return "redirect:/orders/current";
	}
}
