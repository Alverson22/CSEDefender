package data;

import static helpers.Artist.QuickLoad;

import java.util.Random;

import javax.swing.JOptionPane;

import static helpers.Artist.DrawQuadTex;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;
import static helpers.Artist.*;

public class Game {
	private Random rand = new Random();
	private TileGrid grid;
	private Player player;
	private WaveManager waveManager;
	private UI gameUI;
	private Menu towerPickerMenu;
	private Texture menuBackground;
	private Enemy[] enemyTypes;
	private String questions[] = {"���j��u�S������F�v�H��?  A.���^��  B.���^�E  C.�ڤڰ�",
			"�аݤU�C���@���ߤ���t�~�Ⱖ���b�@�_?  A.����  B.����  C.�N�N ",
			"�аݳ\�����б³̳��w�ܪ����Ƭ���?  A.�i��  B.���d  C.��",
			"�аݰ��|�l�б¸g���F�ƻ��ܤ� = ?  A.�ܭD  B.�ܫ�  C.�ܦ�"
	};
	private String answers[] = {"C", "B", "A", "B"};
	private String answer;
	
	public Game(TileGrid grid){
		this.grid = grid;
		enemyTypes = new Enemy[3];
		enemyTypes[0] = new EnemyPlane(2, 0, grid);
		enemyTypes[1] = new EnemyUFO(2, 0, grid);
		enemyTypes[2] = new EnemyAlien(2, 0, grid);
		waveManager = new WaveManager(enemyTypes, 1, 5);
		player = new Player(grid, waveManager);
		player.setup();
		this.menuBackground = QuickLoad("menu_background2");
		setupUI();
	}
	
	private void setupUI(){
		gameUI = new UI();
		
		gameUI.createMenu("TowerPicker", 1280, 400, 192, 960, 2, 0);
		towerPickerMenu = gameUI.getMenu("TowerPicker");
		towerPickerMenu.quickAdd("IceCannon", "cannonIceBase2");
		towerPickerMenu.quickAdd("30", "money30");
		towerPickerMenu.quickAdd("BlueCannon", "cannonBase");
		towerPickerMenu.quickAdd("20", "money20");
		towerPickerMenu.quickAdd("RedCannon", "cannonBaseRed");
		towerPickerMenu.quickAdd("50", "money50");
		gameUI.addButton("Mission", "icon2", WIDTH / 2+575, (int)(HEIGHT * 0.15f));
		
	}
	
	private void updateUI(){
		gameUI.draw();
		gameUI.drawString(1320, 700, "Lives: " + Player.Lives);
		gameUI.drawString(1320, 800, "Cash: " + Player.Cash);
		gameUI.drawString(1320, 600, "Wave "+waveManager.getWaveNumber());
		gameUI.drawString(0, 0, StateManager.framesInLastSecond+" fps");
		if(Mouse.next()){
			boolean mouseClicked = Mouse.isButtonDown(0);
			if(mouseClicked)
			{
				if(towerPickerMenu.isButtonClicked("BlueCannon"))
				{
						player.pickTower(new TowerCannonBlue(TowerType.CannonBlue,grid.getTile(0, 0),waveManager.getCurrentWave().getEnemiyList()));
				}
				if(towerPickerMenu.isButtonClicked("IceCannon"))
				{
					player.pickTower(new TowerCannonIce(TowerType.CannonIce,grid.getTile(0, 0),waveManager.getCurrentWave().getEnemiyList()));
				}
				if(towerPickerMenu.isButtonClicked("RedCannon"))
				{
					player.pickTower(new TowerCannonFire(TowerType.CannonRed,grid.getTile(0, 0),waveManager.getCurrentWave().getEnemiyList()));
				}
			}
		}
	}
	
	public void updateButtons() {
		if(Mouse.isButtonDown(0)) {
			int i = rand.nextInt(4);
			try{
			if(gameUI.isButtonClicked("Mission")) {
				answer = JOptionPane.showInputDialog(questions[i]);
				try {
					answer = answer.toUpperCase();
					if(answer.equals(answers[i])) {
						JOptionPane.showMessageDialog(null, "�A����F !!     ��o50��~~", "�q��", JOptionPane.QUESTION_MESSAGE);
						player.modifyCash(50);
					}
					else
						JOptionPane.showMessageDialog(null, "�A�����F !!", "�q��", JOptionPane.QUESTION_MESSAGE);
						
				} catch (NullPointerException e){
					JOptionPane.showMessageDialog(null, "�A�����F�Ȩ��B�~���������| !!", "�q��", JOptionPane.QUESTION_MESSAGE);
				}
					gameUI.destroyMissionButton();
			}
			} catch(NullPointerException e) {
			}
		}
	}
	
	public void update(){
		DrawQuadTex(menuBackground, 1280, 0, 192, 960);
		grid.draw();
		waveManager.update();
		player.update();
		gameUI.draw();
		updateUI();
		updateButtons();
	}
}