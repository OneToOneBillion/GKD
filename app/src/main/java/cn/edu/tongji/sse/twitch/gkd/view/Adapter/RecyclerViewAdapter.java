package cn.edu.tongji.sse.twitch.gkd.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    private View inflater;
    int[] post_avater;
    String[] post_name;
    String[] post_content;
    String[] post_time;
    int[] post_likes;

    //构造方法，传入数据
    public RecyclerViewAdapter(Context context,int[] post_avater,
                               String[] post_name,String[] post_content,
                               String[] post_time,int[] post_likes){
        this.context = context;
        this.post_avater=post_avater;
        this.post_name=post_name;
        this.post_content=post_content;
        this.post_time=post_time;
        this.post_likes=post_likes;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.post_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将数据和控件绑定
        holder.post_avater.setImageResource(post_avater[position]);
        holder.post_name.setText(post_name[position]);
        holder.post_content.setText(post_content[position]);
        holder.post_time.setText(post_time[position]);
        holder.post_likes.setImageResource(post_likes[position]);
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return post_name.length;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView post_name,post_content,post_time;
        ImageView post_avater,post_likes;
        public MyViewHolder(View itemView) {
            super(itemView);
            post_avater=(ImageView) itemView.findViewById(R.id.post_avater);
            post_name=(TextView) itemView.findViewById(R.id.post_name);
            post_content=(TextView) itemView.findViewById(R.id.post_content);
            post_time=(TextView) itemView.findViewById(R.id.post_time);
            post_likes=(ImageView) itemView.findViewById(R.id.post_likes);
        }
    }
}
