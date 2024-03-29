package com.tomasfriends.bansikee_server.mypage.controller;

import com.tomasfriends.bansikee_server.mypage.service.DiaryService;
import com.tomasfriends.bansikee_server.mypage.service.dto.req.DiaryRequestDto;
import com.tomasfriends.bansikee_server.mypage.service.dto.resp.DiaryListResponseDto;
import com.tomasfriends.bansikee_server.mypage.service.dto.resp.DiaryResponseDto;
import com.tomasfriends.bansikee_server.response.dto.ListDataSuccessResponse;
import com.tomasfriends.bansikee_server.response.dto.SingleDataSuccessResponse;
import com.tomasfriends.bansikee_server.response.dto.SuccessCode;
import com.tomasfriends.bansikee_server.response.dto.SuccessResponse;
import com.tomasfriends.bansikee_server.response.service.ResponseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = {"7. 일기"})
@RestController
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;
    private final ResponseService responseService;

    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "일지 작성", notes = "토큰 인증 필요")
    @PostMapping("/registration/diary")
    public ResponseEntity<SuccessResponse> registerDiary(@RequestBody @Valid DiaryRequestDto diaryRequestDto) {
        diaryService.save(diaryRequestDto);
        return responseService.getSuccessResult(SuccessCode.DIARY_REGISTER_SUCCESS);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "내식물의 다이어리 리스트 조회", notes = "토큰 인증 필요")
    @GetMapping("/diary/{myPlantId}")
    public ResponseEntity<ListDataSuccessResponse<DiaryListResponseDto>> findPlantDiary(@PathVariable("myPlantId") int myPlantId) {
        List<DiaryListResponseDto> myAllPlantList = diaryService.findAll(myPlantId);
        return responseService.getListResult(myAllPlantList, SuccessCode.MY_DIARY_READ_SUCCESS);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "일기 상세 조회", notes = "토큰 인증 필요")
    @GetMapping("/diary/plantDiary/{diaryId}")
    public ResponseEntity<SingleDataSuccessResponse<DiaryResponseDto>> registerPlant(@PathVariable("diaryId") Integer diaryId) {
        DiaryResponseDto diaryResponse = diaryService.findDiary(diaryId);
        return responseService.getSingleResult(diaryResponse, SuccessCode.DIARY_READ_SUCCESS);
    }

    @ApiImplicitParams({
        @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
    })
    @ApiOperation(value = "일기 삭제", notes = "토큰 인증 필요")
    @DeleteMapping("/diary/delete/{diaryId}")
    public ResponseEntity<SuccessResponse> deleteMyPlant(@PathVariable("diaryId") Integer diaryId) {
        diaryService.delete(diaryId);
        return responseService.getSuccessResult(SuccessCode.DIARY_DELETE_SUCCESS);
    }
}
