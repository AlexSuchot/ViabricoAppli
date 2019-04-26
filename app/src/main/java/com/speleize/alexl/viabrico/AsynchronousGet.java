package com.speleize.alexl.viabrico;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class AsynchronousGet {

    public String url;
    public RequestBody body;
    public final OkHttpClient client;

    public AsynchronousGet(String url, String body) {
        this.url = url;
        this.body = RequestBody.create(JSON, body);
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }



    public MediaType JSON = MediaType.get("application/json; charset=utf-8");


    public void run() throws Exception {
        Request request = new Request.Builder()
                .url(this.url)
                .post(this.body)
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
