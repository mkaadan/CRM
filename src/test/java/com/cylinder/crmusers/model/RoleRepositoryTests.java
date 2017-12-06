package com.cylinder.crmusers.model;

import com.cylinder.RespositoryTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class RoleRepositoryTests extends RespositoryTests {

    @Autowired
    RoleRepository roleRepository;

    @Before
    public void initData() {
        Role role = new Role();
        role.setRoleId(new Long("1"));
        role.setRoleName("ADMIN");
        roleRepository.save(role);
        role = new Role();
        role.setRoleId(new Long("2"));
        role.setRoleName("USER");
        roleRepository.save(role);
    }

    @Test
    public void testFindByRoleName() {
        Role role = roleRepository.findByRoleName("ADMIN");
        assertEquals(role.getRoleId().equals(new Long("1")), true);
        assertEquals(role.getRoleName().equals("ADMIN"), true);
        role = roleRepository.findByRoleName("NOTAROLE");
        assertEquals(role == null, true);
    }

    @Test
    public void testExistsByRoleAndId() {
        Long id = new Long("1");
        String roleName = "ADMIN";
        assertEquals(roleRepository.existsByRoleAndId(roleName, id), true);
        id = new Long("7");
        roleName = "USER";
        assertEquals(roleRepository.existsByRoleAndId(roleName, id), false);
        id = new Long("2");
        roleName = "NOTAROLE";
        assertEquals(roleRepository.existsByRoleAndId(roleName, id), false);
    }

}
