package com.project.coresol.apiexample.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.coresol.apiexample.Models.YoutubeModel;
import com.project.coresol.apiexample.R;
import com.project.coresol.apiexample.adapters.CustomBaseAdapter;
import com.project.coresol.apiexample.app.AppConfig;
import com.project.coresol.apiexample.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class YoutubeActivity extends AppCompatActivity {
    EditText editText;
    ListView listView;
    Button btn_search;
    ProgressDialog pDialog;
    ArrayList<YoutubeModel> allRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        editText = (EditText)findViewById(R.id.editText);
        listView = (ListView)findViewById(R.id.listview);
        btn_search = (Button)findViewById(R.id.btn_search);

        pDialog = new ProgressDialog(YoutubeActivity.this);
        pDialog.setMessage("please wait!!");
        /*ArrayList<YoutubeModel> allRecord = new ArrayList<>();
        YoutubeModel youtubeModel = new YoutubeModel("Crikett Title","this is desc","https://i.ytimg.com/vi/r7cISkbCeBA/default.jpg");
        allRecord.add(youtubeModel);

        youtubeModel = new YoutubeModel("Crikett Title","this is desc","https://i.ytimg.com/vi/r7cISkbCeBA/default.jpg");
        allRecord.add(youtubeModel);

        youtubeModel = new YoutubeModel("Crikett Title","this is desc","https://i.ytimg.com/vi/r7cISkbCeBA/default.jpg");
        allRecord.add(youtubeModel);

        CustomBaseAdapter adapter = new CustomBaseAdapter(this,allRecord);
        listView.setAdapter(adapter);*/
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pDialog.isShowing())
                    pDialog.show();
                String query = editText.getText().toString();
                allRecord = new ArrayList<>();
                StringRequest strReq = null;
                try {
                    strReq = new StringRequest(Request.Method.GET,
                            AppConfig.URL_YOUTUBE+"&q="+ URLEncoder.encode(query, "UTF-8"), new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //Log.d(TAG, "Login Response: " + response.toString());
                            if (pDialog.isShowing())
                                pDialog.hide();

                            Log.d("MainActivity response",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray itemsJsonArray = jsonObject.getJSONArray("items");
                                for (int i =0;i<itemsJsonArray.length();i++){
                                    JSONObject singleVideoJsonOnject = itemsJsonArray.getJSONObject(i);
                                    JSONObject snippetObject = singleVideoJsonOnject.getJSONObject("snippet");
                                    String title = snippetObject.getString("title");
                                    String desc = snippetObject.getString("description");
                                    JSONObject thumbnailObject = snippetObject.getJSONObject("thumbnails");

                                    JSONObject defaultObject = thumbnailObject.getJSONObject("default");
                                    String urll= defaultObject.getString("url");


                                    YoutubeModel youtubeModel = new YoutubeModel(title,desc,urll);
                                    allRecord.add(youtubeModel);
                                }
                                CustomBaseAdapter adapter = new CustomBaseAdapter(YoutubeActivity.this,allRecord);
                                listView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
    //                Log.e(TAG, "Login Error: " + error.getMessage());
                            Toast.makeText(getApplicationContext(),
                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            if (pDialog.isShowing())
                                pDialog.hide();
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, "youtube");
            }
        });

    }
}
