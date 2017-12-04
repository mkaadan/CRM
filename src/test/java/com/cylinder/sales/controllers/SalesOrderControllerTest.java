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
}
