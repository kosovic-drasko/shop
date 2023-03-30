package infinityshopping.service;

import infinityshopping.service.dto.PaymentCartDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link infinityshopping.domain.PaymentCart}.
 */
public interface PaymentCartService {
    /**
     * Save a paymentCart.
     *
     * @param paymentCartDTO the entity to save.
     * @return the persisted entity.
     */
    PaymentCartDTO save(PaymentCartDTO paymentCartDTO);

    /**
     * Updates a paymentCart.
     *
     * @param paymentCartDTO the entity to update.
     * @return the persisted entity.
     */
    PaymentCartDTO update(PaymentCartDTO paymentCartDTO);

    /**
     * Partially updates a paymentCart.
     *
     * @param paymentCartDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PaymentCartDTO> partialUpdate(PaymentCartDTO paymentCartDTO);

    /**
     * Get all the paymentCarts.
     *
     * @return the list of entities.
     */
    List<PaymentCartDTO> findAll();

    /**
     * Get the "id" paymentCart.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PaymentCartDTO> findOne(Long id);

    /**
     * Delete the "id" paymentCart.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
