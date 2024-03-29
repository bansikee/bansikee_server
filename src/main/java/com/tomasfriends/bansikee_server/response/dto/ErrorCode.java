package com.tomasfriends.bansikee_server.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    INVALID_REQUEST_DATA(444, "Invalid request Data", "입력값을 형식에 맞게 넣어주세요. (이메일 형식, 빈값 불가)"),
    NOT_INVALID_JWT_TOKEN(419, "Invalid Token Value", "유효하지 않은 토큰입니다."),
    COMMUNICATION_ERROR(420, "Communication Error Occurred Or Invalid Token.", "유효하지 않은 엑세스토큰입니다."),
    NOT_REGISTERED_EMAIL(455, "Not registered email.", "해당 이메일로 가입된 계정이 없습니다."),
    NOT_SAME_PASSWORD_AND_REPEATED(450, "Password and repeated password are different.", "비밀번호와 확인이 같지 않습니다."),
    INVALID_PASSWORD(451, "Password is Invalid.", "비밀번호가 틀렸습니다."),
    ALREADY_EXIST_EMAIL(452, "The email is already exist.", "이미 가입된 이메일입니다."),
    HAVE_TO_SIGN_IN_WITH_OAUTH(460, "Need to Sign in with Oauth.", "소셜 로그인 회원입니다."),
    NOT_REGISTERED_ID(455, "Not registered id.", "해당 아이디인 계정이 없습니다.(이미 탈퇴한 회원의 토큰입니다.)"),
    NOT_EXIST_PLANT_IN_MYPAGE(480, "Not a My Plant", "등록된 식물이 아닙니다."),
    NOT_VALID_AUTH(580, "Have not Auth.", "수정 권한이 없습니다."),
    NOT_EXIST_DIARY(499, "Not Exist Diary.", "존재하지 않는 일기입니다."),
    NOT_EXIST_PLANT(476, "Not Exist Plant.", "존재하지 않는 식물입니다.");

    private final int status;
    private final String title;
    private final String detail;
}
