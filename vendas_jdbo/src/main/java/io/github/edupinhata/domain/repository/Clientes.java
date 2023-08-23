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
    private static String UPDATE_QUERY = "update cliente set nome = ? where id = ?";
    private static String DELETE_QUERY = "delete from cliente where id = ?";
    private static String SELECT_BY_NAME = "select * from cliente where nome = ?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Cliente salvar(Cliente cliente){
        jdbcTemplate.update( INSERT_QUERY, new Object[]{cliente.getNome()} );
        return cliente;
    }

    public Cliente atualizar(Cliente cliente){
        jdbcTemplate.update( UPDATE_QUERY, new Object[]{cliente.getNome(), cliente.getId()});
        return cliente;
    }

    public void deletar(int id){
        jdbcTemplate.update( DELETE_QUERY, new Object[]{ id});
    }

    public void deletar(Cliente cliente){
        deletar(cliente.getId());
    }

    public List<Cliente> getAll(){
        return jdbcTemplate.query(SELECT_ALL_QUERY, getRowMapper());
    }

    public List<Cliente> searchByName(String name){
        return jdbcTemplate.query(SELECT_ALL_QUERY.concat(" where nome like ?"),
                new Object[]{"%" + name + "%"},
                getRowMapper() );
    }

    private static RowMapper<Cliente> getRowMapper() {
        return new RowMapper<Cliente>() {
            @Override
            public Cliente mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Cliente(resultSet.getInt("id"), resultSet.getString("nome"));
            }
        };
    }
}
