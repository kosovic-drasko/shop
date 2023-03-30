package infinityshopping.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductInOrderMainMapperTest {

    private ProductInOrderMainMapper productInOrderMainMapper;

    @BeforeEach
    public void setUp() {
        productInOrderMainMapper = new ProductInOrderMainMapperImpl();
    }
}
