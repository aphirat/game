package sut.game01.core.sprite;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.CallbackList;
import playn.core.util.Clock;
import sut.game01.core.MotherClass;
import sut.game01.core.TestScreen;

/**
 * Created by Administrator on 4/3/2557.
 */
public class Ball extends MotherClass {


    private Sprite sprite;
    private int spriteIndex=0;
    private boolean hasLoaded=false;

    private Body body;




    public  enum State{
        IDLE
    };
    private State state = State.IDLE;
    private int e =0;
    private int offset=0;

    public Ball(final World world,final float x_px, final float y_px){
        sprite = SpriteLoader.getSprite("images/ball.json");
        sprite.addCallback(new CallbackList<Sprite>(){

            @Override
            public void onSuccess(Sprite result) {
                sprite.setSprite(spriteIndex);
                sprite.layer().setOrigin(sprite.width() / 2f, sprite.height() / 2f);
                sprite.layer().setTranslation(x_px,y_px);
                body = initPhysicsBody(world, TestScreen.M_PER_PIXEL * x_px, TestScreen.M_PER_PIXEL * y_px);
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

                state=State.IDLE;
                spriteIndex=-1;
                e=0;
            }

        });

    }

    public void contract(Contact cotract){

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
        shape.setAsBox(30 * TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.1f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.5f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        body.setLinearDamping(0f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }


    public void update(int delta){
        if(!hasLoaded)return;
          if(e>150){
            switch(state){

                case IDLE: offset=0;
                    break;

            }

            spriteIndex = offset + ((spriteIndex+1)%4);
            sprite.setSprite(spriteIndex);
            e=0;

        }

    }


    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation((body.getPosition().x/TestScreen.M_PER_PIXEL),
                body.getPosition().y/TestScreen.M_PER_PIXEL);
        sprite.layer().setRotation(body.getAngle());
    }

    @Override
    public void Contact(MotherClass other) {

        if(other.getClass()==Kick.class){

            getBody().applyLinearImpulse(new Vec2(10f, -100f), body.getPosition());
        }
    }

    public Body getBody() {
        return body;
    }

}

