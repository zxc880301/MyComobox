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
 * �Զ���������Spinner--���ComboBox
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
	 * ��ʼ������ͼ
	 */
	private void initChildView(){
		initEditContentView();
		initBtnShowLvView();
		initLvContent();
	}

	/**
	 * ��ʼ��listView
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
	 * �������������
	 * @param text
	 */
	public void setText(String text){
		editContent.setText(text);
	}
	
	/**
	 * ��ȡ���������
	 * @return
	 */
	public String getText(){
		return editContent.getText().toString() ;
	}
	
	/**
	 * ���������б�����
	 * @param data ����
	 */
	public void setData(String[] data){
		this.data = data;
		if(dataAdapter != null) dataAdapter.notifyDataSetChanged();
	}
	
	/**
	 * ��ʼ������������
	 */
	private void initDataAdapter(){
		dataAdapter = new ListViewAdapter(ctx);
	}
	
	
	/**
	 * ��ʼ��ѡ��ť
	 */
	private void initBtnShowLvView(){
		btnShowLv = new Button(ctx);
		btnShowLv.setText("ѡ��");
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
	 * ��ʼ������ؼ�
	 */
	private void initEditContentView(){
		editContent = new EditText(ctx);
		editContent.setHint("������...");
		editContent.setLayoutParams(getLayoutParam(new int[]{5,7,0,0}, 1));
		this.addView(editContent);
	}
	
	
	
	/**
	 * ��ȡ����ͼ���ֲ���
	 * @return ��ͼ���ֲ���
	 */
	private LinearLayout.LayoutParams getLayoutParam(int[] margin, float weight){
		LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		layoutParam.setMargins(margin[0], margin[1], margin[2], margin[3]);
		layoutParam.weight = weight;
		return layoutParam;
	}
	
	/**
	 * ��ʾ�����б�
	 */
	private void showPopWindow(){
		if(popView == null){
			popView = new PopupWindow(lvContent, EditSpinner.this.getWidth(), 300);//LayoutParams.WRAP_CONTENT);
            //��Ҫ˳����PopUpWindow dimiss��PopUpWindow�ı�������Ϊ�ա�
			popView.setBackgroundDrawable(new BitmapDrawable());
			//��ý��㣬�����ڵ���setFocusable��true�������󣬿���ͨ��Back(����)�˵�ʹPopUpWindow dimiss
			popView.setFocusable(true);  
            //���PopUpWindow����Ŀؼ�Ҳ����ʹ��PopUpWindow dimiss��
			popView.setOutsideTouchable(true);
			popView.showAsDropDown(EditSpinner.this, 0, 0);
			 }else if(popView.isShowing()){
				 popView.dismiss();
			 }else{
				 popView.showAsDropDown(EditSpinner.this);
		}
	}
	
/**
 * listView �Զ���������
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
	  * listviewѡ������ӿ�
	  */
	 public interface ListViewItemSelectedListener{
		 void onItemSelected(int position);
	 }
	 
	 /**
	  * ���ü��� 
	  */
	 public void setOnListViewItemSelectedListener(ListViewItemSelectedListener listViewItemSelectedListener){
		 this.listViewItemSelectedListener = listViewItemSelectedListener;
	 }
	 
}
