package entities.shots;

import entities.Entity;
import entities.AE.AE_ManaExplosion;
import main.Game;

public class Shot_PlayerMana extends Shot {
    int explDmg, explKnck;
    public Shot_PlayerMana(int x, int y, double dx, double dy, double ang, double speed, double dmg, int eDmg, int eKnc){
        super(x, y, 10, 10, dx, dy, ang, speed, dmg, 35, Game.sheet.getSprite(131, 67, 10, 10));
        explDmg = eDmg;
        explKnck = eKnc;
    }

    public void die(Entity target){
        if (explDmg > 0) Game.entities.add(new AE_ManaExplosion(centerX(), centerY(), explDmg, explKnck));
        Game.shots.remove(this);
    }
}
