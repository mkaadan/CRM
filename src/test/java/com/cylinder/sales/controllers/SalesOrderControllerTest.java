package com.cylinder.sales.controllers;

import java.lang.Iterable;
import java.util.ArrayList;

import com.cylinder.sales.model.*;
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

//  @MockBean
//  private QuoteRepository quoteRepository;
//
//  @MockBean
//  private ProductSalesOrderRepository productSalesOrderRepository;

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

//    private ArrayList<Quote> mockQuoteData() {
//        ArrayList<Quote> quotes = new ArrayList();
//        Quote quote = new Quote();
//        quote.setQuoteId(new Long("1"));
//        quotes.add(quote);
//        quote = new Quote();
//        quote.setQuoteId(new Long("2"));
//        quotes.add(quote);
//        quote = new Quote();
//        quote.setQuoteId(new Long("3"));
//        quotes.add(quote);
//        return quotes;
//    }
//
//    private ArrayList<ProductSalesOrder> mockProductSalesOrderData() {
//        ArrayList<ProductSalesOrder> productSalesOrders = new ArrayList();
//        ProductSalesOrder productSalesOrder = new ProductSalesOrder();
//        productSalesOrder.setSalesOrderProductId(new Long("1"));
//        productSalesOrders.add(productSalesOrder);
//        productSalesOrder = new ProductSalesOrder();
//        productSalesOrder.setSalesOrderProductId(new Long("2"));
//        productSalesOrders.add(productSalesOrder);
//        productSalesOrder = new ProductSalesOrder();
//        productSalesOrder.setSalesOrderProductId(new Long("3"));
//        productSalesOrders.add(productSalesOrder);
//        return productSalesOrders;
//    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(salesOrderController)
                .apply(springSecurity(super.springSecurityFilterChain))
                .build();
        super.mockUserRepository();
        Iterable<SalesOrder> salesOrders = mockSalesOrderListData();
//        Iterable<Quote> quotes = mockQuoteData();
//        Iterable<ProductSalesOrder> productSalesOrders = mockProductSalesOrderData();
        given(this.salesOrderRepository.findAll()).willReturn(salesOrders);
        given(this.salesOrderRepository.findOne(eq(new Long("1")))).willReturn(mockSingleSalesOrderData());
        given(this.salesOrderRepository.findOne(eq(new Long("1")))).willReturn(null);
        given(this.salesOrderRepository.existsById(new Long("1"))).willReturn(true);
        given(this.salesOrderRepository.existsById(new Long("5"))).willReturn(false);
        given(this.salesOrderRepository.save(any(SalesOrder.class))).willReturn(mockSingleSalesOrderData());
//        given(this.quoteRepository.findAll()).willReturn(quotes);
//        given(this.productSalesOrderRepository.findAll()).willReturn(productSalesOrders);
    }

    @Test
    @WithMockUser(username="fake@mail.com", authorities="USER")
    public void testIndex() throws Exception {
        this.mockMvc.perform(get("/salesorder"))
                .andExpect(status().isOk());
    }
}
