package com.giftsearcher.api;

import com.giftsearcher.entity.Shop;
import com.giftsearcher.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @RequestMapping("/{idShop}")
    public Shop getShopById(@PathVariable Long idShop) {
        return shopService.findShopById(idShop);
    }

}
