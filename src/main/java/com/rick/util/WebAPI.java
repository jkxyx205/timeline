package com.rick.util;

import com.aliyun.oss.common.utils.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rick
 * @createdAt 2021-08-20 11:00:00
 */
public final class WebAPI {

    /**
     * 根据经纬度获取具体地址
     * @param lat
     * @param lng
     * @return
     */
    public static String reverseGeocoding(String lat, String lng) {
        if (!StringUtils.hasText(lat) || !StringUtils.hasText(lng)) {
            return null;
        }

        String url = "https://api.map.baidu.com/reverse_geocoding/v3/?ak=cctNvXcciKZDZd4MAILYvXKLG35XPo3X&output=json&coordtype=wgs84ll&location=" + lat + "," + lng;

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        try {
            HttpResponse httpresponse = httpclient.execute(httpget);
            String json = IOUtils.readStreamAsString(httpresponse.getEntity().getContent(), "utf-8");
            return resolveJsonValue(json, "formatted_address");
        } catch (IOException e) {}
        return null;
    }

    private static String resolveJsonValue(String json, String key) {
        int start = json.indexOf(key) + key.length();
        int end = json.length() - 1;
        List<Integer> indexList = new ArrayList<>();

        for (int i = start; i <= end; i++) {
            char curChar = json.charAt(i);
            if (curChar == '"') {
                indexList.add(i);
            }

            if (indexList.size() == 3) {
                break;
            }
        }

        return json.substring(indexList.get(1) + 1, indexList.get(2));
    }
}
