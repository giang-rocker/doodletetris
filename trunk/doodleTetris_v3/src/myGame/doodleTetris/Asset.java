package myGame.doodleTetris;


import myGame.doodleTetris.framework.Image;
import myGame.doodleTetris.framework.ImgButton;
import myGame.doodleTetris.framework.Music;
import myGame.doodleTetris.framework.Sound;

	public class Asset {
		// bgg
		// list random BG
		public static Image[] list_bg;
	public static Image bg_main;
	public static Image bg_loading;
	public static Image bg_gameScreen;
	public static Image bg_autoPlay;
	public static Image bg_about;
	public static Image bg_board;
	public static Image bg_scoreBoard;
	public static Image bg_nextBlock;

	public static Image bg_hiscore;
	public static ImgButton slide_hiscore;
	public static Image bg_highScore;

	
	//select level
	public static Image bg_selectLevel_main;
	public static Image bg_selectLevel_nav;
	//slide select level
	public static ImgButton slide_level;
	public static ImgButton slide_level_lock;
	public static Image icon_star;
	public static Image icon_star_dis;

		
	// UI
	public static ImgButton UI_Pause;
	public static ImgButton UI_Ready;
	public static ImgButton UI_GameOver;
	public static ImgButton UI_StageClear;
	public static ImgButton UI_Exit;
	
	// main menu Button
	public static ImgButton btn_setting;
	public static ImgButton btn_help;
	public static ImgButton btn_record;
	public static ImgButton btn_about;
	public static ImgButton btn_autoPlay_B;
	public static ImgButton btn_genetic;
	public static ImgButton btn_hillClimbing;
	public static ImgButton btn_chromosomeSlide;
	public static ImgButton btn_slow;
	public static ImgButton btn_fast;
	public static ImgButton btn_slow_dis;
	public static ImgButton btn_fast_dis;
	//string
	public static Image str_genetic;
	public static Image str_hillClimbing;
	
	public static ImgButton btn_modeAdv;
	public static ImgButton btn_modeArc;
	public static ImgButton btn_modeClassic;
	public static ImgButton btn_sound;
	
	// game button
	public static ImgButton btn_left;
	public static ImgButton btn_right;
	public static ImgButton btn_down;
	public static ImgButton btn_rotate;
	public static ImgButton btn_pause;
	
	// game menu button
	public static ImgButton btn_mainMenu;
	public static ImgButton btn_newGame;
	public static ImgButton btn_nextStage;
	public static ImgButton btn_playAgain;
	public static ImgButton btn_menuStage;
	public static ImgButton btn_yes;
	public static ImgButton btn_no;
	public static ImgButton btn_autoPlay;
	public static ImgButton btn_disAutoPlay;
	//block
	public static Image block_red;
	public static Image block_green;
	public static Image block_blue;
	public static Image block_white;
	public static Image block_yellow;
	public static Image block_orange;
	public static Image block_purple;
	public static Image block_null;
	public static Image block_map;
	public static Image block_shadow;
	
	// items
	public static Image item_boomb;
	public static Image item_rocket;
	public static Image item_dynamite;

	// icon
	public static ImgButton icon_boomb;
	public static ImgButton icon_dynamite;
	public static ImgButton icon_rocket;
	
	// music
	public static ImgButton icon_sound;
	public static ImgButton icon_music;
	public static ImgButton icon_sound_dis;
	public static ImgButton icon_music_dis;
	
	
	// number - 10 is ":" hai cham
	public static Image[] number;
	public static Image minues;
	
	// number level
	public static Image[] numberLevel;
	
	// bonus
	public static Image[] bonusImg;
	
	// sound
	public static Sound sound_endFall;
	public static Sound	sound_cleanRow;
	public static Sound sound_ready;
	public static Sound sound_gameOver;
	public static Sound sound_go;
	public static Sound sound_move;
	public static Sound sound_rotation;
	public static Sound sound_fall;
	public static Sound sound_nextLevel;
	public static Sound sound_star;
	public static Sound sound_stageClean;
	// music
	
	public static Music bg_track;

}
