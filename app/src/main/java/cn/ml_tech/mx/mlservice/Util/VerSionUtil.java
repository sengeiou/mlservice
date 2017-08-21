package cn.ml_tech.mx.mlservice.Util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cn.ml_tech.mx.mlservice.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhongwang on 2017/8/1.
 */
public class VerSionUtil {
    private Context context;
    private static String url;
    private static OkHttpClient okHttpClient;
    private int serverCode;
    private int clientCode;
    private String updateInfo;
    private String downLoadUrl;
    private String rootUrl;
    private Handler handler;
    private static final int UPDATETIP = 33;
    private static final int DOWNLOADSUCESS = 44;
    private static final int DOWNLOADFAILURE = 55;
    private UpdateDialog updateDialog;
    private static final int PACKAGE_NAME_START_INDEX = 8;

    public VerSionUtil(final Context context) {
        this.context = context;
        url = context.getResources().getString(R.string.SERVERURL);
        rootUrl = context.getResources().getString(R.string.SERVERROOT);
        if (okHttpClient == null)
            okHttpClient = new OkHttpClient();
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    switch (msg.what) {
                        case UPDATETIP:
                            updateDialog = new UpdateDialog(context, R.layout.updatedialog, new int[]{R.id.llpostive, R.id.llnegative});
                            updateDialog.setUpdateInfo(updateInfo);
                            updateDialog.setOnCenterItemClickListener(new UpdateDialog.OnCenterItemClickListener() {
                                @Override
                                public void OnCenterItemClick(UpdateDialog dialog, View view) {
                                    switch (view.getId()) {
                                        case R.id.llpostive:
                                            updateDialog.dismiss();
                                            String url = rootUrl + "/" + downLoadUrl;
                                            downLoadFile(rootUrl + "/" + downLoadUrl, downLoadUrl);
                                            break;
                                        case R.id.llnegative:
                                            updateDialog.dismiss();
                                            break;
                                    }
                                }
                            });
                            updateDialog.show();
                            break;
                        case DOWNLOADSUCESS:
                            installApk();
                            break;
                        case DOWNLOADFAILURE:
                            Toast.makeText(context, "更新失败,请检查是否有读写权限", Toast.LENGTH_SHORT).show();
                            break;

                    }
                }
            };
        }
    }

    private void installApk() {
        String str = downLoadUrl;
        String fileName = Environment.getExternalStorageDirectory().getPath() + "/" + str;
        File file = new File(fileName);
        if (file.exists()) {
            openFile(file, context);
        }

    }

    private void openFile(File c, Context context) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.fromFile(c),
                "application/vnd.android.package-archive");
        context.startActivity(i);


    }


    public void updateVersion() {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zb", "false");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String versionInfo = response.body().string();
                Log.d("zb", versionInfo);
                try {
                    JSONObject jsonObject = new JSONObject(versionInfo);
                    serverCode = jsonObject.getInt("versionCode");
                    updateInfo = jsonObject.getString("updateInfo");
                    downLoadUrl = jsonObject.getString("downloadUrl");
                    int clientcode = getVersionCode();
                    Log.d("zb", "clientcode" + clientcode);
                    if (serverCode != clientcode) {
                        handler.sendEmptyMessage(UPDATETIP);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void downLoadFile(String fileUrl, String filename) {
        final File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + filename);
        if (file.exists()) {
            file.delete();
        }
        final Request request = new Request.Builder().url(fileUrl).build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("zb", "失败");
                handler.sendEmptyMessage(DOWNLOADFAILURE);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 404) {
                    Log.d("zw", "下载路径不存在");
                    return;
                }
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                file.createNewFile();
                file.setWritable(Boolean.TRUE);
                try {
                    long total = response.body().contentLength();
                    long current = 0;
                    is = response.body().byteStream();
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        current += len;
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    handler.sendEmptyMessage(DOWNLOADSUCESS);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (is != null) {
                            is.close();
                        }
                        if (fos != null) {
                            fos.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        handler.sendEmptyMessage(DOWNLOADFAILURE);
                    }
                }
            }
        });
    }

    public int getVersionCode() {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        int versionCode = 0;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    public String getVersionName() {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


}

