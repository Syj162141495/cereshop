/*
 * Copyright (C) 2017-2021
 * All rights reserved, Designed By 深圳中科鑫智科技有限公司
 * Copyright authorization contact 18814114118
 */
package com.shop.cereshop.commons.utils;


import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.*;
import java.util.Iterator;

@Slf4j
public class ImageUtil {


    /**
     * 从图片的MultipartFile获取InputStream
     *
     * @param multipartFile MultipartFile
     * @return InputStream
     */
    public static InputStream getImgInputStream(MultipartFile multipartFile) {
        InputStream inputStream;
        try {
            byte[] bytes = multipartFile.getBytes();
            inputStream = new ByteArrayInputStream(bytes);
            return inputStream;
        } catch (IOException e) {
            log.error("ImageUtil.getImgInputStream error:{}", e);
            return null;
        }
    }


    /**
     * 转换图片格式为jpg，保持原尺寸不变，并获取新的InputStream
     *
     * @param imageInputStream InputStream
     * @return InputStream
     */
    public static InputStream formatToJPG(InputStream imageInputStream) {
        InputStream inputStream;
        try {
            OutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(imageInputStream).scale(1f).outputFormat("png").toOutputStream(outputStream);
            byte[] buf = ((ByteArrayOutputStream) outputStream).toByteArray();
            inputStream = new ByteArrayInputStream(buf);
            return inputStream;
        } catch (IOException e) {
            log.error("ImageUtil.formatToJPG error:{}", e);
            return null;
        }
    }

    /**
     * 转换图片格式为jpg，保持原尺寸不变，并获取新的InputStream
     *
     * @param multipartFile MultipartFile
     * @return InputStream
     */
    public static InputStream formatToJPG(MultipartFile multipartFile) {
        InputStream imgInputStream = getImgInputStream(multipartFile);

        String filename = multipartFile.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        if ("jpg".equals(suffix)) {
            return imgInputStream;
        }
        return formatToJPG(imgInputStream);

    }


    /**
     * 压缩图片文件大小,图片尺寸不变
     *
     * @param imgInputStream InputStream
     * @param quality        图片质量
     * @return InputStream
     */
    public static InputStream compressImg(InputStream imgInputStream, float quality) {
        OutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream;
        try {
            if (quality > 1) {
                quality = 1;
            } else if (quality <= 0) {
                quality = 0.5f;
            }
            Thumbnails.of(imgInputStream).scale(1f).outputQuality(quality).outputFormat("png").toOutputStream(outputStream);
            byte[] buf = ((ByteArrayOutputStream) outputStream).toByteArray();
            inputStream = new ByteArrayInputStream(buf);
            return inputStream;
        } catch (IOException e) {
            log.error("ImageUtil.compressImg error:{}", e);
            return null;
        }
    }

    /**
     * 压缩图片文件大小,图片尺寸不变
     *
     * @param multipartFile MultipartFile
     * @param quality       图片质量
     * @return InputStream
     */
    public static InputStream compressImg(MultipartFile multipartFile, float quality) {
        InputStream imgInputStream = formatToJPG(multipartFile);
        return compressImg(imgInputStream, quality);
    }

    /**
     * 压缩图片文件大小,图片尺寸不变
     *
     * @param base64  base64编码的图片
     * @param quality 图片质量
     * @return InputStream
     */
    public static InputStream compressImg(String base64, float quality) {
        try {
            byte[] images = Base64Util.decode(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(images);
            return compressImg(inputStream, quality);
        }catch (Exception e){
            e.printStackTrace();
        }
            return null;
    }

    /**
     * 压缩图片文件大小,图片尺寸不变
     *
     * @param imgInputStream 图片imgInputStream
     * @param quality        图片质量
     * @return base64编码的图片
     */
    public static String compressImgReturnBase64(InputStream imgInputStream, float quality) {
        OutputStream outputStream = new ByteArrayOutputStream();
        try {
            if (quality > 1) {
                quality = 1.0f;
            } else if (quality <= 0) {
                quality = 0.5f;
            }
            Thumbnails.of(imgInputStream).scale(1f).outputQuality(quality).outputFormat("png").toOutputStream(outputStream);
            byte[] buf = ((ByteArrayOutputStream) outputStream).toByteArray();
            return Base64Util.encode(buf);
        } catch (IOException e) {
            log.error("ImageUtil.compressImg error:{}", e);
            return null;
        }
    }

    /**
     * 压缩图片文件大小,图片尺寸不变
     *
     * @param multipartFile MultipartFile
     * @param quality       图片质量
     * @return base64编码的图片
     */
    public static String compressImgReturnBase64(MultipartFile multipartFile, float quality) {
        InputStream inputStream = formatToJPG(multipartFile);
        return compressImgReturnBase64(inputStream, quality);
    }

    /**
     * 压缩图片文件大小,图片尺寸不变
     *
     * @param base64  图片base64编码
     * @param quality 图片质量
     * @return base64编码的图片
     */
    public static String compressImgReturnBase64(String base64, float quality) {
        try {
            byte[] images = Base64Util.decode(base64);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(images);
            return compressImgReturnBase64(inputStream, quality);
        }catch (Exception e){
            e.printStackTrace();
        }
            return null;
    }


    /**
     * 按照指定比例进行缩小和放大
     *
     * @param imgInputStream InputStream
     * @param coefficient    缩放系数,coefficient大于1为放大，小于1且大于0为缩小
     * @return InputStream
     */
    public static InputStream zoomImg(InputStream imgInputStream, float coefficient) {
        OutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream;
        try {
            if (coefficient < 0) {
                coefficient = 0.5f;
            }
            Thumbnails.of(imgInputStream).scale(coefficient).outputFormat("png").toOutputStream(outputStream);
            byte[] buf = ((ByteArrayOutputStream) outputStream).toByteArray();
            inputStream = new ByteArrayInputStream(buf);
            return inputStream;
        } catch (IOException e) {
            log.error("ImageUtil.zoomImg error:{}", e);
            return null;
        }

    }

    /**
     * 按照指定比例进行缩小和放大
     *
     * @param multipartFile MultipartFile
     * @param coefficient   缩放系数,coefficient大于1为放大，小于1且大于0为缩小
     * @return InputStream
     */
    public static InputStream zoomImg(MultipartFile multipartFile, float coefficient) {
        InputStream imgInputStream = formatToJPG(multipartFile);
        return zoomImg(imgInputStream, coefficient);
    }

    /**
     * 裁剪图片
     *
     * @param imgInputStream InputStream
     * @param x              x方向
     * @param y              y方向
     * @return InputStream
     */
    public static InputStream cutImg(InputStream imgInputStream, int x, int y) {
        OutputStream outputStream = new ByteArrayOutputStream();
        InputStream inputStream;
        try {
            Thumbnails.of(imgInputStream).size(x, y).keepAspectRatio(false).outputFormat("png").toOutputStream(outputStream);
            byte[] buf = ((ByteArrayOutputStream) outputStream).toByteArray();
            inputStream = new ByteArrayInputStream(buf);
            return inputStream;
        } catch (IOException e) {
            log.error("ImageUtil.cutImg error:{}", e);
            return null;
        }
    }

    /**
     * 裁剪图片
     *
     * @param multipartFile MultipartFile
     * @param x             x方向
     * @param y             y方向
     * @return InputStream
     */
    public static InputStream cutImg(MultipartFile multipartFile, int x, int y) {
        InputStream imgInputStream = formatToJPG(multipartFile);
        return cutImg(imgInputStream, x, y);
    }

    /**
     * 给某张图片进行标记，并输出标记后的新图片流
     *
     * @param inputStream 输入流
     * @param origin_x    标记框x坐标起点
     * @param origin_y    标记框y坐标起点
     * @param length_x    标记框x方向长度
     * @param length_y    标记框y方向长度
     * @return
     * @throws Exception
     */
    public static InputStream drawFaceBox(InputStream inputStream, int origin_x, int origin_y, int length_x, int length_y) throws Exception {
        BufferedImage image = ImageIO.read(inputStream);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.RED);//画笔颜色
        g2.drawRect(origin_x, origin_y, length_x, length_y);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
        g2.setStroke(new BasicStroke(10.0f));//设置线宽为3.0
        OutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpeg", outputStream);
        byte[] buf = ((ByteArrayOutputStream) outputStream).toByteArray();
        inputStream = new ByteArrayInputStream(buf);
        g2.dispose();
        return inputStream;
    }

    public static InputStream drawFaceBox(byte[] bytes, int origin_x, int origin_y, int length_x, int length_y) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return drawFaceBox(inputStream, origin_x, origin_y, length_x, length_y);
    }


    /**
     * 给某张图片绘制标记框，并输出至指定位置
     *
     * @param inputStream 输入流
     * @param origin_x    标记框x坐标起点
     * @param origin_y    标记框y坐标起点
     * @param length_x    标记框x方向长度
     * @param length_y    标记框y方向长度
     * @param outputPath  输出路径，例如F：//test
     * @param fileName    输出文件的名称，不需要加后缀名
     * @throws Exception
     */
    public static void drawFaceBoxToFile(InputStream inputStream,
                                         int origin_x, int origin_y,
                                         int length_x, int length_y, String outputPath, String fileName) throws Exception {
        BufferedImage image = ImageIO.read(inputStream);
        Graphics2D g2 = image.createGraphics();
        g2.setColor(Color.RED);//画笔颜色
        g2.drawRect(origin_x, origin_y, length_x, length_y);//矩形框(原点x坐标，原点y坐标，矩形的长，矩形的宽)
        g2.setStroke(new BasicStroke(50.0f));//设置线宽为3.0
        String path = outputPath.endsWith("/") ? (outputPath + fileName + ".jpg") : (outputPath + "/" + fileName + ".jpg");
        FileOutputStream out = new FileOutputStream(path);//输出图片的地址
        ImageIO.write(image, "jpeg", out);
        g2.dispose();
        out.close();
    }

    public static void drawFaceBoxToFile(byte[] bytes,
                                         int origin_x, int origin_y,
                                         int length_x, int length_y, String outputPath, String fileName) throws Exception {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        drawFaceBoxToFile(inputStream, origin_x, origin_y, length_x, length_y, outputPath, fileName);
    }

    public static void readImage(File file) throws IOException {
        ImageInputStream input = ImageIO.createImageInputStream(file);
        Iterator readers = ImageIO.getImageReaders(input);
        if(readers == null || !readers.hasNext()) {
            throw new RuntimeException("1 No ImageReaders found");
        }
        ImageReader reader = (ImageReader) readers.next();
        reader.setInput(input);
        String format = reader.getFormatName() ;
        BufferedImage image;

        if ( "JPEG".equalsIgnoreCase(format) ||"JPG".equalsIgnoreCase(format) )   {
            try {
                // 尝试读取图片 (包括颜色的转换).
                image = reader.read(0); //RGB
            } catch (IIOException e) {
                // 读取Raster (没有颜色的转换).
                Raster raster = reader.readRaster(0, null);//CMYK
                image = createJPEG4(raster);
            }
            image.getGraphics().drawImage(image, 0, 0, null);
            //String newfilename = filename.substring(0,filename.lastIndexOf("."))+"_rgb"+filename.substring(filename.lastIndexOf("."));
            String newfilename = file.getName();
            File newFile = new File(newfilename);
            FileOutputStream out = new FileOutputStream(newFile);
            ImageIO.write(image, "jpeg", out);
            out.flush();
            out.close();
            input.close();
        }
    }

    private static BufferedImage createJPEG4(Raster raster) {
        int w = raster.getWidth();
        int h = raster.getHeight();
        byte[] rgb = new byte[w * h * 3];
        //彩色空间转换
        float[] Y = raster.getSamples(0, 0, w, h, 0, (float[]) null);
        float[] Cb = raster.getSamples(0, 0, w, h, 1, (float[]) null);
        float[] Cr = raster.getSamples(0, 0, w, h, 2, (float[]) null);
        float[] K = raster.getSamples(0, 0, w, h, 3, (float[]) null);
        for (int i = 0, imax = Y.length, base = 0; i < imax; i++, base += 3) {
            float k = 220 - K[i], y = 255 - Y[i], cb = 255 - Cb[i],
                    cr = 255 - Cr[i];

            double val = y + 1.402 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);

            val = y - 0.34414 * (cb - 128) - 0.71414 * (cr - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 1] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);

            val = y + 1.772 * (cb - 128) - k;
            val = (val - 128) * .65f + 128;
            rgb[base + 2] = val < 0.0 ? (byte) 0 : val > 255.0 ? (byte) 0xff
                    : (byte) (val + 0.5);
        }
        raster = Raster.createInterleavedRaster(new DataBufferByte(rgb, rgb.length), w, h, w * 3, 3, new int[]{0, 1, 2}, null);
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorModel cm = new ComponentColorModel(cs, false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
        return new BufferedImage(cm, (WritableRaster) raster, true, null);
    }

    /**
     * MultipartFile 转 File
     *
     * @param file
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile file) throws Exception {

        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            InputStream ins = null;
            ins = file.getInputStream();
            toFile = new File(file.getOriginalFilename());
            inputStreamToFile(ins, toFile);
            ins.close();
        }
        return toFile;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
