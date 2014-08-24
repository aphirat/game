package sut.game01.core.sprite;


import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import playn.core.*;
import playn.core.util.CallbackList;
import playn.core.util.Clock;
import sut.game01.core.MotherClass;
import sut.game01.core.TestScreen;

import java.util.List;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

public class Macro extends MotherClass {



    private Sprite sprite;
    private int spriteIndex=0;
    private boolean hasLoaded=false;
    private Body body;
    private GroupLayer layer;
    Ball b;
    List<MotherClass> m;
    private int timeOut=0;
    private boolean Whattime=false;




    public  enum State{
        RIGHT,LEFT,KICKR,KICKL,GOAL
    };

    public static State state = State.RIGHT;
    private int e =0;
    private int offset=0;



    public Macro(final World world,final float x_px, final float y_px,final GroupLayer layer,final List<MotherClass>m){

        this.layer=layer;
        this.m=m;

         switch (TestScreen.selected){
            case 1:sprite = SpriteLoader.getSprite("images/macro.json");
                break;
            case 2:sprite = SpriteLoader.getSprite("images/eri.json");
                break;
            case 3:sprite = SpriteLoader.getSprite("images/tarma.json");
                break;
            case 4:sprite = SpriteLoader.getSprite("images/fio.json");
                break;
        }

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

        Image left = assets().getImage("images/l.png");
        ImageLayer left1 = graphics().createImageLayer(left);
        left1.setTranslation(30f,430f);

        layer.add(left1);
        left1.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                getBody().applyForce(new Vec2(-200f,0f),getBody().getPosition());
            }
        });

        Image right = assets().getImage("images/r.png");
        ImageLayer right1 = graphics().createImageLayer(right);
        right1.setTranslation(120f,430f);
        layer.add(right1);
        right1.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                getBody().applyForce(new Vec2(200f, 0f), getBody().getPosition());
            }
        });

        Image kick = assets().getImage("images/kick.png");
        ImageLayer kick1 = graphics().createImageLayer(kick);
        kick1.setTranslation(420f,430f);
        layer.add(kick1);
        kick1.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                state=State.KICKR;
            }
        });


        Image jump = assets().getImage("images/jump.png");
        ImageLayer jump1 = graphics().createImageLayer(jump);
        jump1.setTranslation(510f,430f);
        layer.add(jump1);
        jump1.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);
                jump();
            }
        });



    }
    public Layer layer(){
        return sprite.layer();
    }
    public void jump(){

        if(body.getLinearVelocity().y ==0 ){
            body.applyLinearImpulse(new Vec2(0f, -25f), body.getPosition());
        }
    }


    public void Destroy(){
        sprite.layer().parent().remove(sprite.layer());
        body.getWorld().destroyBody(getBody());

    }

    private Body initPhysicsBody(World world, float x, float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(56 * TestScreen.M_PER_PIXEL/2,sprite.layer().height()*TestScreen.M_PER_PIXEL/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.1f;
        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x, y), 0f);

        return body;
    }


    public void update(int delta){
        if(!hasLoaded)return;
        e+=delta;
        timeOut+=delta;



        if(e>150){
            switch(state){

                case RIGHT: offset=0;
                    break;
                case LEFT: offset=4;
                    break;
                case KICKR: offset=8;

                    if(spriteIndex==11){
                      state=State.RIGHT;

                        m.add(new Kick(body.getWorld(),body.getPosition().x+1f,body.getPosition().y+1f,true));
                    }

                    break;
                case KICKL: offset=12;
                    break;



            }



            spriteIndex = offset + ((spriteIndex+1)%4);
            sprite.setSprite(spriteIndex);
            e=0;

            if(timeOut>=20){
                Whattime=true;

            }

        }


    }
    public void paint(Clock clock) {
        if(!hasLoaded) return;
        sprite.layer().setTranslation((body.getPosition().x/TestScreen.M_PER_PIXEL)-10,
                body.getPosition().y/TestScreen.M_PER_PIXEL);
    }

    public Body getBody() {
        return body;
    }



}

