package cn.edu.tongji.sse.twitch.gkd.view.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter.ISocialPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.SocialPresenter.SocialPresenterImpl;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private View inflater;
    private ISocialPresenter iSocialPresenter;
    Bitmap[] post_avater;
    String[] post_name;
    String[] post_content;
    Bitmap[] post_photo;
    String[] post_time;
    boolean[] post_likes;
    int[] postLikesnum;
    String username;

    //构造方法，传入数据
    public RecyclerViewAdapter(String username,Context context,Bitmap[] post_avater,
                               String[] post_name,String[] post_content,
                               Bitmap[] post_photo, String[] post_time,
                               boolean[] post_likes,int[] postLikesnum){
        iSocialPresenter=new SocialPresenterImpl(this);
        this.username=username;
        this.context = context;
        this.post_avater=post_avater;
        this.post_name=post_name;
        this.post_content=post_content;
        this.post_photo=post_photo;
        this.post_time=post_time;
        this.post_likes=post_likes;
        this.postLikesnum=postLikesnum;
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
        //Bitmap bitmap = BitmapFactory.decodeFile(post_avater[position]);
        holder.post_avater.setImageBitmap(post_avater[position]);
        holder.post_name.setText(post_name[position]);
        holder.post_content.setText(post_content[position]);
        //Bitmap photoBitmap = BitmapFactory.decodeFile(post_photo[position]);
        holder.post_photo.setImageBitmap(post_photo[position]);
        holder.post_time.setText(post_time[position]);
        if(post_likes[position]){
            holder.post_likes.setBackgroundResource(R.drawable.like_after);
            holder.post_likes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.post_likes.setChecked(isChecked);
                    holder.post_likes.setBackgroundResource(isChecked?R.drawable.like_before:R.drawable.like_after);
                    holder.postLikesnum.setText(isChecked?postLikesnum[position]-1+"":postLikesnum[position]+"");
                }
            });
        }
        else {
            holder.post_likes.setBackgroundResource(R.drawable.like_before);
            holder.post_likes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    holder.post_likes.setChecked(isChecked);
                    holder.post_likes.setBackgroundResource(isChecked?R.drawable.like_after:R.drawable.like_before);
                    holder.postLikesnum.setText(isChecked?postLikesnum[position]+1+"":postLikesnum[position]+"");
                }
            });
        }
        holder.postLikesnum.setText(postLikesnum[position]+"");
        holder.post_likes.setTag(position);
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return post_name.length;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView post_name,post_content,post_time,postLikesnum;
        ImageView post_avater,post_photo;
        ToggleButton post_likes;
        public MyViewHolder(View itemView) {
            super(itemView);
            post_avater=(ImageView) itemView.findViewById(R.id.post_avater);
            post_name=(TextView) itemView.findViewById(R.id.post_name);
            post_content=(TextView) itemView.findViewById(R.id.post_content);
            post_photo=(ImageView) itemView.findViewById(R.id.post_photo);
            post_time=(TextView) itemView.findViewById(R.id.post_time);
            post_likes=(ToggleButton) itemView.findViewById(R.id.post_likes);
            postLikesnum=(TextView) itemView.findViewById(R.id.postLikesnum);

            post_likes.setOnClickListener(RecyclerViewAdapter.this);
        }
    }

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }

    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }

    private OnItemClickListener mOnItemClickListener;//声明自定义的接口

    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();      //getTag()获取数据
        if (mOnItemClickListener != null) {
            switch (v.getId()){
                case R.id.post_likes:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    if(!post_likes[position]){
                        iSocialPresenter.addPostLikes(post_name[position],post_time[position],username);
                        post_likes[position]=true;
                    }
                    else{
                        iSocialPresenter.deletePostLikes(post_name[position],post_time[position],username);
                        post_likes[position]=false;
                    }
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
}
