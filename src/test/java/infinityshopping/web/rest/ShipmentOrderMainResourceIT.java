package infinityshopping.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import infinityshopping.IntegrationTest;
import infinityshopping.domain.ShipmentOrderMain;
import infinityshopping.repository.ShipmentOrderMainRepository;
import infinityshopping.service.dto.ShipmentOrderMainDTO;
import infinityshopping.service.mapper.ShipmentOrderMainMapper;
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
 * Integration tests for the {@link ShipmentOrderMainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShipmentOrderMainResourceIT {

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

    private static final String ENTITY_API_URL = "/api/shipment-order-mains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShipmentOrderMainRepository shipmentOrderMainRepository;

    @Autowired
    private ShipmentOrderMainMapper shipmentOrderMainMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShipmentOrderMainMockMvc;

    private ShipmentOrderMain shipmentOrderMain;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentOrderMain createEntity(EntityManager em) {
        ShipmentOrderMain shipmentOrderMain = new ShipmentOrderMain()
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .street(DEFAULT_STREET)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY)
            .phoneToTheReceiver(DEFAULT_PHONE_TO_THE_RECEIVER)
            .firm(DEFAULT_FIRM)
            .taxNumber(DEFAULT_TAX_NUMBER);
        return shipmentOrderMain;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShipmentOrderMain createUpdatedEntity(EntityManager em) {
        ShipmentOrderMain shipmentOrderMain = new ShipmentOrderMain()
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .phoneToTheReceiver(UPDATED_PHONE_TO_THE_RECEIVER)
            .firm(UPDATED_FIRM)
            .taxNumber(UPDATED_TAX_NUMBER);
        return shipmentOrderMain;
    }

    @BeforeEach
    public void initTest() {
        shipmentOrderMain = createEntity(em);
    }

    @Test
    @Transactional
    void createShipmentOrderMain() throws Exception {
        int databaseSizeBeforeCreate = shipmentOrderMainRepository.findAll().size();
        // Create the ShipmentOrderMain
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);
        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeCreate + 1);
        ShipmentOrderMain testShipmentOrderMain = shipmentOrderMainList.get(shipmentOrderMainList.size() - 1);
        assertThat(testShipmentOrderMain.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testShipmentOrderMain.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testShipmentOrderMain.getStreet()).isEqualTo(DEFAULT_STREET);
        assertThat(testShipmentOrderMain.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testShipmentOrderMain.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testShipmentOrderMain.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testShipmentOrderMain.getPhoneToTheReceiver()).isEqualTo(DEFAULT_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentOrderMain.getFirm()).isEqualTo(DEFAULT_FIRM);
        assertThat(testShipmentOrderMain.getTaxNumber()).isEqualTo(DEFAULT_TAX_NUMBER);
    }

    @Test
    @Transactional
    void createShipmentOrderMainWithExistingId() throws Exception {
        // Create the ShipmentOrderMain with an existing ID
        shipmentOrderMain.setId(1L);
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        int databaseSizeBeforeCreate = shipmentOrderMainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFirstNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentOrderMainRepository.findAll().size();
        // set the field null
        shipmentOrderMain.setFirstName(null);

        // Create the ShipmentOrderMain, which fails.
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLastNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentOrderMainRepository.findAll().size();
        // set the field null
        shipmentOrderMain.setLastName(null);

        // Create the ShipmentOrderMain, which fails.
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStreetIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentOrderMainRepository.findAll().size();
        // set the field null
        shipmentOrderMain.setStreet(null);

        // Create the ShipmentOrderMain, which fails.
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPostalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentOrderMainRepository.findAll().size();
        // set the field null
        shipmentOrderMain.setPostalCode(null);

        // Create the ShipmentOrderMain, which fails.
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCityIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentOrderMainRepository.findAll().size();
        // set the field null
        shipmentOrderMain.setCity(null);

        // Create the ShipmentOrderMain, which fails.
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCountryIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentOrderMainRepository.findAll().size();
        // set the field null
        shipmentOrderMain.setCountry(null);

        // Create the ShipmentOrderMain, which fails.
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPhoneToTheReceiverIsRequired() throws Exception {
        int databaseSizeBeforeTest = shipmentOrderMainRepository.findAll().size();
        // set the field null
        shipmentOrderMain.setPhoneToTheReceiver(null);

        // Create the ShipmentOrderMain, which fails.
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShipmentOrderMains() throws Exception {
        // Initialize the database
        shipmentOrderMainRepository.saveAndFlush(shipmentOrderMain);

        // Get all the shipmentOrderMainList
        restShipmentOrderMainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shipmentOrderMain.getId().intValue())))
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
    void getShipmentOrderMain() throws Exception {
        // Initialize the database
        shipmentOrderMainRepository.saveAndFlush(shipmentOrderMain);

        // Get the shipmentOrderMain
        restShipmentOrderMainMockMvc
            .perform(get(ENTITY_API_URL_ID, shipmentOrderMain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shipmentOrderMain.getId().intValue()))
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
    void getNonExistingShipmentOrderMain() throws Exception {
        // Get the shipmentOrderMain
        restShipmentOrderMainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingShipmentOrderMain() throws Exception {
        // Initialize the database
        shipmentOrderMainRepository.saveAndFlush(shipmentOrderMain);

        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();

        // Update the shipmentOrderMain
        ShipmentOrderMain updatedShipmentOrderMain = shipmentOrderMainRepository.findById(shipmentOrderMain.getId()).get();
        // Disconnect from session so that the updates on updatedShipmentOrderMain are not directly saved in db
        em.detach(updatedShipmentOrderMain);
        updatedShipmentOrderMain
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .phoneToTheReceiver(UPDATED_PHONE_TO_THE_RECEIVER)
            .firm(UPDATED_FIRM)
            .taxNumber(UPDATED_TAX_NUMBER);
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(updatedShipmentOrderMain);

        restShipmentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentOrderMainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
        ShipmentOrderMain testShipmentOrderMain = shipmentOrderMainList.get(shipmentOrderMainList.size() - 1);
        assertThat(testShipmentOrderMain.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testShipmentOrderMain.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testShipmentOrderMain.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testShipmentOrderMain.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testShipmentOrderMain.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShipmentOrderMain.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testShipmentOrderMain.getPhoneToTheReceiver()).isEqualTo(UPDATED_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentOrderMain.getFirm()).isEqualTo(UPDATED_FIRM);
        assertThat(testShipmentOrderMain.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void putNonExistingShipmentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();
        shipmentOrderMain.setId(count.incrementAndGet());

        // Create the ShipmentOrderMain
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shipmentOrderMainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShipmentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();
        shipmentOrderMain.setId(count.incrementAndGet());

        // Create the ShipmentOrderMain
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShipmentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();
        shipmentOrderMain.setId(count.incrementAndGet());

        // Create the ShipmentOrderMain
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentOrderMainMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShipmentOrderMainWithPatch() throws Exception {
        // Initialize the database
        shipmentOrderMainRepository.saveAndFlush(shipmentOrderMain);

        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();

        // Update the shipmentOrderMain using partial update
        ShipmentOrderMain partialUpdatedShipmentOrderMain = new ShipmentOrderMain();
        partialUpdatedShipmentOrderMain.setId(shipmentOrderMain.getId());

        partialUpdatedShipmentOrderMain
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .taxNumber(UPDATED_TAX_NUMBER);

        restShipmentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentOrderMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipmentOrderMain))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
        ShipmentOrderMain testShipmentOrderMain = shipmentOrderMainList.get(shipmentOrderMainList.size() - 1);
        assertThat(testShipmentOrderMain.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testShipmentOrderMain.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testShipmentOrderMain.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testShipmentOrderMain.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testShipmentOrderMain.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShipmentOrderMain.getCountry()).isEqualTo(DEFAULT_COUNTRY);
        assertThat(testShipmentOrderMain.getPhoneToTheReceiver()).isEqualTo(DEFAULT_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentOrderMain.getFirm()).isEqualTo(DEFAULT_FIRM);
        assertThat(testShipmentOrderMain.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void fullUpdateShipmentOrderMainWithPatch() throws Exception {
        // Initialize the database
        shipmentOrderMainRepository.saveAndFlush(shipmentOrderMain);

        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();

        // Update the shipmentOrderMain using partial update
        ShipmentOrderMain partialUpdatedShipmentOrderMain = new ShipmentOrderMain();
        partialUpdatedShipmentOrderMain.setId(shipmentOrderMain.getId());

        partialUpdatedShipmentOrderMain
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .street(UPDATED_STREET)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY)
            .phoneToTheReceiver(UPDATED_PHONE_TO_THE_RECEIVER)
            .firm(UPDATED_FIRM)
            .taxNumber(UPDATED_TAX_NUMBER);

        restShipmentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShipmentOrderMain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShipmentOrderMain))
            )
            .andExpect(status().isOk());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
        ShipmentOrderMain testShipmentOrderMain = shipmentOrderMainList.get(shipmentOrderMainList.size() - 1);
        assertThat(testShipmentOrderMain.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testShipmentOrderMain.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testShipmentOrderMain.getStreet()).isEqualTo(UPDATED_STREET);
        assertThat(testShipmentOrderMain.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testShipmentOrderMain.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testShipmentOrderMain.getCountry()).isEqualTo(UPDATED_COUNTRY);
        assertThat(testShipmentOrderMain.getPhoneToTheReceiver()).isEqualTo(UPDATED_PHONE_TO_THE_RECEIVER);
        assertThat(testShipmentOrderMain.getFirm()).isEqualTo(UPDATED_FIRM);
        assertThat(testShipmentOrderMain.getTaxNumber()).isEqualTo(UPDATED_TAX_NUMBER);
    }

    @Test
    @Transactional
    void patchNonExistingShipmentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();
        shipmentOrderMain.setId(count.incrementAndGet());

        // Create the ShipmentOrderMain
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShipmentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shipmentOrderMainDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShipmentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();
        shipmentOrderMain.setId(count.incrementAndGet());

        // Create the ShipmentOrderMain
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShipmentOrderMain() throws Exception {
        int databaseSizeBeforeUpdate = shipmentOrderMainRepository.findAll().size();
        shipmentOrderMain.setId(count.incrementAndGet());

        // Create the ShipmentOrderMain
        ShipmentOrderMainDTO shipmentOrderMainDTO = shipmentOrderMainMapper.toDto(shipmentOrderMain);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShipmentOrderMainMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shipmentOrderMainDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShipmentOrderMain in the database
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShipmentOrderMain() throws Exception {
        // Initialize the database
        shipmentOrderMainRepository.saveAndFlush(shipmentOrderMain);

        int databaseSizeBeforeDelete = shipmentOrderMainRepository.findAll().size();

        // Delete the shipmentOrderMain
        restShipmentOrderMainMockMvc
            .perform(delete(ENTITY_API_URL_ID, shipmentOrderMain.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShipmentOrderMain> shipmentOrderMainList = shipmentOrderMainRepository.findAll();
        assertThat(shipmentOrderMainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
