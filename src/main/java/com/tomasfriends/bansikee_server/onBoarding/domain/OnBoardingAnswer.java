package com.tomasfriends.bansikee_server.onBoarding.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@Data
@IdClass(OnBoardingAnswerPK.class)
public class OnBoardingAnswer {
    @Id
    private Integer userIdx;

    @Id
    private Integer questionIdx;

    private Integer optionIdx;

    public static OnBoardingAnswer of(Integer userIdx, Integer questionIdx, Integer optionIdx) {
        OnBoardingAnswer onBoardingAnswer = new OnBoardingAnswer();
        onBoardingAnswer.userIdx = userIdx;
        onBoardingAnswer.questionIdx = questionIdx;
        onBoardingAnswer.optionIdx = optionIdx;
        return onBoardingAnswer;
    }



//    public static OnBoardingAnswer of(Integer userIdx, Integer questionIdx, Integer optionIdx) {
//        OnBoardingAnswer onBoardingAnswer = new OnBoardingAnswer();
//        onBoardingAnswer.userIdx = userIdx;
//        onBoardingAnswer.questionIdx = questionIdx;
//        onBoardingAnswer.optionIdx = optionIdx;
//        return onBoardingAnswer;
//    }
}
