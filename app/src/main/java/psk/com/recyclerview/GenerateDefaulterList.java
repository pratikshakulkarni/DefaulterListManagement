package psk.com.recyclerview;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.CheckedOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenerateDefaulterList extends Fragment {
   private RecyclerView RV;
    private RecyclerView.Adapter adapter1;
    Context c;
    private List<NewList> li;
    private static final String df_url = "http://192.168.0.102/attend/default.php";


    public GenerateDefaulterList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_generate_defaulter_list, container, false);
        RV = (RecyclerView)v.findViewById(R.id.recyclerView1);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(c));

        li = new ArrayList<>();
        loadRcData();
        return v;
    }
    private void loadRcData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, df_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Server_Response");
                            for (int i = 0 ; i< jsonArray.length();i++)
                            {
                                JSONObject obj = jsonArray.getJSONObject(i);
                                NewList nl = new NewList(
                                        obj.getString("roll_no"),
                                        obj.getString("D")
                                );
                                li.add(nl);
                            }
                            adapter1 = new MyAdapt(li,c);
                            RV.setAdapter(adapter1);
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(c, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };
        MySingleton.getInstance(c).addToRequestQueu(stringRequest);

    }






}
