package com.lweishi.utils;

import com.lweishi.exception.GlobalException;
import com.lweishi.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.*;

@Component
public class ColorUtils {

    @Autowired
    private UploadService uploadService;

    public static final int originWidth=132; //图片宽度
    public static final int originHeight=132; //图片高度

    public static final int width=128; //外环宽度
    public static final int height=128; //外环高度

    public String paintInActiveImg(String fileName, String colorHex) {
        try {
            String inactiveBorderColorHex = "BBBBBB";
            BufferedImage img = paintImg(colorHex, inactiveBorderColorHex);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(img, "png", out);
            byte[] bytes = out.toByteArray();
            MultipartFile multipartFile = new MultipartFile() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public String getOriginalFilename() {
                    return fileName;
                }

                @Override
                public String getContentType() {
                    return null;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public long getSize() {
                    return 1000;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return bytes;
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return null;
                }

                @Override
                public void transferTo(File file) throws IOException, IllegalStateException {

                }
            };
            String imgUrl = uploadService.uploadImage(multipartFile, fileName);

            return imgUrl;
        } catch (IOException e) {
            throw new GlobalException(30001, "生成颜色图片失败！");
        }
        //写出图片到本地
//        ImageIO.write(res, "PNG", new FileOutputStream("/Users/sen____/" + fileName + ".png"));//保存图片 JPEG表示保存格式
    }

    public String paintActiveImg(String fileName, String colorHex)  {
        try {
            String path = System.getProperty("user.dir") + "/imgs/";
            File file = new File(path + "checked.png");
            BufferedImage checkedImg = ImageIO.read(file);
            String inactiveBorderColorHex = "F4516C";
            BufferedImage img = paintImg(colorHex, inactiveBorderColorHex);
            Graphics2D g2d = img.createGraphics();
            g2d.drawImage(checkedImg, originWidth / 2 -24, originHeight / 2 - 24, null);
            g2d.dispose();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(img, "png", out);
            byte[] bytes = out.toByteArray();
            MultipartFile multipartFile = new MultipartFile() {
                @Override
                public String getName() {
                    return null;
                }

                @Override
                public String getOriginalFilename() {
                    return fileName;
                }

                @Override
                public String getContentType() {
                    return null;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public long getSize() {
                    return 1000;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return bytes;
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return null;
                }

                @Override
                public void transferTo(File file) throws IOException, IllegalStateException {

                }
            };
            String imgUrl = uploadService.uploadImage(multipartFile, fileName);

            return imgUrl;
        } catch (IOException e) {
            throw new GlobalException(30001, "生成颜色图片失败！");
        }

//        ImageIO.write(res2, "PNG", new FileOutputStream("/Users/sen____/" + fileName + ".png"));//保存图片 JPEG表示保存格式
    }

    private static BufferedImage paintImg(String colorHex, String inactiveBorderColorHex) throws IOException {
        BufferedImage image = new BufferedImage(originWidth,originHeight,BufferedImage.TYPE_INT_RGB);//INT精确度达到一定,RGB三原色，高度70,宽度150

        //得到它的绘制环境(这张图片的笔)
        Graphics2D g2 = (Graphics2D) image.getGraphics();

        //消除锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //加粗
        g2.setStroke(new BasicStroke(2f));
        //设置外环颜色
        g2.setBackground(Color.white);
        g2.clearRect(0, 0, originWidth, originHeight);//通过使用当前绘图表面的背景色进行填充来清除指定的矩形。
        g2.setPaint(ColorUtils.toColorFromString("0xFF" + inactiveBorderColorHex));
        //绘制外环空心圆
        g2.drawOval(2,2,width,height);

        //加粗
        g2.setStroke(new BasicStroke(2f));
        //设置内环颜色
        g2.setColor(ColorUtils.toColorFromString("0xFF" + colorHex));
        //绘制内环实心圆
        g2.fillOval(17,17,100,100);

        //释放资源
        g2.dispose();
        //裁剪成圆形图片
        BufferedImage res = convertCircular(image);

        return res;
    }

    /**
     * 传入的图像必须是 正方形 的才会裁剪成圆形，如果是 长方形 的比例则会变成椭圆。
     *
     * @param bi1  用户头像地址
     * @return
     * @throws IOException
     */
    public static BufferedImage convertCircular(BufferedImage bi1) {
        // 透明底的图片
        BufferedImage bi2 = new BufferedImage(originWidth, originHeight, BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, bi1.getWidth(), bi1.getHeight());
        Graphics2D g2 = bi2.createGraphics();
        // 设置抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setClip(shape);
        g2.drawImage(bi1, 0, 0, null);

        g2.dispose();
        return bi2;
    }


    /**
     * 字符串转换成Color对象
     * @param colorStr 16进制颜色字符串
     * @return Color对象
     * */
    public static Color toColorFromString(String colorStr){
        colorStr = colorStr.replace("#", "").substring(4);
        Color color =  new Color(Integer.parseInt(colorStr, 16)) ;
        //java.awt.Color[r=0,g=0,b=255]
        return color;
    }
}
