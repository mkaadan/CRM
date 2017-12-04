package com.cylinder.contacts.controllers;

import java.lang.Iterable;
import java.util.ArrayList;

import com.cylinder.contacts.model.*;
import com.cylinder.contacts.controller.ContactsController;
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

public class ContactControllerTest extends ControllerTests {

    @InjectMocks
    ContactsController contactsController;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private EmailRepository emailRepository;

    private ArrayList<Contact> mockContactListData() {
        ArrayList<Contact> contacts = new ArrayList();
        Contact contact = new Contact();
        contact.setContactId(new Long("1"));
        contact.setLastName("Testa");
        contacts.add(contact);
        contact = new Contact();
        contact.setLeadId(new Long("2"));
        contact.setLastName("Testb");
        contacts.add(contact);
        contact = new Contact();
        contact.setLeadId(new Long("3"));
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


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contactsController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();
        super.mockUserRepository();
        Iterable<Contact> leads = mockContactListData();
        given(this.contactRepository.findAll()).willReturn(leads);
        given(this.contactRepository.findOne(eq(new Long("1")))).willReturn(mockSingleContactData());
        given(this.contactRepository.findOne(eq(new Long("1")))).willReturn(null);
        given(this.contactRepository.existsById(new Long("1"))).willReturn(true);
        given(this.contactRepository.existsById(new Long("5"))).willReturn(false);
        given(this.contactRepository.save(any(Contact.class))).willReturn(mockSingleContactData());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
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
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/records/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/records/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testGetEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testGetEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/contact/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testPostEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(post("/contact/edit/{id}", new Long("1"))
                .param("contactId","1")
                .param("lastName", "power")
                .param("firstName", "austin")
                .param("address.apartmentNumber", "null")
                .param("address.city", "null")
                .param("address.streetAddress", "null")
                .param("address.stateProv", "null")
                .param("address.country", "null")
                .param("address.zipPostal", "null")
                .param("address.poBox", "null")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contact/records/1"));
        verify(this.contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testPostEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(post("/contact/edit/{id}", new Long("5"))
                .param("contactId","5")
                .param("lastName", "power")
                .param("firstName", "austin")
                .param("address.apartmentNumber", "null")
                .param("address.city", "null")
                .param("address.streetAddress", "null")
                .param("address.stateProv", "null")
                .param("address.country", "null")
                .param("address.zipPostal", "null")
                .param("address.poBox", "null")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testPostEditRecordWithExistantRecordInvalidData() throws Exception {
        this.mockMvc.perform(post("/contact/edit/{id}", new Long("1"))
                .param("contactId","1")
                .param("lastName", "power2")
                .param("firstName", "austin")
                .param("address.apartmentNumber", "null")
                .param("address.city", "null")
                .param("address.streetAddress", "null")
                .param("address.stateProv", "null")
                .param("address.country", "null")
                .param("address.zipPostal", "null")
                .param("address.poBox", "null")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("contactData", "lastName"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testNewRecord() throws Exception {
        this.mockMvc.perform(get("/contact/new/")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testPostNewRecordWithValidData() throws Exception {
        this.mockMvc.perform(post("/contact/new/")
                .param("lastName", "power")
                .param("firstName", "austin")
                .param("address.apartmentNumber", "null")
                .param("address.city", "null")
                .param("address.streetAddress", "null")
                .param("address.stateProv", "null")
                .param("address.country", "null")
                .param("address.zipPostal", "null")
                .param("address.poBox", "null")
                .with(csrf()))
                .andExpect(status().is3xxRedirection());
        verify(this.contactRepository, times(1)).save(any(Contact.class));
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testPostNewRecordWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/contact/new/")
                .param("lastName", "power2")
                .param("firstName", "austin")
                .param("address.apartmentNumber", "null")
                .param("address.city", "null")
                .param("address.streetAddress", "null")
                .param("address.stateProv", "null")
                .param("address.country", "null")
                .param("address.zipPostal", "null")
                .param("address.poBox", "null")
                .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("contactData", "lastName"))
                .andExpect(status().isOk());
    }
}
