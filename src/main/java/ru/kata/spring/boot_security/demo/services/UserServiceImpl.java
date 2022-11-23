package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;


import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {



    private final UserDao userRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(
            UserDao userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }




    public User findUserBYId(Long id) {
        Optional<User> userfromDB  = userRepository.show(id);
        return userfromDB.orElse(new User());
    }


    public List<User> allUsers() {
        return userRepository.index();
    }

    public boolean saveUser(User user) {
        Optional<User> userfromDB = userRepository.findByUsername(user.getUsername());

        if(!userfromDB.isEmpty()) {
            return false;
        }
//        user.setRoles(new Role("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean deleteUser(Long userId) {
        if (userRepository.show(userId).isPresent()) {
            userRepository.delete(userId);
            return true;
        }
        return false;
    }


    public boolean updateUser(Long userId, User user) {
        if(userRepository.show(userId).isPresent()) {
            User userforUpdate = userRepository.show(userId).get();
            userforUpdate.setUsername(user.getUsername());
            userforUpdate.setPassword(user.getPassword());
            userforUpdate.setEmail(user.getEmail());
            return true;
        }
        return false;
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<User> user =  userRepository.findByUsername(username);
//        if (user.isEmpty()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
//                user.get().getPassword(),mapRolesAuthorities(user.get().getRoles()));
        User user = userRepository.findByUsername(username).get();
        user.getRoles();
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("Пользователя с таким username не существует");
        }
    }

    @Override
    public void newUpdateUser(User user, List<Role> role) {
        String password = findUserBYId(user.getId()).getPassword();
        String pass = user.getPassword();
        if (!passwordEncoder.encode(user.getPassword()).equals(password)){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        role.stream().forEach(n -> user.addRole(n));
        userRepository.newupdate(user);
    }

    //    private Collection<? extends GrantedAuthority> mapRolesAuthorities(Collection<Role> roles) {
//        return roles.stream().map(role ->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }

}
