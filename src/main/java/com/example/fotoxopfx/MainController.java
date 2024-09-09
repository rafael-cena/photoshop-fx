package com.example.fotoxopfx;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ImageView imageView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onAbrir(ActionEvent actionEvent) {
        FileChooser fileChooser;
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D://"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.webp")
                ,new FileChooser.ExtensionFilter("JPG", "*.jpg")
                ,new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
                ,new FileChooser.ExtensionFilter("PNG", "*.png")
                ,new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);
        }
    }

    public void onSalvar(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D://"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.webp")
                ,new FileChooser.ExtensionFilter("JPG", "*.jpg")
                ,new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
                ,new FileChooser.ExtensionFilter("PNG", "*.png")
                ,new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        File arq = fileChooser.showSaveDialog(null);
        if (arq != null) {
            Image image = imageView.getImage();
            BufferedImage bimg = SwingFXUtils.fromFXImage(image, null);
            /* tratamento para salvar uma imagem modificada*/
            BufferedImage copy = new BufferedImage(bimg.getWidth(), bimg.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = copy.createGraphics();
            g2d.setColor(Color.WHITE); // Or what ever fill color you want...
            g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
            g2d.drawImage(bimg, 0, 0, null);
            g2d.dispose();
            /* fim do tratamento*/
            try {
                ImageIO.write(bimg, String.valueOf(fileChooser.getSelectedExtensionFilter()), arq);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void onSalvarComo(ActionEvent actionEvent) {
    }

    public void onSair(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void onTonsCinza(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversor.tonsCinza(image);
        imageView.setImage(image);
    }

    public void onPretoBranco(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        image = Conversor.pretoBranco(image);
        imageView.setImage(image);
    }

    public void onEspelharHorizontal(ActionEvent actionEvent) {
        imageView.setImage(Conversor.espelharHorizontal(imageView.getImage()));
    }

    public void onDetectarBordas(ActionEvent actionEvent) {
        imageView.setImage(Conversor.detectarBordasIJ(imageView.getImage()));
    }

    public void onEspelharVertical(ActionEvent actionEvent) {
        imageView.setImage(Conversor.espelharVertical(imageView.getImage()));
    }
}