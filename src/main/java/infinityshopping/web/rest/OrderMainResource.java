package infinityshopping.web.rest;

import infinityshopping.repository.OrderMainRepository;
import infinityshopping.service.OrderMainService;
import infinityshopping.service.dto.OrderMainDTO;
import infinityshopping.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link infinityshopping.domain.OrderMain}.
 */
@RestController
@RequestMapping("/api")
public class OrderMainResource {

    private final Logger log = LoggerFactory.getLogger(OrderMainResource.class);

    private static final String ENTITY_NAME = "orderMain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderMainService orderMainService;

    private final OrderMainRepository orderMainRepository;

    public OrderMainResource(OrderMainService orderMainService, OrderMainRepository orderMainRepository) {
        this.orderMainService = orderMainService;
        this.orderMainRepository = orderMainRepository;
    }

    /**
     * {@code POST  /order-mains} : Create a new orderMain.
     *
     * @param orderMainDTO the orderMainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderMainDTO, or with status {@code 400 (Bad Request)} if the orderMain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-mains")
    public ResponseEntity<OrderMainDTO> createOrderMain(@RequestBody OrderMainDTO orderMainDTO) throws URISyntaxException {
        log.debug("REST request to save OrderMain : {}", orderMainDTO);
        if (orderMainDTO.getId() != null) {
            throw new BadRequestAlertException("A new orderMain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderMainDTO result = orderMainService.save(orderMainDTO);
        return ResponseEntity
            .created(new URI("/api/order-mains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-mains/:id} : Updates an existing orderMain.
     *
     * @param id the id of the orderMainDTO to save.
     * @param orderMainDTO the orderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderMainDTO,
     * or with status {@code 400 (Bad Request)} if the orderMainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-mains/{id}")
    public ResponseEntity<OrderMainDTO> updateOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderMainDTO orderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update OrderMain : {}, {}", id, orderMainDTO);
        if (orderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OrderMainDTO result = orderMainService.update(orderMainDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderMainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /order-mains/:id} : Partial updates given fields of an existing orderMain, field will ignore if it is null
     *
     * @param id the id of the orderMainDTO to save.
     * @param orderMainDTO the orderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderMainDTO,
     * or with status {@code 400 (Bad Request)} if the orderMainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the orderMainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the orderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/order-mains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<OrderMainDTO> partialUpdateOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody OrderMainDTO orderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update OrderMain partially : {}, {}", id, orderMainDTO);
        if (orderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, orderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!orderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OrderMainDTO> result = orderMainService.partialUpdate(orderMainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderMainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /order-mains} : get all the orderMains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderMains in body.
     */
    @GetMapping("/order-mains")
    public List<OrderMainDTO> getAllOrderMains() {
        log.debug("REST request to get all OrderMains");
        return orderMainService.findAll();
    }

    /**
     * {@code GET  /order-mains/:id} : get the "id" orderMain.
     *
     * @param id the id of the orderMainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderMainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-mains/{id}")
    public ResponseEntity<OrderMainDTO> getOrderMain(@PathVariable Long id) {
        log.debug("REST request to get OrderMain : {}", id);
        Optional<OrderMainDTO> orderMainDTO = orderMainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderMainDTO);
    }

    /**
     * {@code DELETE  /order-mains/:id} : delete the "id" orderMain.
     *
     * @param id the id of the orderMainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-mains/{id}")
    public ResponseEntity<Void> deleteOrderMain(@PathVariable Long id) {
        log.debug("REST request to delete OrderMain : {}", id);
        orderMainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
