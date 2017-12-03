package com.cylinder.sales.model.forms;

import com.cylinder.sales.model.ProductQuote;
import com.cylinder.sales.model.Quote;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

public class QuoteForm {

    /* Quote the form is for */
    @Getter
    @Setter
    @Valid
    private Quote quote;

    /* list of all the products associated with the quote*/
    @Getter
    @Setter
    @Valid
    private List<ProductQuote> productList = new ArrayList<ProductQuote>();

    public QuoteForm() {
    }

    public QuoteForm(Quote quote, List<ProductQuote> productList) {
        this.quote = quote;
        this.productList = productList;
    }
}
