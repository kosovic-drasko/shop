package infinityshopping.service.mapper;

import infinityshopping.domain.Cart;
import infinityshopping.domain.ShipmentCart;
import infinityshopping.service.dto.CartDTO;
import infinityshopping.service.dto.ShipmentCartDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShipmentCart} and its DTO {@link ShipmentCartDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipmentCartMapper extends EntityMapper<ShipmentCartDTO, ShipmentCart> {
    @Mapping(target = "cart", source = "cart", qualifiedByName = "cartId")
    ShipmentCartDTO toDto(ShipmentCart s);

    @Named("cartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoCartId(Cart cart);
}
