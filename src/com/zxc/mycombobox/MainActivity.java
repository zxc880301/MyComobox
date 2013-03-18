package com.zxc.mycombobox;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.zxc.mycombobox.view.EditSpinner;
import com.zxc.mycombobox.view.EditSpinner.ListViewItemSelectedListener;

public class MainActivity extends Activity {

	protected static final String TAG = "EditSpinner";
	private EditSpinner editSp;
	private Button btnSubmit;
	private TextView txtShow;
	private String[] data = {	"��������", "���ι�ˮ ", "��������", "��������", "ʳƷ����", "��������", "��ʳ����", "ס�޽���", "ϴԡ����"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	private void init() {
		editSp = (EditSpinner) this.findViewById(R.id.editSpinner);
		editSp.setData(data);
		editSp.setText("��������");
		editSp.setOnListViewItemSelectedListener(new ListViewItemSelectedListener() {
			
			@Override
			public void onItemSelected(int position) {
				Log.d(TAG, ""+position);
			}
		});
		txtShow = (TextView) this.findViewById(R.id.txt_show);
		btnSubmit = (Button) this.findViewById(R.id.btn_submit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				txtShow.setText(editSp.getText());
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
