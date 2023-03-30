package infinityshopping.service;

import infinityshopping.service.dto.ShipmentOrderMainDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link infinityshopping.domain.ShipmentOrderMain}.
 */
public interface ShipmentOrderMainService {
    /**
     * Save a shipmentOrderMain.
     *
     * @param shipmentOrderMainDTO the entity to save.
     * @return the persisted entity.
     */
    ShipmentOrderMainDTO save(ShipmentOrderMainDTO shipmentOrderMainDTO);

    /**
     * Updates a shipmentOrderMain.
     *
     * @param shipmentOrderMainDTO the entity to update.
     * @return the persisted entity.
     */
    ShipmentOrderMainDTO update(ShipmentOrderMainDTO shipmentOrderMainDTO);

    /**
     * Partially updates a shipmentOrderMain.
     *
     * @param shipmentOrderMainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ShipmentOrderMainDTO> partialUpdate(ShipmentOrderMainDTO shipmentOrderMainDTO);

    /**
     * Get all the shipmentOrderMains.
     *
     * @return the list of entities.
     */
    List<ShipmentOrderMainDTO> findAll();

    /**
     * Get the "id" shipmentOrderMain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ShipmentOrderMainDTO> findOne(Long id);

    /**
     * Delete the "id" shipmentOrderMain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
