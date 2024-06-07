package com.ljz.compilationVSM;

import com.ljz.compilationVSM.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CompilationVSMApplication.class})
public class BaseTest {
    @Test
    public void test() throws BizException {
        log.info("测试启动正常");
    }

}
