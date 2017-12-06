package com.cylinder;

import com.cylinder.crmusers.model.CrmUser;
import com.cylinder.crmusers.model.CrmUserRepository;
import com.cylinder.crmusers.model.Role;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import static org.mockito.BDDMockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class ControllerTests {

  @MockBean
  protected CrmUserRepository userRepository;

  @Autowired
  protected FilterChainProxy springSecurityFilterChain;

  protected MockMvc mockMvc;

  protected CrmUser getUserData() {
    CrmUser user = new CrmUser();
    user.setAccountId(new Long("1"));
    user.setFirstName("Bob");
    user.setLastName("Saget");
    user.setEmail("fake@mail.com");
    user.setPassword("$2a$12$pb3lrDyioOI.jb1U9ZLUMOUAzKUp4vMdxg9CoEHp0ZwjfD1o/Qf8a");
    user.setRole(mockRoles().get(0));
    return user;
  }

  protected ArrayList<CrmUser> mockUserData() {
    ArrayList<CrmUser> users = new ArrayList();
    CrmUser user = new CrmUser();
    user.setAccountId(new Long("1"));
    user.setLastName("Saget");
    user.setFirstName("Bob");
    user.setEmail("fake@mail.com");
    user.setRole(mockRoles().get(0));
    users.add(user);
    user = new CrmUser();
    user.setAccountId(new Long("2"));
    user.setLastName("Sageta");
    user.setFirstName("Boba");
    user.setEmail("fakea@mail.com");
    user.setRole(mockRoles().get(0));
    users.add(user);
    user.setAccountId(new Long("3"));
    user.setLastName("Sagetb");
    user.setFirstName("Bobb");
    user.setEmail("fakeb@mail.com");
    user.setRole(mockRoles().get(0));
    users.add(user);
    return users;
  }

  protected ArrayList<Role> mockRoles() {
    ArrayList<Role> roles = new ArrayList();
    Role role = new Role();
    role.setRoleId(new Long("1"));
    role.setRoleName("ADMIN");
    roles.add(role);
    role = new Role();
    role.setRoleId(new Long("2"));
    role.setRoleName("USER");
    roles.add(role);
    return roles;
  }

  protected void mockUserRepository() {
    given(this.userRepository.findAll()).willReturn(mockUserData());
    given(this.userRepository.findByEmail(anyString())).willReturn(getUserData());
  }
}
