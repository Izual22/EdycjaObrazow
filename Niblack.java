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
import java.lang.Math;
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

public class Niblack extends Application {

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

            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            double his[]= new double[256];
            double mean=0;
            double test=0;
            double tresh;
            double st[]=new double[10];
            double s=0;
            double k=-0.2;
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int argb = pixelReader.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    test=(r+g+b)/3;
                    mean+=(r+g+b)/3;
                    his[0]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x-1,y-1);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[1]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x-1,y);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[2]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x,y-1);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[3]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x+1,y+1);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[4]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x,y+1);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[5]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x+1,y);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[6]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x+1,y-1);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[7]=(r+g+b)/3;
                    argb=pixelReader.getArgb(x-1,y+1);
                    r=(0xff & (argb >> 16));
                    g= (0xff & (argb >> 8));
                    b= (0xff & argb);
                    mean+=(r+g+b)/3;
                    his[8]=(r+g+b)/3;
                    mean=mean/9;
                    for(int i=0;i<9;i++){
                        st[i]=his[i]-mean;
                        s+=st[i]*st[i];
                    }
                    s=s/9;
                    s=Math.sqrt(s);
                    tresh=mean+k*s;
                    if(test>tresh){
                        writer.setColor(x,y,Color.WHITE);
                    }
                    else{
                        writer.setColor(x,y,Color.BLACK);
                    }
                }
            }
          
            Po.setImage(wImage);
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

        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageView, chartHistogram, Po);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, textSrc, btnDo, hBox);

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