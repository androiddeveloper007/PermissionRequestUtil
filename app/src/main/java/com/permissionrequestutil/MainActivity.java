package com.permissionrequestutil;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.easypermission.PermissionSettingPage;

import static com.permissionrequestutil.PermissionsTool.REQUEST_MODIFY_PHONE_STATE;
import static com.permissionrequestutil.PermissionsTool.REQUEST_READWRITE_EXTERNAL_STORAGE;
import static com.permissionrequestutil.PermissionsTool.REQUEST_RECORD_AUDIO;

public class MainActivity extends AppCompatActivity {

    private PermissionsTool pManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //request permission
        pManager = new PermissionsTool(this);
        pManager.requestSdcardPermission();

        //6.0判断悬浮窗权限
//        if (!PermissionUtils.isHasOverlaysPermission(this)) {
//            //跳转到悬浮窗设置页面
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
//            startActivityForResult(intent, 1);
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READWRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("权限已申请");
            } else {
                showToast("权限已拒绝");
                PermissionSettingPage.start(this, false);
            }
            pManager.requestRecordAudioPermission();
        } else if(requestCode == REQUEST_RECORD_AUDIO) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("权限已申请");
            } else {
                showToast("权限已拒绝");
                PermissionSettingPage.start(this, false);
            }
//            pManager.requestManagePhonePermission();
        } else if(requestCode == REQUEST_MODIFY_PHONE_STATE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast("权限已申请");
            } else {
                showToast("权限已拒绝");
                PermissionSettingPage.start(this, false);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void showToast(String string){
        Toast.makeText(MainActivity.this,string,Toast.LENGTH_LONG).show();
    }
}
