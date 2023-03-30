package infinityshopping.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link infinityshopping.domain.PaymentOrderMain} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentOrderMainDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal priceNet;

    @NotNull
    private BigDecimal vat;

    @NotNull
    private BigDecimal priceGross;

    private OrderMainDTO orderMain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(BigDecimal priceNet) {
        this.priceNet = priceNet;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getPriceGross() {
        return priceGross;
    }

    public void setPriceGross(BigDecimal priceGross) {
        this.priceGross = priceGross;
    }

    public OrderMainDTO getOrderMain() {
        return orderMain;
    }

    public void setOrderMain(OrderMainDTO orderMain) {
        this.orderMain = orderMain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentOrderMainDTO)) {
            return false;
        }

        PaymentOrderMainDTO paymentOrderMainDTO = (PaymentOrderMainDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentOrderMainDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentOrderMainDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priceNet=" + getPriceNet() +
            ", vat=" + getVat() +
            ", priceGross=" + getPriceGross() +
            ", orderMain=" + getOrderMain() +
            "}";
    }
}
