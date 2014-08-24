package sut.game01.core;

import playn.core.*;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by Administrator on 4/3/2557.
 */
public class ChoiceScreen extends Screen {
    private final ScreenStack ss;

    private ImageLayer[] backImg = new ImageLayer[4] ;
    private ImageLayer[] colorImg = new ImageLayer[4] ;
    private ImageLayer p1Img;
    private GroupLayer[] showImg = new GroupLayer[4];
    private GroupLayer[] show1Img = new GroupLayer[4];
    private  int select = 0;
    private ImageLayer right1,left1,start1;


    public ChoiceScreen(ScreenStack ss){
        this.ss = ss;
    }

    @Override
    public void wasShown() {
        super.wasShown();
        Image bgImage = assets().getImage("images/choice.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        layer.add(bgLayer);

        Image back = assets().getImage("images/back.png");
        ImageLayer back1 = graphics().createImageLayer(back);
        back1.setTranslation(0f,15f);
        layer.add(back1);

        back1.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                ss.remove(ChoiceScreen.this);
            }
        });

        Image left = assets().getImage("images/left.png");
        ImageLayer left1 = graphics().createImageLayer(left);
        left1.setTranslation(10f,425f);
        layer.add(left1);

        left1.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                showImg[select].removeAll();
                show1Img[select].removeAll();
                showImg[select].add(backImg[select]);
                if (--select < 0) select = 3;
                showImg[select].removeAll();
                showImg[select].add(colorImg[select]);
                show1Img[select].add(p1Img);
            }
        });

        Image right = assets().getImage("images/right.png");
        ImageLayer right1 = graphics().createImageLayer(right);
        right1.setTranslation(600f, 425f);
        layer.add(right1);

        right1.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                showImg[select].removeAll();
                show1Img[select].removeAll();
                showImg[select].add(backImg[select]);
                if (++select > 3) select = 0;
                showImg[select].removeAll();
                showImg[select].add(colorImg[select]);
                show1Img[select].add(p1Img);
            }
        });


        p1Img = graphics().createImageLayer(assets().getImage("images/p1.png"));

        backImg[0] = graphics().createImageLayer(assets().getImage("images/1.png"));
        backImg[1] = graphics().createImageLayer(assets().getImage("images/2.png"));
        backImg[2] = graphics().createImageLayer(assets().getImage("images/3.1.png"));
        backImg[3] = graphics().createImageLayer(assets().getImage("images/4.png"));

        colorImg[0] = graphics().createImageLayer(assets().getImage("images/1.1.png"));
        colorImg[1] = graphics().createImageLayer(assets().getImage("images/2.1.png"));
        colorImg[2] = graphics().createImageLayer(assets().getImage("images/3.png"));
        colorImg[3] = graphics().createImageLayer(assets().getImage("images/4.1.png"));

        colorImg[0].addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);

                TestScreen.selected=1;
                ss.push(new TestScreen(ss));

            }
        });

        colorImg[1].addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);

                TestScreen.selected = 2;
                ss.push(new TestScreen(ss));
            }
        });

        colorImg[2].addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                TestScreen.selected = 3;
                ss.push(new TestScreen(ss));
            }
        });

        colorImg[3].addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                TestScreen.selected = 4;
                ss.push(new TestScreen(ss));
            }
        });
         /*spriteIA.layer().addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                System.out.println("ItemA");
                TestScreen.selected = 1;

            }
        });*/



        for(int j = 0; j< show1Img.length;j++)
            show1Img[j] = graphics().createGroupLayer();

        show1Img[0].add(p1Img);
        show1Img[0].setTranslation(70f,95f);
        show1Img[1].setTranslation(210f,95f);
        show1Img[2].setTranslation(350f,95f);
        show1Img[3].setTranslation(498f,95f);

        for(GroupLayer x : show1Img)
            layer.add(x);


        for(int i = 0; i< showImg.length;i++)
            showImg[i] = graphics().createGroupLayer();

        showImg[0].add(colorImg[0]);
        showImg[0].setTranslation(37f,140f);
        showImg[1].add(backImg[1]);
        showImg[1].setTranslation(180f,140f);
        showImg[2].add(backImg[2]);
        showImg[2].setTranslation(320f,140f);
        showImg[3].add(backImg[3]);
        showImg[3].setTranslation(467f,142f);

        for(GroupLayer x : showImg)
            layer.add(x);


    }

    @Override
    public void update(int delta) {
        super.update(delta);


    }
}



