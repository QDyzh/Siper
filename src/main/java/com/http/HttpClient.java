package com.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.DeflateDecompressingEntity;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HttpClient {
    public static final int METHOD_TYPE_POST = 1;
    public static final int METHOD_TYPE_GET = 0;
    private CloseableHttpClient httpClient = null;
    private HttpPost httpPost;
    private HttpGet httpGet;
    private String charset;


    public HttpClient( String url, int method, String charset, Map<String, Object> headMap) {
        this.httpClient = HttpClients.createDefault();
        this.charset = charset;
        if(method == METHOD_TYPE_POST) {
            this.httpPost = new HttpPost(url);
            // 设置header
            Iterator headIterator = headMap.entrySet().iterator();
            while(headIterator.hasNext()){
                Map.Entry<String,Object> elem = (Map.Entry<String, Object>) headIterator.next();
                this.httpPost.setHeader(elem.getKey(), elem.getValue().toString());
            }
        } else if(method == METHOD_TYPE_GET) {
            this.httpGet = new HttpGet(url);
            // 设置header
            Iterator headIterator = headMap.entrySet().iterator();
            while(headIterator.hasNext()){
                Map.Entry<String,Object> elem = (Map.Entry<String, Object>) headIterator.next();
                this.httpGet.setHeader(elem.getKey(), elem.getValue().toString());
            }
        }
    }

    private String sendMsg(HttpResponse response) throws Exception {
        String result = null;
        if(response != null){
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                // 因服务器用的gzip编码传输。
                // 突然resEntity.getContentEncoding() 空指针。。。。不清楚为什么
                if (resEntity.getContentEncoding() != null) {
                    if("gzip".equalsIgnoreCase(resEntity.getContentEncoding().getValue())){
                        resEntity = new GzipDecompressingEntity(resEntity);
                    } else if("deflate".equalsIgnoreCase(resEntity.getContentEncoding().getValue())){
                        resEntity = new DeflateDecompressingEntity(resEntity);
                    }
                }
                result = EntityUtils.toString(resEntity, this.charset);
            }
        }
        return result;
    }

    public String doPost(Map<String,Object> map, String charset) throws Exception {
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,Object> elem = (Map.Entry<String, Object>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(),elem.getValue().toString()));
        }
        if(list.size() > 0){
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
            this.httpPost.setEntity(entity);
        }
        return sendMsg(this.httpClient.execute(this.httpPost));
    }

    public String doGet() throws Exception {
        return sendMsg(this.httpClient.execute(this.httpGet));
    }
}
