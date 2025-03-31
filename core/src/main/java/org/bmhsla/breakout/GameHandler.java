package org.bmhsla.breakout;

import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.utils.ScreenUtils;
import org.bmhsla.breakout.factories.EntityFactory;
import org.bmhsla.breakout.systems.CollisionSystem;
import org.bmhsla.breakout.systems.CollisionSystem;
import org.bmhsla.breakout.systems.InputSystem;
import org.bmhsla.breakout.systems.RenderSystem;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class GameHandler extends ApplicationAdapter {

    private Engine engine;


    @Override
    public void create() {

        engine = new Engine();
        engine.addSystem(new RenderSystem());
        engine.addSystem(new CollisionSystem());
        EntityFactory entityFactory = new EntityFactory();
        engine.addEntity(entityFactory.createPaddleEntity());
        engine.addEntity(entityFactory.createBallEntity());
        engine.addSystem(new InputSystem());
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

       engine.update(Gdx.graphics.getDeltaTime());
    }

}

