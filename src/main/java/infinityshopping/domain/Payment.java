package infinityshopping.domain;

import infinityshopping.domain.enumeration.PaymentStatusEnum;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Payment.
 */
@Entity
@Table(name = "payment")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Payment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 1000)
    @Column(name = "name", length = 1000, nullable = false)
    private String name;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "10000")
    @Column(name = "price_net", precision = 21, scale = 2, nullable = false)
    private BigDecimal priceNet;

    @NotNull
    @DecimalMin(value = "0")
    @DecimalMax(value = "100")
    @Column(name = "vat", precision = 21, scale = 2, nullable = false)
    private BigDecimal vat;

    @Column(name = "price_gross", precision = 21, scale = 2)
    private BigDecimal priceGross;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status_enum")
    private PaymentStatusEnum paymentStatusEnum;

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Payment id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Payment name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPriceNet() {
        return this.priceNet;
    }

    public Payment priceNet(BigDecimal priceNet) {
        this.setPriceNet(priceNet);
        return this;
    }

    public void setPriceNet(BigDecimal priceNet) {
        this.priceNet = priceNet;
    }

    public BigDecimal getVat() {
        return this.vat;
    }

    public Payment vat(BigDecimal vat) {
        this.setVat(vat);
        return this;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public BigDecimal getPriceGross() {
        return this.priceGross;
    }

    public Payment priceGross(BigDecimal priceGross) {
        this.setPriceGross(priceGross);
        return this;
    }

    public void setPriceGross(BigDecimal priceGross) {
        this.priceGross = priceGross;
    }

    public PaymentStatusEnum getPaymentStatusEnum() {
        return this.paymentStatusEnum;
    }

    public Payment paymentStatusEnum(PaymentStatusEnum paymentStatusEnum) {
        this.setPaymentStatusEnum(paymentStatusEnum);
        return this;
    }

    public void setPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum) {
        this.paymentStatusEnum = paymentStatusEnum;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public Payment createTime(Instant createTime) {
        this.setCreateTime(createTime);
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public Payment updateTime(Instant updateTime) {
        this.setUpdateTime(updateTime);
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Payment)) {
            return false;
        }
        return id != null && id.equals(((Payment) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Payment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", priceNet=" + getPriceNet() +
            ", vat=" + getVat() +
            ", priceGross=" + getPriceGross() +
            ", paymentStatusEnum='" + getPaymentStatusEnum() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            "}";
    }
}
