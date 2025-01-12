package entities.shots;

import entities.Entity;
import entities.enemies.Boss_Duality;
import main.Game;
import world.World;

public class Shot_DualityBlackHand extends Shot {
    private Boss_Duality owner;
    public Shot_DualityBlackHand(int x, int y, double dx, double dy, Boss_Duality own){
        super(x, y, 13, 14, dx, dy, 0, 5, 40, 35, Game.sheet.getSprite(32, 160, 16, 16));
        owner = own;
    }

    public void die(Entity target) {
        if(target == Game.player && World.bossTime){
            Game.player.mana = Math.max(Game.player.mana - damage, 0);
            owner.life = Math.min(owner.maxLife, owner.life + damage);
        }
        Game.eShots.remove(this);
    }
}
