package com.ljz.compilationVSM.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * C++源代码处理工具类
 *
 * @author ljz
 * @since 2025-01-17
 */
@Slf4j
@Component
public class SourceCodeUtil {

    /**
     * 源代码记录行号前缀
     */
    @Value("${oj.source-code-delimiter.prefix}")
    private String sourceCodeDelimiterPrefix;

    /**
     * 源代码记录行号后缀
     */
    @Value("${oj.source-code-delimiter.suffix}")
    private String sourceCodeDelimiterSuffix;

    public static void main(String[] args) {
        String codeString = """
                int main() {
                // This is a line comment
                 std::string str = \"Hello, World!\";// linecomment
                 std::cout << str << std::endl; /* This is a block comment */
                 return 0;
                }
                """;
        SourceCodeUtil sourceCodeUtil = new SourceCodeUtil();
        // 入库代码
        String sqlCode = sourceCodeUtil.toSqlCode(codeString);
        System.out.println(sqlCode);
        // 展示代码
        List<String> codeLine = sourceCodeUtil.toCodeLine(sqlCode);
        codeLine.forEach(System.out::println);
    }

    /**
     * 将源代码处理后转化为紧凑的入库代码字符串
     *
     * @param sourceCode 源代码字符串
     * @return 入库代码字符串
     */
    public String toSqlCode(String sourceCode) {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        return handleSourceCode(sourceCode).stream()
                .map(line -> sourceCodeDelimiterPrefix + atomicInteger.getAndIncrement() + sourceCodeDelimiterSuffix + line)
                .collect(Collectors.joining());
    }

    /**
     * 将mysql中紧凑代码转化为代码行链表
     *
     * @param sqlCode sql中的紧凑代码
     * @return 解析后的代码行链表
     */
    public List<String> toCodeLine(String sqlCode) {
        return Arrays.stream(sqlCode.split(sourceCodeDelimiterPrefix + "\\d+" + sourceCodeDelimiterSuffix))
                // 去除首个分隔符前的空字符串
                .skip(1)
                .toList();
    }

    /**
     * 处理源代码
     *
     * @param codeString 源代码
     * @return 代码行列表
     */
    private List<String> handleSourceCode(String codeString) {
        // 去除注释
        String pattern = "(//.*)|(/\\*(.|\\R)*?\\*/)|(^\\s*$)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(codeString);
        String result1 = m.replaceAll("");
        // 去除空行
        Pattern r2 = Pattern.compile("^\\s*\\n", Pattern.MULTILINE);
        Matcher m2 = r2.matcher(result1);
        String result2 = m2.replaceAll("");

        return Arrays.stream(result2.split("\\R")).toList();
    }
}
