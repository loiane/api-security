package com.loiane.api.product;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ProductDAO {
  
  private final JdbcTemplate template;

  public ProductDAO(JdbcTemplate template) {
    this.template = template;
  }

  private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
    rs.getLong("id"),
    rs.getString("name"),
    rs.getString("description"),
    rs.getDouble("price")
  );

  public List<Product> search(String productName) {
    String queryString = "SELECT * FROM product WHERE name LIKE '%"
     + productName + " %'OR description LIKE '%" + productName + "%'";
    return template.query(queryString, productRowMapper);
  }
}
