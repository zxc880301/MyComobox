 package com.zxc.mycombobox.view;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
/**
 * 自定义可输入的Spinner--类次ComboBox
 * @author zxc
 *
 */
public class EditSpinner extends LinearLayout {

	private Context ctx;
	private EditText editContent;
	private Button btnShowLv;
	private ListView lvContent;
	private PopupWindow popView;
	private String[] data;
	private ListViewAdapter dataAdapter;
	private ListViewItemSelectedListener listViewItemSelectedListener;
	
	
	public EditSpinner(Context context) {
		super(context);
		this.ctx = context;
		initChildView();
	}

	public EditSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.ctx = context;
		initChildView();
	}

	/**
	 * 初始化子视图
	 */
	private void initChildView(){
		initEditContentView();
		initBtnShowLvView();
		initLvContent();
	}

	/**
	 * 初始化listView
	 */
	private void initLvContent(){
		lvContent = new ListView(ctx);
		lvContent.setCacheColorHint(Color.TRANSPARENT);
		lvContent.setFadingEdgeLength(0);
		lvContent.setBackgroundColor(Color.WHITE);
		lvContent.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		initDataAdapter();
		lvContent.setAdapter(dataAdapter);
		lvContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(data != null && data.length > 0){
					popView.dismiss();
					listViewItemSelectedListener.onItemSelected(arg2);
					editContent.setText(data[arg2]);			
				}
			}
		});
	}
	
	/**
	 * 设置输入框文字
	 * @param text
	 */
	public void setText(String text){
		editContent.setText(text);
	}
	
	/**
	 * 获取输入框文字
	 * @return
	 */
	public String getText(){
		return editContent.getText().toString() ;
	}
	
	/**
	 * 设置下拉列表数据
	 * @param data 数据
	 */
	public void setData(String[] data){
		this.data = data;
		if(dataAdapter != null) dataAdapter.notifyDataSetChanged();
	}
	
	/**
	 * 初始化数据适配器
	 */
	private void initDataAdapter(){
		dataAdapter = new ListViewAdapter(ctx);
	}
	
	
	/**
	 * 初始化选择按钮
	 */
	private void initBtnShowLvView(){
		btnShowLv = new Button(ctx);
		btnShowLv.setText("选择");
		btnShowLv.setLayoutParams(getLayoutParam(new int[]{0,4,0,0}, 2.5f));
		btnShowLv.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showPopWindow();
				}
		});
		this.addView(btnShowLv);
		
	}
	
	/**
	 * 初始化输入控件
	 */
	private void initEditContentView(){
		editContent = new EditText(ctx);
		editContent.setHint("请输入...");
		editContent.setLayoutParams(getLayoutParam(new int[]{5,7,0,0}, 1));
		this.addView(editContent);
	}
	
	
	
	/**
	 * 获取子视图布局参数
	 * @return 视图布局参数
	 */
	private LinearLayout.LayoutParams getLayoutParam(int[] margin, float weight){
		LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParam.setMargins(margin[0], margin[1], margin[2], margin[3]);
		layoutParam.weight = weight;
		return layoutParam;
	}
	
	/**
	 * 显示下拉列表
	 */
	private void showPopWindow(){
		if(popView == null){
			popView = new PopupWindow(lvContent, EditSpinner.this.getWidth(), 300);//LayoutParams.WRAP_CONTENT);
            //需要顺利让PopUpWindow dimiss；PopUpWindow的背景不能为空。
			popView.setBackgroundDrawable(new BitmapDrawable());
			//获得焦点，并且在调用setFocusable（true）方法后，可以通过Back(返回)菜单使PopUpWindow dimiss
			popView.setFocusable(true);  
            //点击PopUpWindow外面的控件也可以使得PopUpWindow dimiss。
			popView.setOutsideTouchable(true);
			popView.showAsDropDown(EditSpinner.this, 0, 0);
			 }else if(popView.isShowing()){
				 popView.dismiss();
			 }else{
				 popView.showAsDropDown(EditSpinner.this);
		}
	}
	
/**
 * listView 自定义适配器
 * @author zxc
 *
 */
	 class ListViewAdapter extends BaseAdapter {
       private LayoutInflater inflate;
       public ListViewAdapter(Context context) {        	
       	inflate = LayoutInflater.from(context);
       }

       @Override
       public int getCount() {
           return data == null ? 0 : data.length;
       }

       @Override
       public Object getItem(int position) {
           return data == null ? 0 : data[position];
       }

       @Override
       public long getItemId(int position) {
           return position;
       }

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
           TextView textview = null;
           if(convertView==null){
               convertView	= inflate.inflate(android.R.layout.simple_list_item_1, null);
               textview = (TextView)convertView.findViewById(android.R.id.text1);
               convertView.setTag(textview);
           }else{
           	textview = (TextView) convertView.getTag();
           }
           textview.setText(data[position]);
           return convertView;
		}
   }
	 
	 /**
	  * listview选择监听接口
	  */
	 public interface ListViewItemSelectedListener{
		 void onItemSelected(int position);
	 }
	 
	 /**
	  * 设置监听 
	  */
	 public void setOnListViewItemSelectedListener(ListViewItemSelectedListener listViewItemSelectedListener){
		 this.listViewItemSelectedListener = listViewItemSelectedListener;
	 }
	 
}
