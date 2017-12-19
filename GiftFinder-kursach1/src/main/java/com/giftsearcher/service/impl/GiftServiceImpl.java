package com.giftsearcher.service.impl;

import com.giftsearcher.entity.Gift;
import com.giftsearcher.entity.Hobby;
import com.giftsearcher.entity.Holiday;
import com.giftsearcher.entity.Shop;
import com.giftsearcher.entity.enumerate.Gender;
import com.giftsearcher.repository.GiftRepository;
import com.giftsearcher.service.GiftService;
import com.giftsearcher.service.ShopService;
import com.giftsearcher.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class GiftServiceImpl implements GiftService {

    private final GiftRepository giftRepository;
    private final ShopService shopService;

    @Autowired
    public GiftServiceImpl(GiftRepository giftRepository, ShopService shopService) {
        this.giftRepository = giftRepository;
        this.shopService = shopService;
    }

    @Override
    public List<Gift> findTenByBestRating(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "rating.averageRating"));
        return findTenBySortProp(pageRequest);
    }

    private List<Gift> findTenBySortProp(Pageable pageable) {
        Page<Gift> popularGiftPage = giftRepository.findAll(pageable);

        return popularGiftPage.getContent()
                .stream()
                .map(gift -> processGift(gift))
                .collect(Collectors.toList());
    }

    @Override
    public List<Gift> findTenByPopular(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "appreciated"));
        return findTenBySortProp(pageRequest);
    }

    @Override
    public List<Gift> findTenByNew(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "dateAdding"));
        return findTenBySortProp(pageRequest);
    }

    @Override
    public List<Gift> findTenByCheap(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.ASC, "price"));
        return findTenBySortProp(pageRequest);
    }

    @Override
    public List<Gift> findTenByExpensive(Pageable pageable) {
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), new Sort(Sort.Direction.DESC, "price"));
        return findTenBySortProp(pageRequest);
    }

    @Override
    public Gift findById(Long id) {
        Gift gift = giftRepository.findOne(id);
        if (gift == null) {
            return null;
        }
        gift.setRating(null);
        gift.getShop().setGiftList(null);
        gift.getShop().getAddressList().get(0).setShop(null);
        gift.getShop().getAddressList().get(0).setGeoData(null);
        gift.getShop().setAddressList(gift.getShop().getAddressList().subList(0,1));
        gift.setImage(new ImageUtil().getImageFromResource(gift.getImagePath()));
        gift.setHobbyList(null);
        gift.setHolidayList(null);
        return gift;
    }

    private Gift processGift(Gift gift) {
        gift.setRating(null);
        gift.getShop().setGiftList(null);
        gift.getShop().setAddressList(null);
        gift.getShop().setShopName(null);
        gift.setImage(new ImageUtil().getImageFromResource(gift.getImagePath()));
        gift.setHobbyList(null);
        gift.setHolidayList(null);
        return gift;
    }

    @Override
    public List<Gift> findOfAdvancedSearch(double priceFrom,
                                           double priceTo,
                                           Byte recommendedAgeFrom,
                                           Byte recommendedAgeTo,
                                           Gender gender,
                                           String hobbyName,
                                           String holidayName) {
        List<Gift> giftList = giftRepository.findAllByPriceBetweenAndRecommendedAgeBetweenAndGender(
                priceFrom, priceTo,recommendedAgeFrom, recommendedAgeTo, gender);

        List<Gift> newGifts = new ArrayList<>();
        for (Gift g : giftList) {
            int count = 0;
            for (Hobby hobby : g.getHobbyList()){
                if (hobby.getHobbyName().equals(hobbyName)) {
                    count++;
                    break;
                }
            }

            for (Holiday holiday : g.getHolidayList()){
                if (holiday.getHolidayName().equals(holidayName)) {
                    count++;
                    break;
                }
            }
            if (count == 2){
                newGifts.add(g);
            }
        }
        return newGifts
                .stream()
                .map(gift -> processGift(gift))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Gift upCountLikeGift(Long id) {
        Gift gift = giftRepository.findOne(id);
        if (gift == null) {
            return null;
        }
        gift.setAppreciated(gift.getAppreciated() + 1);
        return giftRepository.save(gift);
    }

    @Override
    @Transactional
    public Gift save(Gift gift) {
        final String IMAGE_FORMAT = "jpg";
        final String IMAGE_NAME = UUID.randomUUID().toString() + "." + IMAGE_FORMAT.toLowerCase();
        final String IMAGE_PATH = "src/main/resources/static/";

        Shop shop = shopService.findShopByShopName(gift.getShop().getShopName());
        gift.setShop(shop);

        try (InputStream bais = new ByteArrayInputStream(gift.getImage()); InputStream bis = new BufferedInputStream(bais)) {
            BufferedImage image = ImageIO.read(bis);
            ImageIO.write(image, IMAGE_FORMAT, new File(IMAGE_PATH + IMAGE_NAME));
            gift.setImagePath(IMAGE_NAME);
            gift.setDateAdding(new Date());
            gift = giftRepository.save(gift);
            return gift;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}