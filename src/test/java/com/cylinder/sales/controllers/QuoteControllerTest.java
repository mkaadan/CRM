package com.cylinder.sales.controllers;

import java.lang.Iterable;
import java.util.ArrayList;

import com.cylinder.accounts.model.Account;
import com.cylinder.accounts.model.AccountRepository;
import com.cylinder.contacts.model.Contact;
import com.cylinder.contacts.model.ContactRepository;
import com.cylinder.products.model.Product;
import com.cylinder.products.model.ProductRepository;
import com.cylinder.sales.model.*;
import com.cylinder.sales.model.forms.*;
import com.cylinder.sales.controllers.QuotesController;
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

public class QuoteControllerTest extends ControllerTests {

  @InjectMocks
  QuotesController quoteController;

  @MockBean
  private QuoteRepository quoteRepository;

  @MockBean
  private ProductRepository productRepository;

  @MockBean
  private ProductQuoteRepository productQuoteRepository;

  @MockBean
  private AccountRepository accountRepository;

  @MockBean
  private ContactRepository contactRepository;

  @MockBean
  private ArrayList<QuoteForm> quoteForms;

  @MockBean
  private Contact sampleContact;

  @MockBean
  private Account sampleAccount;


  private ArrayList<Quote> mockQuoteListData() {
    ArrayList<Quote> quotes = new ArrayList();
    Quote quote = new Quote();
    quote.setQuoteId(new Long("1"));
    quotes.add(quote);
    quote = new Quote();
    quote.setQuoteId(new Long("2"));
    quotes.add(quote);
    quote = new Quote();
    quote.setQuoteId(new Long("3"));
    quotes.add(quote);
    return quotes;
  }

  private Quote mockSingleQuoteData() {
    Quote quote = new Quote();
    quote.setQuoteId(new Long("1"));
    return quote;
  }

  private Contact mockSingleContactData() {
      Contact contact = new Contact();
      contact.setContactId(new Long("1"));
      return contact;
  }

  private Account mockSingleAccountData() {
      Account account = new Account();
      account.setAccountId(new Long("1"));
      return account;
  }

    private ArrayList<ProductQuote> mockProductQuoteData() {
        ArrayList<ProductQuote> productQuotes = new ArrayList();
        ProductQuote productQuote = new ProductQuote();
        productQuote.setQuoteProductId(new Long("1"));
        productQuotes.add(productQuote);
        productQuote = new ProductQuote();
        productQuote.setQuoteProductId(new Long("2"));
        productQuotes.add(productQuote);
        productQuote = new ProductQuote();
        productQuote.setQuoteProductId(new Long("3"));
        productQuotes.add(productQuote);
        return productQuotes;
    }

    private ArrayList<QuoteForm> mockQuoteFormListData(Iterable<Quote> quotes) {
        ArrayList<QuoteForm> quoteForms = new ArrayList();
        QuoteForm quoteForm;
        for(Quote quote: quotes) {
            if (quote != null) {
                quoteForm = new QuoteForm(quote,null);
                quoteForms.add(quoteForm);
            }
        }
        return quoteForms;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(quoteController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();
        super.mockUserRepository();
        Iterable<Quote> quotes = mockQuoteListData();
        Iterable<ProductQuote> productQuotes = mockProductQuoteData();
        quoteForms = mockQuoteFormListData(quotes);
        sampleContact = mockSingleContactData();
        sampleAccount = mockSingleAccountData();
        given(this.quoteRepository.findAll()).willReturn(quotes);
        given(this.quoteRepository.findOne(eq(new Long("1")))).willReturn(mockSingleQuoteData());
        given(this.quoteRepository.findOne(eq(new Long("1")))).willReturn(null);
        given(this.quoteRepository.existsById(new Long("1"))).willReturn(true);
        given(this.quoteRepository.existsById(new Long("5"))).willReturn(false);
        given(this.quoteRepository.save(any(Quote.class))).willReturn(mockSingleQuoteData());
        given(this.productQuoteRepository.findAll()).willReturn(productQuotes);
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/quote"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testIndexWithAnon() throws Exception {
        this.mockMvc.perform(get("/quote"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/quote/records/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/quote/records/5"))
                .andExpect(status().isNotFound());
    }


  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testGetEditRecordWithExistantRecord() throws Exception {
    this.mockMvc.perform(get("/quote/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testGetEditRecordWithNonExistantRecord() throws Exception {
    this.mockMvc.perform(get("/quote/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testPostEditRecordWithExistantRecord() throws Exception {
    this.mockMvc.perform(post("/quote/edit/{id}", new Long("1"))
                    .param("quote.quoteId","1")
                    .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/quote/records/1"));
    verify(this.quoteRepository, times(1)).save(any(Quote.class));
  }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testPostEditRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(post("/quote/edit/{id}", new Long("5"))
                    .param("quote.quoteId","5")
                    .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testPostEditRecordWithExistantRecordInvalidData() throws Exception {
      this.mockMvc.perform(post("/quote/edit/{id}", new Long("1"))
                    .param("quote.quoteId","1")
                    .param("quote.contact","sampleAccount")
                    .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("quoteData", "quote.contact"))
                .andExpect(status().isOk());
      }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testNewRecord() throws Exception {
        this.mockMvc.perform(get("/quote/new/")
                        .with(csrf()))
                .andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser(username="fake@mail.com", authorities="USER")
//    public void testPostNewRecordWithValidData() throws Exception {
//      this.mockMvc.perform(post("/quote/new/")
//                    .param("quote.quoteId","1")
//                    .with(csrf()))
//              .andExpect(status().is3xxRedirection());
//      verify(this.quoteRepository, times(1)).save(any(Quote.class));
//      }
//
//    @Test
//    @WithMockUser(username="fake@mail.com", authorities="USER")
//    public void testPostNewRecordWithInvalidData() throws Exception {
//      this.mockMvc.perform(post("/quote/new/")
//                    .param("quote.quoteId","1")
//                    .with(csrf()))
//              .andExpect(model().attributeHasFieldErrors("quoteData", "quote.invoiceNumber"))
//              .andExpect(status().isOk());
//      }
//
//    @Test
//    @WithMockUser(username="fake@mail.com", authorities="USER")
//    public void testAddRowToExisting() throws Exception{
//        this.mockMvc.perform(get("/quote/edit/{id}", new Long("1"))
//                    .param("addRow","addRow")
//                    .with(csrf()))
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockUser(username="fake@mail.com", authorities="USER")
//    public void testAddRowToNew() throws Exception{
//        this.mockMvc.perform(get("/quote/new/")
//                .param("addRow","addRow")
//                .with(csrf()))
//            .andExpect(status().isOk());
//    }
}
