package entities.shots;

import entities.Entity;
import entities.enemies.Boss_Sucubus;
import main.Game;
import world.World;

public class Shot_SuccubusVampireBat extends Shot {
    private Boss_Sucubus owner;
    public Shot_SuccubusVampireBat(int x, int y, double dx, double dy, double dmg, Boss_Sucubus own){
        super(x + (Game.rand.nextInt(2) == 0 ? 80 : -80), y + (Game.rand.nextInt(2) == 0 ? 80 : -80), 8, 13, dx, dy, 0, 7, dmg, 50, Game.sheet.getSprite(96, 160, 16, 16));
        owner = own;
    }

    public void die(Entity target) {
        if(target == Game.player && World.bossTime){
            owner.life = Math.min(owner.maxLife, owner.life + damage);
        }
        Game.eShots.remove(this);
    }
}
