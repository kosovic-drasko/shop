package infinityshopping.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import infinityshopping.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentCartDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentCartDTO.class);
        PaymentCartDTO paymentCartDTO1 = new PaymentCartDTO();
        paymentCartDTO1.setId(1L);
        PaymentCartDTO paymentCartDTO2 = new PaymentCartDTO();
        assertThat(paymentCartDTO1).isNotEqualTo(paymentCartDTO2);
        paymentCartDTO2.setId(paymentCartDTO1.getId());
        assertThat(paymentCartDTO1).isEqualTo(paymentCartDTO2);
        paymentCartDTO2.setId(2L);
        assertThat(paymentCartDTO1).isNotEqualTo(paymentCartDTO2);
        paymentCartDTO1.setId(null);
        assertThat(paymentCartDTO1).isNotEqualTo(paymentCartDTO2);
    }
}
