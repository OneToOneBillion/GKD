package cn.edu.tongji.sse.twitch.gkd.view.PostView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.edu.tongji.sse.twitch.gkd.R;
import cn.edu.tongji.sse.twitch.gkd.bean.Post;
import cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter.IPostPresenter;
import cn.edu.tongji.sse.twitch.gkd.presenter.PostPresenter.PostPresenterImpl;
import cn.edu.tongji.sse.twitch.gkd.view.ChangeInfoView.ImageUtils;
import cn.edu.tongji.sse.twitch.gkd.view.SocialView.SocialActivity;

public class PostActivity extends AppCompatActivity implements IPostView {
    private ImageButton cancel_post,addImage;
    private Button create_new_post;
    private TextView tCreatePost;
    private EditText post_content;
    private ImageView showPhoto;
    private IPostPresenter iPostPresenter;
    private static final String TAG = "MainActivity";
    private String imagePath="";

    protected static final int CHOOSE_PICTURE = 0;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    SharedPreferences sysSettingSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post);

        sysSettingSp=this.getSharedPreferences("sysSetting",MODE_PRIVATE);

        iPostPresenter=new PostPresenterImpl(this);
        create_new_post=findViewById(R.id.creat_new_post);
        cancel_post=findViewById(R.id.cancel_post);
        post_content=findViewById(R.id.post_content);
        tCreatePost=findViewById(R.id.createPostText);
        showPhoto=findViewById(R.id.showPhoto);
        addImage=findViewById(R.id.addImage);

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog(v);
            }
        });

        create_new_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPostPresenter.createNewPost(post_content.getText().toString(),getUserID(),imagePath);
                Intent intent = new Intent(PostActivity.this, SocialActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        cancel_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"取消发布动态！",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(PostActivity.this, SocialActivity.class);
                intent.putExtra("data",getUserID());
                startActivity(intent);
                finish();
            }
        });

        //语言设置
        if(sysSettingSp.getString("language","").equals("English")) {
            tCreatePost.setText(R.string.create_post_en);
            create_new_post.setText(R.string.create_en);
        }
        else{
            tCreatePost.setText(R.string.create_post_cn);
            create_new_post.setText(R.string.create_cn);
        }
    }

    /*public String getUserID(){
        return mpost_user.getText().toString();
    }*/

    public String getUserID(){
        Intent intent=getIntent();
        return intent.getStringExtra("data");
    }

    public String getPostContent(){
        return post_content.getText().toString();
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
            showPhoto.setImageBitmap(photo);
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