package infinityshopping.service.impl;

import infinityshopping.domain.PaymentOrderMain;
import infinityshopping.repository.PaymentOrderMainRepository;
import infinityshopping.service.PaymentOrderMainService;
import infinityshopping.service.dto.PaymentOrderMainDTO;
import infinityshopping.service.mapper.PaymentOrderMainMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PaymentOrderMain}.
 */
@Service
@Transactional
public class PaymentOrderMainServiceImpl implements PaymentOrderMainService {

    private final Logger log = LoggerFactory.getLogger(PaymentOrderMainServiceImpl.class);

    private final PaymentOrderMainRepository paymentOrderMainRepository;

    private final PaymentOrderMainMapper paymentOrderMainMapper;

    public PaymentOrderMainServiceImpl(
        PaymentOrderMainRepository paymentOrderMainRepository,
        PaymentOrderMainMapper paymentOrderMainMapper
    ) {
        this.paymentOrderMainRepository = paymentOrderMainRepository;
        this.paymentOrderMainMapper = paymentOrderMainMapper;
    }

    @Override
    public PaymentOrderMainDTO save(PaymentOrderMainDTO paymentOrderMainDTO) {
        log.debug("Request to save PaymentOrderMain : {}", paymentOrderMainDTO);
        PaymentOrderMain paymentOrderMain = paymentOrderMainMapper.toEntity(paymentOrderMainDTO);
        paymentOrderMain = paymentOrderMainRepository.save(paymentOrderMain);
        return paymentOrderMainMapper.toDto(paymentOrderMain);
    }

    @Override
    public PaymentOrderMainDTO update(PaymentOrderMainDTO paymentOrderMainDTO) {
        log.debug("Request to update PaymentOrderMain : {}", paymentOrderMainDTO);
        PaymentOrderMain paymentOrderMain = paymentOrderMainMapper.toEntity(paymentOrderMainDTO);
        paymentOrderMain = paymentOrderMainRepository.save(paymentOrderMain);
        return paymentOrderMainMapper.toDto(paymentOrderMain);
    }

    @Override
    public Optional<PaymentOrderMainDTO> partialUpdate(PaymentOrderMainDTO paymentOrderMainDTO) {
        log.debug("Request to partially update PaymentOrderMain : {}", paymentOrderMainDTO);

        return paymentOrderMainRepository
            .findById(paymentOrderMainDTO.getId())
            .map(existingPaymentOrderMain -> {
                paymentOrderMainMapper.partialUpdate(existingPaymentOrderMain, paymentOrderMainDTO);

                return existingPaymentOrderMain;
            })
            .map(paymentOrderMainRepository::save)
            .map(paymentOrderMainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentOrderMainDTO> findAll() {
        log.debug("Request to get all PaymentOrderMains");
        return paymentOrderMainRepository
            .findAll()
            .stream()
            .map(paymentOrderMainMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentOrderMainDTO> findOne(Long id) {
        log.debug("Request to get PaymentOrderMain : {}", id);
        return paymentOrderMainRepository.findById(id).map(paymentOrderMainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PaymentOrderMain : {}", id);
        paymentOrderMainRepository.deleteById(id);
    }
}
