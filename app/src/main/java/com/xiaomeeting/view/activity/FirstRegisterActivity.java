package com.xiaomeeting.view.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.victor.loading.book.BookLoading;
import com.xiaomeeting.IView.IFirstRegisterView;
import com.xiaomeeting.R;
import com.xiaomeeting.app.MyApplication;
import com.xiaomeeting.presenter.Presenter;
import com.xiaomeeting.presenter.User.FirstRegisterPresenter;
import com.xiaomeeting.utils.ToastUtil;
import com.xiaomeeting.view.MyView.ImgEditText;
import com.xiaomeeting.view.fragment.SecondRegisterFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Blinger on 2018/5/6.
 */

public class FirstRegisterActivity extends BaseActivity implements IFirstRegisterView {


    @BindView(R.id.register_next_button)
    Button registerNextButton;
    @BindView(R.id.bookloading)
    BookLoading bookloading;
    private SecondRegisterFragment secondRegisterFragment;
    private FirstRegisterPresenter firstRegisterPresenter;
    private int status;
    private String info;
    private LinearLayout linearLayoutFrame;
    private LinearLayout linearLayout;
    private ImgEditText sNumEdit;
    private ImgEditText sNameEdit;

    @Override
    public void onFirstRegisterSuccess(String msg, int status) {
        if (bookloading.isStart()) {
            bookloading.setVisibility(View.GONE);
            bookloading.stop();
        }

        ToastUtil.getInstance().showSuccess(getApplicationContext(), msg);

        ViewGroup.LayoutParams layoutParams = linearLayoutFrame.getLayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.second_register_fragment, secondRegisterFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onFirstRegisterFailure(String msg, int status) {
        if (bookloading.isStart()) {
            linearLayout.setVisibility(View.GONE);
            bookloading.stop();
        }

        ToastUtil.getInstance().showFailue(MyApplication.getContext(), msg);

        //this.status = status;

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

        sNameEdit = (ImgEditText) this.findViewById(R.id.sNameIet_register);
        sNumEdit = (ImgEditText) this.findViewById(R.id.sNumIet_register);
        linearLayoutFrame = (LinearLayout) this.findViewById(R.id.first_register_line);
        linearLayout = (LinearLayout) this.findViewById(R.id.bookloading_line);
        linearLayout.setVisibility(View.GONE);
        //bookloading.start();
        firstRegisterPresenter = new FirstRegisterPresenter(this, this);
        secondRegisterFragment = new SecondRegisterFragment();

        sNameEdit.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                sNameEdit.setText(null);
            }
        });
        sNumEdit.setDrawableClick(new ImgEditText.IMyRightDrawableClick() {
            @Override
            public void rightDrawableClick() {
                sNumEdit.setText(null);
            }
        });

    }


    @OnClick(R.id.register_next_button)
    public void onViewClicked() {
        if (!bookloading.isStart()) {
            linearLayout.setVisibility(View.VISIBLE);
            bookloading.start();
        }
        linearLayout.setVisibility(View.GONE);
        String sNum = sNumEdit.getText().toString();
        String sName = sNameEdit.getText().toString();

        firstRegisterPresenter.FirstRegister(sNum, sName);
        /*ViewGroup.LayoutParams layoutParams = linearLayoutFrame.getLayoutParams();
        layoutParams.width = 0;
        layoutParams.height = 0;

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.second_register_fragment, secondRegisterFragment);
        fragmentTransaction.commit();*/
    }

}
