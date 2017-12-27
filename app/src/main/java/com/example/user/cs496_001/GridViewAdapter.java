package com.example.user.cs496_001;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.io.IOException;
import java.util.ArrayList;

public class GridViewAdapter extends ArrayAdapter {
    private int layoutResourceId;
    private Context context;
    private ArrayList<Uri> data = new ArrayList();

    public GridViewAdapter(Context context, int layoutResourceId, ArrayList data){
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item, parent, false);
        }
        imageView = (ImageView) convertView.findViewById(R.id.img);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        imageView.setImageBitmap(resized);

        return imageView;
    }


}



