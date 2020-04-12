package com.example.bolderdash.Persons;





public class Diamond extends GravityAll {
    public  Diamond(){
        Type=5;
    }
    public  Diamond(int xc, int yc){
        Type=5;
        x=xc;
        y=yc;
    }

    /*
    @Override
    public void run(){
        if(touched) return;
        touched=true;
        last_x=x; last_y=y;

//        if ((((All) draw2D.mapo[y + 1][x]).Type == 1 || ((All) draw2D.mapo[y + 1][x]).Type == 6 || ((All) draw2D.mapo[y + 1][x]).Type == 7) && ((All) draw2D.mapo[y][x]).fall) {
//            kill(y, x, 1);
//        } else
            {
            if (((All) draw2D.mapo[y + 1][x]).Type == 0) {      //eсли бит карты снизу равен пустоте, то
                ((All) draw2D.mapo[y][x]).fall = true;                        //счётчик падения
                draw2D.mapo[y + 1][x] = draw2D.mapo[y][x];    //перекидываем себя вперед и шифруем прибавляя 0x80
                //((All) draw2D.mapo[y + 1][x]).Type += 0x80;
                draw2D.mapo[y][x] = new Void();        //Заставляем за собою и шифруем прибавляя 0x80
                ((All) draw2D.mapo[y + 1][x]).y++;
            } else {

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
     */
}

