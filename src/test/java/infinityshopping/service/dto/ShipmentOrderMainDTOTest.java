package infinityshopping.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import infinityshopping.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ShipmentOrderMainDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShipmentOrderMainDTO.class);
        ShipmentOrderMainDTO shipmentOrderMainDTO1 = new ShipmentOrderMainDTO();
        shipmentOrderMainDTO1.setId(1L);
        ShipmentOrderMainDTO shipmentOrderMainDTO2 = new ShipmentOrderMainDTO();
        assertThat(shipmentOrderMainDTO1).isNotEqualTo(shipmentOrderMainDTO2);
        shipmentOrderMainDTO2.setId(shipmentOrderMainDTO1.getId());
        assertThat(shipmentOrderMainDTO1).isEqualTo(shipmentOrderMainDTO2);
        shipmentOrderMainDTO2.setId(2L);
        assertThat(shipmentOrderMainDTO1).isNotEqualTo(shipmentOrderMainDTO2);
        shipmentOrderMainDTO1.setId(null);
        assertThat(shipmentOrderMainDTO1).isNotEqualTo(shipmentOrderMainDTO2);
    }
}
