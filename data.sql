insert into `t_method_body`(id,method_id,description,input,output,in_param,out_param,global_var,changed_global,pre_method,body)
values(31231412312,3,"获取一个字符，返回值字符的ASCII值","int a = 1;","","","105","","","","int getch(){}");


insert into `t_method_testcase`(id,method_id,pre_code,terminal_input,terminal_output)
values(3116114,
       3,
       '#include<iostream>\n#include<cstdio>\nusing namespace std;\nint getch();\nint main(){\n    int output = getch();\n    cout<<endl;\n    cout<<"fhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofahwjioefhjo"<<endl;\n   cout<<output<<endl;\n    cout<<"fhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofahwjioefhjo"<<endl;\n}\n',
       'int i = 8;',
       '\nfhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofahwjioefhjo\n105\nfhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofahwjioefhjo\n'
      );


insert into `t_lexer`(id, language, comp_language, description, commit_num, pass_num)
values (1,"C++","PL0", "描述", 1,0),
       (2,"C++","Cangjie", "", 1,0);