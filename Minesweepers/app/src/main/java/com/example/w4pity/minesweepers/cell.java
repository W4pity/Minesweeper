package com.example.w4pity.minesweepers;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

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
    public String nbBombe;
    public Cover cover = Cover.YES;
    public Paint  blue = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paint red = new Paint(Paint.ANTI_ALIAS_FLAG);
    public Paint actualPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    public cell (Type t)
    {
        actualPaint.setColor(0xFF0000FF);
        blue.setColor(0xFF0000FF);
        red.setColor(0xFFFF0000);
        type = t;
    }

    public void state()
    {
        if (cover == Cover.YES)
            actualPaint.setColor(Color.BLUE);
        else if(cover == Cover.NO) {
            actualPaint.setColor(Color.WHITE);

            if(type == Type.MINE)
                actualPaint.setColor(Color.RED);
        }
    }


}
