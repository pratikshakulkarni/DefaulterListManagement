package psk.com.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PRATIKSHA on 06-10-2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    String rno,no=LoginNext.sub;
    public String subject=LoginNext.sub;
    private static final String addurl = "your url";

    ArrayList<Listitem> checkedStudents=new ArrayList<>();
    public static ArrayList<Integer> chkStu = new ArrayList<Integer>();

    private List<Listitem> listitems;
    private Context context;

    public MyAdapter(List<Listitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyAdapter.ViewHolder holder, int position) {
        final Listitem listitem = listitems.get(position);

        holder.stu_name.setText(listitem.getName());
        holder.stu_rno.setText(listitem.getRno());
        holder.chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,listitem.getName(),Toast.LENGTH_LONG).show();
                boolean checked = holder.chk.isChecked();
                if(checked)
                { chkStu.add(Integer.parseInt(listitem.getRno()));
                StringRequest sr = new StringRequest(Request.Method.POST, addurl,
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
                        HashMap<String,String> params  = new HashMap<>();
                        params.put("sub",no);
                        params.put("rno",listitem.getRno());
                        return params;
                    }
                };
                MySingleton.getInstance(context).addToRequestQueu(sr);
                }
                else
                    chkStu.remove(Integer.parseInt(listitem.getRno()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView stu_name;
        public TextView stu_rno;
        public CheckBox chk;



        public ViewHolder(View itemView) {
            super(itemView);

            stu_rno = (TextView) itemView.findViewById(R.id.stu_rno);
            stu_name = (TextView) itemView.findViewById(R.id.stu_name);
            chk= (CheckBox) itemView.findViewById(R.id.chk);


        }

    }
}
