package cn.edu.tongji.sse.twitch.gkd.view.ChangeInfoView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.User;
import cn.edu.tongji.sse.twitch.gkd.presenter.ChangeInfoPresenter.ChangeInfoPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.presenter.ChangeInfoPresenter.IChangeInfoPresenter;
import cn.edu.tongji.sse.twitch.gkd.view.PersonalView.PersonalActivity;
import cn.edu.tongji.sse.twitch.gkd.view.UserLoginView.UserLoginActivity;

import static cn.bmob.v3.Bmob.getApplicationContext;

public class ChangeInfoActivity extends AppCompatActivity implements IChangeInfoView{
    private ImageButton returnPersonal;
    private EditText target;
    private IChangeInfoPresenter iChangeInfoPresenter;
    private Button logout,changeAvater,save_change;
    private ImageView show_avater;
    private TextView neckname;
    private static final String TAG = "MainActivity";
    private String imagePath="";
    private String imageBase64="";

    protected static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    private ImageView iv_personal_icon;

    SharedPreferences cbSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changeinfo);

        cbSp=this.getSharedPreferences("cb", Context.MODE_PRIVATE);

        iChangeInfoPresenter=new ChangeInfoPresenterImpl(this);
        returnPersonal=findViewById(R.id.returnPersonalView);
        show_avater=findViewById(R.id.show_avater);
        save_change=findViewById(R.id.save_change);
        neckname=findViewById(R.id.neckname);
        target=findViewById(R.id.target);
        logout=findViewById(R.id.logout);
        changeAvater=findViewById(R.id.changeAvater);

        neckname.setText(getUserID());
        BmobQuery<User> userBmobQuery=new BmobQuery<>();
        userBmobQuery.addWhereEqualTo("username",getUserID());
        userBmobQuery.findObjects(new FindListener<User>() {
            @Override
            public void done(List<User> list, BmobException e) {
                if(e==null){
                    byte [] input = Base64.decode(list.get(0).getAvater(), Base64.DEFAULT);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(input, 0, input.length);
                    show_avater.setImageBitmap(bitmap);
                    target.setText(list.get(0).getTarget());
                }
            }
        });

        //修改头像
        changeAvater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog(v);
            }
        });

        //返回个人界面
        returnPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChangeInfoActivity.this, PersonalActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //保存修改信息
        save_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChangeInfoPresenter.saveChangeInfo(getUserID(),imageBase64,target.getText().toString());
                Toast.makeText(getApplicationContext(),"修改个人信息成功！",Toast.LENGTH_SHORT).show();
            }
        });

        //退出登录，返回登陆界面
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editor editor = cbSp.edit();
                editor.putBoolean("CbAutomaticLogin",false);
                editor.apply();
                Intent intent = new Intent(ChangeInfoActivity.this, UserLoginActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });
    }

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }

    /**
     * 显示修改头像的对话框
     */
    public void showChoosePicDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Log.d(TAG,"setImageToView:"+photo);
            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            //iv_personal_icon.setImageBitmap(photo);
            show_avater.setImageBitmap(photo);
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //读取图片到ByteArrayOutputStream
        bitmap.compress(Bitmap.CompressFormat.PNG, 40, baos); //参数如果为100那么就不压缩
        byte[] bytes = baos.toByteArray();
        imageBase64 = Base64.encodeToString(bytes,Base64.DEFAULT);

        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
            Log.d(TAG,"imagePath:"+imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }
}
