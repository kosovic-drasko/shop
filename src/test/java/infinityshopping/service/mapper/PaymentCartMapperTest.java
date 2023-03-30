package infinityshopping.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentCartMapperTest {

    private PaymentCartMapper paymentCartMapper;

    @BeforeEach
    public void setUp() {
        paymentCartMapper = new PaymentCartMapperImpl();
    }
}
