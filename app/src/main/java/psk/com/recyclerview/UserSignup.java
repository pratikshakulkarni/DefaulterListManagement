package psk.com.recyclerview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSignup extends AppCompatActivity {
    public EditText n1, e1, p1, cp1;
    public Button b1;
    AlertDialog.Builder builder;
    public static final String db_url = "your url";
    String name, email, pass, cpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);

        n1 = (EditText) findViewById(R.id.editText3);
        e1 = (EditText) findViewById(R.id.editText4);
        p1 = (EditText) findViewById(R.id.editText5);
        cp1 = (EditText) findViewById(R.id.editText6);
        b1 = (Button) findViewById(R.id.button2);
        builder = new AlertDialog.Builder(UserSignup.this);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = n1.getText().toString();
                email = e1.getText().toString();
                pass = p1.getText().toString();
                cpass = cp1.getText().toString();

                if (name.equals("") || email.equals("") || pass.equals("") || cp1.equals("")) {
                    builder.setTitle("Something Went Wrong!");
                    builder.setMessage("Please fill all the fields");
                    display_alert("Input_Error");
                } else if (!validateemail(email)) {
                    e1.setError("Invalid Email");
                    e1.requestFocus();
                } else if (pass.length() < 8) {
                    builder.setTitle("Length");
                    builder.setMessage("Password length should be greater the 8");
                    display_alert("Pass_length");
                } else {
                    if (!pass.equals(cpass)) {
                        builder.setTitle("Something Went Wrong!");
                        builder.setMessage("Password Dont Match");
                        display_alert("Input_Error");
                    } else {

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, db_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(UserSignup.this, db_url, Toast.LENGTH_SHORT).show();
                                        try {
                                            JSONArray jsonArray = new JSONArray(response);
                                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                                            String code = jsonObject.getString("code");
                                            String message = jsonObject.getString("message");
                                            builder.setTitle("Server Response");
                                            builder.setMessage(message);
                                            display_alert(code);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Toast.makeText(user_signup.this, error.toString(), Toast.LENGTH_SHORT).show();
                                // Log.e("error",error.toString());
                                if (error instanceof NetworkError) {
                                    Toast.makeText(getApplicationContext(), "Cannot connect to Internet...Please check your connection!", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ServerError) {
                                    Toast.makeText(getApplicationContext(), "The server could not be found. Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                } else if (error instanceof AuthFailureError) {
                                    Toast.makeText(getApplicationContext(), "AuthFailureError", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof ParseError) {
                                    Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                                } else if (error instanceof NoConnectionError) {
                                    Toast.makeText(getApplicationContext(), "NoConnectionError", Toast.LENGTH_SHORT).show();
                                } else if (error instanceof TimeoutError) {
                                    Toast.makeText(getApplicationContext(), "Connection TimeOut! Please check your internet connection.", Toast.LENGTH_SHORT).show();

                                }
                            }
                        })
                        {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("Name", name);
                                params.put("Email_id", email);
                                params.put("Password", pass);
                                return params;
                            }

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                // do not add anything here
                                return headers;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded";
                            }
                        };

                        MySingleton.getInstance(UserSignup.this).addToRequestQueu(stringRequest);

                    }
                }
            }

            private boolean validateemail(String email) {
                String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                Pattern pattern = Pattern.compile(emailPattern);
                Matcher matcher = pattern.matcher(email);

                return matcher.matches();

            }
        });
    }

    public void display_alert(final String code) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (code.equals("Input_Error")) {
                    p1.setText("");
                    cp1.setText("");
                } else if (code.equals("reg_success")) {
                    finish();
                } else if (code.equals("reg_failed")) {
                    n1.setText("");
                    e1.setText("");
                    p1.setText("");
                    cp1.setText("");
                } else if (code.equals("Pass_length")) {
                    p1.setText("");
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
