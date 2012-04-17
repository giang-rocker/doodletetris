package myGame.doodleTetris;


import myGame.doodleTetris.framework.AndroidGraphics;
import myGame.doodleTetris.framework.Image;
import myGame.doodleTetris.framework.ImgButton;
import myGame.doodleTetris.framework.Music;
import myGame.doodleTetris.framework.Screen;
import myGame.doodleTetris.framework.Sound;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

public class LoadingScreen extends Screen {

	public LoadingScreen (Game game) {
		super(game);
		// TODO Auto-generated constructor stub
		Log.d("myTag","loading screen");
	}
	
	@Override
	public void update(float deltaTime) {
		AndroidGraphics g = game.getGraphics();
		
		
		//loading
		Asset.bg_loading = new Image (g.newBitmap("Background/bg_loading.jpg"),0,0);
		// set image source
		Asset.btn_setting = new ImgButton (g.newBitmap("Button/btn_setting.png"),0,0);
		Asset.btn_record = new ImgButton (g.newBitmap("Button/btn_record.png"),0,0);
		Asset.btn_help = new ImgButton (g.newBitmap("Button/btn_help.png"),0,0);
		Asset.btn_about = new ImgButton (g.newBitmap("Button/btn_about.png"),0,0);
		Asset.btn_sound = new ImgButton (g.newBitmap("Button/btn_sound.png"),0,0);
		// set mode image
		Asset.btn_modeAdv = new ImgButton (g.newBitmap("Button/btn_modeAdv.png"),0,0);
		Asset.btn_modeArc = new ImgButton (g.newBitmap("Button/btn_modeArc.png"),0,0);
		Asset.btn_modeClassic = new ImgButton (g.newBitmap("Button/btn_modeClassic.png"),0,0);
		
		
		// set bg main;
		Asset.list_bg = new Image[1];
		for (int i =0; i< Asset.list_bg.length;i++)
		Asset.list_bg[i] = new Image (g.newBitmap("Background/bg_"+ Integer.toString(i)  +".jpg"),0,0);
		
		// bg_board
		Asset.bg_board = new Image (g.newBitmap("Background/bg_board.png"),0,0);
		Asset.bg_main = new Image (g.newBitmap("Background/bg_main.png"),0,0);
		Asset.bg_scoreBoard = new Image (g.newBitmap("Button/board.png"),0,0);
		Asset.bg_nextBlock = new Image (g.newBitmap("Button/bg_nextBlock.png"),0,0);
		
		// select level
		Asset.bg_selectLevel_main = new Image (g.newBitmap("Background/bg_select_lv_main.jpg"),0,0);
		Asset.bg_selectLevel_nav = new Image (g.newBitmap("Background/bg_nav.png"),0,0);
		// icon select level
		Asset.slide_level = new  ImgButton(g.newBitmap("Button/btn_level_info.png"),0,0	);
		Asset.slide_level_lock = new  ImgButton(g.newBitmap("Button/btn_level_info_lock.png"),0,0	);
		Asset.icon_star = new  Image(g.newBitmap("Button/icon_star.png"),0,0	);
		Asset.icon_star_dis = new  Image(g.newBitmap("Button/icon_star_dis.png"),0,0	);
		
		
		// set UI
		Asset.UI_Pause = new ImgButton(g.newBitmap("StateUI/pauseUI.png"),48,120	);
		Asset.UI_GameOver = new ImgButton(g.newBitmap("StateUI/gameOverUI.png"),24,72	);
		Asset.UI_StageClear = new ImgButton(g.newBitmap("StateUI/stateClearUI.png"),24,72	);
		Asset.UI_Exit = new ImgButton(g.newBitmap("StateUI/exitUI.png"),0,0	);
		
		// button game button arrow
		Asset.btn_left = new ImgButton (g.newBitmap("Button/btn_left.png"),0,0);
		Asset.btn_right = new ImgButton (g.newBitmap("Button/btn_right.png"),0,0);
		Asset.btn_rotate = new ImgButton (g.newBitmap("Button/btn_rotate.png"),0,0);
		Asset.btn_down = new ImgButton (g.newBitmap("Button/btn_down.png"),0,0);
		Asset.btn_pause = new ImgButton (g.newBitmap("Button/btn_pause.png"),0,0);
		
		// button game menu Button
		Asset.btn_mainMenu = new ImgButton (g.newBitmap("Button/btn_mainMenu.png"),0,0);
		Asset.btn_newGame = new ImgButton (g.newBitmap("Button/btn_newGame.png"),0,0);
		Asset.btn_nextStage = new ImgButton (g.newBitmap("Button/btn_nextStage.png"),0,0);
		Asset.btn_menuStage = new ImgButton (g.newBitmap("Button/btn_menuStage.png"),0,0);
		Asset.btn_playAgain = new ImgButton (g.newBitmap("Button/btn_playAgain.png"),0,0);
		Asset.btn_yes = new ImgButton (g.newBitmap("Button/btn_yes.png"),0,0);
		Asset.btn_no = new ImgButton (g.newBitmap("Button/btn_no.png"),0,0);
		
		// block
		Asset.block_blue = new Image (g.newBitmap("Block/block_blue.png"),0,0);
		Asset.block_green = new Image (g.newBitmap("Block/block_green.png"),0,0);
		Asset.block_red = new Image (g.newBitmap("Block/block_red.png"),0,0);
		Asset.block_purple = new Image (g.newBitmap("Block/block_purple.png"),0,0);
		Asset.block_white = new Image (g.newBitmap("Block/block_white.png"),0,0);
		Asset.block_yellow = new Image (g.newBitmap("Block/block_yellow.png"),0,0);
		Asset.block_orange = new Image (g.newBitmap("Block/block_orange.png"),0,0);
		Asset.block_null = new Image (g.newBitmap("Block/block_null.png"),0,0);
		Asset.block_map = new Image(g.newBitmap("Block/block_map.png"), 0, 0);
		
		//items
		Asset.item_boomb = new Image (g.newBitmap("Items/item_boomb.png"),0,0);
		Asset.item_rocket = new Image (g.newBitmap("Items/item_rocket.png"),0,0);
		Asset.item_dynamite = new Image (g.newBitmap("Items/item_dynamite.png"),0,0);
		
		// icon
		Asset.icon_boomb = new ImgButton (g.newBitmap("Items/icon_boomb.png"),0,0);
		Asset.icon_dynamite = new ImgButton (g.newBitmap("Items/icon_dynamite.png"),0,0);
		Asset.icon_rocket = new ImgButton (g.newBitmap("Items/icon_rocket.png"),0,0);
		
		//
		Asset.icon_music = new ImgButton (g.newBitmap("Button/icon_music.png"),0,0);
		Asset.icon_sound = new ImgButton (g.newBitmap("Button/icon_sound.png"),0,0);
		Asset.icon_music_dis = new ImgButton (g.newBitmap("Button/icon_music_dis.png"),0,0);
		Asset.icon_sound_dis = new ImgButton (g.newBitmap("Button/icon_sound_dis.png"),0,0);
		
		
		// number
		Asset.number= new  Image[11];
		for (int i =0;i<=9;i++){
			Asset.number[i] = new Image (g.newBitmap("Number/n_"+ Integer.toString(i)+".png"),0,0);
		}
		
		// load hai cham
		Asset.number[10] = new Image (g.newBitmap("Number/db_dot.png"),0,0);
	
		
		// number level

		Asset.numberLevel = new  Image[10];
		for (int i =0;i<=9;i++){
	//System.out.println("Number/lv_"+ Integer.toString(i)+".png");
			Asset.numberLevel[i] = new Image (g.newBitmap("Number/lv_"+ Integer.toString(i)+".png"),0,0);
		}
		
		// bonus
		Asset.bonusImg = new  Image[5];
		for (int i =3;i<=4;i++){
		Asset.bonusImg[i] = new Image (g.newBitmap("Button/bonusX"+ Integer.toString(i)+".png"),0,0);
		}
		
		
		
		// load sound
		Asset.sound_endFall = new Sound ((Context) game, R.raw.endfall);
//		/Asset.sound_ready = new Sound ((Context) game, R.raw.ready);
		Asset.sound_fall = new Sound ((Context) game, R.raw.fall);
		Asset.sound_endFall = new Sound ((Context) game, R.raw.endfall);
		Asset.sound_move = new Sound ((Context) game, R.raw.move);
		Asset.sound_cleanRow = new Sound ((Context)game, R.raw.cleanrow);
		Asset.sound_gameOver = new Sound ((Context)game, R.raw.game_over);
		Asset.sound_nextLevel = new Sound ((Context)game, R.raw.next_level);
		Asset.sound_star = new Sound ((Context)game, R.raw.sound_star);
		Asset.sound_stageClean = new Sound ((Context)game, R.raw.stage_clean);
		// load track
		Asset.bg_track = new Music((Context) game, R.raw.bg_track);
		
		// set main menuScreem
		game.setScreen(new MainMenu(game));
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
