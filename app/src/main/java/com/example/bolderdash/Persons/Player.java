package com.example.bolderdash.Persons;

import com.example.bolderdash.MainActivity;

import static com.example.bolderdash.MainActivity.left;
import static com.example.bolderdash.MainActivity.right;
import static com.example.bolderdash.MainActivity.top;
import static com.example.bolderdash.MainActivity.bottom;
import static com.example.bolderdash.MainActivity.xm;
import static com.example.bolderdash.MainActivity.ym;
import static com.example.bolderdash.MainActivity.Scope;

public class Player extends All {
    public Player(){
        Type=1;
    }


    @Override
    public void run(){
        if(touched) return;
        touched=true;

        /** если нажата область экрана, то идти вниз*/
        if(bottom&&!top&&!right&&!left) movepl(x,y,0,1);

        /** если нажата область экрана, то идти вверх*/
        if(top&&!bottom&&!right&&!left) movepl(x,y,0,-1);

        /** если нажата область экрана, то идти вправа*/
        if( right && !bottom && !top && !left ) movepl(x,y,1,0);

        /** если нажата область экрана, то идти влево*/
        if( left && !bottom && !top && !right ) movepl(x,y,-1,0);
    }

    public void movepl(int x, int y,int xkoff,int ykoff){

        last_x=x; last_y=y;
        //если вправа, то xkoff=1
        //если влево, то xkoff=-1
        //если вниз, то ykoff=1
        //если вверх, то ykoff=-1
        if(((All)draw2D.mapo[y][x]).Type>=80) return;
        if (((All)draw2D.mapo[y+ykoff][x + xkoff]).Type == 2 || ((All)draw2D.mapo[y+ykoff][x + xkoff]).Type == 0) {
            draw2D.mapo[y+ykoff][x + xkoff] =draw2D.mapo[y][x];
            //((All)draw2D.mapo[y+ykoff][x+xkoff]).Type+=0x80;
            ((All)draw2D.mapo[y+ykoff][x+xkoff]).setXY(x+xkoff,y+ykoff);
            draw2D.mapo[y][x]= new Void();
            xm=x+xkoff;
            ym=y+ykoff;
        }
        if (((All)draw2D.mapo[y+ykoff][x + xkoff]).Type == 5) {
            draw2D.mapo[y+ykoff][x + xkoff] = draw2D.mapo[y][x];
            //((All)draw2D.mapo[y+ykoff][x+xkoff]).Type+=0x80;
            ((All)draw2D.mapo[y+ykoff][x+xkoff]).setXY(x+xkoff,y+ykoff);
            draw2D.mapo[y][x] = new Void();
            Scope+=100;
            xm=x+xkoff;
            ym=y+ykoff;
        }
        if (((All)draw2D.mapo[y+ykoff][x+xkoff]).Type == 4) {                  //Если камень стоит перед нами, то
            int tempx=x+xkoff;                            //временные переменные
            int tempy=y+ykoff;

            {
                int c123=0;
                while(((All)draw2D.mapo[tempy][tempx]).Type==4)
                {
                    tempx+=xkoff;
                    tempy+=ykoff;
                    c123++;
                }
                if(((All)draw2D.mapo[tempy][tempx]).Type==0) {
                    for (; c123 > 0; c123--) {
                        draw2D.mapo[tempy][tempx] = draw2D.mapo[tempy-ykoff][tempx-xkoff];
                        ((All)draw2D.mapo[tempy][tempx]).setXY(tempx,tempy);
                        //((All)draw2D.mapo[tempy][tempx]).Type+=0x80;
                        tempx-=xkoff;
                        tempy-=ykoff;
                    }
                    draw2D.mapo[tempy][tempx]=draw2D.mapo[y][x];
                    ((All)draw2D.mapo[tempy][tempx]).setXY(tempx,tempy);
                    draw2D.mapo[y][x]=new Void();
                }
            }
        }
    }
}
