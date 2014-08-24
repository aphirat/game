package sut.game01.core.sprite;

/**
 * Created by Administrator on 23/3/2557.
 */
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.util.CallbackList;
import playn.core.util.Clock;
import sut.game01.core.MotherClass;
import sut.game01.core.TestScreen;

import java.util.List;


public class Clark extends MotherClass {
    private Sprite sprite;
    private int spriteIndex=0;
    private boolean hasLoaded=false;
    private Body body;
    Ball b;
    private World world;
    List<MotherClass> ca;
    private int timeOut=0;
    private boolean Whattime=false;



    public enum State{
        RIGHT,LEFT,KICKR,KICKL
    };


    public static State state = State.LEFT;

    private int e =0;
    private int offset=0;
    public Clark(final World world,final float x_px, final float y_px,final Ball b,final List<MotherClass> ca){
        this.b = b;
        this.ca = ca;
        sprite = SpriteLoader.getSprite("images/clark.json");
        sprite.addCallback(new CallbackList<Sprite>(){

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px,y_px);
                body = initPhysicsBody(world,TestScreen.M_PER_PIXEL*x_px,TestScreen.M_PER_PIXEL*y_px);
                hasLoaded = true;

            }

            @Override
            public void onFailure(Throwable cause) {
                PlayN.log().error("Error loading image!", cause);
            }
        });
    }
    public Layer layer(){
        return sprite.layer();
    }

    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        body.setFixedRotation(true);
        shape.setAsBox(56 * TestScreen.M_PER_PIXEL / 2 , sprite.layer().height()*TestScreen.M_PER_PIXEL /2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }
    public void checkRange(){

        if(b.getBody()==null){
            return;
        }

        if(body.getLocalPoint(b.getBody().getPosition()).x>= 3f){
           body.applyLinearImpulse(new Vec2(10f,0f),body.getPosition());
        }
        else if (body.getLocalPoint(b.getBody().getPosition()).x<= -3f){
                 body.applyLinearImpulse(new Vec2(-10f, 0f), body.getPosition());
        }

        else if (body.getLocalPoint(b.getBody().getPosition()).x>0&&
                 body.getLocalPoint(b.getBody().getPosition()).x<=3f ||
                 body.getLocalPoint(b.getBody().getPosition()).x<0&&
                 body.getLocalPoint(b.getBody().getPosition()).x>=-3f){
                  state = State.KICKL;
             if (body.getLocalPoint(b.getBody().getPosition()).y<=-1){
                body.applyLinearImpulse(new Vec2(10f,0f),body.getPosition());
            }
        }
        else if (body.getLocalPoint(b.getBody().getPosition()).x<11 &&
                 body.getLocalPoint(b.getBody().getPosition()).x>=-3f&&
                 body.getLocalPoint(b.getBody().getPosition()).y <-10f){
             if (body.getLinearVelocity().y ==0 ){
                 body.applyLinearImpulse(new Vec2(5f, -10f), body.getPosition());
        }
        }
        //else {body.applyLinearImpulse(new Vec2(-100f,0f),body.getPosition());
          //      body.applyLinearImpulse(new Vec2(150f,0f),body.getPosition());
        //}
    }
    public void update(int delta){

        if(!hasLoaded) return;
        checkRange();
        e += delta;
        timeOut+=delta;

        if(e > 150){
            switch (state){

                case RIGHT: offset=0;
                    break;
                case LEFT: offset=4;
                    break;
                case KICKR: offset=8;
                    break;
                case KICKL: offset=12;
                    if(spriteIndex==15){
                        state=State.LEFT;
                        ca.add(new Kick(body.getWorld(), body.getPosition().x -1f, body.getPosition().y - 1f, true));
                    }
                    break;
            }



            spriteIndex = offset + ((spriteIndex + 1)%4);
            sprite.setSprite(spriteIndex);

            e=0;


            if(timeOut>=20){
                Whattime=true;

            }
        }

    }

    public void paint(Clock clock) {

        if(!hasLoaded) return;
        sprite.layer().setTranslation(body.getPosition().x / TestScreen.M_PER_PIXEL,body.getPosition().y / TestScreen.M_PER_PIXEL);
    }

    public Body getBody() {
        return this.body;
    }






}
