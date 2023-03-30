package infinityshopping.web.rest;

import static infinityshopping.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import infinityshopping.IntegrationTest;
import infinityshopping.domain.PaymentOrderMain;
import infinityshopping.repository.PaymentOrderMainRepository;
import infinityshopping.service.dto.PaymentOrderMainDTO;
import infinityshopping.service.mapper.PaymentOrderMainMapper;
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
 * Integration tests for the {@link PaymentOrderMainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentOrderMainResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_GROSS = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/payment-order-mains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentOrderMainRepository paymentOrderMainRepository;

    @Autowired
    private PaymentOrderMainMapper paymentOrderMainMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentOrderMainMockMvc;

    private PaymentOrderMain paymentOrderMain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentOrderMain createEntity(EntityManager em) {
        PaymentOrderMain paymentOrderMain = new PaymentOrderMain()
            .name(DEFAULT_NAME)
            .priceNet(DEFAULT_PRICE_NET)
            .vat(DEFAULT_VAT)
            .priceGross(DEFAULT_PRICE_GROSS);
        return paymentOrderMain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentOrderMain createUpdatedEntity(EntityManager em) {
        PaymentOrderMain paymentOrderMain = new PaymentOrderMain()
            .name(UPDATED_NAME)
            .priceNet(UPDATED_PRICE_NET)
            .vat(UPDATED_VAT)
            .priceGross(UPDATED_PRICE_GROSS);
        return paymentOrderMain;
    }

    @BeforeEach
    public void initTest() {
        paymentOrderMain = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentOrderMain() throws Exception {
        int databaseSizeBeforeCreate = paymentOrderMainRepository.findAll().size();
        // Create the PaymentOrderMain
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);
        restPaymentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentOrderMain testPaymentOrderMain = paymentOrderMainList.get(paymentOrderMainList.size() - 1);
        assertThat(testPaymentOrderMain.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPaymentOrderMain.getPriceNet()).isEqualByComparingTo(DEFAULT_PRICE_NET);
        assertThat(testPaymentOrderMain.getVat()).isEqualByComparingTo(DEFAULT_VAT);
        assertThat(testPaymentOrderMain.getPriceGross()).isEqualByComparingTo(DEFAULT_PRICE_GROSS);
    }

    @Test
    @Transactional
    void createPaymentOrderMainWithExistingId() throws Exception {
        // Create the PaymentOrderMain with an existing ID
        paymentOrderMain.setId(1L);
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        int databaseSizeBeforeCreate = paymentOrderMainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentOrderMainRepository.findAll().size();
        // set the field null
        paymentOrderMain.setName(null);

        // Create the PaymentOrderMain, which fails.
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        restPaymentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceNetIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentOrderMainRepository.findAll().size();
        // set the field null
        paymentOrderMain.setPriceNet(null);

        // Create the PaymentOrderMain, which fails.
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        restPaymentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVatIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentOrderMainRepository.findAll().size();
        // set the field null
        paymentOrderMain.setVat(null);

        // Create the PaymentOrderMain, which fails.
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        restPaymentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceGrossIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentOrderMainRepository.findAll().size();
        // set the field null
        paymentOrderMain.setPriceGross(null);

        // Create the PaymentOrderMain, which fails.
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        restPaymentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentOrderMains() throws Exception {
        // Initialize the database
        paymentOrderMainRepository.saveAndFlush(paymentOrderMain);

        // Get all the paymentOrderMainList
        restPaymentOrderMainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentOrderMain.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].priceNet").value(hasItem(sameNumber(DEFAULT_PRICE_NET))))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(sameNumber(DEFAULT_VAT))))
            .andExpect(jsonPath("$.[*].priceGross").value(hasItem(sameNumber(DEFAULT_PRICE_GROSS))));
    }

    @Test
    @Transactional
    void getPaymentOrderMain() throws Exception {
        // Initialize the database
        paymentOrderMainRepository.saveAndFlush(paymentOrderMain);

        // Get the paymentOrderMain
        restPaymentOrderMainMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentOrderMain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentOrderMain.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.priceNet").value(sameNumber(DEFAULT_PRICE_NET)))
            .andExpect(jsonPath("$.vat").value(sameNumber(DEFAULT_VAT)))
            .andExpect(jsonPath("$.priceGross").value(sameNumber(DEFAULT_PRICE_GROSS)));
    }

    @Test
    @Transactional
    void getNonExistingPaymentOrderMain() throws Exception {
        // Get the paymentOrderMain
        restPaymentOrderMainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentOrderMain() throws Exception {
        // Initialize the database
        paymentOrderMainRepository.saveAndFlush(paymentOrderMain);

        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();

        // Update the paymentOrderMain
        PaymentOrderMain updatedPaymentOrderMain = paymentOrderMainRepository.findById(paymentOrderMain.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentOrderMain are not directly saved in db
        em.detach(updatedPaymentOrderMain);
        updatedPaymentOrderMain.name(UPDATED_NAME).priceNet(UPDATED_PRICE_NET).vat(UPDATED_VAT).priceGross(UPDATED_PRICE_GROSS);
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(updatedPaymentOrderMain);

        restPaymentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentOrderMainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
        PaymentOrderMain testPaymentOrderMain = paymentOrderMainList.get(paymentOrderMainList.size() - 1);
        assertThat(testPaymentOrderMain.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentOrderMain.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testPaymentOrderMain.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testPaymentOrderMain.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
    }

    @Test
    @Transactional
    void putNonExistingPaymentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();
        paymentOrderMain.setId(count.incrementAndGet());

        // Create the PaymentOrderMain
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentOrderMainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();
        paymentOrderMain.setId(count.incrementAndGet());

        // Create the PaymentOrderMain
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();
        paymentOrderMain.setId(count.incrementAndGet());

        // Create the PaymentOrderMain
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentOrderMainWithPatch() throws Exception {
        // Initialize the database
        paymentOrderMainRepository.saveAndFlush(paymentOrderMain);

        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();

        // Update the paymentOrderMain using partial update
        PaymentOrderMain partialUpdatedPaymentOrderMain = new PaymentOrderMain();
        partialUpdatedPaymentOrderMain.setId(paymentOrderMain.getId());

        partialUpdatedPaymentOrderMain.name(UPDATED_NAME).vat(UPDATED_VAT).priceGross(UPDATED_PRICE_GROSS);

        restPaymentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentOrderMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentOrderMain))
            )
            .andExpect(status().isOk());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
        PaymentOrderMain testPaymentOrderMain = paymentOrderMainList.get(paymentOrderMainList.size() - 1);
        assertThat(testPaymentOrderMain.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentOrderMain.getPriceNet()).isEqualByComparingTo(DEFAULT_PRICE_NET);
        assertThat(testPaymentOrderMain.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testPaymentOrderMain.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
    }

    @Test
    @Transactional
    void fullUpdatePaymentOrderMainWithPatch() throws Exception {
        // Initialize the database
        paymentOrderMainRepository.saveAndFlush(paymentOrderMain);

        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();

        // Update the paymentOrderMain using partial update
        PaymentOrderMain partialUpdatedPaymentOrderMain = new PaymentOrderMain();
        partialUpdatedPaymentOrderMain.setId(paymentOrderMain.getId());

        partialUpdatedPaymentOrderMain.name(UPDATED_NAME).priceNet(UPDATED_PRICE_NET).vat(UPDATED_VAT).priceGross(UPDATED_PRICE_GROSS);

        restPaymentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentOrderMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentOrderMain))
            )
            .andExpect(status().isOk());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
        PaymentOrderMain testPaymentOrderMain = paymentOrderMainList.get(paymentOrderMainList.size() - 1);
        assertThat(testPaymentOrderMain.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPaymentOrderMain.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testPaymentOrderMain.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testPaymentOrderMain.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();
        paymentOrderMain.setId(count.incrementAndGet());

        // Create the PaymentOrderMain
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentOrderMainDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();
        paymentOrderMain.setId(count.incrementAndGet());

        // Create the PaymentOrderMain
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = paymentOrderMainRepository.findAll().size();
        paymentOrderMain.setId(count.incrementAndGet());

        // Create the PaymentOrderMain
        PaymentOrderMainDTO paymentOrderMainDTO = paymentOrderMainMapper.toDto(paymentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentOrderMainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentOrderMain in the database
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentOrderMain() throws Exception {
        // Initialize the database
        paymentOrderMainRepository.saveAndFlush(paymentOrderMain);

        int databaseSizeBeforeDelete = paymentOrderMainRepository.findAll().size();

        // Delete the paymentOrderMain
        restPaymentOrderMainMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentOrderMain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentOrderMain> paymentOrderMainList = paymentOrderMainRepository.findAll();
        assertThat(paymentOrderMainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
