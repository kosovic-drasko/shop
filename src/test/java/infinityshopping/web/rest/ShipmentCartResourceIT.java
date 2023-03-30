package infinityshopping.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import infinityshopping.IntegrationTest;
import infinityshopping.domain.ShipmentCart;
import infinityshopping.repository.ShipmentCartRepository;
import infinityshopping.service.dto.ShipmentCartDTO;
import infinityshopping.service.mapper.ShipmentCartMapper;
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
 * Integration tests for the {@link ShipmentCartResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipmentCartResourceIT {

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STREET = "AAAAAAAAAA";
    private static final String UPDATED_STREET = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_TO_THE_RECEIVER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_TO_THE_RECEIVER = "BBBBBBBBBB";

    private static final String DEFAULT_FIRM = "AAAAAAAAAA";
    private static final String UPDATED_FIRM = "BBBBBBBBBB";

    private static final String DEFAULT_TAX_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_TAX_NUMBER = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shipment-carts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShipmentCartRepository shipmentCartRepository;

    @Autowired
    private ShipmentCartMapper shipmentCartMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentCartMockMvc;

    private ShipmentCart shipmentCart;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentCart createEntity(EntityManager em) {
        ShipmentCart shipmentCart = new ShipmentCart()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .street(DEFAULT_STREET)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .phoneToTheReceiver(DEFAULT_PHONE_TO_THE_RECEIVER)
            .firm(DEFAULT_FIRM)
            .taxNumber(DEFAULT_TAX_NUMBER);
        return shipmentCart;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentCart createUpdatedEntity(EntityManager em) {
        ShipmentCart shipmentCart = new ShipmentCart()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .phoneToTheReceiver(UPDATED_PHONE_TO_THE_RECEIVER)
            .firm(UPDATED_FIRM)
            .taxNumber(UPDATED_TAX_NUMBER);
        return shipmentCart;
    }

    @BeforeEach
    public void initTest() {
        shipmentCart = createEntity(em);
    }

    @Test
    @Transactional
    void createShipmentCart() throws Exception {
        int databaseSizeBeforeCreate = shipmentCartRepository.findAll().size();
        // Create the ShipmentCart
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);
        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentCart testShipmentCart = shipmentCartList.get(shipmentCartList.size() - 1);
        assertThat(testShipmentCart.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testShipmentCart.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testShipmentCart.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testShipmentCart.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testShipmentCart.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShipmentCart.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testShipmentCart.getPhoneToTheReceiver()).isEqualTo(DEFAULT_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentCart.getFirm()).isEqualTo(DEFAULT_FIRM);
        assertThat(testShipmentCart.getTaxNumber()).isEqualTo(DEFAULT_TAX_NUMBER);
    }

    @Test
    @Transactional
    void createShipmentCartWithExistingId() throws Exception {
        // Create the ShipmentCart with an existing ID
        shipmentCart.setId(1L);
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        int databaseSizeBeforeCreate = shipmentCartRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentCartRepository.findAll().size();
        // set the field null
        shipmentCart.setFirstName(null);

        // Create the ShipmentCart, which fails.
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentCartRepository.findAll().size();
        // set the field null
        shipmentCart.setLastName(null);

        // Create the ShipmentCart, which fails.
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentCartRepository.findAll().size();
        // set the field null
        shipmentCart.setStreet(null);

        // Create the ShipmentCart, which fails.
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentCartRepository.findAll().size();
        // set the field null
        shipmentCart.setPostalCode(null);

        // Create the ShipmentCart, which fails.
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentCartRepository.findAll().size();
        // set the field null
        shipmentCart.setCity(null);

        // Create the ShipmentCart, which fails.
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentCartRepository.findAll().size();
        // set the field null
        shipmentCart.setCountry(null);

        // Create the ShipmentCart, which fails.
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneToTheReceiverIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentCartRepository.findAll().size();
        // set the field null
        shipmentCart.setPhoneToTheReceiver(null);

        // Create the ShipmentCart, which fails.
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        restShipmentCartMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShipmentCarts() throws Exception {
        // Initialize the database
        shipmentCartRepository.saveAndFlush(shipmentCart);

        // Get all the shipmentCartList
        restShipmentCartMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].street").value(hasItem(DEFAULT_STREET)))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)))
            .andExpect(jsonPath("$.[*].phoneToTheReceiver").value(hasItem(DEFAULT_PHONE_TO_THE_RECEIVER)))
            .andExpect(jsonPath("$.[*].firm").value(hasItem(DEFAULT_FIRM)))
            .andExpect(jsonPath("$.[*].taxNumber").value(hasItem(DEFAULT_TAX_NUMBER)));
    }

    @Test
    @Transactional
    void getShipmentCart() throws Exception {
        // Initialize the database
        shipmentCartRepository.saveAndFlush(shipmentCart);

        // Get the shipmentCart
        restShipmentCartMockMvc
            .perform(get(ENTITY_API_URL_ID, shipmentCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentCart.getId().intValue()))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.street").value(DEFAULT_STREET))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY))
            .andExpect(jsonPath("$.phoneToTheReceiver").value(DEFAULT_PHONE_TO_THE_RECEIVER))
            .andExpect(jsonPath("$.firm").value(DEFAULT_FIRM))
            .andExpect(jsonPath("$.taxNumber").value(DEFAULT_TAX_NUMBER));
    }

    @Test
    @Transactional
    void getNonExistingShipmentCart() throws Exception {
        // Get the shipmentCart
        restShipmentCartMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShipmentCart() throws Exception {
        // Initialize the database
        shipmentCartRepository.saveAndFlush(shipmentCart);

        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();

        // Update the shipmentCart
        ShipmentCart updatedShipmentCart = shipmentCartRepository.findById(shipmentCart.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentCart are not directly saved in db
        em.detach(updatedShipmentCart);
        updatedShipmentCart
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .phoneToTheReceiver(UPDATED_PHONE_TO_THE_RECEIVER)
            .firm(UPDATED_FIRM)
            .taxNumber(UPDATED_TAX_NUMBER);
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(updatedShipmentCart);

        restShipmentCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
        ShipmentCart testShipmentCart = shipmentCartList.get(shipmentCartList.size() - 1);
        assertThat(testShipmentCart.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testShipmentCart.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testShipmentCart.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testShipmentCart.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testShipmentCart.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShipmentCart.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testShipmentCart.getPhoneToTheReceiver()).isEqualTo(UPDATED_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentCart.getFirm()).isEqualTo(UPDATED_FIRM);
        assertThat(testShipmentCart.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingShipmentCart() throws Exception {
        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();
        shipmentCart.setId(count.incrementAndGet());

        // Create the ShipmentCart
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentCartDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShipmentCart() throws Exception {
        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();
        shipmentCart.setId(count.incrementAndGet());

        // Create the ShipmentCart
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentCartMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShipmentCart() throws Exception {
        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();
        shipmentCart.setId(count.incrementAndGet());

        // Create the ShipmentCart
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentCartMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShipmentCartWithPatch() throws Exception {
        // Initialize the database
        shipmentCartRepository.saveAndFlush(shipmentCart);

        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();

        // Update the shipmentCart using partial update
        ShipmentCart partialUpdatedShipmentCart = new ShipmentCart();
        partialUpdatedShipmentCart.setId(shipmentCart.getId());

        partialUpdatedShipmentCart.firm(UPDATED_FIRM);

        restShipmentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipmentCart))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
        ShipmentCart testShipmentCart = shipmentCartList.get(shipmentCartList.size() - 1);
        assertThat(testShipmentCart.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testShipmentCart.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testShipmentCart.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testShipmentCart.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testShipmentCart.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShipmentCart.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testShipmentCart.getPhoneToTheReceiver()).isEqualTo(DEFAULT_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentCart.getFirm()).isEqualTo(UPDATED_FIRM);
        assertThat(testShipmentCart.getTaxNumber()).isEqualTo(DEFAULT_TAX_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateShipmentCartWithPatch() throws Exception {
        // Initialize the database
        shipmentCartRepository.saveAndFlush(shipmentCart);

        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();

        // Update the shipmentCart using partial update
        ShipmentCart partialUpdatedShipmentCart = new ShipmentCart();
        partialUpdatedShipmentCart.setId(shipmentCart.getId());

        partialUpdatedShipmentCart
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .phoneToTheReceiver(UPDATED_PHONE_TO_THE_RECEIVER)
            .firm(UPDATED_FIRM)
            .taxNumber(UPDATED_TAX_NUMBER);

        restShipmentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentCart.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipmentCart))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
        ShipmentCart testShipmentCart = shipmentCartList.get(shipmentCartList.size() - 1);
        assertThat(testShipmentCart.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testShipmentCart.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testShipmentCart.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testShipmentCart.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testShipmentCart.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShipmentCart.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testShipmentCart.getPhoneToTheReceiver()).isEqualTo(UPDATED_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentCart.getFirm()).isEqualTo(UPDATED_FIRM);
        assertThat(testShipmentCart.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingShipmentCart() throws Exception {
        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();
        shipmentCart.setId(count.incrementAndGet());

        // Create the ShipmentCart
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipmentCartDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShipmentCart() throws Exception {
        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();
        shipmentCart.setId(count.incrementAndGet());

        // Create the ShipmentCart
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentCartMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShipmentCart() throws Exception {
        int databaseSizeBeforeUpdate = shipmentCartRepository.findAll().size();
        shipmentCart.setId(count.incrementAndGet());

        // Create the ShipmentCart
        ShipmentCartDTO shipmentCartDTO = shipmentCartMapper.toDto(shipmentCart);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentCartMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentCartDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentCart in the database
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShipmentCart() throws Exception {
        // Initialize the database
        shipmentCartRepository.saveAndFlush(shipmentCart);

        int databaseSizeBeforeDelete = shipmentCartRepository.findAll().size();

        // Delete the shipmentCart
        restShipmentCartMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipmentCart.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShipmentCart> shipmentCartList = shipmentCartRepository.findAll();
        assertThat(shipmentCartList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
