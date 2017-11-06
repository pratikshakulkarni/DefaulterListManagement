package psk.com.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import static psk.com.recyclerview.R.id.db;

public class LoginNext extends AppCompatActivity {
    public static String sub;
    CheckBox c1,c2,c3,c4,c5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_next);

        c1 = (CheckBox) findViewById(R.id.db);
        c2 = (CheckBox) findViewById(R.id.se);
        c3 = (CheckBox) findViewById(R.id.is);
        c4 = (CheckBox) findViewById(R.id.cn);
        c5 = (CheckBox) findViewById(R.id.toc);

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c1.isChecked())
                {
                sub="dbms";
                startActivity(new Intent(LoginNext.this,attendance.class));}
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c2.isChecked())
                {
                sub="sepm";
                startActivity(new Intent(LoginNext.this,attendance.class));}
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c3.isChecked())
                {
                sub="isee";
                startActivity(new Intent(LoginNext.this,attendance.class));}

            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c4.isChecked())
                {
                sub="cn";
                startActivity(new Intent(LoginNext.this,attendance.class));}

            }
        });
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(c5.isChecked())
                {
                sub="toc";
                startActivity(new Intent(LoginNext.this,attendance.class));}

            }
        });




    }


}
