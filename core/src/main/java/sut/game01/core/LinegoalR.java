package sut.game01.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import playn.core.GroupLayer;
import sut.game01.core.sprite.Ball;
import sut.game01.core.sprite.Macro;

import java.util.ArrayList;

/**
 * Created by Administrator on 24/3/2557.
 */
public class LinegoalR extends MotherClass {

    private static int width = 24;
    private static int height = 18;
    private World world;
    private Score1 s1;
    public static int o=0;
    private Macro m;
    private Body body;
    private Body ground7;
    private GroupLayer layer;
    ArrayList<MotherClass> mm;

    public LinegoalR(World world, Score1 s1){
        this.world=world;
        this.s1 = s1;

        ground7 = world.createBody(new BodyDef());
        PolygonShape groundShape7 = new PolygonShape();
        groundShape7.setAsEdge(new Vec2(width-0.1f,height-3f),new Vec2(width-0.1f,height-8.325f));
        ground7.createFixture(groundShape7,0.0f);


    }

    @Override
    public void Contact(MotherClass other) {
        if(other.getClass()== Ball.class){
            s1.show();
            o++;

        }
    }
    public static int gamescore1(){
        return o;

    }


    public Body getBody(){
        return ground7;
    }




}
