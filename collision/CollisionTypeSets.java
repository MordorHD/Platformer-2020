package platformer.collision;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javaJG.geometry.Rectangle2D;
import platformer.Settings;
import platformer.entity.Entity;

final class CollisionTypeSets {

	private CollisionTypeSets() {
	}
//
//	private static final List<CollisionType> GROUND_TRIANGLE_2D = Arrays.asList(
//			new CollisionType("DIRT_GRASS_TRIANGLE", true, true, Layer.FOREGROUND,
//				new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Dirt_Grass_Triangle.png")))
//					.modifyBounds(false, new Triangle2D(0, 0, -30, -30))
//			
//			);

	private static final List<CollisionType> GROUND_HALF_2D = Arrays.asList(
			CollisionTypeFactory.build()
					.name("STONE_GRASS_HALF")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Stone_Grass_Half.png")))
					.collide(new Rectangle2D(0, 0, 30, 10))
					.create(),
			CollisionTypeFactory.build()
					.name("STONE_HALF")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Stone_Half.png")))
					.collide(new Rectangle2D(0, 0, 30, 10))
					.create());

	private static final List<CollisionType> GROUND_2D = Arrays.asList(
			CollisionTypeFactory.build()
					.name("STONE_BACKGROUND")
					.noCollision()
					.layer(Layer.BACKGROUND)
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Stone_Background.png")))
					.create(),
			CollisionTypeFactory.build()
					.name("STONE")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Stone.png")))
					.create(),
			CollisionTypeFactory.build()
					.name("STONE_GRASS")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Stone_Grass.png")))
					.create());

	private static final List<CollisionType> ACTION = Arrays.asList(
			CollisionTypeFactory.build()
					.name("COIN")
					.noCollision()
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Coin.png"), "Coin/Coin.png", 16, 16))
					.entityBehaviour(new EntityBehaviour() {
						@Override
						public void intersecting(Collision parent, Entity e) {
							parent.closeActivity();
							parent.setVisibility(false);
							e.stats.NUMBER_OF_COINS++;
						}
					})
					.create(),
			CollisionTypeFactory.build()
					.name("SPIKES")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Spikes.png")))
					.collide(new Rectangle2D(0, 10, 30, 20))
					.entityBehaviour(new EntityBehaviour() {
						@Override
						public void intersecting(Collision parent, Entity e) {
							if (e.stats.HP > 0) e.applyDamage(1);
							e.velocityY = -8;
						}
					})
					.create(),
			CollisionTypeFactory.build()
					.name("CEILING_SPIKES")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Ceiling_Spikes.png")))
					.collide(new Rectangle2D(0, 0, 30, 20))
					.entityBehaviour(new EntityBehaviour() {
						@Override
						public void intersecting(Collision parent, Entity e) {
							if (e.stats.HP > 0) e.applyDamage(1);
						}
					})
					.create(),
			CollisionTypeFactory.build()
					.name("HEART")
					.noCollision()
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Heart.png")))
					.entityBehaviour(new EntityBehaviour() {
						@Override
						public void intersecting(Collision parent, Entity e) {
							if (e.stats.HP < e.stats.MAX_HP) {
								e.stats.HP++;
								parent.closeActivity();
								parent.setVisibility(false);
							}
						}
					})
					.create());
	private static final List<CollisionType> DECORATION_3D = Arrays.asList(
			CollisionTypeFactory.build()
					.name("TORCH")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Torch.png"), "Torch/Torch.png", 48, 48))
					.noCollision()
					.layer(Layer.DECORATIVE)
					.draw(new Rectangle2D(-30, -30, 90, 90))
					.create(),
			CollisionTypeFactory.build()
					.name("TORCH_FLOOR")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Torch_Floor.png"), "Torch_Floor/Torch_Floor.png", 48, 48))
					.noCollision()
					.layer(Layer.DECORATIVE)
					.draw(new Rectangle2D(-30, -30, 90, 90))
					.create());
	
	private static final List<CollisionType> DECORATION_2D = Arrays.asList(
			CollisionTypeFactory.build()
					.name("GRASS")
					.graphics(new GraphicalCollisionComponent(Settings.loadImage("collisionImages/Grass.png")))
					.noCollision()
					.layer(Layer.FOREGROUND)
					.create());

	private static final List<CollisionType> BUILT_IN_TYPES;

	static {
		BUILT_IN_TYPES = new ArrayList<CollisionType>(GROUND_2D);
		BUILT_IN_TYPES.addAll(GROUND_HALF_2D);
		BUILT_IN_TYPES.addAll(ACTION);
		BUILT_IN_TYPES.addAll(DECORATION_2D);
		BUILT_IN_TYPES.addAll(DECORATION_3D);
	}

	static List<CollisionType> BUILT_IN_TYPES() {
		return BUILT_IN_TYPES;
	}

}
