package ru.bazzar.auth.services;

import ru.bazzar.auth.entities.Role;

public interface RoleService {
    Role findRoleByName(String name);
}
