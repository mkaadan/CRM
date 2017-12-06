package com.cylinder.deals.model;

import com.cylinder.RespositoryTests;
import com.cylinder.deals.model.Deal;
import com.cylinder.deals.model.DealRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class DealRepositoryTests extends RespositoryTests {

    @Autowired
    DealRepository dealRepository;

    @Before
    public void initData() {
        Deal deal = new Deal();
        deal.setDealName("testA");
        dealRepository.save(deal);

        deal = new Deal();
        deal.setDealName("testB");
        dealRepository.save(deal);

        deal = new Deal();
        deal.setDealName("testC");
        dealRepository.save(deal);
    }

    @Test
    public void testExistsBy() {
        Long id = new Long("1");
        boolean isExisting = dealRepository.existsByDealId(id);
        assertEquals(isExisting, true);

        id = new Long("4");
        isExisting = dealRepository.existsByDealId(id);
        assertEquals(isExisting, false);
    }

    @Test
    public void testDeleteById() {
        Long id = new Long("4");
        boolean isExisting = dealRepository.existsByDealId(id);
        assertEquals(isExisting, true);

        dealRepository.deleteByDealId(id);
        isExisting = dealRepository.existsByDealId(id);
        assertEquals(isExisting, false);
    }

}
