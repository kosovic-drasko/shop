package infinityshopping.service.impl;

import infinityshopping.domain.PaymentCart;
import infinityshopping.repository.PaymentCartRepository;
import infinityshopping.service.PaymentCartService;
import infinityshopping.service.dto.PaymentCartDTO;
import infinityshopping.service.mapper.PaymentCartMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentCart}.
 */
@Service
@Transactional
public class PaymentCartServiceImpl implements PaymentCartService {

    private final Logger log = LoggerFactory.getLogger(PaymentCartServiceImpl.class);

    private final PaymentCartRepository paymentCartRepository;

    private final PaymentCartMapper paymentCartMapper;

    public PaymentCartServiceImpl(PaymentCartRepository paymentCartRepository, PaymentCartMapper paymentCartMapper) {
        this.paymentCartRepository = paymentCartRepository;
        this.paymentCartMapper = paymentCartMapper;
    }

    @Override
    public PaymentCartDTO save(PaymentCartDTO paymentCartDTO) {
        log.debug("Request to save PaymentCart : {}", paymentCartDTO);
        PaymentCart paymentCart = paymentCartMapper.toEntity(paymentCartDTO);
        paymentCart = paymentCartRepository.save(paymentCart);
        return paymentCartMapper.toDto(paymentCart);
    }

    @Override
    public PaymentCartDTO update(PaymentCartDTO paymentCartDTO) {
        log.debug("Request to update PaymentCart : {}", paymentCartDTO);
        PaymentCart paymentCart = paymentCartMapper.toEntity(paymentCartDTO);
        paymentCart = paymentCartRepository.save(paymentCart);
        return paymentCartMapper.toDto(paymentCart);
    }

    @Override
    public Optional<PaymentCartDTO> partialUpdate(PaymentCartDTO paymentCartDTO) {
        log.debug("Request to partially update PaymentCart : {}", paymentCartDTO);

        return paymentCartRepository
            .findById(paymentCartDTO.getId())
            .map(existingPaymentCart -> {
                paymentCartMapper.partialUpdate(existingPaymentCart, paymentCartDTO);

                return existingPaymentCart;
            })
            .map(paymentCartRepository::save)
            .map(paymentCartMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentCartDTO> findAll() {
        log.debug("Request to get all PaymentCarts");
        return paymentCartRepository.findAll().stream().map(paymentCartMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentCartDTO> findOne(Long id) {
        log.debug("Request to get PaymentCart : {}", id);
        return paymentCartRepository.findById(id).map(paymentCartMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentCart : {}", id);
        paymentCartRepository.deleteById(id);
    }
}
