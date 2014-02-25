package sut.game01.core.sprite;


import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.CallbackList;

public class Zealot {



    private Sprite sprite;
    private int spriteIndex=0;
    private boolean hasLoaded=false;
    private int hp=100, time=0;


    public  enum State{
        IDIE,DIE
    };

    private State state = State.IDIE;
    private int e =0;
    private int offset=0;

    public Zealot(final float x, final float y){
        sprite = SpriteLoader.getSprite("images/zealot.json");
        sprite.addCallback(new CallbackList<Sprite>(){

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x,y);
                hasLoaded = true;

            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }
        });

        sprite.layer().addListener(new Pointer.Adapter(){
            @Override
            public  void onPointerEnd(Pointer.Event event){

                state=State.DIE;
                spriteIndex=-1;
                e=0;
            }

        });

    }
    public Layer layer(){
        return sprite.layer();
    }


    public void update(int delta){
        if(!hasLoaded)return;
        e+=delta;
        time += delta;
        if(time >= 1000){
            hp = 50;
        }
        if(time >= 2000){
            hp = 0;
            if(hp ==0){
                state = State.DIE;
            }
        }

        if(e>150){
            switch(state){

                case IDIE: offset=0;
                    if(hp == 50)
                        state = State.DIE;
                    break;
                case DIE: offset=4;
                    if(spriteIndex == 6){
                        sprite.setSprite(7);
                    }
                    break;

            }

            spriteIndex = offset + ((spriteIndex+1)%4);
            sprite.setSprite(spriteIndex);
            e=0;

        }

    }

}

