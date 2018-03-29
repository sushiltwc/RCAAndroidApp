package com.twc.rca.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.twc.rca.mrz.MrzDate;
import com.twc.rca.mrz.MrzParser;
import com.twc.rca.mrz.MrzRange;
import com.twc.rca.mrz.MrzSex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Sushil on 28-03-2018.
 */

public class PassportUtils {

    private TessBaseAPI mTess;
    String datapath = "";
    Context context;

    public PassportUtils(Context context){
    this.context=context;
    }

    public void processFront(Bitmap bitmap) {
        String OCRresult = null;

        //initialize Tesseract API
        String language = "eng";
        datapath = context.getFilesDir() + "/tesseract/";
        mTess = new TessBaseAPI();

        checkFile(new File(datapath + "tessdata/"));

        mTess.init(datapath, language);

        bitmap = Resize(bitmap, (int) (2 * bitmap.getWidth()), (int) (2 * bitmap.getHeight()));

        bitmap = cutTop(bitmap);
        bitmap = SetGrayscale(bitmap);
        bitmap = RemoveNoise(bitmap);
        mTess.setImage(bitmap);

        OCRresult = mTess.getUTF8Text();
        Log.d("Result : ", OCRresult);
        String[] info = new String[2];
        String[] str = new String[2];
        str = OCRresult.split("\n");

        String str1 = str[str.length - 2].replace(" ", "").replace("-", "").replace("(", "").replace(")", "").replace("¢", "");
        String str2 = str[str.length - 1].replace(" ", "").replace("-", "").replace("(", "").replace(")", "");
        Log.d("OCR : ", OCRresult);

        if (str1.length() != 44) {
            int strLength = 44 - str1.length();
            for (int i = 0; i < strLength; i++)
                str1 += "<";
        }
        if (str2.length() != 44) {
            int strLength = 44 - str2.length();
            for (int i = 0; i < strLength; i++)
                str2 += "<";
        }
        Log.d("Str1 : ", str1);
        Log.d("Str2 : ", str2);

        getPersonalDetails(str1);
        getPassportDetails(str2);
    }

    private void checkFile(File dir) {
        if (!dir.exists() && dir.mkdirs()) {
            copyFiles();
        }
        if (dir.exists()) {
            String datafilepath = datapath + "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles() {
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = context.getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }
            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void getPersonalDetails(String str) {
        String nationality = null, surname = null, name = null;
        String[] userName;
        //Checking first character is P or not at position 0
        if (str.charAt(0) == 'P') {
            //Checking second
            if (str.charAt(1) == '<' || str.charAt(1) == 'K') {
                nationality = new MrzParser(str).parseString(new MrzRange(2, 5, 0)).replace('1', 'I').replace('0', 'D');
                str = str.replace('5', 'S').replace('1', 'I');
                str = str.replaceAll("[^A-Za-z<]+", "");
                userName = new MrzParser(str).parseName(new MrzRange(5, str.length(), 0));
                surname = userName[0].toUpperCase().replaceAll("[^A-Za-z]+", "");
                name = userName[1].toUpperCase().replaceAll("[^A-Za-z]+", "");
            } else {
                nationality = new MrzParser(str).parseString(new MrzRange(1, 4, 0)).replace('1', 'I').replace('0', 'D');
                String str1 = str.replaceAll("[^A-Za-z<]+", "");
                userName = new MrzParser(str1).parseName(new MrzRange(4, str1.length(), 0));
                surname = userName[0];
                name = userName[1];
            }
            ILog.d("Issuing Country :",nationality);
            ILog.d("Surname :",surname);
            ILog.d("Name :",name);
        }
    }

    static void getPassportDetails(String str) {
        str = str.substring(0, 28);
        String passportStr = str.substring(0, 8);
        String remainStr = str.substring(9, 28);
        MrzSex sex = null;
        MrzDate dob=null,doe=null;

        Log.d("PassportNo :", passportStr);
        Log.d("Remain :", remainStr);
        passportStr = passportStr.replaceAll("[^A-Za-z<0-9]+", "");
        String passportNo = new MrzParser(passportStr).parseString(new MrzRange(0, passportStr.length(), 0));
        String nationality = new MrzParser(remainStr).parseString(new MrzRange(1, 4, 0)).replace('1', 'I').replace('0', 'D');
        dob = new MrzParser(remainStr).parseDate(new MrzRange(4, 10, 0));
        doe = new MrzParser(remainStr).parseDate(new MrzRange(12, 18, 0));
        if (!Character.toString(remainStr.charAt(11)).equalsIgnoreCase(" ") || !Character.isLetter(remainStr.charAt(11)))
            sex = new MrzParser(remainStr).parseSex(11, 0);

        ILog.d("Passport No :",passportNo);
        ILog.d("Nationality :",nationality);
        ILog.d("DOB :",dob.toString());
        ILog.d("DOE :",doe.toString());
        ILog.d("Sex :",sex.toString());

    }

    public static Bitmap cutTop(Bitmap bitmap) {
        Bitmap cutBitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight() / 2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(cutBitmap);
        Rect srcRect = new Rect(0, bitmap.getHeight() / 2, bitmap.getWidth(),
                bitmap.getHeight());
        Rect desRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight() / 2);
        canvas.drawBitmap(bitmap, srcRect, desRect, null);
        return cutBitmap;
    }

    //Resize
    public static Bitmap Resize(Bitmap bmp, int newWidth, int newHeight) {

        int width = bmp.getWidth();

        int height = bmp.getHeight();

        float scaleWidth = ((float) newWidth) / width;

        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation

        Matrix matrix = new Matrix();

        // resize the bit map

        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap

        Bitmap resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, width, height, matrix, false);

        return resizedBitmap;

    }

    //SetGrayscale
    public static Bitmap SetGrayscale(Bitmap img) {

        int width, height;
        height = img.getHeight();
        width = img.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(img, 0, 0, paint);
        return bmpGrayscale;
    }

    //RemoveNoise
    public static Bitmap RemoveNoise(Bitmap bmap) {
        for (int x = 0; x < bmap.getWidth(); x++) {
            for (int y = 0; y < bmap.getHeight(); y++) {
                int pixel = bmap.getPixel(x, y);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (R < 162 && G < 162 && B < 162)
                    bmap.setPixel(x, y, Color.BLACK);
            }
        }
        for (int x = 0; x < bmap.getWidth(); x++) {
            for (int y = 0; y < bmap.getHeight(); y++) {
                int pixel = bmap.getPixel(x, y);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (R > 162 && G > 162 && B > 162)
                    bmap.setPixel(x, y, Color.WHITE);
            }
        }
        return bmap;
    }
}
