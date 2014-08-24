package sut.game01.core;


import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.callbacks.DebugDraw;
import org.jbox2d.collision.Manifold;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;
import playn.core.*;
import playn.core.util.Clock;
import sut.game01.Debug.DebugDrawBox2D;
import sut.game01.core.sprite.Ball;
import sut.game01.core.sprite.Clark;
import sut.game01.core.sprite.Kick;
import sut.game01.core.sprite.Macro;
import tripleplay.game.ScreenStack;
import tripleplay.game.UIScreen;

import java.util.ArrayList;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;


public class TestScreen extends UIScreen {

    public static float M_PER_PIXEL = 1/26.666667f;
    private static int width = 24;
    private static int height = 18;
    public static int selected=0;
    public World world;
    private Body body;
    private boolean hasLoaded=false;
    private DebugDrawBox2D debugDraw;
    private boolean showDebugDraw = true;
    private ImageLayer left1,right1,kick1,jump1;
    private int o = 0;
    boolean pausee=false;
    ArrayList<MotherClass>bul=new ArrayList<MotherClass>();
    ArrayList<MotherClass>tmp=new ArrayList<MotherClass>();
    ArrayList<MotherClass>bin=new ArrayList<MotherClass>();
    private boolean rr=true;
    private GroupLayer pausess = graphics().createGroupLayer();
    private GroupLayer gameovers = graphics().createGroupLayer();
    private GroupLayer timeS = graphics().createGroupLayer();
    private GroupLayer timeM = graphics().createGroupLayer();
    private final ScreenStack ss;
    private int timeout=0;
    private int sec=30;
    private int min=0;
    private boolean timet=false;
    private boolean gameover=false;
    private boolean winner=false;
    private boolean drawer=false;
    private boolean loser=false;


    private Image number[] = {
    assets().getImage("images/t0.png"),
    assets().getImage("images/t1.png"),
    assets().getImage("images/t2.png"),
    assets().getImage("images/t3.png"),
    assets().getImage("images/t4.png"),
    assets().getImage("images/t5.png"),
    assets().getImage("images/t6.png"),
    assets().getImage("images/t7.png"),
    assets().getImage("images/t8.png"),
    assets().getImage("images/t9.png")};



    Ball b;
    Macro m;
    LineWorld l;
    LinegoalR lr;
    LinegoalL ll;
    Score s;
    Score1 s1;
    Clark c;
    Kick k ;




    public TestScreen(ScreenStack ss)
    {
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
                MotherClass a = null;
                MotherClass b = null;
                for (MotherClass x : bul){
                    if (x.getBody() == contact.getFixtureA().getBody()){
                        a =  x;
                    }
                    if (x.getBody() == contact.getFixtureB().getBody()){
                        b = x;
                    }
                    if (a != null && b != null){

                        a.Contact(b);
                        b.Contact(a);
                        break;
                    }

                }


            }
            @Override
            public void endContact(Contact contact) {
            }
            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }
        });


        if(showDebugDraw){

            CanvasImage image = graphics().createImage(
                    (int)(width/ TestScreen.M_PER_PIXEL),
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


        Image background = assets().getImage("images/background.png");
        ImageLayer background1 = graphics().createImageLayer(background);
        background1.setTranslation(0f,0f);
        layer.add(background1);

        Image time = assets().getImage("images/time.png");
        ImageLayer time1 = graphics().createImageLayer(time);
        time1.setTranslation(300f,5f);
        layer.add(time1);

        Image simicoron = assets().getImage("images/simicoron.png");
        ImageLayer simicoron1 = graphics().createImageLayer(simicoron);
        simicoron1.setTranslation(305f,35f);
        layer.add(simicoron1);



        Image pause = assets().getImage("images/pause-button.png");
        ImageLayer pause1 = graphics().createImageLayer(pause);
        pause1.setTranslation(570f,10f);
        layer.add(pause1);

        pause1.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {
                super.onPointerEnd(event);

                pausee=true;

                Image pausebg = assets().getImage("images/pause.png");
                Image back1 = assets().getImage("images/p.png");
                ImageLayer pausebgl = graphics().createImageLayer(pausebg);
                ImageLayer back1l = graphics().createImageLayer(back1);
                back1l.setTranslation(275f,325f);
                pausebgl.setTranslation(0f,0f);
                pausess.add(back1l);
                pausess.add(pausebgl);
                layer.add(pausess);

                back1l.addListener(new Pointer.Adapter() {
                    @Override
                    public void onPointerEnd(Pointer.Event event) {
                        if (rr == true) {
                            pausess.removeAll();
                            pausee = false;
                        }
                    }
                });

            }
        });



        Image flor = assets().getImage("images/flor.png");
        ImageLayer flor1 = graphics().createImageLayer(flor);
        flor1.setTranslation(0f,365f);
        layer.add(flor1);



        s= new Score(layer);
        s1= new Score1(layer);

        l=new LineWorld(world);
        lr=new LinegoalR(world,s1);
        ll=new LinegoalL(world,s);

        m = new Macro(world,200,300,layer,bul);
        b = new Ball(world,340f,300f);
        c = new Clark(world,450,300f,b,bul);
        bul.add(lr);
        bul.add(ll);
        bul.add(b);



        layer.add(m.layer());
        layer.add(b.layer());
        layer.add(c.layer());
        layer.add(timeS);
        timeS.setTranslation(300f,30);
        layer.add(timeM);
        timeM.setTranslation(250f,30);

        Image goal = assets().getImage("images/goal.png");
        ImageLayer goal1 = graphics().createImageLayer(goal);

        Image goal11 = assets().getImage("images/goal1.png");
        ImageLayer goal12 = graphics().createImageLayer(goal11);

        layer.add(goal1);
        goal1.setTranslation(0f, 255f);
        layer.add(goal12);
        goal12.setTranslation(600f, 255f);




    }
    public void gameover(){

        gameover=true;

        if(LinegoalR.o> LinegoalL.q){
            Image win = assets().getImage("images/youwin.png");
            ImageLayer winl = graphics().createImageLayer(win);
            winl.setTranslation(0f,0f);
            gameovers.add(winl);

            winner=true;
        }
        if(LinegoalR.o< LinegoalL.q){
            Image lose = assets().getImage("images/youlose.png");
            ImageLayer losel = graphics().createImageLayer(lose);
            losel.setTranslation(0f,0f);
            gameovers.add(losel);
            loser=true;
        }
        if(LinegoalR.o== LinegoalL.q){
            Image draw = assets().getImage("images/youdraw.png");
            ImageLayer drawl = graphics().createImageLayer(draw);
            drawl.setTranslation(0f,0f);
            gameovers.add(drawl);
            drawer=true;
        }
        Image gameover = assets().getImage("images/home.png");
        Image play = assets().getImage("images/refresh.png");
        ImageLayer playl = graphics().createImageLayer(play);
        ImageLayer gameoverl = graphics().createImageLayer(gameover);
        gameoverl.setTranslation(220f,248f);
        playl.setTranslation(320f,245f);
        gameoverl.addListener(new Pointer.Adapter() {
            @Override
            public void onPointerEnd(Pointer.Event event) {

                ss.push(new HomeScreen(ss));
                ss.remove(TestScreen.this);


            }
        });
        playl.addListener(new Pointer.Adapter(){
            @Override
            public void onPointerEnd(Pointer.Event event) {

                super.onPointerEnd(event);
                ss.remove(TestScreen.this);
            }
        });
        gameovers.add(gameoverl);
        gameovers.add(playl);
        layer.add(gameovers);

    }
    public GroupLayer Create(int time)
    {
        String[] txtTime = String.valueOf(time).split("");

        GroupLayer tmpGroup = graphics().createGroupLayer();

        float space = 0;
        for(int i = 1;i < txtTime.length;i++)
        {
            Image tmp = number[Integer.valueOf(txtTime[i])];
            ImageLayer numImg = graphics().createImageLayer(tmp);
            numImg.setTranslation((space += 30),0);
            tmpGroup.add(numImg);
        }

        return  tmpGroup;
    }


    @Override
    public void update(int delta) {
        super.update(delta);

        if(pausee==true)return;
        if(gameover==true)return;

        if(winner==true){
            LinegoalR.o=0;
            LinegoalL.q=0;
        }
        if(drawer==true){
            LinegoalR.o=0;
            LinegoalL.q=0;
        }
        if(loser==true){
            LinegoalR.o=0;
            LinegoalL.q=0;
        }

        m.update(delta);
        b.update(delta);
        c.update(delta);


        timeS.removeAll();
        timeM.removeAll();
        timeS.add(Create(sec));
        timeM.add(Create(min));

        timeout+=delta;
        if(timeout>=1000){
            if(sec==0){
                min--;
                sec=60;
            }
            else if(min<=0 && sec<=1){
                timet=true;
                gameover();
            }
            timeout=0;
            sec--;
        }


        world.step(0.033f, 10, 10);



        bul.addAll(tmp);
        tmp.clear();

        for(MotherClass motherClass : bul){
            if(motherClass.getTimeOut()){
                bin.add(motherClass);
            }else{
                motherClass.update(delta);
            }
        }

        for(MotherClass mo: bin){
          mo.Destroy();
          bul.remove(mo);
        }

        bin.clear();




    }


    @Override
    public void paint(Clock clock) {
        super.paint(clock);
        m.paint(clock);
        b.paint(clock);
        c.paint(clock);

        if(showDebugDraw){
            debugDraw.getCanvas().clear();
            world.drawDebugData();
        }


    }

}
