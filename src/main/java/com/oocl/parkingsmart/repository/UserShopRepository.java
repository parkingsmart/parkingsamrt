package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.UserShopPromotions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserShopRepository extends JpaRepository<UserShopPromotions,Long> {
    List<UserShopPromotions> findAllByUserId(Long id);

    UserShopPromotions findByUserIdAndShopId(Long userId, Long shopId);
}
