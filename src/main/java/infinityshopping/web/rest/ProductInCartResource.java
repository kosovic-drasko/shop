package infinityshopping.web.rest;

import infinityshopping.repository.ProductInCartRepository;
import infinityshopping.service.ProductInCartService;
import infinityshopping.service.dto.ProductInCartDTO;
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
 * REST controller for managing {@link infinityshopping.domain.ProductInCart}.
 */
@RestController
@RequestMapping("/api")
public class ProductInCartResource {

    private final Logger log = LoggerFactory.getLogger(ProductInCartResource.class);

    private static final String ENTITY_NAME = "productInCart";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProductInCartService productInCartService;

    private final ProductInCartRepository productInCartRepository;

    public ProductInCartResource(ProductInCartService productInCartService, ProductInCartRepository productInCartRepository) {
        this.productInCartService = productInCartService;
        this.productInCartRepository = productInCartRepository;
    }

    /**
     * {@code POST  /product-in-carts} : Create a new productInCart.
     *
     * @param productInCartDTO the productInCartDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new productInCartDTO, or with status {@code 400 (Bad Request)} if the productInCart has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/product-in-carts")
    public ResponseEntity<ProductInCartDTO> createProductInCart(@RequestBody ProductInCartDTO productInCartDTO) throws URISyntaxException {
        log.debug("REST request to save ProductInCart : {}", productInCartDTO);
        if (productInCartDTO.getId() != null) {
            throw new BadRequestAlertException("A new productInCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProductInCartDTO result = productInCartService.save(productInCartDTO);
        return ResponseEntity
            .created(new URI("/api/product-in-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /product-in-carts/:id} : Updates an existing productInCart.
     *
     * @param id the id of the productInCartDTO to save.
     * @param productInCartDTO the productInCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInCartDTO,
     * or with status {@code 400 (Bad Request)} if the productInCartDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the productInCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/product-in-carts/{id}")
    public ResponseEntity<ProductInCartDTO> updateProductInCart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductInCartDTO productInCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProductInCart : {}, {}", id, productInCartDTO);
        if (productInCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productInCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productInCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProductInCartDTO result = productInCartService.update(productInCartDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productInCartDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /product-in-carts/:id} : Partial updates given fields of an existing productInCart, field will ignore if it is null
     *
     * @param id the id of the productInCartDTO to save.
     * @param productInCartDTO the productInCartDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated productInCartDTO,
     * or with status {@code 400 (Bad Request)} if the productInCartDTO is not valid,
     * or with status {@code 404 (Not Found)} if the productInCartDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the productInCartDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/product-in-carts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProductInCartDTO> partialUpdateProductInCart(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProductInCartDTO productInCartDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProductInCart partially : {}, {}", id, productInCartDTO);
        if (productInCartDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, productInCartDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!productInCartRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProductInCartDTO> result = productInCartService.partialUpdate(productInCartDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, productInCartDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /product-in-carts} : get all the productInCarts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of productInCarts in body.
     */
    @GetMapping("/product-in-carts")
    public List<ProductInCartDTO> getAllProductInCarts() {
        log.debug("REST request to get all ProductInCarts");
        return productInCartService.findAll();
    }

    /**
     * {@code GET  /product-in-carts/:id} : get the "id" productInCart.
     *
     * @param id the id of the productInCartDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the productInCartDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/product-in-carts/{id}")
    public ResponseEntity<ProductInCartDTO> getProductInCart(@PathVariable Long id) {
        log.debug("REST request to get ProductInCart : {}", id);
        Optional<ProductInCartDTO> productInCartDTO = productInCartService.findOne(id);
        return ResponseUtil.wrapOrNotFound(productInCartDTO);
    }

    /**
     * {@code DELETE  /product-in-carts/:id} : delete the "id" productInCart.
     *
     * @param id the id of the productInCartDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/product-in-carts/{id}")
    public ResponseEntity<Void> deleteProductInCart(@PathVariable Long id) {
        log.debug("REST request to delete ProductInCart : {}", id);
        productInCartService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
