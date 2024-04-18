package com.djawnstj.mvcframework.context;

import com.djawnstj.mvcframework.code.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextTest {

    private final ApplicationContext applicationContext = new ApplicationContext("com.djawnstj.mvcframework");

    @DisplayName("애너테이션 기반 컴포넌트 스캔을 진행한다")
    @Test
    public void componentScan() throws Exception {
        // when
        applicationContext.initialize();

        // then
        assertAll(
                () -> assertThat(applicationContext.getBean(LogUtils.class)).isNotNull(),
                () -> assertThat(applicationContext.getBean(TestConfig.class)).isNotNull(),
                () -> assertThat(applicationContext.getBean(UserConfig.class)).isNotNull(),
                () -> assertThat(applicationContext.getBean(UserRepository.class)).isNotNull(),
                () -> assertThat(applicationContext.getBean(UserService.class)).isNotNull(),
                () -> assertThat(applicationContext.getBean(UserTestCode.class)).isNotNull()
        );
    }

}
