package com.cylinder.accounts.controllers;

import com.cylinder.ControllerTests;
import com.cylinder.accounts.model.Account;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.accounts.model.AccountTypeRepository;
import com.cylinder.accounts.model.Type;
import com.cylinder.contacts.model.Contact;
import com.cylinder.contacts.model.ContactRepository;
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

public class AccountControllerTest extends ControllerTests {

    @InjectMocks
    private AccountsController accountController;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private AccountTypeRepository typeRepository;

    @MockBean
    private ContactRepository contactRepository;

    private ArrayList<Account> mockAccountListData() {
        ArrayList<Account> accounts = new ArrayList<>();

        Account account = new Account();
        account.setAccountId(new Long("1"));
        account.setAccountName("Testa");
        accounts.add(account);

        account = new Account();
        account.setAccountId(new Long("2"));
        account.setAccountName("Testb");
        accounts.add(account);

        account = new Account();
        account.setAccountId(new Long("3"));
        account.setAccountName("Testc");
        accounts.add(account);

        return accounts;
    }

    private Account mockSingleAccountData() {
        Account account = new Account();
        account.setAccountId(new Long("1"));
        account.setAccountName("Testa");
        return account;
    }

    private ArrayList<Type> mockTypeData() {
        ArrayList<Type> types = new ArrayList<>();

        Type type = new Type();
        type.setTypeId(new Long("1"));
        type.setDescriptor("Customer");
        types.add(type);

        type = new Type();
        type.setTypeId(new Long("2"));
        type.setDescriptor("Investor");
        types.add(type);

        type = new Type();
        type.setTypeId(new Long("3"));
        type.setDescriptor("Partner");
        types.add(type);

        return types;
    }

    private ArrayList<Contact> mockContactData() {
        ArrayList<Contact> contacts = new ArrayList<>();
        Contact contact = new Contact();
        contact.setContactId(new Long("1"));
        contact.setLastName("Testa");
        contacts.add(contact);
        contact = new Contact();
        contact.setContactId(new Long("2"));
        contact.setLastName("Testb");
        contacts.add(contact);
        contact = new Contact();
        contact.setContactId(new Long("3"));
        contact.setLastName("Testc");
        contacts.add(contact);
        return contacts;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(accountController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();

        super.mockUserRepository();
        Iterable<Account> accounts = mockAccountListData();
        Iterable<Type> types = mockTypeData();
        Iterable<Contact> contacts = mockContactData();

        given(this.accountRepository.findAll()).willReturn(accounts);

        given(this.accountRepository.findOne(eq(new Long("1")))).willReturn(mockSingleAccountData());
        given(this.accountRepository.findOne(eq(new Long("1")))).willReturn(null);

        given(this.accountRepository.existsById(new Long("1"))).willReturn(true);
        given(this.accountRepository.existsById(new Long("5"))).willReturn(false);

        given(this.accountRepository.save(any(Account.class))).willReturn(mockSingleAccountData());

        given(this.typeRepository.findAll()).willReturn(types);
        given(this.contactRepository.findAll()).willReturn(contacts);
        System.out.println(contacts);
    }


    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/account"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testIndexWithAnon() throws Exception {
        this.mockMvc.perform(get("/account"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithExistentRecord() throws Exception {
        this.mockMvc.perform(get("/account/records/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithNonExistentRecord() throws Exception {
        this.mockMvc.perform(get("/account/records/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithExistentRecord() throws Exception {
        this.mockMvc.perform(get("/account/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithNonExistentRecord() throws Exception {
        this.mockMvc.perform(get("/account/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistentRecord() throws Exception {
        this.mockMvc.perform(post("/account/edit/{id}", new Long("1"))
                .param("accountId", "1")
                .param("accountName", "companyname")
                .param("numberEmployees", "100")
                .param("shippingAddress.apartmentNumber", "null")
                .param("shippingAddress.city", "null")
                .param("shippingAddress.streetAddress", "null")
                .param("shippingAddress.stateProv", "null")
                .param("shippingAddress.country", "null")
                .param("shippingAddress.zipPostal", "null")
                .param("shippingAddress.poBox", "null")
                .param("billingAddress.apartmentNumber", "null")
                .param("billingAddress.city", "null")
                .param("billingAddress.streetAddress", "null")
                .param("billingAddress.stateProv", "null")
                .param("billingAddress.country", "null")
                .param("billingAddress.zipPostal", "null")
                .param("billingAddress.poBox", "null")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/account/records/1"));
        verify(this.accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithNonExistentRecord() throws Exception {
        this.mockMvc.perform(post("/account/edit/{id}", new Long("5"))
                .param("accountId", "5")
                .param("accountName", "companyname")
                .param("numberEmployees", "100")
                .param("shippingAddress.apartmentNumber", "null")
                .param("shippingAddress.city", "null")
                .param("shippingAddress.streetAddress", "null")
                .param("shippingAddress.stateProv", "null")
                .param("shippingAddress.country", "null")
                .param("shippingAddress.zipPostal", "null")
                .param("shippingAddress.poBox", "null")
                .param("billingAddress.apartmentNumber", "null")
                .param("billingAddress.city", "null")
                .param("billingAddress.streetAddress", "null")
                .param("billingAddress.stateProv", "null")
                .param("billingAddress.country", "null")
                .param("billingAddress.zipPostal", "null")
                .param("billingAddress.poBox", "null")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistentRecordInvalidData() throws Exception {
        this.mockMvc.perform(post("/account/edit/{id}", new Long("1"))
                .param("accountId", "1")
                .param("accountName", "company100")
                .param("numberEmployees", "100")
                .param("shippingAddress.apartmentNumber", "null")
                .param("shippingAddress.city", "null")
                .param("shippingAddress.streetAddress", "null")
                .param("shippingAddress.stateProv", "null")
                .param("shippingAddress.country", "null")
                .param("shippingAddress.zipPostal", "null")
                .param("shippingAddress.poBox", "null")
                .param("billingAddress.apartmentNumber", "null")
                .param("billingAddress.city", "null")
                .param("billingAddress.streetAddress", "null")
                .param("billingAddress.stateProv", "null")
                .param("billingAddress.country", "null")
                .param("billingAddress.zipPostal", "null")
                .param("billingAddress.poBox", "null")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("accountData", "accountName"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testNewRecord() throws Exception {
        this.mockMvc.perform(get("/account/new/")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithValidData() throws Exception {
        this.mockMvc.perform(post("/account/new/")
                .param("accountName", "companyname")
                .param("numberEmployees", "100")
                .param("shippingAddress.apartmentNumber", "null")
                .param("shippingAddress.city", "null")
                .param("shippingAddress.streetAddress", "null")
                .param("shippingAddress.stateProv", "null")
                .param("shippingAddress.country", "null")
                .param("shippingAddress.zipPostal", "null")
                .param("shippingAddress.poBox", "null")
                .param("billingAddress.apartmentNumber", "null")
                .param("billingAddress.city", "null")
                .param("billingAddress.streetAddress", "null")
                .param("billingAddress.stateProv", "null")
                .param("billingAddress.country", "null")
                .param("billingAddress.zipPostal", "null")
                .param("billingAddress.poBox", "null")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(this.accountRepository, times(1)).save(any(Account.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/account/new/")
                .param("accountName", "company100")
                .param("numberEmployees", "100")
                .param("shippingAddress.apartmentNumber", "null")
                .param("shippingAddress.city", "null")
                .param("shippingAddress.streetAddress", "null")
                .param("shippingAddress.stateProv", "null")
                .param("shippingAddress.country", "null")
                .param("shippingAddress.zipPostal", "null")
                .param("shippingAddress.poBox", "null")
                .param("billingAddress.apartmentNumber", "null")
                .param("billingAddress.city", "null")
                .param("billingAddress.streetAddress", "null")
                .param("billingAddress.stateProv", "null")
                .param("billingAddress.country", "null")
                .param("billingAddress.zipPostal", "null")
                .param("billingAddress.poBox", "null")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("accountData", "accountName"))
                .andExpect(status().isOk());
    }
}
