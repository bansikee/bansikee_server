package com.tomasfriends.bansikee_server.dictionary.domain;

import com.tomasfriends.bansikee_server.onBoarding.domain.OnBoardingAnswer;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "plant")
public class PlantDictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer plantIdx;

    private String name;

    private String species;

    private String info;

    private String managementLevel;

    private Integer height;

    private Integer width;

    private String speed;

    private String smell;

    private String temperature;

    private String water;

    private String light;

    private String area;

    private String plantImgUrl;

    private Integer managementLevelCode;
    private Integer heightCode;
    private Integer speedCode;
    private Integer smellCode;

    public static PlantDictionary of( String name, String species, String info, String managementLevel, Integer height,Integer width,String speed,String smell,String temperature,String water,String light,String area, String plantImgUrl, Integer managementLevelCode, Integer heightCode, Integer speedCode,Integer smellCode) {
        PlantDictionary plantDictionary = new PlantDictionary();

        plantDictionary.name = name;
        plantDictionary.species = species;
        plantDictionary.info = info;
        plantDictionary.managementLevel = managementLevel;
        plantDictionary.height = height;
        plantDictionary.width = width;
        plantDictionary.speed = speed;
        plantDictionary.smell = smell;
        plantDictionary.temperature = temperature;
        plantDictionary.water = water;
        plantDictionary.light = light;
        plantDictionary.area = area;
        plantDictionary.plantImgUrl = plantImgUrl;
        plantDictionary.managementLevelCode = managementLevelCode;
        plantDictionary.heightCode = heightCode;
        plantDictionary.speedCode = speedCode;
        plantDictionary.smellCode = smellCode;
        return plantDictionary;
    }
}