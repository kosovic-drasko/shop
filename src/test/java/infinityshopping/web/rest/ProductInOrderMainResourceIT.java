package infinityshopping.web.rest;

import static infinityshopping.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import infinityshopping.IntegrationTest;
import infinityshopping.domain.ProductInOrderMain;
import infinityshopping.repository.ProductInOrderMainRepository;
import infinityshopping.service.dto.ProductInOrderMainDTO;
import infinityshopping.service.mapper.ProductInOrderMainMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ProductInOrderMainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductInOrderMainResourceIT {

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_VAT = new BigDecimal(1);
    private static final BigDecimal UPDATED_VAT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PRICE_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE_GROSS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_PRICE_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TOTAL_PRICE_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE_GROSS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_STOCK = new BigDecimal(1);
    private static final BigDecimal UPDATED_STOCK = new BigDecimal(2);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_PRODUCT_ID = 1L;
    private static final Long UPDATED_PRODUCT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/product-in-order-mains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductInOrderMainRepository productInOrderMainRepository;

    @Autowired
    private ProductInOrderMainMapper productInOrderMainMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductInOrderMainMockMvc;

    private ProductInOrderMain productInOrderMain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInOrderMain createEntity(EntityManager em) {
        ProductInOrderMain productInOrderMain = new ProductInOrderMain()
            .category(DEFAULT_CATEGORY)
            .name(DEFAULT_NAME)
            .quantity(DEFAULT_QUANTITY)
            .priceNet(DEFAULT_PRICE_NET)
            .vat(DEFAULT_VAT)
            .priceGross(DEFAULT_PRICE_GROSS)
            .totalPriceNet(DEFAULT_TOTAL_PRICE_NET)
            .totalPriceGross(DEFAULT_TOTAL_PRICE_GROSS)
            .stock(DEFAULT_STOCK)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .productId(DEFAULT_PRODUCT_ID);
        return productInOrderMain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInOrderMain createUpdatedEntity(EntityManager em) {
        ProductInOrderMain productInOrderMain = new ProductInOrderMain()
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .priceNet(UPDATED_PRICE_NET)
            .vat(UPDATED_VAT)
            .priceGross(UPDATED_PRICE_GROSS)
            .totalPriceNet(UPDATED_TOTAL_PRICE_NET)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .productId(UPDATED_PRODUCT_ID);
        return productInOrderMain;
    }

    @BeforeEach
    public void initTest() {
        productInOrderMain = createEntity(em);
    }

    @Test
    @Transactional
    void createProductInOrderMain() throws Exception {
        int databaseSizeBeforeCreate = productInOrderMainRepository.findAll().size();
        // Create the ProductInOrderMain
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);
        restProductInOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInOrderMain testProductInOrderMain = productInOrderMainList.get(productInOrderMainList.size() - 1);
        assertThat(testProductInOrderMain.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProductInOrderMain.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductInOrderMain.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testProductInOrderMain.getPriceNet()).isEqualByComparingTo(DEFAULT_PRICE_NET);
        assertThat(testProductInOrderMain.getVat()).isEqualByComparingTo(DEFAULT_VAT);
        assertThat(testProductInOrderMain.getPriceGross()).isEqualByComparingTo(DEFAULT_PRICE_GROSS);
        assertThat(testProductInOrderMain.getTotalPriceNet()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE_NET);
        assertThat(testProductInOrderMain.getTotalPriceGross()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE_GROSS);
        assertThat(testProductInOrderMain.getStock()).isEqualByComparingTo(DEFAULT_STOCK);
        assertThat(testProductInOrderMain.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductInOrderMain.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProductInOrderMain.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProductInOrderMain.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
    }

    @Test
    @Transactional
    void createProductInOrderMainWithExistingId() throws Exception {
        // Create the ProductInOrderMain with an existing ID
        productInOrderMain.setId(1L);
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);

        int databaseSizeBeforeCreate = productInOrderMainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductInOrderMains() throws Exception {
        // Initialize the database
        productInOrderMainRepository.saveAndFlush(productInOrderMain);

        // Get all the productInOrderMainList
        restProductInOrderMainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInOrderMain.getId().intValue())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(sameNumber(DEFAULT_QUANTITY))))
            .andExpect(jsonPath("$.[*].priceNet").value(hasItem(sameNumber(DEFAULT_PRICE_NET))))
            .andExpect(jsonPath("$.[*].vat").value(hasItem(sameNumber(DEFAULT_VAT))))
            .andExpect(jsonPath("$.[*].priceGross").value(hasItem(sameNumber(DEFAULT_PRICE_GROSS))))
            .andExpect(jsonPath("$.[*].totalPriceNet").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_NET))))
            .andExpect(jsonPath("$.[*].totalPriceGross").value(hasItem(sameNumber(DEFAULT_TOTAL_PRICE_GROSS))))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(sameNumber(DEFAULT_STOCK))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].productId").value(hasItem(DEFAULT_PRODUCT_ID.intValue())));
    }

    @Test
    @Transactional
    void getProductInOrderMain() throws Exception {
        // Initialize the database
        productInOrderMainRepository.saveAndFlush(productInOrderMain);

        // Get the productInOrderMain
        restProductInOrderMainMockMvc
            .perform(get(ENTITY_API_URL_ID, productInOrderMain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productInOrderMain.getId().intValue()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.quantity").value(sameNumber(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.priceNet").value(sameNumber(DEFAULT_PRICE_NET)))
            .andExpect(jsonPath("$.vat").value(sameNumber(DEFAULT_VAT)))
            .andExpect(jsonPath("$.priceGross").value(sameNumber(DEFAULT_PRICE_GROSS)))
            .andExpect(jsonPath("$.totalPriceNet").value(sameNumber(DEFAULT_TOTAL_PRICE_NET)))
            .andExpect(jsonPath("$.totalPriceGross").value(sameNumber(DEFAULT_TOTAL_PRICE_GROSS)))
            .andExpect(jsonPath("$.stock").value(sameNumber(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.productId").value(DEFAULT_PRODUCT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingProductInOrderMain() throws Exception {
        // Get the productInOrderMain
        restProductInOrderMainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductInOrderMain() throws Exception {
        // Initialize the database
        productInOrderMainRepository.saveAndFlush(productInOrderMain);

        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();

        // Update the productInOrderMain
        ProductInOrderMain updatedProductInOrderMain = productInOrderMainRepository.findById(productInOrderMain.getId()).get();
        // Disconnect from session so that the updates on updatedProductInOrderMain are not directly saved in db
        em.detach(updatedProductInOrderMain);
        updatedProductInOrderMain
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .priceNet(UPDATED_PRICE_NET)
            .vat(UPDATED_VAT)
            .priceGross(UPDATED_PRICE_GROSS)
            .totalPriceNet(UPDATED_TOTAL_PRICE_NET)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .productId(UPDATED_PRODUCT_ID);
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(updatedProductInOrderMain);

        restProductInOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInOrderMainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
        ProductInOrderMain testProductInOrderMain = productInOrderMainList.get(productInOrderMainList.size() - 1);
        assertThat(testProductInOrderMain.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProductInOrderMain.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInOrderMain.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testProductInOrderMain.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testProductInOrderMain.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testProductInOrderMain.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testProductInOrderMain.getTotalPriceNet()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_NET);
        assertThat(testProductInOrderMain.getTotalPriceGross()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testProductInOrderMain.getStock()).isEqualByComparingTo(UPDATED_STOCK);
        assertThat(testProductInOrderMain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductInOrderMain.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProductInOrderMain.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProductInOrderMain.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void putNonExistingProductInOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();
        productInOrderMain.setId(count.incrementAndGet());

        // Create the ProductInOrderMain
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInOrderMainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductInOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();
        productInOrderMain.setId(count.incrementAndGet());

        // Create the ProductInOrderMain
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductInOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();
        productInOrderMain.setId(count.incrementAndGet());

        // Create the ProductInOrderMain
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductInOrderMainWithPatch() throws Exception {
        // Initialize the database
        productInOrderMainRepository.saveAndFlush(productInOrderMain);

        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();

        // Update the productInOrderMain using partial update
        ProductInOrderMain partialUpdatedProductInOrderMain = new ProductInOrderMain();
        partialUpdatedProductInOrderMain.setId(productInOrderMain.getId());

        partialUpdatedProductInOrderMain
            .quantity(UPDATED_QUANTITY)
            .priceNet(UPDATED_PRICE_NET)
            .priceGross(UPDATED_PRICE_GROSS)
            .totalPriceNet(UPDATED_TOTAL_PRICE_NET)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .productId(UPDATED_PRODUCT_ID);

        restProductInOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInOrderMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInOrderMain))
            )
            .andExpect(status().isOk());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
        ProductInOrderMain testProductInOrderMain = productInOrderMainList.get(productInOrderMainList.size() - 1);
        assertThat(testProductInOrderMain.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProductInOrderMain.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductInOrderMain.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testProductInOrderMain.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testProductInOrderMain.getVat()).isEqualByComparingTo(DEFAULT_VAT);
        assertThat(testProductInOrderMain.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testProductInOrderMain.getTotalPriceNet()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_NET);
        assertThat(testProductInOrderMain.getTotalPriceGross()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testProductInOrderMain.getStock()).isEqualByComparingTo(UPDATED_STOCK);
        assertThat(testProductInOrderMain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductInOrderMain.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProductInOrderMain.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProductInOrderMain.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void fullUpdateProductInOrderMainWithPatch() throws Exception {
        // Initialize the database
        productInOrderMainRepository.saveAndFlush(productInOrderMain);

        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();

        // Update the productInOrderMain using partial update
        ProductInOrderMain partialUpdatedProductInOrderMain = new ProductInOrderMain();
        partialUpdatedProductInOrderMain.setId(productInOrderMain.getId());

        partialUpdatedProductInOrderMain
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .priceNet(UPDATED_PRICE_NET)
            .vat(UPDATED_VAT)
            .priceGross(UPDATED_PRICE_GROSS)
            .totalPriceNet(UPDATED_TOTAL_PRICE_NET)
            .totalPriceGross(UPDATED_TOTAL_PRICE_GROSS)
            .stock(UPDATED_STOCK)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .productId(UPDATED_PRODUCT_ID);

        restProductInOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInOrderMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInOrderMain))
            )
            .andExpect(status().isOk());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
        ProductInOrderMain testProductInOrderMain = productInOrderMainList.get(productInOrderMainList.size() - 1);
        assertThat(testProductInOrderMain.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProductInOrderMain.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInOrderMain.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testProductInOrderMain.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testProductInOrderMain.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testProductInOrderMain.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testProductInOrderMain.getTotalPriceNet()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_NET);
        assertThat(testProductInOrderMain.getTotalPriceGross()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testProductInOrderMain.getStock()).isEqualByComparingTo(UPDATED_STOCK);
        assertThat(testProductInOrderMain.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductInOrderMain.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProductInOrderMain.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProductInOrderMain.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingProductInOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();
        productInOrderMain.setId(count.incrementAndGet());

        // Create the ProductInOrderMain
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productInOrderMainDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductInOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();
        productInOrderMain.setId(count.incrementAndGet());

        // Create the ProductInOrderMain
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductInOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = productInOrderMainRepository.findAll().size();
        productInOrderMain.setId(count.incrementAndGet());

        // Create the ProductInOrderMain
        ProductInOrderMainDTO productInOrderMainDTO = productInOrderMainMapper.toDto(productInOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInOrderMainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInOrderMain in the database
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductInOrderMain() throws Exception {
        // Initialize the database
        productInOrderMainRepository.saveAndFlush(productInOrderMain);

        int databaseSizeBeforeDelete = productInOrderMainRepository.findAll().size();

        // Delete the productInOrderMain
        restProductInOrderMainMockMvc
            .perform(delete(ENTITY_API_URL_ID, productInOrderMain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductInOrderMain> productInOrderMainList = productInOrderMainRepository.findAll();
        assertThat(productInOrderMainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
