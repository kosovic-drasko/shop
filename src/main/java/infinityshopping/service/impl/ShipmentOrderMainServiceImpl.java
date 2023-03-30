package infinityshopping.service.impl;

import infinityshopping.domain.ShipmentOrderMain;
import infinityshopping.repository.ShipmentOrderMainRepository;
import infinityshopping.service.ShipmentOrderMainService;
import infinityshopping.service.dto.ShipmentOrderMainDTO;
import infinityshopping.service.mapper.ShipmentOrderMainMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShipmentOrderMain}.
 */
@Service
@Transactional
public class ShipmentOrderMainServiceImpl implements ShipmentOrderMainService {

    private final Logger log = LoggerFactory.getLogger(ShipmentOrderMainServiceImpl.class);

    private final ShipmentOrderMainRepository shipmentOrderMainRepository;

    private final ShipmentOrderMainMapper shipmentOrderMainMapper;

    public ShipmentOrderMainServiceImpl(
        ShipmentOrderMainRepository shipmentOrderMainRepository,
        ShipmentOrderMainMapper shipmentOrderMainMapper
    ) {
        this.shipmentOrderMainRepository = shipmentOrderMainRepository;
        this.shipmentOrderMainMapper = shipmentOrderMainMapper;
    }

    @Override
    public ShipmentOrderMainDTO save(ShipmentOrderMainDTO shipmentOrderMainDTO) {
        log.debug("Request to save ShipmentOrderMain : {}", shipmentOrderMainDTO);
        ShipmentOrderMain shipmentOrderMain = shipmentOrderMainMapper.toEntity(shipmentOrderMainDTO);
        shipmentOrderMain = shipmentOrderMainRepository.save(shipmentOrderMain);
        return shipmentOrderMainMapper.toDto(shipmentOrderMain);
    }

    @Override
    public ShipmentOrderMainDTO update(ShipmentOrderMainDTO shipmentOrderMainDTO) {
        log.debug("Request to update ShipmentOrderMain : {}", shipmentOrderMainDTO);
        ShipmentOrderMain shipmentOrderMain = shipmentOrderMainMapper.toEntity(shipmentOrderMainDTO);
        shipmentOrderMain = shipmentOrderMainRepository.save(shipmentOrderMain);
        return shipmentOrderMainMapper.toDto(shipmentOrderMain);
    }

    @Override
    public Optional<ShipmentOrderMainDTO> partialUpdate(ShipmentOrderMainDTO shipmentOrderMainDTO) {
        log.debug("Request to partially update ShipmentOrderMain : {}", shipmentOrderMainDTO);

        return shipmentOrderMainRepository
            .findById(shipmentOrderMainDTO.getId())
            .map(existingShipmentOrderMain -> {
                shipmentOrderMainMapper.partialUpdate(existingShipmentOrderMain, shipmentOrderMainDTO);

                return existingShipmentOrderMain;
            })
            .map(shipmentOrderMainRepository::save)
            .map(shipmentOrderMainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShipmentOrderMainDTO> findAll() {
        log.debug("Request to get all ShipmentOrderMains");
        return shipmentOrderMainRepository
            .findAll()
            .stream()
            .map(shipmentOrderMainMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentOrderMainDTO> findOne(Long id) {
        log.debug("Request to get ShipmentOrderMain : {}", id);
        return shipmentOrderMainRepository.findById(id).map(shipmentOrderMainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentOrderMain : {}", id);
        shipmentOrderMainRepository.deleteById(id);
    }
}
