package com.tomasfriends.bansikee_server.mypage.service.dto.resp;

import com.tomasfriends.bansikee_server.mypage.service.dto.Watered;
import com.tomasfriends.bansikee_server.mypage.service.dto.Weather;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class DiaryResponseDto {

    private final int myDiaryId;
    private final List<String> diaryPictures;
    private final long dayFromBirth;
    private final String nickName;
    private final Weather weather;
    private final Watered watered;
    private final double height;
    private final LocalDateTime writeDate;
    private final String contents;
}
