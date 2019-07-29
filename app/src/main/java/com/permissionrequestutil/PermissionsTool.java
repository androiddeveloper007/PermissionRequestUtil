package com.permissionrequestutil;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Arrays;

/**
 * author manley
 */
public class PermissionsTool {
    public static String TAG = "PermissionsTool";
    public final static int REQUEST_READWRITE_EXTERNAL_STORAGE = 10000;
    public final static int REQUEST_RECORD_AUDIO = 10001;
    public final static int REQUEST_MODIFY_PHONE_STATE = 10002;
    private Activity act;

    public PermissionsTool(Activity a) {
        this.act = a;
    }

    //获取所有权限
    private String[] retrievePermissions() {
        try {
            String [] requestedPermissions = act.getPackageManager().getPackageInfo(act
                    .getPackageName(), PackageManager.GET_PERMISSIONS).requestedPermissions;
            android.util.Log.d(TAG, Arrays.toString(requestedPermissions));
            return requestedPermissions;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("This should have never happened.", e);
        }
    }

    //一次请求所有权限
    public void requestPermissions() {
        String[] permissions = retrievePermissions();
        if(permissions!=null){
            for (String permission: permissions) {
                if (ContextCompat.checkSelfPermission(act, permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(act, permissions,0);
                }
            }
        }
    }

    public void requestSdcardPermission() {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(act,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE
                            , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, REQUEST_READWRITE_EXTERNAL_STORAGE);
        }
    }

    //需要系统签名才能生效,否则申请直接失败回调
    public void requestManagePhonePermission() {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.MODIFY_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{
                            Manifest.permission.MODIFY_PHONE_STATE
                    }, REQUEST_MODIFY_PHONE_STATE);
        }
    }

    public void requestRecordAudioPermission() {
        if (ContextCompat.checkSelfPermission(act,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(act,
                    new String[]{
                            Manifest.permission.RECORD_AUDIO
                    }, REQUEST_RECORD_AUDIO);
        }
    }

    /**
     * 跳转到权限设置界面
     */
    private void gotoPermissionSetting() {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", act.getPackageName(), null));
        act.startActivity(intent);
    }
}
