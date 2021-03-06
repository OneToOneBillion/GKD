package cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.edu.tongji.sse.twitch.gkd.bean.Follow;
import cn.edu.tongji.sse.twitch.gkd.bean.Followed;
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
                    BmobQuery<Follow> followBmobQuery=new BmobQuery<>();
                    followBmobQuery.findObjects(new FindListener<Follow>() {
                        @Override
                        public void done(List<Follow> followList, BmobException e) {
                            for(int i=0;i<followList.size();i++){
                                for(int j=0;j<followList.get(i).getaFollowername().size();j++){
                                    if(userID.equals(followList.get(i).getaFollowername().get(j))){
                                        Follow follow=new Follow();
                                        follow.setsUsername(followList.get(i).getsUsername());

                                        ArrayList<String> aFollowername=new ArrayList<>();
                                        aFollowername=followList.get(i).getaFollowername();
                                        aFollowername.set(j,userID);
                                        follow.setaFollowername(aFollowername);

                                        ArrayList<String> aFollowerIcon=new ArrayList<>();
                                        aFollowerIcon=followList.get(i).getaFollowerIcon();
                                        aFollowerIcon.set(j,savater);
                                        follow.setaFollowerIcon(aFollowerIcon);
                                        follow.update(followList.get(i).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {

                                            }
                                        });
                                    }
                                }
                            }
                        }
                    });
                    BmobQuery<Followed> followedBmobQuery=new BmobQuery<>();
                    followedBmobQuery.findObjects(new FindListener<Followed>() {
                        @Override
                        public void done(List<Followed> followedList, BmobException e) {
                            for(int i=0;i<followedList.size();i++){
                                for(int j=0;j<followedList.get(i).getaFollowername().size();j++){
                                    if(userID.equals(followedList.get(i).getaFollowername().get(j))){
                                        Followed followed=new Followed();
                                        followed.setsUsername(followedList.get(i).getsUsername());

                                        ArrayList<String> aFollowername=new ArrayList<>();
                                        aFollowername=followedList.get(i).getaFollowername();
                                        aFollowername.set(j,userID);
                                        followed.setaFollowername(aFollowername);

                                        ArrayList<String> aFollowerIcon=new ArrayList<>();
                                        aFollowerIcon=followedList.get(i).getaFollowerIcon();
                                        aFollowerIcon.set(j,savater);
                                        followed.setaFollowerIcon(aFollowerIcon);
                                        followed.update(followedList.get(i).getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {

                                            }
                                        });
                                    }
                                }
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

