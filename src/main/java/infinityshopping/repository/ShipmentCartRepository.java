package infinityshopping.repository;

import infinityshopping.domain.ShipmentCart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ShipmentCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ShipmentCartRepository extends JpaRepository<ShipmentCart, Long> {}
