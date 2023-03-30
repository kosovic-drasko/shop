package infinityshopping.repository;

import infinityshopping.domain.ShipmentOrderMain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShipmentOrderMain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentOrderMainRepository extends JpaRepository<ShipmentOrderMain, Long> {}
