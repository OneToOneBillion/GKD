package cn.edu.tongji.sse.twitch.gkd.presenter.ChangeInfoPresenter;

import cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel.ChangeInfoModelImpl;
import cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel.IChangeInfoModel;
import cn.edu.tongji.sse.twitch.gkd.view.ChangeInfoView.IChangeInfoView;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.IPersonalView;

public class ChangeInfoPresenterImpl implements IChangeInfoPresenter, IChangeInfoModel.OnChangeListener {
    private IChangeInfoView iChangeInfoView;
    private IChangeInfoModel iChangeInfoModel;
    private IPersonalView iPersonalView;

    public ChangeInfoPresenterImpl(IChangeInfoView IChangeInfoView){
        iChangeInfoView=IChangeInfoView;
        iChangeInfoModel=new ChangeInfoModelImpl(this);
    }

    public void saveChangeInfo(String userID,String avater,String target){
        iChangeInfoModel.saveChange(userID,avater,target,this);
    }

    public void changeSuccess(String userID,String avater,String target){
        iPersonalView.setTarget(target);
        iPersonalView.setAvater(avater);
    }
    public void changeFailed(){

    }
}
