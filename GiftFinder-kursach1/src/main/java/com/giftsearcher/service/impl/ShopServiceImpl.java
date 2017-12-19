package com.giftsearcher.service.impl;

import com.giftsearcher.entity.Address;
import com.giftsearcher.entity.Shop;
import com.giftsearcher.repository.ShopRepository;
import com.giftsearcher.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public Shop findShopById(Long idShop) {
        Shop shop = shopRepository.findOne(idShop);
        shop.setGiftList(null);
        List<Address> addressList = shop.getAddressList().stream()
                .map(address -> {
                    address.setShop(null);
                    return address;
                }).collect(Collectors.toList());

        shop.setAddressList(addressList);
        return shop;
    }

    @Override
    public Shop findShopByShopName(String shopName) {
        return shopRepository.findShopByShopName(shopName);
    }
}
