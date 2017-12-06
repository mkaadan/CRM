package com.cylinder.crmusers.controllers;

import java.lang.Iterable;
import java.util.ArrayList;

import com.cylinder.crmusers.model.*;
import com.cylinder.crmusers.model.services.*;
import com.cylinder.crmusers.controllers.UserController;
import com.cylinder.ControllerTests;

import org.junit.*;
import static org.junit.Assert.*;

import org.mockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.BDDMockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.mockito.Matchers;
import static org.mockito.Mockito.times;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithAnonymousUser;

import org.springframework.security.core.Authentication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
public class AdminControllerTests extends ControllerTests {

  @InjectMocks
  private AdminController adminController;

  @MockBean
  private AdminService service;

  @MockBean
  private BCryptPasswordEncoder passwordEncoder;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(adminController)
                             .apply(springSecurity(super.springSecurityFilterChain))
                             .build();
    given(this.service.findAllRoles()).willReturn(super.mockRoles());
    given(this.service.findAllUsers()).willReturn(super.mockUserData());
    given(this.service.getUserRepository()).willReturn(userRepository);
    given(this.userRepository.findByEmail(anyString())).willReturn(getUserData());
    given(this.service.userExistsByAccountId(eq(new Long("1")))).willReturn(true);
    given(this.service.userExistsByAccountId(eq(new Long("2")))).willReturn(false);
    given(this.service.findUserByAccountId(eq(new Long("1")))).willReturn(getUserData());
    given(this.service.isOnlyAdmin(eq(new Long("1")))).willReturn(false);
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testIndex() throws Exception {
    this.mockMvc.perform(get("/admin/user/overview"))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testGetNew() throws Exception {
    this.mockMvc.perform(get("/admin/user/new"))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testGetNewPostValidData() throws Exception {
    this.mockMvc.perform(post("/admin/user/new")
                          .param("firstName", "Bob")
                          .param("lastName", "Noop")
                          .param("email", "myemail@gmail.com")
                          .param("password", "p@ss")
                          .param("role.roleId", "1")
                          .param("role.roleName", "ADMIN")
                          .with(csrf()))
                  .andExpect(status().is3xxRedirection())
                  .andExpect(redirectedUrl("/admin/user/overview"));
    verify(this.service, times(1)).saveUser(any(CrmUser.class));
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testGetNewPostInValidData() throws Exception {
    this.mockMvc.perform(post("/admin/user/new")
                          .param("firstName", "Bob")
                          .param("lastName", "Noop")
                          .param("email", "myemail@@gmail.com")
                          .param("password", "p@ss")
                          .param("role.roleId", "1")
                          .param("role.roleName", "ADMIN")
                          .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userForm", "email"));
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testGetEdit() throws Exception {
    this.mockMvc.perform(get("/admin/user/edit/1"))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testGetEditNotExistant() throws Exception {
    this.mockMvc.perform(get("/admin/user/edit/2"))
                .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testPostEditValidData() throws Exception {
    this.mockMvc.perform(post("/admin/user/edit/1")
                          .param("accountId", "1")
                          .param("email", "test@gmail.com")
                          .param("firstName", "Test")
                          .param("lastName", "Boo")
                          .param("role.roleId", "1")
                          .param("role.roleName", "Admin")
                          .param("isEnabled", "true")
                          .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/user/overview"));
    verify(this.service, times(1)).saveUser(any(CrmUser.class));
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="ADMIN")
  public void testPostEditInValidData() throws Exception {
    this.mockMvc.perform(post("/admin/user/edit/1")
                          .param("accountId", "1")
                          .param("email", "test@@gmail.com")
                          .param("firstName", "Test")
                          .param("lastName", "Boo")
                          .param("role.roleId", "1")
                          .param("role.roleName", "Admin")
                          .param("isEnabled", "true")
                          .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeHasFieldErrors("userForm", "email"));
  }
}
