package com.ljz.compilationVSM;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CommonIfaceTest {

    @Test
    public void test1(){
        String test = """
                #include<iostream>
                #include<string>
                using namespace std;
                int main(){
                    string str;
                    cin>>str;
                    cout<<"test2";
                    return 0;
                }
                """;
        System.out.println(test.length());
    }
}
