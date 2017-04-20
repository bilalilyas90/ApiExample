package com.project.coresol.apiexample.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    EditText editText_email,editText_password;
    Button btn_login;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editText_email = (EditText)findViewById(R.id.editText_email);
        editText_password = (EditText)findViewById(R.id.editText_password);
        btn_login = (Button)findViewById(R.id.btn_login);

        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Please Wait!!");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = editText_email.getText().toString();
                final String password = editText_password.getText().toString();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(),"fill fields",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    StringRequest strReq = new StringRequest(Request.Method.POST,
                            AppConfig.URL_MAIN, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            //Log.d(TAG, "Login Response: " + response.toString());
                            if (pDialog.isShowing())
                                pDialog.hide();

                            Log.d("Login response",response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                boolean checkLogin = jsonObject.getBoolean("error");
                                if(!checkLogin){
                                    JSONObject studntObject = jsonObject.getJSONObject("student_rec");
                                    String name = studntObject.getString("name");
                                    Toast.makeText(getApplicationContext(),"This user ("+name+") has been logged in successfully",Toast.LENGTH_SHORT).show();
                                }else{
                                    String errorMsg = jsonObject.getString("error_msg");
                                    Toast.makeText(getApplicationContext(),errorMsg,Toast.LENGTH_SHORT).show();

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
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            /*http://localhost/android/mysql_api.php?
                            action=login&email=abcd@email.com&password=123456*/
                            HashMap<String, String> map = new HashMap<String, String>();
                            map.put("action","login");
                            map.put("email",email);
                            map.put("password",password);
                            return map;
                        }
                    };
                    AppController.getInstance().addToRequestQueue(strReq, "loginReq");
                }
            }
        });

    }
}
