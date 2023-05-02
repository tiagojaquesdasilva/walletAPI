package com.wallet.service;

import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemService {
    WalletItem save(WalletItem i);

    Page<WalletItem> findBetweenDates(Long wallet, Date start, Date end, int page);

    List<WalletItem> findByWalletIdAndType(long wallet, TypeEnum type);

    BigDecimal sumByWalletId(long wallet);
}
