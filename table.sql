drop table if exists user,question,choose,fill,grade,log,context,question_index,method_name,method_body;

create table user(
                     id bigint auto_increment primary key ,
                     user_name varchar(20),
                     password varchar(256)
);
insert into user(user_name,password) values("mitsuha","$2a$10$gezWANeSw8bcYD1y0Me13eDm6kWn6DCyhd00Of37.2ZKlzwzqDwHi");



create table question(
                         id bigint auto_increment primary key ,
                         title varchar(100),
                         choice1 varchar(100) comment '标准答案' ,
                         choice2 varchar(100),
                         choice3 varchar(100),
                         choice4 varchar(100)
);

create table context(
                        id bigint auto_increment primary key ,
                        content text
);


create table question_index(
                               id bigint auto_increment primary key ,
                               context_id bigint,
                               question_id bigint,
                               type varchar(20),
                               language varchar(20)
);


create table log(
                    id bigint auto_increment primary key ,
                    type varchar(20),
                    time datetime,
                    details varchar(150),
                    user_id bigint
);


create table grade(
                      id bigint auto_increment primary key,
                      score tinyint,
                      user_id bigint,
                      experiment_id tinyint,
                      difficulty varchar(20)
);

create table choose(
                       id bigint auto_increment primary key ,
                       title varchar(200),
                       choice0 varchar(40),
                       choice1 varchar(40),
                       choice2 varchar(40),
                       choice3 varchar(40),
                       key_answer varchar(40)
);

create table fill(
                     id bigint auto_increment primary key ,
                     title varchar(200),
                     key_answer varchar(20)
);

insert into choose(title,choice0,choice1,choice2,choice3,key_answer)
values("q0","apple","pear","orange","banana","apple"),
      ("q1","app1le","pe1ar","ora1nge","ba1nana","app1le"),
      ("q2","ap2le","pe2ar","ora2nge","bana2na","ap2ple"),
      ("q3","app3le","pe3ar","or3ange","bana3na","app3le"),
      ("q4","app4le","pe4ar","ora3n4ge","ban4ana","app4le"),
      ("q5","appl5e","pe5ar","oran5ge","ba54nana","app5le"),
      ("q6","apple6","pear6","orange6","banana6","apple6"),
      ("q7","apple7","pear7","orange7","banana7","apple7"),
      ("q8","apple8","pear8","orange8","banana8","apple8"),
      ("q9","apple9","pear9","orange9","banana9","apple9");




insert into fill(title,key_answer)
values("1+1","2"),
      ("实验名称","词法分析器"),
      ("apple是什么意思","苹果"),
      ("g为多少","9.8"),
      ("性别","男性"),
      ("int是什么类型","整型"),
      ("词法分析后是什么","语法分析"),
      ("java编译后生成什么","字节码文件");



insert into choose(title,choice0,choice1,choice2,choice3,key_answer)
values ("编译程序绝大多数时间花在什么上","词法分析","语法分析","语义分析","表格管理","表格管理"),
       ("汇编程序是将___ 翻译成__;编译程序是将____翻译成_____。①高级语言②汇编语言③机器语言④高级语言或汇编语言⑤汇编语言或机器语言","①③①⑤","②③①⑤","④③①③","②③①③","②③①⑤"),
       ("给定文法G[A]：A→bA|cc ____是该文法的句子。","bcbc","bcbcc","bccbcc","bbbcc","bbbcc"),
       ("已知语言{anbnci | n>=1,i>=0 }，则下述文法中，______可以产生该语言。","S→AB, A→aAb|ab, B→cB|c","S→aAb, A→aBb, B→cB|c","S→AB, A→aAb|ab, B→cB|ε","S→aSb|A, A→bAc|c","S→AB, A→aAb|ab, B→cB|ε"),
       ("在通常的语法分析方法中，___特别适用于表达式的分析。","LR(1)分析法","LL(1)分析法","递归下降分析法","算符优先分析法","算符优先分析法"),
       ("在DFA的最小化过程中，初始应该把集合根据____划分子集。","初态和非初态","终态和非终态","是否含有ε出边","是否有出边","终态和非终态"),
       ("基本块内的优化为___","代码外提，删除归纳变量","强度削弱，代码外提","删除多余运算，删除无用赋值","循环展开，循环合并","删除多余运算，删除无用赋值"),
       ("若文法G定义的语言是无限集，则文法必然是___","递归的","二义性的","前后文无关的","无二义性的","递归的"),
       ("下列____优化方法不是针对循环优化进行的。","强度削弱","删除归纳变量","删除公共子表达式","代码外提","删除公共子表达式"),
       ("错误的局部化是指____","把错误理解成局部的错误","对错误在局部范围内进行纠正","当发现错误时，跳过错误所在的语法单位继续分析下去","当发现错误时立即停止编译，待用户改正错误后再继续编译","当发现错误时，跳过错误所在的语法单位继续分析下去");




create table method_name(
                       id int primary key auto_increment,
                       language varchar(10),
                       comp_language varchar(10),
                       name varchar(100),
                       level tinyint,
                       commit_num bigint,
                       pass_num bigint
);


insert  into method_name(language,comp_language, name,level,commit_num,pass_num)
values ("c","PL0","void error(int n)",0,4234242,3242),
       ("c","PL0","int getsym()",2,4244234242,3242342),
       ("c","PL0","int getch()",1,423214242,32342),
       ("c","PL0","void init()",1,421134242,113242),
       ("c","PL0","int gen(enum fct, int y, int z)",2,411234242,31111242),
       ("c","PL0","int test(bool * sl, bool * s2, int n)",1,421134242,321134242),
       ("c","PL0","int inset(int e, bool * s)",1,421134242,321134242),
       ("c","PL0","int addset(bool * sr, bool * sl, bool * s2, int n)",1,421134242,321134242),
       ("c","PL0","int subset(bool * sr, bool * sl, bool * s2, int n)",1,421134242,321134242),
       ("c","PL0","int mulset(bool * sr, bool * sl, bool * s2, int n)",1,421134242,321134242),
       ("c","PL0","int block(int lev, int tx, bool * fsys)",1,421134242,321134242),
       ("c","PL0","void interpret()",1,421134242,321134242),
       ("c","PL0","int factor(bool * fsys, int * ptx, int lev)",1,421134242,321134242),
       ("c","PL0","int term(bool * fsys, int * ptx, int lev)",1,421134242,321134242),
       ("c","PL0","int condition(bool * fsys, int * ptx, int lev)",1,421134242,321134242),
       ("c","PL0","int expression(bool * fsys, int * ptx, int lev)",1,421134242,321134242),
       ("c","PL0","int statement(bool * fsys, int * ptx, int lev)",1,421134242,321134242),
       ("c","PL0","void listcode(int cx0)",1,421134242,321134242),
       ("c","PL0","int vardeclaration(int * ptx, int lev, int * pdx)",1,421134242,321134242),
       ("c","PL0","int constdeclaration(int * ptx, int lev, int * pdx)",1,421134242,321134242),
       ("c","PL0","int position(char * idt, int tx)",1,421134242,321134242),
       ("c","PL0","void enter(enum object k, int * ptx, int lev, int * pdx)",1,421134242,321134242),
       ("c","PL0","int base(int l, int * s, int b)",1,421134242,321134242);



create table method_body(
                            id int primary key auto_increment,
                            method_id int,
                            description varchar(200),
                            input varchar(200),
                            output varchar(200),
                            in_param varchar(200),
                            out_param varchar(200),
                            body varchar(300),
                            check_body varchar(200)
);

insert into method_body(method_id,description,input,output,in_param,out_param,body,check_body)
values(3,"get a char and assign to c","int a = 8;","","char c","char c","int getch(){}","c-->i");



