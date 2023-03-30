package infinityshopping.web.rest;

import infinityshopping.repository.ShipmentCartRepository;
import infinityshopping.service.ShipmentCartService;
import infinityshopping.service.dto.ShipmentCartDTO;
import infinityshopping.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link infinityshopping.domain.ShipmentCart}.
 */
@RestController
@RequestMapping("/api")
public class ShipmentCartResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentCartResource.class);

    private static final String ENTITY_NAME = "shipmentCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipmentCartService shipmentCartService;

    private final ShipmentCartRepository shipmentCartRepository;

    public ShipmentCartResource(ShipmentCartService shipmentCartService, ShipmentCartRepository shipmentCartRepository) {
        this.shipmentCartService = shipmentCartService;
        this.shipmentCartRepository = shipmentCartRepository;
    }

    /**
     * {@code POST  /shipment-carts} : Create a new shipmentCart.
     *
     * @param shipmentCartDTO the shipmentCartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipmentCartDTO, or with status {@code 400 (Bad Request)} if the shipmentCart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipment-carts")
    public ResponseEntity<ShipmentCartDTO> createShipmentCart(@Valid @RequestBody ShipmentCartDTO shipmentCartDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShipmentCart : {}", shipmentCartDTO);
        if (shipmentCartDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentCartDTO result = shipmentCartService.save(shipmentCartDTO);
        return ResponseEntity
            .created(new URI("/api/shipment-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipment-carts/:id} : Updates an existing shipmentCart.
     *
     * @param id the id of the shipmentCartDTO to save.
     * @param shipmentCartDTO the shipmentCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentCartDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentCartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipmentCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipment-carts/{id}")
    public ResponseEntity<ShipmentCartDTO> updateShipmentCart(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShipmentCartDTO shipmentCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipmentCart : {}, {}", id, shipmentCartDTO);
        if (shipmentCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShipmentCartDTO result = shipmentCartService.update(shipmentCartDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shipment-carts/:id} : Partial updates given fields of an existing shipmentCart, field will ignore if it is null
     *
     * @param id the id of the shipmentCartDTO to save.
     * @param shipmentCartDTO the shipmentCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentCartDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentCartDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipmentCartDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipmentCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shipment-carts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipmentCartDTO> partialUpdateShipmentCart(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShipmentCartDTO shipmentCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipmentCart partially : {}, {}", id, shipmentCartDTO);
        if (shipmentCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipmentCartDTO> result = shipmentCartService.partialUpdate(shipmentCartDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentCartDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipment-carts} : get all the shipmentCarts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipmentCarts in body.
     */
    @GetMapping("/shipment-carts")
    public List<ShipmentCartDTO> getAllShipmentCarts() {
        log.debug("REST request to get all ShipmentCarts");
        return shipmentCartService.findAll();
    }

    /**
     * {@code GET  /shipment-carts/:id} : get the "id" shipmentCart.
     *
     * @param id the id of the shipmentCartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipmentCartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipment-carts/{id}")
    public ResponseEntity<ShipmentCartDTO> getShipmentCart(@PathVariable Long id) {
        log.debug("REST request to get ShipmentCart : {}", id);
        Optional<ShipmentCartDTO> shipmentCartDTO = shipmentCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentCartDTO);
    }

    /**
     * {@code DELETE  /shipment-carts/:id} : delete the "id" shipmentCart.
     *
     * @param id the id of the shipmentCartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipment-carts/{id}")
    public ResponseEntity<Void> deleteShipmentCart(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentCart : {}", id);
        shipmentCartService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
