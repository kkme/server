package com.hifun.soul.payserver;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import biz.source_code.base64Coder.Base64Coder;

import com.qq.open.OpensnsException;
import com.qq.open.SnsSigCheck;

public class SigUtil {
	private final static String secret = "c4c38ddf497bbfda016af3d87fc63eba&";
	// 编码方式
	private static final String CONTENT_CHARSET = "UTF-8";

	// HMAC算法
	private static final String HMAC_ALGORITHM = "HmacSHA1";

	public static String encodeToASCIIString(String src) {
		// 除了 0~9 a~z A~Z !*() 之外其他字符按其ASCII码的十六进制加%进行表示，例如“-”编码为“%2D”。
		// 需对value先按照如下编码规则进行编码（注意这里不是urlencode）
		// payitem中，单价如果有小数点“.”，请编码为“%2E”。
		StringBuilder result = new StringBuilder();
		for (char each : src.toCharArray()) {
			if (!needEncode(each)) {
				result.append(each);
				continue;
			}
			if (each == '.') {
				result.append("%2E");
				continue;
			}
			int tempInt = each;
			String tempStr = Integer.toHexString(tempInt);
			result.append("%").append(tempStr);
		}
		return result.toString();
	}

	private static boolean needEncode(char each) {
		return !Pattern.matches("\\w", String.valueOf(each));
	}

	public static String urlEncode(String src)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(src, "UTF-8").replace("+", "%20")
				.replace("*", "%2A");
	}

	public static boolean checkSig(String method, String uri, String sig,
			Map<String, String> params) throws UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeyException {

		for (String each : params.keySet()) {
			params.put(each, SigUtil.encodeToASCIIString(params.get(each)));
		}
		// 排序
		String[] names = params.keySet().toArray(new String[0]);
		Arrays.sort(names);
		System.out.println(Arrays.toString(names));
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (String each : names) {
			sb.append(each).append("=").append(params.get(each));
			if (index < names.length - 1) {
				sb.append("&");
			}
			index++;
		}
		// 进行URL编码
		String paramsString = SigUtil.urlEncode(sb.toString());
		System.out.println("paramString: " + paramsString);

		StringBuilder srcBuilder = new StringBuilder().append(method)
				.append("&").append(SigUtil.urlEncode(uri));
		srcBuilder.append(paramsString);
		String srcString = srcBuilder.toString();
		System.out.println("befor key: " + srcString);
		// 生成签名值
		Mac mac = Mac.getInstance(HMAC_ALGORITHM);
		SecretKeySpec secretKey = new SecretKeySpec(
				secret.getBytes(CONTENT_CHARSET), mac.getAlgorithm());

		mac.init(secretKey);
		byte[] hash = mac.doFinal(srcString.getBytes(CONTENT_CHARSET));
		// base64
		String mySig = new String(Base64Coder.encode(hash));
		System.out.println("mySig: " + mySig);
		System.out.println("sig: " + sig);
		return sig.equals(mySig);
	}

	public static void main(String[] args) throws UnsupportedEncodingException,
			NoSuchAlgorithmException, InvalidKeyException, OpensnsException {
		HashMap<String, String> params = new HashMap<String, String>();
		HashMap<String, String> params2 = new HashMap<String, String>();
		params.put("amt", "80");
		params.put("appid", "33758");
		params.put("billno", "-APPDJT18700-20120210-1428215572");
		params.put("openid", "test001");
		params.put("payamt_coins", "20");
		params.put("payitem", "1*8*1");
		params.put("ts", "1328855301");
		params.put("providetype", "0");
		params.put("pubacct_payamt_coins", "10");
		params.put("token", "53227955F80B805B50FFB511E5AD51E025360");
		params.put("version", "v3");
		params.put("zoneid", "1");

		params2.put("amt", "80");
		params2.put("appid", "33758");
		params2.put("billno", "-APPDJT18700-20120210-1428215572");
		params2.put("openid", "test001");
		params2.put("payamt_coins", "20");
		params2.put("payitem", "1*8*1");
		params2.put("ts", "1328855301");
		params2.put("providetype", "0");
		params2.put("pubacct_payamt_coins", "10");
		params2.put("token", "53227955F80B805B50FFB511E5AD51E025360");
		params2.put("version", "v3");
		params2.put("zoneid", "1");

		// System.out.println(checkSig("GET", "/cgi-bin/temp.py",
		// "VvKwcaMqUNpKhx0XfCvOqPRiAnU=", params));
		// 先对参数编码
		SnsSigCheck.codePayValue(params);
		System.out.println(SnsSigCheck.makeSig("GET", "/pay_server/pay.do",
				params, secret));
		System.out.println(SnsSigCheck.verifySig("GET", "/pay_server/pay.do",
				params2, secret, "XqZ72Q3s9Rk+y/2uCvQBpSXYagU="));
		String sig2 = "XqZ72Q3s9Rk+y/2uCvQBpSXYagU=";
		System.out.println(SnsSigCheck.encodeUrl(sig2));
	}

}
