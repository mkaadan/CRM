package com.cylinder.products.controllers;

import java.lang.Iterable;
import java.util.ArrayList;

import com.cylinder.products.model.*;
import com.cylinder.products.controllers.ProductsController;
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


public class ProductControllerTest extends ControllerTests {

  @InjectMocks
  ProductsController productController;

  @MockBean
  private ProductRepository productRepository;

  @MockBean
  private CategoriesRepository categoriesRepository ;


  private ArrayList<Product> mockProductListData() {
    ArrayList<Product> products = new ArrayList();
    Product product = new Product();
    product.setProductId(new Long("1"));
    product.setName("Box");
    products.add(product);
    product = new Product();
    product.setProductId(new Long("2"));
    product.setName("Car");
    products.add(product);
    product = new Product();
    product.setProductId(new Long("3"));
    product.setName("Bag");
    products.add(product);
    return products;
  }

  private Product mockSingleProductData(){
    Product product = new Product() ;
    product.setProductId(new Long("1"));
    product.setName("Box");
    return product;
  }

  private ArrayList<Categories> mockCategoriesData(){
    ArrayList<Categories> categories = new ArrayList();
    Categories category = new Categories();
    category.setCategoryId(new Long("1"));
    category.setDescriptor("This is a box");
    category = new Categories();
    category.setCategoryId(new Long("2"));
    category.setDescriptor("This is a car");
    category = new Categories();
    category.setCategoryId(new Long("2"));
    category.setDescriptor("This is a bag");
    return categories ;
  }

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    mockMvc = MockMvcBuilders.standaloneSetup(productController)
                             .apply(springSecurity(super.springSecurityFilterChain))
                             .build();
    super.mockUserRepository();
    Iterable<Product> products = mockProductListData();
    Iterable<Categories> categories = mockCategoriesData();
    given(this.productRepository.findAll()).willReturn(products);
    given(this.productRepository.findOne(eq(new Long("1")))).willReturn(mockSingleProductData());
    given(this.productRepository.existsByProductId(new Long("1"))).willReturn(true);
    given(this.productRepository.existsByProductId(new Long("5"))).willReturn(false);
    given(this.productRepository.save(any(Product.class))).willReturn(mockSingleProductData());
    given(this.categoriesRepository.findAll()).willReturn(categories);
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testIndex() throws Exception {
    this.mockMvc.perform(get("/product"))
                .andExpect(status().isOk());
  }

  @Test
  @WithAnonymousUser
  public void testIndexWithAnon() throws Exception {
    this.mockMvc.perform(get("/product"))
                .andExpect(status().isFound());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testRecordWithExistantRecord() throws Exception {
    this.mockMvc.perform(get("/product/records/1"))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testRecordWithNonExistantRecord() throws Exception {
    this.mockMvc.perform(get("/product/records/5"))
                .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testGetEditRecordWithExistantRecord() throws Exception {
    this.mockMvc.perform(get("/product/edit/{id}", new Long("1")).with(csrf()))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testGetEditRecordWithNonExistantRecord() throws Exception {
    this.mockMvc.perform(get("/product/edit/{id}", new Long("5")).with(csrf()))
                .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testPostEditRecordWithExistantRecord() throws Exception {
    this.mockMvc.perform(post("/product/edit/{id}", new Long("1"))
                        .param("productId","1")
                        .param("name", "Box")
                        .param("code", "123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/records/1"));
    verify(this.productRepository, times(1)).save(any(Product.class));
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testPostEditRecordWithNonExistantRecord() throws Exception {
    this.mockMvc.perform(post("/product/edit/{id}", new Long("5"))
                        .param("productId","5")
                        .param("name", "Box")
                        .param("code", "123")
                        .with(csrf()))
                .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testPostEditRecordWithExistantRecordInvalidData() throws Exception {
    this.mockMvc.perform(post("/product/edit/{id}", new Long("1"))
                        .param("productId","5")
                        .param("name", "Box")
                        .param("code", "1!23")
                        .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("productData", "code"))
                .andExpect(status().isOk());
  }
  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testNewRecord() throws Exception {
    this.mockMvc.perform(get("/product/new/")
                        .with(csrf()))
                .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testPostNewRecordWithValidData() throws Exception {
    this.mockMvc.perform(post("/product/new/")
                        .param("name", "Box")
                        .param("code", "123")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection());
    verify(this.productRepository, times(1)).save(any(Product.class));
  }

  @Test
  @WithMockUser(username="fake@mail.com", authorities="USER")
  public void testPostNewRecordWithInvalidData() throws Exception {
    this.mockMvc.perform(post("/product/new/")
                        .param("name", "Box")
                        .param("code", "1!23")
                        .with(csrf()))
                .andExpect(model().attributeHasFieldErrors("productData", "code"))
                .andExpect(status().isOk());
  }


}
