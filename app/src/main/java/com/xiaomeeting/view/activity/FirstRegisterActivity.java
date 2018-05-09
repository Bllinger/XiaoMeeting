package com.xiaomeeting.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xiaomeeting.IView.IFirstRegisterView;
import com.xiaomeeting.R;
import com.xiaomeeting.presenter.FirstRegisterPresenter;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.utils.ToastUtil;
import com.xiaomeeting.view.fragment.SecondFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Blinger on 2018/5/6.
 */

public class FirstRegisterActivity extends BaseActivity implements IFirstRegisterView {

    @BindView(R.id.edit_sNum)
    EditText editSNum;
    @BindView(R.id.edit_sName)
    EditText editSName;
    @BindView(R.id.next_button)
    Button nextButton;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private SecondFragment secondFragment;
    private FirstRegisterPresenter firstRegisterPresenter;
    private int status;
    private String info;
    private LinearLayout linearLayout;
    @Override
    public void onFirstRegisterSuccess(String msg,int status) {
        ToastUtil.showToast(getApplicationContext(),msg);
        this.status = status;
        //
    }

    @Override
    public void onFirstRegisterFailure(String msg,int status) {
        ToastUtil.showToast(getApplicationContext(),msg);
        this.status = status;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_firstregister;
    }

    @Override
    protected Presenter getPresenter() {
        return firstRegisterPresenter;
    }

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {

        linearLayout = (LinearLayout)findViewById(R.id.first_register_line);
        firstRegisterPresenter = new FirstRegisterPresenter(this,this);

        secondFragment = new SecondFragment();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.next_button)
    public void onViewClicked() {
        String sNum = editSNum.getText().toString();
        String sName = editSName.getText().toString();

        firstRegisterPresenter.FirstRegister(sNum,sName);
        //status = 1;
        switch (status){
            case 1:
                ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
                layoutParams.width=0;
                layoutParams.height=0;

                fragmentManager = getFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.second_register_fragment,secondFragment);
                fragmentTransaction.commit();
                break;
            default:
                ToastUtil.showToast(getApplicationContext(),info);

        }


    }
}
