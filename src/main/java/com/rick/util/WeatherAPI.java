package com.rick.util;

import com.aliyun.oss.common.utils.IOUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rick
 * @createdAt 2023-02-22 13:11:00
 */
@Service
public class WeatherAPI {

    private static final Pattern PATTERN = Pattern.compile("\\w{40}");

    public String byCity(String cityName) {
        String[] cookieAndToken;
        try {
            cookieAndToken = getCookieAndToken();
        } catch (IOException e) {
            return null;
        }

        String url = "http://www.free-api.com/urltask";

        final List<NameValuePair> formData = new ArrayList<>();
        formData.add(new BasicNameValuePair("fzsid", "69"));
        formData.add(new BasicNameValuePair("city", cityName));

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader("accept", "application/json, text/javascript, */*; q=0.01");
        httpPost.addHeader("cookie", cookieAndToken[0]);
        httpPost.addHeader("origin", "https://www.free-api.com");
        httpPost.addHeader("referer", "https://www.free-api.com/use/69");
        httpPost.addHeader("x-csrf-token", cookieAndToken[1]);
        httpPost.addHeader("cache-control", "no-cache");
        httpPost.setEntity(new UrlEncodedFormEntity(formData, Consts.UTF_8));

        HttpResponse httpresponse;
        try {
            httpresponse = httpclient.execute(httpPost);
            return IOUtils.readStreamAsString(httpresponse.getEntity().getContent(), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String[] getCookieAndToken() throws IOException {
        String url = "http://www.free-api.com/use/69";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpresponse = null;
        try {
            httpresponse = httpclient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder cookiesBuilder = new StringBuilder();
        for (Header header : httpresponse.getHeaders("set-cookie")) {
            cookiesBuilder.append(header.getValue()).append(";");
        }

        String html = IOUtils.readStreamAsString(httpresponse.getEntity().getContent(), "utf-8");


        Matcher matcher = PATTERN.matcher(html.substring(0, 500));
        matcher.find();
        String token = matcher.group(0);

        return new String[] { cookiesBuilder.toString(), token };
    }

}
