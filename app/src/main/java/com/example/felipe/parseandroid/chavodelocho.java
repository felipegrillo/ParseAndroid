package com.example.felipe.parseandroid;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

/**
 * Created by ieepo on 27/02/15.
 */
public class chavodelocho {
    int image;
    String name;
    String description;
    String imagenURL;
    Bitmap img;


    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }

    public chavodelocho(int image, String name, String description,String imagenURL, Bitmap img) {
        this.imagenURL=imagenURL;
        this.img = img;
        this.image = image;
        this.name = name;
        this.description = description;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
