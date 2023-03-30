package infinityshopping.repository;

import infinityshopping.domain.ProductInOrderMain;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductInOrderMain entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductInOrderMainRepository extends JpaRepository<ProductInOrderMain, Long> {}
