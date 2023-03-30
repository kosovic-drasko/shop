package infinityshopping.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import infinityshopping.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OrderMainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderMainDTO.class);
        OrderMainDTO orderMainDTO1 = new OrderMainDTO();
        orderMainDTO1.setId(1L);
        OrderMainDTO orderMainDTO2 = new OrderMainDTO();
        assertThat(orderMainDTO1).isNotEqualTo(orderMainDTO2);
        orderMainDTO2.setId(orderMainDTO1.getId());
        assertThat(orderMainDTO1).isEqualTo(orderMainDTO2);
        orderMainDTO2.setId(2L);
        assertThat(orderMainDTO1).isNotEqualTo(orderMainDTO2);
        orderMainDTO1.setId(null);
        assertThat(orderMainDTO1).isNotEqualTo(orderMainDTO2);
    }
}
