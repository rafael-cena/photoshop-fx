package com.example.fotoxopfx;

import ij.ImagePlus;
import ij.process.ImageProcessor;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class Conversor {

    public static Image tonsCinza (Image image) {
        // converte um Image em BufferedImage
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        // captura pixels da imagem

        //           Red  Green Blue Alpha
        int pixel[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();
        for (int lin =0; lin < image.getHeight(); lin++) {
            for (int col =0; col < image.getWidth(); col++) {
                raster.getPixel(col,lin,pixel);  // obtenha um pixel

                //Transformando o pixel em escala cinza
                int tonsCinza = (int)(0.299*pixel[0]+0.587*pixel[1]+0.114*pixel[2]);
                pixel[0] = pixel[1] = pixel[2] = tonsCinza;
//                pixel[0] = 255-pixel[0];
//                pixel[1] = 255-pixel[1];
//                pixel[2] = 255-pixel[2];

                raster.setPixel(col,lin,pixel);  // reaplique o pixel
            }
        }

        // se necessário, volte para um Image
        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image pretoBranco (Image image) {
        image = tonsCinza(image);
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        // captura pixels da imagem

        //           Red  Green Blue Alpha
        int pixel[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();

        for (int lin =0; lin < image.getHeight(); lin++) {
            for (int col =0; col < image.getWidth(); col++) {
                raster.getPixel(col,lin,pixel);

                if (pixel[0] > 255/2) {
                    pixel[0] = pixel[1] = pixel[2] = 255;
                }
                else {
                    pixel[0] = pixel[1] = pixel[2] = 0;
                }

                raster.setPixel(col, lin, pixel);
            }
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image espelharHorizontal (Image image) {
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);

        int pixel[] = { 0 , 0 , 0 , 0 };
        int pixelEsp[] = { 0, 0, 0, 0 };
        WritableRaster raster=bimg.getRaster();

        int metLarg = (int) image.getWidth()/2;

        for (int lin =0; lin < image.getHeight(); lin++) {
            for (int col =0; col < metLarg; col++) {
                int colEsp = (int) image.getWidth() - col-1;

                raster.getPixel(col,lin,pixel);
                raster.getPixel(colEsp,lin,pixelEsp);

                raster.setPixel(col,lin,pixelEsp);
                raster.setPixel(colEsp,lin,pixel);
            }
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    static public Image detectarBordasIJ (Image image) {
        ImagePlus imagePlus = new ImagePlus();
        BufferedImage bimg = SwingFXUtils.fromFXImage(image, null);
        imagePlus.setImage(bimg);
        ImageProcessor imageProcessor = imagePlus.getProcessor();
        imageProcessor.findEdges();
        return SwingFXUtils.toFXImage(imagePlus.getBufferedImage(), null);
    }

    public static Image espelharVertical(Image image) {
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);

        int pixel[] = { 0 , 0 , 0 , 0 };
        int pixelEsp[] = { 0, 0, 0, 0 };
        WritableRaster raster=bimg.getRaster();

        int metAlt = (int) image.getHeight()/2;

        for (int lin =0; lin < metAlt; lin++) {
            for (int col =0; col < image.getWidth(); col++) {
                int linEsp = (int) image.getHeight() - lin-1;

                raster.getPixel(col,lin,pixel);
                raster.getPixel(col,linEsp,pixelEsp);

                raster.setPixel(col,lin,pixelEsp);
                raster.setPixel(col,linEsp,pixel);
            }
        }
        return SwingFXUtils.toFXImage(bimg, null);
    }

    public static Image negativo(Image image) {
        // converte um Image em BufferedImage
        BufferedImage bimg;
        bimg= SwingFXUtils.fromFXImage(image, null);
        // captura pixels da imagem

        //           Red  Green Blue Alpha
        int pixel[] = { 0 , 0 , 0 , 0 };
        WritableRaster raster=bimg.getRaster();
        for (int lin =0; lin < image.getHeight(); lin++) {
            for (int col =0; col < image.getWidth(); col++) {
                raster.getPixel(col,lin,pixel);
                pixel[0] = 255-pixel[0];
                pixel[1] = 255-pixel[1];
                pixel[2] = 255-pixel[2];

                raster.setPixel(col,lin,pixel);
            }
        }

        // se necessário, volte para um Image
        return SwingFXUtils.toFXImage(bimg, null);
    }
}
