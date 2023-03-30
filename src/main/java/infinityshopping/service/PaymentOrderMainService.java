package infinityshopping.service;

import infinityshopping.service.dto.PaymentOrderMainDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link infinityshopping.domain.PaymentOrderMain}.
 */
public interface PaymentOrderMainService {
    /**
     * Save a paymentOrderMain.
     *
     * @param paymentOrderMainDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentOrderMainDTO save(PaymentOrderMainDTO paymentOrderMainDTO);

    /**
     * Updates a paymentOrderMain.
     *
     * @param paymentOrderMainDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentOrderMainDTO update(PaymentOrderMainDTO paymentOrderMainDTO);

    /**
     * Partially updates a paymentOrderMain.
     *
     * @param paymentOrderMainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentOrderMainDTO> partialUpdate(PaymentOrderMainDTO paymentOrderMainDTO);

    /**
     * Get all the paymentOrderMains.
     *
     * @return the list of entities.
     */
    List<PaymentOrderMainDTO> findAll();

    /**
     * Get the "id" paymentOrderMain.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentOrderMainDTO> findOne(Long id);

    /**
     * Delete the "id" paymentOrderMain.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
