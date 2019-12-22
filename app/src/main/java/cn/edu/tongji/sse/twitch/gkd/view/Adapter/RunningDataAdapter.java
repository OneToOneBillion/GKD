package cn.edu.tongji.sse.twitch.gkd.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import cn.edu.tongji.sse.twitch.gkd.R;

public class RunningDataAdapter extends RecyclerView.Adapter<RunningDataAdapter.MyViewHolder> {
    private Context context;
    private View inflater;
    int[] running_item_num;
    double[] running_item_distance;
    long[] running_item_timelength;
    String[] running_item_time;

    //构造方法，传入数据
    public RunningDataAdapter(Context context,int[] running_item_num,
            double[] running_item_distance,
            long[] running_item_timelength,
            String[] running_item_time){
        this.context = context;
        this.running_item_num=running_item_num;
        this.running_item_distance=running_item_distance;
        this.running_item_timelength=running_item_timelength;
        this.running_item_time=running_item_time;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //创建ViewHolder，返回每一项的布局
        inflater = LayoutInflater.from(context).inflate(R.layout.running_data_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(inflater);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //将数据和控件绑定
        holder.running_item_num.setText(running_item_num[position]+"");
        holder.running_item_distance.setText(running_item_distance[position]+"m");
        long run_minute=running_item_timelength[position]/60000;
        long run_second=(running_item_timelength[position]%60000)/1000;
        holder.running_item_timelength.setText(run_minute+"min"+run_second+"s");
        holder.running_item_time.setText(running_item_time[position]);
    }

    @Override
    public int getItemCount() {
        //返回Item总条数
        return running_item_num.length;
    }

    //内部类，绑定控件
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView  running_item_num, running_item_distance, running_item_timelength, running_item_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            running_item_num=(TextView) itemView.findViewById(R.id.running_item_num);
            running_item_distance=(TextView) itemView.findViewById(R.id.running_item_distance);
            running_item_timelength=(TextView) itemView.findViewById(R.id.running_item_timelength);
            running_item_time=(TextView) itemView.findViewById(R.id.running_item_time);
        }
    }
}

