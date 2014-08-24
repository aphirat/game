package sut.game01.core.sprite;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import sut.game01.core.MotherClass;
import sut.game01.core.TestScreen;

/**
 * Created by Administrator on 24/3/2557.
 */
public class Kick extends MotherClass {

    private boolean hasload=false,WhatTime=false;
    private int offset=0,timeOut=0,e=0;

    private Body body;
    public Body getBody() {
        return body;
    }

    public Kick(final World world,final float x,final float y,final boolean m){

        body = initPhysicsBody(world, x,y);
        hasload = true;

        if(m==true){
            body.applyLinearImpulse(new Vec2(100f,50f),body.getPosition());
        }


    }


    private Body initPhysicsBody (World world,float x,float y){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.position = new Vec2(0,0);
        Body body = world.createBody(bodyDef);
        body.setBullet(true);
        body.setFixedRotation(true);
        ;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(20 * TestScreen.M_PER_PIXEL /2,20* TestScreen.M_PER_PIXEL / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.09f;
        fixtureDef.friction = 0f;
        //fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        body.setLinearDamping(0.2f);
        body.setTransform(new Vec2(x,y),0f);
        return body;
    }
    public Boolean getTimeOut(){
        return WhatTime;
    }
    public void Destroy(){
        body.getWorld().destroyBody(body);
    }
    public void update(int delta){
        if (!hasload) return;
        e+=0;
        timeOut+=delta ;
        if(timeOut>=1){
            WhatTime=true;
        }
    }
    public void paint() {
        if(!hasload) return;
    }


}

