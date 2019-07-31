package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.UserShopPromotions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<UserShopPromotions,Long> {
}
