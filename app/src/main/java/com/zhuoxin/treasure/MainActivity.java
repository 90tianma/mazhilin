package com.zhuoxin.treasure;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.zhuoxin.treasure.user.login.LoginActivity;
import com.zhuoxin.treasure.user.register.RegisterActivity;
import com.zhuoxin.treasure.utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.main_register) Button btnRegister;
    @Bind(R.id.main_login)  Button btnLogin;
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityUtils=new ActivityUtils(this);
        }

        @Override
        public void onContentChanged() {
            super.onContentChanged();
            ButterKnife.bind(this);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId()== android.support.v7.appcompat.R.id.home){
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
        @OnClick({R.id.main_register,R.id.main_login})
        public void onClick(View view){
            switch (view.getId()){
                case R.id.main_login:
                    activityUtils.startActivity(LoginActivity.class);
                    break;
                case R.id.main_register:
                    activityUtils.startActivity(RegisterActivity.class);
                    break;
            }
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            finish();
        }
        private long it=0;
        @Override
        public void onBackPressed() {
            long now= System.currentTimeMillis();
            if (now-it<1000){
                super.onBackPressed();
            }else{
                it=now;
                activityUtils.showToast("双击退出");
            }
        }
    }