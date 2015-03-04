package com.example.felipe.parseandroid;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;

import com.parse.Parse;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by ieepo on 27/02/15.
 */
public class chavodelocho {

    String name;
    String description;
    String imagenURL;
    ParseFile imags;

    public ParseFile getImags() {
        return imags;
    }

    public void setImags(ParseFile imags) {
        this.imags = imags;
    }




    public String getImagenURL() {
        return imagenURL;
    }

    public void setImagenURL(String imagenURL) {
        this.imagenURL = imagenURL;
    }



    public chavodelocho(String name, String description,String imagenURL,ParseFile imgs) {
        this.imagenURL=imagenURL;
        this.name = name;
        this.description = description;
        this.imags=imgs;

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
