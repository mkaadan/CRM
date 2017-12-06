package com.cylinder.leads.controllers;

import com.cylinder.ControllerTests;
import com.cylinder.leads.model.*;
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

public class LeadControllerTest extends ControllerTests {

    @InjectMocks
    LeadsController leadController;

    @MockBean
    private LeadRepository leadRepository;

    @MockBean
    private SourceRepository sourceRepository;

    @MockBean
    private StatusRepository statusRepository;

    private ArrayList<Lead> mockLeadListData() {
        ArrayList<Lead> leads = new ArrayList();
        Lead lead = new Lead();
        lead.setLeadId(new Long("1"));
        lead.setLastName("Testa");
        leads.add(lead);
        lead = new Lead();
        lead.setLeadId(new Long("2"));
        lead.setLastName("Testb");
        leads.add(lead);
        lead = new Lead();
        lead.setLeadId(new Long("3"));
        lead.setLastName("Testc");
        leads.add(lead);
        return leads;
    }

    private Lead mockSingleLeadData() {
        Lead lead = new Lead();
        lead.setLeadId(new Long("1"));
        lead.setLastName("Testa");
        return lead;
    }

    private ArrayList<Source> mockSourceData() {
        ArrayList<Source> sources = new ArrayList();
        Source source = new Source();
        source.setSourceId(new Long("1"));
        source.setDescriptor("Advertisment");
        sources.add(source);
        source = new Source();
        source.setSourceId(new Long("2"));
        source.setDescriptor("Cold call");
        sources.add(source);
        source = new Source();
        source.setSourceId(new Long("3"));
        source.setDescriptor("Cold call");
        sources.add(source);
        return sources;
    }

    private ArrayList<Status> mockStatusData() {
        ArrayList<Status> statuses = new ArrayList();
        Status status = new Status();
        status.setStatusId(new Long("1"));
        status.setDescriptor("Attempt To Contact");
        statuses.add(status);
        status = new Status();
        status.setStatusId(new Long("2"));
        status.setDescriptor("Contacted");
        statuses.add(status);
        return statuses;
    }


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(leadController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();
        super.mockUserRepository();
        Iterable<Lead> leads = mockLeadListData();
        Iterable<Source> sources = mockSourceData();
        Iterable<Status> statuses = mockStatusData();
        given(this.leadRepository.findAll()).willReturn(leads);
        given(this.leadRepository.findOne(eq(new Long("1")))).willReturn(mockSingleLeadData());
        given(this.leadRepository.findOne(eq(new Long("1")))).willReturn(null);
        given(this.leadRepository.existsById(new Long("1"))).willReturn(true);
        given(this.leadRepository.existsById(new Long("5"))).willReturn(false);
        given(this.leadRepository.save(any(Lead.class))).willReturn(mockSingleLeadData());
        given(this.sourceRepository.findAll()).willReturn(sources);
        given(this.statusRepository.findAll()).willReturn(statuses);
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/lead"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testIndexWithAnon() throws Exception {
        this.mockMvc.perform(get("/lead"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/lead/records/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/lead/records/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/lead/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testGetEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/lead/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(post("/lead/edit/{id}", new Long("1"))
                .param("leadId", "1")
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
                .andExpect(redirectedUrl("/lead/records/1"));
        verify(this.leadRepository, times(1)).save(any(Lead.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(post("/lead/edit/{id}", new Long("5"))
                .param("leadId", "5")
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
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostEditRecordWithExistantRecordInvalidData() throws Exception {
        this.mockMvc.perform(post("/lead/edit/{id}", new Long("1"))
                .param("leadId", "1")
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
                .andExpect(model().attributeHasFieldErrors("leadData", "lastName"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testNewRecord() throws Exception {
        this.mockMvc.perform(get("/lead/new/")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithValidData() throws Exception {
        this.mockMvc.perform(post("/lead/new/")
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
        verify(this.leadRepository, times(1)).save(any(Lead.class));
    }

    @Test
    @WithMockUser(username = "fake@mail.com", authorities = "USER")
    public void testPostNewRecordWithInvalidData() throws Exception {
        this.mockMvc.perform(post("/lead/new/")
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
                .andExpect(model().attributeHasFieldErrors("leadData", "lastName"))
                .andExpect(status().isOk());
    }
}
