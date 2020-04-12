package com.example.bolderdash.Persons;

import com.example.bolderdash.Draw2D;
import com.example.bolderdash.MainActivity;

public class All {
    public int Type;
    Draw2D draw2D= MainActivity.draw2D;
    public int dx=0,dy=0;
    public int crs;
    public int x,y;
    public boolean fall=false;
    public boolean killed=false;

    public int last_x=-1;
    public int last_y=-1;

    public boolean touched=false;

    public void setXY(int xc,int yc){
        x=xc;
        y=yc;
    }
    public final void kill(int y,int x,int koff) {
        int c=0;
        for (int xc = x - 1; xc <= x + 1; xc++) {
            for (int yc = y - 1; yc <= y + 1; yc++) {
                c=((All)draw2D.mapo[yc+koff][xc]).Type;
                switch(c)
                {
                    case 3:
                        break;
//                    case 6:
//                    case 7:
//                        draw2D.mapo[yc+koff][xc] = new Diamond(xc,yc+koff);
//                        kill(yc,xc,koff);
//                        break;
                    default:
                        draw2D.mapo[yc+koff][xc] = new Diamond(xc,yc+koff);
                        break;
                }
            }
        }
    }
    public void run(){}
    public boolean checkmap(int c){
        int cc=0;
        for(int y=0;y<draw2D.mapo.length;y++){
            for(int x=0;x<draw2D.mapo.length;x++){
                if(((All)draw2D.mapo[y][x]).Type==4) cc++;
            }
        }
        if(cc>0) return true;
        else return false;
    }

    public void moveXY(int xc, int yc)
    {
        draw2D.mapo[yc][xc]=this;
        draw2D.mapo[y][x]=new Void();
        ((All)draw2D.mapo[yc][xc]).setXY(xc,yc);
    }

    public int GetType(int x, int y)
    {
        return ((All) draw2D.mapo[y][x]).Type;
    }

    public void SetType(int x, int y, int t)
    {
        ((All) draw2D.mapo[y][x]).Type=t;
    }

    public boolean isKilled(int x, int y, int cdx, int cdy, int[] mass)
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
}
