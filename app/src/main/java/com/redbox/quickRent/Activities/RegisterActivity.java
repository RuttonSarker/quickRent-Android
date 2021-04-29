package com.redbox.quickRent.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.redbox.quickRent.R;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private EditText nameE,emailE,mobileE,passwordE;
    private Button subimtButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameE=findViewById(R.id.name);
        emailE=findViewById(R.id.email);
        mobileE=findViewById(R.id.mobile);
        passwordE=findViewById(R.id.password);
        subimtButton=findViewById(R.id.submit_button);
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");

        subimtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameE.setError(null);
                emailE.setError(null);
                mobileE.setError(null);
                passwordE.setError(null);
                String name,email,mobile,password;
                name= nameE.getText().toString();
                email=emailE.getText().toString();
                mobile=mobileE.getText().toString();
                password=passwordE.getText().toString();

                if (isValid(name,email,mobile,password)){
                    register(name,email,mobile,password);
                }
            }
        });
    }

    private void register(final String name, final String email, final String mobile, final String password){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, Url.register_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Success")){
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    progressDialog.dismiss();
                    RegisterActivity.this.finish();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                Log.d("Volley",error.getMessage());

            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params= new HashMap<>();
                params.put("name",name);
                params.put("email",email);
                params.put("mobile",mobile);
                params.put("password",password);
                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private boolean isValid(String name,String email,String mobile,String password) {
        if (name.isEmpty()) {
            showMessage("Name is empty");
            nameE.setError("Mobile Number is Empty");
            return false;
        } else if (email.isEmpty()) {
            showMessage("E-mail is empty");
            emailE.setError("Mobile Number is Empty");
            return  false;
        } else if (mobile.length() != 11) {
            showMessage("Invalid mobile number, number should be of 11 digits");
            mobileE.setError("Mobile Number is Empty");
            return false;
        }else if (password.isEmpty()){
            showMessage("Password Empty");
            passwordE.setError("Mobile Number is Empty");
            return false;
        }

        return true;
    }

    private void showMessage(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();

    }
}
