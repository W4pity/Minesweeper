package com.example.w4pity.minesweepers;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.widget.Toast;

/**
 * Created by W4pity on 30/03/2016.
 */
enum Type {INCONNU,MINE ,DRAPEAUX, VIDE } ;
enum Cover{YES, NO};
public class cell {
    public int size = 40;
    public int nbCell;
   public Rect r;
    public int x, y;
    public Type type;
    public int nbBombe;
    public Cover cover = Cover.YES;
    public Paint  blue = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paint  black = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paint  green = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paint red = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paint actualPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paint actualWrite = new Paint(Paint.ANTI_ALIAS_FLAG);
    public boolean marked = false;


    public cell (Type t)
    {
        actualPaint.setColor(0xFF0000FF);
        actualWrite.setColor(0xFF0000FF);
        blue.setColor(0xFF0000FF);
        red.setColor(0xFFFF0000);
        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        black = new Paint(Paint.ANTI_ALIAS_FLAG);

        green.setColor(0xFF00FF00);
        black.setColor(0xFFFFFFFF);
        type = t;
    }

    public void state()
    {

        if (cover == Cover.YES) {
            actualPaint.setColor(Color.BLUE);
            if(marked)
                actualPaint.setColor(Color.YELLOW);
        }

        else if(cover == Cover.NO) {
            actualPaint.setColor(Color.WHITE);

            if(type == Type.MINE) {
                actualPaint.setColor(Color.RED);
               // Toast.makeText(CustomView.cc, "GAME OVER", Toast.LENGTH_LONG).show();

                CustomView.lost = true;
            }

        }
    }

    public void stateWrite()
    {
        if(nbBombe == 1)
            actualWrite.setColor(Color.BLUE);
       else if(nbBombe == 2)
            actualWrite.setColor(Color.GREEN);
        else if(nbBombe == 3)
            actualWrite.setColor(Color.YELLOW);
        else
            actualWrite.setColor(Color.RED);
    }


}
