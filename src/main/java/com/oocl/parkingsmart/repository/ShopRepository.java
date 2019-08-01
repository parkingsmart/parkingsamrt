package com.oocl.parkingsmart.repository;

import com.oocl.parkingsmart.entity.ShopPromotions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopPromotions,Long> {
}
