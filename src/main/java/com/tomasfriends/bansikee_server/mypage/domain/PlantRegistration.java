package com.tomasfriends.bansikee_server.mypage.domain;

import com.tomasfriends.bansikee_server.common.BaseEntity;
import com.tomasfriends.bansikee_server.mypage.service.dto.MyPlantListResponseDto;
import com.tomasfriends.bansikee_server.mypage.service.dto.MyPlantResponseDto;
import com.tomasfriends.bansikee_server.mypage.service.dto.PlantRegistrationRequestDto;
import com.tomasfriends.bansikee_server.onBoarding.domain.Plant;
import com.tomasfriends.bansikee_server.sign.domain.BansikeeUser;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "myPlant")
public class PlantRegistration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "myPlantImgUrl")
    private String pictureUrl;

    @Column(name = "nickName")
    private String plantNickName;

    @Column(name = "startDate")
    private LocalDateTime plantBirth;

    @Column(name = "summary")
    private String plantIntroduce;

    @Column(name = "waterCycle")
    private Integer wateringCycle;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private BansikeeUser user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantIdx")
    private Plant plant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myPlant", orphanRemoval=true)
    private List<Diary> diaries;

    @Builder
    public PlantRegistration(Integer id, String pictureUrl, Plant plant, String plantNickName, LocalDateTime plantBirth, String plantIntroduce, Integer wateringCycle, BansikeeUser user) {
        this.id = id;
        this.pictureUrl = pictureUrl;
        this.plant = plant;
        this.plantNickName = plantNickName;
        this.plantBirth = plantBirth;
        this.plantIntroduce = plantIntroduce;
        this.wateringCycle = wateringCycle;
        this.user = user;
    }

    public MyPlantListResponseDto toListResponseDto() {
        return MyPlantListResponseDto.builder()
            .MyPlantId(id)
            .nickName(plantNickName)
            .contents(plantIntroduce)
            .plantName(plant.getName())
            .profileImgUrl(pictureUrl)
            .build();
    }

    public MyPlantResponseDto toMyPlantResponseDto() {
        return MyPlantResponseDto.builder()
            .myPlantId(id)
            .plantName(plant.getName())
            .nickName(plantNickName)
            .water(wateringCycle)
            .contents(plantIntroduce)
            .myPlantProfileUrl(pictureUrl)
            .startDate(plantBirth)
            .dDay(getDDay(plantBirth))
            .build();
    }

    public long getDDay(LocalDateTime plantBirth) {
        return ChronoUnit.DAYS.between(plantBirth.toLocalDate(), LocalDate.now());
    }
}