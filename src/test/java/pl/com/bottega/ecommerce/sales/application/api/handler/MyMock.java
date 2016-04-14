package pl.com.bottega.ecommerce.sales.application.api.handler;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.Client;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;
import pl.com.bottega.ecommerce.system.application.SystemUser;

public class MyMock {

	public static AddProductCommand addProductCommandMock() {
		AddProductCommand addProductCommand = Mockito.mock(AddProductCommand.class);
		Mockito.when(addProductCommand.getOrderId()).thenReturn(new Id("000001"));
		Mockito.when(addProductCommand.getProductId()).thenReturn(new Id("000001"));
		Mockito.when(addProductCommand.getQuantity()).thenReturn(5);
		return addProductCommand;
	}

	public static ReservationRepository reservationRepositoryMock() {
		ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
		Mockito.when(reservationRepository.load(new Id("000001"))).thenReturn(
				new Reservation(new Id("000001"), Reservation.ReservationStatus.OPENED, new ClientData(new Id("000001"), "Clent"), new Date()));
		return reservationRepository;
	}

	public static ProductRepository productRepositoryMock() {
		ProductRepository productRepository = Mockito.mock(ProductRepository.class);
		Product product = new Product(new Id("000001"), new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())),
				"Test", ProductType.DRUG);
		Mockito.when(productRepository.load(new Id("000001"))).thenReturn(product);
		return productRepository;
	}

	public static ProductRepository productRepositoryByRemovedProductMock() {
		ProductRepository productRepository = Mockito.mock(ProductRepository.class);
		Product product = new Product(new Id("000001"), new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())),
				"Test", ProductType.DRUG);
		product.markAsRemoved();
		Mockito.when(productRepository.load(new Id("000001"))).thenReturn(product);
		return productRepository;
	}

	public static ClientRepository clientRepositoryMock() {
		ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
		Mockito.when(clientRepository.load(new Id("000001"))).thenReturn(new Client());
		return clientRepository;
	}

	public static SuggestionService suggestionServiceMock() {
		SuggestionService suggestionService = Mockito
				.mock(pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService.class);
		Mockito.when(suggestionService.suggestEquivalent(new Product(new Id("000001"),
				new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())), "cos tam", ProductType.DRUG),
				new Client()))
				.thenReturn(new Product(new Id("000001"), new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())),
						"Test", ProductType.STANDARD));
		return suggestionService;
	}

	public static SystemContext systemContextMock() {
		SystemContext systemContext = Mockito.mock(SystemContext.class);
		Mockito.when(systemContext.getSystemUser()).thenReturn(new SystemUser(new Id("000001")));
		return systemContext;
	}
}
