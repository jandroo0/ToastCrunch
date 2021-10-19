package com.garcia.toastcrunch.Util;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.garcia.toastcrunch.Objects.Chest;
import com.garcia.toastcrunch.Objects.Enemies.Enemy;
import com.garcia.toastcrunch.Objects.Enemies.Goblin;
import com.garcia.toastcrunch.Objects.Enemies.Worm;
import com.garcia.toastcrunch.Objects.InteractiveTileObject;
import com.garcia.toastcrunch.Objects.Item;
import com.garcia.toastcrunch.Screens.GameScreen;

import java.net.InterfaceAddress;
import java.util.ArrayList;

public class B2WorldCreator {

    private Array<Chest> chests;
    private Array<Goblin> goblins;
    private Array<Worm> worms;

    public B2WorldCreator(GameScreen screen) {

        World world = screen.getWorld();
        TiledMap map = screen.getMap();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        chests = new Array<Chest>();
        goblins = new Array<Goblin>();
        worms = new Array<Worm>();

        // create ground fixtures
        for (MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = Constants.GROUND_BIT;
            body.createFixture(fdef);
        }

//         create bounds fixtures
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / Constants.PPM, (rect.getY() + rect.getHeight() / 2) / Constants.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rect.getWidth() / 2) / Constants.PPM, (rect.getHeight() / 2) / Constants.PPM);
            fdef.shape = shape;
            fdef.friction = 0;
            fdef.filter.categoryBits = Constants.BORDER_BIT;
            body.createFixture(fdef);
        }

        // goblin enemy
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            goblins.add(new Goblin(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }

        // worm enemy
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            worms.add(new Worm(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }

        // chest object
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect =  ((RectangleMapObject) object).getRectangle();
            chests.add(new Chest(screen, rect.getX() / Constants.PPM, rect.getY() / Constants.PPM));
        }
    }


    public Array<Item> getItems() {
        Array<Item> items = new Array<Item>();
        items.addAll(chests);
        return items;
    }

    public Array<Enemy> getEnemies() {
        Array<Enemy> enemies = new Array<Enemy>();
        enemies.addAll(goblins);
        enemies.addAll(worms);
        return enemies;
    }

}
