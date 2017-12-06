package com.cylinder.contacts.controllers;

import com.cylinder.ControllerTests;
import com.cylinder.accounts.model.Account;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.contacts.controller.ContactsController;
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

public class ContactControllerTest extends ControllerTests {

    @InjectMocks
    private ContactsController contactsController;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private AccountRepository accountRepository;

    private ArrayList<Contact> mockContactListData() {
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

    private Contact mockSingleContactData() {
        Contact contact = new Contact();
        contact.setContactId(new Long("1"));
        contact.setLastName("Testa");
        return contact;
    }

    private ArrayList<Account> mockAccountData() {
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

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contactsController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();
        super.mockUserRepository();
        Iterable<Contact> contacts = mockContactListData();
        Iterable<Account> accounts = mockAccountData();
        given(this.contactRepository.findAll()).willReturn(contacts);
        given(this.contactRepository.findOne(eq(new Long("1")))).willReturn(mockSingleContactData());
        given(this.contactRepository.findOne(eq(new Long("1")))).willReturn(null);
        given(this.contactRepository.existsByContactId(new Long("1"))).willReturn(true);
        given(this.contactRepository.existsByContactId(new Long("5"))).willReturn(false);
        given(this.contactRepository.save(any(Contact.class))).willReturn(mockSingleContactData());
        given(this.accountRepository.findAll()).willReturn(accounts);
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/contact"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testIndexWithAnon() throws Exception {
        this.mockMvc.perform(get("/contact"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/records/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/records/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(post("/contact/edit/{id}", new Long("1"))
                .param("contactId", "1")
                .param("lastName", "power")
                .param("firstName", "austin")
                .param("mailingAddress.apartmentNumber", "null")
                .param("mailingAddress.city", "null")
                .param("mailingAddress.streetAddress", "null")
                .param("mailingAddress.stateProv", "null")
                .param("mailingAddress.country", "null")
                .param("mailingAddress.zipPostal", "null")
                .param("mailingAddress.poBox", "null")
                .param("otherAddress.apartmentNumber", "null")
                .param("otherAddress.city", "null")
                .param("otherAddress.streetAddress", "null")
                .param("otherAddress.stateProv", "null")
                .param("otherAddress.country", "null")
                .param("otherAddress.zipPostal", "null")
                .param("otherAddress.poBox", "null")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact/records/1"));
        verify(this.contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(post("/contact/edit/{id}", new Long("5"))
                .param("contactId", "5")
                .param("lastName", "power")
                .param("firstName", "austin")
                .param("mailingAddress.apartmentNumber", "null")
                .param("mailingAddress.city", "null")
                .param("mailingAddress.streetAddress", "null")
                .param("mailingAddress.stateProv", "null")
                .param("mailingAddress.country", "null")
                .param("mailingAddress.zipPostal", "null")
                .param("mailingAddress.poBox", "null")
                .param("otherAddress.apartmentNumber", "null")
                .param("otherAddress.city", "null")
                .param("otherAddress.streetAddress", "null")
                .param("otherAddress.stateProv", "null")
                .param("otherAddress.country", "null")
                .param("otherAddress.zipPostal", "null")
                .param("otherAddress.poBox", "null")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistantRecordInvalidData() throws Exception {
        this.mockMvc.perform(post("/contact/edit/{id}", new Long("1"))
                .param("contactId", "1")
                .param("lastName", "power2")
                .param("firstName", "austin")
                .param("mailingAddress.apartmentNumber", "null")
                .param("mailingAddress.city", "null")
                .param("mailingAddress.streetAddress", "null")
                .param("mailingAddress.stateProv", "null")
                .param("mailingAddress.country", "null")
                .param("mailingAddress.zipPostal", "null")
                .param("mailingAddress.poBox", "null")
                .param("otherAddress.apartmentNumber", "null")
                .param("otherAddress.city", "null")
                .param("otherAddress.streetAddress", "null")
                .param("otherAddress.stateProv", "null")
                .param("otherAddress.country", "null")
                .param("otherAddress.zipPostal", "null")
                .param("otherAddress.poBox", "null")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("contactData", "lastName"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testNewRecord() throws Exception {
        this.mockMvc.perform(get("/contact/new/")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithValidData() throws Exception {
        this.mockMvc.perform(post("/contact/new/")
                .param("lastName", "power")
                .param("firstName", "austin")
                .param("mailingAddress.apartmentNumber", "null")
                .param("mailingAddress.city", "null")
                .param("mailingAddress.streetAddress", "null")
                .param("mailingAddress.stateProv", "null")
                .param("mailingAddress.country", "null")
                .param("mailingAddress.zipPostal", "null")
                .param("mailingAddress.poBox", "null")
                .param("otherAddress.apartmentNumber", "null")
                .param("otherAddress.city", "null")
                .param("otherAddress.streetAddress", "null")
                .param("otherAddress.stateProv", "null")
                .param("otherAddress.country", "null")
                .param("otherAddress.zipPostal", "null")
                .param("otherAddress.poBox", "null")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(this.contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/contact/new/")
                .param("lastName", "power2")
                .param("firstName", "austin")
                .param("mailingAddress.apartmentNumber", "null")
                .param("mailingAddress.city", "null")
                .param("mailingAddress.streetAddress", "null")
                .param("mailingAddress.stateProv", "null")
                .param("mailingAddress.country", "null")
                .param("mailingAddress.zipPostal", "null")
                .param("mailingAddress.poBox", "null")
                .param("otherAddress.apartmentNumber", "null")
                .param("otherAddress.city", "null")
                .param("otherAddress.streetAddress", "null")
                .param("otherAddress.stateProv", "null")
                .param("otherAddress.country", "null")
                .param("otherAddress.zipPostal", "null")
                .param("otherAddress.poBox", "null")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("contactData", "lastName"))
                .andExpect(status().isOk());
    }
}
