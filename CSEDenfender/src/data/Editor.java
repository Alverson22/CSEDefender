package data;

import static helpers.Artist.DrawQuadTex;
import static helpers.Artist.HEIGHT;
import static helpers.Artist.QuickLoad;
import static helpers.Leveler.*;

import javax.swing.JOptionPane;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import UI.UI;
import UI.UI.Menu;
import helpers.StateManager;
import helpers.StateManager.GameState;

public class Editor {

	private TileGrid grid;
	private int index;
	private TileType[] types;
	private UI editorUI;
	private Menu tilePickerMenu;
	private Texture menuBackground;
	
	public Editor() {
		this.grid = LoadMap("newMap1");
		this.index = 0;
		
		this.types = new TileType[12];
		this.types[0] = TileType.Grass;
		this.types[1] = TileType.Dirt;
		this.types[2] = TileType.Water;
		this.types[3] = TileType.castle1;
		this.types[4] = TileType.castle2;
		this.types[5] = TileType.castle3;
		this.types[6] = TileType.castle4;
		this.types[7] = TileType.castle5;
		this.types[8] = TileType.castle6;
		this.types[9] = TileType.castle7;
		this.types[10] = TileType.castle8;
		this.types[11] = TileType.castle9;
		this.menuBackground = QuickLoad("menu_background_editor");
		setupUI();
	}
	private void setupUI(){
		editorUI = new UI();
		editorUI.createMenu("TilePicker", 1280, 100, 192, 960, 2, 0);
		tilePickerMenu = editorUI.getMenu("TilePicker");
		tilePickerMenu.quickAdd("Grass", "grass64");
		tilePickerMenu.quickAdd("Dirt", "dirt64");
		tilePickerMenu.quickAdd("Water", "water64");
		tilePickerMenu.quickAdd("castle1", "castle-1");
		tilePickerMenu.quickAdd("castle2", "castle-2");
		tilePickerMenu.quickAdd("castle3", "castle-3");
		tilePickerMenu.quickAdd("castle4", "castle-4");
		tilePickerMenu.quickAdd("castle5", "castle-5");
		tilePickerMenu.quickAdd("castle6", "castle-6");
		tilePickerMenu.quickAdd("castle7", "castle-7");
		tilePickerMenu.quickAdd("castle8", "castle-8");
		tilePickerMenu.quickAdd("castle9", "castle-9");
	}

	public void update() {
		draw();

		if(Mouse.next()){
			boolean mouseClicked = Mouse.isButtonDown(0);
			if(mouseClicked)
			{
				if(tilePickerMenu.isButtonClicked("Grass")){
					index = 0;
				}else if(tilePickerMenu.isButtonClicked("Dirt")){
					index = 1;
				}else if(tilePickerMenu.isButtonClicked("Water")){
					index = 2;
				}else if(tilePickerMenu.isButtonClicked("castle1")){
					index = 3;
				}else if(tilePickerMenu.isButtonClicked("castle2")){
					index = 4;
				}else if(tilePickerMenu.isButtonClicked("castle3")){
					index = 5;
				}else if(tilePickerMenu.isButtonClicked("castle4")){
					index = 6;
				}else if(tilePickerMenu.isButtonClicked("castle5")){
					index = 7;
				}else if(tilePickerMenu.isButtonClicked("castle6")){
					index = 8;
				}else if(tilePickerMenu.isButtonClicked("castle7")){
					index = 9;
				}else if(tilePickerMenu.isButtonClicked("castle8")){
					index = 10;
				}else if(tilePickerMenu.isButtonClicked("castle9")){
					index = 11;
				}else{
					setTile();
				}
			}
		}

		// Handle Keyboard Input
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT && Keyboard.getEventKeyState()) {
				moveIndex();
			}
			if (Keyboard.getEventKey() == Keyboard.KEY_LEFT && Keyboard.getEventKeyState()) {
				SaveMap("newMap1", grid);
				StateManager.map = LoadMap("newMap1");
				StateManager.setState(GameState.MAINMENU);
				JOptionPane.showMessageDialog(null, "�a���x�s���\  ��^�D�e��", "�q��", JOptionPane.QUESTION_MESSAGE);
			}
		}
	}
	
	private void draw(){
		DrawQuadTex(menuBackground, 1280, 0, 192, 960);
		grid.draw();
		editorUI.draw();
	}

	private void setTile() {
		grid.setTile((int) Math.floor(Mouse.getX() / 64), (int) Math.floor((HEIGHT - Mouse.getY() - 1) / 64),
				types[index]);
	}

	private void moveIndex() {
		index++;
		if (index > types.length - 1) {
			index = 0;
		}
	}

}