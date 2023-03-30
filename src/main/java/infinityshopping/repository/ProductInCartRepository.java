package infinityshopping.repository;

import infinityshopping.domain.ProductInCart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ProductInCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductInCartRepository extends JpaRepository<ProductInCart, Long> {}
