package psk.com.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserLogin extends AppCompatActivity {


    public static String h_name,h_email;
    public EditText nText;
    public EditText nPass;
    public Button bSubmit;
    String l_name,l_pass;
    String url = "your url";
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        builder = new AlertDialog.Builder(UserLogin.this);
        bSubmit = (Button)findViewById(R.id.button);
        nText = (EditText)findViewById(R.id.editText);
        nPass = (EditText)findViewById(R.id.editText2);


        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l_name = nText.getText().toString();
                l_pass = nPass.getText().toString();
                System.out.println("Lname :" + l_name+ "Lpass :" +l_pass);

                if(l_name.equals("") || l_pass.equals(""))
                {
                    builder.setTitle("Something went wrong!");
                    displayAlert("Enter valid username & password!");
                }

                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //Log.d(Tag,"Register Response: " + response.toString());
                                    Toast.makeText(UserLogin.this, "Success1", Toast.LENGTH_SHORT).show();
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        h_name = jsonObject.getString("name");
                                        h_email = jsonObject.getString("email");

                                        Toast.makeText(getApplicationContext(),code,Toast.LENGTH_LONG).show();

                                        if(code.equals("Login_Failed"))
                                        {
                                            Toast.makeText(UserLogin.this, "Success6", Toast.LENGTH_SHORT).show();
                                            builder.setTitle("Login Error");
                                            displayAlert(jsonObject.getString("message"));

                                        }
                                        else
                                        {
                                            Toast.makeText(UserLogin.this, "Success", Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(UserLogin.this,LoginNext.class);
                                            startActivity(i);
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(UserLogin.this, e.toString(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                        Log.e("error",e.toString());
                                        Toast.makeText(UserLogin.this,e.toString(),Toast.LENGTH_LONG);
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(UserLogin.this,error.toString(),Toast.LENGTH_LONG).show();

                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<String, String>();
                            System.out.println("email :" + l_name+ " pass :" +l_pass);
                            params.put("Email_id",l_name);
                            params.put("Password",l_pass);
                            return params;
                        }


                    };
                    MySingleton.getInstance(UserLogin.this).addToRequestQueu(stringRequest);
                }
            }
        });
    }



public void displayAlert(String message){
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){

@Override
public void onClick(DialogInterface dialogInterface, int i) {
        nText.setText("");
        nPass.setText("");
        }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        }

public void go(View view)
        {
        Intent i1 = new Intent(getApplicationContext(),UserSignup.class);
        startActivity(i1);
        }

}

