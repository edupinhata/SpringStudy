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
    private static String SELECT_ALL_QUERY = "select * from cliente";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update( INSERT_QUERY, new Object[]{cliente.getNome()} );
        return cliente;
    }

    public List<Cliente> getAll(){
        return jdbcTemplate.query(SELECT_ALL_QUERY, new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        });
    }
}
