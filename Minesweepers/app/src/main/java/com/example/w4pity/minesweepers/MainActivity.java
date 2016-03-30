package com.example.w4pity.minesweepers;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bO = (Button)findViewById(R.id.reset);
        bO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                Log.d("resetapp", "reseted");
            }
        });
        //CustomView cv = new CustomView(this);
    }


    public void reset()
    {
        for(int i =0; i<10; i++)
            for(int j = 0; j<10;j++) {
                CustomView.grid[i][j].cover = Cover.YES;
                //CustomView.grid[i][j].actualPaint.setColor(Color.BLUE);
                CustomView.grid[i][j].type = Type.INCONNU;
            }
        for(int i = 0; i<20; i++)
        {
            //int[] rand = new int[100];
            Random rx = new Random();
            Random ry = new Random();
            int a = rx.nextInt(9);
            int b = ry.nextInt(9);
            if(CustomView.grid[a][b].type != Type.MINE)
                CustomView.grid[a][b].type = Type.MINE;
            else i--;
        }
        CustomView.lost = false;
    }

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
