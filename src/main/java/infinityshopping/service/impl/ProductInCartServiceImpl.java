package infinityshopping.service.impl;

import infinityshopping.domain.ProductInCart;
import infinityshopping.repository.ProductInCartRepository;
import infinityshopping.service.ProductInCartService;
import infinityshopping.service.dto.ProductInCartDTO;
import infinityshopping.service.mapper.ProductInCartMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProductInCart}.
 */
@Service
@Transactional
public class ProductInCartServiceImpl implements ProductInCartService {

    private final Logger log = LoggerFactory.getLogger(ProductInCartServiceImpl.class);

    private final ProductInCartRepository productInCartRepository;

    private final ProductInCartMapper productInCartMapper;

    public ProductInCartServiceImpl(ProductInCartRepository productInCartRepository, ProductInCartMapper productInCartMapper) {
        this.productInCartRepository = productInCartRepository;
        this.productInCartMapper = productInCartMapper;
    }

    @Override
    public ProductInCartDTO save(ProductInCartDTO productInCartDTO) {
        log.debug("Request to save ProductInCart : {}", productInCartDTO);
        ProductInCart productInCart = productInCartMapper.toEntity(productInCartDTO);
        productInCart = productInCartRepository.save(productInCart);
        return productInCartMapper.toDto(productInCart);
    }

    @Override
    public ProductInCartDTO update(ProductInCartDTO productInCartDTO) {
        log.debug("Request to update ProductInCart : {}", productInCartDTO);
        ProductInCart productInCart = productInCartMapper.toEntity(productInCartDTO);
        productInCart = productInCartRepository.save(productInCart);
        return productInCartMapper.toDto(productInCart);
    }

    @Override
    public Optional<ProductInCartDTO> partialUpdate(ProductInCartDTO productInCartDTO) {
        log.debug("Request to partially update ProductInCart : {}", productInCartDTO);

        return productInCartRepository
            .findById(productInCartDTO.getId())
            .map(existingProductInCart -> {
                productInCartMapper.partialUpdate(existingProductInCart, productInCartDTO);

                return existingProductInCart;
            })
            .map(productInCartRepository::save)
            .map(productInCartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductInCartDTO> findAll() {
        log.debug("Request to get all ProductInCarts");
        return productInCartRepository.findAll().stream().map(productInCartMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductInCartDTO> findOne(Long id) {
        log.debug("Request to get ProductInCart : {}", id);
        return productInCartRepository.findById(id).map(productInCartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProductInCart : {}", id);
        productInCartRepository.deleteById(id);
    }
}
