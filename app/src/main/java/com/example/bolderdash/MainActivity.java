package com.example.bolderdash;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.bolderdash.Persons.All;

import java.util.Timer;
import java.util.TimerTask;

import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity{
    public static Draw2D draw2D;
    private JoystickView joystick;
    /** Переменные системы- не менять!*/
        public static float xs,ys;
        public static boolean right,left,top,bottom;
        public float rect_heihgt_f_button;
        public float rect_weight_f_button;
        boolean isCheckedsw=false;
        int dx=1,dy=1;
        float xt,yt,xst,yst;
    public int width,height;
    public static int xm=0,ym=0;

    public static float objectToDrawHeight = 50; //Specified in plain pixels
    public static float objectToDrawWidth = 50; //Specified in plain pixels
    /** Настройки игры*/
    public static int Scope=0;
    public static int mapsize=16;       //стандартно должно быть 16
    public static int period=150;       //250
    public static int mapnum=0,num=1;
    EditText editTextnum,editTextmapnum;




    public int mapbac[][]=new int[mapsize+2][mapsize+2];

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        draw2D=findViewById(R.id.draw2D);
        //setContentView(draw2D);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        Button bbottom = (Button) findViewById(R.id.downb);
        bbottom.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bottom=true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    bottom=false;
                }
                return false;
            }
        });
        Button btop = (Button) findViewById(R.id.upb);
        btop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    top=true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP) top=false;
                return false;
            }
        });
        Button bleft = (Button) findViewById(R.id.leftb);
        bleft.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    left=true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP) left=false;
                return false;
            }
        });
        Button bright = (Button) findViewById(R.id.rightb);
        bright.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    right=true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP) right=false;
                return false;
            }
        });


        editTextnum=findViewById(R.id.editTextnum);
        editTextmapnum=findViewById(R.id.editTextmapnum);
        //editTextnum.setText(num);
        //editTextmapnum.setText(mapnum);
        editTextnum.setInputType(2);
        editTextmapnum.setInputType(2);

//        float density = getResources().getDisplayMetrics().density;
//        objectToDrawHeight *= density;
//        objectToDrawWidth *= density;


        joystick= (JoystickView) findViewById(R.id.joystickView);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
                                       @Override
                                       public void onMove(int angle, int strength) {
                                           if (angle > (90 - 45) && angle < (90 + 45) && strength > 30) top = true;
                                           else top = false;
                                           if (angle > (180 - 45) && angle < (180 + 45) && strength > 30) left = true;
                                           else left = false;
                                           if (angle > (270 - 45) && angle < (270 + 45) && strength > 30) bottom = true;
                                           else bottom = false;
                                           if (((angle < (90 - 40) &&angle>=0)||(angle>(270+45)&&angle<=360))&&strength > 40) right = true;
                                           else right=false;
                                       }
        });
        joystick.setButtonColor(Color.argb(86,60,20,150));
        joystick.setBackgroundColor(Color.argb(50, 135,24,146));




        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // ночная тема не активна, используется светлая тема
                ((Switch)findViewById(R.id.joystickswitch)).setTextColor(Color.BLACK);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                // ночная тема активна, и она используется
                ((Switch)findViewById(R.id.joystickswitch)).setTextColor(Color.WHITE);
                findViewById(R.id.frameLayout).setBackgroundColor(Color.rgb(33, 33, 34));
                break;
        }





        DisplayMetrics displaymetrics = getResources().getDisplayMetrics(); //инстализация для получения координат прикосновения

        rect_heihgt_f_button=displaymetrics.heightPixels/10;    //делаем сетку разделения экрана
        rect_weight_f_button=displaymetrics.widthPixels/10;


        xs=rect_weight_f_button*5;      //Делаем, чтобы серединой прикосновения был экран(единождая настрнойка)
        ys=rect_heihgt_f_button*5;




        start_map();
        //draw2D.setVisibility(View.INVISIBLE);
        joystick.setVisibility(View.INVISIBLE);
        findViewById(R.id.reset).setVisibility(View.INVISIBLE);
        findViewById(R.id.Skip).setVisibility(View.INVISIBLE);
        Visibleb(false);
        Switch sw = (Switch) findViewById(R.id.joystickswitch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) //Line A
            {
                isCheckedsw=isChecked;
            }
        });

        findViewById(R.id.contboy).setY(rect_heihgt_f_button*7);
        findViewById(R.id.contboy).setX(rect_heihgt_f_button*1);
        }
        public void onClickStart(View view){
            draw2D.setVisibility(View.VISIBLE);
            joystick.setVisibility(View.VISIBLE);
            findViewById(R.id.reset).setVisibility(View.VISIBLE);
            findViewById(R.id.Skip).setVisibility(View.VISIBLE);

            if(isCheckedsw) {
                Visibleb(true);
                joystick.setVisibility(View.INVISIBLE);
            }
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            width = size.x;
            height = size.y;




            try {
                //num = Integer.valueOf(String.valueOf(editTextnum.getText()));
                if(num<1||num>2){
                    num=1;
                    Toast.makeText(getApplicationContext(),"The map number was greater than 2 or less than 1, so it was replaced by 1",Toast.LENGTH_SHORT).show();
                }
                //mapnum=Integer.valueOf(String.valueOf(editTextmapnum.getText()));
                if(mapnum<0||mapnum>75){
                    mapnum=1;
                    Toast.makeText(getApplicationContext(),"The map number was greater than 75 or less than 0, so it was replaced by 1",Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getApplicationContext(),"Set map successful!",Toast.LENGTH_SHORT).show();
            }catch (Exception e){
            }

            start_map();
            setValues();



            findViewById(R.id.Start).setVisibility(View.INVISIBLE);
            findViewById(R.id.editor).setVisibility(View.INVISIBLE);
            editTextnum.setVisibility(View.INVISIBLE);
            editTextmapnum.setVisibility(View.INVISIBLE);
            findViewById(R.id.textView).setVisibility(View.INVISIBLE);
            findViewById(R.id.textView2).setVisibility(View.INVISIBLE);
            findViewById(R.id.joystickswitch).setVisibility(View.INVISIBLE);



        }
        public void onClickEDITOR(View view){
        draw2D.setVisibility(View.VISIBLE);
        draw2D.Editor=true;
            //findViewById(R.id.Start).setVisibility(View.INVISIBLE);
            findViewById(R.id.editor).setVisibility(View.INVISIBLE);
            for(float yx=1;yx<draw2D.mapo.length-1;yx++){
                for(float xx=1; xx<draw2D.mapo.length-1;xx++){
                    xt=(xx-1)*draw2D.rect_width;
                    yt=(yx-1)*draw2D.rect_height;
                    xst=((int)xs-1)*draw2D.rect_width;
                    yst=((int)ys-1)*draw2D.rect_height;

                }
            }
    }
    public void Reset(View view){
        start_map();
        Toast.makeText(getApplicationContext(),"Level reset successful",Toast.LENGTH_SHORT).show();
    }
    public void Skip(View view){
        mapnum++;
        start_map();
        Toast.makeText(getApplicationContext(),"Level skiped successful",Toast.LENGTH_SHORT).show();
    }




    /**Управление кнопками*/







        public void start_map(){
            /** Перерисовка карты*/
            draw2D.Map(mapsize);

             /*
                0 - это пустота
                1 - это игрок
                2 - это грязь
                3 - это несрушимая стена
                4 - это камень
                5 - это алмаз
                6 - противник квадратик
                7 - противник batterfly(бабочка)
                8 - стена
            */



            ViewGroup.LayoutParams params = joystick.getLayoutParams();
            params.width = width/2;
            params.height = height/2;
            joystick.setLayoutParams(params);


            if(mapnum>75){
                mapnum=1;
                num++;
            }
            if(num>2) num=1;

            mapbac=map(num,mapnum,mapsize);


            //Object o = null;

            for(int y=0;y<mapsize+2;y++){
                for(int x=0;x<mapsize+2;x++){
                    draw2D.map[y][x]=mapbac[y][x];
                }
            }

            draw2D.Mapo(draw2D.map);
            
            
            
            Scope=0;

            for(int i=0;i<draw2D.mapo.length;i++) {
                for (int j=0; j<draw2D.mapo.length;j++){
                    mapbac[i][j] = ((All)draw2D.mapo[i][j]).Type;
                }
            }
        }



    /**Обработка косания по экрану*/ //На данный момент негде не используется
    public boolean onTouchEvent(MotionEvent event) {
        xs=width/draw2D.getWidth();
        ys=height/draw2D.getHeight();
        draw2D.strx=""+xs;
        draw2D.stry=""+ys;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                joystick.setX((event.getX()/xs)-draw2D.rect_width*4);
                joystick.setY((event.getY()/ys)-draw2D.rect_height*4);
                draw2D.path.moveTo(xs,ys);
                break;
            case MotionEvent.ACTION_MOVE: // движение
                break;
            case MotionEvent.ACTION_UP: // отпускание
                break;

        }
//        super.setFinishOnTouchOutside(false);
        return super.onTouchEvent(event);
    }


    private boolean isKilled(int x, int y, int cdx, int cdy, int[] mass)
    {
        boolean res=false;
        int dx=((All)draw2D.mapo[y+cdy][x+cdx]).last_x-((All)draw2D.mapo[y+cdy][x+cdx]).x;
        int dy=((All)draw2D.mapo[y+cdy][x+cdx]).last_y-((All)draw2D.mapo[y+cdy][x+cdx]).y;
        if(cdx==dx&&cdy==dy) {
            int cc=((All) draw2D.mapo[y+cdy][x+cdx]).Type;
            for(int i=0;i<mass.length;i++) {
                if ( cc == mass[i] )
                {
                    res = true;
                    break;
                }
            }
        }
        return res;
    }

    private void setValues() {
        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            int f1=0;
            int cc=0;

            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        draw2D.invalidate();
                        cc++;                               //Счетчик "скорости"
                        int x,y;
                        //int dxm,dym;
                        int c;

                        for (y = 0; y < draw2D.mapo.length; y++) {
                            for (x = 0; x < draw2D.mapo.length; x++) {
                                c = ((All) draw2D.mapo[y][x]).Type;         //переменная, где храниться бит карты
                                if (c == 1) {                   //&&f1=0){ Если найден игрок, то
                                    xm = x;
                                    ym = y;
                                }
                            }
                        }

                        for (y = 0; y < draw2D.mapo.length; y++) {
                            for (x = 0; x < draw2D.mapo.length; x++) {
                                ((All)draw2D.mapo[y][x]).setXY(x,y);
                                ((All)draw2D.mapo[y][x]).run();
                            }
                        }
//                        right=false;
//                        left=false;
//                        bottom=false;
//                        top=false;
                        int[] mass1={6,7};
                        int[] mass2={4,5};
                        for (y = 0; y < draw2D.mapo.length; y++) {
                            for (x = 0; x < draw2D.mapo.length; x++) {
                                int cc=((All)draw2D.mapo[y][x]).Type;
                                switch(cc)
                                {
                                    case 1:
                                        if(isKilled(x,y,0,-1,mass2) || isKilled(x,y,-1,0,mass1) || isKilled(x,y,0,-1,mass1) || isKilled(x,y,0,1,mass1) || isKilled(x,y,1,0,mass1))
                                        {
                                            ((All)draw2D.mapo[y][x]).kill(y,x,0);
                                        }
                                        break;
                                    case 6:
                                    case 7:
                                        if(isKilled(x,y,0,-1,mass2))
                                        {
                                            ((All)draw2D.mapo[y][x]).kill(y,x,0);
                                        }
                                        break;

                                }
                                ((All)draw2D.mapo[y][x]).touched=false;
                            }
                        }



                        boolean f=false;
                        int crystal=0;
                        for (y = 0; y < draw2D.mapo.length; y++) {                              //цикл расшифровки
                            for (x = 0; x < draw2D.mapo.length; x++) {                          //цикл расшифровки
                                c = ((All)draw2D.mapo[y][x]).Type;

                                if (c == 1 || c == 0x81) f = true;
                                if (c == 5 || c == 0x85) crystal++;

                            }
                        }
                        if(!f){
                            if(f1==0) f1=10;    //отсчёт 10 повторов цикла
                            f1--;
                        }
                        if((crystal==0)){
                            if(f1==0) f1=2;
                            f1--;
                        }
                        if(f1==1){
                            if(f)mapnum++;
                            start_map();
                            f1=0;
                        }

                    }
                });
            }
        }, 0, period);     //Задержка игры
    }


    public static int[][] map(int Num,int nummap,int mapsize) {
        mapsize+=2;
        int[][] map = new int[mapsize][mapsize];
        int tild=0;
        int numcart=-1;
        int temp=0;
        int fss=0;
        boolean find=false;
        String str=Maps01.str;

            if(Num==1)str=Maps01.str;
            if(Num==2)str=Maps02.str;
            //else return null;
            String[] linez=str.split("\n");
            String line=linez[fss];
            while (fss<linez.length) {

                //System.out.println(line);
                char[] a= line.toCharArray();


                for (int i = 0; i < a.length; i++) {
                    if (a[i] == '-') tild++;

                    try {
                        if (a[i + 1] == '-' && a[i + 2] == '$') break;
                    }catch (IndexOutOfBoundsException e){}

                    if(tild==1)numcart=0;

                    if (tild == 2 && a[i] != ' ' && a[i] != '-') {
                        //if (a[i] == '$') break;
                        numcart = (numcart * 10) + Integer.valueOf(String.valueOf(a[i]));
                    }
                }
                if(numcart!=-1){
                    System.out.println(numcart);
                    if(nummap==numcart){
                        for(int z=0;z<a.length;z++){
                            if(a[z]==',')break;
                            map[temp][z]=3;
                            if(a[z]=='"'){
                                if(find) find=false;
                                else find=true;
                            }
                            if(find){
                                if(a[z]=='#')map[temp][z]=8;
                                if(a[z]=='+')map[temp][z]=1;
                                if(a[z]=='@')map[temp][z]=4;
                                if(a[z]=='$')map[temp][z]=5;
                                if(a[z]=='%')map[temp][z]=7;
                                if(a[z]=='&')map[temp][z]=6;
                                if(a[z]==' ')map[temp][z]=0;
                                if(a[z]==':')map[temp][z]=2;
                            }
                            if(z==a.length-1||z==0||temp==0||temp==mapsize-1) map[temp][z]=3;
                        }
                        for(int i=0;i<mapsize;i++){
                            for(int j=0;j<mapsize;j++){
                                if(i==0||i==mapsize-1)map[i][j]=3;
                            }
                        }
                        temp++;
                    }
                }

                line=linez[fss];
                tild=0;
                fss++;

            }
        return map;
    }
    public byte getScreenOrientation(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            return 1;
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 0;
        else
            return -1;
    }
    public void Visibleb(boolean visible){
        if(visible) {
            findViewById(R.id.leftb).setVisibility(View.VISIBLE);
            findViewById(R.id.rightb).setVisibility(View.VISIBLE);
            findViewById(R.id.upb).setVisibility(View.VISIBLE);
            findViewById(R.id.downb).setVisibility(View.VISIBLE);
        }
        if(!visible) {
            findViewById(R.id.leftb).setVisibility(View.INVISIBLE);
            findViewById(R.id.rightb).setVisibility(View.INVISIBLE);
            findViewById(R.id.upb).setVisibility(View.INVISIBLE);
            findViewById(R.id.downb).setVisibility(View.INVISIBLE);
        }

        ViewGroup.LayoutParams params = findViewById(R.id.contboy).getLayoutParams();
        params.width = (int)(rect_weight_f_button/2);
        params.height = (int) (rect_weight_f_button/2);
        findViewById(R.id.contboy).setLayoutParams(params);

        params = findViewById(R.id.leftb).getLayoutParams();
        params.width = (int)(rect_weight_f_button/1.5);
        params.height = (int) (rect_weight_f_button/1.5);
        findViewById(R.id.leftb).setLayoutParams(params);

        params = findViewById(R.id.rightb).getLayoutParams();
        params.width = (int)(rect_weight_f_button/1.5);
        params.height = (int) (rect_weight_f_button/1.5);
        findViewById(R.id.rightb).setLayoutParams(params);

        params = findViewById(R.id.upb).getLayoutParams();
        params.width = (int)(rect_weight_f_button/1.5);
        params.height = (int) (rect_weight_f_button/1.5);
        findViewById(R.id.upb).setLayoutParams(params);

        params = findViewById(R.id.downb).getLayoutParams();
        params.width = (int)(rect_weight_f_button/1.5);
        params.height = (int) (rect_weight_f_button/1.5);
        findViewById(R.id.downb).setLayoutParams(params);

//        findViewById(R.id.rightb).setScaleX(rect_weight_f_button/100);
//        findViewById(R.id.rightb).setScaleY(rect_heihgt_f_button/100);
//
//        findViewById(R.id.upb).setScaleX(rect_weight_f_button/100);
//        findViewById(R.id.upb).setScaleY(rect_heihgt_f_button/100);
//
//        findViewById(R.id.downb).setScaleX(rect_weight_f_button/100);
//        findViewById(R.id.downb).setScaleY(rect_heihgt_f_button/100);
    }
}

