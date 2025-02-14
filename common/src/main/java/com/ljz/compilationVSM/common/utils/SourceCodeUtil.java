package com.ljz.compilationVSM.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
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
@RequiredArgsConstructor
public class SourceCodeUtil {

    private final MessageEncodeUtil messageEncodeUtil;

    /**
     * 源代码记录行号前缀
     */
    @Value("${oj.source-code.delimiter}")
    private String sourceCodeDelimiter;

    @Value("${oj.lexer.pd.delimiter}")
    private String pdDelimiter;

    private static final List<String> keywords = List.of(
            "int", "char", "float", "double", "long", "short", "void", "if", "else", "for", "while", "return", "break",
            "continue", "switch", "case", "default", "struct", "class", "public", "private", "protected", "try", "catch"
    );

    /**
     * 将源代码处理后转化为紧凑的入库代码字符串
     *
     * @param sourceCode 源代码字符串
     * @return 入库代码字符串
     */
    public String toSqlCode(String sourceCode) {
        return String.join(sourceCodeDelimiter, handleSourceCode(sourceCode));
    }

    /**
     * 将mysql中紧凑代码转化为代码行列表
     *
     * @param sqlCode sql中的紧凑代码
     * @return 解析后的代码行列表
     */
    public List<String> toCodeLine(String sqlCode) {
        return Arrays.stream(sqlCode.split(sourceCodeDelimiter, -1)).toList();
    }

    /**
     * 将数据库中的源代码转化为映射后的入库字符串
     *
     * @param sqlCode 数据库源代码
     * @return 映射后的入库字符串
     */
    public String toSqlCodeMap(String sqlCode) {
        List<String> codeLine = toCodeLine(sqlCode);
        return codeLine.stream()
                .filter(str -> !str.matches("[\\s,;'\"]*[{()}]*"))
                .map(this::replaceVariablesAndFunctions)
                .map(item -> item.replaceAll("\\R", ""))
                .map(messageEncodeUtil::encode16)
                .collect(Collectors.joining(pdDelimiter));
    }

    /**
     * 将数据库中映射字符串解析为映射数组
     *
     * @param mapStr 映射字符串
     * @return 映射数组
     */
    public String[] mapStr2mapList(String mapStr) {
        return mapStr.split(pdDelimiter, -1);
    }

    /**
     * 将代码中的变量和函数用var替代
     *
     * @param line 代码行字符串
     * @return 处理后的字符串
     */
    private String replaceVariablesAndFunctions(String line) {
        // 正则表达式：匹配潜在的变量名和函数名
        String regex = "\\b([a-zA-Z_][a-zA-Z0-9_]*)\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(line);

        StringBuilder result = new StringBuilder();
        while (matcher.find()) {
            String match = matcher.group(1);
            // 如果是关键字，则不替换
            if (keywords.contains(match)) {
                matcher.appendReplacement(result, match);
            } else {
                // 否则替换为 var
                matcher.appendReplacement(result, "var");
            }
        }
        matcher.appendTail(result);
        return result.toString();
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

        return Arrays.stream(result2.split("\\R", -1)).toList();
    }
}
