package com.cylinder.leads.model;

import com.cylinder.RespositoryTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class LeadRepositoryTests extends RespositoryTests {

    @Autowired
    LeadRepository leadRepository;

    @Before
    public void initData() {
        Lead lead = new Lead();
        lead.setLastName("Saget");
        leadRepository.save(lead);
        lead = new Lead();
        lead.setLastName("Testy");
        leadRepository.save(lead);
        lead = new Lead();
        lead.setLastName("Testo");
        leadRepository.save(lead);
    }

    @Test
    public void testExistsBy() {
        Long id = new Long("1");
        boolean isExisting = leadRepository.existsById(id);
        assertEquals(isExisting, true);
        id = new Long("4");
        isExisting = leadRepository.existsById(id);
        assertEquals(isExisting, false);
    }

    @Test
    public void testDeleteById() {
        Long id = new Long("4");
        boolean isExisting = leadRepository.existsById(id);
        assertEquals(isExisting, true);
        leadRepository.deleteById(id);
        isExisting = leadRepository.existsById(id);
        assertEquals(isExisting, false);
    }

}
