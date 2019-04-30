package com.speleize.alexl.viabrico;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class RequestApi {

    public String url;
    public RequestBody body;
    public final OkHttpClient client;
    public MediaType JSON = MediaType.get("application/json; charset=utf-8");
    public String strEmail;
    public String strPassword;

    public RequestApi(String url, String body, String strEmail, String strPassword) {
        this.url = url;
        this.strEmail = strEmail;
        this.strPassword = strPassword;
        this.body = RequestBody.create(JSON, body);
        client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public void run() throws Exception {
        String email = strEmail;
        String password = strPassword;
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .add("password", password)
                .build();

        Request request = new Request.Builder()
                .url("https://viabrico.herokuapp.com/suppliers")
                .header("Authorization", "Bearer ")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        Log.e("Response :", responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    Log.e("Response : ", responseBody.string());
                }
            }
        });
    }
}
