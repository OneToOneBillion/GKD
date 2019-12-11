package cn.edu.tongji.sse.twitch.gkd.presenter.ChangeInfoPresenter;

import cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel.ChangeInfoModelImpl;
import cn.edu.tongji.sse.twitch.gkd.model.ChangeInfoModel.IChangeInfoModel;
import cn.edu.tongji.sse.twitch.gkd.view.ChangeInfoView.IChangeInfoView;

public class ChangeInfoPresenterImpl implements IChangeInfoPresenter, IChangeInfoModel.OnChangeListener {
    private IChangeInfoView iChangeInfoView;
    private IChangeInfoModel iChangeInfoModel;

    public ChangeInfoPresenterImpl(IChangeInfoView IChangeInfoView){
        iChangeInfoView=IChangeInfoView;
        iChangeInfoModel=new ChangeInfoModelImpl(this);
    }

    public void saveChangeInfo(String userID,String neckname,String target){
        iChangeInfoModel.saveChange(userID,neckname,target,this);
    }

    public void changeSuccess(){

    }
    public void changeFailed(){

    }
}
