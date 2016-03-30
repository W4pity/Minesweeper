package com.example.w4pity.minesweepers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Display;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
    private int size;
    public static boolean lost = false, win = false;
    public static Context cc;
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
       CustomView c = (CustomView)findViewById(R.id.Cv);
        //int width = getDisplay().;
        size=40;
        Log.d("sizewidth", "size" + 7);

        //int height = getMeasuredHeight();
        //c.setMeasuredDimension(width, width);
       // c.setMeasuredDimension(c.getMeasuredWidth(),c.getMeasuredWidth());
      //  c.setLayoutParams(new LinearLayout.LayoutParams(width, width));
        red = new Paint(Paint.ANTI_ALIAS_FLAG);
        green = new Paint(Paint.ANTI_ALIAS_FLAG);
        black = new Paint(Paint.ANTI_ALIAS_FLAG);
        blue = new Paint(Paint.ANTI_ALIAS_FLAG);
        red.setColor(0xFFFF0000);
        green.setColor(0xFF00FF00);
        blue.setColor(0xFF0000FF);
        black.setColor(0xFFFFFFFF);
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
            }


// initialise all the touch arrays to have 16 elements as we know no way to
// accurately determine how many pointers the device will handle. 16 is an
// overkill value for phones and tablets as it would require a minimum of
// four hands on a device to hit that pointer limit
        touches = new boolean[16];
        touchx = new float[16];
        touchy = new float[16];
// initialise the first square that will be shown at all times
        touchx[0] = 200.f;
        touchy[0] = 200.f;
// initialise the rectangle
        square = new Rect(0, 0, size, size);

// we start off with nothing touching the view
        touch = false;

    }

    // public method that needs to be overridden to draw the contents of this
// widget


    public Paint testColor(cell c)
    {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        if(c.nbBombe == 1)
            p.setColor(Color.BLUE);
        else if(c.nbBombe == 2)
            p.setColor(Color.GREEN);
        else if(c.nbBombe == 3)
            p.setColor(Color.YELLOW);
        else
            p.setColor(Color.RED);
        return p;
    }



    public void onDraw(Canvas canvas) {
// call the superclass method
        super.onDraw(canvas);

    // draw the rest of the squares in green to indicate multitouch
    for (int j = 0; j < 10; j++) {
        for (int i = 0; i < 10; i++) {
            grid[j][i].nbCell = j * i + i;
            //canvas.drawRect(new Rect(-0+i*size/2, -size/2, size/2, 0+i*size/2), black);
            canvas.save();
            grid[j][i].state();
            grid[j][i].stateWrite();
            // grid[9][7].actualPaint.setColor(Color.RED);
            grid[j][i].x = i * size;
            grid[j][i].y = j * size;
            grid[j][i].r = new Rect(i * size + 1, j * size + 1, size + i * size, size + j * size);
            canvas.drawRect(grid[j][i].r, grid[j][i].actualPaint);
            if (grid[j][i].cover == Cover.NO && grid[j][i].nbBombe != 0 && grid[j][i].type != Type.MINE)
                canvas.drawText(grid[j][i].nbBombe + "", grid[j][i].r.centerX(), grid[j][i].r.centerY(), testColor(grid[j][i]));

            canvas.restore();


        }

    }


    }

    // public method that needs to be overridden to handle the touches from a
// user
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
                        grid[j][i].cover = Cover.NO;
                        //grid[j][i].type = Type.MINE;
                        Log.d("eventpress", "eventx" + (int)event.getX());
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

