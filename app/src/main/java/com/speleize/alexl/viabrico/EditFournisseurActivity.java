package com.speleize.alexl.viabrico;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class EditFournisseurActivity extends AppCompatActivity {

    private EditText editname;
    private EditText editaddress;
    private EditText editphone;
    private EditText editdescription;
    private EditText editmail;
    private Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fournisseur);

        editname = findViewById(R.id.editname);
        editaddress = findViewById(R.id.editaddress);
        editphone = findViewById(R.id.editphone);
        editdescription = findViewById(R.id.editdescription);
        editmail = findViewById(R.id.editmail);
        updateButton = findViewById(R.id.submit);


        // Récupération du token :

        SharedPreferences getToken = getSharedPreferences("prefToken", Context.MODE_PRIVATE);
        String token = getToken.getString("token", "");


        // Récupération de l'ID :

        Intent getIntent = getIntent();
        Integer id = getIntent.getIntExtra("idFournisseur", 0);


        Request.get("https://viabrico.herokuapp.com/suppliers/" + id, token, null, new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.d("response : ", responseString);
                Gson gson = new Gson();
                Fournisseur json = gson.fromJson(responseString, Fournisseur.class);

                // Données par défaut :
                editname.setText(json.getName());
                editdescription.setText(json.getDescription());
                editaddress.setText(json.getAddress());
                editmail.setText(json.getMail());
                editphone.setText(json.getPhone().toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.d("error :", responseString);

            }

        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Values to string :
                String strName = editname.getText().toString();
                String strAddress = editaddress.getText().toString();
                String strPhone = editphone.getText().toString();
                String strDescription = editdescription.getText().toString();
                String strMail = editmail.getText().toString();

                // On vérifie que les champs ne soient pas vide :
                if(     !isEmpty(strName) &&
                        !isEmpty(strAddress) &&
                        !isEmpty(strPhone) &&
                        !isEmpty(strDescription) &&
                        !isEmpty(strMail)) {
                    // On vérifie que strMail est bien un mail :
                    if (isEmailValid(strMail)) {
                        editmail.setError(null);


                        // On vérifie que strPhone est bien un numéro :
                        if (isNumeric(strPhone)) {
                            editphone.setError(null);

                            RequestParams params = new RequestParams();
                            params.put("name", strName);
                            params.put("address", strAddress);
                            params.put("phone", strPhone);
                            params.put("description", strDescription);
                            params.put("email", strMail);

                            Request.put("https://viabrico.herokuapp.com/suppliers/" + id, token, params, new TextHttpResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, Header[] headers, String responseBody) {

                                    // called when response HTTP status is "200 OK"
                                    Log.d("res", "code: " + statusCode);
                                    Log.d("res", "headers: " + headers.toString());
                                    Log.d("res", "res:" + responseBody);

                                    Intent intent = new Intent(getApplicationContext(), FournisseursActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                                    Log.d("res", "res: " + error);

                                    Log.d("status code :", "status code :" + statusCode);
                                }
                            });
                        } else {
                            editphone.setError("Numéro non valide !");
                        }
                    } else {
                        editmail.setError("Email non valide !");
                    }
                } else {
                    Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs." , Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    public boolean isEmpty(String value){
        if(value.isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
}
