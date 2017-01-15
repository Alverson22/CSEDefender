package data;
import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public enum TowerType {
	
	CannonRed(new Texture[]{QuickLoad("cannonBaseRed") , QuickLoad("cannonGun")},ProjectileType.FireBall, 0, 400, 2.5f, -50),
	CannonBlue(new Texture[]{QuickLoad("cannonBase") , QuickLoad("cannonGunBlue")},ProjectileType.CannonBall, 0, 300, 1, -20),
	CannonIce(new Texture[]{QuickLoad("cannonIceBase2"), QuickLoad("cannonIceGun")},ProjectileType.IceBall, 0, 200, 2, -30);
	
	Texture[] textures;
	ProjectileType projectileType;
	int damage, range, cost;
	float firingSpeed;
	
	TowerType(Texture[] textures ,ProjectileType projectileType, int damage, int range, float firingSpeed, int cost)
	{
		this.textures = textures;
		this.projectileType = projectileType;
		this.damage = damage;
		this.range = range;
		this.firingSpeed = firingSpeed;
		this.cost = cost;
	}
}
