package com.giftsearcher.util;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class ImageUtil {

    //Предназначен для внутреннего использования, и по
    // названию картинки с расширением, ищет её на
    // сервере и преобразует в массив байт
    public byte[] getImageFromResource(String imagePath) {
        final String imageUrl = "/static/" + imagePath;
        return getImageArrayByteFromStringUrl(imageUrl);
    }

    private byte[] getImageArrayByteFromStringUrl(String imageUrl) {
        InputStream imageStream = getClass().getResourceAsStream(imageUrl);
        if (imageStream == null) {
            return null;
        }
        try {
            return IOUtils.toByteArray(imageStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Предназанчен для API, т.к. мы можем передать только
    // назывние картинки, то мы должны найти её на сервере
    // если она есть, и передать название с расширением
    public byte[] getImageFromResourceApi(String imagePath) {
        String imageUrl = getImageResource(imagePath);
        return getImageArrayByteFromStringUrl(imageUrl);
    }

    private String getImageResource(String imagePath) {
        final String imagePngUrl = "/static/" + imagePath + ".png";
        final String imageJpgUrl = "/static/" + imagePath + ".jpg";
        final String imageGifUrl = "/static/" + imagePath + ".gif";
        final String imageJpegUrl = "/static/" + imagePath + ".jpeg";

        if (getClass().getResource(imagePngUrl) != null) {
            return imagePngUrl;
        } else if (getClass().getResource(imageJpgUrl) != null) {
            return imageJpgUrl;
        } else if (getClass().getResource(imageGifUrl) != null) {
            return imageGifUrl;
        } else if (getClass().getResource(imageJpegUrl) != null) {
            return imageJpegUrl;
        }
        return null;
    }

}
