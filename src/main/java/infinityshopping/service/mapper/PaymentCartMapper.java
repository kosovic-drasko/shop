package infinityshopping.service.mapper;

import infinityshopping.domain.Cart;
import infinityshopping.domain.PaymentCart;
import infinityshopping.service.dto.CartDTO;
import infinityshopping.service.dto.PaymentCartDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentCart} and its DTO {@link PaymentCartDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentCartMapper extends EntityMapper<PaymentCartDTO, PaymentCart> {
    @Mapping(target = "cart", source = "cart", qualifiedByName = "cartId")
    PaymentCartDTO toDto(PaymentCart s);

    @Named("cartId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CartDTO toDtoCartId(Cart cart);
}
