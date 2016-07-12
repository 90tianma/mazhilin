package com.zhuoxin.treasure.user.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.zhuoxin.treasure.R;
import com.zhuoxin.treasure.user.Constant;
import com.zhuoxin.treasure.user.User;
import com.zhuoxin.treasure.user.account.HomeActivity;
import com.zhuoxin.treasure.user.login.AlertDialogFragment;
import com.zhuoxin.treasure.utils.ActivityUtils;
import com.zhuoxin.treasure.utils.RegexUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends MvpActivity<RegisterView,RegisterPresenter> implements RegisterView {
    private ActivityUtils activityUtils;
    private String name,psw,rePsw;
    private ProgressDialog progressDialog;
    @Bind(R.id.register_toolbar)
    Toolbar toolbar;
    @Bind(R.id.register_btn_register)
    Button register;
    @Bind(R.id.register_name)
    EditText etName;
    @Bind(R.id.register_psw)     EditText etPsw;
    @Bind(R.id.register_rePsw)   EditText etRePsw;
    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        activityUtils=new ActivityUtils(this);
    }
    /*设置toolbar返回点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // 当布局发生改变时来回调的方法(如每次setContentView后)
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");
        etName.addTextChangedListener(tWatcher);
        etPsw.addTextChangedListener(tWatcher);
        etRePsw.addTextChangedListener(tWatcher);

    }
    /*EditText输入发生变化的监听*/
    TextWatcher tWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        /*当输入框输入信息发生变化时执行*/
        @Override
        public void afterTextChanged(Editable editable) {
            name=etName.getText().toString();
            psw=etPsw.getText().toString();
            rePsw=etRePsw.getText().toString();
            boolean canRegister=!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(psw) && psw.equals(rePsw);
            register.setEnabled(canRegister);

        }
    };

    /*注册点击事件*/
    @OnClick(R.id.register_btn_register)
    public void register(){
        // 正则进行判断输入的用户名是否有效
        if (RegexUtils.verifyUsername(name) != RegexUtils.VERIFY_SUCCESS) {
            showUsernameError();
            return;
        }
        // 正则进行判断输入的密码是否有效
        if (RegexUtils.verifyPassword(psw) != RegexUtils.VERIFY_SUCCESS) {
            showPasswordError();
            return;
        }
        getPresenter().register(new User(name,psw));
    }
    /*用户名输入错误提示*/
    private void showUsernameError() {
        String msg = getString(R.string.username_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.username_error, msg);
        fragment.show(getSupportFragmentManager(), "showUsernameError");
    }
    /*密码输入错误提示*/
    private void showPasswordError() {
        String msg = getString(R.string.password_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.password_error, msg);
        fragment.show(getSupportFragmentManager(), "showPasswordError");
    }
    /*重写RegisterView 的方法*/
    @Override
    public void showMessage(String msg) {/*设置显示信息*/
        activityUtils.showToast(msg);
    }

    @Override
    public void showProgress() {/*设置注册进度提示信息*/
        activityUtils.hideSoftKeyboard();
        progressDialog = ProgressDialog.show(this, "", "正在后台注册中,请稍后...");
    }

    @Override
    public void hidProgress() {/*隐藏注册进度*/
        if(progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void navigateToHome() {/*设置跳转到主页*/
        activityUtils.startActivity(HomeActivity.class);
        finish();
        //发送广播  结束本页面
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(Constant.ACTION_ENTER_HOME));
    }

    @Override
    public void clearEditText() {/*清空输入信息*/
        etName.setText("");
        etPsw.setText("");
        etRePsw.setText("");
        register.setEnabled(false);
    }
}
