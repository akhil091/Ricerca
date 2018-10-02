package com.example.rohit9934.anything;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class ShowData extends AppCompatActivity {
//TextView t1;
    Toolbar tool;
    String s1;
     WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
      //  t1=(TextView)findViewById(R.id.first);
        tool= (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Recommendations");
      /*  if(!(CheckNetwork.isInternetAvailable(ShowData.this))) //returns true if internet available
        {
            Toast.makeText(ShowData.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            // finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            //do something. loadwebview.
        }*/
 /*       ImageView img= (ImageView) findViewById(R.id.img);
        String st="http://img.youtube.com/vi/n92qIk6nU84/maxresdefault.jpg";
        Picasso.with(this).load(st).into(img);*/
 //Show Recommendations;
        webview= (WebView) findViewById(R.id.webview);

        //t1.setClickable(true);
        //t1.setMovementMethod(LinkMovementMethod.getInstance());
        // t1.setMovementMethod(LinkMovementMethod.getInstance());
        s1= getIntent().getExtras().getString("username");
        /*t1.setText(s1);*/
        JSONObject json = new JSONObject();
        try {
            json.put("userhandle",s1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //String url="https://gentle-bayou-76173.herokuapp.com";
        String url = "https://ricerca.eu-gb.mybluemix.net/";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //The below 3 lines is to fetch mood type from jsonobject.
                        Iterator<String> keys= response.keys();
                        final String s= keys.next();
                        final String value = response.optString(s);
                        if(value.equals("Not valid")){
                            Toast.makeText(ShowData.this, "Please Enter valid Username", Toast.LENGTH_SHORT).show();
                           // finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        }
                        else {
                        //    Toast.makeText(ShowData.this,s, Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder= new AlertDialog.Builder(ShowData.this);
                            builder.setTitle("Successfully analyzed: "+(s.toUpperCase()));
                            builder.setMessage("would you like to have some recommendation based on the sentiment ?");
                            builder.setPositiveButton("Yes, Recommend Me", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    webview.setWebViewClient(new WebViewClient());
                                    // Toast.makeText(ShowData.this, s, Toast.LENGTH_SHORT).show();
                                    webview.loadUrl(value);
                                    WebSettings web = webview.getSettings();
                                    web.setJavaScriptEnabled(true);
                                }
                            });
                            builder.setNegativeButton("No, Go back", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(ShowData.this, MainActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                            });
                            AlertDialog alertDialog= builder.create();
                            alertDialog.show();

                            //String text = "<a href='"+value+"'> Google </a>";
                        }
                       //String text = "<a href='www.link.com'>Click here</a>";

                       // textView.setText(Html.fromHtml(text));
                        // Linkify.addLinks(t1, Linkify.WEB_URLS);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        //  jsonObjectRequest.setTag(REQ_TAG);
//        JsonObjectRequest requestQueue= new JsonObjectRequest();
//        requestQueue.add(jsonObjectRequest);
        Volley.newRequestQueue(this).add(jsonObjectRequest
        );
    }

    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }
        else {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }   }
}
