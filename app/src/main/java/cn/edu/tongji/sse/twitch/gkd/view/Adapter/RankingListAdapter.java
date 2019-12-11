package cn.edu.tongji.sse.twitch.gkd.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.R;

public class RankingListAdapter extends RecyclerView.Adapter<RankingListAdapter.MyViewHolder> {
    private Context context;
    private View inflater;
    String[] ranking_item_rank;
    int[] ranking_item_avater;
    String[] ranking_item_name;

    //构造方法，传入数据
    public RankingListAdapter(Context context,String[] ranking_item_rank, int[] ranking_item_avater,
                           String[] ranking_item_name){
        this.context = context;
        this.ranking_item_rank=ranking_item_rank;
        this.ranking_item_avater=ranking_item_avater;
        this.ranking_item_name=ranking_item_name;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.ranking_list_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将数据和控件绑定
        holder.ranking_item_rank.setText(ranking_item_rank[position]);
        holder.ranking_item_avater.setImageResource(ranking_item_avater[position]);
        holder.ranking_item_name.setText(ranking_item_name[position]);
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return ranking_item_name.length;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ranking_item_name,ranking_item_rank;
        ImageView ranking_item_avater;
        public MyViewHolder(View itemView) {
            super(itemView);
            ranking_item_rank=(TextView) itemView.findViewById(R.id.ranking_item_rank);
            ranking_item_avater=(ImageView) itemView.findViewById(R.id.ranking_item_avater);
            ranking_item_name=(TextView) itemView.findViewById(R.id.ranking_item_name);
        }
    }
}

