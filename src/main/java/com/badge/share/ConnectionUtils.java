package com.badge.share;

import com.badge.share.PostRequest.PostRequestBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ConnectionUtils {


    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private void close() throws IOException {
        httpClient.close();
    }

    public String sendGet(HttpGet request) throws Exception {


        String result = null;

        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        try (CloseableHttpResponse response = httpClient.execute(request)) {


            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();

            if (entity != null) {
                result = EntityUtils.toString(entity);
            }

        }
        return  result;

    }

    public String sendPost(URIBuilder builder) throws Exception {

        HttpPost post = new HttpPost(builder.build());
        String result = null;


        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(post)) {

            result = (EntityUtils.toString(response.getEntity()));
        }

        return result;


    }
    public int postTheData(String urlEndPoint,String requestBody, String accessToken) throws IOException {
        URL url = new URL(urlEndPoint);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Authorization", "Bearer "+ accessToken);
        http.setRequestProperty("Content-Type", "application/json");

        System.out.println(requestBody);
        System.out.println("Bearer "+ accessToken);
        byte[] out = requestBody.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);


        int rcode = http.getResponseCode();
        http.disconnect();

        return rcode;

    }


}
