package com.cylinder.crmusers.model;

import com.cylinder.RespositoryTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class CrmUserRepositoryTests extends RespositoryTests {

    @Autowired
    CrmUserRepository userRepository;

    @Before
    public void initData() {
        Role adminRole = new Role();
        adminRole.setRoleId(new Long("1"));
        adminRole.setRoleName("ADMIN");
        Role userRole = new Role();
        userRole.setRoleId(new Long("2"));
        userRole.setRoleName("USER");
        CrmUser user = new CrmUser();
        user.setEmail("testball@gmail.com");
        user.setPassword("123pass");
        user.setIsEnabled(true);
        user.setFirstName("Test");
        user.setLastName("McGee");
        user.setRole(adminRole);
        userRepository.save(user);
    }

    @Test
    public void testFindByEmail() {
        CrmUser user = userRepository.findByEmail("testball@gmail.com");
        assertEquals(user == null, false);
        assertEquals(user.getLastName() == "McGee", true);
        assertEquals(user.getAccountId() == 0, true);
        user = userRepository.findByEmail("not@gmail.com");
        assertEquals(user == null, true);
    }

    @Test
    public void testFindByAccountId() {
        Long accountId = new Long("0");
        CrmUser user = userRepository.findByAccountId(accountId);
        assertEquals(user == null, false);
        assertEquals(user.getLastName() == "McGee", true);
        assertEquals(user.getAccountId() == 0, true);
        user = userRepository.findByAccountId(new Long("1"));
        assertEquals(user == null, true);
    }

    @Test
    public void testExistsByAccountId() {
        assertEquals(userRepository.existsByAccountId(new Long("0")), true);
        assertEquals(userRepository.existsByAccountId(new Long("1")), false);
    }

    @Test
    public void testExistsByAccountIdAndEmail() {
        assertEquals(userRepository.existsByAccountIdAndEmail(new Long("0"), "testball@gmail.com"), true);
        assertEquals(userRepository.existsByAccountIdAndEmail(new Long("11"), "testfake@gmail.com"), false);
        assertEquals(userRepository.existsByAccountIdAndEmail(new Long("1"), "testfake@gmail.com"), false);
    }

}
