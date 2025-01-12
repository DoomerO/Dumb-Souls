package entities.enemies;

import entities.orbs.EXP_Orb;
import main.*;
import world.World;

public class Enemy_Debri extends Enemy{
	public Enemy_Debri(int x, int y, int expValue, boolean specialRare) {
		super(x, y, 16, 16, Game.sheet.getSprite(48, 144, 16, 16));
		this.specialRare = specialRare;
		if (specialRare){
			specialMult = 3;
			hue = 0xFFFFFF;
		}
		getAnimation(48, 144, 16, 16, 3);
        this.expValue = expValue;
        maxLife = 8 * specialMult + (int)(0.08 * World.wave);
        life = maxLife;
		damage = 6 * specialMult + 0.06 * World.wave;
		this.expValue = expValue;
        maxSpeed = 0.2 + (specialMult - 1)/3;
        speed = maxSpeed;
		setMask(1, 1, 14, 14);
		maxFrames = 6;
	}

    public void die(){
        Game.enemies.remove(this);
        Game.entities.add(new EXP_Orb(centerX(), centerY(), expValue, hue));
    }

    public void tick() {
		animate();

        if (calculateDistance(centerX(), centerY(), Game.player.centerX(), Game.player.centerY()) < 48){
            maxSpeed = 1.5;
        }

        if (!isColiding(Game.player)) {
			movement();
		}

		else {
			giveCollisionDamage(Game.player, 15, 1);
		}
        slownessEffect(0.95);

        shotDamage();
		if (life <= 0) {
			die();
		}
	}
}