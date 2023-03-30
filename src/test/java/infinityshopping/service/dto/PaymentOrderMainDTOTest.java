package infinityshopping.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import infinityshopping.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentOrderMainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentOrderMainDTO.class);
        PaymentOrderMainDTO paymentOrderMainDTO1 = new PaymentOrderMainDTO();
        paymentOrderMainDTO1.setId(1L);
        PaymentOrderMainDTO paymentOrderMainDTO2 = new PaymentOrderMainDTO();
        assertThat(paymentOrderMainDTO1).isNotEqualTo(paymentOrderMainDTO2);
        paymentOrderMainDTO2.setId(paymentOrderMainDTO1.getId());
        assertThat(paymentOrderMainDTO1).isEqualTo(paymentOrderMainDTO2);
        paymentOrderMainDTO2.setId(2L);
        assertThat(paymentOrderMainDTO1).isNotEqualTo(paymentOrderMainDTO2);
        paymentOrderMainDTO1.setId(null);
        assertThat(paymentOrderMainDTO1).isNotEqualTo(paymentOrderMainDTO2);
    }
}
