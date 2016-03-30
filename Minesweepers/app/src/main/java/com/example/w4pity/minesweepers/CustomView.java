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
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Random;


// class definition
public class CustomView extends View {
     private Paint red, green, blue, black;
     private Rect square; // the square itself
    private cell grid[][] = new cell[10][10];
     private boolean touches[]; // which fingers providing input
     private float touchx[]; // x position of each touch
     private float touchy[]; // y position of each touch
     private int first; // the first touch to be rendered
     private boolean touch; // do we have at least on touch
    private int size;

    // default constructor for the class that takes in a context
    public CustomView(Context c) {
        super(c);
        init();
    }

    // constructor that takes in a context and also a list of attributes
// that were set through XML
    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        init();
    }

    // constructor that take in a context, attribute set and also a default
// style in case the view is to be styled in a certian way
    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);
        init();
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
               grid[j][i].nbBombe = analyze(j,i);
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
    public void onDraw(Canvas canvas) {
// call the superclass method
        super.onDraw(canvas);

        // draw the rest of the squares in green to indicate multitouch
       for(int j = 0; j < 10; j++) {
           for(int i = 0; i<10; i++)
           {
              grid[j][i].nbCell = j*i+i;
               //canvas.drawRect(new Rect(-0+i*size/2, -size/2, size/2, 0+i*size/2), black);
               canvas.save();
               grid[j][i].state();
              // grid[9][7].actualPaint.setColor(Color.RED);
               grid[j][i].x = i*size;
               grid[j][i].y = j*size;
                grid[j][i].r = new Rect(i*size+1, j*size+1, size+i*size, size+j*size);
               canvas.drawRect(grid[j][i].r, grid[j][i].actualPaint);
               if(grid[j][i].cover == Cover.NO)
                   canvas.drawText(grid[j][i].nbBombe, grid[j][i].r.centerX(), grid[j][i].r.centerY(), green);

               canvas.restore();




           }

       }
         /*  if(touches[i]) {
                canvas.save();
                canvas.translate(touchx[i], touchy[i]);
                if(first == i)
                    canvas.drawRect(square, red);
                else
                    canvas.drawRect(square, green);
                canvas.restore();
            }*/
      //  }
// if there is no touches then just draw a single blue square in the last place
       /* if(!touch) {
            canvas.save();
            //canvas.translate(touchx[first], touchy[first]);
            canvas.drawRect(square, blue);
            canvas.restore();
        }*/


    }

    // public method that needs to be overridden to handle the touches from a
// user
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
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




        // determine what kind of touch event we have
      /*  if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
// this indicates that the user has placed the first finger on the
// screen what we will do here is enable the pointer, track its location
// and indicate that the user is touching the screen right now
// we also take a copy of the pointer id as the initial pointer for this
// touch
            int pointer_id = event.getPointerId(event.getActionIndex());
            touches[pointer_id] = true;
            touchx[pointer_id] = event.getX();
            touchy[pointer_id] = event.getY();
            touch = true;
            first = pointer_id;
            invalidate();
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_UP) {
// this indicates that the user has removed the last finger from the
// screen and has ended all touch events. here we just disable the
// last touch.
            int pointer_id = event.getPointerId(event.getActionIndex());
            touches[pointer_id] = false;
            first = pointer_id;
            touch = false;
            invalidate();
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_MOVE) {
// indicates that one or more pointers has been moved. Android for
// efficiency will batch multiple move events into one. thus you
// have to check to see if all pointers have been moved.
            for (int i = 0; i < 16; i++) {
                int pointer_index = event.findPointerIndex(i);
                if (pointer_index != -1) {
                    touchx[i] = event.getX(pointer_index);
                    touchy[i] = event.getY(pointer_index);
                }
            }
            invalidate();
            return true;
        }
        else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
// indicates that a new pointer has been added to the list
// here we enable the new pointer and keep track of its position
            int pointer_id = event.getPointerId(event.getActionIndex());
            touches[pointer_id] = true;
            touchx[pointer_id] = event.getX(pointer_id);
            touchy[pointer_id] = event.getY(pointer_id);
            invalidate();
            return true;
        } else if(event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
// indicates that a pointer has been removed from the list
// note that is is possible for us to lose our initial pointer
// in order to maintain some semblance of an active pointer
// (this may be needed depending on the application) we set
// the earliest pointer to be the new first pointer.
            int pointer_id = event.getPointerId(event.getActionIndex());
            touches[pointer_id] = false;
            if(pointer_id == first) {
                for(int i = 0; i < 16; i++)
                    if(touches[i]) {
                        first = i;
                        break;
                    }
            }
            invalidate();
            return true;
        }
*/
// if we get to this point they we have not handled the touch
// ask the system to handle it instead
        invalidate();
        return true;
    }
        return super.onTouchEvent(event);
    }

    public String analyze(int x, int y)
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
            return " ";
        else return nb+"";

    }

}

