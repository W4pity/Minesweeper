package com.example.w4pity.minesweepers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


// class definition
public class CustomView extends View {
     private Paint red, green, blue, black;
     private Rect square; // the square itself
    public static cell grid[][] = new cell[10][10];
     private boolean touches[]; // which fingers providing input
     private float touchx[]; // x position of each touch
     private float touchy[]; // y position of each touch
     private int first; // the first touch to be rendered
     private boolean touch; // do we have at least on touch
    public int size;
    public static boolean lost = false, win = false;
    public static Context cc;
    public static boolean mode = true, looseToast = false; //1= normal 0 marking
    public static int mark = 0;
    // default constructor for the class that takes in a context
    public CustomView(Context c) {
        super(c);

    }

    // constructor that takes in a context and also a list of attributes
// that were set through XML
    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        cc = c;
        init();


    }

    // constructor that take in a context, attribute set and also a default
// style in case the view is to be styled in a certian way
    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);

    }

    // refactored init method as most of this code is shared by all the
// constructors
    private void init() {

        // create the paint objects for rendering our rectangles

        //int width = getDisplay().;
       // size=40;
        Log.d("sizewidth", "size" + 7);

       // LinearLayout l = (LinearLayout)findViewById(R.id.ly);
       // int width = l.getWidth();
        //Log.d("sizeL", ": "+width);
        //int height = getMeasuredHeight();
        //c.setMeasuredDimension(width, width);
       // c.setMeasuredDimension(c.getMeasuredWidth(),c.getMeasuredWidth());
      //  c.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        black = new Paint(Paint.ANTI_ALIAS_FLAG);
        black.setTextSize(40);
        blue = new Paint(Paint.ANTI_ALIAS_FLAG);
        red.setColor(0xFFFF0000);
        green.setColor(0xFF00FF00);
        blue.setColor(0xFF0000FF);
        black.setColor(Color.BLACK);
        for(int i =0; i<10; i++)
            for(int j = 0; j<10;j++) {
                grid[i][j] = new cell(Type.INCONNU);

            }
        for(int i = 0; i<20; i++)
        {
            //int[] rand = new int[100];
             Random rx = new Random();
            Random ry = new Random();
            int a = rx.nextInt(9);
            int b = ry.nextInt(9);
            if(grid[a][b].type != Type.MINE)
                grid[a][b].type = Type.MINE;
            else i--;
        }
        for(int i =0; i<10; i++)
            for(int j = 0; j<10;j++) {
               grid[i][j].nbBombe = analyze(j,i);

                grid[i][j].stateWrite();


                /*if(grid[i][j].nbBombe == 0)
                    Log.d("nbbombe", "000");*/
            }





    }

    // public method that needs to be overridden to draw the contents of this
// widget

    public void reset()
    {
        CustomView.lost = false;
        mark=0;
        MainActivity.t.setText("Marked: "+mark);
        for(int i =0; i<10; i++)
            for(int j = 0; j<10;j++) {
                CustomView.grid[i][j].cover = Cover.YES;
                //CustomView.grid[i][j].actualPaint.setColor(Color.BLUE);
                CustomView.grid[i][j].type = Type.INCONNU;
                CustomView.grid[i][j].marked = false;
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
        for(int i =0; i<10; i++)
            for(int j = 0; j<10;j++) {
                grid[i][j].nbBombe = analyze(j,i);
                grid[i][j].stateWrite();


            }

        invalidate();
    }


    public Paint testColor(cell c)
    {

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(30);
        if(c.nbBombe == 1)
            p.setColor(Color.BLUE);
        else if(c.nbBombe == 2)
            p.setColor(Color.GREEN);
        else if(c.nbBombe == 3)
            p.setColor(Color.YELLOW);
        else
            p.setColor(Color.RED);
        invalidate();
        return p;
    }



    public void onDraw(Canvas canvas) {
// call the superclass method
            super.onDraw(canvas);

    // draw the rest of the squares in green to indicate multitouch
    for (int j = 0; j < 10; j++) {
        for (int i = 0; i < 10; i++) {
            grid[j][i].nbCell = j * i + i;
           if(lost)
                grid[j][i].cover = Cover.NO;
            //canvas.drawRect(new Rect(-0+i*size/2, -size/2, size/2, 0+i*size/2), black);
            //canvas.save();
            grid[j][i].state();
            grid[j][i].stateWrite();
            // grid[9][7].actualPaint.setColor(Color.RED);
            grid[j][i].x = i * size;
            grid[j][i].y = j * size;
            grid[j][i].r = new Rect(i * size + 1, j * size + 1, size + i * size, size + j * size);
            canvas.drawRect(grid[j][i].r, grid[j][i].actualPaint);
            if (grid[j][i].cover == Cover.NO && grid[j][i].nbBombe != 0 && grid[j][i].type != Type.MINE)
                canvas.drawText(grid[j][i].nbBombe + "", grid[j][i].r.centerX()-10, grid[j][i].r.centerY()+10, testColor(grid[j][i]));
            if(grid[j][i].cover == Cover.NO  && grid[j][i].type == Type.MINE)
                canvas.drawText("M", grid[j][i].r.centerX()-10, grid[j][i].r.centerY()+10, black);

            //canvas.restore();
           // MainActivity.viewMark();
            //mark++;

        }

    }


    }


    
    public void uncover(int j, int i, int nb) {

        if (j <= 9 && j >= 0 && i <= 9 && i >= 0) {
        if(grid[j][i].cover!=Cover.NO && !grid[j][i].marked) {
            grid[j][i].cover = Cover.NO;

            if (grid[j][i].nbBombe == 0) {



                uncover(j, i - 1, nb);
                uncover(j, i + 1, nb);
                uncover(j - 1, i, nb);
                uncover(j + 1, i, nb);
                uncover(j - 1, i - 1, nb);
                uncover(j - 1, i + 1, nb);
                uncover(j + 1, i + 1, nb);
                uncover(j + 1, i - 1, nb);

            }

            }
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN && !lost) {
           // grid[1][4].type = Type.MINE;
            Log.d("eventpress", "fuck " + (int)event.getX());
            //grid[1][4].actualPaint.setColor(Color.RED);
          //  int pointer_id = event.getPointerId(event.getActionIndex());
            for(int j = 0; j < 10; j++) {
                for(int i = 0; i<10; i++) {

                   // Log.d("eventpress", "eventx" + (int)event.getX());
                   if(grid[j][i].r.contains((int)event.getX(), (int)event.getY()))
                    {
                        if(grid[j][i].type == Type.MINE)
                        {
                            looseToast =! looseToast;
                        }
                        if(grid[j][i].nbBombe == 0 && mode)
                            uncover(j, i, 0);
                        if(!grid[j][i].marked) {
                            if (mode)
                                grid[j][i].cover = Cover.NO;
                            else {
                                grid[j][i].marked = !grid[j][i].marked;
                                if (grid[j][i].marked && grid[j][i].cover == Cover.YES )
                                    mark++;
                                else
                                    if( grid[j][i].cover == Cover.YES)
                                        mark--;
                               try {
                                    MainActivity.t.setText("Marked: "+mark);
                                }
                                catch(Exception e)
                                {
                                    Log.d("exep", e.toString());
                                }

                            }
                        }
                        else
                        if(!mode && grid[j][i].cover == Cover.YES) {
                            grid[j][i].marked = !grid[j][i].marked;
                            mark--;
                            MainActivity.t.setText("Marked: "+mark);
                        }


                        //grid[j][i].type = Type.MINE;

                    }
                }
            }


        invalidate();
        return true;
    }

        return super.onTouchEvent(event);
    }

    public int analyze(int x, int y)
    {
        int nb = 0;
        if(x==0 && y==0)
        {
            if(grid[y][x+1].type == Type.MINE)
            nb++;
            if(grid[y+1][x+1].type == Type.MINE)
                nb++;
                if(grid[y+1][x].type == Type.MINE)
                    nb++;
        }
        else if(x==9 && y==0)
        {
            if(grid[y][x-1].type == Type.MINE)
                nb++;
            if(grid[y+1][x-1].type == Type.MINE)
                nb++;
            if(grid[y+1][x].type == Type.MINE)
                nb++;
        }
        else if(x==0 && y==9)
        {
            if(grid[y-1][x].type == Type.MINE)
                nb++;
            if(grid[y-1][x+1].type == Type.MINE)
                nb++;
            if(grid[y][x+1].type == Type.MINE)
                nb++;
        }
        else if (x==9 && y==9)
        {
            if(grid[y-1][x-1].type == Type.MINE)
                nb++;
            if(grid[y-1][x].type == Type.MINE)
                nb++;
            if(grid[y][x-1].type == Type.MINE)
                nb++;
        }

        else if(x==0)
        {
            if(grid[y-1][x].type == Type.MINE)
                nb++;
            if(grid[y+1][x].type == Type.MINE)
                nb++;
            if(grid[y-1][x+1].type == Type.MINE)
                nb++;
            if(grid[y][x+1].type == Type.MINE)
                nb++;
            if(grid[y+1][x+1].type == Type.MINE)
                nb++;
        }
        else if(x==9)
        {
            if(grid[y-1][x-1].type == Type.MINE)//
                nb++;
            if(grid[y-1][x].type == Type.MINE)//
                nb++;
            if(grid[y][x-1].type == Type.MINE)//
                nb++;
            if(grid[y+1][x-1].type == Type.MINE)
                nb++;
            if(grid[y+1][x].type == Type.MINE)
                nb++;
        }

        else if(y==0)
        {
            if(grid[y][x-1].type == Type.MINE)//
                nb++;
            if(grid[y+1][x-1].type == Type.MINE)//
                nb++;
            if(grid[y+1][x].type == Type.MINE)//
                nb++;
            if(grid[y+1][x+1].type == Type.MINE)
                nb++;
            if(grid[y][x+1].type == Type.MINE)
                nb++;
        }

        else if(y==9)
        {
            if(grid[y][x-1].type == Type.MINE)
                nb++;
            if(grid[y-1][x+1].type == Type.MINE)//
                nb++;
            if(grid[y-1][x].type == Type.MINE)//
                nb++;
            if(grid[y-1][x-1].type == Type.MINE)
                nb++;
            if(grid[y][x+1].type == Type.MINE)//
                nb++;
        }
        else
        {
            if(grid[y][x-1].type == Type.MINE)
                nb++;
            if(grid[y][x+1].type == Type.MINE)//
                nb++;
            if(grid[y-1][x].type == Type.MINE)//
                nb++;
            if(grid[y+1][x].type == Type.MINE)
                nb++;
            if(grid[y-1][x-1].type == Type.MINE)//
                nb++;
            if(grid[y-1][x+1].type == Type.MINE)//
                nb++;
            if(grid[y+1][x+1].type == Type.MINE)//
                nb++;
            if(grid[y+1][x-1].type == Type.MINE)//
                nb++;
        }
        if(nb == 0)
            return 0;
        else return nb;

    }

}

