package com.permissionrequestutil;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.easypermission.EasyPermission;
import com.easypermission.GrantResult;
import com.easypermission.NextAction;
import com.easypermission.Permission;
import com.easypermission.PermissionRequestListener;
import com.easypermission.RequestPermissionRationalListener;

import java.util.Map;

public class EasyDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EasyPermission.with(this)
                .addPermissions(Permission.Group.LOCATION)      //申请定位权限组
                .addPermissions(Permission.Group.PHONE)          //申请打电话权限
                .addRequestPermissionRationaleHandler(Permission.ACCESS_FINE_LOCATION,
                        new RequestPermissionRationalListener() {
                    @Override
                    public void onRequestPermissionRational(String permission,
                                                            boolean requestPermissionRationaleResult,
                                                            final NextAction nextAction) {
                        //这里处理具体逻辑，如弹窗提示用户等,但是在处理完自定义逻辑后必须调用nextAction的next方法
                        showToast("");
                    }
                })
                .addRequestPermissionRationaleHandler(Permission.CALL_PHONE, new RequestPermissionRationalListener() {
                    @Override
                    public void onRequestPermissionRational(String permission,
                                                            boolean requestPermissionRationaleResult,
                                                            final NextAction nextAction) {
                        //这里处理具体逻辑，如弹窗提示用户等,但是在处理完自定义逻辑后必须调用nextAction的next方法

                        showToast("");
                    }
                })
                .request(new PermissionRequestListener() {
                    @Override
                    public void onGrant(Map<String, GrantResult> result) {
                        //权限申请返回
                        showToast("");
                    }

                    @Override
                    public void onCancel(String stopPermission) {
                        //在addRequestPermissionRationaleHandler的处理函数里面调用了
                        // NextAction.next(NextActionType.STOP,就会中断申请过程，直接回调到这里来
                        showToast("");
                    }
                });
    }


    private void showToast(String string){
        Toast.makeText(EasyDemoActivity.this,string,Toast.LENGTH_LONG).show();
    }
}
