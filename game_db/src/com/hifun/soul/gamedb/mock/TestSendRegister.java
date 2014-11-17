package com.hifun.soul.gamedb.mock;

import java.io.IOException;

import com.hifun.soul.core.util.HttpUtil;

/**
 * 注册测试, 华子参考;
 * 
 * @author crazyjohn
 * 
 */
public class TestSendRegister {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String response = HttpUtil
				.getUrl("http://192.168.1.108:8080/pay_server/pay.do?method=register&openid=test001&token=53227955F80B805B50FFB511E5AD51E025360");
		System.out.println(response);
	}

}
