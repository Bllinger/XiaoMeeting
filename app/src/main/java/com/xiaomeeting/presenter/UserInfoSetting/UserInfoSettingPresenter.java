package com.xiaomeeting.presenter.UserInfoSetting;

import android.content.Context;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.xiaomeeting.IView.IUserInfoSettingView;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.model.entity.User.Login.UserInfo;
import com.xiaomeeting.model.entity.User.UpdateInfo;
import com.xiaomeeting.model.interfaces.IRespository;
import com.xiaomeeting.model.respository.UserRespository;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.LogUtil;
import com.xiaomeeting.utils.QiNiu.Auth;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Blinger on 2018/6/2.
 */

public class UserInfoSettingPresenter extends Presenter {
    private final String TAG = LogUtil.createTag(this);
    private IUserInfoSettingView iUserInfoSettingView;
    private IRespository iRespository;
    private UserInfo userInfo;
    private String headPath;

    public UserInfoSettingPresenter(Context context, IUserInfoSettingView iUserInfoSettingView, UserInfo userInfo) {
        super(context);
        this.iUserInfoSettingView = iUserInfoSettingView;
        this.userInfo = userInfo;

        this.iRespository = UserRespository.getInstance();
    }

    @Override
    public void destroy() {
        if (updateInfoSubscriber != null) {
            updateInfoSubscriber.unsubscribe();
        }
    }

    //将用户信息更新到数据库
    private void updateDataBase(List<String> infoList) {

        try {
            userInfo.setHeadImageUrl(headPath);
            userInfo.setUserName(infoList.get(0));
            userInfo.setSex(infoList.get(1));
            userInfo.setTelephone(infoList.get(2));
            userInfo.setEmail(infoList.get(3));

            int flag = userInfo.updateAll("studentnum = ?", userInfo.getStudentNum());
            LogUtil.d(TAG + "updateDataBase", "" + flag);
            /*if (){
                LogUtil.d(TAG+"updateDataBase","update success");
                updateService();
            }else {
                LogUtil.d(TAG+"updateDataBase","update failue");
            }*/

        } catch (Exception e) {
            LogUtil.e(TAG + "updateDataBase", e.toString());
            iUserInfoSettingView.updateDataFailue("信息保存错误");
        }

    }

    //将用户信息更新到服务器
    private void updateService() {
        rxJavaExecuter.execute(
                iRespository.getUpdateInfo(userInfo.getToken(), userInfo.getHeadImageUrl(), userInfo.getUserName(), userInfo.getSex(), userInfo.getTelephone(), userInfo.getEmail())
                , updateInfoSubscriber = new UpdateInfoSubscriber()
        );
    }

    //头像上传至七牛云
    public void updateQiNiuService(String imagePath, final List<String> infoList) {
        UploadManager uploadManager = new UploadManager();
        // 设置图片名字
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = "headImage_" + this.userInfo.getStudentNum() + simpleDateFormat.format(new Date());
        try {
            uploadManager.put(imagePath, key, Auth.create(Constant.AccessKey, Constant.SecretKey).uploadToken("xiaomeeting"), new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject res) {
                    // info.error中包含了错误信息，可打印调试
                    // 上传成功后将key值上传到自己的服务器
                    if (info.isOK()) {
                        LogUtil.i(TAG, "token===" + Auth.create(Constant.AccessKey, Constant.SecretKey).uploadToken("xiaomeeting"));
                        headPath = "http://p9sy7o4yb.bkt.clouddn.com/" + key;
                        LogUtil.i(TAG, "complete: " + headPath);
                        iUserInfoSettingView.updateDataSuccess("上传成功");
                        //updateDataBase(infoList);
                    } else {
                        LogUtil.e(TAG + "uploadManager_error", info.error);
                        iUserInfoSettingView.updateDataFailue("头像上传错误");
                    }
                }
            }, null);
        } catch (Exception e) {
            iUserInfoSettingView.updateDataFailue("头像上传错误");
            LogUtil.e(TAG + "___updateQiNiuService", e.toString());
        }
    }

    private UpdateInfoSubscriber updateInfoSubscriber;

    private class UpdateInfoSubscriber extends Subscriber<UpdateInfo> {
        private String msg;
        private int status;

        @Override
        public void onCompleted() {
            LogUtil.d(TAG + "onCompleted()", "请求成功");

            switch (status) {
                case 1:
                    iUserInfoSettingView.updateDataSuccess(msg);
                    break;
                default:
                    iUserInfoSettingView.updateDataFailue(msg);
                    break;
            }
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.e(TAG + "onError", e.toString());
            if (msg == null) {
                msg = "网络错误";
            }

            iUserInfoSettingView.updateDataFailue(msg);
        }

        @Override
        public void onNext(UpdateInfo updateInfo) {
            this.msg = updateInfo.getInfo();
            this.status = updateInfo.getStatus();
        }
    }

}
