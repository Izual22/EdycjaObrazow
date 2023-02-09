/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication1;

/**
 *
 * @author piotr
 */

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.lang.Math;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Oczernij extends Application {

    String defaultImage = "https://www.ultimate-photo-tips.com/image-files/histogram-dark-2.jpg";

    @Override
    public void start(Stage primaryStage) {

        Label labelInfo = new Label();
        labelInfo.setText(
                "java.version: " + System.getProperty("java.version") + "\n"
                + "javafx.runtime.version: " + System.getProperty("javafx.runtime.version")
        );

        TextField textSrc = new TextField();
        textSrc.setText(defaultImage);
        Slider slider=new Slider(100,1000,100);
        slider.setMajorTickUnit(100.0);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        Slider przediał=new Slider(0,100,0);
        przediał.setMajorTickUnit(20.0);
        przediał.setMinorTickCount(0);
        przediał.setSnapToTicks(true);
        przediał.setShowTickLabels(true);
        przediał.setShowTickMarks(true);
        Button btnDo = new Button("Pobierz obrazek");
        ImageView imageView = new ImageView();
        ImageView Po=new ImageView();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String, Number> chartHistogram
                = new LineChart<>(xAxis, yAxis);
        chartHistogram.setCreateSymbols(false);

        btnDo.setOnAction((ActionEvent event) -> {

            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            Po.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            chartHistogram.getData().clear();
            ImageHistogram imageHistogram = new ImageHistogram(image);
            if(imageHistogram.isSuccess()){
                chartHistogram.getData().addAll(
                    //imageHistogram.getSeriesAlpha(),
                    imageHistogram.getSeriesRed(),
                    imageHistogram.getSeriesGreen(),
                    imageHistogram.getSeriesBlue());
            }
        });
        Po.setOnMouseClicked(event->{
                int x=(int)event.getX();
                int y=(int)event.getY();
                Image image=Po.getImage();
                int width=(int)image.getWidth();
                int height=(int)image.getHeight();
                PixelReader pixelReader=image.getPixelReader();
                WritableImage wImage=new WritableImage(pixelReader,width,height);
                PixelWriter writer=wImage.getPixelWriter();
                int ile=(int)slider.getValue();
                int narazie=0;
                int argb=pixelReader.getArgb(x, y);
                int a = (0xff & (argb >> 24));
                int r = (0xff & (argb >> 16));
                int g = (0xff & (argb >> 8));
                int b = (0xff & argb);
                int pomy=0;
                int pomx=0;
                if(ile!=1000){
                    pomx=x;
                    pomy=y;
                }
                for(int yd=pomy;yd<height && narazie!=ile;yd++){
                    for(int xd=pomx;xd<width && narazie!=ile;xd++){
                        int ARGB=pixelReader.getArgb(xd,yd);
                        int trzy=3;
                        int A = (0xff & (ARGB >> 24));
                        int R = (0xff & (ARGB >> 16));
                        int G = (0xff & (ARGB >> 8));
                        int B = (0xff & ARGB);
                        int tresh=(int)przediał.getValue();
                        if(r+tresh>=R && r-tresh<=R){
                            trzy--;
                        }
                        if(b+tresh>=B && b-tresh<=B){
                            trzy--;
                        }
                        if(g+tresh>=G && g-tresh<=G){
                            trzy--;
                        }
                        if(trzy<=1){
                            writer.setColor(xd, yd, Color.BLACK);
                            if(ile!=1000)
                            {
                                narazie++;
                            }
                        }
                        else if(ile!=1000){
                            yd++;
                            xd=pomx;
                        }
                    }
                }
                Po.setImage(wImage);
            });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(Po);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, textSrc, btnDo, slider,przediał, hBox);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("Histogram i Otsu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    class ImageHistogram {

        private Image image;

        private long alpha[] = new long[256];
        private long red[] = new long[256];
        private long green[] = new long[256];
        private long blue[] = new long[256];

        XYChart.Series seriesAlpha;
        XYChart.Series seriesRed;
        XYChart.Series seriesGreen;
        XYChart.Series seriesBlue;

        private boolean success;

        ImageHistogram(Image src) {
            image = src;
            success = false;

            //init
            for (int i = 0; i < 256; i++) {
                alpha[i] = red[i] = green[i] = blue[i] = 0;
            }

            PixelReader pixelReader = image.getPixelReader();
            if (pixelReader == null) {
                return;
            }

            //count pixels
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int argb = pixelReader.getArgb(x, y);
                    int a = (0xff & (argb >> 24));
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);

                    alpha[a]++;
                    red[r]++;
                    green[g]++;
                    blue[b]++;

                }
            }

            seriesAlpha = new XYChart.Series();
            seriesRed = new XYChart.Series();
            seriesGreen = new XYChart.Series();
            seriesBlue = new XYChart.Series();
            seriesAlpha.setName("alpha");
            seriesRed.setName("red");
            seriesGreen.setName("green");
            seriesBlue.setName("blue");

            //copy alpha[], red[], green[], blue[]
            //to seriesAlpha, seriesRed, seriesGreen, seriesBlue
            for (int i = 0; i < 256; i++) {
                seriesAlpha.getData().add(new XYChart.Data(String.valueOf(i), alpha[i]));
                seriesRed.getData().add(new XYChart.Data(String.valueOf(i), red[i]));
                seriesGreen.getData().add(new XYChart.Data(String.valueOf(i), green[i]));
                seriesBlue.getData().add(new XYChart.Data(String.valueOf(i), blue[i]));
            }

            success = true;
        }

        public boolean isSuccess() {
            return success;
        }
      
        public XYChart.Series getSeriesAlpha() {
            return seriesAlpha;
        }

        public XYChart.Series getSeriesRed() {
            return seriesRed;
        }

        public XYChart.Series getSeriesGreen() {
            return seriesGreen;
        }

        public XYChart.Series getSeriesBlue() {
            return seriesBlue;
        }
    }

}