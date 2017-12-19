package com.giftsearcher.service;

import com.giftsearcher.entity.Gift;
import com.giftsearcher.entity.enumerate.Gender;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftService{

    List<Gift> findTenByBestRating(Pageable pageable);

    List<Gift> findTenByPopular(Pageable pageable);

    List<Gift> findTenByNew(Pageable pageable);

    List<Gift> findTenByCheap(Pageable pageable);

    List<Gift> findTenByExpensive(Pageable pageable);

    Gift findById(Long id);

    List<Gift> findOfAdvancedSearch(double priceFrom,
                                    double priceTo,
                                    Byte recommendedAgeFrom,
                                    Byte recommendedAgeTo,
                                    Gender gender,
                                    String holiday,
                                    String hobby);
    Gift upCountLikeGift(Long id);

    Gift save(Gift gift);
}
