package io.github.edupinhata.domain.repository;

import io.github.edupinhata.domain.entity.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
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

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Cliente salvar(Cliente cliente){
        // Persist a client that was never in entityManager before
        // When I persist it, the entityManager will start to track it
        System.out.println("Saving client: " + cliente.getNome());
        if (!entityManager.contains(cliente)) {
            cliente = entityManager.merge(cliente);
        }
        entityManager.persist(cliente);
        return cliente;
    }

    @Transactional
    public Cliente update(Cliente cliente){
        // The merge updates an entity that is already been
        // managed by the entityManager
        System.out.println("Updating client: " + cliente.getNome());
        entityManager.merge(cliente);
        return cliente;
    }

    @Transactional
    public void deletar(int id){
        Cliente cliente = entityManager.find( Cliente.class, id);
        deletar(cliente);
    }

    @Transactional
    public void deletar(Cliente cliente)
    {
        System.out.println("Deleting client: " + cliente.getNome());
        if (!entityManager.contains(cliente))
        {
            cliente = entityManager.merge(cliente);
        }
        entityManager.remove(cliente);
    }

    @Transactional
    public List<Cliente> getAll(){
        return entityManager
                .createQuery("from Cliente", Cliente.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public List<Cliente> searchByName(String name){
        String jpql = "select c from Cliente c where c.nome like :nome";
        TypedQuery<Cliente> query = entityManager.createQuery(jpql, Cliente.class);
        query.setParameter("nome", "%" + name + "%");
        return query.getResultList();
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
