package com.sdzk.buss.api.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class ReduceImgUtil {

    /**
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
     *
     * @param imgsrc     源图片地址
     * @param imgdist    目标图片地址
     * @param widthdist  压缩后图片宽度（当rate==null时，必传）
     * @param heightdist 压缩后图片高度（当rate==null时，必传）
     * @param rate       压缩比例
     */
    public static void reduceImg(String imgsrc, String imgdist, int widthdist,
                                 int heightdist, Float rate) {
        try {
            File srcfile = new File(imgsrc);
            // 检查文件是否存在
            if (!srcfile.exists()) {
                return;
            }
            // 如果rate不为空说明是按比例压缩
            if (rate != null && rate > 0) {
                // 获取文件高度和宽度
                int[] results = getImgWidthAndHeight(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return;
                } else {
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = ImageIO.read(srcfile);
            BufferedImage tag = new BufferedImage((int) widthdist,
                    (int) heightdist, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(
                    src.getScaledInstance(widthdist, heightdist,
                            Image.SCALE_SMOOTH), 0, 0, null);

            /*FileOutputStream out = new FileOutputStream(imgdist);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(tag);*/
            String formatName = imgdist.substring(imgdist.lastIndexOf(".") + 1);
            ImageIO.write(tag, formatName, new File(imgdist));
            //out.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取图片宽高
     *
     * @param file 图片文件
     */
    public static int[] getImgWidthAndHeight(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            is = new FileInputStream(file);
            src = ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽
            result[1] = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int[] getReduceWidthAndHeight(int standard, File file) {
        int[] results = getImgWidthAndHeight(file);
        int[] reduces = {0, 0};
        if (results == null || results[0] == 0 || results[1] == 0) {

        } else {
            int width = results[0];
            int height = results[1];
            if (width >= height) {
                reduces[0] = standard;
                reduces[1] = standard * height / width;
            } else {
                reduces[1] = standard;
                reduces[0] = standard * width / height;
            }
        }
        return reduces;
    }


    public static void main(String[] args) {
        System.out.println("压缩图片开始...");

        File srcfile = new File("F:\\uploadfile\\TEMP\\timg (1).jpg");
        System.out.println("压缩前srcfile size:" + srcfile.length());

        reduceImg("F:\\uploadfile\\TEMP\\timg (1).jpg", "F:\\uploadfile\\TEMP\\reduce.jpg", getReduceWidthAndHeight(100, srcfile)[0], getReduceWidthAndHeight(100, srcfile)[1], null);

        File distfile = new File("F:\\uploadfile\\TEMP\\reduce.jpg");
        System.out.println("压缩后distfile size:" + distfile.length());
    }
}
