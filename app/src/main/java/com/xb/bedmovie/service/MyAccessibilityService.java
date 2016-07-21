package com.xb.bedmovie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.util.Log;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

@SuppressLint("NewApi")
public class MyAccessibilityService extends AccessibilityService {

	public static int INVOKE_TYPE = 0;
	public static final int TYPE_KILL_APP = 1;
	public static final int TYPE_INSTALL_APP = 2;
	public static final int TYPE_UNINSTALL_APP = 3;

	private String[] packageNames = new String[]{"com.android.packageinstaller","com.google.android.packageinstaller", "com.lenovo.safecenter", "com.lenovo.security"};

	private HashSet<ArrayList<String>> packages = new HashSet(Arrays.asList(packageNames));
	public static void reset(){
		INVOKE_TYPE = 0;
	}

	/**
	 * 此方法是accessibility service的配置信息 写在java类中是为了向下兼容
	 */
	@Override
	protected void onServiceConnected() {
		super.onServiceConnected();
		AccessibilityServiceInfo mAccessibilityServiceInfo = new AccessibilityServiceInfo();
		// 响应事件的类型，这里是全部的响应事件（长按，单击，滑动等）
		mAccessibilityServiceInfo.eventTypes = AccessibilityEvent.TYPES_ALL_MASK;
		// 反馈给用户的类型，这里是语音提示
		mAccessibilityServiceInfo.feedbackType = AccessibilityServiceInfo.FEEDBACK_HAPTIC;
		// 过滤的包名
		mAccessibilityServiceInfo.packageNames = packageNames;
	}

	@Override
	public void onAccessibilityEvent(AccessibilityEvent event) {
		this.processAccessibilityEnvent(event);
	}

	private void processAccessibilityEnvent(AccessibilityEvent event) {

		Log.d("test", AccessibilityEvent.eventTypeToString(event.getEventType()));
		if (event.getSource() == null) {
			Log.d("test", "the source = null");
		} else {
			Log.d("test", "event = " + event.toString());
			switch (INVOKE_TYPE) {
			case TYPE_KILL_APP:
				processKillApplication(event);
				break;
			case TYPE_INSTALL_APP:
				processinstallApplication(event);
				break;
			case TYPE_UNINSTALL_APP:
				processUninstallApplication(event);
				break;				
			default:
				break;
			}						
		}
	}

	@Override
	protected boolean onKeyEvent(KeyEvent event) {
		return true;

	}

	@Override
	public void onInterrupt() {
		// TODO Auto-generated method stub

	}

	private void processUninstallApplication(AccessibilityEvent event) {
		if (event.getSource() != null) {
			if (packages.contains(event.getPackageName())) {			
//				if (event.getPackageName().equals("com.android.packageinstaller") || event.getPackageName().equals("com.lenovo.safecenter")) {
				List<AccessibilityNodeInfo> ok_nodes = event.getSource().findAccessibilityNodeInfosByText("卸载");
				if (ok_nodes!=null && !ok_nodes.isEmpty()) {
					AccessibilityNodeInfo node;
					for(int i=0; i<ok_nodes.size(); i++){
						node = ok_nodes.get(i);
						if (node.getClassName().equals("android.widget.Button") && node.isEnabled()) {
							node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
						}
					}

				}
			}
		}

	}

	private void processinstallApplication(AccessibilityEvent event) {
		if (event.getSource() != null) {
			if (packages.contains(event.getPackageName())) {
				List<AccessibilityNodeInfo> unintall_nodes = event.getSource().findAccessibilityNodeInfosByText("安装");
				if (unintall_nodes!=null && !unintall_nodes.isEmpty()) {
					AccessibilityNodeInfo node;
					for(int i=0; i<unintall_nodes.size(); i++){
						node = unintall_nodes.get(i);
						if (node.getClassName().equals("android.widget.Button") && node.isClickable()) {
							node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
						}
					}
				}
				
				List<AccessibilityNodeInfo> next_nodes = event.getSource().findAccessibilityNodeInfosByText("下一步");
				if (next_nodes!=null && !next_nodes.isEmpty()) {
					AccessibilityNodeInfo node;
					for(int i=0; i<next_nodes.size(); i++){
						node = next_nodes.get(i);
						if (node.getClassName().equals("android.widget.Button") && node.isClickable()) {
							node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
						}
					}
				}
				
				
				
				List<AccessibilityNodeInfo> ok_nodes = event.getSource().findAccessibilityNodeInfosByText("完成");
				if (ok_nodes!=null && !ok_nodes.isEmpty()) {
					AccessibilityNodeInfo node;
					for(int i=0; i<ok_nodes.size(); i++){
						node = ok_nodes.get(i);
						Toast.makeText(this, node.getText() + (node.getWindowId() +""), Toast.LENGTH_SHORT).show();
						if (node.getClassName().equals("android.widget.Button")) {
							node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
						}
					}	
				}
				
				
				if(android.os.Build.MODEL.equals("sangsung")){
					List<AccessibilityNodeInfo> confirm_nodes = event.getSource().findAccessibilityNodeInfosByText("确认");
					if (confirm_nodes!=null && !confirm_nodes.isEmpty()) {
						AccessibilityNodeInfo node;
						for(int i=0; i<confirm_nodes.size(); i++){
							node = confirm_nodes.get(i);
							if (node.getClassName().equals("android.widget.Button") && node.isClickable()) {
								node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
							}
						}	
					}
				}


			}
		}

	}

	private void processKillApplication(AccessibilityEvent event) {
		
		if (event.getSource() != null) {
			if (event.getPackageName().equals("com.android.settings")) {
				List<AccessibilityNodeInfo> stop_nodes = event.getSource().findAccessibilityNodeInfosByText("停止");
				if (stop_nodes!=null && !stop_nodes.isEmpty()) {
					AccessibilityNodeInfo node;
					for(int i=0; i<stop_nodes.size(); i++){
						node = stop_nodes.get(i);
						if (node.getClassName().equals("android.widget.Button")) {
							if(node.isEnabled()){
							   node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
							}
						}
					}
				}

				List<AccessibilityNodeInfo> ok_nodes = event.getSource().findAccessibilityNodeInfosByText("确认");
				if (ok_nodes!=null && !ok_nodes.isEmpty()) {
					AccessibilityNodeInfo node;
					for(int i=0; i<ok_nodes.size(); i++){
						node = ok_nodes.get(i);
						if (node.getClassName().equals("android.widget.Button")) {
							node.performAction(AccessibilityNodeInfo.ACTION_CLICK);
							Log.d("action", "click ok");
						}
					}

				}
			}
		}
	}

}
