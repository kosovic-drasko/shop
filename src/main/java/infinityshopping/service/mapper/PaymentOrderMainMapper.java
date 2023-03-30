package infinityshopping.service.mapper;

import infinityshopping.domain.OrderMain;
import infinityshopping.domain.PaymentOrderMain;
import infinityshopping.service.dto.OrderMainDTO;
import infinityshopping.service.dto.PaymentOrderMainDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PaymentOrderMain} and its DTO {@link PaymentOrderMainDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentOrderMainMapper extends EntityMapper<PaymentOrderMainDTO, PaymentOrderMain> {
    @Mapping(target = "orderMain", source = "orderMain", qualifiedByName = "orderMainId")
    PaymentOrderMainDTO toDto(PaymentOrderMain s);

    @Named("orderMainId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    OrderMainDTO toDtoOrderMainId(OrderMain orderMain);
}
