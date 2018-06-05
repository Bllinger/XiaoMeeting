package com.xiaomeeting.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.victor.loading.book.BookLoading;
import com.xiaomeeting.IView.ILoginView;
import com.xiaomeeting.R;
import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.constants.Constant;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.presenter.User.LoginPresenter;
import com.xiaomeeting.utils.LogUtil;
import com.xiaomeeting.utils.Md5;
import com.xiaomeeting.utils.ToastUtil;
import com.xiaomeeting.view.MyView.ImgEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Blinger on 2018/5/13.
 */

public class LoginActivity extends BaseActivity implements ILoginView {
    private final String TAG = LogUtil.createTag(this);
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.forget_pwd_text)
    TextView forgetPwdText;
    @BindView(R.id.register_text)
    TextView registerText;
    @BindView(R.id.bookloading_login)
    BookLoading bookloadingLogin;
    @BindView(R.id.bookloading_login_line)
    LinearLayout bookloadingLoginLine;

    private ImgEditText numIet;
    private LoginPresenter loginPresenter;
    private ImgEditText pwdIet;
    private String uuid;
    private boolean isHidden = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected Presenter getPresenter() {
        return loginPresenter;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

        bookloadingLoginLine.setVisibility(View.GONE);

        numIet = (ImgEditText) this.findViewById(R.id.numIet);
        pwdIet = (ImgEditText) this.findViewById(R.id.pwdIet);
        numIet.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                numIet.setText(null);
            }
        });
        pwdIet.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                if (isHidden) {
                    //设置EditText文本为可见的
                    pwdIet.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    pwdIet.setRightDrawable(getResources().getDrawable(R.drawable.eye));
                } else {
                    //设置EditText文本为隐藏的
                    pwdIet.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    pwdIet.setRightDrawable(getResources().getDrawable(R.drawable.eye1));
                }
                isHidden = !isHidden;
                pwdIet.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = pwdIet.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });

        loginPresenter = new LoginPresenter(this, this);

        Thread thread = new Thread(runnable);
        thread.start();

    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            SharedPreferences sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);
            uuid = sharedPreferences.getString("uuid", "");

            LogUtil.d("UUID", "UUID:" + uuid);
        }
    };

    @Override
    public void LoginSuccess(String msg) {
        if (bookloadingLogin.isStart()) {
            bookloadingLoginLine.setVisibility(View.GONE);
            bookloadingLogin.stop();
        }
        MyApplication.setLOGINSTATUS(Constant.LoginStatusSUCCESS);
        ToastUtil.getInstance().showSuccess(this, msg);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void LoginFailure(String msg) {
        if (bookloadingLogin.isStart()) {
            bookloadingLoginLine.setVisibility(View.GONE);
            bookloadingLogin.stop();
        }
        ToastUtil.getInstance().showFailue(MyApplication.getContext(), msg);
        LogUtil.e(TAG + "___LoginFailure", "登录失败");
    }


    @OnClick({R.id.login_button, R.id.forget_pwd_text, R.id.register_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                LogUtil.d(TAG + "___click", "hello login");
                String sNum = numIet.getText().toString();
                String password = pwdIet.getText().toString();

                if (!(sNum.isEmpty() || password.isEmpty())) {
                    if (!bookloadingLogin.isStart()) {
                        bookloadingLoginLine.setVisibility(View.VISIBLE);
                        bookloadingLogin.start();
                    }
                    String passwordMD5 = Md5.md5(password, Constant.key);
                    LogUtil.d(TAG + "___onViewClicked", passwordMD5);
                    loginPresenter.login(sNum, passwordMD5, uuid);
                } else {
                    ToastUtil.getInstance().showFailue(MyApplication.getContext(), "请输入完整信息");
                }

                break;
            case R.id.forget_pwd_text:
                LogUtil.d(TAG + "___click", "hello forget");
                break;
            case R.id.register_text:
                LogUtil.d(TAG + "___click", "hello register");
                Intent intent = new Intent(MyApplication.getContext(), FirstRegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

}
