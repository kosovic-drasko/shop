package infinityshopping.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import infinityshopping.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductInOrderMainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInOrderMainDTO.class);
        ProductInOrderMainDTO productInOrderMainDTO1 = new ProductInOrderMainDTO();
        productInOrderMainDTO1.setId(1L);
        ProductInOrderMainDTO productInOrderMainDTO2 = new ProductInOrderMainDTO();
        assertThat(productInOrderMainDTO1).isNotEqualTo(productInOrderMainDTO2);
        productInOrderMainDTO2.setId(productInOrderMainDTO1.getId());
        assertThat(productInOrderMainDTO1).isEqualTo(productInOrderMainDTO2);
        productInOrderMainDTO2.setId(2L);
        assertThat(productInOrderMainDTO1).isNotEqualTo(productInOrderMainDTO2);
        productInOrderMainDTO1.setId(null);
        assertThat(productInOrderMainDTO1).isNotEqualTo(productInOrderMainDTO2);
    }
}
