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

    public void saveChange(String userID,String neckname,String target,OnChangeListener onChangeListener){
        BmobQuery<User> bmobQuery=new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",userID);
        bmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    User user =new User();
                    user.setUsername(neckname);
                    user.setTarget(target);
                    user.update(list.get(0).getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e==null){
                                Toast.makeText(getApplicationContext(), "修改成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "修改失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getApplicationContext(), "修改失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

