package com.tomasfriends.bansikee_server.mypage.domain;

import com.tomasfriends.bansikee_server.common.BaseEntity;
import com.tomasfriends.bansikee_server.home.service.dto.HomePlant;
import com.tomasfriends.bansikee_server.mypage.service.dto.resp.MyPlantListResponseDto;
import com.tomasfriends.bansikee_server.mypage.service.dto.resp.MyPlantResponseDto;
import com.tomasfriends.bansikee_server.onBoarding.domain.Plant;
import com.tomasfriends.bansikee_server.sign.domain.BansikeeUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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

    private LocalDate lastDiaryDate;
    private LocalDate lastWateredDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private BansikeeUser user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plantIdx")
    private Plant plant;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "myPlant", orphanRemoval=true)
    private List<Diary> diaries = new ArrayList<>();

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
        Map<String, Long> waterDaysNotice = getWaterDaysNotice();
        return MyPlantListResponseDto.builder()
            .myPlantId(id)
            .nickName(plantNickName)
            .contents(plantIntroduce)
            .plantName(plant.getName())
            .profileImgUrl(pictureUrl)
            .waterDaysFrom(waterDaysNotice.get("from"))
            .waterDaysTo(waterDaysNotice.get("to"))
            .build();
    }

    public Map<String, Long> getWaterDaysNotice() {
        if (lastWateredDate == null) {
            return Map.of("from", -1L, "to", -1L);
        }
        long from = getDDay(lastWateredDate.atStartOfDay(), LocalDateTime.now());
        long to = wateringCycle - from;
        return Map.of("from", from, "to", to);
    }

    public MyPlantResponseDto toMyPlantResponseDto() {
        return MyPlantResponseDto.builder()
            .myPlantId(id)
            .plantId(plant.getPlantIdx())
            .plantName(plant.getName())
            .nickName(plantNickName)
            .water(wateringCycle)
            .contents(plantIntroduce)
            .myPlantProfileUrl(pictureUrl)
            .startDate(plantBirth)
            .plantTip(plant.getInfo())
            .dDay(getDDay(plantBirth, LocalDateTime.now()))
            .build();
    }

    public static long getDDay(LocalDateTime birth, LocalDateTime writeDate) {
        return ChronoUnit.DAYS.between(birth.toLocalDate(), writeDate.toLocalDate());
    }

    public String getBirth(LocalDateTime date) {
        Period period = LocalDate.now().until(date.toLocalDate());
        return period.getYears() + "년 | " + period.getMonths() + "개월차";
    }

    public HomePlant toHomeMyPlant() {
        Map<String, Long> waterDaysNotice = getWaterDaysNotice();
        return HomePlant.builder()
            .myPlantId(id)
            .myPlantImgUrl(pictureUrl)
            .myPlantNickName(plantNickName)
            .myPlantName(plant.getName())
            .myPlantWaterCycle(wateringCycle)
            .myPlantLastWaterDay(lastWateredDate)
            .waterDayFrom(waterDaysNotice.get("from"))
            .waterDayTo(waterDaysNotice.get("to"))
            .myPlantAge(getBirth(plantBirth))
            .todayDiaryStatus(getIsWriteDiary())
            .build();
    }

    private boolean getIsWriteDiary() {
        if (lastDiaryDate == null) {
            return false;
        }
        return lastDiaryDate.isEqual(LocalDate.now());
    }
}