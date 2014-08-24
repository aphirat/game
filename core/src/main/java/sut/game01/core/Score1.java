package sut.game01.core;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Administrator on 20/3/2557.
 */
public class Score1 {

    public Image[] number = new Image[10];
    public ImageLayer[] test = new ImageLayer[2];
    public static GroupLayer[] scorePosition = new GroupLayer[2];
    private GroupLayer layer;
    public int j,i=0;
    public static int scoreL=0;


    public Score1(GroupLayer layer) {
        this.layer=layer;

        number[0]  = assets().getImage("images/zero.png");
        number[1]  = assets().getImage("images/one.png");
        number[2]  = assets().getImage("images/two.png");
        number[3]  = assets().getImage("images/three.png");
        number[4]  = assets().getImage("images/four.png");
        number[5]  = assets().getImage("images/five.png");
        number[6]  = assets().getImage("images/six.png");
        number[7]  = assets().getImage("images/seven.png");
        number[8]  = assets().getImage("images/eigh.png");
        number[9]  = assets().getImage("images/nine.png");


        for(int i=0;i < scorePosition.length ;i++)
        {
            scorePosition[i] = graphics().createGroupLayer();
            layer.add(scorePosition[i]);
        }
        for(int j =0;j<test.length;j++)
        {

            test[j] = graphics().createImageLayer(number[0]);
            scorePosition[j].add(test[j]);

        }

        scorePosition[0].setTranslation(150, 10);
        scorePosition[1].setTranslation(210, 10);


    }


    public void show() {

        scoreL++;
        i++;

        if (i<=9){
            scorePosition[1].removeAll();
            test[1]=graphics().createImageLayer(number[i]);
            scorePosition[1].add(test[1]);
            scorePosition[1].setTranslation(210, 10);
        }
        if(i==10){
            i=0;j++;
            scorePosition[1].removeAll();
            test[1]=graphics().createImageLayer(number[i]);
            scorePosition[1].add(test[1]);
            scorePosition[1].setTranslation(210, 10);

            scorePosition[0].removeAll();
            test[0]=graphics().createImageLayer(number[j]);
            scorePosition[0].add(test[0]);
            scorePosition[0].setTranslation(150, 10);
        }

    }









}
