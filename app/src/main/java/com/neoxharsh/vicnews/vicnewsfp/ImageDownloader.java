package com.neoxharsh.vicnews.vicnewsfp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<URL,Void,Bitmap> {
    ImageView imageView;

    public ImageDownloader(ImageView imageView){
        this.imageView = imageView;
    }
    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Bitmap doInBackground(URL... urls) {
        URL imageURL = urls[0];
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection)imageURL.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            Bitmap bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Bitmap image) {
            if (image !=null){
                imageView.setImageBitmap(image);
            }
    }


}
