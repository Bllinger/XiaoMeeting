package com.xiaomeeting.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mob.MobSDK;
import com.xiaomeeting.IView.ISecondRegisterView;
import com.xiaomeeting.R;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.presenter.SecondRegisterPresenter;
import com.xiaomeeting.utils.ToastUtil;
import com.xiaomeeting.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Blinger on 2018/5/7.
 */

public class SecondFragment extends BaseFragment implements ISecondRegisterView {
    @BindView(R.id.edit_phone)
    EditText editPhone;
    @BindView(R.id.edit_verification)
    EditText editVerification;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.edit_confirm_password)
    EditText editConfirmPassword;
    @BindView(R.id.register_button)
    Button registerButton;
    Unbinder unbinder;
    @BindView(R.id.gain_code)
    Button gainCode;
    @BindView(R.id.wait_time)
    TextView waitTime;
    Unbinder unbinder1;
    Unbinder unbinder2;
    private int time = 60;
    //private Context context;
    private String msg;
    private int flag ;
    private String myPhone;
    private String myCode;
    private SecondRegisterPresenter secondRegisterPresenter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second_register;
    }

    @Override
    protected void initView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        secondRegisterPresenter = new SecondRegisterPresenter(context, this);

        MobSDK.init(context);

        waitTime.setVisibility(View.GONE);
    }
    Handler handlerText = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                gainCode.setVisibility(View.GONE);
                waitTime.setVisibility(View.VISIBLE);
                if (time > 0) {
                    waitTime.setText("重新发送" + time + "秒");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                } else {
                    waitTime.setText("提示信息");
                    time = 60;
                    waitTime.setVisibility(View.GONE);
                    gainCode.setVisibility(View.VISIBLE);
                }
            }else {
                editVerification.setText("");
                waitTime.setText("提示信息");
                time = 60;
                waitTime.setVisibility(View.GONE);
                gainCode.setVisibility(View.VISIBLE);
            }
        }
    };
    private void reminderText(){
        //gainCode.setVisibility(View.GONE);
        //waitTime.setVisibility(View.VISIBLE);
        handlerText.sendEmptyMessageDelayed(1,1000);
    }
    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    //ToastUtil.showToast(context,"验证码已发送");
                    msg = "验证码已发送";
                    reminderText();

                } else{
                    // TODO 处理错误的结果
                    //Log.e("获取错误",""+result);
                    msg = "获取错误";
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    flag = 1;
                    //Log.d("返回消息",""+flag);
                } else{
                    // TODO 处理错误的结果
                    flag = -1;
                }

            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    protected Presenter getPresenter() {
        return secondRegisterPresenter;
    }


    @Override
    public void onRegisterSuccess(String msg) {
        ToastUtil.showToast(context, msg);
    }

    @Override
    public void onRegisterFailure(String msg) {
        ToastUtil.showToast(context, msg);
    }


    @OnTextChanged(value = R.id.edit_verification, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextChangeAfter(Editable editable) {
        //调用MobSMS
        this.myCode = editable.toString().trim();
        if (myCode.length() == 6) {
            //ToastUtil.showToast(context, "Hello,edit!");
            submitCode("86",myPhone,myCode);
            //isFlag = false;
        }
    }



    @OnClick({R.id.gain_code, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gain_code:
                myPhone = editPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(myPhone)){
                    if (myPhone.length() == 11){
                        sendCode("86",myPhone);
                        ToastUtil.showToast(context,msg);
                        editVerification.requestFocus();
                        //gainCode.setVisibility(View.VISIBLE);
                    }else {
                        ToastUtil.showToast(context,"请输入完整的电话号码");
                        editPhone.requestFocus();
                    }
                }else {
                    ToastUtil.showToast(context,"请输入你的电话号码");
                    editPhone.requestFocus();
                }
                break;
            case R.id.register_button:
                if (myCode.length() < 6){
                    flag = 0;
                }
                switch (flag){
                    case 1:
                        String phone = editPhone.getText().toString();
                        String password = editPassword.getText().toString();
                        String passwordMd5 = secondRegisterPresenter.md5(password, Constant.key);

                        if (!passwordMd5.isEmpty()) {
                            secondRegisterPresenter.SecondRegister(phone, passwordMd5);
                            Log.i("Key", passwordMd5);
                        }
                        ToastUtil.showToast(context, "注册成功");
                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        break;
                    case 0:
                        ToastUtil.showToast(context,"请输入完整验证码");
                        break;
                    case -1:
                        ToastUtil.showToast(context,"验证码错误");
                    default:
                        ToastUtil.showToast(context,"异常错误");
                }

                break;
        }
    }
}
