package sut.game01.core;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

/**
 * Created by Administrator on 20/3/2557.
 */
public class LineWorld extends MotherClass {

    private static int width = 24;
    private static int height = 18;
    private World world;




    public LineWorld(World world){

        this.world = world;
        Body ground = world.createBody(new BodyDef());
        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsEdge(new Vec2(0,height-2.5f),new Vec2(width,height-2.5f));
        ground.createFixture(groundShape,0.0f);

        Body ground1 = world.createBody(new BodyDef());
        PolygonShape groundShape1 = new PolygonShape();
        groundShape1.setAsEdge(new Vec2(0,height-18),new Vec2(width,height-18));
        ground1.createFixture(groundShape1,0.0f);

        Body ground2 = world.createBody(new BodyDef());
        PolygonShape groundShape2 = new PolygonShape();
        groundShape2.setAsEdge(new Vec2(width,0),new Vec2(width,height));
        ground2.createFixture(groundShape2,0.0f);

        Body ground3 = world.createBody(new BodyDef());
        PolygonShape groundShape3 = new PolygonShape();
        groundShape3.setAsEdge(new Vec2(0.01f,height),new Vec2(0.01f,0));
        ground3.createFixture(groundShape3,0.0f);


        Body ground5 = world.createBody(new BodyDef());
        PolygonShape groundShape5 = new PolygonShape();
        groundShape5.setAsEdge(new Vec2(0.1f,height-8.50f),new Vec2(1.25f,height-8.50f));
        ground5.createFixture(groundShape5,0.0f);


        Body ground6 = world.createBody(new BodyDef());
        PolygonShape groundShape6 = new PolygonShape();
        groundShape6.setAsEdge(new Vec2(width-0.1f,height-8.50f),new Vec2(width-1.25f,height-8.50f));
        ground6.createFixture(groundShape6,0.0f);





    }


}
