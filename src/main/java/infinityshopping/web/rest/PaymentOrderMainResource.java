package infinityshopping.web.rest;

import infinityshopping.repository.PaymentOrderMainRepository;
import infinityshopping.service.PaymentOrderMainService;
import infinityshopping.service.dto.PaymentOrderMainDTO;
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
 * REST controller for managing {@link infinityshopping.domain.PaymentOrderMain}.
 */
@RestController
@RequestMapping("/api")
public class PaymentOrderMainResource {

    private final Logger log = LoggerFactory.getLogger(PaymentOrderMainResource.class);

    private static final String ENTITY_NAME = "paymentOrderMain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentOrderMainService paymentOrderMainService;

    private final PaymentOrderMainRepository paymentOrderMainRepository;

    public PaymentOrderMainResource(
        PaymentOrderMainService paymentOrderMainService,
        PaymentOrderMainRepository paymentOrderMainRepository
    ) {
        this.paymentOrderMainService = paymentOrderMainService;
        this.paymentOrderMainRepository = paymentOrderMainRepository;
    }

    /**
     * {@code POST  /payment-order-mains} : Create a new paymentOrderMain.
     *
     * @param paymentOrderMainDTO the paymentOrderMainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentOrderMainDTO, or with status {@code 400 (Bad Request)} if the paymentOrderMain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-order-mains")
    public ResponseEntity<PaymentOrderMainDTO> createPaymentOrderMain(@Valid @RequestBody PaymentOrderMainDTO paymentOrderMainDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentOrderMain : {}", paymentOrderMainDTO);
        if (paymentOrderMainDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentOrderMain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentOrderMainDTO result = paymentOrderMainService.save(paymentOrderMainDTO);
        return ResponseEntity
            .created(new URI("/api/payment-order-mains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-order-mains/:id} : Updates an existing paymentOrderMain.
     *
     * @param id the id of the paymentOrderMainDTO to save.
     * @param paymentOrderMainDTO the paymentOrderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentOrderMainDTO,
     * or with status {@code 400 (Bad Request)} if the paymentOrderMainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentOrderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-order-mains/{id}")
    public ResponseEntity<PaymentOrderMainDTO> updatePaymentOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentOrderMainDTO paymentOrderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentOrderMain : {}, {}", id, paymentOrderMainDTO);
        if (paymentOrderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentOrderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentOrderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentOrderMainDTO result = paymentOrderMainService.update(paymentOrderMainDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentOrderMainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-order-mains/:id} : Partial updates given fields of an existing paymentOrderMain, field will ignore if it is null
     *
     * @param id the id of the paymentOrderMainDTO to save.
     * @param paymentOrderMainDTO the paymentOrderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentOrderMainDTO,
     * or with status {@code 400 (Bad Request)} if the paymentOrderMainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentOrderMainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentOrderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-order-mains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentOrderMainDTO> partialUpdatePaymentOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentOrderMainDTO paymentOrderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentOrderMain partially : {}, {}", id, paymentOrderMainDTO);
        if (paymentOrderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentOrderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentOrderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentOrderMainDTO> result = paymentOrderMainService.partialUpdate(paymentOrderMainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentOrderMainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-order-mains} : get all the paymentOrderMains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentOrderMains in body.
     */
    @GetMapping("/payment-order-mains")
    public List<PaymentOrderMainDTO> getAllPaymentOrderMains() {
        log.debug("REST request to get all PaymentOrderMains");
        return paymentOrderMainService.findAll();
    }

    /**
     * {@code GET  /payment-order-mains/:id} : get the "id" paymentOrderMain.
     *
     * @param id the id of the paymentOrderMainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentOrderMainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-order-mains/{id}")
    public ResponseEntity<PaymentOrderMainDTO> getPaymentOrderMain(@PathVariable Long id) {
        log.debug("REST request to get PaymentOrderMain : {}", id);
        Optional<PaymentOrderMainDTO> paymentOrderMainDTO = paymentOrderMainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentOrderMainDTO);
    }

    /**
     * {@code DELETE  /payment-order-mains/:id} : delete the "id" paymentOrderMain.
     *
     * @param id the id of the paymentOrderMainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-order-mains/{id}")
    public ResponseEntity<Void> deletePaymentOrderMain(@PathVariable Long id) {
        log.debug("REST request to delete PaymentOrderMain : {}", id);
        paymentOrderMainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
