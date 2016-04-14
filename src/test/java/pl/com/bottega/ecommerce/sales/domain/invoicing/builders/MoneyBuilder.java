package pl.com.bottega.ecommerce.sales.domain.invoicing.builders;

import java.math.BigDecimal;
import java.util.Currency;

import pl.com.bottega.ecommerce.sharedkernel.Money;

public class MoneyBuilder {

    private BigDecimal denomination = new BigDecimal(1000);

    private String currencyCode = "EUR";

    public MoneyBuilder() {
    }

    public MoneyBuilder withDenomination(BigDecimal denomination) {
        this.denomination = denomination;
        return this;
    }

    public MoneyBuilder withCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
        return this;
    }

    public Money build() {
        return new Money(denomination, Currency.getInstance(currencyCode));
    }
}
