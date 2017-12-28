package com.example.user.cs496_001;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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

        ViewHolder holder = new ViewHolder(imageView);
        holder.position = position;

        new ThumbnailTask(position, holder, context.getContentResolver(), data).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);

        return convertView;
    }

    private static class ThumbnailTask extends AsyncTask {

        private int mPosition;
        private ViewHolder mHolder;
        private ContentResolver contentResolver;
        private ArrayList<Uri> data;

        public ThumbnailTask(int position, ViewHolder holder, ContentResolver contentResolver, ArrayList<Uri> data) {
            this.mPosition = position;
            this.mHolder = holder;
            this.contentResolver = contentResolver;
            this.data = data;
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, data.get(mPosition));
            } catch (IOException e) {
                e.printStackTrace();
            }

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            return resized;
        }

        @Override
        protected void onPostExecute(Object o) {
            if(mHolder.position == mPosition) {
                mHolder.thumbnail.setImageBitmap((Bitmap) o);
            }
        }
    }

    private static class ViewHolder {
        public ImageView thumbnail;
        public int position;

        public ViewHolder(ImageView imageView) {
            this.thumbnail = imageView;
        }
    }

}



