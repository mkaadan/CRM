package com.cylinder.crmusers.controllers;

import java.lang.Iterable;
import java.util.ArrayList;

import com.cylinder.crmusers.model.*;
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


@SpringBootTest
public class UserControllerTests extends ControllerTests {

   @InjectMocks
   UserController userController;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(userController)
                             .apply(springSecurity(super.springSecurityFilterChain))
                             .build();
    super.mockUserRepository();
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testEditUserPassword() throws Exception {
    this.mockMvc.perform(get("/user/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
  }
}
