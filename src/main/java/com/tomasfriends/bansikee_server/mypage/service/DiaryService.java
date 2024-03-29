package com.tomasfriends.bansikee_server.mypage.service;

import com.tomasfriends.bansikee_server.common.AuthenticationUser;
import com.tomasfriends.bansikee_server.mypage.domain.Diary;
import com.tomasfriends.bansikee_server.mypage.domain.DiaryPicture;
import com.tomasfriends.bansikee_server.mypage.domain.MyPlant;
import com.tomasfriends.bansikee_server.mypage.domain.PlantRegistration;
import com.tomasfriends.bansikee_server.mypage.domain.repository.DiaryRepository;
import com.tomasfriends.bansikee_server.mypage.domain.repository.MyPlantRepository;
import com.tomasfriends.bansikee_server.mypage.domain.repository.PictureRepository;
import com.tomasfriends.bansikee_server.mypage.service.dto.*;
import com.tomasfriends.bansikee_server.mypage.service.dto.req.DiaryRequestDto;
import com.tomasfriends.bansikee_server.mypage.service.dto.resp.DiaryListResponseDto;
import com.tomasfriends.bansikee_server.mypage.service.dto.resp.DiaryResponseDto;
import com.tomasfriends.bansikee_server.mypage.service.exceptions.NotExistDiaryException;
import com.tomasfriends.bansikee_server.mypage.service.exceptions.NotExistMyPlantException;
import com.tomasfriends.bansikee_server.sign.domain.BansikeeUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService implements MyPlant {

    private final DiaryRepository diaryRepository;
    private final PictureRepository pictureRepository;
    private final MyPlantRepository myPlantRepository;

    @Transactional
    public void save(DiaryRequestDto diaryRequestDto) {
        BansikeeUser loginUser = AuthenticationUser.getAuthenticationUser();
        Optional<PlantRegistration> registeredPlant = myPlantRepository.findById(diaryRequestDto.getMyPlantId());
        Diary diary = diaryRequestDto.toDiaryEntity(registeredPlant.orElseThrow(NotExistMyPlantException::new), loginUser);

        diaryRepository.save(diary);
        savePictures(diaryRequestDto, diary);
        checkWatered(diaryRequestDto.getWatered(), diaryRequestDto.getMyPlantId());
        checkTodayDiary(diaryRequestDto.getMyPlantId());
    }

    private void savePictures(DiaryRequestDto diaryRequestDto, Diary diary) {
        List<DiaryPicture> diaryPictures = diaryRequestDto.toDiaryPictureEntities(diary);
        diaryPictures.forEach(pictureRepository::save);
    }

    private void checkWatered(Watered watered, int myPlantId) {
        if (watered.isWatered()) {
            myPlantRepository.checkLastWaterDate(myPlantId, LocalDate.now());
        }
    }

    private void checkTodayDiary(int myPlantId) {
        myPlantRepository.checkLastDiaryDate(myPlantId, LocalDate.now());
    }

    public List<DiaryListResponseDto> findAll(int myPlantId) {
        BansikeeUser loginUser = AuthenticationUser.getAuthenticationUser();
        List<Diary> allDiary = diaryRepository.findAllByMyPlantId(myPlantId);
        return allDiary.stream()
            .filter(diary -> diary.isWriter(loginUser, diary.getUser()))
            .map(t -> t.toListResponseDto(t.getId(), t.getFirstDiaryPicture(), t.getCreatedDate().toLocalDate()))
            .sorted(Comparator.comparing(DiaryListResponseDto::getDiaryId).reversed())
            .collect(Collectors.toList());
    }

    public DiaryResponseDto findDiary(Integer diaryId) {
        Optional<Diary> foundDiary = diaryRepository.findById(diaryId);
        Diary diary = foundDiary.orElseThrow(NotExistDiaryException::new);
        checkAuth(diary.getUser().getId());
        return diary.toDiaryResponseDto();
    }

    @Transactional
    public void delete(Integer diaryId) {
        getUser(diaryId);
        diaryRepository.deleteById(diaryId);
    }

    public void getUser(Integer diaryId) {
        int diaryOwnerId = diaryRepository.findById(diaryId)
            .orElseThrow(NotExistMyPlantException::new)
            .getUser()
            .getId();
        checkAuth(diaryOwnerId);
    }
}
