package infinityshopping.service.impl;

import infinityshopping.domain.ShipmentCart;
import infinityshopping.repository.ShipmentCartRepository;
import infinityshopping.service.ShipmentCartService;
import infinityshopping.service.dto.ShipmentCartDTO;
import infinityshopping.service.mapper.ShipmentCartMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ShipmentCart}.
 */
@Service
@Transactional
public class ShipmentCartServiceImpl implements ShipmentCartService {

    private final Logger log = LoggerFactory.getLogger(ShipmentCartServiceImpl.class);

    private final ShipmentCartRepository shipmentCartRepository;

    private final ShipmentCartMapper shipmentCartMapper;

    public ShipmentCartServiceImpl(ShipmentCartRepository shipmentCartRepository, ShipmentCartMapper shipmentCartMapper) {
        this.shipmentCartRepository = shipmentCartRepository;
        this.shipmentCartMapper = shipmentCartMapper;
    }

    @Override
    public ShipmentCartDTO save(ShipmentCartDTO shipmentCartDTO) {
        log.debug("Request to save ShipmentCart : {}", shipmentCartDTO);
        ShipmentCart shipmentCart = shipmentCartMapper.toEntity(shipmentCartDTO);
        shipmentCart = shipmentCartRepository.save(shipmentCart);
        return shipmentCartMapper.toDto(shipmentCart);
    }

    @Override
    public ShipmentCartDTO update(ShipmentCartDTO shipmentCartDTO) {
        log.debug("Request to update ShipmentCart : {}", shipmentCartDTO);
        ShipmentCart shipmentCart = shipmentCartMapper.toEntity(shipmentCartDTO);
        shipmentCart = shipmentCartRepository.save(shipmentCart);
        return shipmentCartMapper.toDto(shipmentCart);
    }

    @Override
    public Optional<ShipmentCartDTO> partialUpdate(ShipmentCartDTO shipmentCartDTO) {
        log.debug("Request to partially update ShipmentCart : {}", shipmentCartDTO);

        return shipmentCartRepository
            .findById(shipmentCartDTO.getId())
            .map(existingShipmentCart -> {
                shipmentCartMapper.partialUpdate(existingShipmentCart, shipmentCartDTO);

                return existingShipmentCart;
            })
            .map(shipmentCartRepository::save)
            .map(shipmentCartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShipmentCartDTO> findAll() {
        log.debug("Request to get all ShipmentCarts");
        return shipmentCartRepository.findAll().stream().map(shipmentCartMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ShipmentCartDTO> findOne(Long id) {
        log.debug("Request to get ShipmentCart : {}", id);
        return shipmentCartRepository.findById(id).map(shipmentCartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ShipmentCart : {}", id);
        shipmentCartRepository.deleteById(id);
    }
}
