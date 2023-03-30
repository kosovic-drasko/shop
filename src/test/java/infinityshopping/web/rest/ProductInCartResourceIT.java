package infinityshopping.web.rest;

import static infinityshopping.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import infinityshopping.IntegrationTest;
import infinityshopping.domain.ProductInCart;
import infinityshopping.repository.ProductInCartRepository;
import infinityshopping.service.dto.ProductInCartDTO;
import infinityshopping.service.mapper.ProductInCartMapper;
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
 * Integration tests for the {@link ProductInCartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProductInCartResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-in-carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductInCartRepository productInCartRepository;

    @Autowired
    private ProductInCartMapper productInCartMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductInCartMockMvc;

    private ProductInCart productInCart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInCart createEntity(EntityManager em) {
        ProductInCart productInCart = new ProductInCart()
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
        return productInCart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInCart createUpdatedEntity(EntityManager em) {
        ProductInCart productInCart = new ProductInCart()
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
        return productInCart;
    }

    @BeforeEach
    public void initTest() {
        productInCart = createEntity(em);
    }

    @Test
    @Transactional
    void createProductInCart() throws Exception {
        int databaseSizeBeforeCreate = productInCartRepository.findAll().size();
        // Create the ProductInCart
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);
        restProductInCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInCart testProductInCart = productInCartList.get(productInCartList.size() - 1);
        assertThat(testProductInCart.getCategory()).isEqualTo(DEFAULT_CATEGORY);
        assertThat(testProductInCart.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductInCart.getQuantity()).isEqualByComparingTo(DEFAULT_QUANTITY);
        assertThat(testProductInCart.getPriceNet()).isEqualByComparingTo(DEFAULT_PRICE_NET);
        assertThat(testProductInCart.getVat()).isEqualByComparingTo(DEFAULT_VAT);
        assertThat(testProductInCart.getPriceGross()).isEqualByComparingTo(DEFAULT_PRICE_GROSS);
        assertThat(testProductInCart.getTotalPriceNet()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE_NET);
        assertThat(testProductInCart.getTotalPriceGross()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE_GROSS);
        assertThat(testProductInCart.getStock()).isEqualByComparingTo(DEFAULT_STOCK);
        assertThat(testProductInCart.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductInCart.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testProductInCart.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testProductInCart.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
    }

    @Test
    @Transactional
    void createProductInCartWithExistingId() throws Exception {
        // Create the ProductInCart with an existing ID
        productInCart.setId(1L);
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);

        int databaseSizeBeforeCreate = productInCartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductInCarts() throws Exception {
        // Initialize the database
        productInCartRepository.saveAndFlush(productInCart);

        // Get all the productInCartList
        restProductInCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInCart.getId().intValue())))
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
    void getProductInCart() throws Exception {
        // Initialize the database
        productInCartRepository.saveAndFlush(productInCart);

        // Get the productInCart
        restProductInCartMockMvc
            .perform(get(ENTITY_API_URL_ID, productInCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productInCart.getId().intValue()))
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
    void getNonExistingProductInCart() throws Exception {
        // Get the productInCart
        restProductInCartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProductInCart() throws Exception {
        // Initialize the database
        productInCartRepository.saveAndFlush(productInCart);

        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();

        // Update the productInCart
        ProductInCart updatedProductInCart = productInCartRepository.findById(productInCart.getId()).get();
        // Disconnect from session so that the updates on updatedProductInCart are not directly saved in db
        em.detach(updatedProductInCart);
        updatedProductInCart
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
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(updatedProductInCart);

        restProductInCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
        ProductInCart testProductInCart = productInCartList.get(productInCartList.size() - 1);
        assertThat(testProductInCart.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProductInCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInCart.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testProductInCart.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testProductInCart.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testProductInCart.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testProductInCart.getTotalPriceNet()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_NET);
        assertThat(testProductInCart.getTotalPriceGross()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testProductInCart.getStock()).isEqualByComparingTo(UPDATED_STOCK);
        assertThat(testProductInCart.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductInCart.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProductInCart.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProductInCart.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void putNonExistingProductInCart() throws Exception {
        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();
        productInCart.setId(count.incrementAndGet());

        // Create the ProductInCart
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductInCart() throws Exception {
        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();
        productInCart.setId(count.incrementAndGet());

        // Create the ProductInCart
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductInCart() throws Exception {
        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();
        productInCart.setId(count.incrementAndGet());

        // Create the ProductInCart
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInCartMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductInCartWithPatch() throws Exception {
        // Initialize the database
        productInCartRepository.saveAndFlush(productInCart);

        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();

        // Update the productInCart using partial update
        ProductInCart partialUpdatedProductInCart = new ProductInCart();
        partialUpdatedProductInCart.setId(productInCart.getId());

        partialUpdatedProductInCart
            .category(UPDATED_CATEGORY)
            .name(UPDATED_NAME)
            .quantity(UPDATED_QUANTITY)
            .totalPriceNet(UPDATED_TOTAL_PRICE_NET)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restProductInCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInCart))
            )
            .andExpect(status().isOk());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
        ProductInCart testProductInCart = productInCartList.get(productInCartList.size() - 1);
        assertThat(testProductInCart.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProductInCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInCart.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testProductInCart.getPriceNet()).isEqualByComparingTo(DEFAULT_PRICE_NET);
        assertThat(testProductInCart.getVat()).isEqualByComparingTo(DEFAULT_VAT);
        assertThat(testProductInCart.getPriceGross()).isEqualByComparingTo(DEFAULT_PRICE_GROSS);
        assertThat(testProductInCart.getTotalPriceNet()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_NET);
        assertThat(testProductInCart.getTotalPriceGross()).isEqualByComparingTo(DEFAULT_TOTAL_PRICE_GROSS);
        assertThat(testProductInCart.getStock()).isEqualByComparingTo(DEFAULT_STOCK);
        assertThat(testProductInCart.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductInCart.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProductInCart.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProductInCart.getProductId()).isEqualTo(DEFAULT_PRODUCT_ID);
    }

    @Test
    @Transactional
    void fullUpdateProductInCartWithPatch() throws Exception {
        // Initialize the database
        productInCartRepository.saveAndFlush(productInCart);

        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();

        // Update the productInCart using partial update
        ProductInCart partialUpdatedProductInCart = new ProductInCart();
        partialUpdatedProductInCart.setId(productInCart.getId());

        partialUpdatedProductInCart
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

        restProductInCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInCart))
            )
            .andExpect(status().isOk());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
        ProductInCart testProductInCart = productInCartList.get(productInCartList.size() - 1);
        assertThat(testProductInCart.getCategory()).isEqualTo(UPDATED_CATEGORY);
        assertThat(testProductInCart.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInCart.getQuantity()).isEqualByComparingTo(UPDATED_QUANTITY);
        assertThat(testProductInCart.getPriceNet()).isEqualByComparingTo(UPDATED_PRICE_NET);
        assertThat(testProductInCart.getVat()).isEqualByComparingTo(UPDATED_VAT);
        assertThat(testProductInCart.getPriceGross()).isEqualByComparingTo(UPDATED_PRICE_GROSS);
        assertThat(testProductInCart.getTotalPriceNet()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_NET);
        assertThat(testProductInCart.getTotalPriceGross()).isEqualByComparingTo(UPDATED_TOTAL_PRICE_GROSS);
        assertThat(testProductInCart.getStock()).isEqualByComparingTo(UPDATED_STOCK);
        assertThat(testProductInCart.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductInCart.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testProductInCart.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testProductInCart.getProductId()).isEqualTo(UPDATED_PRODUCT_ID);
    }

    @Test
    @Transactional
    void patchNonExistingProductInCart() throws Exception {
        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();
        productInCart.setId(count.incrementAndGet());

        // Create the ProductInCart
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productInCartDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductInCart() throws Exception {
        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();
        productInCart.setId(count.incrementAndGet());

        // Create the ProductInCart
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductInCart() throws Exception {
        int databaseSizeBeforeUpdate = productInCartRepository.findAll().size();
        productInCart.setId(count.incrementAndGet());

        // Create the ProductInCart
        ProductInCartDTO productInCartDTO = productInCartMapper.toDto(productInCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInCartMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInCartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInCart in the database
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductInCart() throws Exception {
        // Initialize the database
        productInCartRepository.saveAndFlush(productInCart);

        int databaseSizeBeforeDelete = productInCartRepository.findAll().size();

        // Delete the productInCart
        restProductInCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, productInCart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductInCart> productInCartList = productInCartRepository.findAll();
        assertThat(productInCartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
