package data;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import static helpers.Clock.*;

public class Wave {

	private float timeSinceLastSpawn, spawnTime;
	private Enemy[] enemyTypes;
	private CopyOnWriteArrayList<Enemy> enemyList;
	private int enemiesPerWave , enemiesSpawned;
	private boolean waveCompleted;
	public static boolean boss = false;

	public Wave(Enemy[] enemyTypeS, float spawnTime, int enemiesPerWave) {
		this.enemyTypes = enemyTypeS;
		this.spawnTime = spawnTime;
		this.enemiesPerWave = enemiesPerWave;
		this.enemiesSpawned = 0;
		this.timeSinceLastSpawn = 0;
		this.enemyList = new CopyOnWriteArrayList<Enemy>();
		this.waveCompleted = false;
		Spawn();
	}

	public void update() {
		boolean allEnemiesDead = true;
		if(enemiesSpawned < enemiesPerWave){
			timeSinceLastSpawn += Delta();
			if (timeSinceLastSpawn > spawnTime) {
				Spawn();
				timeSinceLastSpawn = 0;
			}
		}
		for (Enemy e : enemyList) {
			if (e.isAlive()) {
				allEnemiesDead = false;
				e.update();
				e.draw();
			} else
				enemyList.remove(e);
		}
		if(allEnemiesDead)
		{
			waveCompleted = true;
		}
	}

	private void Spawn() {
		if(!boss){
		int enemyChosen = 0;
		Random random = new Random();
		enemyChosen = random.nextInt(enemyTypes.length - 1);
		
		enemyList.add(new Enemy(enemyTypes[enemyChosen].getTexture(), enemyTypes[enemyChosen].getStartTile(), enemyTypes[enemyChosen].getTileGrid(), 64, 64,
				enemyTypes[enemyChosen].getSpeed(), enemyTypes[enemyChosen].getHealth()));
		enemiesSpawned++;
		}
		else {
			enemyList.add(new Enemy(enemyTypes[2].getTexture(), enemyTypes[2].getStartTile(), enemyTypes[2].getTileGrid(), 64, 64,
					enemyTypes[2].getSpeed(), enemyTypes[2].getHealth()));
			enemiesSpawned++;
		}

	}
	
	public boolean isCompleted()
	{
		return waveCompleted;
	}
	
	public CopyOnWriteArrayList<Enemy> getEnemiyList()
	{
		return enemyList;
	}
}
