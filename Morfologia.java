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

public class Morfologia extends Application {

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
        Button btnDo = new Button("Binaryzuj");
        Button btnEr = new Button("Eroduj");
        Button btnDil=new Button("Dilutuj");
        Button btnOp=new Button("Otwarcie");
        Button btnCl=new Button("Zamknięcie");
        ImageView imageView = new ImageView();
        ImageView Po=new ImageView();
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String, Number> chartHistogram
                = new LineChart<>(xAxis, yAxis);
        chartHistogram.setCreateSymbols(false); 
        btnEr.setOnAction((ActionEvent event) -> {
            Image image=Po.getImage();
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            WritableImage erode=new WritableImage(width,height);
            erode=erode(image);
            Po.setImage(erode);
        });
        btnOp.setOnAction((ActionEvent event) -> {
            Image image=Po.getImage();
            WritableImage Erode=erode(image);
            WritableImage Open=dilate(Erode);
            Po.setImage(Open);
        });
        btnCl.setOnAction((ActionEvent event) -> {
            Image image=Po.getImage();
            WritableImage Dilate=dilate(image);
            WritableImage Close=erode(Dilate);
            Po.setImage(Close);
        });
        btnDil.setOnAction((ActionEvent event) -> {
            Image image=Po.getImage();
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            WritableImage dilate=new WritableImage(width,height);
            dilate=dilate(image);
            Po.setImage(dilate);
        });
        btnDo.setOnAction((ActionEvent event) -> {

            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            double tr=127;
            int hist[]=new int[256];
            double pix=width*height;
            int ile=0;
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                        hist[(int)test]++;
                }
            }
            double tres=0;
            int i=0;
            int jes=0;
            int il=0;
            int jas=0;
            int kon=0;
            while(kon!=1){
                if(i<tr){
                    jes+=hist[i]*i;
                    ile+=hist[i];
                }
                else{
                    il+=hist[i];
                    jas+=hist[i]*i;
                }
                if(i>=255){
                    i=0;
                    jas=jas/il;
                    jes=jes/ile;
                    tres=(jes+jas)/2;
                    jas=jes=il=ile=0;
                    if(tr==tres){
                        kon=1;
                    }
                    else{
                        tr=tres;
                    }
                }
                else{
                    i++;
                }
            }
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                    if(test<tres){
                        writer.setColor(x,y,Color.BLACK);
                    }
                    else{
                        writer.setColor(x,y,Color.WHITE);
                    }
                }
            }
            Po.setImage(wImage);
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(Po);
        HBox buttons=new HBox();
        buttons.getChildren().addAll(btnDo,btnEr,btnDil,btnOp,btnCl);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, textSrc,buttons, hBox);
        
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
    WritableImage erode(Image image){
        int width=(int)image.getWidth();
        int height=(int)image.getHeight();
        WritableImage Er=new WritableImage(width,height);
        PixelReader pixelReader=image.getPixelReader();
        PixelWriter writer=Er.getPixelWriter();
        for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                    double test=color.getRed()*256;
                    if(test==0 ||x==0 || x==width-1 || y==0 || y==height-1){
                        writer.setColor(x, y, Color.BLACK);
                    }
                    else{
                        int ile=0;
                        for(int Y=y-1;Y<=y+1;Y++){
                            for(int X=x-1;X<=x+1;X++){
                               if(x!=X || y!=Y){
                                   Color set=pixelReader.getColor(X, Y);
                                   double czy=set.getRed()*256;
                                   if(czy>0){
                                       ile++;
                                   }
                               }
                            }
                        }
                        if(ile==8){
                            writer.setColor(x, y, Color.WHITE);
                        }
                        else{
                            writer.setColor(x, y, Color.BLACK);
                        }
                    }
                }
            }
        return Er;
    }
    WritableImage dilate(Image image){
        int width=(int)image.getWidth();
        int height=(int)image.getHeight();
        WritableImage Di=new WritableImage(width,height);
        PixelReader pixelReader=image.getPixelReader();
        PixelWriter writer=Di.getPixelWriter();
        for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                    double test=color.getRed()*256;
                    if(test==0||x==0 || x==width-1 || y==0 || y==height-1){
                        writer.setColor(x, y, Color.BLACK);
                    }
                    else{
                        for(int Y=y-1;Y<=y+1;Y++){
                            for(int X=x-1;X<=x+1;X++){
                               if(X>=0 || X<width || Y>=0||Y<height)
                                   writer.setColor(X, Y, Color.WHITE);
                            }
                        }
                    }
                }
            }
        return Di;
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