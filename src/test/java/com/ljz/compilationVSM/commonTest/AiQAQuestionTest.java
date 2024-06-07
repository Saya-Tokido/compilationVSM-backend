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
        aiQAPO.setQuestion("问题9：能否教我用C语言写一个词法分析器中的函数“isDelimiter(token):判断一个单词是否是分隔符”？");
        String answer = "当然可以。在C语言中，一个分隔符可以是空格、制表符、回车等。下面是实现isDelimiter函数的C代码：\n" +
                "\n" +
                "```c\n" +
                "#include <stdbool.h> // for bool, true, false\n" +
                "\n" +
                "// Function to check if a token is a delimiter\n" +
                "bool isDelimiter(char token) {\n" +
                "    // We will use ASCII values to check if the token is a delimiter\n" +
                "    switch (token) {\n" +
                "        case'': // Space\n" +
                "        case '\\t': // Tab\n" +
                "        case '\\n': // Newline\n" +
                "        case '\\r': // Carriage return\n" +
                "            return true;\n" +
                "        default:\n" +
                "            return false;\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "在上面的代码中，我们使用switch-case结构来检查字符的ASCII值。如果ASCII值在空格、制表符、换行符或回车符的ASCII值中，则函数返回true，表示该字符是分隔符。否则，返回false。\n" +
                "\n" +
                "请注意，本示例中只检查了空格、制表符、换行符和回车符。如果需要检查更多的分隔符，可以根据需要添加到switch-case结构中。\n";
        try{
            aiQAPO.setAnswer(GzipUtil.compressData(answer));
        }catch(IOException e){
            log.error("error",e);
        }
        aiQARepository.save(aiQAPO);

    }
}
