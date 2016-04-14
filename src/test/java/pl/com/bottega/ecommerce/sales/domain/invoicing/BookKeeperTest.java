package pl.com.bottega.ecommerce.sales.domain.invoicing;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.invoicing.builders.BookKeeperBuilder;
import pl.com.bottega.ecommerce.sales.domain.invoicing.builders.InvoiceRequestBuilder;
import pl.com.bottega.ecommerce.sales.domain.invoicing.builders.MoneyBuilder;
import pl.com.bottega.ecommerce.sales.domain.invoicing.builders.ProductDataBuilder;
import pl.com.bottega.ecommerce.sales.domain.invoicing.builders.RequestItemBuilder;
import pl.com.bottega.ecommerce.sales.domain.invoicing.builders.TaxBuilder;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

	private BookKeeper bookKeeper;
	private TaxPolicy taxPolicy;
	private InvoiceRequest invoiceRequest;

	@Before
	public void start() {
		taxPolicy = Mockito.mock(TaxPolicy.class);
		bookKeeper = new BookKeeperBuilder().build();
		invoiceRequest = new InvoiceRequestBuilder().build();
		
		Mockito.when(taxPolicy.calculateTax(Mockito.any(ProductType.class), Mockito.any(Money.class))).thenReturn(
				new TaxBuilder().build());
	}

	@Test
	public void firstTestCase() {
		ProductData productData = new ProductDataBuilder().build();
		RequestItem item = new RequestItemBuilder().withProductData(productData).build();
		invoiceRequest.add(item);

		Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		assertThat(invoice.getItems().size(), Matchers.is(1));
		assertThat(invoice.getItems().get(0).getProduct(), Matchers.is(productData));
	}
	
	@Test
	public void secondTestCase() {
		
		ProductData productData = new ProductData(Id.generate(),
				new Money(new BigDecimal(1000), Currency.getInstance("EUR")), "Standard", ProductType.STANDARD,
				new Date());
		Money totalCost = new Money(new BigDecimal(10000), Currency.getInstance("EUR"));
		RequestItem item = new RequestItem(productData, 10, totalCost);
		
		ProductData productData2 = new ProductData(Id.generate(),
				new Money(new BigDecimal(2000), Currency.getInstance("EUR")), "Drug", ProductType.DRUG,
				new Date());
		Money totalCost2 = new Money(new BigDecimal(10000), Currency.getInstance("EUR"));
		RequestItem item2 = new RequestItem(productData2, 5, totalCost2);
		invoiceRequest.add(item);
		invoiceRequest.add(item2);

		Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		Mockito.verify(taxPolicy, Mockito.times(2)).calculateTax(Mockito.any(ProductType.class), Mockito.any(Money.class));
	}
	
	@Test
	public void thirdTestCase() {
		Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		assertThat(invoice.getItems().size(), Matchers.is(0));
		Mockito.verify(taxPolicy, Mockito.never()).calculateTax(Mockito.any(ProductType.class), Mockito.any(Money.class));
	}
	
	@Test
	public void fourthTestCase() {
		
		ProductData productData = new ProductData(Id.generate(),
				new Money(new BigDecimal(1000), Currency.getInstance("EUR")), "Standard", ProductType.STANDARD,
				new Date());
		Money totalCost = new Money(new BigDecimal(10000), Currency.getInstance("EUR"));
		RequestItem item = new RequestItem(productData, 10, totalCost);
		invoiceRequest.add(item);

		Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);
		assertThat(invoice.getGros(), Matchers.is(new Money(new BigDecimal(11000), Currency.getInstance("EUR"))));
		assertThat(invoice.getNet(), Matchers.is(new Money(new BigDecimal(10000), Currency.getInstance("EUR"))));
		assertThat(invoice.getItems().get(0).getGros(), Matchers.is(new Money(new BigDecimal(11000), Currency.getInstance("EUR"))));
		assertThat(invoice.getItems().get(0).getNet(), Matchers.is(new Money(new BigDecimal(10000), Currency.getInstance("EUR"))));
		assertThat(invoice.getItems().get(0).getTax().getAmount(), Matchers.is(new Money(new BigDecimal(1000), Currency.getInstance("EUR"))));
		Mockito.verify(taxPolicy, Mockito.times(1)).calculateTax(Mockito.any(ProductType.class), Mockito.any(Money.class));
	}

}
