package infinityshopping.service.mapper;

import infinityshopping.domain.OrderMain;
import infinityshopping.domain.ShipmentOrderMain;
import infinityshopping.service.dto.OrderMainDTO;
import infinityshopping.service.dto.ShipmentOrderMainDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ShipmentOrderMain} and its DTO {@link ShipmentOrderMainDTO}.
 */
@Mapper(componentModel = "spring")
public interface ShipmentOrderMainMapper extends EntityMapper<ShipmentOrderMainDTO, ShipmentOrderMain> {
    @Mapping(target = "orderMain", source = "orderMain", qualifiedByName = "orderMainId")
    ShipmentOrderMainDTO toDto(ShipmentOrderMain s);

    @Named("orderMainId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderMainDTO toDtoOrderMainId(OrderMain orderMain);
}
