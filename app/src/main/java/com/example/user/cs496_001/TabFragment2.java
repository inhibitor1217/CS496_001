package com.example.user.cs496_001;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.util.ArrayList;


public class TabFragment2 extends Fragment {
    int PERMISSION_CODE = 100;
    final int REQ_CODE_SELECT_IMAGE=100;
    private ArrayList<Uri> imageList;
    private GridViewAdapter gridViewAdapter;
    private GridView grid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        imageList = new ArrayList<>();
        View resultView = inflater.inflate(R.layout.tab_fragment_2, container, false);

        grid = (GridView) resultView.findViewById(R.id.gridView);
        gridViewAdapter = new GridViewAdapter(getActivity(), R.layout.grid_item, imageList);
        grid.setAdapter(gridViewAdapter);

        Button loadImageButton = (Button) resultView.findViewById(R.id.load);
        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    galleryIntent.setType("image/*");
                    startActivityForResult(galleryIntent, PERMISSION_CODE);
                }
            }
        });

        return resultView;

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
                    gridViewAdapter.notifyDataSetChanged();

                    grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Intent intent = new Intent(getActivity(), PopUpActivity.class);
                            intent.putExtra("position", i);
                            intent.putExtra("uriList", imageList);
                            startActivity(intent);
                        }

                    });

                    grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                            AlertDialog.Builder del_btn = new AlertDialog.Builder(getActivity());
                            del_btn.setMessage("이미지를 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int j) {
                                            imageList.remove(i);
                                            gridViewAdapter.notifyDataSetChanged();
                                        };
                                    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int j) {
                                }
                            });
                            del_btn.show();
                            return true;
                        }
                    });

                }else if (clipData == null){
                    Toast.makeText(getActivity(),"하나 이상의 사진을 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(getActivity(),"10개 이하의 사진을 선택하세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        }
    }

}
