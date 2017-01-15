package helpers;

import data.Editor;
import data.Game;
import data.Lose;
import data.MainMenu;
import data.TileGrid;
import data.Wave;
import data.Win;

import static helpers.Leveler.LoadMap;

public class StateManager {
	
	public static enum GameState{
		MAINMENU, GAME, EDITOR, WIN, LOSE
	}
	
	public static GameState gameState = GameState.MAINMENU;
	public static MainMenu mainMenu;
	public static Game game;
	public static Editor editor;
	public static Lose lose;
	public static Win win;
	
	public static long nextSecond = System.currentTimeMillis() + 1000;
	public static int framesInLastSecond = 0;
	public static int framesInCurrentSecond = 0;
	
	public static TileGrid map = LoadMap("newMap1");
	
	public static void update(){
		switch(gameState){
		case MAINMENU:
			if (mainMenu == null) {
				mainMenu = new MainMenu();
			}
			lose = null;
			mainMenu.update();
			break;
		case GAME:
			if(game == null)
				game = new Game(map);
			game.update();
			break;
		case EDITOR:
			if(editor == null)
				editor = new Editor();
			editor.update();
			break;
		case LOSE:
			if(lose == null) {
				lose = new Lose();
			}
			game = null;
			Wave.boss = false;
			lose.update();
			break;
		case WIN:
			if(win == null) {
				win = new Win();
			}
			game = null;
			Wave.boss = false;
			win.update();
			break;
		}
		
		long currentTime = System.currentTimeMillis();
		if(currentTime > nextSecond){
			nextSecond += 1000;
			framesInLastSecond = framesInCurrentSecond;
			framesInCurrentSecond = 0;	
		}
		framesInCurrentSecond++;
	}
	
	public static void setState(GameState newState){
		gameState = newState;
	}
}
