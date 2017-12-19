package com.giftsearcher.service;

import com.giftsearcher.entity.Shop;

public interface ShopService {
    Shop findShopById(Long idShop);
    Shop findShopByShopName(String shopName);
}
