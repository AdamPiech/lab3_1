package pl.com.bottega.ecommerce.sales.domain.invoicing.builders;

import java.util.Date;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductDataBuilder {

    private Id id = Id.generate();
    private Money money = new MoneyBuilder().build();
    private String name = "Product";
    private Date date = new Date();
    private ProductType productType = ProductType.STANDARD;

    public ProductDataBuilder() {
    }

    public ProductDataBuilder withProductId(Id id) {
        this.id = id;
        return this;
    }

    public ProductDataBuilder withPrice(Money money) {
        this.money = money;
        return this;
    }

    public ProductDataBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductDataBuilder withSnapshotDate(Date date) {
        this.date = date;
        return this;
    }

    public ProductDataBuilder withType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public ProductData build() {
        return new ProductData(id, money, name, productType, date);
    }
}
