package infinityshopping.service;

import infinityshopping.service.dto.OrderMainDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link infinityshopping.domain.OrderMain}.
 */
public interface OrderMainService {
    /**
     * Save a orderMain.
     *
     * @param orderMainDTO the entity to save.
     * @return the persisted entity.
     */
    OrderMainDTO save(OrderMainDTO orderMainDTO);

    /**
     * Updates a orderMain.
     *
     * @param orderMainDTO the entity to update.
     * @return the persisted entity.
     */
    OrderMainDTO update(OrderMainDTO orderMainDTO);

    /**
     * Partially updates a orderMain.
     *
     * @param orderMainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OrderMainDTO> partialUpdate(OrderMainDTO orderMainDTO);

    /**
     * Get all the orderMains.
     *
     * @return the list of entities.
     */
    List<OrderMainDTO> findAll();

    /**
     * Get the "id" orderMain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OrderMainDTO> findOne(Long id);

    /**
     * Delete the "id" orderMain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
