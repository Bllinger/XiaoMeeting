package com.xiaomeeting.view.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.victor.loading.book.BookLoading;
import com.xiaomeeting.IView.IUserInfoSettingView;
import com.xiaomeeting.R;
import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.model.entity.User.Login.UserInfo;
import com.xiaomeeting.presenter.UserInfoSetting.UserInfoSettingPresenter;
import com.xiaomeeting.utils.LogUtil;
import com.xiaomeeting.utils.QiNiu.StatusBarUtil;
import com.xiaomeeting.utils.ToastUtil;
import com.xiaomeeting.view.MyView.PickerView;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.app.TakePhotoActivity;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;
import org.devio.takephoto.model.TResult;
import org.devio.takephoto.model.TakePhotoOptions;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Blinger on 2018/6/3.
 */

public class UserInfoSettingActivity extends TakePhotoActivity implements IUserInfoSettingView {
    @BindView(R.id.head_image_setting)
    CircleImageView headImageSetting;
    @BindView(R.id.setting_head_image)
    RelativeLayout settingHeadImage;
    @BindView(R.id.setting_user_name_tv)
    TextView settingUserNameTv;
    @BindView(R.id.setting_user_name)
    RelativeLayout settingUserName;
    @BindView(R.id.setting_sex_tv)
    TextView settingSexTv;
    @BindView(R.id.setting_sex)
    RelativeLayout settingSex;
    @BindView(R.id.setting_phone_tv)
    TextView settingPhoneTv;
    @BindView(R.id.setting_phone)
    RelativeLayout settingPhone;
    @BindView(R.id.setting_email_tv)
    TextView settingEmailTv;
    @BindView(R.id.setting_email)
    RelativeLayout settingEmail;
    @BindView(R.id.setting_school_tv)
    TextView settingSchoolTv;
    @BindView(R.id.setting_school)
    RelativeLayout settingSchool;
    @BindView(R.id.setting_leader_status_tv)
    TextView settingLeaderStatusTv;
    @BindView(R.id.setting_leader_status)
    RelativeLayout settingLeaderStatus;
    private final String TAG = LogUtil.createTag(this);
    @BindView(R.id.save_info_button)
    Button saveInfoButton;
    @BindView(R.id.bookloading_sec)
    BookLoading bookloadingSec;
    @BindView(R.id.bookloading_line_save)
    LinearLayout bookloadingLineSave;
    private Dialog upSex;
    private String UserSex;
    private int flag = 0;

    private UserInfoSettingPresenter presenter;
    private Unbinder unbind;
    private String headFile;
    private List<String> userInfoList;
    private UserInfo userInfo;
    PopupWindow popupWindow;

    TakePhoto takePhoto;
    File file;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        StatusBarUtil.setTransparent(UserInfoSettingActivity.this);

        unbind = ButterKnife.bind(this);

        init(savedInstanceState);
    }

    protected int getLayoutId() {
        return R.layout.activity_userinfo_setting;
    }

    protected UserInfoSettingPresenter getPresenter() {
        return presenter;
    }

    protected void init(@Nullable Bundle savedInstanceState) {
        userInfoList = new ArrayList<String>();
        //初始化TakePhoto
        takePhoto = getTakePhoto();

        bookloadingLineSave.setVisibility(View.GONE);

        if (MyApplication.getLOGINSTATUS() == Constant.LoginStatusSUCCESS) {
            userInfo = DataSupport.findFirst(UserInfo.class);
            LogUtil.d(TAG + "____userInfo", userInfo.getStudentNum() + "___headImage:" + userInfo.getHeadImageUrl());
            presenter = new UserInfoSettingPresenter(this, this, userInfo);
            try {
                if (userInfo.getHeadImageUrl() != null) {
                    Picasso.with(UserInfoSettingActivity.this)
                            .load(userInfo.getHeadImageUrl())
                            .into(headImageSetting);
                }
            } catch (Exception e) {
                LogUtil.e(TAG + "initHead", "" + e);
            }

            settingUserNameTv.setText(userInfo.getUserName());
            settingSexTv.setText(userInfo.getSex());
            settingPhoneTv.setText(userInfo.getTelephone());
            settingEmailTv.setText(userInfo.getEmail());
        }

    }

    @OnClick({R.id.setting_head_image, R.id.setting_user_name, R.id.setting_sex, R.id.setting_phone, R.id.setting_email, R.id.setting_school, R.id.setting_leader_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_head_image:
                PopupWindow();
                LogUtil.d(TAG + "___onViewClicked", "Click HeadImage");
                break;
            case R.id.setting_user_name:
                onCreateEditDialog("昵称");
                LogUtil.d(TAG + "___onViewClicked", "Click Name");
                break;
            case R.id.setting_sex:
                selectDialog(0);
                LogUtil.d(TAG + "___onViewClicked", "Click Sex");
                break;
            case R.id.setting_phone:
                onCreateEditDialog("手机号");
                LogUtil.d(TAG + "___onViewClicked", "Click Phone");
                break;
            case R.id.setting_email:
                onCreateEditDialog("email");
                LogUtil.d(TAG + "___onViewClicked", "Click Email");
                break;
            case R.id.setting_school:
                LogUtil.d(TAG + "___onViewClicked", "Click School");
                break;

        }
    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 5120;
        int width = 100;
        int height = 100;

        CompressConfig config;
        config = new CompressConfig.Builder().setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                //是否保留原图
                .enableReserveRaw(false)
                .create();

        takePhoto.onEnableCompress(config, false);
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setWithOwnGallery(true);
        builder.setCorrectImage(true);

        takePhoto.setTakePhotoOptions(builder.create());

    }

    private CropOptions getCropOptions() {
        int height = 100;
        int width = 100;

        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    private void PopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popupwindow, null);
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);

        final TextView picTV = (TextView) contentView.findViewById(R.id.pop_pic);
        final TextView cameraTV = (TextView) contentView.findViewById(R.id.pop_camera);
        final TextView cancelTV = (TextView) contentView.findViewById(R.id.pop_cancel);

        file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!(file.getParentFile().exists())) {
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
        LogUtil.d(TAG + "___headLocal", "" + file.getPath());
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);

        picTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动相册
                //takePhoto.onPickMultipleWithCrop(1,getCropOptions());
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());

                LogUtil.d(TAG + "___PopupWindow", "点击了相册");
                popupWindow.dismiss();
            }
        });
        cameraTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //启动相机
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                LogUtil.d(TAG + "___PopupWindow", "点击了相机");
                popupWindow.dismiss();
            }
        });

        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消
                popupWindow.dismiss();
            }
        });

        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    private void onCreateEditDialog(final String hintString) {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View nameView = layoutInflater.inflate(R.layout.userinfo_setting_edit, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.changename_edit);
        SpannableString s = new SpannableString(hintString);
        userInput.setHint(s);

        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                if (hintString.equals("昵称")) {
                                    if (userInput.getText().toString().length() <= 10) {
                                        settingUserNameTv.setText(userInput.getText());
                                    } else {
                                        ToastUtil.getInstance().showFailue(MyApplication.getContext(), "昵称最大长度不超过10");
                                    }
                                } else if (hintString.equals("手机号")) {
                                    if (userInput.getText().toString().length() <= 11) {
                                        settingUserNameTv.setText(userInput.getText());
                                    } else {
                                        ToastUtil.getInstance().showFailue(MyApplication.getContext(), "非法手机号");
                                    }
                                } else {
                                    settingUserNameTv.setText(userInput.getText());
                                }
                                dialog.cancel();
                                flag = 1;
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void selectDialog(final int msg) {
        upSex = new Dialog(UserInfoSettingActivity.this, R.style.dialog);
        View defaultView = LayoutInflater.from(this).inflate(
                R.layout.dialog, null);
        PickerView picker = (PickerView) defaultView.findViewById(R.id.pickerView);
        Button finish = (Button) defaultView
                .findViewById(R.id.finish);
        Button cancel = (Button) defaultView
                .findViewById(R.id.cancel);
        List<String> data = new ArrayList<String>();


        switch (msg) {
            case 0:
                data.add("男");
                data.add("女");
                break;

        }
        picker.setData(data);
        upSex.setContentView(defaultView);
        upSex.setCancelable(true);
        upSex.setCanceledOnTouchOutside(false);
        upSex.show();
        WindowManager windowManager = UserInfoSettingActivity.this.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = upSex.getWindow().getAttributes();
        lp.width = (int) (display.getWidth());
        upSex.getWindow().setAttributes(lp);
        upSex.getWindow().setGravity(Gravity.BOTTOM);

        picker.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                UserSex = text;
            }
        });

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upSex.dismiss();
                //将数据保存
                switch (msg) {
                    case 0:
                        settingUserNameTv.setText(UserSex);
                        flag = 1;
                        LogUtil.d(TAG + "___finish.setOnClickListener", "选择了" + UserSex);
                        break;
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upSex.dismiss();
            }
        });

        upSex.setCanceledOnTouchOutside(true);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);

        headFile = result.getImage().getCompressPath();
        LogUtil.d(TAG + "___headLocal:", headFile);
        Glide.with(this)
                .load(new File(headFile))
                .into(headImageSetting);
        flag = 1;
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);

        LogUtil.e(TAG + "___takeFail", "设置头像失败");
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //

        if (presenter == null && getPresenter() != null) {
            presenter = getPresenter();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbind.unbind();

        if (presenter != null) {
            presenter.destroy();
        }
    }

    @OnClick(R.id.save_info_button)
    public void onViewClicked() {
        if (flag == 1) {
            userInfoList.add(headFile);
            userInfoList.add(settingUserNameTv.getText().toString());
            userInfoList.add(settingSexTv.getText().toString());
            userInfoList.add(settingPhoneTv.getText().toString());
            userInfoList.add(settingEmailTv.getText().toString());

            if (!bookloadingSec.isStart()) {
                bookloadingLineSave.setVisibility(View.VISIBLE);
                bookloadingSec.start();
            }

            presenter.updateQiNiuService(headFile, userInfoList);
        } else {
            ToastUtil.getInstance().showFailue(MyApplication.getContext(), "未做修改，不用保存");
        }
    }

    @Override
    public void updateDataSuccess(String msg) {
        if (bookloadingSec.isStart()) {
            bookloadingLineSave.setVisibility(View.GONE);
            bookloadingSec.stop();
        }
        ToastUtil.showToast(MyApplication.getContext(), "保存成功");
        Intent intent = new Intent(UserInfoSettingActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateDataFailue(String msg) {
        if (bookloadingSec.isStart()) {
            bookloadingLineSave.setVisibility(View.GONE);
            bookloadingSec.stop();
        }
        ToastUtil.showToast(MyApplication.getContext(), "保存失败");
    }
}
