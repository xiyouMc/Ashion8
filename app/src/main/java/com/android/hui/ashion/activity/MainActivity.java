package com.android.hui.ashion.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.style.BulletSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.hui.ashion.R;
import com.android.hui.ashion.activity.BaseActivity;
import com.android.hui.ashion.dialog.SweetAlertDialog;
import com.android.hui.ashion.util.NetworkRequest;
import com.android.hui.ashion.util.ToastUtil;
import com.android.hui.ashion.util.VersionUtil;


public class MainActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mContext = this;
        checkVesrionCode();
        NetworkRequest.init(mContext);
    }

    private void checkVesrionCode() {
        int vc = VersionUtil.versionCode(mContext);
        if (vc < 2) {
            //ToastUtil.showToast(mContext, "update");
            updateVersion();
        } else {
            return;
        }

    }

    private void updateVersion() {

        new SweetAlertDialog(mContext, 1)
                .setTitle(getResources().getString(R.string.dialog_title))
                .setMessage(getResources().getString(R.string.dialog_message))
                .setPositiveButton(getResources().getString(R.string.dialog_positive))
                .setNegativeButton(getResources().getString(R.string.dialog_negative))
                .setNegativeClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        ToastUtil.showToast(mContext, "no undate now");
                        sweetAlertDialog.dismiss();
                    }
                }).setPositiveClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                ToastUtil.showToast(mContext, "update now");
                sweetAlertDialog.dismiss();
            }
        }).show();

       /* final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        CountDownTimer timer = new CountDownTimer(12 * 100, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                builder.setMessage(getResources().getString(R.string.dialog_message));
                builder.setTitle(getResources().getString(R.string.dialog_title));
                builder.setPositiveButton(getResources().getString(R.string.dialog_positive), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton(getResources().getString(R.string.dialog_negative), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            }

            @Override
            public void onFinish() {
                // builder.create().dismiss();
            }
        }.start();*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
