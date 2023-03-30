package infinityshopping.service;

import infinityshopping.service.dto.ProductInOrderMainDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link infinityshopping.domain.ProductInOrderMain}.
 */
public interface ProductInOrderMainService {
    /**
     * Save a productInOrderMain.
     *
     * @param productInOrderMainDTO the entity to save.
     * @return the persisted entity.
     */
    ProductInOrderMainDTO save(ProductInOrderMainDTO productInOrderMainDTO);

    /**
     * Updates a productInOrderMain.
     *
     * @param productInOrderMainDTO the entity to update.
     * @return the persisted entity.
     */
    ProductInOrderMainDTO update(ProductInOrderMainDTO productInOrderMainDTO);

    /**
     * Partially updates a productInOrderMain.
     *
     * @param productInOrderMainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProductInOrderMainDTO> partialUpdate(ProductInOrderMainDTO productInOrderMainDTO);

    /**
     * Get all the productInOrderMains.
     *
     * @return the list of entities.
     */
    List<ProductInOrderMainDTO> findAll();

    /**
     * Get the "id" productInOrderMain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProductInOrderMainDTO> findOne(Long id);

    /**
     * Delete the "id" productInOrderMain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
