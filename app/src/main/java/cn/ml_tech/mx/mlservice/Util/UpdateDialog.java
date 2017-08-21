package cn.ml_tech.mx.mlservice.Util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.ml_tech.mx.mlservice.R;


/**
 * Created by zhongwang on 2017/8/1.
 */

public class UpdateDialog extends Dialog implements View.OnClickListener {
    private Context context;      // 上下文
    private int layoutResID;      // 布局文件id
    private int[] listenedItems;
    private OnCenterItemClickListener listener;
    private String updateInfo;

    public UpdateDialog(@NonNull Context context) {
        super(context);
    }

    public UpdateDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected UpdateDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public UpdateDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.dialog_node);
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    public void setUpdateInfo(String updateInfo) {
        this.updateInfo = updateInfo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(layoutResID);
        TextView updateInfo = (TextView) findViewById(R.id.tvUpdateInfo);
        updateInfo.setText(this.updateInfo.replace("\\n", "\n"));
        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = display.getWidth() * 4 / 10;
        getWindow().setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        //遍历控件id,添加点击事件
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
        initView();

    }

    private void initView() {
    }

    public interface OnCenterItemClickListener {
        void OnCenterItemClick(UpdateDialog dialog, View view);
    }

    public void setOnCenterItemClickListener(OnCenterItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v)

    {

        listener.OnCenterItemClick(this, v);
    }
}
