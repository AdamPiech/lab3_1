package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.ClientRepository;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductRepository;
import pl.com.bottega.ecommerce.sales.domain.reservation.ReservationRepository;
import pl.com.bottega.ecommerce.system.application.SystemContext;

public class AddProductCommandHandlerTest {

	private ReservationRepository reservationRepository;
	private ProductRepository productRepository;
	private SuggestionService suggestionService;
	private ClientRepository clientRepository;
	private SystemContext systremContext;
	
	private AddProductCommandHandler productCommandHandler;
	private AddProductCommand addProductCommand;
	
	@Before
	public void start() {
        reservationRepository = MyMock.reservationRepositoryMock();
        productRepository = MyMock.productRepositoryMock();
        suggestionService = MyMock.suggestionServiceMock();
        clientRepository = MyMock.clientRepositoryMock();
        systremContext = MyMock.systemContextMock();
        
        productCommandHandler = new AddProductCommandHandler(reservationRepository, productRepository, suggestionService, clientRepository, systremContext);
        addProductCommand = MyMock.addProductCommandMock();
	}
	
	
}
