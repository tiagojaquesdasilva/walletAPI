package com.wallet.repository;

import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface WalletItemRepository extends JpaRepository<WalletItem, Long> {
    Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long id, Date init, Date end, Pageable pg);

    List<WalletItem> findByWallet_IdAndType(Long wallet, TypeEnum type);

    @Query(value = "select sum(value) from WalletItem wi where wi.wallet.id = :wallet")
    BigDecimal sumByWalletId(@Param("wallet") Long wallet);
}