package sut.game01.core;

import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class HomeScreen extends UIScreen {

    private final ScreenStack ss;

    public HomeScreen(ScreenStack ss){
        this.ss = ss;
    }


    @Override
    public void wasShown() {
        super.wasShown();
        Image bgImage = assets().getImage("images/Head.png");
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        layer.add(bgLayer);

        Image bgImage1 = assets().getImage("images/setting.png");
        ImageLayer bgLayer1 = graphics().createImageLayer(bgImage1);
        bgLayer1.setTranslation(570f,10f);
        layer.add(bgLayer1);

        Image bgImage2 = assets().getImage("images/pay.png");
        ImageLayer bgLayer2 = graphics().createImageLayer(bgImage2);
        bgLayer2.setTranslation(450f,400f);
        layer.add(bgLayer2);

        bgLayer2.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                ss.push(new ChoiceScreen(ss));
            }
        });





    }
}


