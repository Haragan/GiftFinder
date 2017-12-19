package com.giftsearcher.api;

import com.giftsearcher.entity.Gift;
import com.giftsearcher.entity.enumerate.Gender;
import com.giftsearcher.service.GiftService;
import com.giftsearcher.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private final GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

//    @PreAuthorize("hasAuthority('EMPLOYEE')")

    @RequestMapping(
            value = "/image/{imagePath}",
            method = RequestMethod.GET,
            produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_GIF_VALUE }
            )
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable String imagePath) throws IOException {
        return new ImageUtil().getImageFromResourceApi(imagePath);
    }

    @RequestMapping(value = "/gift/{id}", method = RequestMethod.GET)
    public Gift giftById(@PathVariable Long id){
       return giftService.findById(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Gift> bestRatingGifts(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return giftService.findTenByBestRating(pageable);
    }

    @RequestMapping(value = "/popular", method = RequestMethod.GET)
    public List<Gift> popularGifts(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return giftService.findTenByPopular(pageable);
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public List<Gift> newGifts(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return giftService.findTenByNew(pageable);
    }

    @RequestMapping(value = "/cheap", method = RequestMethod.GET)
    public List<Gift> cheapGifts(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return giftService.findTenByCheap(pageable);
    }

    @RequestMapping(value = "/expensive", method = RequestMethod.GET)
    public List<Gift> expensiveGifts(@PageableDefault(page = 0, size = 10) Pageable pageable){
        return giftService.findTenByExpensive(pageable);
    }

    @RequestMapping(value = "/advancedSearch", method = RequestMethod.POST)
    public List<Gift> useAdvancedSearch(@RequestParam int priceFrom,
                                        @RequestParam int priceTo,
                                        @RequestParam byte ageFrom,
                                        @RequestParam byte ageTo,
                                        @RequestParam String hobby,
                                        @RequestParam String holiday,
                                        @RequestParam Gender gender){
        List<Gift> gifts = giftService.findOfAdvancedSearch(priceFrom, priceTo, ageFrom, ageTo, gender, hobby, holiday);
        return gifts;
    }

    @RequestMapping(
            value = { "/create" },
            method = RequestMethod.POST,
            consumes=MediaType.APPLICATION_JSON_VALUE,
            produces=MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity createTask(@RequestBody Gift requestGift){
        Gift gift = giftService.save(requestGift);
        if (gift != null && gift.getId() != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(value = "/like/{idGift}", method = RequestMethod.GET)
    public Gift likedGift(@PathVariable Long idGift) {
        Gift upGift = giftService.upCountLikeGift(idGift);

        upGift.setRating(null);
        upGift.getShop().setGiftList(null);
        upGift.getShop().getAddressList().get(0).setShop(null);
        upGift.getShop().getAddressList().get(0).setGeoData(null);
        upGift.getShop().setAddressList(upGift.getShop().getAddressList().subList(0,1));
        upGift.setImage(new ImageUtil().getImageFromResource(upGift.getImagePath()));
        upGift.setHobbyList(null);
        upGift.setHolidayList(null);
        return upGift;
    }
}
