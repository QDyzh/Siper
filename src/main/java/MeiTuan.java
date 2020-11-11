import com.file.ExcelFile;
import com.http.HttpClient;
import com.util.StringUtil;
import com.vo.ShopVo;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MeiTuan {
    private static Map<String, Object> headMap = new HashMap<>();

    static {
        headMap.put("Date", new Date());
        headMap.put("Content-Type", "text/html; charset=UTF-8");
        headMap.put("Host", "i.meituan.com");
        headMap.put("Connection", "keep-alive");
        headMap.put("Content-Security-Policy", "default-src 'self' js://* *.meituan.com *.sankuai.com https://*.sankuai.com https://*.meituan.com *.meituan.net https://*.meituan.net *.maoyan.com https://*.maoyan.com wvjbscheme://* imeituan://* *.dianping.com https://*.dianping.com *.dpfile.com https://*.dpfile.com *.51ping.com https://*.51ping.com http://api.mobile.meituan.com http://sentry7.sankuai.com https://*.qq.com https://sentry7.sankuai.com *.amap.com ws://* https://fecsp.sankuai.com *.google-analytics.com 'unsafe-inline' 'unsafe-eval' blob: data:; report-uri https://fecsp.sankuai.com/report/touch");
        headMap.put("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 12_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16A366 MicroMessenger/6.7.3(0x16070321) NetType/WIFI Language/zh_CN");
    }

    public static void main(String[] args) throws Exception {
        String urlpath = "http://i.meituan.com/s/a?cid=-1&bid=-1&sid=rating&p=#{pageNo}&ciid=45&bizType=area&csp=&nocount=true&stid_b=_b2&w=%E8%87%AA%E4%B9%A0%E5%AE%A4";
        String url = "";
        List<ShopVo> shops = new ArrayList<>();
        HttpClient client = null;
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
        new ExcelFile<>(shops).importData("D:\\迅雷下载\\美团抓取数据.xlsx");
    }

    /**
     * 解析响应报文
     * @param responseStr 响应报文
     * @param shops 获取实体集合
     */
    private static void analysisResponse(String responseStr, List<ShopVo> shops) {
        String regExItem = "<dd class=\"poi-list-item\">.*?</dd>";
        String regExName = "<span class=\"poiname\">.*?</span>";
        String regExStar = "<em class=\"star-text\">.*?</em>";
        String regExAddr = "<a class=\"\" onclick=\"return false;\" href=\"//i.meituan.com/chongqing(.*?)\">.*?</a>";
        String regDetailUrl = "<p data-com=\"redirect\"  data-href=\"//i.meituan.com/poi/(.*?)\">";
        Pattern pattern = Pattern.compile(regExItem);
        Matcher matcher = pattern.matcher(responseStr);
        ShopVo shop;
        int i = 0;
        while(matcher.find()) {
            System.out.println("开始解析第" + (++i) + "数据");
            String tempItem = matcher.group(0);
            shop = new ShopVo();
            // 店铺名字
            String name = getValue(regExName, tempItem);
            if(StringUtil.isEmpty(name)) {
                continue;
            }
            shop.setName(name.substring(name.indexOf("poiname\">") + 9, name.lastIndexOf("</span>")));
            // 店铺评分
            String star = getValue(regExStar, tempItem);
            if(!StringUtil.isEmpty(star)) {
                shop.setStar(star.substring(star.indexOf("star-text\">") + 11, star.lastIndexOf("</em>")));
            }
            // 店铺所在区域
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
            // 获取明细请求地址URL
            String detailUrl = getValue(regDetailUrl, tempItem);
            if(!StringUtil.isEmpty(detailUrl)) {
                StringBuffer url = new StringBuffer("http:")
                        .append(detailUrl.substring(detailUrl.indexOf("data-href=\"") + 11, detailUrl.lastIndexOf("\">")));
                shop.setDetailUrl(url.toString());
            }
            // 请求明细地址获取数据明细
            try {
                getDetailData(shop);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(!shops.contains(shop)) {
                shops.add(shop);
            }
        }
    }

    private static void getDetailData(ShopVo shop) throws Exception {getDetailData(shop, shop.getDetailUrl());}
    /**
     * Http请求明细地址，解析明细数据
     * @param shop
     * @param url
     */
    private static void getDetailData(ShopVo shop, String url) throws Exception {
        HttpClient client = new HttpClient(url, HttpClient.METHOD_TYPE_GET,"UTF-8", headMap);
        String result = client.doGet();
        // 解析数据
        String regAddress = "desc:\"(.*)?\""; // 描述：详细地址，联系方式
        String regCommentCount = "查看全部(.*)?条评价"; // 获取总评价数
        String desc = getValue(regAddress, result);
        if(!StringUtil.isEmpty(desc)) {
            shop.setDesc(desc);
            // 根据店铺名解析
            String[] datas = desc.substring(desc.indexOf(shop.getName()) + shop.getName().length() + 1, desc.lastIndexOf("。\"")).split("，");
            if(datas.length > 1) {
                shop.setPhone(datas[datas.length-1]);
                shop.setDetailAddress(Arrays.toString(Arrays.copyOf(datas, datas.length-1)));
            } else {
                shop.setDetailAddress(datas[0]);
            }
        }
        // 总评论数
        String commentCount = getValue(regCommentCount, result);
        if(!StringUtil.isEmpty(commentCount)) {
            shop.setCommentCount(commentCount.substring(commentCount.indexOf("查看全部") + 4, commentCount.lastIndexOf("条评价")));
        }
    }

    /**
     * 获取正则表示匹配值
     * @param regEx 正则表达式
     * @param str 响应字符串
     * @return
     */
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
