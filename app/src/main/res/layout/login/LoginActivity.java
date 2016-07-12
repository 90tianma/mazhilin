package zhuoxin.com.myapp.user.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zhuoxin.com.myapp.R;
import zhuoxin.com.myapp.fragment.AlertDialogFragment;
import zhuoxin.com.myapp.treasure.HomeActivity;
import zhuoxin.com.myapp.user.Constant;
import zhuoxin.com.myapp.user.User;
import zhuoxin.com.myapp.utils.ActivityUtils;
import zhuoxin.com.myapp.utils.RegexUtils;

public class LoginActivity extends MvpActivity<zhuoxin.com.myapp.user.login.LoginView,zhuoxin.com.myapp.user.login.LoginPresenter>  /* AppCompatActivity*/ implements zhuoxin.com.myapp.user.login.LoginView {
    @Bind(R.id.login_toolbar)Toolbar toolbar;
    @Bind(R.id.login_btn_login)Button login;
    @Bind(R.id.login_et_name)   EditText etName;
    @Bind(R.id.login_et_pwd) EditText etPwd;
    private String name;/*输入的用户名*/
    private String psw;/*输入的密码*/

    private ProgressDialog progressDialog;
    private ActivityUtils activityUtils;

    @NonNull
    @Override
    public zhuoxin.com.myapp.user.login.LoginPresenter createPresenter() {
        return new zhuoxin.com.myapp.user.login.LoginPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityUtils=new ActivityUtils(this);
        ButterKnife.bind(this);
        /*设置toolbar */
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("登录");

        /*添加对EditText的监听*/
        etName.addTextChangedListener(etWatcher);
        etPwd.addTextChangedListener(etWatcher);
    }
    /*设置toolbar 返回按钮点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * EditText的监听
     */
    TextWatcher etWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        /*EditText输入框内容发生变化执行的方法*/
        @Override
        public void afterTextChanged(Editable editable) {
            name=etName.getText().toString();
            psw=etPwd.getText().toString();
            boolean canLogin=!(TextUtils.isEmpty(name)|| TextUtils.isEmpty(psw));
            login.setEnabled(canLogin);
        }
    };

    /*登录点击事件*/
    @OnClick(R.id.login_btn_login)
    public void login(){
        Log.i("tag","执行登录");
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
    //    new LoginPresenter().login(new User(name,psw));
        getPresenter().login(new User(name,psw));

    }
    /*设置用户名输入错误提示*/
    private void showUsernameError() {
        String msg = getString(R.string.username_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.username_error, msg);
        fragment.show(getSupportFragmentManager(), "showUsernameError");
    }
    /*设置密码输入错误提示*/
    private void showPasswordError() {
        String msg = getString(R.string.password_rules);
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(R.string.password_error, msg);
        fragment.show(getSupportFragmentManager(), "showPasswordError");
    }



    /*重写LoginView的方法*/
        @Override
        public void showProgress() {//设置显示进度
            activityUtils.hideSoftKeyboard();
            progressDialog = ProgressDialog.show(this, "", "正在后台登陆……");
        }
        @Override
        public void hidProgress() {/*设置隐藏进度*/
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
        public void showMessage(String msg) {/*设置提示信息*/
            activityUtils.showToast(msg);
        }

        @Override
        public void clearEditText() {/*清空输入框*/
            etName.setText("");
            etPwd.setText("");
        }
}
