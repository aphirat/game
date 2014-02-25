package sut.game01.core;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.CanvasImage;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import sut.game01.Debug.DebugDrawBox2D;
import sut.game01.core.sprite.Zealot;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;




public class TestScreen extends Screen {

    public static float M_PER_PIXEL = 1/26.666667f;
    private static int width = 24;
    private static int height = 18;

    private World world;

    private Body body2;

    private DebugDrawBox2D debugDraw;
    private boolean showDebugDraw = true;

    private final ScreenStack ss;
    Zealot z = new Zealot(200f,300f);

    public TestScreen(ScreenStack ss) {
        this.ss = ss;
    }


    @Override
    public void wasAdded() {
        super.wasAdded();

        Vec2 gravity = new Vec2(0.0f,10.0f);
        world = new World(gravity,true);
        world.setWarmStarting(true);
        world.setAutoClearForces(true);

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }

        });
        if(showDebugDraw){

            CanvasImage image = graphics().createImage(
                    (int)(width/TestScreen.M_PER_PIXEL),
                    (int)(height/TestScreen.M_PER_PIXEL)
            );
            layer.add(graphics().createImageLayer(image));
            debugDraw = new DebugDrawBox2D();
            debugDraw.setCanvas(image);
            debugDraw.setFlipY(false);
            debugDraw.setStrokeAlpha(150);
            debugDraw.setFillAlpha(75);
            debugDraw.setStrokeWidth(2.0f);
            debugDraw.setFlags(DebugDraw.e_shapeBit|
                    DebugDraw.e_jointBit|
                    DebugDraw.e_aabbBit);
            debugDraw.setCamera(0,0,1f/TestScreen.M_PER_PIXEL);
            world.setDebugDraw(debugDraw);

        }


        Image bgImage = assets().getImage("images/.png");
        Image bg1Image = assets().getImage("images/1234.png");
        bgImage.addCallback(new Callback<Image>() {
            @Override
            public void onSuccess(Image result) {

            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });
        ImageLayer bgLayer = graphics().createImageLayer(bgImage);
        ImageLayer bg1Layer = graphics().createImageLayer(bg1Image);
        layer.add(bgLayer);
        layer.add(bg1Layer);

        bg1Layer.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                body2.applyLinearImpulse(new Vec2(100f,0f), body2.getPosition());
            }
        });
        layer.add(z.layer());

        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(2f,height-2),
                new Vec2(width-2f,height-2));
        ground.createFixture(groundShape,0.0f);
        createBox();
        createBox1();


    }
     private void createBox(){
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0,0);

        Body body = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 1f;
        body.createFixture(fd);
        body.setLinearDamping(0.5f);
        body.setTransform(new Vec2(10f,height-2),0);
     }

    private void createBox1(){
        BodyDef bf = new BodyDef();
        bf.type = BodyType.DYNAMIC;
        bf.position = new Vec2(0,0);

        body2 = world.createBody(bf);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1f);
        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 0.1f;
        fd.friction = 0.1f;
        fd.restitution = 1f;
        body2.createFixture(fd);
        body2.setLinearDamping(0.5f);
        body2.setTransform(new Vec2(5f,height-2),0);


    }


    @Override
    public void update(int delta) {
        super.update(delta);
        z.update(delta);
        world.step(0.033f,10,10);

    }

    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }
    }

}
