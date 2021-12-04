package com.badge.share;


import com.badge.share.PostRequest.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    private final String clientId = "78slbnekpqfyos";
    private final String clientSecret = "KoXu9yJTt4QI0vfF";
    private final String redirectURI = "https://154.53.33.183:8443/share/linkedin";
    private final String apiBaseURL = "https://api.linkedin.com/v2/";


    private final String clientIdFB = "616071912907239";
    private final String clientSecretFB = "d5ae1ccfe1c3e044b588964a94618044";
    private final String redirectURIFB = "https://localhost:8443/share/facebook";
    private final String apiBaseURLFB = "https://api.linkedin.com/v2/";


    @RequestMapping(value = "/share/linkedin", method = RequestMethod.GET)
    public ModelAndView callback(@RequestParam("state") String state, @RequestParam("code") String code) throws Exception {
        ConnectionUtils connectionUtils = new ConnectionUtils();
        ObjectMapper ob = new ObjectMapper();

        URIBuilder builder = new URIBuilder("https://www.linkedin.com/oauth/v2/accessToken");
        builder.setParameter("grant_type", "authorization_code")
                .setParameter("client_id", clientId)
                .setParameter("client_secret", clientSecret)
                .setParameter("code", code)
                .setParameter("redirect_uri", redirectURI);
        String getAccessToken = connectionUtils.sendPost(builder);
        AccessTokenResponse accessTokenResponse = ob.readValue(getAccessToken, AccessTokenResponse.class);


        HttpGet request = new HttpGet(apiBaseURL + "me");
        request.addHeader("Authorization", "Bearer " + accessTokenResponse.getAccess_token());
        String getUserURN = connectionUtils.sendGet(request);
        UserDetailsReponse userDetailsReponse = ob.readValue(getUserURN, UserDetailsReponse.class);

        LinkedinStateData linkedinStateData = ob.readValue(state,LinkedinStateData.class);

        PostRequestBody postRequestBody = new PostRequestBody();
        Content content = new Content();
        content.setTitle(linkedinStateData.getSubtitle());
        List<ContentEntities> contentEntitieslist = content.getContentEntities();
        ContentEntities contentEntities = new ContentEntities();
        List<Thumbnails> thumbnails = new ArrayList<>();
        if(linkedinStateData.getImageUrl().size()>0) {
            for (int i = 0; i < linkedinStateData.getImageUrl().size(); i++) {
                Thumbnails thumbnail = new Thumbnails();
                thumbnail.setResolvedUrl(linkedinStateData.getImageUrl().get(i).getImage());
                thumbnails.add(thumbnail);

            }
        }
        contentEntities.setThumbnails(thumbnails);
        contentEntities.setEntityLocation(linkedinStateData.getPostClickUrl());
        contentEntitieslist.add(contentEntities);
        content.setContentEntities(contentEntitieslist);
        postRequestBody.setContent(content);
        postRequestBody.setOwner("urn:li:person:"+userDetailsReponse.getId());
        postRequestBody.setSubject(linkedinStateData.getSubtitle());
        Text text = new Text();
        text.setText(linkedinStateData.getText());
        postRequestBody.setText(text);


        String requestBody = ob.writeValueAsString(postRequestBody);


        int resultPost = connectionUtils.postTheData(apiBaseURL + "shares", requestBody, accessTokenResponse.getAccess_token());
        Result result1 = new Result();

        if (resultPost == 201) {
            result1.setResult("DONE");

        } else {
            result1.setResult("FAILED");

        }


        return new ModelAndView("redirect:" + "https://google.com");


    }


    @RequestMapping(value = "/share/facebook", method = RequestMethod.GET)
    public ModelAndView callbackFacebook(@RequestParam("state") String state, @RequestParam("code") String code) throws Exception {
        ConnectionUtils connectionUtils = new ConnectionUtils();
        ObjectMapper ob = new ObjectMapper();

        URIBuilder builder = new URIBuilder("https://graph.facebook.com/v12.0/oauth/access_token");
        builder.setParameter("client_id", clientIdFB)
                .setParameter("client_secret", clientSecretFB)
                .setParameter("code", code)
                .setParameter("redirect_uri", redirectURIFB);
        String getAccessToken = connectionUtils.sendPost(builder);
        System.out.println(getAccessToken);
        AccessTokenResponse accessTokenResponse = ob.readValue(getAccessToken, AccessTokenResponse.class);

        System.out.println(accessTokenResponse.toString());
        String access = accessTokenResponse.getAccess_token();

        FacebookClient facebookClient = new DefaultFacebookClient(access,null);
        FacebookType response = facebookClient.publish("me/feed",FacebookType.class, Parameter.with("message","TEST API"));

        return new ModelAndView("redirect:" + "https://google.com");


    }


}
