package com.cylinder.sales.model;

import com.cylinder.RespositoryTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class QuoteRepositoryTests extends RespositoryTests {

    @Autowired
    QuoteRepository quoteRepository;

    @Before
    public void initData() {
        Quote quote = new Quote();
        quoteRepository.save(quote);
        quote = new Quote();
        quoteRepository.save(quote);
        quote = new Quote();
        quoteRepository.save(quote);
    }

    @Test
    public void testExistsBy() {
        Long id = new Long("1");
        boolean isExisting = quoteRepository.existsById(id);
        assertEquals(isExisting, true);
        id = new Long("4");
        isExisting = quoteRepository.existsById(id);
        assertEquals(isExisting, false);
    }

    @Test
    public void testDeleteById() {
        Long id = new Long("4");
        boolean isExisting = quoteRepository.existsById(id);
        assertEquals(isExisting, true);
        quoteRepository.deleteById(id);
        isExisting = quoteRepository.existsById(id);
        assertEquals(isExisting, false);
    }

}
