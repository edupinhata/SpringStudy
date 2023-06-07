package io.github.edupinhata.domain.repository;

import io.github.edupinhata.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class Clientes {

    private static String INSERT_QUERY = "insert into cliente (nome) values (?)";
    private static String UPDATE_QUERY = "UPDATE cliente SET nome=? WHERE id=?";
    private static String DELETE_QUERY = "DELETE FROM cliente WHERE id=?";
    private static String SELECT_ALL_QUERY = "select * from cliente";
    private static String SELECT_BY_NAME_QUERY = "select * from cliente where nome like ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente save(Cliente cliente){
        jdbcTemplate.update( INSERT_QUERY, new Object[]{cliente.getNome()} );
        return cliente;
    }

    public Cliente update(Cliente cliente){
        jdbcTemplate.update( UPDATE_QUERY, new Object[]{cliente.getNome(), cliente.getId()} );
        return cliente;
    }

    public void delete(Cliente cliente){
        delete(cliente.getId());
    }

    public void delete(Integer id){
        jdbcTemplate.update(DELETE_QUERY, new Object[]{id});
    }

    public List<Cliente> getAll(){
        return jdbcTemplate.query(SELECT_ALL_QUERY, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        });
    }

    public List<Cliente> getByName(String name){
        return jdbcTemplate.query(SELECT_BY_NAME_QUERY,
                new Object[]{"%" + name + "%"},
                new RowMapper<Cliente>(){
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(
                        resultSet.getInt("id"),
                        resultSet.getString("nome")
                );
            }
        });
    }
}
