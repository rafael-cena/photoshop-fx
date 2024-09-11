package com.example.fotoxopfx;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.AnnotatedArrayType;
import java.net.URL;
import java.nio.Buffer;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public ImageView imageView;
    public File file;

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
        file = fileChooser.showOpenDialog(null);

        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(800);
            imageView.setFitHeight(600);
        }
    }

    public void onSalvar(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        if (file != null) {
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
                ImageIO.write(copy, "jpg", file);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void onSalvarComo(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("D://"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.webp")
                ,new FileChooser.ExtensionFilter("jpg", "*.jpg")
                ,new FileChooser.ExtensionFilter("jpeg", "*.jpeg")
                ,new FileChooser.ExtensionFilter("png", "*.png")
                ,new FileChooser.ExtensionFilter("bmp", "*.bmp")
        );
        File arq = fileChooser.showSaveDialog(null);

        Image image = imageView.getImage();
        if (arq != null) {
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
                ImageIO.write(copy, "jpg", arq);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void onSair(ActionEvent actionEvent) {
        Image image = imageView.getImage();
        Image initImage = new Image(file.toURI().toString());

        if (!Conversor.compararImagens(image, initImage)) {
            System.out.println("img diferente");
            if (JOptionPane.showConfirmDialog(null, "Deseja sair?", "Confirmação", JOptionPane.YES_NO_OPTION) == 1) {
                Platform.exit();
            }
            else if (JOptionPane.showConfirmDialog(null, "Deseja sair mesmo assim?", "Você possui alterações NÃO salvas", JOptionPane.YES_NO_OPTION) == 1) {
                Platform.exit();
            }
        }
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

    public void onSobre(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PhotoShopFX.class.getResource("about-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 400);
        Stage stage = new Stage();
        stage.setTitle("Sobre o Aplicativo");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.initStyle(StageStyle.DECORATED);
        stage.showAndWait();
    }

    public void onNegativo(ActionEvent actionEvent) {
        imageView.setImage(Conversor.negativo(imageView.getImage()));
    }
}