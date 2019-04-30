package com.speleize.alexl.viabrico;
import com.loopj.android.http.*;


public class Request {
    public static final String BASE_URL = "https://viabrico.herokuapp.com/";

    public static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        client.get(getUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        client.post(getUrl(url), params, responseHandler);
    }

    public static void put(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        client.put(getUrl(url), params, responseHandler);
    }

    public static void delete(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        client.delete(getUrl(url), params, responseHandler);
    }

    public static String getUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

    public static void setTimeout() {
        client.setConnectTimeout(60000);
        client.setResponseTimeout(60000);
    }
}