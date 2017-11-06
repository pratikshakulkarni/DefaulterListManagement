package psk.com.recyclerview;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class attendance extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public TextView n, e;
    private DrawerLayout mDrawer;
    private int flag;
    private String s;
    private ActionBarDrawerToggle myToggle;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<Listitem> listitems;
    private NavigationView nav;
    public FloatingActionButton fab;
    FragmentTransaction fragmentTransaction;
    private static final String url_data = "your url";
    public String p_url = "your url", no;
    String rno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attendance);

        no = LoginNext.sub;
        Toast.makeText(this, LoginNext.sub, Toast.LENGTH_LONG).show();
        fab  = (FloatingActionButton) findViewById(R.id.fab);
        flag = 0;

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listitems = new ArrayList<>();

        LoadRecyclerViewData();


        nav = (NavigationView) findViewById(R.id.mynavbar);
        nav.setNavigationItemSelectedListener(this);
        View v = nav.inflateHeaderView(R.layout.header);

        n = (TextView) v.findViewById(R.id.head_name);
        e = (TextView) v.findViewById(R.id.head_email);

        n.setText(UserLogin.h_name);
        e.setText(UserLogin.h_email);


        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        myToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
        mDrawer.addDrawerListener(myToggle);
        myToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
    public void hideFloatingActionButton() {
        fab.hide();
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_stu:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.mainContainer, new AddStudent());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Add Student");
                item.setChecked(true);
                mDrawer.closeDrawers();
                break;
            case R.id.search:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.mainContainer, new Search());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Search");
                item.setChecked(true);
                mDrawer.closeDrawers();
                break;
            case R.id.defaultlist:
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.mainContainer, new GenerateDefaulterList());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                getSupportActionBar().setTitle("Defaulter List");
                item.setChecked(true);
                mDrawer.closeDrawers();
                break;

            case R.id.logout:
                Toast.makeText(getApplicationContext(), "Successfully Loggedout", Toast.LENGTH_LONG).show();
                startActivity(new Intent(attendance.this, UserLogin.class));

        }


        return true;
    }


    private void LoadRecyclerViewData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Server_Response");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject o = jsonArray.getJSONObject(i);
                                Listitem item = new Listitem(
                                        o.getString("name"),
                                        o.getString("roll_no")

                                );
                                listitems.add(item);
                            }

                            adapter = new MyAdapter(listitems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("EROR" + error.getMessage());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    public void reload(View v)
    {
        for(Integer i : MyAdapter.chkStu)
        {
            String rno = Integer.toString(i);
            callme(rno);
        }
    }

    public void callme(final String rno)
    {
        System.out.println("Roll new :" +rno);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, p_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> params = new HashMap<>();
                params.put("sub",no);
                params.put("rno",rno);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueu(stringRequest);
    }

    public void getSelected(View v) {
        int i = 0;
        for (Integer i1 : MyAdapter.chkStu) {
//            System.out.println("Roll no :" + i1 + "\n");
            rno = Integer.toString(i1);

//            Toast.makeText(this, rno, Toast.LENGTH_LONG).show();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, p_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();

                    params.put("sub", no);
                    params.put("rno", rno);

                    return params;
                }
            };
            MySingleton.getInstance(attendance.this).addToRequestQueu(stringRequest);
        }
    }

}