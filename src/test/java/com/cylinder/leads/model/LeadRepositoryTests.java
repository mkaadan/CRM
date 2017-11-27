package com.cylinder.leads.model.LeadRepositoryTests;

import com.cylinder.leads.model.LeadRepository;
import com.cylinder.leads.model.Lead;
import com.cylinder.RespositoryTests;

import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.*;

import java.util.List;

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
