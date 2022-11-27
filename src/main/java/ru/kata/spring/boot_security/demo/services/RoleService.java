package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface RoleService {


    List<Role> index();

    Role getRole(Long id);

    List<Role> getRolesListById(List<Long> roles);
}
