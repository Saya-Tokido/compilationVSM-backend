package com.ljz.compilationVSM.util;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.util.Map;

public class MysqlCodeGenerator {
    public static void main(String[] args) {
        String finalProjectPath = System.getProperty("user.dir");
        FastAutoGenerator.create("jdbc:mysql://10.16.40.100:3306/compilationVSM?useSSL=false", "root", "root")
                .globalConfig(builder -> builder
                        .author("ljz")
                        .outputDir(Paths.get(System.getProperty("user.dir")) + "/src/main/java")
                        .commentDate("yyyy-MM-dd HH:mm:ss")
                )
                .packageConfig(builder -> builder
                        .parent("com.ljz.compilationVSM")
                        .entity("entity")
                        .service("service")
                        .serviceImpl("service.impl")
                        .mapper("dao")
                        .xml("dao")
                        .pathInfo(Map.of(
                                        OutputFile.xml, finalProjectPath + "/src/main/resources/com/ljz/compilationVSM/dao",
                                        OutputFile.controller, ""
                                )
                        )
                )
                .strategyConfig(builder -> builder
                        .addInclude("t_ai_q_a")
                        .addTablePrefix("t_")
                        .entityBuilder()
                        .formatFileName("%sPO")
                        .enableLombok()
                        .enableTableFieldAnnotation()
                        .enableFileOverride()
                        .controllerBuilder()
                        .disable()
                        .serviceBuilder()
                        .formatServiceFileName("%sRepository")
                        .formatServiceImplFileName("%sRepositoryImpl")
                        .enableFileOverride()
                        .mapperBuilder()
                        .formatMapperFileName("%sMapper")
                        .formatXmlFileName("%sMapper")
                        .enableFileOverride()
                        .build()
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
