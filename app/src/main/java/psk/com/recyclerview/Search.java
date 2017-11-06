package psk.com.recyclerview;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {
    AlertDialog.Builder builder;
    EditText e1;
    TextView nm,s1,s2,s3,s4,s5;
    Button btn;
    public static final String search_url = "your url";
    private String editrno,d,s,i,n,r,c,t;


    public Search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_search, container, false);

        e1 = (EditText)v.findViewById(R.id.Search_id);
        nm = (TextView) v.findViewById(R.id.come_name);
        s1 = (TextView)v.findViewById(R.id.sub1);
        s2 = (TextView)v.findViewById(R.id.sub2);
        s3 = (TextView)v.findViewById(R.id.sub3);
        s4 = (TextView)v.findViewById(R.id.sub4);
        s5 = (TextView)v.findViewById(R.id.sub5);
        btn = (Button) v.findViewById(R.id.search_bth);
        builder = new AlertDialog.Builder(getContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editrno = e1.getText().toString();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, search_url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    if(code.equals("Error"))
                                    {
                                        builder.setTitle("Something went wrong!");
                                        display_alert(jsonObject.getString("message"));
                                    }
                                    else {
                                        d = jsonObject.getString("dbms");
                                        s = jsonObject.getString("sepm");
                                        i = jsonObject.getString("isee");
                                        r = jsonObject.getString("roll_no");
                                        n = jsonObject.getString("name");
                                        c = jsonObject.getString("cn");
                                        t = jsonObject.getString("toc");
                                        nm.setText(n);
                                        s1.setText(d);
                                        s2.setText(s);
                                        s3.setText(i);
                                        s4.setText(c);
                                        s5.setText(t);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> params = new HashMap<String, String>();
                        params.put("roll_no",editrno);
                        return params;
                    }
                };
                MySingleton.getInstance(getContext()).addToRequestQueu(stringRequest);
            }
        });

        ((attendance) getActivity()).hideFloatingActionButton();
        return v;
    }

    public void display_alert(final String code)
    {
        builder.setPositiveButton("OK",new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(code.equals("Input_Error"))
                {

                }

                else if(code.equals("reg_success"))
                {

                }
                else if(code.equals("reg_failed"))
                {

                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

}
