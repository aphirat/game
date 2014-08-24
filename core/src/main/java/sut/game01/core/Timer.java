package sut.game01.core;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Administrator on 20/3/2557.
 */
public class Timer extends MotherClass {

    public Image[] number = new Image[10];
    public  ImageLayer[] test = new ImageLayer[2];
    public static GroupLayer[] scorePosition = new GroupLayer[2];
    private GroupLayer layer;
    public static int j=7,i=10;
    private ScreenStack ss;


    public Timer(GroupLayer layer) {
        this.layer=layer;

        Image time = assets().getImage("images/time.png");
        ImageLayer time1 = graphics().createImageLayer(time);
        time1.setTranslation(295f, 2f);
        layer.add(time1);

        number[0]  = assets().getImage("images/t0.png");
        number[1]  = assets().getImage("images/t1.png");
        number[2]  = assets().getImage("images/t2.png");
        number[3]  = assets().getImage("images/t3.png");
        number[4]  = assets().getImage("images/t4.png");
        number[5]  = assets().getImage("images/t5.png");
        number[6]  = assets().getImage("images/t6.png");
        number[7]  = assets().getImage("images/t7.png");
        number[8]  = assets().getImage("images/t8.png");
        number[9]  = assets().getImage("images/t9.png");


        for(int i=0;i < scorePosition.length ;i++)
        {
            scorePosition[i] = graphics().createGroupLayer();
            layer.add(scorePosition[i]);
        }


        test[0] = graphics().createImageLayer(number[6]);
        scorePosition[0].add(test[0]);
        test[1] = graphics().createImageLayer(number[0]);
        scorePosition[1].add(test[1]);




        scorePosition[0].setTranslation(300, 30);
        scorePosition[1].setTranslation(335, 30);

    }


    public void update(int delta){

        if(i==10&&j==7){
            i--;
            scorePosition[1].removeAll();
            test[1]=graphics().createImageLayer(number[i]);
            scorePosition[1].add(test[1]);
            scorePosition[1].setTranslation(335,30);

            j=j-2;
            scorePosition[0].removeAll();
            test[0]=graphics().createImageLayer(number[j]);
            scorePosition[0].add(test[0]);
            scorePosition[0].setTranslation(300, 30);
        }
        if(i>0){
            i--;
            scorePosition[1].removeAll();
            test[1]=graphics().createImageLayer(number[i]);
            scorePosition[1].add(test[1]);
            scorePosition[1].setTranslation(335,30);
        }
        if(i==0){

            scorePosition[1].removeAll();
            test[1]=graphics().createImageLayer(number[i]);
            scorePosition[1].add(test[1]);
            scorePosition[1].setTranslation(335,30);


            j--;
            scorePosition[0].removeAll();
            test[0]=graphics().createImageLayer(number[j]);
            scorePosition[0].add(test[0]);
            scorePosition[0].setTranslation(300,30);

            i=10;
        }


    }




}
