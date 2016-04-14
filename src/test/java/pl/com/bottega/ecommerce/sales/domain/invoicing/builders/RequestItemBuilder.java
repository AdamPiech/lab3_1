package pl.com.bottega.ecommerce.sales.domain.invoicing.builders;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.invoicing.RequestItem;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class RequestItemBuilder {
	
    private ProductData productData = new ProductData(Id.generate(),
            new Money(new BigDecimal("1000"), "EUR"), "Product", ProductType.STANDARD, new Date());
    private int quantity = 1;
    private Money money = new Money(new BigDecimal("1000"), "EUR");

    public RequestItemBuilder() {
    }

    public RequestItemBuilder withProductData(ProductData productData) {
        this.productData = productData;
        return this;
    }

    public RequestItemBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public RequestItemBuilder withTotalCost(Money money) {
        this.money = money;
        return this;
    }

    public RequestItem build() {
        return new RequestItem(productData, quantity, money);
    }
}
