package com.tomasfriends.bansikee_server.mypage.service;

import com.tomasfriends.bansikee_server.mypage.domain.Diary;
import com.tomasfriends.bansikee_server.mypage.domain.DiaryPicture;
import com.tomasfriends.bansikee_server.mypage.domain.PlantRegistration;
import com.tomasfriends.bansikee_server.mypage.domain.repository.DiaryRepository;
import com.tomasfriends.bansikee_server.mypage.domain.repository.MyPlantRepository;
import com.tomasfriends.bansikee_server.mypage.domain.repository.PictureRepository;
import com.tomasfriends.bansikee_server.mypage.service.dto.DiaryRequestDto;
import com.tomasfriends.bansikee_server.mypage.service.exception.NotExistMyPlantException;
import com.tomasfriends.bansikee_server.sign.domain.BansikeeUser;
import com.tomasfriends.bansikee_server.sign.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final PictureRepository pictureRepository;
    private final MyPlantRepository myPlantRepository;

    public void save(DiaryRequestDto diaryRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BansikeeUser loginUser = (BansikeeUser) authentication.getPrincipal();
        Optional<PlantRegistration> registeredPlant = myPlantRepository.findById(diaryRequestDto.getPlantId());
        Diary diary = diaryRequestDto.toDiaryEntity(registeredPlant.orElseThrow(NotExistMyPlantException::new), loginUser);
        diaryRepository.save(diary);
        savePictures(diaryRequestDto, diary);
    }

    public void savePictures(DiaryRequestDto diaryRequestDto, Diary diary) {
        List<DiaryPicture> diaryPictures = diaryRequestDto.toDiaryPictureEntities(diary);
        diaryPictures.forEach(pictureRepository::save);
    }
}
