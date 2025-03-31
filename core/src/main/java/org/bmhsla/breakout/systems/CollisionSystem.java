package org.bmhsla.breakout.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import org.bmhsla.breakout.components.*;

public class CollisionSystem extends EntitySystem {

    @Override
    public void update(float deltaTime) {
        for (Entity entity : getEngine().getEntitiesFor(Family.all(TypeTagComponent.class, PositionComponent.class, SizeComponent.class, DirectionComponent.class).get())) {
            TypeTagComponent typeTagComponent = entity.getComponent(TypeTagComponent.class);
            if (typeTagComponent.tag.equals("ball")) {
                PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
                SizeComponent sizeComponent = entity.getComponent(SizeComponent.class);
                DirectionComponent directionComponent = entity.getComponent(DirectionComponent.class);
                SpeedComponent speedComponent = entity.getComponent(SpeedComponent.class);

                positionComponent.position.x += directionComponent.direction.x * speedComponent.speed * deltaTime;
                positionComponent.position.y += directionComponent.direction.y * speedComponent.speed * deltaTime;

                //remove ball that falls below paddle


                //bounce off the left wall
                if (positionComponent.position.x <= 0) {
                    positionComponent.position.x = 0;
                    directionComponent.direction.x *= -1;


                }

                if (positionComponent.position.x + sizeComponent.size.x >= Gdx.graphics.getWidth()) {
                    positionComponent.position.x = Gdx.graphics.getWidth() - sizeComponent.size.x;
                    directionComponent.direction.x *= -1;


                }
                if (positionComponent.position.y + sizeComponent.size.y >= Gdx.graphics.getHeight()) {
                    positionComponent.position.y = Gdx.graphics.getHeight() - sizeComponent.size.y;
                    directionComponent.direction.y *= -1;


                }
            }
        }
    }
}
