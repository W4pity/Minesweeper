package com.example.w4pity.minesweepers;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    static TextView t;
    static Context cont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        t=(TextView)findViewById(R.id.tv2);
        Display d = getWindowManager().getDefaultDisplay();
        int width = d.getWidth();
        CustomView c = (CustomView)findViewById(R.id.Cv);
        cont = getApplicationContext();
        c.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        c.size = width/10;
        Button bO = (Button)findViewById(R.id.reset);
        bO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView c = (CustomView)findViewById(R.id.Cv);
                c.reset();


                Log.d("resetapp", "reseted");
            }
        });



        final Button bu = (Button)findViewById(R.id.bu);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomView.mode = !CustomView.mode;
                if(CustomView.mode)
                    bu.setText("uncover mode");
                else bu.setText("marked mode");

                Log.d("resetapp", "reseted");


            }
        });
        //CustomView cv = new CustomView(this);

    }


    //set la textview2, drapeaux

    /*static void viewMark()
    {
       t.setText("Marked");
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
