package com.wallet.service;

import com.wallet.entity.WalletItem;
import org.springframework.data.domain.Page;

import java.util.Date;

public interface WalletItemService {
    WalletItem save(WalletItem i);

    Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page);
}
