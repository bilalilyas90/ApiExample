package com.project.coresol.apiexample;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.coresol.apiexample.app.AppConfig;
import com.project.coresol.apiexample.app.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editText_num1,editText_num2;
    Button btn_add;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_num1 = (EditText)findViewById(R.id.editText_num1);
        editText_num2 = (EditText)findViewById(R.id.editText_num2);
        btn_add = (Button)findViewById(R.id.btn_add);
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait ...");
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag_string_req = "req_add";

                final String str_num1 = editText_num1.getText().toString();
                final String str_num2 = editText_num2.getText().toString();
                if (!pDialog.isShowing())
                    pDialog.show();

                StringRequest strReq = new StringRequest(Request.Method.GET,
                        AppConfig.URL_MAIN+"?action=minus&num1="+str_num1+"&num2="+str_num2, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        //Log.d(TAG, "Login Response: " + response.toString());
                        if (pDialog.isShowing())
                            pDialog.hide();

                        Log.d("MainActivity response",response);
                        try {
                            JSONObject jobj = new JSONObject(response);
                            boolean check = jobj.getBoolean("error");
                            if(!check){
                                /*JSONArray itemsArray = jobj.getJSONArray("items");
                                for (int i=0;i<itemsArray.length();i++){
                                    JSONObject jobjjj = (JSONObject) itemsArray.get(i);

                                }*/

                                String resultt = jobj.getString("result");
                                Toast.makeText(getApplicationContext(),
                                        resultt,Toast.LENGTH_SHORT).show();
                            }else{
                                String errorMsg = jobj.getString("error_msg");
                                Toast.makeText(getApplicationContext(),
                                        errorMsg, Toast.LENGTH_LONG).show();
                            }
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

                // Adding request to request queue
                AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
            }
        });
    }
}
