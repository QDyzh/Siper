import com.file.ExcelFile;
import com.http.HttpClient;
import com.util.StringUtil;
import com.vo.ShopVo;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeiTuan {
    public static void main(String[] args) throws Exception {
        String urlpath = "http://i.meituan.com/s/a?cid=-1&bid=-1&sid=rating&p=#{pageNo}&ciid=45&bizType=area&csp=&nocount=true&stid_b=_b2&w=%E8%87%AA%E4%B9%A0%E5%AE%A4";
        Map<String, Object> headMap = new HashMap<>();
        headMap.put("Date", new Date());
        headMap.put("Content-Type", "text/html; charset=UTF-8");
        headMap.put("Host", "i.meituan.com");
        headMap.put("Connection", "keep-alive");
        headMap.put("Content-Security-Policy", "default-src 'self' js://* *.meituan.com *.sankuai.com https://*.sankuai.com https://*.meituan.com *.meituan.net https://*.meituan.net *.maoyan.com https://*.maoyan.com wvjbscheme://* imeituan://* *.dianping.com https://*.dianping.com *.dpfile.com https://*.dpfile.com *.51ping.com https://*.51ping.com http://api.mobile.meituan.com http://sentry7.sankuai.com https://*.qq.com https://sentry7.sankuai.com *.amap.com ws://* https://fecsp.sankuai.com *.google-analytics.com 'unsafe-inline' 'unsafe-eval' blob: data:; report-uri https://fecsp.sankuai.com/report/touch");
        headMap.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16A366 MicroMessenger/6.7.3(0x16070321) NetType/WIFI Language/zh_CN");
        HttpClient client = null;
        String url = "";
        List<ShopVo> shops = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            url = urlpath.replace("#{pageNo}", (i + 1) + "");
            // 请求数据
            client = new HttpClient(url, HttpClient.METHOD_TYPE_GET, "UTF-8", headMap);
            String result = client.doGet();
            // 解析数据
            analysisResponse(result.replaceAll("\n", ""), shops);
        }
        System.out.println(shops.size());
        // 数据写入文件Excel
        new ExcelFile<>(shops).importData("D:\\迅雷下载\\text1.xlsx");
    }

    private static void analysisResponse(String responseStr, List<ShopVo> shops) {
        String regExItem = "<dd class=\"poi-list-item\">.*?</dd>";
        String regExName = "<span class=\"poiname\">.*?</span>";
        String regExStar = "<em class=\"star-text\">.*?</em>";
        String regExAddr = "<a class=\"\" onclick=\"return false;\" href=\"//i.meituan.com/chongqing(.*?)\">.*?</a>";
        Pattern pattern = Pattern.compile(regExItem);
        Matcher matcher = pattern.matcher(responseStr);
        ShopVo shop;
        while(matcher.find()) {
            String tempItem = matcher.group(0);
            shop = new ShopVo();
            String name = getValue(regExName, tempItem);
            if(StringUtil.isEmpty(name)) {
                continue;
            }
            shop.setName(name.substring(name.indexOf("poiname\">") + 9, name.lastIndexOf("</span>")));
            String star = getValue(regExStar, tempItem);
            if(!StringUtil.isEmpty(star)) {
                shop.setStar(star.substring(star.indexOf("star-text\">") + 11, star.lastIndexOf("</em>")));
            }
            String address= getValue(regExAddr, tempItem);
            if(!StringUtil.isEmpty(address)) {
                String[] addressArray = address.substring(address.indexOf("href=\"//i.meituan.com/chongqing"),
                        address.lastIndexOf("</a>")).split(">");
                if(addressArray.length != 2) {
                    shop.setAddress("无地址信息");
                } else {
                    shop.setAddress(addressArray[1]);
                }
            }
            if(!shops.contains(shop)) {
                shops.add(shop);
            }
        }
    }

    private static String getValue(String regEx, String str) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(0);
        } else {
            return null;
        }
    }
}
