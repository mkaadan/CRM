package com.cylinder.sales.model.forms;

import lombok.*;
import com.cylinder.sales.model.Quote;
import com.cylinder.sales.model.ProductQuote;

import java.util.List;
import java.util.ArrayList;

public class QuoteForm {
  @Getter
  @Setter
  private Quote quote;
  @Getter
  @Setter
  private List<ProductQuote> productList = new ArrayList<ProductQuote>();

  public QuoteForm() {}

  public QuoteForm(Quote quote, List<ProductQuote> productList) {
    this.quote = quote;
    this.productList = productList;
  }
}
