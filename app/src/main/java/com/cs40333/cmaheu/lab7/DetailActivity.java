package com.cs40333.cmaheu.lab7;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DetailActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private static Uri pictureUri;
    @Override
    public void onCreate(Bundle bundle) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        super.onCreate(bundle);
        setContentView(com.cs40333.cmaheu.lab7.R.layout.activity_detail);
        DBHelper mydb=new DBHelper(getApplicationContext());

        int resID;
        //Team teamInfo = (Team) getIntent().getSerializableExtra("team");
        int spot=(int) getIntent().getSerializableExtra("teamID");
        Team teamInfo = mydb.getATeam(spot);

        TextView date = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_date);
        TextView place = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_place);
        date.setText(teamInfo.getLongDate());
        place.setText(teamInfo.getLocation());

        ImageView logo1 = (ImageView) findViewById(com.cs40333.cmaheu.lab7.R.id.img_logo1);
        TextView loc1 = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_loc1);
        TextView team1 = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_team1);
        TextView record1 = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_record1);

        resID = this.getResources().getIdentifier(teamInfo.getLogo(), "drawable", this.getPackageName());
        logo1.setImageResource(resID);
        loc1.setText(teamInfo.getPlaceName());
        team1.setText(teamInfo.getTeamName());
        record1.setText(teamInfo.getRecord());

        TextView scorepnts = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_scorepnts);
        TextView scoreTime = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_scoreTime);
        scorepnts.setText(teamInfo.getScore());
        scoreTime.setText(teamInfo.getTimeleft());

        ImageView logo2 = (ImageView) findViewById(com.cs40333.cmaheu.lab7.R.id.img_logo2);
        TextView loc2 = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_loc2);
        TextView team2 = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_team2);
        TextView record2 = (TextView) findViewById(com.cs40333.cmaheu.lab7.R.id.txt_record2);

        resID = this.getResources().getIdentifier(getString(com.cs40333.cmaheu.lab7.R.string.team2logo), "drawable", this.getPackageName());
        logo2.setImageResource(resID);
        loc2.setText(com.cs40333.cmaheu.lab7.R.string.team2loc);
        team2.setText(com.cs40333.cmaheu.lab7.R.string.team2name);
        record2.setText(com.cs40333.cmaheu.lab7.R.string.team2record);
        final Context myContext = this;

        Button btn_cam = (Button) findViewById(com.cs40333.cmaheu.lab7.R.id.btn_camera);
        btn_cam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File PictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                String pictureName = getPictureName();
                File imageFile = new File(PictureDirectory, pictureName);
                pictureUri =  Uri.fromFile(imageFile);

                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "BestMoments" + timestamp + ".jpg";

        // Get image from intent data
        // keep current timestamp
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
                int permissionCheck = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);
                while(permissionCheck == -1) {}

                Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath()+"/"+getPictureName();
                boolean yes = pictureDirectory.exists();

                //Uri imageUri = Uri.parse(pictureDirectoryPath);
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(pictureUri);

                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    ImageView imgView = (ImageView) findViewById(com.cs40333.cmaheu.lab7.R.id.photo_taken);
                    imgView.setImageBitmap(image);
                    imgView.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
