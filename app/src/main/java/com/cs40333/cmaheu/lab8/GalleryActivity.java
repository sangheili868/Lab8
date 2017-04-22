package com.cs40333.cmaheu.lab8;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chris_000 on 4/20/2017.
 */

public class GalleryActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static Uri pictureUri;
    int teamID;

    public void onCreate(Bundle bundle) {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        super.onCreate(bundle);
        setContentView(com.cs40333.cmaheu.lab8.R.layout.activity_gallery);
        teamID=(int) getIntent().getSerializableExtra("teamID");

        /*GalleryActivity is going to inflate the activity_gallery layout file.


        The gridview of GalleryActivity will display all the photos from the Image table that are associated with
        the team id in question. You will need to select all the images from the Image table that have the team
        id of the current team. Call the getSelectEntries function from the DBHelper to get the images you need.
        Make sure that you update the where clause inside getSelectEntries from " book_id = ?" to  " team_id = ?".
        The team_id is equal to the team id sent by the DetailActivity to GalleryActivity.

        Use SimpleCursorAdapter to display images returned by the getSelectEntries function
        (Look at the populateGridView() function in the sample project).
        */

        showGallery();

        FloatingActionButton fab_cam = (FloatingActionButton) findViewById(com.cs40333.cmaheu.lab8.R.id.fab_cam);
        fab_cam.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("teamID",teamID);
                File PictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

                String pictureName = getPictureName();
                File imageFile = new File(PictureDirectory, pictureName);
                pictureUri = Uri.fromFile(imageFile);

                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    private void showGallery() {


        DBHelper mydb=new DBHelper(getApplicationContext());
        GridView gridview = (GridView) findViewById(R.id.grid_gallery);
        String[] fields = new String[]{DBHelper.COL_ID, DBHelper.COL_IMAGE};
        String[] args = new String[]{Integer.toString(teamID)};
        int[] items = new int[] {R.id.gal_image};

        Cursor cursor = mydb.getImages(teamID);
        SimpleCursorAdapter galleryCursorAdapter = new SimpleCursorAdapter(this, R.layout.image_item, cursor, fields, items,1);

        galleryCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                int tmp=view.getId();
                String tmp4 = getResources().getResourceEntryName(view.getId());
                if(view.getId() == R.id.gal_image) {
                    ImageView imageView=(ImageView) view;
                    byte[] imageArray = cursor.getBlob(1);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageArray,0,imageArray.length);
                    imageView.setImageBitmap(bitmap);
                    return true;
                }
                return false;
            }
        });
        gridview.setAdapter(galleryCursorAdapter);
    }
    private String getPictureName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "IMG" + timestamp + ".jpg";

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
                DBHelper mydb=new DBHelper(getApplicationContext());
                int teamID=(int) getIntent().getSerializableExtra("teamID");

                //Intent photoGalleryIntent = new Intent(Intent.ACTION_PICK);
                //File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                //String pictureDirectoryPath = pictureDirectory.getPath()+"/"+getPictureName();
                //boolean yes = pictureDirectory.exists();

                //Uri imageUri = Uri.parse(pictureDirectoryPath);
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(pictureUri);
//https://github.com/sangheili868/Lab7/blob/master/app/src/main/java/com/cs40333/cmaheu/lab7/DetailActivity.java
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    mydb.insertImage(teamID, image);
                    showGallery();
                    //ImageView imgView = (ImageView) findViewById(com.cs40333.cmaheu.lab8.R.id.photo_taken);
                    //imgView.setImageBitmap(image);
                    //imgView.setVisibility(View.VISIBLE);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
