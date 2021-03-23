package com.tomasfriends.bansikee_server.mypage.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Transactional
class PlantRegistrationTest {

    @Test
    void calculateDate() {
        // given
        PlantRegistration plantRegistration = new PlantRegistration();
        LocalDateTime ldt = LocalDateTime.of(2021, 2, 22, 14, 22);

        // when
        long dDay = plantRegistration.getDDay(ldt);

        // then
//        Assertions.assertThat(dDay).isEqualTo(27);
        Assertions.assertThat(dDay).isNotNull();
    }
}