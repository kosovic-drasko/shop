package infinityshopping.repository;

import infinityshopping.domain.PaymentOrderMain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PaymentOrderMain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentOrderMainRepository extends JpaRepository<PaymentOrderMain, Long> {}
