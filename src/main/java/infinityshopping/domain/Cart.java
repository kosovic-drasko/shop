package infinityshopping.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Cart.
 */
@Entity
@Table(name = "cart")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "amount_of_cart_net", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountOfCartNet;

    @NotNull
    @Column(name = "amount_of_cart_gross", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountOfCartGross;

    @NotNull
    @Column(name = "amount_of_shipment_net", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountOfShipmentNet;

    @NotNull
    @Column(name = "amount_of_shipment_gross", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountOfShipmentGross;

    @NotNull
    @Column(name = "amount_of_order_net", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountOfOrderNet;

    @NotNull
    @Column(name = "amount_of_order_gross", precision = 21, scale = 2, nullable = false)
    private BigDecimal amountOfOrderGross;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Cart id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmountOfCartNet() {
        return this.amountOfCartNet;
    }

    public Cart amountOfCartNet(BigDecimal amountOfCartNet) {
        this.setAmountOfCartNet(amountOfCartNet);
        return this;
    }

    public void setAmountOfCartNet(BigDecimal amountOfCartNet) {
        this.amountOfCartNet = amountOfCartNet;
    }

    public BigDecimal getAmountOfCartGross() {
        return this.amountOfCartGross;
    }

    public Cart amountOfCartGross(BigDecimal amountOfCartGross) {
        this.setAmountOfCartGross(amountOfCartGross);
        return this;
    }

    public void setAmountOfCartGross(BigDecimal amountOfCartGross) {
        this.amountOfCartGross = amountOfCartGross;
    }

    public BigDecimal getAmountOfShipmentNet() {
        return this.amountOfShipmentNet;
    }

    public Cart amountOfShipmentNet(BigDecimal amountOfShipmentNet) {
        this.setAmountOfShipmentNet(amountOfShipmentNet);
        return this;
    }

    public void setAmountOfShipmentNet(BigDecimal amountOfShipmentNet) {
        this.amountOfShipmentNet = amountOfShipmentNet;
    }

    public BigDecimal getAmountOfShipmentGross() {
        return this.amountOfShipmentGross;
    }

    public Cart amountOfShipmentGross(BigDecimal amountOfShipmentGross) {
        this.setAmountOfShipmentGross(amountOfShipmentGross);
        return this;
    }

    public void setAmountOfShipmentGross(BigDecimal amountOfShipmentGross) {
        this.amountOfShipmentGross = amountOfShipmentGross;
    }

    public BigDecimal getAmountOfOrderNet() {
        return this.amountOfOrderNet;
    }

    public Cart amountOfOrderNet(BigDecimal amountOfOrderNet) {
        this.setAmountOfOrderNet(amountOfOrderNet);
        return this;
    }

    public void setAmountOfOrderNet(BigDecimal amountOfOrderNet) {
        this.amountOfOrderNet = amountOfOrderNet;
    }

    public BigDecimal getAmountOfOrderGross() {
        return this.amountOfOrderGross;
    }

    public Cart amountOfOrderGross(BigDecimal amountOfOrderGross) {
        this.setAmountOfOrderGross(amountOfOrderGross);
        return this;
    }

    public void setAmountOfOrderGross(BigDecimal amountOfOrderGross) {
        this.amountOfOrderGross = amountOfOrderGross;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cart)) {
            return false;
        }
        return id != null && id.equals(((Cart) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", amountOfCartNet=" + getAmountOfCartNet() +
            ", amountOfCartGross=" + getAmountOfCartGross() +
            ", amountOfShipmentNet=" + getAmountOfShipmentNet() +
            ", amountOfShipmentGross=" + getAmountOfShipmentGross() +
            ", amountOfOrderNet=" + getAmountOfOrderNet() +
            ", amountOfOrderGross=" + getAmountOfOrderGross() +
            "}";
    }
}
