package com.giftsearcher.repository;

import com.giftsearcher.entity.Gift;
import com.giftsearcher.entity.enumerate.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//Репозиторий - это класс который будет обращаться к данным
public interface GiftRepository extends JpaRepository<Gift, Long> {

    List<Gift> findAllByPriceBetweenAndRecommendedAgeBetweenAndGender(double priceFrom, double priceTo, Byte recommendedAgeFrom, Byte recommendedAgeTo, Gender gender);
}