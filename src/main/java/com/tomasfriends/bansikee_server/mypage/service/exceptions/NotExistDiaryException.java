package com.tomasfriends.bansikee_server.mypage.service.exceptions;

public class NotExistDiaryException extends RuntimeException {

    public NotExistDiaryException(String msg, Throwable t) {
        super(msg, t);
    }

    public NotExistDiaryException(String msg) {
        super(msg);
    }

    public NotExistDiaryException() {
        super();
    }
}
