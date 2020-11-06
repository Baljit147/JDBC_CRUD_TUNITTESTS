package ca.sheridancollege;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import ca.sheridancollege.beans.Drink;
import ca.sheridancollege.database.DatabaseAccess;

@SpringBootTest
@AutoConfigureMockMvc
public class DrinkTestCases {
//  We will use this whenever we are testing a url, mockMvc that is
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	@Autowired
	private DatabaseAccess da;
	
	
	@Test
	public void testLoadingAddDrinkPage() throws Exception {
		this.mockMvc.perform(get("/")) //url localhost:8080/
			.andExpect(status().isOk()) //This is a 200 status code, 200 meaning all good.
			.andExpect(view().name("home.html")); //home.htmml page is returned.
	}
	
	@Test
	public void testAddingDrink() throws Exception {
		this.mockMvc.perform(get("/add") //url localhost:8080/add
			.flashAttr("drink", new Drink())) //Form binding with a Drink object
			.andExpect(status().isOk())
			.andExpect(view().name("home.html"));
	}
	
	@Test
	public void testViewDrinksPage() throws Exception {
		this.mockMvc.perform(get("/view")) //url localhost:8080/add
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("drinkList"))
			.andExpect(view().name("view.html"));
	}
	
	@Test
	public void testDeleteDrink() throws Exception {
		this.mockMvc.perform(get("/delete/{id}",1)) //url localhost:8080/add
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/view")); //redirect to localhost:8080/view
	}
	
	@Test
	public void testDefaultDrinks() throws Exception {
		ArrayList<Drink> drinks = da.getDrinks2();
		if (drinks != null && drinks.size() > 0) {
			assert true;
		} else {
			assert false;
		}
	}
	
	@Test
	public void testAddDrinkToDatabase() throws Exception {
		
		int originalSize = da.getDrinks2().size();
		da.addDrink(new Drink(100, "a", "b", 1, "c", 2, "d"));
		int newSize = da.getDrinks2().size();
		
		assertThat(newSize).isEqualTo(originalSize + 1);

	}
	
	@Test
	public void testGetDrinkByIdGood() throws Exception{
		Drink d = da.getDrinkById(2);
		assertThat(d).isNotNull();
	}
	
	@Test
	public void testGetDrinkByIdBad() throws Exception{
		Drink d = da.getDrinkById(50);
		assertThat(d).isNull();
	}
	
	@Test
	public void testDeleteById() throws Exception{
		Drink d = da.getDrinkById(3);
		if(d == null) {
			assert false;
		} else {
			da.deleteDrink(3);
			d = da.getDrinkById(3);
			assertThat(d).isNull();
		} 
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
