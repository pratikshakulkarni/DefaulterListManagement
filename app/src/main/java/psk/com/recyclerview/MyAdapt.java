package psk.com.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PRATIKSHA on 25-10-2017.
 */

public class MyAdapt extends RecyclerView.Adapter<MyAdapt.ViewHolder> {
    private List<NewList> ls;
    Context c;


    public MyAdapt(List<NewList> ls, Context c) {
        this.ls = ls;
        this.c = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dlist,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewList nl = ls.get(position);

        holder.dr.setText(nl.getRno());
        holder.dp.setText(nl.getPer());

    }

    @Override
    public int getItemCount() {
        return ls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView dn;
        public TextView dr;
        public TextView dp;

        public ViewHolder(View itemView) {
            super(itemView);


             dr = (TextView) itemView.findViewById(R.id.d_rno);
             dp = (TextView) itemView.findViewById(R.id.d_per);
        }
    }
}
