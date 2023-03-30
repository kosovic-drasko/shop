package infinityshopping.service.mapper;

import infinityshopping.domain.OrderMain;
import infinityshopping.service.dto.OrderMainDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link OrderMain} and its DTO {@link OrderMainDTO}.
 */
@Mapper(componentModel = "spring")
public interface OrderMainMapper extends EntityMapper<OrderMainDTO, OrderMain> {}
