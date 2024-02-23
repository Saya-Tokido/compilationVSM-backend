package com.ljz.compilationVSM;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@SpringBootTest
class CompilationVSMApplicationTests {

	@Test
	void test1() {
		String url = "https://www.runoob.com/try/compile2.php";
		RestTemplate client = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
//  请勿轻易改变此提交方式，大部分的情况下，提交方式都是表单提交
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
		MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
//  也支持中文
		params.add("code", "#include <stdio.h>\n" +
				"int main()\n" +
				"{int i=99;\n" +
				"printf(\"%d\",i);\n" +
				"   return 0;\n" +
				"}");
		params.add("token", "066417defb80d038228de76ec581a50a");
		params.add("stdin", "");
		params.add("language", "7");
		params.add("fileext", "c");
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//  执行HTTP请求
		ResponseEntity<HashMap> response = client.exchange(url, HttpMethod.POST, requestEntity, HashMap.class);
//  输出结果
		System.out.println(response.getBody());
	}

	void test2(){
		String test="//"
	}
}
