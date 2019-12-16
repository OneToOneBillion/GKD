package cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel;

import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.ChangeInfoPresenter.IChangeInfoPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.ChangeInfoView.IChangeInfoView;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class ChangeInfoModelImpl implements IChangeInfoModel {
    private IChangeInfoView iChangeInfoView;
    private IChangeInfoPresenter iChangeInfoPresenter;

    public ChangeInfoModelImpl(IChangeInfoPresenter IChangeInfoPresenter){
        iChangeInfoPresenter=IChangeInfoPresenter;
    }

    public void saveChange(String userID,String savater,String starget,OnChangeListener onChangeListener){
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",userID);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    User user =new User();
                    user.setUsername(userID);
                    user.setPassword(list.get(0).getPassword());
                    user.setFollow_num(list.get(0).getFollow_num());
                    user.setFollowed_num(list.get(0).getFollowed_num());
                    user.setPost_num(list.get(0).getPost_num());
                    user.setPunchin_num(list.get(0).getPunchin_num());
                    user.setValue("target",starget);
                    user.setValue("avater",savater);
                    user.setRun_times(list.get(0).getRun_times());
                    user.setRun_distance(list.get(0).getRun_distance());
                    user.update(list.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                onChangeListener.changeSuccess(userID,savater,starget);
                            }else{
                                onChangeListener.changeFailed();
                            }
                        }
                    });
                }else{
                    onChangeListener.changeFailed();
                }
            }
        });
    }
}

