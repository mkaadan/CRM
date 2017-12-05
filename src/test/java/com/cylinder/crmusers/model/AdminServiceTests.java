package com.cylinder.crmusers.model;

import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.Role;
import org.junit.*;
import org.mockito.*;
import static org.junit.Assert.*;

import com.cylinder.crmusers.model.services.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
public class AdminServiceTests {

  @InjectMocks
  AdminServiceImpl service;

  @MockBean
  protected CrmUserRepository userRepository;

  @MockBean
  protected RoleRepository roleRepository;

  protected Role mockAdminRole() {
    Role role = new Role();
    role.setRoleId(new Long("1"));
    role.setRoleName("ADMIN");
    return role;
  }

  protected CrmUser mockUserData() {
    CrmUser user = new CrmUser();
    user.setAccountId(new Long("1"));
    user.setFirstName("Bob");
    user.setLastName("Saget");
    user.setEmail("fake@mail.com");
    user.setPassword("heyya");
    user.setRole(mockAdminRole());
    return user;
  }

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    given(roleRepository.findByRoleName(eq("ADMIN"))).willReturn(mockAdminRole());
    given(service.findUserByAccountId(any(Long.class))).willReturn(mockUserData());
  }

  @Test
  public void testIsOnlyAdminIsTrue() {
    given(userRepository.countByRoleId(eq(new Long("1")))).willReturn(new Long("1"));
    assertEquals(service.isOnlyAdmin(new Long("1")), true);
  }

  @Test
  public void testIsOnlyAdminIsFalse() {
    given(userRepository.countByRoleId(eq(new Long("1")))).willReturn(new Long("2"));
    assertEquals(service.isOnlyAdmin(new Long("1")), false);
  }
}
