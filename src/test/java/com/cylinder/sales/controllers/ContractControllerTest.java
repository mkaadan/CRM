package com.cylinder.sales.controllers;

import java.lang.Iterable;
import java.util.ArrayList;

import com.cylinder.sales.model.*;
import com.cylinder.sales.controllers.ContractsController;
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

public class ContractControllerTest extends ControllerTests{

  @InjectMocks
  ContractsController contractController;

  @MockBean
  private ContractRepository contractRepository;

  private ArrayList<Contract> mockContractListData(){
    ArrayList<Contract> contracts = new ArrayList();
    Contract contract = new Contract();
    contract.setContractId(new Long("1"));
    contract.setContractTitle("ContractOne");
    contracts.add(contract);
    contract = new Contract();
    contract.setContractId(new Long("2"));
    contract.setContractTitle("ContractTwo");
    contracts.add(contract);
    contract = new Contract();
    contract.setContractId(new Long("3"));
    contract.setContractTitle("ContractThree");
    contracts.add(contract);
    return contracts;
  }

  private Contract mockSingleContractData(){
    Contract contract = new Contract();
    contract.setContractId(new Long("1"));
    contract.setContractTitle("ContractOne");
    return contract;
  }

      @Before
      public void setup() {
          MockitoAnnotations.initMocks(this);
          mockMvc = MockMvcBuilders.standaloneSetup(contractController)
                  .apply(springSecurity(super.springSecurityFilterChain))
                  .build();
          super.mockUserRepository();
          Iterable<Contract> contract = mockContractListData();
          given(this.contractRepository.findAll()).willReturn(contract);
          given(this.contractRepository.findOne(eq(new Long("1")))).willReturn(mockSingleContractData());
          given(this.contractRepository.findOne(eq(new Long("1")))).willReturn(null);
          given(this.contractRepository.existsById(new Long("1"))).willReturn(true);
          given(this.contractRepository.existsById(new Long("5"))).willReturn(false);
          given(this.contractRepository.save(any(Contract.class))).willReturn(mockSingleContractData());
      }

      @Test
      @WithMockUser(username="fake@mail.com", authorities="USER")
      public void testIndex() throws Exception {
          this.mockMvc.perform(get("/contract"))
                  .andExpect(status().isOk());
      }

      @Test
      @WithAnonymousUser
      public void testIndexWithAnon() throws Exception {
          this.mockMvc.perform(get("/contract"))
                  .andExpect(status().isFound());
      }

      @Test
      @WithMockUser(username="fake@mail.com", authorities="USER")
      public void testRecordWithExistantRecord() throws Exception {
          this.mockMvc.perform(get("/contract/records/1"))
                  .andExpect(status().isOk());
      }

      @Test
      @WithMockUser(username="fake@mail.com", authorities="USER")
      public void testRecordWithNonExistantRecord() throws Exception {
          this.mockMvc.perform(get("/contract/records/5"))
                  .andExpect(status().isNotFound());
      }

      @Test
      @WithMockUser(username="fake@mail.com", authorities="USER")
      public void testGetEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contract/edit/{id}", new Long("1")).with(csrf()))
                    .andExpect(status().isOk());
      }

      @Test
      @WithMockUser(username="fake@mail.com", authorities="USER")
      public void testGetEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contract/edit/{id}", new Long("5")).with(csrf()))
                    .andExpect(status().isNotFound());
      }

      @Test
      @WithMockUser(username="fake@mail.com", authorities="USER")
      public void testPostEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(post("/contract/edit/{id}", new Long("1"))
                        .param("contractId","1")
                        .with(csrf()))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/contract/records/1"));
        verify(this.contractRepository, times(1)).save(any(Contract.class));
      }

        @Test
        @WithMockUser(username="fake@mail.com", authorities="USER")
        public void testPostEditRecordWithNonExistantRecord() throws Exception {
            this.mockMvc.perform(post("/contract/edit/{id}", new Long("5"))
                        .param("contractId","5")
                        .with(csrf()))
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(username="fake@mail.com", authorities="USER")
        public void testPostEditRecordWithExistantRecordInvalidData() throws Exception {
          this.mockMvc.perform(post("/contract/edit/{id}", new Long("1"))
                        .param("contractId","1")
                        .param("contractTitle", "Hello 34554")
                        .with(csrf()))
                    .andExpect(model().attributeHasFieldErrors("contractData", "contractTitle"))
                    .andExpect(status().isOk());
          }

        @Test
        @WithMockUser(username="fake@mail.com", authorities="USER")
        public void testNewRecord() throws Exception {
            this.mockMvc.perform(get("/contract/new/")
                            .with(csrf()))
                    .andExpect(status().isOk());
        }

        @Test
        @WithMockUser(username="fake@mail.com", authorities="USER")
        public void testPostNewRecordWithValidData() throws Exception {
          this.mockMvc.perform(post("/contract/new/")
                        .param("contractId","1")
                        .with(csrf()))
                  .andExpect(status().is3xxRedirection());
          verify(this.contractRepository, times(1)).save(any(Contract.class));
          }

        @Test
        @WithMockUser(username="fake@mail.com", authorities="USER")
        public void testPostNewRecordWithInvalidData() throws Exception {
          this.mockMvc.perform(post("/contract/new/")
                        .param("contractId","1")
                        .param("contractTitle", "Hello 6767")
                        .with(csrf()))
                  .andExpect(model().attributeHasFieldErrors("contractData", "contractTitle"))
                  .andExpect(status().isOk());
          }

  }
