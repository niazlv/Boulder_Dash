package com.example.bolderdash;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.bolderdash.Persons.All;
import com.example.bolderdash.Persons.Diamond;
import com.example.bolderdash.Persons.Dir;
import com.example.bolderdash.Persons.Player;
import com.example.bolderdash.Persons.Stone;
import com.example.bolderdash.Persons.Void;
import com.example.bolderdash.Persons.Wall;
import com.example.bolderdash.Persons.breakableWall;
import com.example.bolderdash.Persons.enemyBatterfly;
import com.example.bolderdash.Persons.enemyRect;

import java.io.IOException;

import static android.os.SystemClock.sleep;

public class Draw2D extends View {
    //MainActivity main=new MainActivity();
    public int[][] map;
    public boolean Editor=false;
    public static int heihgt,weight;
    //float rect_weidth_sc;
    //float rect_height_sc;
    public int rect_height,rect_width;
    public String strx="0";
    public String stry="0";
    int mapsize=MainActivity.mapsize;
    public Object[][] mapo=new Object[mapsize+2][mapsize+2];
    int count=0;
    boolean run=false;
    Bitmap mBitmap;
    Canvas canvas;



    /**Картинка*/
    private Bitmap bmpbk;
    private Bitmap bmppl,bmppl1,bmppl2;
    private Bitmap bmpwl;
    private Bitmap bmpdr;
    private Bitmap bmpst;
    private Bitmap bmpdm;
    private Bitmap bmpre,bmpre1;
    private Bitmap bmpbf,bmpbf1;


    public Draw2D(Context context, AttributeSet attrs) {
        super(context, attrs);
        map=Map(mapsize);
    }

    private Paint mPaint = new Paint();
    public Path path=new Path();

    public int[][] Map(int sizes){      //Auto generate array for map *.* Автоматическая генерация карты
            /*
    0 - это пустота
    1 - это игрок
    2 - это грязь
    3 - это несрушимая стена
    4 - это камень
    5 - это алмаз
    6 - противник квадратик
    7 - противник batterfly(бабочка)
     */
            sizes=sizes+2;
        map=new int[sizes][sizes];

        for(int i = 0; i<sizes; i++){
            for(int j = 0; j<sizes; j++){

                if(j==0||i==0||i==sizes-1||j==sizes-1) {      //Постройка нерушимых стен(границ карты)
                    map[i][j]=3;
                }
                else{
                    map[i][j]=2;
                }
            }
        }



        return map;
    }


    public void Mapo(int[][] map){
        int c;
        Object o=null;
        for(int y=0;y<mapsize+2;y++){
            for(int x=0;x<mapsize+2;x++){
                try {
                    c = map[y][x];
                    if (c == 0) o = new Void();
                    if (c == 1) o = new Player();
                    if (c == 2) o = new Dir();
                    if (c == 3) o = new breakableWall();
                    if (c == 4) o = new Stone();
                    if (c == 5)
                    {
                        o = new Diamond();
                    }
                    if (c == 6) o = new enemyRect();
                    if (c == 7) o = new enemyBatterfly();
                    if (c == 8) o = new Wall();
                    mapo[y][x] = o;
                    o=null;
                    ((All)mapo[y][x]).setXY(x,y);
//                    ((All)mapo[y][x]).last_x=x;
//                    ((All)mapo[y][x]).last_y=y;

                }
                catch(Exception e)
                {
                    int i=0;
                    i=i+1;
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBitmap);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.setBitmap(mBitmap);
        super.onDraw(canvas);
        heihgt=getHeight();
        weight=getWidth();
        rect_width=weight/(mapsize);
        //float rect_height=rect_width;
        rect_height=heihgt/(mapsize);
        //rect_weidth_sc=weight/10;
        //rect_height_sc=heihgt/10;
        canvas.scale(1,1);


        if(rect_width<rect_height) rect_height=rect_width;
        else rect_width=rect_height;


        if(!run){

            bmppl = BitmapFactory.decodeResource(getResources(), R.drawable.a04);
            bmppl1 = BitmapFactory.decodeResource(getResources(), R.drawable.a14);
            bmppl2 = BitmapFactory.decodeResource(getResources(), R.drawable.a15);
            bmppl = Bitmap.createScaledBitmap(bmppl, (int) rect_width, (int) rect_height, true);
            bmppl1 = Bitmap.createScaledBitmap(bmppl1, (int) rect_width, (int) rect_height, true);
            bmppl2 = Bitmap.createScaledBitmap(bmppl2, (int) rect_width, (int) rect_height, true);

            bmpdr=BitmapFactory.decodeResource(getResources(), R.drawable.a01);
            bmpdr=Bitmap.createScaledBitmap(bmpdr,(int)rect_width, (int)rect_height, true);

            bmpwl=BitmapFactory.decodeResource(getResources(), R.drawable.a05);
            bmpwl=Bitmap.createScaledBitmap( bmpwl ,(int)rect_width, (int)rect_height, true);

            bmpbk=BitmapFactory.decodeResource(getResources(), R.drawable.a00);
            bmpbk=Bitmap.createScaledBitmap(bmpbk,(int)rect_width, (int)rect_height, true);

            bmpst=BitmapFactory.decodeResource(getResources(), R.drawable.a02);
            bmpst=Bitmap.createScaledBitmap( bmpst ,(int)rect_width, (int)rect_height, true);

            bmpdm=BitmapFactory.decodeResource(getResources(), R.drawable.a03);
            bmpdm=Bitmap.createScaledBitmap( bmpdm ,(int)rect_width, (int)rect_height, true);

            bmpre = BitmapFactory.decodeResource(getResources(), R.drawable.a06);
            bmpre1=BitmapFactory.decodeResource(getResources(), R.drawable.a20);
            bmpre=Bitmap.createScaledBitmap( bmpre ,(int)rect_width, (int)rect_height, true);
            bmpre1=Bitmap.createScaledBitmap( bmpre1 ,(int)rect_width, (int)rect_height, true);

            bmpbf=BitmapFactory.decodeResource(getResources(), R.drawable.a07);
            bmpbf1=BitmapFactory.decodeResource(getResources(), R.drawable.a21);
            bmpbf=Bitmap.createScaledBitmap( bmpbf ,(int)rect_width, (int)rect_height, true);
            bmpbf1=Bitmap.createScaledBitmap( bmpbf1 ,(int)rect_width, (int)rect_height, true);


            run=true;
        }


        // стиль Заливка
        mPaint.setStyle(Paint.Style.FILL);

        // закрашиваем холст белым цветом
        mPaint.setColor(Color.WHITE);
        canvas.drawPaint(mPaint);

        // Рисуем желтый круг
       // mPaint.setAntiAlias(true);
       // mPaint.setColor(Color.YELLOW);
       // canvas.drawCircle(950, 30, 25, mPaint);
        // Рисуем зелёный прямоугольник
       // mPaint.setColor(Color.GREEN);


        int c;
        float x,y;
        mPaint.setTextSize(50);
        //canvas.drawText(str, 100,100,mPaint);

        for(float yx=1;yx<map.length-1;yx++){
            for(float xx=1; xx<map.length-1;xx++){
                count++;
                if(count>=3) count=0;
                x=(xx-1)*rect_width;
                y=(yx-1)*rect_height;
                /*
                mPaint.setColor(Color.BLUE);
                canvas.drawText(strx, 100,100,mPaint);
                canvas.drawText(stry, 100,200,mPaint);
                mPaint.setAlpha(2);
                canvas.drawRect(rect_weidth_sc*3, rect_height_sc*7, rect_weidth_sc*4,rect_height_sc*8,mPaint);
                canvas.drawRect(rect_weidth_sc*3, rect_height_sc*9, rect_weidth_sc*4,rect_height_sc*10,mPaint);
                canvas.drawRect(rect_weidth_sc*2, rect_height_sc*8, rect_weidth_sc*3,rect_height_sc*9,mPaint);
                canvas.drawRect(rect_weidth_sc*4, rect_height_sc*8, rect_weidth_sc*5,rect_height_sc*9,mPaint);


                 */
                //Mapo(map);
                All cs=(All)mapo[(int)yx][(int)xx];
                //c=map[(int)yx][(int)xx];
                c=cs.Type;
                if(c==0){       //void
                    //mPaint.setColor(Color.BLACK);
                    canvas.drawBitmap(bmpbk, x,y,null);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==1){       //player
                    //mPaint.setColor(Color.GREEN);
                    if(count%2==0)canvas.drawBitmap(bmppl1, x,y,null);
                    else canvas.drawBitmap(bmppl2,x,y,null);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==2){       //dirt
                    canvas.drawBitmap(bmpdr, x,y,null);
                    //mPaint.setColor(Color.GRAY);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==3){       //don't breakble wall
                    canvas.drawBitmap(bmpwl, x,y,null);
                    //mPaint.setColor(Color.DKGRAY);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==4){       //stone
                    //mPaint.setColor(Color.YELLOW);
                    canvas.drawBitmap(bmpst, x,y,null);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==5){       //diamond
                    //mPaint.setColor(Color.CYAN);
                    canvas.drawBitmap(bmpdm, x,y,null);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==6){       //rect enemy
                    //mPaint.setColor(0x0FFF0000);

                    if(count%2==0)canvas.drawBitmap(bmpre, x,y,null);
                    else canvas.drawBitmap(bmpre1,x,y,null);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==7){       //baterfly
                    //mPaint.setColor(Color.RED );
                    if(count%2==0)canvas.drawBitmap(bmpbf, x,y,null);
                    else canvas.drawBitmap(bmpbf1, x,y,null);
                    //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
                }
                if(c==8){
                    canvas.drawBitmap(bmpwl, x,y,null);
                //mPaint.setColor(Color.DKGRAY);
                //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
            }
                //mPaint.setColor(Color.BLACK);
                //canvas.drawRect(x,y,(float)(x+rect_width*0.05),(float)(y+rect_height*0.05 ),mPaint);


                if(Editor){
                    x=(16-1)*rect_width;
                    y=(16-1)*rect_height;
                    canvas.drawBitmap(bmpbk,(5-1)*rect_width,(18)*rect_height,null);
                    canvas.drawBitmap(bmppl,(6-1)*rect_width,(18)*rect_height,null);
                    canvas.drawBitmap(bmpdr,(7-1)*rect_width,(18)*rect_height,null);
                    canvas.drawBitmap(bmpwl,(8-1)*rect_width,(18)*rect_height,null);
                    canvas.drawBitmap(bmpst,(9-1)*rect_width,(18)*rect_height,null);
                    canvas.drawBitmap(bmpdm,(10-1)*rect_width,(18)*rect_height,null);
                    canvas.drawBitmap(bmpre,(11-1)*rect_width,(18)*rect_height,null);
                    canvas.drawBitmap(bmpbf,(12-1)*rect_width,(18)*rect_height,null);
                    Editor=!Editor;
                }
            }

            }
        }
        //canvas.drawRect(x,y,x+rect_width,y+rect_height,mPaint);
}
