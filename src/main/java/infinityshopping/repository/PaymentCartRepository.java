package infinityshopping.repository;

import infinityshopping.domain.PaymentCart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentCartRepository extends JpaRepository<PaymentCart, Long> {}
