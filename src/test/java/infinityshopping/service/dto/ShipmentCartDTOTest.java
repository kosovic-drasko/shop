package infinityshopping.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import infinityshopping.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShipmentCartDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentCartDTO.class);
        ShipmentCartDTO shipmentCartDTO1 = new ShipmentCartDTO();
        shipmentCartDTO1.setId(1L);
        ShipmentCartDTO shipmentCartDTO2 = new ShipmentCartDTO();
        assertThat(shipmentCartDTO1).isNotEqualTo(shipmentCartDTO2);
        shipmentCartDTO2.setId(shipmentCartDTO1.getId());
        assertThat(shipmentCartDTO1).isEqualTo(shipmentCartDTO2);
        shipmentCartDTO2.setId(2L);
        assertThat(shipmentCartDTO1).isNotEqualTo(shipmentCartDTO2);
        shipmentCartDTO1.setId(null);
        assertThat(shipmentCartDTO1).isNotEqualTo(shipmentCartDTO2);
    }
}
