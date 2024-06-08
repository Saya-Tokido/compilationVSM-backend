package com.ljz.compilationVSM.commonTest;

import com.ljz.compilationVSM.BaseTest;
import com.ljz.compilationVSM.entity.AiQAPO;
import com.ljz.compilationVSM.service.AiQARepository;
import com.ljz.compilationVSM.util.GzipUtil;
import com.ljz.compilationVSM.util.SnowflakeIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Slf4j
public class AiQAQuestionTest extends BaseTest {
    @Autowired
    private AiQARepository aiQARepository;

    @Test
    public void test(){
        AiQAPO aiQAPO =new AiQAPO();
        aiQAPO.setId(SnowflakeIdGenerator.generate());
        aiQAPO.setQuestion("问题10：能否教我用C语言写一个词法分析器中的函数“addError(message):将错误信息记录到日志或错误处理器中”？");
        String answer = "当然可以。以下是用C语言编写的`addError`函数示例。在此示例中，我们使用`fprintf`函数将错误信息写入到标准错误流`stderr`中。\n" +
                "\n" +
                "```c\n" +
                "#include <stdio.h>\n" +
                "\n" +
                "void addError(const char *message) {\n" +
                "    fprintf(stderr, \"[Error] %s\\n\", message);\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "这个函数将`message`参数的值插入到`[Error] %s\\n`占位符中，然后将格式化后的字符串写入`stderr`。\n" +
                "\n" +
                "在中文中，这个函数可以这样解释：此函数将错误信息（以字符串形式提供）添加到错误处理器中。在这个示例中，错误信息被写入到标准错误流中。您可以根据需要修改此函数以满足您的特定需求。例如，您可以将错误信息写入到一个文件中，或使用日志库来记录错误信息。";
        try{
            aiQAPO.setAnswer(GzipUtil.compressData(answer));
        }catch(IOException e){
            log.error("error",e);
        }
        aiQARepository.save(aiQAPO);

    }
}
