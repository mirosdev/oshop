package com.example.springframework.oshop.services.implementations;

import com.example.springframework.oshop.domain.Role;
import com.example.springframework.oshop.repositories.RoleRepository;
import com.example.springframework.oshop.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
