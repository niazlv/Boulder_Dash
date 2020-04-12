package com.example.bolderdash.Persons;

public class GravityAll extends All {

    @Override
    public void run(){
//        if(killed){
//            kill(y, x, 1);
//            killed=false;
//        }
//        if ((((All) draw2D.mapo[y + 1][x]).Type == 1 || ((All) draw2D.mapo[y + 1][x]).Type == 6 || ((All) draw2D.mapo[y + 1][x]).Type == 7) && ((y-last_y)>0)) {
//            //killed=true;
//            kill(y,x,1);
//        }
//        else
        {
            if(touched) return;
            touched=true;
            last_x=x; last_y=y;
            if (((All) draw2D.mapo[y + 1][x]).Type == 0) {      //eсли бит карты снизу равен пустоте, то
                //((All) draw2D.mapo[y][x]).fall = true;                        //счётчик падения
                draw2D.mapo[y + 1][x] = draw2D.mapo[y][x];    //перекидываем себя вперед
                //((All) draw2D.mapo[y + 1][x]).Type += 0x80;
                draw2D.mapo[y][x] = new Void();        //Заставляем за собою
                ((All) draw2D.mapo[y + 1][x]).y++;
            }else {
                if (((All) draw2D.mapo[y][x + 1]).Type == 0 && ((All) draw2D.mapo[y + 1][x + 1]).Type == 0) { //если справа свободно и справа снизу свободно, то
                    draw2D.mapo[y][x + 1] = draw2D.mapo[y][x];                        //перемещаемся вправо
                    //((All) draw2D.mapo[y][x + 1]).Type += 0x80;
                    draw2D.mapo[y][x] = new Void();                            //закрашиваем за собою и шифруем
                    ((All) draw2D.mapo[y][x + 1]).x++;
                } else {
                    if (((All) draw2D.mapo[y][x - 1]).Type == 0 && ((All) draw2D.mapo[y + 1][x - 1]).Type == 0) {   //То же самое, только слева
                        draw2D.mapo[y][x - 1] = draw2D.mapo[y][x];
                        //((All) draw2D.mapo[y][x - 1]).Type += 0x80;
                        draw2D.mapo[y][x] = new Void();
                        ((All) draw2D.mapo[y][x - 1]).x--;
                    }
                }
            }
        }
    }
}
