package com.giftsearcher.repository;

import com.giftsearcher.entity.Shop;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {
    Shop findShopByShopName(String shopName);
}
