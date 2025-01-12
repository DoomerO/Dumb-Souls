package entities.shots;

import entities.Entity;
import main.Game;

public class Shot_PlayerIce extends Shot{
    private double frost;
    public Shot_PlayerIce(int x, int y, double dx, double dy, double spd, double dmg, double frt){
        super(x, y, 14, 14, dx, dy, 0, spd, dmg, 35, Game.sheet.getSprite(128, 48, 16, 16));
        frost = frt;
    }

    public void die(Entity target){
        if(target != null) target.slowness = Math.max(target.slowness, frost);
        Game.shots.remove(this);
    }
}