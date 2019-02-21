package com;

import android.app.Activity;
import android.content.Intent;

import java.util.Stack;

/**
 * @author 作者： Administrator
 *
 * @version 创建时间：2014年8月26日下午6:19:04
 */
public class AppManager {
    
    private static AppManager instance = new AppManager();
    
	private Stack<BaseActivity> activityStack  = new Stack<BaseActivity>();

	private AppManager() {
	}
	
	/**
	 * 单实例 , UI无需考虑多线程同步问题
	 */
	public static AppManager getAppManager() {
		return instance;
	}

	/**
	 * 添加Activity到栈
	 */
	public void addActivity(BaseActivity activity) {
		activityStack.add(activity);
	}

	public void removeActivity(BaseActivity activity)
	{
		activityStack.remove(activity);
	}
	
	/**
	 * 获取当前Activity（栈顶Activity）
	 */
	public BaseActivity currentActivity() {
		if (activityStack == null || activityStack.isEmpty()) {
			return null;
		}
		BaseActivity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 获取当前Activity（栈顶Activity） 没有找到则返回null
	 */
	public BaseActivity findActivity(Class<?> cls) {
		BaseActivity activity = null;
		for (BaseActivity aty : activityStack) {
			if (aty.getClass().equals(cls)) {
				activity = aty;
				break;
			}
		}
		return activity;
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 结束当前Activity（栈顶Activity）
	 */
	public void finishActivity() {
		BaseActivity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 结束指定的Activity(重载)
	 */
	public void finishActivity(Class<?> cls) {
		Activity delActivity = null;
		for (BaseActivity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				delActivity = activity;
				//finishActivity(activity);
			}
		}
		if(null != delActivity){
			finishActivity(delActivity);
		}
	}

	/**
	 * 关闭除了指定activity以外的全部activity 如果cls不存在于栈中，则栈全部清空
	 * 
	 * @param cls
	 */
	public void finishOthersActivity(Class<?> cls) {
		for (BaseActivity activity : activityStack) {
			if (!(activity.getClass().equals(cls))) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}
	


//	/**
//	 * 应用程序退出
//	 */
//	public void AppExit(Context context) {
//		try {
//			finishAllActivity();
//			ActivityManager activityMgr = (ActivityManager) context
//					.getSystemService(Context.ACTIVITY_SERVICE);
//			activityMgr.killBackgroundProcesses(context.getPackageName());
//			System.exit(0);
//		} catch (Exception e) {
//			System.exit(0);
//		}
//	}
	
}