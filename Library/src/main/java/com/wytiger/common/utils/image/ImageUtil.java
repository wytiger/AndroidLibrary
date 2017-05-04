package com.wytiger.common.utils.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;

import com.wytiger.common.utils.IOUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ImageUtil {


    private static final int INSAMPLE_SIZE = 8;
    private static final int QUALITY = 20;

    private ImageUtil() {
    }

    public static boolean isSquare(String imagePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        return options.outHeight == options.outWidth;
    }

    public static void asyncLoadImage(Context context,Uri imageUri, LoadImageCallback callback) {
        new LoadImageUriTask(context,imageUri, callback).execute();
    }

    //异步加载缩略图
    public static void asyncLoadSmallImage(Context context, Uri imageUri, LoadImageCallback callback) {
        new LoadSmallPicTask(context, imageUri, callback).execute();
    }

    //得到指定大小的Bitmap对象
    public static Bitmap getResizedBitmap(Context context, Uri imageUri, int width, int height) {
        InputStream inputStream = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            inputStream = context.getContentResolver().openInputStream(imageUri);
            BitmapFactory.decodeStream(inputStream, null, options);

            options.outWidth = width;
            options.outHeight = height;
            options.inJustDecodeBounds = false;
            IOUtil.closeStream(inputStream);
            inputStream = context.getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(inputStream, null, options);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtil.closeStream(inputStream);
        }
        return null;
    }

    /**
     * 判断是否有SD卡
     *
     * @return true为有SDcard，false则表示没有
     */
    public static boolean hasSdcard() {
        boolean hasCard = false;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            hasCard = true;
        }
        return hasCard;
    }

    /**
     * 使用系统当前日期加以调整作为照片的名称
     *
     * @return String 文件拍照名字
     */
    public static String getPhotoFileName() {
        java.util.Date date = new java.util.Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA);
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 加载本地图片 http://bbs.3gstdy.com
     *
     * @param url 本地图片路径
     * @return Bitmap Bitmap对象
     */
    public static Bitmap getLocalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            BitmapFactory.Options options = new BitmapFactory.Options();
            // 降低图片质量，降低OOM几率
            options.inSampleSize = INSAMPLE_SIZE;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            Bitmap bitmap = BitmapFactory.decodeStream(fis, null, options);
            int degree = readPictureDegree(url);
            if (degree != 0) {// 旋转照片角度
                bitmap = rotateBitmap(bitmap, degree);
            }
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 及时扫描拍照后的照片，在相册就能看到
     *
     * @param context 上下文对象
     * @param path    照片的路径
     */
    public static void scanMedia(Context context, String path) {
        File file = new File(path);
        Uri uri = Uri.fromFile(file);
        Intent scanFileIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        context.sendBroadcast(scanFileIntent);
    }

    public static String saveBitmap2Jpg(Context context, Bitmap bitmap) throws IOException {
        File photoFile = getOwnCacheDirectory(context);
        return saveBitmap2Jpg(context, bitmap, photoFile);
    }

    public static String saveBitmap2Jpg(Context context, Bitmap bitmap, File photoFile) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photoFile);
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        stream.close();
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photoFile);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
        return photoFile.getAbsolutePath();
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使用这个工具生成的图像不会被拉伸。
     *
     * @param filePath 图像的路径
     * @param width    指定输出图像的宽度
     * @param height   指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String filePath, int width, int height) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, width, height);
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
        int degree = readPictureDegree(filePath);
        if (degree != 0) {// 旋转照片角度
            bitmap = rotateBitmap(bitmap, degree);
        }
        return bitmap;
    }

    /**
     * @param bitmap 原图
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, Matrix m) {
        if (null == bitmap) {
            return null;
        }
        Bitmap result;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();
        int edgeLength = Math.min(widthOrg, heightOrg);
        try {
            if (widthOrg > edgeLength) {
                result = Bitmap.createBitmap(bitmap, (widthOrg - edgeLength) / 2, 0, edgeLength, edgeLength, m, true);
            } else {
                result = Bitmap.createBitmap(bitmap, 0, (heightOrg - edgeLength) / 2, edgeLength, edgeLength, m, true);
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     计算图片的缩放值
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int requestWidth, int requestHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > requestHeight || width > requestWidth) {
            final int heightRatio = Math.round((float) height / (float) requestHeight);
            final int widthRatio = Math.round((float) width / (float) requestWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private static File getOwnCacheDirectory(Context context) {
        File imgDir = null;
        if (Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            imgDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        }
        if (imgDir == null || (!imgDir.exists() && !imgDir.mkdirs())) {
            imgDir = context.getCacheDir();
        }
        return new File(imgDir, System.currentTimeMillis() + ".jpeg");
    }

    /**
     * 旋转图片
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    /**
     * drawable2Bitmap
     * @param drawable
     * @return
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 通过Uri获得bitmap
     *
     * @param uri          图像的uri对象
     * @param inSampleSize 图像质量因素
     * @return Bitmap 对象
     */
    public static Bitmap getBitmapFromUri(Context context, Uri uri, int inSampleSize) {
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream, null, options);
            int degree = readPictureDegree(uri.getPath());
            if (degree != 0) {// 旋转照片角度
                bitmap = rotateBitmap(bitmap, degree);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /*
     * 压缩图片，处理某些手机拍照角度旋转的问题
	 */
    public static String compressImage(String filePath, File imageDir, String fileName) throws FileNotFoundException {
        Bitmap bm = getImageThumbnail(filePath, 480, 800);
        File outputFile = new File(imageDir, fileName);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        FileOutputStream out = new FileOutputStream(outputFile);
        bm.compress(Bitmap.CompressFormat.JPEG, QUALITY, out);
        try {
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile.getPath();
    }

    //异步加载图片回调
    public interface LoadImageCallback {
        void callback(Bitmap result);
    }

    private static class LoadImageUriTask extends AsyncTask<Void, Void, Bitmap> {
    	private final Context context;
        private final Uri imageUri;
        private LoadImageCallback callback;

        @SuppressWarnings("unused")
		public LoadImageUriTask(Context context,Uri imageUri, LoadImageCallback callback) {
        	this.context = context;
            this.imageUri = imageUri;
            this.callback = callback;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                InputStream inputStream;
                if (imageUri.getScheme().startsWith("http")
                        || imageUri.getScheme().startsWith("https")) {
                    inputStream = new URL(imageUri.toString()).openStream();
                    return BitmapFactory.decodeStream(inputStream);
                } else {
                    return getBitmapFromUri(context, imageUri, 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            callback.callback(result);
        }
    }

    private static class LoadSmallPicTask extends AsyncTask<Void, Void, Bitmap> {

        private final Uri imageUri;
        private final Context context;
        private LoadImageCallback callback;

        public LoadSmallPicTask(Context context, Uri imageUri, LoadImageCallback callback) {
            this.imageUri = imageUri;
            this.context = context;
            this.callback = callback;
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            return getResizedBitmap(context, imageUri, 200, 200);
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            callback.callback(result);
        }
    }
}
