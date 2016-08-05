package com.lyl.calendar.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.lyl.calendar.CalendarApplication;


public class ToastHelper {

    /**
     * handler to show toasts safely
     */
    private static Handler mHandler = null;

    private static Toast toast = null;

    private static Toast customToast = null;

    private static final int FAVORITE_ALERT_TIMES = 20;

    public static Toast makeToast(Context mContext, int resId) {
        return Toast.makeText(mContext, resId, Toast.LENGTH_SHORT);
    }

    public static Toast makeToast(Context mContext, String text) {
        return Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
    }

    public static void showShort(int resId) {
        showShort(null, resId);
    }

    public static void showShort(Context mContext, int resId) {
        final Context context = (mContext == null ? CalendarApplication.getInstance() : mContext);
        final int resid = resId;
        sharedHandler(context).post(new Runnable() {

            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(resid);
                    toast.setDuration(Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(context.getApplicationContext(), resid, Toast.LENGTH_LONG);
                }
                toast.show();
            }
        });

    }

    public static void showShort(String text) {
        showShort(null, text);
    }

    public static void showShort(Context mContext, String text) {
        final Context context = (mContext == null ? CalendarApplication.getInstance() : mContext);
        final String msg = text;
        sharedHandler(context).post(new Runnable() {

            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(msg);
                    toast.setDuration(Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
                }
                toast.show();
            }
        });
    }


    public static void showLong(int resId) {
        showLong(null, resId);
    }

    public static void showLong(Context mContext, int resId) {
        final Context context = (mContext == null ? CalendarApplication.getInstance() : mContext);
        final int resid = resId;
        sharedHandler(context).post(new Runnable() {

            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(resid);
                    toast.setDuration(Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(context.getApplicationContext(), resid, Toast.LENGTH_LONG);
                }
                toast.show();
            }
        });
    }

    public static void showLong(String text) {
        showLong(null, text);
    }

    public static void showLong(Context mContext, String text) {
        final Context context = (mContext == null ? CalendarApplication.getInstance() : mContext);
        final String msg = text;
        sharedHandler(context).post(new Runnable() {

            @Override
            public void run() {
                if (toast != null) {
                    toast.setText(msg);
                    toast.setDuration(Toast.LENGTH_LONG);
                } else {
                    toast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG);
                }
                toast.show();
            }
        });
    }

    private static Handler sharedHandler(Context context) {
        if (context == null) {
            context = CalendarApplication.getInstance();
        }
        if (mHandler == null) {
            mHandler = Handlers.sharedHandler(context);
        }

        return mHandler;
    }

//	public static void showCustomToast(Context mContext, int resId) {
//		final Context context = (mContext == null ? XMDDApplication.getInstance() : mContext);
//		final int resid = resId;
//		sharedHandler(mContext).post(new Runnable() {
//
//			@Override
//			public void run() {
//				if (customToast == null) {
//					customToast = Toast.makeText(context.getApplicationContext(), "",
//							Toast.LENGTH_SHORT);
//					LayoutInflater inflater = (LayoutInflater) context
//							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//					View view = inflater.inflate(R.layout.custom_toast_layout,
//							null);
//					TextView textView = (TextView) view
//							.findViewById(R.id.custom_toast_text);
//					textView.setText(context.getString(resid));
//					customToast.setView(view);
//				} else {
//					View view = customToast.getView();
//					TextView textView = (TextView) view
//							.findViewById(R.id.custom_toast_text);
//					textView.setText(context.getString(resid));
//				}
//				customToast.show();
//			}
//		});
//	}

//	/**
//	 * 收藏成功提示文案 前20次提示“收藏成功，请到更过→我的收藏中查看”，20次之后提示收藏成功 在此处统一控制显示次数
//	 *
//	 * @param context
//	 */
//	public static void showFavoriteToast(Context context) {
//		PreferencesUtil preference = PreferencesUtil.getInstance(context);
//		int times = preference.getFavoriteAlertTimes();
//		int resid;
//		if (times > FAVORITE_ALERT_TIMES) {
//			resid = R.string.favorite_ok;
//		} else {
//			preference.setFavoriteAlertTimes(++times);
//			resid = R.string.favorite_ok_more_info;
//		}
//		showCustomToast(context, resid);
//	}
}
