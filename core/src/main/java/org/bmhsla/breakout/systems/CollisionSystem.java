package org.bmhsla.breakout.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.sun.tools.javac.code.TypeTag;
import org.bmhsla.breakout.components.*;
import

public class CollisionSystem extends EntitySystem {


    priuate Sound noise;
    public CollisionSystem() {
        noise = Gdx.audio.newSound(Gdx.files.internal("noise.wav"));
    }


    @Override
    public void update(float deltaTime) {
        Entity paddle = null;
        for(Entity entity: getEngine().getEntitiesFor(Family.all(TypeTagComponent.class).get())) {
            TypeTagComponent ttc = entity.getComponent(TypeTagComponent.class);
            if(ttc.tag.equals("paddle")) {
                paddle = entity;
                break;
            }
        }

        if(paddle == null) {
            Gdx.app.error("CollisionSystem", "Paddle not found");
            return;
    }

    PositionComponent paddlePos = paddle.getComponent(PositionComponent.class);
        SizeComponent paddleSize = paddle.getComponent(SizeComponent.class);











        for (Entity entity : getEngine().getEntitiesFor(Family.all(TypeTagComponent.class, PositionComponent.class, SizeComponent.class, DirectionComponent.class).get())) {
            TypeTagComponent typeTagComponent = entity.getComponent(TypeTagComponent.class);
            if (typeTagComponent.tag.equals("ball")) {
                PositionComponent ballPosition = entity.getComponent(PositionComponent.class);
                SizeComponent ballSize = entity.getComponent(SizeComponent.class);
                DirectionComponent ballDirection = entity.getComponent(DirectionComponent.class);
                SpeedComponent ballSpeed = entity.getComponent(SpeedComponent.class);

                ballPosition.position.x += ballDirection.direction.x * ballSpeed.speed * deltaTime;
                ballPosition.position.y += ballDirection.direction.y * ballSpeed.speed * deltaTime;

                //remove ball that falls below paddle


                //bounce off the left wall
                if (ballPosition.position.x <= 0) {
                    ballPosition.position.x = 0;
                    ballDirection.direction.x *= -1;


                }
                //bounce off the right wall
                if (ballPosition.position.x + ballSize.size.x >= Gdx.graphics.getWidth()) {
                    ballPosition.position.x = Gdx.graphics.getWidth() - ballSize.size.x;
                    ballDirection.direction.x *= -1;


                }//bounce of top wall
                if (ballPosition.position.y + ballSize.size.y >= Gdx.graphics.getHeight()) {
                    ballPosition.position.y = Gdx.graphics.getHeight() - ballSize.size.y;
                    ballDirection.direction.y *= -1;


                }

                //bounce of paddle
                boolean overlapsX = ballPosition.position.x + ballSize.size.x > paddlePos.position.x &&
                    ballPosition.position.x < paddlePos.position.x + paddleSize.size.x;
                boolean overlapsY = ballPosition.position.y <= paddlePos.position.y + paddleSize.size.y &&
                    ballPosition.position.y + ballSize.size.y > paddlePos.position.y;

                if(overlapsX && overlapsY && ballDirection.direction.y < 0) {
                    ballPosition.position.y = paddlePos.position.y + paddleSize.size.y;
                    ballDirection.direction.y *= -1; noise.play();
                }
            }
        }
    }
}
