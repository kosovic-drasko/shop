package infinityshopping.web.rest;

import infinityshopping.repository.PaymentCartRepository;
import infinityshopping.service.PaymentCartService;
import infinityshopping.service.dto.PaymentCartDTO;
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
 * REST controller for managing {@link infinityshopping.domain.PaymentCart}.
 */
@RestController
@RequestMapping("/api")
public class PaymentCartResource {

    private final Logger log = LoggerFactory.getLogger(PaymentCartResource.class);

    private static final String ENTITY_NAME = "paymentCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentCartService paymentCartService;

    private final PaymentCartRepository paymentCartRepository;

    public PaymentCartResource(PaymentCartService paymentCartService, PaymentCartRepository paymentCartRepository) {
        this.paymentCartService = paymentCartService;
        this.paymentCartRepository = paymentCartRepository;
    }

    /**
     * {@code POST  /payment-carts} : Create a new paymentCart.
     *
     * @param paymentCartDTO the paymentCartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentCartDTO, or with status {@code 400 (Bad Request)} if the paymentCart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-carts")
    public ResponseEntity<PaymentCartDTO> createPaymentCart(@Valid @RequestBody PaymentCartDTO paymentCartDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentCart : {}", paymentCartDTO);
        if (paymentCartDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentCartDTO result = paymentCartService.save(paymentCartDTO);
        return ResponseEntity
            .created(new URI("/api/payment-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-carts/:id} : Updates an existing paymentCart.
     *
     * @param id the id of the paymentCartDTO to save.
     * @param paymentCartDTO the paymentCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentCartDTO,
     * or with status {@code 400 (Bad Request)} if the paymentCartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-carts/{id}")
    public ResponseEntity<PaymentCartDTO> updatePaymentCart(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentCartDTO paymentCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentCart : {}, {}", id, paymentCartDTO);
        if (paymentCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentCartDTO result = paymentCartService.update(paymentCartDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-carts/:id} : Partial updates given fields of an existing paymentCart, field will ignore if it is null
     *
     * @param id the id of the paymentCartDTO to save.
     * @param paymentCartDTO the paymentCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentCartDTO,
     * or with status {@code 400 (Bad Request)} if the paymentCartDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentCartDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-carts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentCartDTO> partialUpdatePaymentCart(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentCartDTO paymentCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentCart partially : {}, {}", id, paymentCartDTO);
        if (paymentCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentCartDTO> result = paymentCartService.partialUpdate(paymentCartDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentCartDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-carts} : get all the paymentCarts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentCarts in body.
     */
    @GetMapping("/payment-carts")
    public List<PaymentCartDTO> getAllPaymentCarts() {
        log.debug("REST request to get all PaymentCarts");
        return paymentCartService.findAll();
    }

    /**
     * {@code GET  /payment-carts/:id} : get the "id" paymentCart.
     *
     * @param id the id of the paymentCartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentCartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-carts/{id}")
    public ResponseEntity<PaymentCartDTO> getPaymentCart(@PathVariable Long id) {
        log.debug("REST request to get PaymentCart : {}", id);
        Optional<PaymentCartDTO> paymentCartDTO = paymentCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentCartDTO);
    }

    /**
     * {@code DELETE  /payment-carts/:id} : delete the "id" paymentCart.
     *
     * @param id the id of the paymentCartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-carts/{id}")
    public ResponseEntity<Void> deletePaymentCart(@PathVariable Long id) {
        log.debug("REST request to delete PaymentCart : {}", id);
        paymentCartService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
