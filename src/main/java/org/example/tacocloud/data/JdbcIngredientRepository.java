package org.example.tacocloud.data;

import org.example.tacocloud.entity.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public class JdbcIngredientRepository implements IngredientRepository{
//    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public Iterable<Ingredient> findAll() {
//        return jdbcTemplate.query(
//                "select id, name, type from Ingredient",
//                this::mapRowToIngredient);
//    }
//    @Override
//    public Optional<Ingredient> findById(String id) {
//        List<Ingredient> result = jdbcTemplate.query(
//                "select id, name, type from Ingredient where id=?",
//                this::mapRowToIngredient,
//                id);
//        if(result.size() == 0) return Optional.empty();
//        else return Optional.of(result.get(0));
//    }
//
//    @Override
//    public Ingredient save(Ingredient ingredient) {
//        jdbcTemplate.update(
//                "insert into Ingredient (id, name, type) values (?, ?, ?)",
//                ingredient.getId(),
//                ingredient.getName(),
//                ingredient.getType().toString());
//        return ingredient;
//    }
//    private Ingredient mapRowToIngredient(ResultSet resultSet, int rowNum) throws SQLException {
//        return new Ingredient(
//                resultSet.getString("id"),
//                resultSet.getString("name"),
//                Ingredient.Type.valueOf(resultSet.getString("type")));
//    }
//}
