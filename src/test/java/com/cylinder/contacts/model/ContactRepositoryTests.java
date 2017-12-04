package com.cylinder.contacts.model.ContactRepositoryTests;

        import com.cylinder.contacts.model.ContactRepository;
        import com.cylinder.contacts.model.Contact;
        import com.cylinder.RespositoryTests;

        import org.junit.*;
        import org.springframework.beans.factory.annotation.Autowired;
        import static org.junit.Assert.*;

        import java.util.List;

public class ContactRepositoryTests extends RespositoryTests {

    @Autowired
    ContactRepository contactRepository;

    @Before
    public void initData() {
        Contact contact = new Contact();
        contact.setLastName("Saget");
        contactRepository.save(contact);
        contact = new Contact();
        contact.setLastName("Testy");
        contactRepository.save(contact);
        contact = new Contact();
        contact.setLastName("Testo");
        contactRepository.save(contact);
    }

    @Test
    public void testExistsBy() {
        Long id = new Long("1");
        boolean isExisting = contactRepository.existsById(id);
        assertEquals(isExisting, true);
        id = new Long("4");
        isExisting = contactRepository.existsById(id);
        assertEquals(isExisting, false);
    }

    @Test
    public void testDeleteById() {
        Long id = new Long("4");
        boolean isExisting = contactRepository.existsById(id);
        assertEquals(isExisting, true);
        contactRepository.deleteById(id);
        isExisting = contactRepository.existsById(id);
        assertEquals(isExisting, false);
    }

}
