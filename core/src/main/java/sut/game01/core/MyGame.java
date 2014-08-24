package sut.game01.core;

import playn.core.Game;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;

public class MyGame extends Game.Default {

    private ScreenStack ss = new ScreenStack();
    protected final Clock.Source clock = new Clock.Source(33);

    public MyGame() {
        super(33);
    }

    @Override
    public void init() {
        final Screen home = new HomeScreen(ss);
        ss.push(home);

    }

    @Override
    public void update(int delta) {
        ss.update(delta);
    }


    @Override
    public void paint(float alpha) {
        clock.paint(alpha);
        ss.paint(clock);
    }
}
