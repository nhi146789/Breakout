package org.bmhsla.breakout.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.bmhsla.breakout.components.PositionComponent;
import org.bmhsla.breakout.components.RenderComponent;
import org.bmhsla.breakout.components.SizeComponent;

public class RenderSystem  extends EntitySystem {
    private ShapeRenderer shapeRenderer;

    public RenderSystem() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void update(float deltaTime) {
        shapeRenderer.setColor(Color.GREEN);
        for(Entity entity: getEngine().getEntitiesFor(Family.all(PositionComponent.class, SizeComponent.class, RenderComponent.class).get())) {
            PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
            SizeComponent sizeComponent = entity.getComponent(SizeComponent.class);
            RenderComponent renderComponent = entity.getComponent(RenderComponent.class);

            shapeRenderer.begin(renderComponent.shapeType);

            switch(renderComponent.shape) {
                case RECTANGLE:
                    shapeRenderer.rect(positionComponent.position.x, positionComponent.position.y, sizeComponent.size.x, sizeComponent.size.y);
                    break;

                    case CIRCLE:
                        shapeRenderer.circle(positionComponent.position.x + sizeComponent.size.x / 2f,
                            positionComponent.position.y + sizeComponent.size.y / 2f,
                            sizeComponent.size.x / 2f);
                        break;
                        case TRIANGLE:
                            shapeRenderer.triangle(positionComponent.position.x,
                                positionComponent.position.y,
                                positionComponent.position.x + sizeComponent.size.x,
                                positionComponent.position.y,
                                positionComponent.position.x + sizeComponent.size.x / 2f,
                                positionComponent.position.y + sizeComponent.size.y );
                            break;

            }
            shapeRenderer.end();
        }
    }
}
