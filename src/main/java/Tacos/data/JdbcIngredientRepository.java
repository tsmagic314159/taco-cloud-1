package Tacos.data;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import Tacos.Ingredient;

@Repository
public class JdbcIngredientRepository implements IngredientRepository{
	
	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcIngredientRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public Iterable<Ingredient> findAll() {
		return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);
		//该处采用了lambda表达式来代替rowMapper的实现可以采用构建rowMapper来替换该表达式
//		new RowMapper<>() {
//			public Ingredient mapRow(ResultSet rs, int rowNum) throws SQLException{
//				return new Ingredient(rs.getString("id").....)
//			}
//		}
	}

	@Override
	public Ingredient findOne(String id) {
		return jdbc.queryForObject("select id, name, type from Ingredient where id = ?", 
				this::mapRowToIngredient, id);
	}

	@Override
	public Ingredient save(Ingredient ingredient) {
		jdbc.update("insert into Ingredient (id, name, type) values(? ,?, ?)",
				ingredient.getId(), ingredient.getName(), ingredient.getType().toString());
		return ingredient;
	}
	
	//这里的ResultSet映射为对象
	private Ingredient mapRowToIngredient(ResultSet rs, int rowNum) throws SQLException {
		return new Ingredient(rs.getString("id"), rs.getString("name"), Ingredient.Type.valueOf(rs.getString("type")));
	}
	
}
