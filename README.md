# 编译原理实验系统后端说明文档



## 测试说明

### 测试用户

```text
用户名
admin
密码
z*y84m)G]p
```

### 函数体

`int getch()`

函数`id` 为3





### 函数代码编译

目前函数代码编译包括四个部分

1. 题目获取
2. 函数选择
3. 代码校验
4. 代码优化



#### 题目获取

目前只支持通过C语言编写PL0程序的词法分析器

题目通过函数标签来区分



#### 函数选择

程序编译的考察为函数体编写，通过给出函数体及提示来编写代码

对于一个函数实现的题目，需要给出的条件如下

1. 题目描述
2. 终端输入
3. 终端输出
4. 函数入参
5. 函数返回值
6. 需要用到的全局变量
7. 需要修改的全局变量
8. 前置函数签名

题目描述必须清楚，详细说明后面6种需要用到的变量用途和函数如何改变这些变量，以及前置函数的用途



#### 代码校验

代码校验同OJ系统，黑盒测试，提供多组用例来预防侥幸过关

```c++
int global1=1,global2=2;

void fun1(int param1);

int fun2(int param1);

int myFun(int input1,int input2){
    // do something...
    //cin>>abab
    //cout<<baba
    return result;
}
```

以上程序牵涉到6种变量以及前置函数，校验方法如下

```c++
// 导入万能库
#include<bits/stdc++.h>
int preFun(int input, int input2){
    //模拟前置函数
}
int global1;
int changedGlobal;
int myFun(int, int);
int main(){
    int output = myFun(111,222);
    //myFun(111,222);
 	// 如果无返回值
    /**
     *
     * 此处的终端输出为测试的函数输出
     *
     */
    // 分隔符
    cout<<"\nfhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofah\nwjioefhjo\n\n\n\n"；
    cout<<output<<endl;
    // cout<<output.var1<<endl;
    // 或类似的检验方法
    /**
     *
     * 此处的终端输出为函数返回值
     *
     */
    // 分隔符
    cout<<"\nfhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofah\nwjioefhjo\n\n\n\n"；
    /**
     *
     * 此处的终端输出为全局变量的修改，检验方法同上函数返回值
     *
     */
 
}

```

在能够成功编译的前提下，需要校验的有终端输出、函数返回值、需要修改的全局变量

所以对远程编译器提供输入，将上述代码块与用户代码拼接，然后分别校验上述代码三个模块的输出即可进行代码评估。







对于int getch(); 拼接结果如下

```c++
#include<iostream>
#include<cstdio>
using namespace std;

int getch();
int main(){
    int output = getch();
    cout<<endl;
    cout<<"fhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofahwjioefhjo"<<endl;
    cout<<output<<endl;
    cout<<"fhj……*……*（w4oqqh53fowhgfowhfoa…^hjf￥……oahwgofahwjioefhjo"<<endl;
}

int getch(void) {
    return getchar();
}
```











## 代码查重

查重逻辑未使用AST，方法较为简易



1. 将所有变量名，包括函数名，全部替换为var，去掉所有空格和注释，但保留关键字和运算符等，将除了花括号的每一行的代码字符串转化为hash(即使在不同机器上，相同字符串的hash也是一样的)存入set
2. 两个set取交集，行数超过指定值即为疑似重复
3. 选取学号前一位和后一位以及上一次提交的代码(如果有的话)来进行查重



```java
// 参考代码，待验证


import java.util.regex.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CodeDeobfuscator {

    // 去除单行注释
    private static String removeSingleLineComments(String code) {
        return code.replaceAll("//.*", "");
    }

    // 去除多行注释
    private static String removeMultiLineComments(String code) {
        return code.replaceAll("/\\*.*?\\*/", "", Pattern.DOTALL);
    }

    // 替换变量名和函数名为 'var'
    private static String replaceIdentifiers(String code) {
        // 仅替换变量名和函数名（不影响关键字）
        code = code.replaceAll("\\b([a-zA-Z_][a-zA-Z0-9_]*)\\b", "var");
        return code;
    }

    // 移除多余空格
    private static String removeWhitespace(String code) {
        return code.replaceAll("\\s+", "");
    }

    // 计算字符串的 MD5 哈希值
    private static String computeHash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 处理代码
    public static Set<String> processCode(String code) {
        Set<String> hashes = new HashSet<>();
        String[] lines = code.split("\n");

        for (String line : lines) {
            // 移除注释和空白
            String normalizedCode = removeSingleLineComments(line);
            normalizedCode = removeMultiLineComments(normalizedCode);
            normalizedCode = removeWhitespace(normalizedCode);

            // 替换变量名和函数名
            normalizedCode = replaceIdentifiers(normalizedCode);

            // 如果代码行不是空的，计算它的哈希值
            if (!normalizedCode.isEmpty()) {
                String hash = computeHash(normalizedCode);
                hashes.add(hash); // 将哈希值存入集合
            }
        }

        return hashes;
    }

    public static void main(String[] args) {
        // 示例代码
        String code = "int main() {\n    int x = 10;\n    // Variable assignment\n    x = x + 2;\n    return x;\n}";

        // 处理代码并获取哈希值
        Set<String> hashes = processCode(code);

        // 输出哈希值
        System.out.println("代码片段的哈希值:");
        for (String hash : hashes) {
            System.out.println(hash);
        }
    }
}
```



只记录一个用户第一次ac的代码





```c++
#include<iostream>
#include<string>
using namespace std;
int main(){
    string str;
    cin>>str;
    cout<<"test2";
    return 0;
}
```
