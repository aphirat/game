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
public class LinegoalL extends MotherClass {

    private static int width = 24;
    private static int height = 18;
    private World world;
    Score s;
    private Body body;
    public static int q=0;

    Body ground4;
    Macro m;
    private GroupLayer layer;ArrayList<MotherClass> mm;


    public LinegoalL(World world, Score s){
        this.world=world;
        this.s = s;

        ground4 = world.createBody(new BodyDef());
        PolygonShape groundShape4 = new PolygonShape();
        groundShape4.setAsEdge(new Vec2(0.1f,height-3f),new Vec2(0.1f,height-8.325f));
        ground4.createFixture(groundShape4,0.0f);
    }

    @Override
    public void Contact(MotherClass other) {
        if(other.getClass()== Ball.class){
            s.show();
           q++;
            Macro.state= Macro.State.GOAL;
        }
    }

    public static int gamescore(){
        return q;
    }


    public Body getBody(){
        return ground4;
    }
}
