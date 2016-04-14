package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Before;

import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.system.application.SystemContext;

public class AddProductCommandHandlerTest {

	@Before
	public void start() {
        ReservationRepository reservationRepository = MyMock.reservationRepositoryMock();
        ProductRepository productRepository = MyMock.productRepositoryMock();
        SuggestionService suggestionService = MyMock.suggestionServiceMock();
        ClientRepository clientRepository = MyMock.clientRepositoryMock();
        SystemContext systremContext = MyMock.systemContextMock();
        
        AddProductCommandHandler productCommandHandler = new AddProductCommandHandler(reservationRepository, productRepository, suggestionService, clientRepository, systremContext);
        AddProductCommand addProductCommand = MyMock.addProductCommandMock();
	}

	
}
