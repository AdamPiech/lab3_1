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
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class BookKeeperTest {

	private BookKeeper bookKeeper;
	private InvoiceFactory invoiceFactory;
	private TaxPolicy taxPolicy;
	private InvoiceRequest invoiceRequest;

	@Before
	public void start() {
		taxPolicy = Mockito.mock(TaxPolicy.class);
		invoiceFactory = new InvoiceFactory();
		bookKeeper = new BookKeeper(invoiceFactory);
		invoiceRequest = new InvoiceRequest(new ClientData(Id.generate(), "Client"));
		
		Mockito.when(taxPolicy.calculateTax(Mockito.any(ProductType.class), Mockito.any(Money.class))).thenReturn(
				new Tax(new Money(new BigDecimal(1000), Currency.getInstance("EUR")), "Podatek za coœ tam."));
	}

	@Test
	public void firstTestCase() {
		
		ProductData productData = new ProductData(Id.generate(),
				new Money(new BigDecimal(1000), Currency.getInstance("EUR")), "Standard", ProductType.STANDARD,
				new Date());
		Money totalCost = new Money(new BigDecimal(10000), Currency.getInstance("EUR"));
		RequestItem item = new RequestItem(productData, 10, totalCost);
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

}
