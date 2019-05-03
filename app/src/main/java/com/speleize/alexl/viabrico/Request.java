package com.speleize.alexl.viabrico;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class Request {

    // Utilisation de la librairie AsyncHttpClient pour faire des requêtes :
    public static AsyncHttpClient client = new AsyncHttpClient();

    // Méthode get pour retourner le contenu des données par exemple :
    public static void get(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        // On met une fonction Timeout pour ne pas entrer dans un TimeoutException :
        setTimeout();

        // On récupère le token pour pouvoir avoir les droits (celui-ci est stocké dans un sharedpreferences au moment de la connexion :
        getToken(token);

        // On utilise la méthode get d'AsyncHttpClient :
        client.get(url, params, responseHandler);
    }


    // Méthode post : (envoyer des données)
    public static void post(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        if (token != null) {
            getToken(token);
        }
        client.post(url, params, responseHandler);
    }

    // Méthode put : (mettre à jour des données)
    public static void put(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        getToken(token);
        client.put(url, params, responseHandler);
    }

    // Méthode delete : (supprimer des données)
    public static void delete(String url, String token, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        setTimeout();
        getToken(token);
        client.delete(url, params, responseHandler);
    }


    // TimeOutException ne se lance pas avant 60 secondes, cela permet d'avoir le temps de requêter l'API si jamais elle est en sleep :
    public static void setTimeout() {
        client.setConnectTimeout(60000);
        client.setResponseTimeout(60000);
    }

    // On récupère le token :
    public static void getToken(String token) {

        client.addHeader("Authorization", "Bearer " + token);
    }
}