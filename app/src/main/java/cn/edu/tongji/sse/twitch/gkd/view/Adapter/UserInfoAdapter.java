package cn.edu.tongji.sse.twitch.gkd.view.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.R;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.MyViewHolder> {
    private Context context;
    private View inflater;
    String[] avater_info;
    String[] name_info;

    //构造方法，传入数据
    public UserInfoAdapter(Context context,String[] avater_info,
                               String[] name_info){
        this.context = context;
        this.avater_info=avater_info;
        this.name_info=name_info;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.user_info_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将数据和控件绑定
        byte [] input = Base64.decode(avater_info[position], Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(input, 0, input.length);
        holder.avater_info.setImageBitmap(bitmap);
        holder.name_info.setText(name_info[position]);
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return name_info.length;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name_info;
        ImageView avater_info;
        public MyViewHolder(View itemView) {
            super(itemView);
            avater_info=(ImageView) itemView.findViewById(R.id.avater_info);
            name_info=(TextView) itemView.findViewById(R.id.name_info);
        }
    }
}
