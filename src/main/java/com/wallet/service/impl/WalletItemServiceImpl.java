package com.wallet.service.impl;

import com.wallet.entity.WalletItem;
import com.wallet.repository.WalletItemRepository;
import com.wallet.service.WalletItemService;
import com.wallet.util.enums.TypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class WalletItemServiceImpl implements WalletItemService {

    @Autowired
    WalletItemRepository repository;

    @Value("${pagination.item_per_page}")
    private int itemPerPage;

    @Override
    public WalletItem save(WalletItem i) {
        return repository.save(i);
    }

    @Override
    public Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page) {
        @SuppressWarnings("deprecation")
        PageRequest pg = PageRequest.of(page, itemPerPage);

        return repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(wallet, start, end, pg);
    }

    @Override
    public List<WalletItem> findByWalletIdAndType(long wallet, TypeEnum type) {
        return repository.findByWalletIdAndType(wallet, type);
    }

    @Override
    public BigDecimal sumByWalletId(long wallet) {
        return repository.sumByWalletId(wallet);
    }
}
