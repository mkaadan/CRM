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
import com.cylinder.sales.controllers.SalesOrderController;
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

public class SalesOrderControllerTest extends ControllerTests {

  @InjectMocks
  SalesOrderController salesOrderController;

  @MockBean
  private SalesOrderRepository salesOrderRepository;

  @MockBean
  private ProductRepository productRepository;

    @MockBean
  private QuoteRepository quoteRepository;

  @MockBean
  private ProductSalesOrderRepository productSalesOrderRepository;

  @MockBean
  private AccountRepository accountRepository;

  @MockBean
  private ContactRepository contactRepository;

  @MockBean
  private ContractRepository contractRepository;

  @MockBean
  private ArrayList<SalesOrderForm> salesOrderForms;



  private ArrayList<SalesOrder> mockSalesOrderListData() {
    ArrayList<SalesOrder> salesOrders = new ArrayList();
    SalesOrder salesOrder = new SalesOrder();
    salesOrder.setSalesOrderId(new Long("1"));
    salesOrders.add(salesOrder);
    salesOrder = new SalesOrder();
    salesOrder.setSalesOrderId(new Long("2"));
    salesOrders.add(salesOrder);
    salesOrder = new SalesOrder();
    salesOrder.setSalesOrderId(new Long("3"));
    salesOrders.add(salesOrder);
    return salesOrders;
  }

  private SalesOrder mockSingleSalesOrderData() {
    SalesOrder salesOrder = new SalesOrder();
    salesOrder.setSalesOrderId(new Long("1"));
    return salesOrder;
  }

    private ArrayList<Quote> mockQuoteData() {
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

    private ArrayList<ProductSalesOrder> mockProductSalesOrderData() {
        ArrayList<ProductSalesOrder> productSalesOrders = new ArrayList();
        ProductSalesOrder productSalesOrder = new ProductSalesOrder();
        productSalesOrder.setSalesOrderProductId(new Long("1"));
        productSalesOrders.add(productSalesOrder);
        productSalesOrder = new ProductSalesOrder();
        productSalesOrder.setSalesOrderProductId(new Long("2"));
        productSalesOrders.add(productSalesOrder);
        productSalesOrder = new ProductSalesOrder();
        productSalesOrder.setSalesOrderProductId(new Long("3"));
        productSalesOrders.add(productSalesOrder);
        return productSalesOrders;
    }

    private ArrayList<SalesOrderForm> mockSalesOrderFormListData(Iterable<SalesOrder> salesOrders) {
        ArrayList<SalesOrderForm> salesOrderForms = new ArrayList();
        SalesOrderForm salesOrderForm;
//        SalesOrder salesOrder = new SalesOrder();
//        salesOrder.setSalesOrderId(new Long("1"));
//        SalesOrderForm salesOrderForm = new SalesOrderForm(salesOrder,null);
//        salesOrderForms.add(salesOrderForm);
//        salesOrder = new SalesOrder();
//        salesOrder.setSalesOrderId(new Long("2"));
//        salesOrderForm = new SalesOrderForm(salesOrder,null);
//        salesOrderForms.add(salesOrderForm);
//        salesOrder = new SalesOrder();
//        salesOrder.setSalesOrderId(new Long("3"));
//        salesOrderForm = new SalesOrderForm(salesOrder,null);
//        salesOrderForms.add(salesOrderForm);
        for(SalesOrder salesOrder: salesOrders) {
            if (salesOrder != null) {
                salesOrderForm = new SalesOrderForm(salesOrder,null);
                salesOrderForms.add(salesOrderForm);
            }
        }
        return salesOrderForms;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(salesOrderController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();
        super.mockUserRepository();
        Iterable<SalesOrder> salesOrders = mockSalesOrderListData();
        Iterable<Quote> quotes = mockQuoteData();
        Iterable<ProductSalesOrder> productSalesOrders = mockProductSalesOrderData();
        salesOrderForms = mockSalesOrderFormListData(salesOrders);
        given(this.salesOrderRepository.findAll()).willReturn(salesOrders);
        given(this.salesOrderRepository.findOne(eq(new Long("1")))).willReturn(mockSingleSalesOrderData());
        given(this.salesOrderRepository.findOne(eq(new Long("1")))).willReturn(null);
        given(this.salesOrderRepository.existsById(new Long("1"))).willReturn(true);
        given(this.salesOrderRepository.existsById(new Long("5"))).willReturn(false);
        given(this.salesOrderRepository.save(any(SalesOrder.class))).willReturn(mockSingleSalesOrderData());
        given(this.quoteRepository.findAll()).willReturn(quotes);
        given(this.productSalesOrderRepository.findAll()).willReturn(productSalesOrders);
//        given(this.salesOrderFormRepository.findAll()).willReturn(salesOrderForms);
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/salesorder"))
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    public void testIndexWithAnon() throws Exception {
        this.mockMvc.perform(get("/salesorder"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testRecordWithExistantRecord() throws Exception {
        this.mockMvc.perform(get("/salesorder/records/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testRecordWithNonExistantRecord() throws Exception {
        this.mockMvc.perform(get("/salesorder/records/5"))
                .andExpect(status().isNotFound());
    }


  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testGetEditRecordWithExistantRecord() throws Exception {
    this.mockMvc.perform(get("/salesorder/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testGetEditRecordWithNonExistantRecord() throws Exception {
    this.mockMvc.perform(get("/salesorder/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testPostEditRecordWithExistantRecord() throws Exception {
    this.mockMvc.perform(post("/salesorder/edit/{id}", new Long("1"))
                        .param("salesOrder.salesOrderId","1")
                        .param("salesOrder.shippingAddress.apartmentNumber", "null")
                        .param("salesOrder.shippingAddress.city", "null")
                        .param("salesOrder.shippingAddress.streetAddress", "null")
                        .param("salesOrder.shippingAddress.stateProv", "null")
                        .param("salesOrder.shippingAddress.country", "null")
                        .param("salesOrder.shippingAddress.zipPostal", "null")
                        .param("salesOrder.shippingAddress.poBox", "null")
                        .param("salesOrder.billingAddress.apartmentNumber", "null")
                        .param("salesOrder.billingAddress.city", "null")
                        .param("salesOrder.billingAddress.streetAddress", "null")
                        .param("salesOrder.billingAddress.stateProv", "null")
                        .param("salesOrder.billingAddress.country", "null")
                        .param("salesOrder.billingAddress.zipPostal", "null")
                        .param("salesOrder.billingAddress.poBox", "null")
                        .param("salesOrder.contact", "null")
                        .param("salesOrder.account", "null")
                        .param("salesOrder.quote", "null")
                        .param("salesOrder.taxPercent", "null")
                        .param("salesOrder.invoiceNumber", "null")
                        .param("salesOrder.contract", "null")
                        .param("salesOrder.owner", "null")
                        .param("salesOrder.productSalesOrder", "null")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/salesorder/records/1"));
    verify(this.salesOrderRepository, times(1)).save(any(SalesOrder.class));
  }
}
