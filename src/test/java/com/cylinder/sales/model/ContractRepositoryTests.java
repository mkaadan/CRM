package com.cylinder.sales.model.ContractRepositoryTests;

import com.cylinder.RespositoryTests;
import com.cylinder.sales.model.Contract;
import com.cylinder.sales.model.ContractRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ContractRepositoryTests extends RespositoryTests {
    @Autowired
    ContractRepository contractRepository;

    @Before
    public void initData() {
        Contract contract = new Contract();
        contract.setContractTitle("ContractOne");
        contract.setContractText("This is the body. ");
        contractRepository.save(contract);
        contract = new Contract();
        contract.setContractTitle("ContractTwo");
        contractRepository.save(contract);
        contract.setContractTitle("ContractThree");
        contractRepository.save(contract);
    }

    @Test
    public void testExistsBy() {
        Long id = new Long("1");
        boolean isExisting = contractRepository.existsById(id);
        assertEquals(isExisting, true);
        id = new Long("4");
        isExisting = contractRepository.existsById(id);
        assertEquals(isExisting, false);
    }

    @Test
    public void testDeleteById() {
        Long id = new Long("4");
        boolean isExisting = contractRepository.existsById(id);
        assertEquals(isExisting, true);
        contractRepository.deleteById(id);
        isExisting = contractRepository.existsById(id);
        assertEquals(isExisting, false);
    }


}
