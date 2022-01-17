package com.receive;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.excepiton.CaiPiaoException;

import top.sumhzehn.http.HttpUrlHandler;
import top.sumhzehn.json.JsonToTypeReference;
import top.sumhzehn.util.StrUtil;

public class Caipiao {
	private static String SSQ_URL = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice";
	
	/**
	 *  获取双色球往期数据
	 * @param startDay yyyy-MM-dd 开始日期
	 * @param endDay yyyy-MM-dd 结束日期
	 * @param pageNo 页码起始页
	 * @param resList
	 * @return
	 * @throws CaiPiaoException
	 */
	@SuppressWarnings("unchecked")
	public void requestSsq(String startDay, String endDay, int pageNo, List<Map<String, Object>> resList) throws CaiPiaoException{
		Map<String, String> params = new HashMap<>();
		params.put("name", "ssq");
		params.put("issueCount", ""); // 查询多少期 
		params.put("issueStart", "");
		params.put("issueEnd", "");
		params.put("dayStart", startDay);
		params.put("dayEnd", endDay);
		params.put("pageNo", String.valueOf(pageNo));
		try {
			Map<String, String> headMap = new HashMap<>();
			headMap.put("content-Type", "text/json;charset=UTF-8");
			headMap.put("Connection", "keep-alive");
			headMap.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36");
			headMap.put("Cookie", "HMF_CI=dc2cbaa1890e88ce10f384af8e981737b98f8bd9cf96896cf85aa912273c431d72; 21_vq=8");
			String res = HttpUrlHandler.sendGet(SSQ_URL, "UTF-8", params, headMap);
			System.out.println("respone message is " + res);
			if (StrUtil.isNotEmpty(res)) {
				Map<String, Object> map = JsonToTypeReference.jsonToMap(res);
				if (map.get("state") != null && (int)map.get("state") == 0) {
					int pageCount = (int) map.get("pageCount");
					// 解析记录
					if (map.get("result") != null) {
						List<Map<String, Object>> tlist = (List<Map<String, Object>>) map.get("result");
//						System.out.println(map.get("pageCount") + " <======> " + map.get("countNum") + ", pageNo=" + pageNo + ", size=" + tlist.size());
						resList.addAll(tlist);
						if (pageCount == pageNo) {
							return;
						} else {
							requestSsq(startDay, endDay, ++pageNo, resList);
						}
					}
				} else {
					throw new CaiPiaoException("request [" + SSQ_URL + "] is failed, " + res);
				}
			}
		} catch (Exception e) {
			throw new CaiPiaoException("request [" + SSQ_URL + "] is failed !", e);
		}
	}
	
	/**
	 * 	本地测试
	 * @param args
	 */
	public static void main(String[] args) {
		List<Map<String, Object>> list = new ArrayList<>();
		new Caipiao().requestSsq("2021-01-01", "2022-01-31", 1, list);
		System.out.println(list.size());
//		for (Map<String, Object> m : list) {
//			System.out.println(m);
//		}
	}
}
