package com.ecommerce.authservice.repository;


import com.ecommerce.authservice.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByAuthority(String authority);
}
