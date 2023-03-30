package infinityshopping.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrderMainMapperTest {

    private OrderMainMapper orderMainMapper;

    @BeforeEach
    public void setUp() {
        orderMainMapper = new OrderMainMapperImpl();
    }
}
