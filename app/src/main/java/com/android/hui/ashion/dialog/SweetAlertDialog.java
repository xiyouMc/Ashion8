package com.android.hui.ashion.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.TextView;

import com.android.hui.ashion.R;

/**
 * Created by litonghui on 2015/4/2.
 */
public class SweetAlertDialog extends Dialog implements View.OnClickListener {

    private String mTitleText;
    private TextView mTitleTextView;
    private String mMessageText;
    private TextView mMessageTextView;
    private String mPositiveButtonText;
    private Button mPositiveButton;
    private OnSweetClickListener mPostiveClickListener;
    private String mNegativeButtonText;
    private Button mNegativeButton;
    private OnSweetClickListener mNegativeClickListener;


    public static interface OnSweetClickListener {
        public void onClick(SweetAlertDialog sweetAlertDialog);
    }

    public SweetAlertDialog(Context context) {
        super(context);
    }

    public SweetAlertDialog(Context context, int theme) {
        super(context, R.style.alert_dialog);
    }

    protected void onCreate(Bundle savedInstancStace) {
        super.onCreate(savedInstancStace);
        setContentView(R.layout.layout_sweet_alertdialog);
        initView();
        initText();

    }

    private void initView() {
        mTitleTextView = (TextView) findViewById(R.id.dialog_title_view);
        mMessageTextView = (TextView) findViewById(R.id.dialog_message_view);
        mNegativeButton = (Button) findViewById(R.id.dialog_negative_btn);
        mPositiveButton = (Button) findViewById(R.id.dialog_positive_btn);
        mPositiveButton.setOnClickListener(this);
        mNegativeButton.setOnClickListener(this);
    }

    private void initText() {
        setMessage(mMessageText);
        setTitle(mTitleText);
        setNegativeButton(mNegativeButtonText);
        setPositiveButton(mPositiveButtonText);

    }



    public SweetAlertDialog setNegativeButton(@Nullable String text) {
        mNegativeButtonText = text;
        if (mNegativeButtonText != null && mNegativeButton != null) {
            mNegativeButton.setText(mNegativeButtonText);
        }
        return this;
    }

    public SweetAlertDialog setPositiveButton(@Nullable String text) {
        mPositiveButtonText = text;
        if (mPositiveButtonText != null && mNegativeButton != null) {
            mPositiveButton.setText(mPositiveButtonText);
        }
        return this;
    }

    public SweetAlertDialog setTitle(@Nullable String text) {
        mTitleText = text;
        if (mTitleText != null && mTitleTextView != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }

    public SweetAlertDialog setMessage(@Nullable String text) {
        mMessageText = text;
        if (mMessageText != null && mMessageTextView != null) {
            mMessageTextView.setText(mMessageText);
        }
        return this;
    }

    public SweetAlertDialog setPositiveClickListener(OnSweetClickListener listener) {
        mPostiveClickListener = listener;
        return this;
    }

    public SweetAlertDialog setNegativeClickListener(OnSweetClickListener listener) {
        mNegativeClickListener = listener;
        return this;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_negative_btn:
                if (mNegativeClickListener != null) {
                    mNegativeClickListener.onClick(SweetAlertDialog.this);
                } else {
                    SweetAlertDialog.super.dismiss();
                }
                break;
            case R.id.dialog_positive_btn:
                if(mPostiveClickListener!=null){
                    mPostiveClickListener.onClick(SweetAlertDialog.this);
                }else{
                    SweetAlertDialog.super.dismiss();
                }
                break;
        }
    }
}
