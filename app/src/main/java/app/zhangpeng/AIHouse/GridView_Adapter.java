package app.zhangpeng.AIHouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import app.zhangpeng.greenhouse.R;

/**
 * Created by zhangpeng on 2017-02-12.
 */

public class GridView_Adapter extends BaseAdapter {
    private Context context=null;
    private String data[]=null;
    private int imgId[]=null;
    private LayoutInflater mInflater;
    public GridView_Adapter(Context context, String[] data, int[] imgId) {
        this.context = context;
        this.data = data;
        this.imgId = imgId;
        mInflater=LayoutInflater.from(context);
    }
    private class Holder{
        public ImageView item_img;
        public TextView item_tex;
        public ImageView getItem_img(){
            return item_img;
        }
        public void setItem_img(ImageView item_img) {
            this.item_img = item_img;
        }
        public TextView getItem_tex(){
            return item_tex;
        }
        public void setItem_tex(TextView item_tex) {
            this.item_tex = item_tex;
        }

    }
    @Override
    public int getCount(){
        return data.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null){
            view=mInflater.inflate(R.layout.grridview_item,null);
            holder=new Holder();
            holder.item_img=(ImageView)view.findViewById(R.id.imageV);
            holder.item_tex=(TextView)view.findViewById(R.id.Tx);
            view.setTag(holder);
        }else{
            holder=(Holder) view.getTag();
        }
        holder.item_tex.setText(data[position]);
        holder.item_img.setImageResource(imgId[position]);
        return view;
    }

}
