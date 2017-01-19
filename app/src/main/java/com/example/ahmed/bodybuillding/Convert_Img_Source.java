package com.example.ahmed.bodybuillding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class Convert_Img_Source {



    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 , stream);
        return stream.toByteArray();
    }


    public static Bitmap getBitmap_Sourse(byte[] image) {

        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
