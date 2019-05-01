package com.speleize.alexl.viabrico;

import android.util.Log;

import com.loopj.android.http.*;


public class Request {
    //public static final String BASE_URL = "https://viabrico.herokuapp.com/";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        getToken(token);

        client.get(url, params, responseHandler);
    }

    public static void post(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        if (token != null) {
            getToken(token);
        }
        client.post(url, params, responseHandler);
    }

    public static void put(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        getToken(token);
        client.put(url, params, responseHandler);
    }

    public static void delete(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        getToken(token);
        client.delete(url, params, responseHandler);
    }


    public static void setTimeout() {
        client.setConnectTimeout(60000);
        client.setResponseTimeout(60000);
    }

    public static void getToken(String token) {

        client.addHeader("Authorization", "Bearer " + token);
    }
}