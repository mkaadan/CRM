package com.cylinder.accounts.model;

import com.cylinder.RespositoryTests;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class AccountRepositoryTests extends RespositoryTests {

    @Autowired
    AccountRepository accountRepository;

    @Before
    public void initData() {
        Account account = new Account();
        account.setAccountName("testA");
        account.setNumberEmployees(1);
        accountRepository.save(account);

        account = new Account();
        account.setAccountName("testB");
        account.setNumberEmployees(1);
        accountRepository.save(account);

        account = new Account();
        account.setAccountName("testC");
        account.setNumberEmployees(1);
        accountRepository.save(account);
    }

    @Test
    public void testExistsBy() {
        Long id = new Long("1");
        boolean isExisting = accountRepository.existsById(id);
        assertEquals(isExisting, true);

        id = new Long("4");
        isExisting = accountRepository.existsById(id);
        assertEquals(isExisting, false);
    }

    @Test
    public void testDeleteById() {
        Long id = new Long("4");
        boolean isExisting = accountRepository.existsById(id);
        assertEquals(isExisting, true);

        accountRepository.deleteById(id);
        isExisting = accountRepository.existsById(id);
        assertEquals(isExisting, false);
    }

}
