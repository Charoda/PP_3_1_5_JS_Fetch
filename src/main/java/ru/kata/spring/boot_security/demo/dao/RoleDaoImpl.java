package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Role> index() {
        Query query = em.createQuery("from Role");
        return query.getResultList();
    }


    @Override
    public Role getRole(Long id) {
        return em.find(Role.class,id);
    }
}
