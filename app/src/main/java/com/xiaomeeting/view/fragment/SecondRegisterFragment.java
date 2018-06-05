package com.xiaomeeting.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mob.MobSDK;
import com.victor.loading.book.BookLoading;
import com.xiaomeeting.IView.ISecondRegisterView;
import com.xiaomeeting.R;
import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.presenter.User.SecondRegisterPresenter;
import com.xiaomeeting.utils.LogUtil;
import com.xiaomeeting.utils.Md5;
import com.xiaomeeting.utils.ToastUtil;
import com.xiaomeeting.view.activity.HomeActivity;
import com.xiaomeeting.view.MyView.ImgEditText;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Blinger on 2018/5/7.
 */

public class SecondRegisterFragment extends BaseFragment implements ISecondRegisterView {
    @BindView(R.id.edit_phone)
    ImgEditText editPhone;
    @BindView(R.id.edit_verification)
    ImgEditText editVerification;
    @BindView(R.id.edit_password)
    ImgEditText editPassword;
    @BindView(R.id.edit_confirm_password)
    ImgEditText editConfirmPassword;
    @BindView(R.id.register_button)
    Button registerButton;
    @BindView(R.id.gain_code)
    Button gainCode;
    @BindView(R.id.wait_time)
    TextView waitTime;
    @BindView(R.id.bookloading_sec)
    BookLoading bookloadingSec;
    @BindView(R.id.bookloading_line_sec)
    LinearLayout bookloadingLineSec;


    private int time = 60;
    //private Context context;

    private int flag;
    private String myPhone;
    private String myCode;
    private final String TAG = LogUtil.createTag(this);
    private SecondRegisterPresenter secondRegisterPresenter;
    private boolean isHidden = false;
    private boolean ConfirmIsHidden = false;
    private String cookie;
    private boolean isFirst;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_second_register;
    }

    @Override
    protected void initView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        secondRegisterPresenter = new SecondRegisterPresenter(MyApplication.getContext(), this);
        bookloadingLineSec.setVisibility(View.GONE);
        MobSDK.init(MyApplication.getContext());
        waitTime.setVisibility(View.GONE);

        editPhone.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                editPhone.setText(null);
            }
        });

        editVerification.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                editVerification.setText(null);
            }
        });

        editPassword.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                if (isHidden) {
                    //设置EditText文本为可见的
                    editPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editPassword.setRightDrawable(getResources().getDrawable(R.drawable.eye));
                } else {
                    //设置EditText文本为隐藏的
                    editPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editPassword.setRightDrawable(getResources().getDrawable(R.drawable.eye1));
                }
                isHidden = !isHidden;
                editPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = editPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
        editConfirmPassword.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                if (ConfirmIsHidden) {
                    //设置EditText文本为可见的
                    editConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editConfirmPassword.setRightDrawable(getResources().getDrawable(R.drawable.eye));
                } else {
                    //设置EditText文本为隐藏的
                    editConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editConfirmPassword.setRightDrawable(getResources().getDrawable(R.drawable.eye1));
                }
                ConfirmIsHidden = !ConfirmIsHidden;
                editConfirmPassword.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = editConfirmPassword.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences("cookie", Context.MODE_PRIVATE);
            cookie = sharedPreferences.getString("cookie", "");
            LogUtil.d(TAG + "____getCookie", cookie);
        }
    };

    Handler handlerText = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtil.d(TAG + "___thread:", Thread.currentThread().getName());
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
            } else {
                editVerification.setText("");
                waitTime.setText("提示信息");
                time = 60;
                waitTime.setVisibility(View.GONE);
                gainCode.setVisibility(View.VISIBLE);
            }
        }
    };

    private void reminderText() {
        handlerText.sendEmptyMessageDelayed(1, 1000);
    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理成功得到验证码的结果
                    // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                    reminderText();
                    handlerToast.sendEmptyMessage(1);
                } else {
                    // TODO 处理错误的结果
                    //Log.e("获取错误",""+result);
                    handlerToast.sendEmptyMessage(0);
                }

            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    Handler handlerToast = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (isFirst) {
                if (msg.what == 1) {
                    LogUtil.d(TAG + ": info :", "ok");
                    ToastUtil.getInstance().showSuccess(MyApplication.getContext(), "验证码已发送");
                } else {
                    LogUtil.d(TAG + ": info ", "failue");
                    ToastUtil.getInstance().showFailue(MyApplication.getContext(), "获取失败");
                }
                isFirst = false;
            }
        }
    };


    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, String phone, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    flag = 1;
                    //Log.d("返回消息",""+flag);
                } else {
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
        if (bookloadingSec.isStart()) {
            bookloadingSec.stop();
            bookloadingLineSec.setVisibility(View.GONE);
        }
        ToastUtil.getInstance().showSuccess(MyApplication.getContext(), msg);
        LogUtil.d(TAG + "___onRegisterSuccess", "注册成功");
        Intent intent = new Intent(MyApplication.getContext(), HomeActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRegisterFailure(String msg) {
        if (bookloadingSec.isStart()) {
            bookloadingSec.stop();
            bookloadingLineSec.setVisibility(View.GONE);
        }

        ToastUtil.getInstance().showFailue(MyApplication.getContext(), msg);
    }


    @OnTextChanged(value = R.id.edit_verification, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void editTextChangeAfter(Editable editable) {
        //调用MobSMS
        this.myCode = editable.toString().trim();
        if (myCode.length() == 6) {
            submitCode("86", myPhone, myCode);
        }
    }


    @OnClick({R.id.gain_code, R.id.register_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.gain_code:
                isFirst = true;
                myPhone = editPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(myPhone)) {
                    if (myPhone.length() == 11) {
                        sendCode("86", myPhone);
                        editVerification.requestFocus();
                    } else {
                        ToastUtil.getInstance().showFailue(MyApplication.getContext(), "请输入完整的电话号码");
                        editPhone.requestFocus();
                    }
                } else {
                    ToastUtil.getInstance().showFailue(MyApplication.getContext(), "请输入你的电话号码");
                    editPhone.requestFocus();
                }
                break;
            case R.id.register_button:
                if (myCode.length() < 6) {
                    flag = 0;
                }
                flag = 1;
                switch (flag) {
                    case 1:
                        String phone = editPhone.getText().toString();
                        String password = editPassword.getText().toString();
                        String confirmPassword = editConfirmPassword.getText().toString();

                        if (password.equals(confirmPassword)) {
                            if (password.length() >= 8) {
                                String passwordMd5 = Md5.md5(password, Constant.key);

                                if (!(passwordMd5 == null)) {
                                    secondRegisterPresenter.SecondRegister(cookie, phone, passwordMd5);
                                    if (!bookloadingSec.isStart()) {
                                        bookloadingLineSec.setVisibility(View.VISIBLE);
                                        bookloadingSec.start();
                                    }
                                    Log.i("Key", passwordMd5);
                                }
                            } else {
                                ToastUtil.getInstance().showFailue(MyApplication.getContext(), "密码长度小于八位");
                            }
                        } else {
                            ToastUtil.getInstance().showFailue(MyApplication.getContext(), "两次密码不一致");
                        }

                        break;
                    case 0:
                        ToastUtil.getInstance().showFailue(MyApplication.getContext(), "请输入完整验证码");
                        break;
                    case -1:
                        ToastUtil.getInstance().showFailue(MyApplication.getContext(), "验证码错误");
                    default:
                        ToastUtil.getInstance().showFailue(MyApplication.getContext(), "异常错误");
                }

                break;
        }
    }


}
