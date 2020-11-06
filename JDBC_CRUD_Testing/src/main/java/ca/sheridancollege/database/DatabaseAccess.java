package ca.sheridancollege.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.beans.Drink;

@Repository
public class DatabaseAccess {

	@Autowired
	protected NamedParameterJdbcTemplate jdbc;
	
	public void addDrink() {
		String query = "INSERT INTO easy_drinks VALUES" +"('Jons Drink', 'Soda', 3, 'lime juice', 0.5, 'Stir with ice')";
		jdbc.update(query, new HashMap());
	}
	
	public void addDrink(Drink drink) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "INSERT INTO easy_drinks (name, main, amount1, second, amount2, directions) VALUES (:name, :main, :a1, :sec, :a2, :dir)";
		parameters.addValue("name", drink.getName());
		parameters.addValue("main", drink.getMain());
		parameters.addValue("a1", drink.getAmount1());
		parameters.addValue("sec", drink.getSecond());
		parameters.addValue("a2", drink.getAmount2());
		parameters.addValue("dir", drink.getDirections());
		
		jdbc.update(query, parameters);
	}
	
	public Drink getDrinkById(int id) {
		ArrayList<Drink> drinks = new ArrayList<Drink>();
		String query = "SELECT * FROM EASY_DRINKS WHERE id=:id";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("id", id);
		
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		
		for(Map<String,Object>row: rows) {
			Drink d = new Drink();
			d.setId((Integer)row.get("id"));
			d.setName((String)row.get("name"));
			d.setMain((String)row.get("main"));
//			d.setAmount1(((BigDecimal)row.get("amount1")).doubleValue());
			d.setAmount1((double)row.get("amount1"));
			d.setSecond((String)row.get("second"));
			d.setAmount2((double)row.get("amount2"));
//			d.setAmount2(((BigDecimal)row.get("amount2")).doubleValue());
			d.setDirections((String)row.get("directions"));
			drinks.add(d);
		}
		if (drinks.size() > 0)
			return drinks.get(0);
		return null;

//		return drinks;
	}
	
	public ArrayList<Drink> getDrinks2() {
		String query = "SELECT * FROM EASY_DRINKS";
		ArrayList<Drink> drinks = (ArrayList<Drink>) jdbc.query(query, new BeanPropertyRowMapper<Drink>(Drink.class));
		
		return drinks;
	}
	
	public void editDrink(Drink drink) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE easy_drinks SET name=:name, main=:main, amount1=:a1, second=:sec, amount2=:a2,"+
				" directions=:dir WHERE id=:id";
		
		parameters.addValue("id", drink.getId());
		parameters.addValue("name", drink.getName());
		parameters.addValue("main", drink.getMain());
		parameters.addValue("a1", drink.getAmount1());
		parameters.addValue("sec", drink.getSecond());
		parameters.addValue("a2", drink.getAmount2());
		parameters.addValue("dir", drink.getDirections());
		
		jdbc.update(query, parameters);
	}
	
	public void deleteDrink(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "DELETE FROM easy_drinks WHERE id=:id";
		parameters.addValue("id", id);
		
		jdbc.update(query, parameters);
	}
}
