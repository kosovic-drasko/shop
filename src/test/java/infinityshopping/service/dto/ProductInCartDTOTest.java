package infinityshopping.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import infinityshopping.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductInCartDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInCartDTO.class);
        ProductInCartDTO productInCartDTO1 = new ProductInCartDTO();
        productInCartDTO1.setId(1L);
        ProductInCartDTO productInCartDTO2 = new ProductInCartDTO();
        assertThat(productInCartDTO1).isNotEqualTo(productInCartDTO2);
        productInCartDTO2.setId(productInCartDTO1.getId());
        assertThat(productInCartDTO1).isEqualTo(productInCartDTO2);
        productInCartDTO2.setId(2L);
        assertThat(productInCartDTO1).isNotEqualTo(productInCartDTO2);
        productInCartDTO1.setId(null);
        assertThat(productInCartDTO1).isNotEqualTo(productInCartDTO2);
    }
}
