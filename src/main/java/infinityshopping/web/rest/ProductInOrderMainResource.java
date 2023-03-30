package infinityshopping.web.rest;

import infinityshopping.repository.ProductInOrderMainRepository;
import infinityshopping.service.ProductInOrderMainService;
import infinityshopping.service.dto.ProductInOrderMainDTO;
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
 * REST controller for managing {@link infinityshopping.domain.ProductInOrderMain}.
 */
@RestController
@RequestMapping("/api")
public class ProductInOrderMainResource {

    private final Logger log = LoggerFactory.getLogger(ProductInOrderMainResource.class);

    private static final String ENTITY_NAME = "productInOrderMain";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductInOrderMainService productInOrderMainService;

    private final ProductInOrderMainRepository productInOrderMainRepository;

    public ProductInOrderMainResource(
        ProductInOrderMainService productInOrderMainService,
        ProductInOrderMainRepository productInOrderMainRepository
    ) {
        this.productInOrderMainService = productInOrderMainService;
        this.productInOrderMainRepository = productInOrderMainRepository;
    }

    /**
     * {@code POST  /product-in-order-mains} : Create a new productInOrderMain.
     *
     * @param productInOrderMainDTO the productInOrderMainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productInOrderMainDTO, or with status {@code 400 (Bad Request)} if the productInOrderMain has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-in-order-mains")
    public ResponseEntity<ProductInOrderMainDTO> createProductInOrderMain(@RequestBody ProductInOrderMainDTO productInOrderMainDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProductInOrderMain : {}", productInOrderMainDTO);
        if (productInOrderMainDTO.getId() != null) {
            throw new BadRequestAlertException("A new productInOrderMain cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductInOrderMainDTO result = productInOrderMainService.save(productInOrderMainDTO);
        return ResponseEntity
            .created(new URI("/api/product-in-order-mains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-in-order-mains/:id} : Updates an existing productInOrderMain.
     *
     * @param id the id of the productInOrderMainDTO to save.
     * @param productInOrderMainDTO the productInOrderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInOrderMainDTO,
     * or with status {@code 400 (Bad Request)} if the productInOrderMainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productInOrderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-in-order-mains/{id}")
    public ResponseEntity<ProductInOrderMainDTO> updateProductInOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductInOrderMainDTO productInOrderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductInOrderMain : {}, {}", id, productInOrderMainDTO);
        if (productInOrderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productInOrderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productInOrderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductInOrderMainDTO result = productInOrderMainService.update(productInOrderMainDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productInOrderMainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-in-order-mains/:id} : Partial updates given fields of an existing productInOrderMain, field will ignore if it is null
     *
     * @param id the id of the productInOrderMainDTO to save.
     * @param productInOrderMainDTO the productInOrderMainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInOrderMainDTO,
     * or with status {@code 400 (Bad Request)} if the productInOrderMainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productInOrderMainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productInOrderMainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-in-order-mains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductInOrderMainDTO> partialUpdateProductInOrderMain(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductInOrderMainDTO productInOrderMainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductInOrderMain partially : {}, {}", id, productInOrderMainDTO);
        if (productInOrderMainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productInOrderMainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productInOrderMainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductInOrderMainDTO> result = productInOrderMainService.partialUpdate(productInOrderMainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productInOrderMainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-in-order-mains} : get all the productInOrderMains.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productInOrderMains in body.
     */
    @GetMapping("/product-in-order-mains")
    public List<ProductInOrderMainDTO> getAllProductInOrderMains() {
        log.debug("REST request to get all ProductInOrderMains");
        return productInOrderMainService.findAll();
    }

    /**
     * {@code GET  /product-in-order-mains/:id} : get the "id" productInOrderMain.
     *
     * @param id the id of the productInOrderMainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productInOrderMainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-in-order-mains/{id}")
    public ResponseEntity<ProductInOrderMainDTO> getProductInOrderMain(@PathVariable Long id) {
        log.debug("REST request to get ProductInOrderMain : {}", id);
        Optional<ProductInOrderMainDTO> productInOrderMainDTO = productInOrderMainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productInOrderMainDTO);
    }

    /**
     * {@code DELETE  /product-in-order-mains/:id} : delete the "id" productInOrderMain.
     *
     * @param id the id of the productInOrderMainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-in-order-mains/{id}")
    public ResponseEntity<Void> deleteProductInOrderMain(@PathVariable Long id) {
        log.debug("REST request to delete ProductInOrderMain : {}", id);
        productInOrderMainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
