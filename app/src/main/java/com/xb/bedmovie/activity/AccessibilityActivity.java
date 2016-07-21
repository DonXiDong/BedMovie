package com.xb.bedmovie.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import com.xb.bedmovie.R;
import com.xb.bedmovie.service.MyAccessibilityService;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 智能装示例
 */
public class AccessibilityActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibilty);

        ((TextView)this.findViewById(R.id.txt_enabled)).setText(isAccessibleEnabled() ? "智能装功能：已打开" : "智能装功能：已关闭");
        this.findViewById(R.id.activeButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent killIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(killIntent);
            }
        });

        this.findViewById(R.id.installButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                MyAccessibilityService.INVOKE_TYPE = MyAccessibilityService.TYPE_INSTALL_APP;
                String fileName = Environment.getExternalStorageDirectory() + "/apk/swmarket_v1.2.0_20160707110136_release.apk";
//                File installFile = new File(fileName);
//                if (installFile.exists()) {
//                    installFile.delete();
//                }
//                try {
//                    installFile.createNewFile();
//                    FileOutputStream out = new FileOutputStream(installFile);
//                    byte[] buffer = new byte[512];
//                    InputStream in = AccessibilityActivity.this.getAssets().open("test.apk");
//                    int count;
//                    while ((count = in.read(buffer)) != -1) {
//                        out.write(buffer, 0, count);
//                    }
//                    in.close();
//                    out.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + fileName), "application/vnd.android.package-archive");
//                intent.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive");
                startActivity(intent);

            }
        });
        this.findViewById(R.id.uninstallButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                MyAccessibilityService.INVOKE_TYPE = MyAccessibilityService.TYPE_UNINSTALL_APP;
                Uri packageURI = Uri.parse("package:com.example.test");
                Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
                startActivity(uninstallIntent);
            }
        });
        this.findViewById(R.id.killAppButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                MyAccessibilityService.INVOKE_TYPE = MyAccessibilityService.TYPE_KILL_APP;
                Intent killIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri packageURI = Uri.parse("package:com.example.test");
                killIntent.setData(packageURI);
                startActivity(killIntent);
            }
        });
    }

    //检验是否开启自动装功能
    private boolean isAccessibleEnabled() {
        AccessibilityManager manager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);

        List<AccessibilityServiceInfo> runningServices = manager.getEnabledAccessibilityServiceList(AccessibilityEvent.TYPES_ALL_MASK);
        for (AccessibilityServiceInfo info : runningServices) {
            if (info.getId().equals(getPackageName() + "/.service.MyAccessibilityService")) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TextView)this.findViewById(R.id.txt_enabled)).setText(isAccessibleEnabled() ? "智能装功能：已打开" : "智能装功能：已关闭" );
    }
}
