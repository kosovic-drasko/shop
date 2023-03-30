package infinityshopping.repository;

import infinityshopping.domain.OrderMain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the OrderMain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderMainRepository extends JpaRepository<OrderMain, Long> {}
