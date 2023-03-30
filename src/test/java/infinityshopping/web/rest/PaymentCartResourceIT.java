package infinityshopping.web.rest;

import static infinityshopping.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import infinityshopping.IntegrationTest;
import infinityshopping.domain.PaymentCart;
import infinityshopping.domain.enumeration.PaymentStatusEnum;
import infinityshopping.repository.PaymentCartRepository;
import infinityshopping.service.dto.PaymentCartDTO;
import infinityshopping.service.mapper.PaymentCartMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PaymentCartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentCartResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_GROSS = new BigDecimal(2);

    private static final PaymentStatusEnum DEFAULT_PAYMENT_STATUS_ENUM = PaymentStatusEnum.WaitingForBankTransfer;
    private static final PaymentStatusEnum UPDATED_PAYMENT_STATUS_ENUM = PaymentStatusEnum.PreparationForShipping;

    private static final String ENTITY_API_URL = "/api/payment-carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentCartRepository paymentCartRepository;

    @Autowired
    private PaymentCartMapper paymentCartMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentCartMockMvc;

    private PaymentCart paymentCart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentCart createEntity(EntityManager em) {
        PaymentCart paymentCart = new PaymentCart()
            .name(DEFAULT_NAME)
            .priceNet(DEFAULT_PRICE_NET)
            .vat(DEFAULT_VAT)
            .priceGross(DEFAULT_PRICE_GROSS)
            .paymentStatusEnum(DEFAULT_PAYMENT_STATUS_ENUM);
        return paymentCart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentCart createUpdatedEntity(EntityManager em) {
        PaymentCart paymentCart = new PaymentCart()
            .name(UPDATED_NAME)
            .priceNet(UPDATED_PRICE_NET)
            .vat(UPDATED_VAT)
            .priceGross(UPDATED_PRICE_GROSS)
            .paymentStatusEnum(UPDATED_PAYMENT_STATUS_ENUM);
        return paymentCart;
    }

    @BeforeEach
    public void initTest() {
        paymentCart = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentCart() throws Exception {
        int databaseSizeBeforeCreate = paymentCartRepository.findAll().size();
        // Create the PaymentCart
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);
        restPaymentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentCart testPaymentCart = paymentCartList.get(paymentCartList.size() - 1);
        assertThat(testPaymentCart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentCart.getPriceNet()).isEqualByComparingTo(DEFAULT_PRICE_NET);
        assertThat(testPaymentCart.getVat()).isEqualByComparingTo(DEFAULT_VAT);
        assertThat(testPaymentCart.getPriceGross()).isEqualByComparingTo(DEFAULT_PRICE_GROSS);
        assertThat(testPaymentCart.getPaymentStatusEnum()).isEqualTo(DEFAULT_PAYMENT_STATUS_ENUM);
    }

    @Test
    @Transactional
    void createPaymentCartWithExistingId() throws Exception {
        // Create the PaymentCart with an existing ID
        paymentCart.setId(1L);
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        int databaseSizeBeforeCreate = paymentCartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentCartRepository.findAll().size();
        // set the field null
        paymentCart.setName(null);

        // Create the PaymentCart, which fails.
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        restPaymentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceNetIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentCartRepository.findAll().size();
        // set the field null
        paymentCart.setPriceNet(null);

        // Create the PaymentCart, which fails.
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        restPaymentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVatIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentCartRepository.findAll().size();
        // set the field null
        paymentCart.setVat(null);

        // Create the PaymentCart, which fails.
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        restPaymentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceGrossIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentCartRepository.findAll().size();
        // set the field null
        paymentCart.setPriceGross(null);

        // Create the PaymentCart, which fails.
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        restPaymentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentCarts() throws Exception {
        // Initialize the database
        paymentCartRepository.saveAndFlush(paymentCart);

        // Get all the paymentCartList
        restPaymentCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priceNet").value(hasItem(sameNumber(DEFAULT_PRICE_NET))))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(sameNumber(DEFAULT_VAT))))
            .andExpect(jsonPath("$.[*].priceGross").value(hasItem(sameNumber(DEFAULT_PRICE_GROSS))))
            .andExpect(jsonPath("$.[*].paymentStatusEnum").value(hasItem(DEFAULT_PAYMENT_STATUS_ENUM.toString())));
    }

    @Test
    @Transactional
    void getPaymentCart() throws Exception {
        // Initialize the database
        paymentCartRepository.saveAndFlush(paymentCart);

        // Get the paymentCart
        restPaymentCartMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentCart.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.priceNet").value(sameNumber(DEFAULT_PRICE_NET)))
            .andExpect(jsonPath("$.vat").value(sameNumber(DEFAULT_VAT)))
            .andExpect(jsonPath("$.priceGross").value(sameNumber(DEFAULT_PRICE_GROSS)))
            .andExpect(jsonPath("$.paymentStatusEnum").value(DEFAULT_PAYMENT_STATUS_ENUM.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentCart() throws Exception {
        // Get the paymentCart
        restPaymentCartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentCart() throws Exception {
        // Initialize the database
        paymentCartRepository.saveAndFlush(paymentCart);

        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();

        // Update the paymentCart
        PaymentCart updatedPaymentCart = paymentCartRepository.findById(paymentCart.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentCart are not directly saved in db
        em.detach(updatedPaymentCart);
        updatedPaymentCart
            .name(UPDATED_NAME)
            .priceNet(UPDATED_PRICE_NET)
            .vat(UPDATED_VAT)
            .priceGross(UPDATED_PRICE_GROSS)
            .paymentStatusEnum(UPDATED_PAYMENT_STATUS_ENUM);
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(updatedPaymentCart);

        restPaymentCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
        PaymentCart testPaymentCart = paymentCartList.get(paymentCartList.size() - 1);
        assertThat(testPaymentCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentCart.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testPaymentCart.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testPaymentCart.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testPaymentCart.getPaymentStatusEnum()).isEqualTo(UPDATED_PAYMENT_STATUS_ENUM);
    }

    @Test
    @Transactional
    void putNonExistingPaymentCart() throws Exception {
        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();
        paymentCart.setId(count.incrementAndGet());

        // Create the PaymentCart
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentCart() throws Exception {
        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();
        paymentCart.setId(count.incrementAndGet());

        // Create the PaymentCart
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentCart() throws Exception {
        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();
        paymentCart.setId(count.incrementAndGet());

        // Create the PaymentCart
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentCartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentCartDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentCartWithPatch() throws Exception {
        // Initialize the database
        paymentCartRepository.saveAndFlush(paymentCart);

        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();

        // Update the paymentCart using partial update
        PaymentCart partialUpdatedPaymentCart = new PaymentCart();
        partialUpdatedPaymentCart.setId(paymentCart.getId());

        partialUpdatedPaymentCart.priceGross(UPDATED_PRICE_GROSS).paymentStatusEnum(UPDATED_PAYMENT_STATUS_ENUM);

        restPaymentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentCart))
            )
            .andExpect(status().isOk());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
        PaymentCart testPaymentCart = paymentCartList.get(paymentCartList.size() - 1);
        assertThat(testPaymentCart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentCart.getPriceNet()).isEqualByComparingTo(DEFAULT_PRICE_NET);
        assertThat(testPaymentCart.getVat()).isEqualByComparingTo(DEFAULT_VAT);
        assertThat(testPaymentCart.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testPaymentCart.getPaymentStatusEnum()).isEqualTo(UPDATED_PAYMENT_STATUS_ENUM);
    }

    @Test
    @Transactional
    void fullUpdatePaymentCartWithPatch() throws Exception {
        // Initialize the database
        paymentCartRepository.saveAndFlush(paymentCart);

        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();

        // Update the paymentCart using partial update
        PaymentCart partialUpdatedPaymentCart = new PaymentCart();
        partialUpdatedPaymentCart.setId(paymentCart.getId());

        partialUpdatedPaymentCart
            .name(UPDATED_NAME)
            .priceNet(UPDATED_PRICE_NET)
            .vat(UPDATED_VAT)
            .priceGross(UPDATED_PRICE_GROSS)
            .paymentStatusEnum(UPDATED_PAYMENT_STATUS_ENUM);

        restPaymentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentCart))
            )
            .andExpect(status().isOk());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
        PaymentCart testPaymentCart = paymentCartList.get(paymentCartList.size() - 1);
        assertThat(testPaymentCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentCart.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testPaymentCart.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testPaymentCart.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testPaymentCart.getPaymentStatusEnum()).isEqualTo(UPDATED_PAYMENT_STATUS_ENUM);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentCart() throws Exception {
        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();
        paymentCart.setId(count.incrementAndGet());

        // Create the PaymentCart
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentCartDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentCart() throws Exception {
        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();
        paymentCart.setId(count.incrementAndGet());

        // Create the PaymentCart
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentCart() throws Exception {
        int databaseSizeBeforeUpdate = paymentCartRepository.findAll().size();
        paymentCart.setId(count.incrementAndGet());

        // Create the PaymentCart
        PaymentCartDTO paymentCartDTO = paymentCartMapper.toDto(paymentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentCartMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentCartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentCart in the database
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentCart() throws Exception {
        // Initialize the database
        paymentCartRepository.saveAndFlush(paymentCart);

        int databaseSizeBeforeDelete = paymentCartRepository.findAll().size();

        // Delete the paymentCart
        restPaymentCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentCart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentCart> paymentCartList = paymentCartRepository.findAll();
        assertThat(paymentCartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
