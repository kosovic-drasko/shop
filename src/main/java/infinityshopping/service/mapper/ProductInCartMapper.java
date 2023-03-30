package infinityshopping.service.mapper;

import infinityshopping.domain.Cart;
import infinityshopping.domain.ProductInCart;
import infinityshopping.service.dto.CartDTO;
import infinityshopping.service.dto.ProductInCartDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductInCart} and its DTO {@link ProductInCartDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductInCartMapper extends EntityMapper<ProductInCartDTO, ProductInCart> {
    @Mapping(target = "cart", source = "cart", qualifiedByName = "cartId")
    ProductInCartDTO toDto(ProductInCart s);

    @Named("cartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoCartId(Cart cart);
}
