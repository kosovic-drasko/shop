package infinityshopping.service;

import infinityshopping.service.dto.ProductInCartDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link infinityshopping.domain.ProductInCart}.
 */
public interface ProductInCartService {
    /**
     * Save a productInCart.
     *
     * @param productInCartDTO the entity to save.
     * @return the persisted entity.
     */
    ProductInCartDTO save(ProductInCartDTO productInCartDTO);

    /**
     * Updates a productInCart.
     *
     * @param productInCartDTO the entity to update.
     * @return the persisted entity.
     */
    ProductInCartDTO update(ProductInCartDTO productInCartDTO);

    /**
     * Partially updates a productInCart.
     *
     * @param productInCartDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductInCartDTO> partialUpdate(ProductInCartDTO productInCartDTO);

    /**
     * Get all the productInCarts.
     *
     * @return the list of entities.
     */
    List<ProductInCartDTO> findAll();

    /**
     * Get the "id" productInCart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductInCartDTO> findOne(Long id);

    /**
     * Delete the "id" productInCart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
