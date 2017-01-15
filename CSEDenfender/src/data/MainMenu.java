package data;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;
import helpers.StateManager.GameState;

import static helpers.Artist.*;

public class MainMenu {

	private Texture background,ruleBackground;
	private UI menuUI;
	
	public MainMenu(){
		background = QuickLoad("mainmenu");
		menuUI = new UI();
		menuUI.addButton("Play", "playButton", WIDTH / 10 * 1, (int)(HEIGHT * 0.9f));
		menuUI.addButton("Editor", "editorButton", (int) (WIDTH / 10 * 3.5), (int)(HEIGHT * 0.9f));
		menuUI.addButton("Quit", "quitButton", WIDTH / 10 * 6, (int)(HEIGHT * 0.9f));
		ruleBackground = QuickLoad("rule");
	}
	
	private void updateButtons(){
		if(Mouse.isButtonDown(0)){
			if(menuUI.isButtonClicked("Play"))
				StateManager.setState(GameState.GAME);
			if(menuUI.isButtonClicked("Editor"))
				StateManager.setState(GameState.EDITOR);
			if(menuUI.isButtonClicked("Quit"))
				System.exit(0);
			
		}
	}
	public void update(){
		DrawQuadTex(background, 0, 0, 2048, 1024);
		DrawQuadTex(ruleBackground, 1280, 0, 192, 960);
		menuUI.draw();
		updateButtons();
	}
}
