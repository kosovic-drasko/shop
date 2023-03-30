package infinityshopping.web.rest;

import static infinityshopping.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import infinityshopping.IntegrationTest;
import infinityshopping.domain.Cart;
import infinityshopping.repository.CartRepository;
import infinityshopping.service.dto.CartDTO;
import infinityshopping.service.mapper.CartMapper;
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
 * Integration tests for the {@link CartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CartResourceIT {

    private static final BigDecimal DEFAULT_AMOUNT_OF_CART_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OF_CART_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_OF_CART_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OF_CART_GROSS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_OF_SHIPMENT_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OF_SHIPMENT_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_OF_SHIPMENT_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OF_SHIPMENT_GROSS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_OF_ORDER_NET = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OF_ORDER_NET = new BigDecimal(2);

    private static final BigDecimal DEFAULT_AMOUNT_OF_ORDER_GROSS = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OF_ORDER_GROSS = new BigDecimal(2);

    private static final String ENTITY_API_URL = "/api/carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCartMockMvc;

    private Cart cart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createEntity(EntityManager em) {
        Cart cart = new Cart()
            .amountOfCartNet(DEFAULT_AMOUNT_OF_CART_NET)
            .amountOfCartGross(DEFAULT_AMOUNT_OF_CART_GROSS)
            .amountOfShipmentNet(DEFAULT_AMOUNT_OF_SHIPMENT_NET)
            .amountOfShipmentGross(DEFAULT_AMOUNT_OF_SHIPMENT_GROSS)
            .amountOfOrderNet(DEFAULT_AMOUNT_OF_ORDER_NET)
            .amountOfOrderGross(DEFAULT_AMOUNT_OF_ORDER_GROSS);
        return cart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cart createUpdatedEntity(EntityManager em) {
        Cart cart = new Cart()
            .amountOfCartNet(UPDATED_AMOUNT_OF_CART_NET)
            .amountOfCartGross(UPDATED_AMOUNT_OF_CART_GROSS)
            .amountOfShipmentNet(UPDATED_AMOUNT_OF_SHIPMENT_NET)
            .amountOfShipmentGross(UPDATED_AMOUNT_OF_SHIPMENT_GROSS)
            .amountOfOrderNet(UPDATED_AMOUNT_OF_ORDER_NET)
            .amountOfOrderGross(UPDATED_AMOUNT_OF_ORDER_GROSS);
        return cart;
    }

    @BeforeEach
    public void initTest() {
        cart = createEntity(em);
    }

    @Test
    @Transactional
    void createCart() throws Exception {
        int databaseSizeBeforeCreate = cartRepository.findAll().size();
        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);
        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isCreated());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate + 1);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getAmountOfCartNet()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_CART_NET);
        assertThat(testCart.getAmountOfCartGross()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_CART_GROSS);
        assertThat(testCart.getAmountOfShipmentNet()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_SHIPMENT_NET);
        assertThat(testCart.getAmountOfShipmentGross()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_SHIPMENT_GROSS);
        assertThat(testCart.getAmountOfOrderNet()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_ORDER_NET);
        assertThat(testCart.getAmountOfOrderGross()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_ORDER_GROSS);
    }

    @Test
    @Transactional
    void createCartWithExistingId() throws Exception {
        // Create the Cart with an existing ID
        cart.setId(1L);
        CartDTO cartDTO = cartMapper.toDto(cart);

        int databaseSizeBeforeCreate = cartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkAmountOfCartNetIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setAmountOfCartNet(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.toDto(cart);

        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountOfCartGrossIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setAmountOfCartGross(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.toDto(cart);

        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountOfShipmentNetIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setAmountOfShipmentNet(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.toDto(cart);

        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountOfShipmentGrossIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setAmountOfShipmentGross(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.toDto(cart);

        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountOfOrderNetIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setAmountOfOrderNet(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.toDto(cart);

        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAmountOfOrderGrossIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartRepository.findAll().size();
        // set the field null
        cart.setAmountOfOrderGross(null);

        // Create the Cart, which fails.
        CartDTO cartDTO = cartMapper.toDto(cart);

        restCartMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isBadRequest());

        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCarts() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get all the cartList
        restCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cart.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountOfCartNet").value(hasItem(sameNumber(DEFAULT_AMOUNT_OF_CART_NET))))
            .andExpect(jsonPath("$.[*].amountOfCartGross").value(hasItem(sameNumber(DEFAULT_AMOUNT_OF_CART_GROSS))))
            .andExpect(jsonPath("$.[*].amountOfShipmentNet").value(hasItem(sameNumber(DEFAULT_AMOUNT_OF_SHIPMENT_NET))))
            .andExpect(jsonPath("$.[*].amountOfShipmentGross").value(hasItem(sameNumber(DEFAULT_AMOUNT_OF_SHIPMENT_GROSS))))
            .andExpect(jsonPath("$.[*].amountOfOrderNet").value(hasItem(sameNumber(DEFAULT_AMOUNT_OF_ORDER_NET))))
            .andExpect(jsonPath("$.[*].amountOfOrderGross").value(hasItem(sameNumber(DEFAULT_AMOUNT_OF_ORDER_GROSS))));
    }

    @Test
    @Transactional
    void getCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        // Get the cart
        restCartMockMvc
            .perform(get(ENTITY_API_URL_ID, cart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cart.getId().intValue()))
            .andExpect(jsonPath("$.amountOfCartNet").value(sameNumber(DEFAULT_AMOUNT_OF_CART_NET)))
            .andExpect(jsonPath("$.amountOfCartGross").value(sameNumber(DEFAULT_AMOUNT_OF_CART_GROSS)))
            .andExpect(jsonPath("$.amountOfShipmentNet").value(sameNumber(DEFAULT_AMOUNT_OF_SHIPMENT_NET)))
            .andExpect(jsonPath("$.amountOfShipmentGross").value(sameNumber(DEFAULT_AMOUNT_OF_SHIPMENT_GROSS)))
            .andExpect(jsonPath("$.amountOfOrderNet").value(sameNumber(DEFAULT_AMOUNT_OF_ORDER_NET)))
            .andExpect(jsonPath("$.amountOfOrderGross").value(sameNumber(DEFAULT_AMOUNT_OF_ORDER_GROSS)));
    }

    @Test
    @Transactional
    void getNonExistingCart() throws Exception {
        // Get the cart
        restCartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart
        Cart updatedCart = cartRepository.findById(cart.getId()).get();
        // Disconnect from session so that the updates on updatedCart are not directly saved in db
        em.detach(updatedCart);
        updatedCart
            .amountOfCartNet(UPDATED_AMOUNT_OF_CART_NET)
            .amountOfCartGross(UPDATED_AMOUNT_OF_CART_GROSS)
            .amountOfShipmentNet(UPDATED_AMOUNT_OF_SHIPMENT_NET)
            .amountOfShipmentGross(UPDATED_AMOUNT_OF_SHIPMENT_GROSS)
            .amountOfOrderNet(UPDATED_AMOUNT_OF_ORDER_NET)
            .amountOfOrderGross(UPDATED_AMOUNT_OF_ORDER_GROSS);
        CartDTO cartDTO = cartMapper.toDto(updatedCart);

        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getAmountOfCartNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_CART_NET);
        assertThat(testCart.getAmountOfCartGross()).isEqualByComparingTo(UPDATED_AMOUNT_OF_CART_GROSS);
        assertThat(testCart.getAmountOfShipmentNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_SHIPMENT_NET);
        assertThat(testCart.getAmountOfShipmentGross()).isEqualByComparingTo(UPDATED_AMOUNT_OF_SHIPMENT_GROSS);
        assertThat(testCart.getAmountOfOrderNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_ORDER_NET);
        assertThat(testCart.getAmountOfOrderGross()).isEqualByComparingTo(UPDATED_AMOUNT_OF_ORDER_GROSS);
    }

    @Test
    @Transactional
    void putNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCartWithPatch() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart using partial update
        Cart partialUpdatedCart = new Cart();
        partialUpdatedCart.setId(cart.getId());

        partialUpdatedCart
            .amountOfCartNet(UPDATED_AMOUNT_OF_CART_NET)
            .amountOfShipmentNet(UPDATED_AMOUNT_OF_SHIPMENT_NET)
            .amountOfOrderNet(UPDATED_AMOUNT_OF_ORDER_NET);

        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCart))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getAmountOfCartNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_CART_NET);
        assertThat(testCart.getAmountOfCartGross()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_CART_GROSS);
        assertThat(testCart.getAmountOfShipmentNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_SHIPMENT_NET);
        assertThat(testCart.getAmountOfShipmentGross()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_SHIPMENT_GROSS);
        assertThat(testCart.getAmountOfOrderNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_ORDER_NET);
        assertThat(testCart.getAmountOfOrderGross()).isEqualByComparingTo(DEFAULT_AMOUNT_OF_ORDER_GROSS);
    }

    @Test
    @Transactional
    void fullUpdateCartWithPatch() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeUpdate = cartRepository.findAll().size();

        // Update the cart using partial update
        Cart partialUpdatedCart = new Cart();
        partialUpdatedCart.setId(cart.getId());

        partialUpdatedCart
            .amountOfCartNet(UPDATED_AMOUNT_OF_CART_NET)
            .amountOfCartGross(UPDATED_AMOUNT_OF_CART_GROSS)
            .amountOfShipmentNet(UPDATED_AMOUNT_OF_SHIPMENT_NET)
            .amountOfShipmentGross(UPDATED_AMOUNT_OF_SHIPMENT_GROSS)
            .amountOfOrderNet(UPDATED_AMOUNT_OF_ORDER_NET)
            .amountOfOrderGross(UPDATED_AMOUNT_OF_ORDER_GROSS);

        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCart))
            )
            .andExpect(status().isOk());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
        Cart testCart = cartList.get(cartList.size() - 1);
        assertThat(testCart.getAmountOfCartNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_CART_NET);
        assertThat(testCart.getAmountOfCartGross()).isEqualByComparingTo(UPDATED_AMOUNT_OF_CART_GROSS);
        assertThat(testCart.getAmountOfShipmentNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_SHIPMENT_NET);
        assertThat(testCart.getAmountOfShipmentGross()).isEqualByComparingTo(UPDATED_AMOUNT_OF_SHIPMENT_GROSS);
        assertThat(testCart.getAmountOfOrderNet()).isEqualByComparingTo(UPDATED_AMOUNT_OF_ORDER_NET);
        assertThat(testCart.getAmountOfOrderGross()).isEqualByComparingTo(UPDATED_AMOUNT_OF_ORDER_GROSS);
    }

    @Test
    @Transactional
    void patchNonExistingCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cartDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCart() throws Exception {
        int databaseSizeBeforeUpdate = cartRepository.findAll().size();
        cart.setId(count.incrementAndGet());

        // Create the Cart
        CartDTO cartDTO = cartMapper.toDto(cart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCartMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cartDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cart in the database
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCart() throws Exception {
        // Initialize the database
        cartRepository.saveAndFlush(cart);

        int databaseSizeBeforeDelete = cartRepository.findAll().size();

        // Delete the cart
        restCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, cart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cart> cartList = cartRepository.findAll();
        assertThat(cartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
