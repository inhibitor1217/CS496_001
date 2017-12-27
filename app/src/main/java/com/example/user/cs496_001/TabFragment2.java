package com.example.user.cs496_001;

/**
 * Created by user on 2017-12-27.
 */

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;


public class TabFragment2 extends Fragment {
    int PERMISSION_CODE = 100;
    final int REQ_CODE_SELECT_IMAGE=100;
    ArrayList imageList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        imageList = new ArrayList<>();
        return inflater.inflate(R.layout.tab_fragment_2, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void load(View v){

        if (ActivityCompat.checkSelfPermission((Activity) getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) getContext(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        } else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, PERMISSION_CODE);
        }

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getImagefromGallery();
            }
        }
    }

    public void getImagefromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("image/*");
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQ_CODE_SELECT_IMAGE) {
            if(resultCode== Activity.RESULT_OK) {

                ClipData clipData = data.getClipData();
                Uri uri;

                if (clipData != null && clipData.getItemCount() <= 10 ){

                    for(int i=0;i < clipData.getItemCount(); i++){
                        uri = clipData.getItemAt(i).getUri();
                        imageList.add(uri);
                    }
                    GridView grid = (GridView) getView().findViewById(R.id.gridView);
                    GridViewAdapter ImageAdapter = new GridViewAdapter(getActivity(),R.layout.grid_item, imageList);
                    grid.setAdapter(ImageAdapter);

                }else if (clipData == null){
                    Toast.makeText(getActivity(),"Choose picture at least one",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(getActivity(),"Choose picture up to 10",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }
    }

}
