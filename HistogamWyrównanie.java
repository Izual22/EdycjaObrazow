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
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

public class HistogamWyrównanie extends Application {

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
        Button btnDo = new Button("Do Histogram");
        ImageView imageView = new ImageView();
        ImageView Po=new ImageView();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String, Number> chartHistogram
                = new LineChart<>(xAxis, yAxis);
        chartHistogram.setCreateSymbols(false);

        btnDo.setOnAction((ActionEvent event) -> {
            double LUTr[]=new double[256],
            LUTg[]=new double[256],
            LUTb[]=new double[256];
            double R[]=new double[256];
            double G[]=new double[256];
            double B[]=new double[256];
            double DR[]=new double[256];
            double DG[]=new double[256];
            double DB[]=new double[256];
            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    int argb = pixelReader.getArgb(x, y);
                    int a = (0xff & (argb >> 24));
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    R[r]++;
                    G[g]++;
                    B[b]++;
                }
            }
            int size=height*width;
            double sumR=0;
            double sumG=0;
            double sumB=0;
            for(int i=0;i<256;i++){
                sumR+=(R[i]/size);
                sumG+=(G[i]/size);
                sumB+=(B[i]/size);
                DR[i]+=sumR;
                DG[i]+=sumG;
                DB[i]+=sumB;
            }
            int i =0;
            double D0;
            while(DR[i]==0)i++;
            D0=DR[i];
            for(i=0;i<256;i++){
                LUTr[i]=(((DR[i]-D0)/(1-D0))*(256-i));
            }
            i=0;
            while(DG[i]==0)i++;
            D0=DG[i];
            for(i=0;i<256;i++){
                LUTg[i]=(((DG[i]-D0)/(1-D0))*(256-i));
            }
            i=0;
            while(DB[i]==0)i++;
            D0=DB[i];
            for(i=0;i<256;i++){
                LUTb[i]=(((DB[i]-D0)/(1-D0))*(256-i));
            }
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    int argb = pixelReader.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    r=(int)LUTr[r];
                    g=(int)LUTg[g];
                    b=(int)LUTb[b];
                    Color col=Color.rgb(r, g, b);
                    writer.setColor(x,y,col);
                }
            }
            
            Po.setImage(wImage);
            chartHistogram.getData().clear();
            ImageHistogram imageHistogram = new ImageHistogram(wImage);
            if(imageHistogram.isSuccess()){
                chartHistogram.getData().addAll(
                    //imageHistogram.getSeriesAlpha(),
                    imageHistogram.getSeriesRed(),
                    imageHistogram.getSeriesGreen(),
                    imageHistogram.getSeriesBlue());
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageView, chartHistogram, Po);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, textSrc, btnDo, hBox);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("Histogram");
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