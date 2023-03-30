package infinityshopping.service.impl;

import infinityshopping.domain.OrderMain;
import infinityshopping.repository.OrderMainRepository;
import infinityshopping.service.OrderMainService;
import infinityshopping.service.dto.OrderMainDTO;
import infinityshopping.service.mapper.OrderMainMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link OrderMain}.
 */
@Service
@Transactional
public class OrderMainServiceImpl implements OrderMainService {

    private final Logger log = LoggerFactory.getLogger(OrderMainServiceImpl.class);

    private final OrderMainRepository orderMainRepository;

    private final OrderMainMapper orderMainMapper;

    public OrderMainServiceImpl(OrderMainRepository orderMainRepository, OrderMainMapper orderMainMapper) {
        this.orderMainRepository = orderMainRepository;
        this.orderMainMapper = orderMainMapper;
    }

    @Override
    public OrderMainDTO save(OrderMainDTO orderMainDTO) {
        log.debug("Request to save OrderMain : {}", orderMainDTO);
        OrderMain orderMain = orderMainMapper.toEntity(orderMainDTO);
        orderMain = orderMainRepository.save(orderMain);
        return orderMainMapper.toDto(orderMain);
    }

    @Override
    public OrderMainDTO update(OrderMainDTO orderMainDTO) {
        log.debug("Request to update OrderMain : {}", orderMainDTO);
        OrderMain orderMain = orderMainMapper.toEntity(orderMainDTO);
        orderMain = orderMainRepository.save(orderMain);
        return orderMainMapper.toDto(orderMain);
    }

    @Override
    public Optional<OrderMainDTO> partialUpdate(OrderMainDTO orderMainDTO) {
        log.debug("Request to partially update OrderMain : {}", orderMainDTO);

        return orderMainRepository
            .findById(orderMainDTO.getId())
            .map(existingOrderMain -> {
                orderMainMapper.partialUpdate(existingOrderMain, orderMainDTO);

                return existingOrderMain;
            })
            .map(orderMainRepository::save)
            .map(orderMainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderMainDTO> findAll() {
        log.debug("Request to get all OrderMains");
        return orderMainRepository.findAll().stream().map(orderMainMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderMainDTO> findOne(Long id) {
        log.debug("Request to get OrderMain : {}", id);
        return orderMainRepository.findById(id).map(orderMainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OrderMain : {}", id);
        orderMainRepository.deleteById(id);
    }
}
