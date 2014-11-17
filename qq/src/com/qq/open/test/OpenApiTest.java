package com.qq.open.test;

import java.util.HashMap;

import org.json.JSONObject;

import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;

public class OpenApiTest {

	/**
	 * @param args
	 * @throws OpensnsException 
	 */
	public static void main(String[] args) throws OpensnsException {
		OpenApiV3 sdk = new OpenApiV3("100690415", "c4c38ddf497bbfda016af3d87fc63eba");
		sdk.setServerName("openapi.tencentyun.com");
		// 指定OpenApi Cgi名字
		String scriptName = "/v3/user/get_info";

		// 指定HTTP请求协议类型
		String protocol = "http";
		String openId = "C36E2E99DC15EE6297EA42562379D59E";
		String openKey = "00A8FECC8285CEB6BDE1D01787DBAE2A";
		// 填充URL请求参数
		HashMap<String, String> qqParams = new HashMap<String, String>();
		qqParams.put("openid", openId);
		qqParams.put("openkey", openKey);
		qqParams.put("pf", "qzone");

		try {
			String resp = sdk.api(scriptName, qqParams, protocol);
			// 从返回的json窜中解析出结果码, 看看是否成功了;
			System.out.println(resp);
		} finally {
			
		}
	}

}
