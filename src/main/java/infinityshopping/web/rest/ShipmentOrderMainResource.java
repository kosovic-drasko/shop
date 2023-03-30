package infinityshopping.web.rest;

import infinityshopping.repository.ShipmentOrderMainRepository;
import infinityshopping.service.ShipmentOrderMainService;
import infinityshopping.service.dto.ShipmentOrderMainDTO;
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
 * REST controller for managing {@link infinityshopping.domain.ShipmentOrderMain}.
 */
@RestController
@RequestMapping("/api")
public class ShipmentOrderMainResource {

    private final Logger log = LoggerFactory.getLogger(ShipmentOrderMainResource.class);

    private static final String ENTITY_NAME = "shipmentOrderMain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShipmentOrderMainService shipmentOrderMainService;

    private final ShipmentOrderMainRepository shipmentOrderMainRepository;

    public ShipmentOrderMainResource(
        ShipmentOrderMainService shipmentOrderMainService,
        ShipmentOrderMainRepository shipmentOrderMainRepository
    ) {
        this.shipmentOrderMainService = shipmentOrderMainService;
        this.shipmentOrderMainRepository = shipmentOrderMainRepository;
    }

    /**
     * {@code POST  /shipment-order-mains} : Create a new shipmentOrderMain.
     *
     * @param shipmentOrderMainDTO the shipmentOrderMainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shipmentOrderMainDTO, or with status {@code 400 (Bad Request)} if the shipmentOrderMain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shipment-order-mains")
    public ResponseEntity<ShipmentOrderMainDTO> createShipmentOrderMain(@Valid @RequestBody ShipmentOrderMainDTO shipmentOrderMainDTO)
        throws URISyntaxException {
        log.debug("REST request to save ShipmentOrderMain : {}", shipmentOrderMainDTO);
        if (shipmentOrderMainDTO.getId() != null) {
            throw new BadRequestAlertException("A new shipmentOrderMain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShipmentOrderMainDTO result = shipmentOrderMainService.save(shipmentOrderMainDTO);
        return ResponseEntity
            .created(new URI("/api/shipment-order-mains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shipment-order-mains/:id} : Updates an existing shipmentOrderMain.
     *
     * @param id the id of the shipmentOrderMainDTO to save.
     * @param shipmentOrderMainDTO the shipmentOrderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentOrderMainDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentOrderMainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shipmentOrderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shipment-order-mains/{id}")
    public ResponseEntity<ShipmentOrderMainDTO> updateShipmentOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShipmentOrderMainDTO shipmentOrderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShipmentOrderMain : {}, {}", id, shipmentOrderMainDTO);
        if (shipmentOrderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentOrderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentOrderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShipmentOrderMainDTO result = shipmentOrderMainService.update(shipmentOrderMainDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentOrderMainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shipment-order-mains/:id} : Partial updates given fields of an existing shipmentOrderMain, field will ignore if it is null
     *
     * @param id the id of the shipmentOrderMainDTO to save.
     * @param shipmentOrderMainDTO the shipmentOrderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shipmentOrderMainDTO,
     * or with status {@code 400 (Bad Request)} if the shipmentOrderMainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shipmentOrderMainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shipmentOrderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shipment-order-mains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ShipmentOrderMainDTO> partialUpdateShipmentOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShipmentOrderMainDTO shipmentOrderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShipmentOrderMain partially : {}, {}", id, shipmentOrderMainDTO);
        if (shipmentOrderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shipmentOrderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shipmentOrderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShipmentOrderMainDTO> result = shipmentOrderMainService.partialUpdate(shipmentOrderMainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shipmentOrderMainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shipment-order-mains} : get all the shipmentOrderMains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shipmentOrderMains in body.
     */
    @GetMapping("/shipment-order-mains")
    public List<ShipmentOrderMainDTO> getAllShipmentOrderMains() {
        log.debug("REST request to get all ShipmentOrderMains");
        return shipmentOrderMainService.findAll();
    }

    /**
     * {@code GET  /shipment-order-mains/:id} : get the "id" shipmentOrderMain.
     *
     * @param id the id of the shipmentOrderMainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shipmentOrderMainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shipment-order-mains/{id}")
    public ResponseEntity<ShipmentOrderMainDTO> getShipmentOrderMain(@PathVariable Long id) {
        log.debug("REST request to get ShipmentOrderMain : {}", id);
        Optional<ShipmentOrderMainDTO> shipmentOrderMainDTO = shipmentOrderMainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shipmentOrderMainDTO);
    }

    /**
     * {@code DELETE  /shipment-order-mains/:id} : delete the "id" shipmentOrderMain.
     *
     * @param id the id of the shipmentOrderMainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shipment-order-mains/{id}")
    public ResponseEntity<Void> deleteShipmentOrderMain(@PathVariable Long id) {
        log.debug("REST request to delete ShipmentOrderMain : {}", id);
        shipmentOrderMainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
