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

public class Brensen extends Application {

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
        Label zle=new Label();

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
            double M=1,m=255;
            int global=128;
            double vote[]=new double[256];
            WritableImage wImage=new WritableImage(width,height);
            WritableImage Suavola=new WritableImage(width,height);
            WritableImage Otsu=new WritableImage(width,height);
            WritableImage Niblack=new WritableImage(width,height);
            WritableImage Brensen=new WritableImage(width,height);
            Suavola jeden=new Suavola(image);
            Otsu dwa=new Otsu(image);
            Niblack trzy=new Niblack(image);
            Niblack=trzy.zmien();
            Otsu=dwa.zmien();
            Suavola=jeden.zmien();
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=Brensen.getPixelWriter();
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int AR = pixelReader.getArgb(x, y);
                    int R = (0xff & (AR >> 16));
                    int G = (0xff & (AR >> 8));
                    int B = (0xff & AR);
                    double checked=(R+G+B)/3;
                    for(int Y=y-1;Y<=y+1;Y++){    
                    for(int X=x-1;X<=x+1;X++){
                    int argb = pixelReader.getArgb(X, Y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    test=(r+g+b)/3;
                    M=Math.max(M, test);
                    m=Math.min(m,test);
                    }
                    }
                    tresh=(m+M)/2;
                    Color col;
                    int Tresh=(int)tresh;
                    col=Color.rgb(Tresh, Tresh, Tresh);
                    writer.setColor(x,y,col);
                }
            }
            PixelReader readBrensen=Brensen.getPixelReader();
            PixelReader readOtsu=Otsu.getPixelReader();
            PixelReader readSuvola=Suavola.getPixelReader();
            PixelReader readNiblack=Niblack.getPixelReader();
            int spr=0;
            writer=wImage.getPixelWriter();
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int AR = readBrensen.getArgb(x, y);
                    int R = (0xff & (AR >> 16));
                    vote[R]++;
                    AR = readOtsu.getArgb(x, y);
                    R = (0xff & (AR >> 16));
                    vote[R]++;
                    AR = readSuvola.getArgb(x, y);
                    R = (0xff & (AR >> 16));
                    vote[R]++;
                    AR = readNiblack.getArgb(x, y);
                    R = (0xff & (AR >> 16));
                    vote[R]++;
                    vote[global]++;
                    int norm=0;
                    tresh=0;
                    for(int i = 0;i<=128;i++){
                        if(vote[i]>norm){
                            norm=(int)vote[i];
                            tresh=i;
                        }
                    }
                    if(tresh==global){
                        spr++;
                    }
                    int argb = pixelReader.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    test=(r+g+b)/3;
                    if(test>tresh){
                        writer.setColor(x, y, Color.WHITE);
                    }
                    else{
                        writer.setColor(x, y, Color.BLACK);
                    }
                }
            }
            zle.setText("Binaryzacje globalną użyto do "+spr+" pikseli");
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
        vBox.getChildren().addAll(labelInfo, textSrc, btnDo, hBox,zle);

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
    class Suavola{
        private Image po;
        Suavola(Image src){
            po=src;
        }
        public WritableImage zmien(){
            int width=(int)po.getWidth();
            int height=(int)po.getHeight();
            double his[]= new double[256];
            double mean=0;
            double test=0;
            double tresh;
            double R=1.2;
            int p = 3;
            int q=10;
            double st[]=new double[10];
            double s=0;
            double k=-0.2;
            PixelReader pixelReader=po.getPixelReader();
            WritableImage przed=new WritableImage(width,height);
            PixelWriter writer=przed.getPixelWriter();
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int co=0;
                    for(int Y=y-1;Y<=y+1;Y++){    
                    for(int X=x-1;X<=x+1;X++){
                        int argb = pixelReader.getArgb(X, Y);
                        int r = (0xff & (argb >> 16));
                        int g = (0xff & (argb >> 8));
                        int b = (0xff & argb);
                        test=(r+g+b)/3;
                        mean+=(r+g+b)/3;
                        his[co]=(r+g+b)/3;
                        co++;
                        }
                    }
                    mean=mean/9;
                    for(int i=0;i<9;i++){
                        st[i]=his[i]-mean;
                        s+=st[i]*st[i];
                    }
                    s=s/9;
                    s=Math.sqrt(s);
                    double e=Math.exp(-1*q*mean);
                    tresh=mean*(1+p*e+k*(s/R-1));
                    Color col;
                    if(tresh<0){
                        tresh=0;
                    }
                    int Tresh=(int)tresh;
                    col=Color.rgb(Tresh, Tresh, Tresh);
                    writer.setColor(x,y,col);
                }
            }
            return przed;
        }
    }
    class Otsu{
        private Image przed;
        Otsu(Image src){
            przed=src;
        }
        public WritableImage zmien(){
            
            int width=(int)przed.getWidth();
            int height=(int)przed.getHeight();
            double his[]= new double[256];
            WritableImage po=new WritableImage(width,height);
            PixelReader pixelReader=przed.getPixelReader();
            PixelWriter writer=po.getPixelWriter();
            for(int Y=1;Y<height-3;Y+=3){
                for(int X=1;X<width-3;X+=3){
                
                    for(int y=Y-1;y<=Y+1;y++){
                        for(int x=X-1;x<=X+1;x++){
                    int argb = pixelReader.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    int test=(r+g+b)/3;
                    his[test]++;
                }
            }
            int size=height*width;
            for(int i = 0;i<256;i++)
            {
                his[i]=his[i]/size;
            }
            float avg=0;
            for(int i = 0;i<256;i++)
            {
                avg+=i*his[i];
            }
            int tresh=0;
            float max=0;
            float w=0,u=0;
            for(int i=0;i<256;i++){
                w+=his[i];
                u+=his[i]*i;
                float t=avg*w-u;
                float variance=t*t/(w*(1-w));
                if(variance>max){
                    max=variance;
                    tresh=i;
                }
            }
             for(int y=Y-1;y<=Y+1;y++){
                for(int x=X-1;x<=X+1;x++){
                    Color color=pixelReader.getColor(x,y);
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                    Color col;
                    int Tresh=(int)tresh;
                    col=Color.rgb(Tresh, Tresh, Tresh);
                    writer.setColor(x,y,col);
                }
            }
                }
            }
            return po;
        }
    }
    class Niblack{
        Image przed;
        public Niblack(Image src){
            przed=src;
        }
        public WritableImage zmien(){
            int width=(int)przed.getWidth();
            int height=(int)przed.getHeight();
            double his[]= new double[256];
            double mean=0;
            double test=0;
            double tresh;
            double st[]=new double[10];
            double s=0;
            double k=-0.2;
            WritableImage po=new WritableImage(width,height);
            PixelReader pixelReader=przed.getPixelReader();
            PixelWriter writer=po.getPixelWriter();
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int co=0;
                    for(int Y=y-1;Y<=y+1;Y++){    
                    for(int X=x-1;X<=x+1;X++){
                        int argb = pixelReader.getArgb(X, Y);
                        int r = (0xff & (argb >> 16));
                        int g = (0xff & (argb >> 8));
                        int b = (0xff & argb);
                        test=(r+g+b)/3;
                        mean+=(r+g+b)/3;
                        his[co]=(r+g+b)/3;
                        co++;
                        }
                    }
                    mean=mean/9;
                    for(int i=0;i<9;i++){
                        st[i]=his[i]-mean;
                        s+=st[i]*st[i];
                    }
                    s=s/9;
                    s=Math.sqrt(s);
                    tresh=mean+k*s;
                    Color col;
                    int Tresh=(int)tresh;
                    col=Color.rgb(Tresh, Tresh, Tresh);
                    writer.setColor(x,y,col);
                }
            }
            return po;
        }
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