package infinityshopping.service.impl;

import infinityshopping.domain.ProductInOrderMain;
import infinityshopping.repository.ProductInOrderMainRepository;
import infinityshopping.service.ProductInOrderMainService;
import infinityshopping.service.dto.ProductInOrderMainDTO;
import infinityshopping.service.mapper.ProductInOrderMainMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductInOrderMain}.
 */
@Service
@Transactional
public class ProductInOrderMainServiceImpl implements ProductInOrderMainService {

    private final Logger log = LoggerFactory.getLogger(ProductInOrderMainServiceImpl.class);

    private final ProductInOrderMainRepository productInOrderMainRepository;

    private final ProductInOrderMainMapper productInOrderMainMapper;

    public ProductInOrderMainServiceImpl(
        ProductInOrderMainRepository productInOrderMainRepository,
        ProductInOrderMainMapper productInOrderMainMapper
    ) {
        this.productInOrderMainRepository = productInOrderMainRepository;
        this.productInOrderMainMapper = productInOrderMainMapper;
    }

    @Override
    public ProductInOrderMainDTO save(ProductInOrderMainDTO productInOrderMainDTO) {
        log.debug("Request to save ProductInOrderMain : {}", productInOrderMainDTO);
        ProductInOrderMain productInOrderMain = productInOrderMainMapper.toEntity(productInOrderMainDTO);
        productInOrderMain = productInOrderMainRepository.save(productInOrderMain);
        return productInOrderMainMapper.toDto(productInOrderMain);
    }

    @Override
    public ProductInOrderMainDTO update(ProductInOrderMainDTO productInOrderMainDTO) {
        log.debug("Request to update ProductInOrderMain : {}", productInOrderMainDTO);
        ProductInOrderMain productInOrderMain = productInOrderMainMapper.toEntity(productInOrderMainDTO);
        productInOrderMain = productInOrderMainRepository.save(productInOrderMain);
        return productInOrderMainMapper.toDto(productInOrderMain);
    }

    @Override
    public Optional<ProductInOrderMainDTO> partialUpdate(ProductInOrderMainDTO productInOrderMainDTO) {
        log.debug("Request to partially update ProductInOrderMain : {}", productInOrderMainDTO);

        return productInOrderMainRepository
            .findById(productInOrderMainDTO.getId())
            .map(existingProductInOrderMain -> {
                productInOrderMainMapper.partialUpdate(existingProductInOrderMain, productInOrderMainDTO);

                return existingProductInOrderMain;
            })
            .map(productInOrderMainRepository::save)
            .map(productInOrderMainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductInOrderMainDTO> findAll() {
        log.debug("Request to get all ProductInOrderMains");
        return productInOrderMainRepository
            .findAll()
            .stream()
            .map(productInOrderMainMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductInOrderMainDTO> findOne(Long id) {
        log.debug("Request to get ProductInOrderMain : {}", id);
        return productInOrderMainRepository.findById(id).map(productInOrderMainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductInOrderMain : {}", id);
        productInOrderMainRepository.deleteById(id);
    }
}
