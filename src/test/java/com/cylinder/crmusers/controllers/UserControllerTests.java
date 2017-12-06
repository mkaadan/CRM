package com.cylinder.crmusers.controllers;

import com.cylinder.ControllerTests;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTests extends ControllerTests {

    @InjectMocks
    UserController userController;

    @MockBean
    BCryptPasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();
        super.mockUserRepository();
        given(this.userRepository.existsByAccountId(new Long("1"))).willReturn(true);
        given(this.userRepository.existsByAccountId(new Long("5"))).willReturn(false);
        given(this.userRepository.existsByAccountId(new Long("2"))).willReturn(true);
        given(this.passwordEncoder.matches(eq("p@ssw0rd"),
                eq("$2a$12$pb3lrDyioOI.jb1U9ZLUMOUAzKUp4vMdxg9CoEHp0ZwjfD1o/Qf8a")))
                .willReturn(true);
        given(this.passwordEncoder.matches(eq("p@ssw0rd321"),
                eq("$2a$12$pb3lrDyioOI.jb1U9ZLUMOUAzKUp4vMdxg9CoEHp0ZwjfD1o/Qf8a")))
                .willReturn(false);
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testEditUserPassword() throws Exception {
        this.mockMvc.perform(get("/user/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testEditUserPasswordNoUserExists() throws Exception {
        this.mockMvc.perform(get("/user/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testEditUserPasswordRestrictedUser() throws Exception {
        this.mockMvc.perform(get("/user/edit/{id}", new Long("2")).with(csrf()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testEditUserPasswordPost() throws Exception {
        this.mockMvc.perform(post("/user/edit/{id}", new Long("1"))
                .param("accountId", "1")
                .param("currentPassword", "p@ssw0rd")
                .param("newPassword", "p@ssw0rd1")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(this.userRepository, times(1)).updatePassword(any(String.class), any(Long.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testEditUserPasswordPostBadData() throws Exception {
        this.mockMvc.perform(post("/user/edit/{id}", new Long("1"))
                .param("accountId", "1")
                .param("currentPassword", "p@ssw0rd321")
                .param("newPassword", "p@ssw0rd1")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("passForm", "currentPassword"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testEditUserPasswordNoUserExistsPost() throws Exception {
        this.mockMvc.perform(get("/user/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testEditUserPasswordRestrictedUserPost() throws Exception {
        this.mockMvc.perform(get("/user/edit/{id}", new Long("2")).with(csrf()))
                .andExpect(status().isForbidden());
    }
}
