package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.entities.User;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;



    @Override
    public List<User> index() {
        Query query = em.createQuery("from User");
        return query.getResultList();
    }

    @Override
    public Optional<User> show(Long id) {
        return Optional.of(em.find(User.class,id));
    }

    @Override
    public void save(User user) {
        em.persist(user);
    }




    @Override
    public void update(Long id, User user) {
        User userforUpdate = em.find(User.class,id);
        userforUpdate.setUsername(user.getUsername());
        userforUpdate.setPassword(user.getPassword());
        userforUpdate.setEmail(user.getEmail());
        em.merge(userforUpdate);
    }

    @Override
    public void delete(Long id) {
        em.remove(em.find(User.class,id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = em.createQuery("select u from User u join fetch u.roles where u.username =: username ");
        query.setParameter("username",username);
        return query.getResultList().stream().findFirst();
    }
}
