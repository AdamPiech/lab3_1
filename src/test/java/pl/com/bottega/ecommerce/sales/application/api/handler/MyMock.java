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
		Mockito.when(addProductCommand.getOrderId()).thenReturn(Id.generate());
		Mockito.when(addProductCommand.getProductId()).thenReturn(Id.generate());
		Mockito.when(addProductCommand.getQuantity()).thenReturn(5);
		return addProductCommand;
	}

	public static ReservationRepository reservationRepositoryMock() {
		ReservationRepository reservationRepository = Mockito.mock(ReservationRepository.class);
		Mockito.when(reservationRepository.load(Id.generate())).thenReturn(new Reservation(Id.generate(),
				Reservation.ReservationStatus.OPENED, new ClientData(Id.generate(), "Clent"), new Date()));
		return reservationRepository;
	}

	public static ProductRepository productRepositoryMock() {
		ProductRepository productRepository = Mockito.mock(ProductRepository.class);
		Product product = new Product(Id.generate(),
				new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())), "Test", ProductType.DRUG);
		Mockito.when(productRepository.load(Id.generate())).thenReturn(product);
		return productRepository;
	}

	public static ProductRepository productRepositoryByRemovedProductMock() {
		ProductRepository productRepository = Mockito.mock(ProductRepository.class);
		Product product = new Product(Id.generate(),
				new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())), "Test", ProductType.DRUG);
		product.markAsRemoved();
		Mockito.when(productRepository.load(Id.generate())).thenReturn(product);
		return productRepository;
	}

	public static ClientRepository clientRepositoryMock() {
		ClientRepository clientRepository = Mockito.mock(ClientRepository.class);
		Mockito.when(clientRepository.load(Id.generate())).thenReturn(new Client());
		return clientRepository;
	}

	public static SuggestionService suggestionServiceMock() {
		SuggestionService suggestionService = Mockito
				.mock(pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService.class);
		Mockito.when(suggestionService.suggestEquivalent(new Product(Id.generate(),
				new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())), "asd", ProductType.DRUG),
				new Client()))
				.thenReturn(new Product(Id.generate(),
						new Money(new BigDecimal(1000), Currency.getInstance(Locale.getDefault())), "Test",
						ProductType.STANDARD));
		return suggestionService;
	}

	public static SystemContext systemContextMock() {
		SystemContext systemContext = Mockito.mock(SystemContext.class);
		Mockito.when(systemContext.getSystemUser()).thenReturn(new SystemUser(Id.generate()));
		return systemContext;
	}
}
