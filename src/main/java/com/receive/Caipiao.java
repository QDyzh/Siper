package com.receive;

import java.util.HashMap;
import java.util.Map;

import top.sumhzehn.http.HttpUrlHandler;

public class Caipiao {
	public static void main(String[] args) {
		String url = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice";
		Map<String, String> params = new HashMap<>();
		params.put("name", "ssq");
		params.put("issueCount", "100");
		try {
			String res = HttpUrlHandler.sendGet(url, "UTF-8", params);
			System.out.println(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
