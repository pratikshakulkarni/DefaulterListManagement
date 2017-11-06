package psk.com.recyclerview;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class AddStudent extends Fragment  {

    AlertDialog.Builder builder;
    public String r,n,c;
    public EditText roll1,name1,class1;
    public Button add;
    public static final String stu_url = "your url";
    public AddStudent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_student, container, false);
        // Inflate the layout for this fragment


        roll1 = (EditText)rootView.findViewById(R.id.id_roll);
        name1 = (EditText)rootView.findViewById(R.id.id_name);
        class1 = (EditText)rootView.findViewById(R.id.id_class);
        add = (Button)rootView.findViewById(R.id.addstu);
        builder = new AlertDialog.Builder(getContext());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                r= roll1.getText().toString();
                n= name1.getText().toString();
                c= class1.getText().toString();

                if(r.equals("") || n.equals("") || c.equals(""))
                {
                    builder.setTitle("Something went Wrong!");
                    builder.setMessage("Please fill all the fields");
                    display_alert("Input_Error");
                }

                else
                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, stu_url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONArray jsonArray = new JSONArray(response);
                                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                                        String code = jsonObject.getString("code");
                                        String message = jsonObject.getString("message");
                                        builder.setTitle("Server Response");
                                        builder.setMessage(message);
                                        display_alert(code);
                                    } catch (JSONException e) {
                                        Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    })
                    {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            HashMap<String,String> params = new HashMap<String, String>();
                            params.put("roll_no",r);
                            params.put("name",n);
                            params.put("class",c);
                            return params;
                        }
                    };
                    MySingleton.getInstance(getContext()).addToRequestQueu(stringRequest);
                }

            }

        });

        return rootView;
    }

    public void display_alert(final String code)
    {
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("Input_Error"))
                {
                    roll1.setText("");
                    name1.setText("");
                    class1.setText("");
                }

                else if(code.equals("reg_success"))
                {
                    roll1.setText("");
                    name1.setText("");
                    class1.setText("");
                }
                else if(code.equals("reg_failed"))
                {
                    roll1.setText("");
                    name1.setText("");
                    class1.setText("");
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
