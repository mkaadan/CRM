package com.cylinder.deals.controllers;

import com.cylinder.ControllerTests;
import com.cylinder.accounts.model.Account;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.contacts.model.Contact;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.deals.model.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class DealControllerTest extends ControllerTests {

    @InjectMocks
    private DealController dealController;

    @MockBean
    private DealRepository dealRepository;

    @MockBean
    private StageRepository stageRepository;

    @MockBean
    private TypeRepository typeRepository;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private ContactRepository contactRepository;

    private ArrayList<Deal> mockDealListData() {
        ArrayList<Deal> deals = new ArrayList<>();

        Deal deal = new Deal();
        deal.setDealId(new Long("1"));
        deal.setDealName("Testa");
        deals.add(deal);

        deal = new Deal();
        deal.setDealId(new Long("2"));
        deal.setDealName("Testb");
        deals.add(deal);

        deal = new Deal();
        deal.setDealId(new Long("3"));
        deal.setDealName("Testc");
        deals.add(deal);

        return deals;
    }

    private Deal mockSingleDealData() {
        Deal deal = new Deal();
        deal.setDealId(new Long("1"));
        deal.setDealName("Testa");
        return deal;
    }

    private ArrayList<Stage> mockStageData() {
        ArrayList<Stage> stages = new ArrayList<>();

        Stage stage = new Stage();
        stage.setStageId(new Long("1"));
        stage.setDescriptor("stageA");
        stages.add(stage);

        stage = new Stage();
        stage.setStageId(new Long("2"));
        stage.setDescriptor("stageB");
        stages.add(stage);

        stage = new Stage();
        stage.setStageId(new Long("3"));
        stage.setDescriptor("stageC");
        stages.add(stage);

        return stages;
    }

    private ArrayList<Type> mockTypeData() {
        ArrayList<Type> types = new ArrayList<>();

        Type type = new Type();
        type.setTypeId(new Long("1"));
        type.setDescriptor("typeA");
        types.add(type);

        type = new Type();
        type.setTypeId(new Long("2"));
        type.setDescriptor("typeB");
        types.add(type);

        type = new Type();
        type.setTypeId(new Long("3"));
        type.setDescriptor("typeC");
        types.add(type);

        return types;
    }

    private ArrayList<Account> mockAccountData() {
        ArrayList<Account> accounts = new ArrayList<>();

        Account account = new Account();
        account.setAccountId(new Long("1"));
        account.setAccountName("accountA");
        accounts.add(account);

        account = new Account();
        account.setAccountId(new Long("2"));
        account.setAccountName("accountB");
        accounts.add(account);

        account = new Account();
        account.setAccountId(new Long("3"));
        account.setAccountName("accountC");
        accounts.add(account);
        return accounts;
    }

    private ArrayList<Contact> mockContactData() {
        ArrayList<Contact> contacts = new ArrayList<>();

        Contact contact = new Contact();
        contact.setContactId(new Long("1"));
        contact.setLastName("contactA");
        contacts.add(contact);

        contact = new Contact();
        contact.setContactId(new Long("2"));
        contact.setLastName("contactB");
        contacts.add(contact);

        contact = new Contact();
        contact.setContactId(new Long("3"));
        contact.setLastName("contactC");
        contacts.add(contact);
        return contacts;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(dealController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();

        super.mockUserRepository();
        Iterable<Deal> deals = mockDealListData();
        Iterable<Stage> stages = mockStageData();
        Iterable<Type> types = mockTypeData();
        Iterable<Account> accounts = mockAccountData();
        Iterable<Contact> contacts = mockContactData();

        given(this.dealRepository.findAll()).willReturn(deals);

        given(this.dealRepository.findOne(eq(new Long("1")))).willReturn(mockSingleDealData());
        given(this.dealRepository.findOne(eq(new Long("1")))).willReturn(null);

        given(this.dealRepository.existsByDealId(new Long("1"))).willReturn(true);
        given(this.dealRepository.existsByDealId(new Long("5"))).willReturn(false);

        given(this.dealRepository.save(any(Deal.class))).willReturn(mockSingleDealData());

        given(this.stageRepository.findAll()).willReturn(stages);
        given(this.typeRepository.findAll()).willReturn(types);
        given(this.accountRepository.findAll()).willReturn(accounts);
        given(this.contactRepository.findAll()).willReturn(contacts);
        System.out.println(contacts);
    }


    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/deal"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testIndexWithAnon() throws Exception {
        this.mockMvc.perform(get("/deal"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithExistentRecord() throws Exception {
        this.mockMvc.perform(get("/deal/records/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithNonExistentRecord() throws Exception {
        this.mockMvc.perform(get("/deal/records/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithExistentRecord() throws Exception {
        this.mockMvc.perform(get("/deal/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithNonExistentRecord() throws Exception {
        this.mockMvc.perform(get("/deal/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistentRecord() throws Exception {
        this.mockMvc.perform(post("/deal/edit/{id}", new Long("1"))
                .param("dealId", "1")
                .param("dealName", "companyname")


                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/deal/records/1"));
        verify(this.dealRepository, times(1)).save(any(Deal.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithNonExistentRecord() throws Exception {
        this.mockMvc.perform(post("/deal/edit/{id}", new Long("5"))
                .param("dealId", "5")
                .param("dealName", "companyname")


                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistentRecordInvalidData() throws Exception {
        this.mockMvc.perform(post("/deal/edit/{id}", new Long("1"))
                .param("dealId", "1")
                .param("dealName", "")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("dealData", "dealName"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testNewRecord() throws Exception {
        this.mockMvc.perform(get("/deal/new/")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithValidData() throws Exception {
        this.mockMvc.perform(post("/deal/new/")
                .param("dealName", "companyname")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(this.dealRepository, times(1)).save(any(Deal.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/deal/new/")
                .param("dealName", "")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("dealData", "dealName"))
                .andExpect(status().isOk());
    }
}
