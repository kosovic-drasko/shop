package infinityshopping.service.mapper;

import infinityshopping.domain.OrderMain;
import infinityshopping.domain.ProductInOrderMain;
import infinityshopping.service.dto.OrderMainDTO;
import infinityshopping.service.dto.ProductInOrderMainDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProductInOrderMain} and its DTO {@link ProductInOrderMainDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProductInOrderMainMapper extends EntityMapper<ProductInOrderMainDTO, ProductInOrderMain> {
    @Mapping(target = "orderMain", source = "orderMain", qualifiedByName = "orderMainId")
    ProductInOrderMainDTO toDto(ProductInOrderMain s);

    @Named("orderMainId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderMainDTO toDtoOrderMainId(OrderMain orderMain);
}
