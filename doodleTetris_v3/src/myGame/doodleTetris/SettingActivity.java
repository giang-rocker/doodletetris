package myGame.doodleTetris;

import myGame.doodleTetris.framework.AndroidGame;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {

	boolean music = true;
	boolean sound = true;
	boolean engLanguage =true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		final SeekBar seekbarMusic = (SeekBar) findViewById(R.id.sbMusic);
		final SeekBar seekbarSound =(SeekBar) findViewById(R.id.sbSound);
		
		final CheckBox chkMusic = (CheckBox) findViewById(R.id.chkMusic);
		final CheckBox chkSound = (CheckBox) findViewById(R.id.chkSound);
		
		RadioGroup radGroup =(RadioGroup) findViewById(R.id.radGroupOptionLanguage);
		final RadioButton radEnglish = (RadioButton) findViewById(R.id.languageEng);
		RadioButton radVietnamese =(RadioButton) findViewById(R.id.languageViet);
		
		Button btnSave = (Button) findViewById(R.id.btnSave);
		Button btnReset = (Button) findViewById(R.id.btnReset);
		Button btnMenuGame = (Button) findViewById(R.id.btnMenuGame);
		
		chkMusic.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(chkMusic.isChecked())
					music=true;
				else
					music=false;
			}
		});
		chkSound.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(chkSound.isChecked())
					sound=true;
				else
					sound=false;
			}
		});
		
		seekbarMusic.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			TextView percentVolMusic = (TextView) findViewById(R.id.tvPercentVolMusic);
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				percentVolMusic.setText(String.valueOf(progress)+" %");
			}
		});
		seekbarSound.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			TextView percentVolSound = (TextView) findViewById(R.id.tvPercentVolSound);
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				percentVolSound.setText(String.valueOf(progress)+" %");
			}
		});
		
		radGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(radEnglish.isChecked())
					engLanguage =true;
				else
					engLanguage =false;
				
			}
		});
		
		btnSave.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stu
				//try {
					String data[] = new String[5];
					Setting setting = new Setting(new Game());
					setting.saveSetting(music,Float.parseFloat(seekbarMusic.getProgress()+""), sound,Float.parseFloat(seekbarSound.getProgress()+""), engLanguage);
					Toast.makeText(getBaseContext(), "Đã lưu thành công", 1000).show();
					if(setting.loadSetting()){
						data = setting.getData();
					}
					Toast.makeText(getBaseContext(), "Data: "+data[0]+" "+data[1]+" "+data[2]+" "+data[3]+" "+data[4]+" ", 1000).show();
			//	} catch (Exception e) {
					// TODO: handle exception
					//Toast.makeText(getBaseContext(),"Loi: "+ e+" ", 1000).show();
			//	}
				
			}
		});
	}
	
}
