DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`          bigint NOT NULL COMMENT '主键id',
    `user_name`   varchar(20)  DEFAULT '' COMMENT '用户名',
    `password`    varchar(256) DEFAULT '' COMMENT '密码',
    `role`        int(11) DEFAULT 0 COMMENT '角色类型',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '用户表';
CREATE INDEX index_t_user_user_name ON t_user (user_name);


DROP TABLE IF EXISTS `t_student`;
CREATE TABLE `t_student`
(
    `id`           bigint NOT NULL COMMENT '主键id',
    `user_id`      bigint      DEFAULT 0 COMMENT '账号id',
    `number`       bigint      DEFAULT 0 COMMENT '学号',
    `name`         varchar(50) DEFAULT '' COMMENT '姓名',
    `admin_class`  varchar(20) DEFAULT '' COMMENT '行政班',
    `teach_class`  varchar(20) DEFAULT '' COMMENT '教学班',
    `obj_grade`    int(11) DEFAULT 0 COMMENT '客观题成绩',
    `method_grade` int(11) DEFAULT 0 COMMENT '函数题成绩',
    `lexer_grade`  int(11) DEFAULT 0 COMMENT '词法分析题成绩',
    `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`    tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '学生表';
CREATE INDEX index_t_student_number_name ON t_student (number, name);


DROP TABLE IF EXISTS `t_teacher`;
CREATE TABLE `t_teacher`
(
    `id`          bigint NOT NULL COMMENT '主键id',
    `user_id`     bigint       DEFAULT 0 COMMENT '账号id',
    `number`      bigint       DEFAULT 0 COMMENT '工号',
    `name`        varchar(50)  DEFAULT '' COMMENT '姓名',
    `class_list`  varchar(100) DEFAULT '' COMMENT '所带教学班列表',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '教师表';


DROP TABLE IF EXISTS `t_choose`;
CREATE TABLE `t_choose`
(
    `id`          bigint NOT NULL COMMENT '主键id',
    `title`       varchar(200) DEFAULT '' COMMENT '题目',
    `choice0`     varchar(40)  DEFAULT '' COMMENT '选项0',
    `choice1`     varchar(40)  DEFAULT '' COMMENT '选项1',
    `choice2`     varchar(40)  DEFAULT '' COMMENT '选项2',
    `choice3`     varchar(40)  DEFAULT '' COMMENT '选项3',
    `key_answer`  varchar(40)  DEFAULT '' COMMENT '正确选项号和内容',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '选择题表';


DROP TABLE IF EXISTS `t_fill`;
CREATE TABLE `t_fill`
(
    `id`          bigint NOT NULL COMMENT '主键id',
    `title`       varchar(200) DEFAULT '' COMMENT '题目',
    `key_answer`  varchar(20)  DEFAULT '' COMMENT '答案内容',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '填空题表';



DROP TABLE IF EXISTS `t_obj_answer`;
CREATE TABLE `t_obj_answer`
(
    `id`                 bigint NOT NULL COMMENT '主键id',
    `user_id`            bigint       DEFAULT 0 COMMENT '学生用户id',
    `choose_id_list`     varchar(300) DEFAULT '' COMMENT '选择题id列表',
    `fill_id_list`       varchar(300) DEFAULT '' COMMENT '填空题id列表',
    `choose_answer_list` varchar(500) DEFAULT '' COMMENT '选择题答题选项内容列表',
    `fill_answer_list`   varchar(500) DEFAULT '' COMMENT '填空题答题内容列表',
    `choose_grade`       int(11) DEFAULT 0 COMMENT '选择题成绩',
    `fill_grade`         int(11) DEFAULT 0 COMMENT '填空题成绩',
    `create_time`        timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`          tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '客观题答题情况表';



DROP TABLE IF EXISTS `t_lexer_answer`;
CREATE TABLE `t_lexer_answer`
(
    `id`           bigint NOT NULL COMMENT '主键id',
    `user_id`      bigint DEFAULT 0 COMMENT '学生用户id',
    `lexer_id`     bigint DEFAULT 0 COMMENT '词法分析器题目',
    `last_code_id` bigint DEFAULT 0 COMMENT '有得分的最后一次提交的代码id',
    `best_code_id` bigint DEFAULT 0 COMMENT '有得分的最高成绩的代码id',
    `grade`        int(11) DEFAULT 0 COMMENT '成绩',
    `create_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`    tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '词法分析器题成绩表';


DROP TABLE IF EXISTS `t_lexer_code`;
CREATE TABLE `t_lexer_code`
(
    `id`          bigint NOT NULL COMMENT '主键id',
    `code`        text COMMENT '完整代码',
    `code_map`    varchar(6000) DEFAULT '' COMMENT '查重代码映射',
    `row_num`     int(11) DEFAULT 0 COMMENT '代码行数',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '词法分析器代码表';


DROP TABLE IF EXISTS `t_lexer`;
CREATE TABLE `t_lexer`
(
    `id`            bigint NOT NULL COMMENT '主键id',
    `language`      varchar(10)  DEFAULT '' COMMENT '编程语言',
    `comp_language` varchar(10)  DEFAULT '' COMMENT '待编程语言',
    `description`   varchar(100) DEFAULT '' COMMENT '题目描述',
    `commit_num`    bigint       DEFAULT 0 COMMENT '提交次数',
    `pass_num`      bigint       DEFAULT 0 COMMENT '通过次数',
    `create_time`   timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`   timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`     tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '词法分析器题目表';



DROP TABLE IF EXISTS `t_lexer_testcase`;
CREATE TABLE `t_lexer_testcase`
(
    `id`              bigint NOT NULL COMMENT '主键id',
    `lexer_id`        bigint        DEFAULT 0 COMMENT '编译器id',
    `terminal_input`  varchar(3000) DEFAULT '' COMMENT '终端输入',
    `terminal_output` varchar(3000) DEFAULT '' COMMENT '终端输出',
    `create_time`     timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`       tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT = '词法分析器用例表';



DROP TABLE IF EXISTS `t_lexer_p_d`;
CREATE TABLE `t_lexer_p_d`
(
    `id`                 bigint NOT NULL COMMENT '主键id',
    `lexer_id`           bigint        DEFAULT 0 COMMENT '词法分析器题目id',
    `plagiarism_code_id` bigint        DEFAULT 0 COMMENT '查重代码id',
    `comp_code_id`       bigint        DEFAULT 0 COMMENT '参照代码id',
    `fill_grade`         decimal(6, 3) DEFAULT 0.0 COMMENT '填空题成绩',
    `create_time`        timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`        timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`          tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT '词法分析器代码查重表,Plagiarism detection';



DROP TABLE IF EXISTS `t_ai_q_a`;
CREATE TABLE `t_ai_q_a`
(
    `id`          bigint NOT NULL COMMENT '主键id',
    `question`    varchar(200) DEFAULT '' COMMENT '问题',
    `answer`      blob COMMENT '回答',
    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`   tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '智能问答表';



DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config`
(
    `id`                  bigint NOT NULL COMMENT '主键id',
    `lexer_deadline`      DATETIME COMMENT '词法分析器题截止日期',
    `last_update_user_id` bigint DEFAULT 0 COMMENT '最后更新人id',
    `create_time`         timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`         timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `is_delete`           tinyint(1) DEFAULT 0 COMMENT '逻辑删除标志，0为未删除，1为删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT '配置表';


/***********************************   函数体暂不维护    ****************************/


DROP TABLE IF EXISTS `t_method_body`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_method_body`
(
    `id`             bigint NOT NULL,
    `method_id`      bigint        DEFAULT NULL,
    `description`    varchar(1000) DEFAULT NULL,
    `input`          varchar(200)  DEFAULT NULL,
    `output`         varchar(200)  DEFAULT NULL,
    `in_param`       varchar(200)  DEFAULT NULL,
    `out_param`      varchar(200)  DEFAULT NULL,
    `global_var`     varchar(200)  DEFAULT NULL,
    `changed_global` varchar(200)  DEFAULT NULL,
    `pre_method`     varchar(200)  DEFAULT NULL,
    `body`           varchar(300)  DEFAULT NULL,
    `create_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`    timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_delete`      tinyint(1) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `t_method_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `t_method_name`
(
    `id`            bigint NOT NULL,
    `language`      varchar(10)  DEFAULT NULL,
    `comp_language` varchar(10)  DEFAULT NULL,
    `name`          varchar(100) DEFAULT NULL,
    `level`         varchar(5)   DEFAULT NULL,
    `commit_num`    bigint       DEFAULT NULL,
    `pass_num`      bigint       DEFAULT NULL,
    `create_time`   timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`   timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_delete`     tinyint(1) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;



DROP TABLE IF EXISTS `t_method_testcase`;
CREATE TABLE `t_method_testcase`
(
    `id`              bigint NOT NULL COMMENT "用例id",
    `method_id`       bigint        DEFAULT NULL COMMENT "函数id",
    `pre_code`        varchar(2500) DEFAULT NULL COMMENT "校验时前置代码",
    `terminal_input`  varchar(300)  DEFAULT NULL COMMENT "终端输入",
    `terminal_output` varchar(300)  DEFAULT NULL COMMENT "终端输出",
    `create_time`     timestamp NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time`     timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `is_delete`       tinyint(1) DEFAULT 0,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;




