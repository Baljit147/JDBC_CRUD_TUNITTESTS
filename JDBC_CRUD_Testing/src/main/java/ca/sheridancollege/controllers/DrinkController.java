package ca.sheridancollege.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import ca.sheridancollege.beans.Drink;
import ca.sheridancollege.database.DatabaseAccess;

@Controller
public class DrinkController {
	@Autowired
	private DatabaseAccess da;

	@GetMapping("/")
	public String goHome(Model model) {
		model.addAttribute("drink", new Drink(0, "name", "main", 1.0, "second", 1.0, "directions"));
		return "home.html";
	}
	
	@GetMapping("/add")
	public String add(Model model, @ModelAttribute Drink drink) {
		da.addDrink(drink);
		model.addAttribute("drink", new Drink());
		return "home.html";
	}
	
	@GetMapping("/view")
	public String viewDrinks(Model model, @ModelAttribute Drink drink) {
		model.addAttribute("drinkList", da.getDrinks2());
		return "view.html";
	}
	
	@GetMapping("/edit/{id}")
	public String editDrink(Model model, @PathVariable int id) {
		Drink drink = da.getDrinkById(id);
		model.addAttribute("drink", drink);
		return "edit.html";
	}
	
	@GetMapping("/modify")
	public String editDrink(Model model, @ModelAttribute Drink drink) {
		da.editDrink(drink);
		return "redirect:/view;";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteDrink(Model model, @PathVariable int id) {
		da.deleteDrink(id);
		return "redirect:/view";
	}
	
}




