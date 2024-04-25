/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;

public final class PressImageUtils {

    public PressImageUtils() { }

    /**
    * 把图片印刷到图片上
    * @param pressImg -- 水印文件
    * @param targetImg -- 目标文件
    * @param x  水平边距, 就是距离图片边缘的水平距离， 这个参数只有当水印位置是左上，左中，左下， 右上，右中，右下才有意义
    * @param y  垂直边距, 就是距离图片边缘的垂直距离， 这个参数只有当水印位置是左上，中上， 右上，左下，中下，右下才有意义
    */
    public final static void pressImage(String pressImg, String targetImg, int x, int y) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
            BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);

            // 水印文件
            File _filebiao = new File(pressImg);
            Image src_biao = ImageIO.read(_filebiao);
            int wideth_biao = src_biao.getWidth(null);
            int height_biao = src_biao.getHeight(null);
            g.drawImage(src_biao, wideth - wideth_biao - x, height - height_biao -y, wideth_biao,
            height_biao, null);
            // /
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            ImageIO.write(image, "jpeg", out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * 打印文字水印图片
    * @param pressText --文字
    * @param targetImg -- 目标图片
    * @param fontName -- 字体名  （宋体，楷体，黑体，Arial,Times New Roman等）
    * @param fontStyle -- 字体样式 （Font.PLAIN普通、Font.BOLD加粗、Font.ITALIC斜体、Font.BOLD+Font.ITALIC斜体加粗）
    * @param color -- 字体颜色
    * @param fontSize -- 字体大小
    * @param x -- 偏移量
    * @param y
    */
    public static void pressText(String pressText, String targetImg, String fontName,int fontStyle, int color, int fontSize, int x, int y) {
        try {
            File _file = new File(targetImg);
            Image src = ImageIO.read(_file);
            int wideth = src.getWidth(null);
            int height = src.getHeight(null);
            BufferedImage image = new BufferedImage(wideth, height,
            BufferedImage.TYPE_INT_RGB);
            Graphics g = image.createGraphics();
            g.drawImage(src, 0, 0, wideth, height, null);
            // String s="www.qhd.com.cn";
            g.setColor(Color.RED);
            g.setFont(new Font(fontName, fontStyle, fontSize));


            g.drawString(pressText, wideth - fontSize - x, height - fontSize/2 - y);
            g.dispose();
            FileOutputStream out = new FileOutputStream(targetImg);
            ImageIO.write(image, "jpeg", out);
            out.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        pressText("测试水印文字",
                "D:/picture/Desert.jpg","宋体" ,Font.BOLD,1,50,600,600);
    }
}
