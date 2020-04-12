package com.example.bolderdash.Persons;

import static com.example.bolderdash.MainActivity.xm;
import static com.example.bolderdash.MainActivity.ym;

public class EnemyAll extends All {
    @Override
    public void run(){

        if(touched) return;
        touched=true;
        last_x=x; last_y=y;

        int dxm=xm-x;
        int dym=ym-y;
        if(dxm>0) dxm=1;
        if(dxm<0) dxm=-1;
        if(dym>0) dym=1;
        if(dym<0) dym=-1;

        if(GetType(x-1,y)!=0 && GetType(x,y-1)!=0 && GetType(x+1,y)!=0 && GetType(x,y+1)!=0){
            dx = 0;
            dy = 0;
        }

        if(dx == 0 && dy == 0) {
            if(GetType(x,y+1) == 0 )       dy = 1;
            else if(GetType(x,y-1) == 0 )  dy = -1;
            else if(GetType(x+1,y) == 0 )  dx = 1;
            else if(GetType(x-1,y) == 0 )  dx = -1;
        }


        if (dx != 0) {
            if(dym!=0) {
                if(GetType(x, y + dym) == 0 ) {
                    dx=0;
                    dy=dym;
                }
                else
                if(dx!=dxm && GetType(x, y - dym) == 0 ) {
                    dx=0;
                    dy=-dym;
                }
            }
            if(dx!=0) {
                if(GetType(x + dx, y ) != 0 ) {
                    if(GetType(x, y + 1) == 0 ){
                        dx=0;
                        dy=1;
                    }else
                    if(GetType(x, y - 1) == 0 ){
                        dx=0;
                        dy=-1;
                    }
                    else
                    if(GetType(x - dx, y ) == 0 ) {
                        dx = 0;
                    }
                }
            }
        }else
        if (dy != 0) {
            if(dxm!=0) {
                if(GetType(x + dxm, y ) == 0 ) {
                    dx=dxm;
                    dy=0;
                }
                else
                if(dy!=dym && GetType(x - dxm, y ) == 0 ) {
                    dx=-dxm;
                    dy=0;
                }
            }

            if(dy!=0) {
                if(GetType(x, y + dy ) != 0 ) {
                    if(GetType(x + 1, y ) == 0 ){
                        dx=1;
                        dy=0;
                    }else
                    if(GetType(x - 1, y ) == 0 ){
                        dx=-1;
                        dy=0;
                    }
                    else
                    if(GetType(x, y - dy) == 0 ) {
                        dy = 0;
                    }
                }
            }
        }

        draw2D.mapo[y + dy][x + dx] = draw2D.mapo[y][x];
        if (dx != 0 || dy != 0) draw2D.mapo[y][x] = new Void();
        ((All) draw2D.mapo[y + dy][x + dx]).setXY(x+dx,y+dy);
    }
}
