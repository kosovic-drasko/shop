package infinityshopping.service.dto;

import infinityshopping.domain.enumeration.PaymentStatusEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link infinityshopping.domain.PaymentCart} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentCartDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal priceNet;

    @NotNull
    private BigDecimal vat;

    @NotNull
    private BigDecimal priceGross;

    private PaymentStatusEnum paymentStatusEnum;

    private CartDTO cart;

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

    public PaymentStatusEnum getPaymentStatusEnum() {
        return paymentStatusEnum;
    }

    public void setPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum) {
        this.paymentStatusEnum = paymentStatusEnum;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentCartDTO)) {
            return false;
        }

        PaymentCartDTO paymentCartDTO = (PaymentCartDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentCartDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentCartDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priceNet=" + getPriceNet() +
            ", vat=" + getVat() +
            ", priceGross=" + getPriceGross() +
            ", paymentStatusEnum='" + getPaymentStatusEnum() + "'" +
            ", cart=" + getCart() +
            "}";
    }
}
