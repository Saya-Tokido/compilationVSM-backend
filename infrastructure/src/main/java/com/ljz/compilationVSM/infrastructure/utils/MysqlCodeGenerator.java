package com.ljz.compilationVSM.infrastructure.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.util.Map;

/**
 * MybatisPlus代码生成器
 *
 * @author ljz
 * @since 2025-01-16
 */
public class MysqlCodeGenerator {
    public static void main(String[] args) {
        String finalProjectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://10.16.40.100:3306/compilationVSM_backup?useSSL=false", "root", "root")
                .globalConfig(builder -> builder
                        .author("ljz")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/infrastructure/src/main/java")
                        .commentDate("yyyy-MM-dd HH:mm:ss")
                )
                .packageConfig(builder -> builder
                        .parent("com.ljz.compilationVSM.infrastructure")
                        .entity("po")
                        .service("repository")
                        .serviceImpl("repository.impl")
                        .mapper("mapper")
                        .xml("mapper")
                        .pathInfo(Map.of(
                                        OutputFile.xml, finalProjectPath + "/infrastructure/src/main/resources/mapper",
                                        OutputFile.controller, ""
                                )
                        )
                )
                .strategyConfig(builder -> builder
                        // 目前已有表名 t_ai_q_a,t_choose,t_fill,t_lexer,t_lexer_answer,t_lexer_code,t_lexer_p_d,t_lexer_testcase,t_method_body,t_method_name,t_method_testcase,t_obj_answer,t_student,t_teacher,t_user,t_config
                        .addInclude("t_lexer_p_d,t_config")
                        .addTablePrefix("t_")
                        .entityBuilder()
                        .enableFileOverride()
                        .formatFileName("%sPO")
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .controllerBuilder()
                        .disable()
                        .serviceBuilder()
                        .formatServiceFileName("%sRepository")
                        .formatServiceImplFileName("%sRepositoryImpl")
                        .mapperBuilder()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .build()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
