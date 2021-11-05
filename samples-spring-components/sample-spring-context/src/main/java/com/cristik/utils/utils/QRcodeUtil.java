package com.cristik.utils.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;

/**
 * 条形码和二维码编码解码
 */
public class QRcodeUtil {

    /**
     * 条形码编码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void encode(String contents, int width, int height, String imgPath) {
        int codeWidth = 3 + // start guard
                (7 * 6) + // left bars
                5 + // middle guard
                (7 * 6) + // right bars
                3; // end guard
        codeWidth = Math.max(codeWidth, width);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.EAN_13, codeWidth, height, null);
            MatrixToImageWriter.writeToPath(bitMatrix, "png", new File(imgPath).toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 条形码解码
     */
    public static String decode(String imgPath) {
        BufferedImage image;
        Result result;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            result = new MultiFormatReader().decode(bitmap, null);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 二维码编码
     *
     * @param contents
     * @param width
     * @param height
     * @param imgPath
     */
    public static void encode2(String contents, int width, int height, String imgPath) {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        // 指定纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 指定编码格式
        hints.put(EncodeHintType.CHARACTER_SET, "GBK");
        hints.put(EncodeHintType.MARGIN, 0);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
                    BarcodeFormat.QR_CODE, width, height, hints);

            MatrixToImageWriter
                    .writeToFile(bitMatrix, "png", new File(imgPath));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 二维码解码
     *
     * @param imgPath
     * @return String
     */
    public static String decode2(String imgPath) {
        BufferedImage image = null;
        Result result = null;
        try {
            image = ImageIO.read(new File(imgPath));
            if (image == null) {
                System.out.println("the decode image may be not exit.");
            }
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
            hints.put(DecodeHintType.CHARACTER_SET, "GBK");

            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // 条形码
        String imgPath = "target/条形码.png";
        String contents = "6923450657713";
        int width = 105, height = 50;

        QRcodeUtil.encode(contents, width, height, imgPath);
        System.out.println("生成条形码");

        String decodeContent = QRcodeUtil.decode(imgPath);
        System.out.println("条形码解析内容：" + decodeContent);

        // 二维码
        String imgPath2 = "target/二维码.png";
        String contents2 = "http://www.baidu.com";
        int width2 = 150, height2 = 150;

        QRcodeUtil.encode2(contents2, width2, height2, imgPath2);
        System.out.println("二维码已生成");

        String decodeContent2 = QRcodeUtil.decode2(imgPath2);
        System.out.println("二维码解码内容：" + decodeContent2);

    }

}