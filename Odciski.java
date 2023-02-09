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
import java.util.Arrays;


public class Odciski extends Application {
    boolean changed=true;
    long[] check=new long[5];
    int[] A0=new int[]{3, 6, 7, 12, 14, 15, 24, 28, 30,
            31, 48, 56, 60, 62, 63, 96, 112, 120,
            124, 126, 127, 129, 131, 135, 143, 159,
            191, 192, 193, 195, 199, 207, 223, 224,
            225, 227, 231, 239, 240, 241, 243, 247,
            248, 249, 251, 252, 253, 254};
    int[] A1 = new int[] { 7, 14, 28, 56, 112, 131, 193, 224 };
    int[] A2 = new int[] { 7, 14, 15, 28, 30, 56, 60, 112, 120, 131, 135, 193, 195, 224, 225, 240 };
        int[] A3 = new int[] { 7, 14, 15, 28, 30, 31, 56, 60, 62, 112, 120, 124, 131, 135, 143, 193, 195, 199, 224, 225, 227, 240, 241, 248 };
        int[] A4 = new int[] {7, 14, 15, 28, 30, 31, 56, 60, 62,
            63, 112, 120, 124, 126, 131, 135, 143,
            159, 193, 195, 199, 207, 224, 225, 227,
            231, 240, 241, 243, 248, 249, 252};
        int[] A5 = new int[] {7, 14, 15, 28, 30, 31, 56, 60,
            62, 63, 112, 120, 124, 126, 131, 135,
            143, 159, 191, 193, 195, 199, 207, 224,
            225, 227, 231, 239, 240, 241, 243, 248, 249, 251, 252, 254};
        int[] Apxl = new int[] {3, 6, 7, 12, 14, 15, 24, 28, 30,
            31, 48, 56, 60, 62, 63, 96, 112, 120,
            124, 126, 127, 129, 131, 135, 143, 159,
            191, 192, 193, 195, 199, 207, 223, 224,
            225, 227, 231, 239, 240, 241, 243, 247,
            248, 249, 251, 252, 253, 254};

    String defaultImage = "data:image/jpeg;base64,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";

    @Override
    public void start(Stage primaryStage) {

        Label labelInfo = new Label();
        labelInfo.setText(
                "java.version: " + System.getProperty("java.version") + "\n"
                + "javafx.runtime.version: " + System.getProperty("javafx.runtime.version")
        );

        TextField textSrc = new TextField();
        Label procent=new Label();
        Label zle=new Label();
        Label kr=new Label();
        Label krz=new Label();
        Label roz=new Label();
        textSrc.setText(defaultImage);
        Button btnDo = new Button("Cała Operacja");
        Button phas1=new Button("Tylko Otsu");
        Button phas2=new Button("Szkieletyzacja i Otsu");
        Button phas3=new Button("Rozgałęzienia");
        Button phas4=new Button("Usuwanie Minucji");
        ImageView imageView = new ImageView();
        ImageView phase2=new ImageView();
        ImageView Po=new ImageView();

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final LineChart<String, Number> chartHistogram
                = new LineChart<>(xAxis, yAxis);
        chartHistogram.setCreateSymbols(false);
        phas4.setOnAction((ActionEvent event) -> {

            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            double his[]= new double[256];
            int bad=0;
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            WritableImage faza2=new WritableImage(width,height);
            PixelWriter phas=faza2.getPixelWriter();
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
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
             for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                    Color col;
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                    if(test>=tresh){
                    phas.setColor(x,y,Color.WHITE);
                    col=Color.rgb(0, 0, 0);
                    }
                    else{
                    phas.setColor(x,y,Color.BLACK);
                    col=Color.rgb(1, 1, 1);
                    }
                    writer.setColor(x,y,col);
                }
            }
            phase2.setImage(faza2);
            Testing testing=new Testing(wImage);
            wImage=testing.prze();
            PixelReader px=wImage.getPixelReader();
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            for(int y=1;y<height-1;y++){
                for(int x=width-1;x>0;x--){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            for(int x=1;x<width-1;x++){
                for(int y=height-1;y>0;y--){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            for(int x=1;x<width-1;x++){
                for(int y=0;y<height-1;y++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            long[] test=new long[5];
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    Color col;
                    int kol=0;
                    if(r==1){
                        for(int Y=y-1;Y<=y+1;Y++){
                            for(int X=x-1;X<=x+1;X++){
                                if(x!=X && y!=Y){
                                    int ar=px.getArgb(X, Y);
                                    int R=(0xff & (ar >> 16));
                                    if(R>0){
                                        kol++;
                                    }
                                }
                            }
                        }
                        test[kol]++;
                        col=Color.BLACK;
                    }
                    else if(r>1){
                        col=Color.RED;
                    }
                    else{col=Color.rgb(255, 255, 255);}
                    writer.setColor(x,y,col);
                }
            }
            String str=bad+" usunietych minucji";
            zle.setText(str);
            str=test[1]+" krawedzi";
            kr.setText(str);
            str=test[4]+" skrzyrzowań";
            krz.setText(str);
            str=test[3]+" rozgałęzień";
            roz.setText(str);
            Po.setImage(wImage); 
        });
        phas3.setOnAction((ActionEvent event)->{
            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            double his[]= new double[256];
            int bad=0;
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            WritableImage faza2=new WritableImage(width,height);
            PixelWriter phas=faza2.getPixelWriter();
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
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
             for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                    Color col;
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                    if(test>=tresh){
                    phas.setColor(x,y,Color.WHITE);
                    col=Color.rgb(0, 0, 0);
                    }
                    else{
                    phas.setColor(x,y,Color.BLACK);
                    col=Color.rgb(1, 1, 1);
                    }
                    writer.setColor(x,y,col);
                }
            }
            phase2.setImage(faza2);
            Testing testing=new Testing(wImage);
            wImage=testing.prze();
            PixelReader px=wImage.getPixelReader();
            long[] test=new long[5];
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    Color col;
                    int kol=0;
                    if(r==1){
                        for(int Y=y-1;Y<=y+1;Y++){
                            for(int X=x-1;X<=x+1;X++){
                                if(x!=X && y!=Y){
                                    int ar=px.getArgb(X, Y);
                                    int R=(0xff & (ar >> 16));
                                    if(R>0){
                                        kol++;
                                    }
                                }
                            }
                        }
                        test[kol]++;
                        col=Color.BLACK;
                    }
                    else{col=Color.rgb(255, 255, 255);}
                    writer.setColor(x,y,col);
                }}
            String str=test[1]+" krawedzi";
            kr.setText(str);
            str=test[4]+" skrzyrzowań";
            krz.setText(str);
            str=test[3]+" rozgałęzień";
            roz.setText(str);
            Po.setImage(wImage);
        });
        phas2.setOnAction((ActionEvent event)->{
                        String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            double his[]= new double[256];
            int bad=0;
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            WritableImage faza2=new WritableImage(width,height);
            PixelWriter phas=faza2.getPixelWriter();
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
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
             for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                    Color col;
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                    if(test>=tresh){
                    phas.setColor(x,y,Color.WHITE);
                    col=Color.rgb(0, 0, 0);
                    }
                    else{
                    phas.setColor(x,y,Color.BLACK);
                    col=Color.rgb(1, 1, 1);
                    }
                    writer.setColor(x,y,col);
                }
            }
            phase2.setImage(faza2);
            Testing testing=new Testing(wImage);
            wImage=testing.prze();
            PixelReader px=wImage.getPixelReader();
            long[] test=new long[5];
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    Color col;
                    int kol=0;
                    if(r==1){
                        for(int Y=y-1;Y<=y+1;Y++){
                            for(int X=x-1;X<=x+1;X++){
                                if(x!=X && y!=Y){
                                    int ar=px.getArgb(X, Y);
                                    int R=(0xff & (ar >> 16));
                                    if(R>0){
                                        kol++;
                                    }
                                }
                            }
                        }
                        test[kol]++;
                        col=Color.BLACK;
                    }
                    else{col=Color.rgb(255, 255, 255);}
                    writer.setColor(x,y,col);
                }}
            
            Po.setImage(wImage); 
            
        });
        phas1.setOnAction((ActionEvent event)->{
            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            double his[]= new double[256];
            int bad=0;
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            WritableImage faza2=new WritableImage(width,height);
            PixelWriter phas=faza2.getPixelWriter();
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
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
             for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                    Color col;
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                    if(test>=tresh){
                    phas.setColor(x,y,Color.WHITE);
                    col=Color.rgb(0, 0, 0);
                    }
                    else{
                    phas.setColor(x,y,Color.BLACK);
                    col=Color.rgb(1, 1, 1);
                    }
                    writer.setColor(x,y,col);
                }
            }
            phase2.setImage(faza2);
            
        });

        btnDo.setOnAction((ActionEvent event) -> {

            String imageSrc = textSrc.getText();
            Image image = new Image(imageSrc);
            int width=(int)image.getWidth();
            int height=(int)image.getHeight();
            double his[]= new double[256];
            int bad=0;
            WritableImage wImage=new WritableImage(width,height);
            imageView.setImage(image);
            PixelReader pixelReader=image.getPixelReader();
            PixelWriter writer=wImage.getPixelWriter();
            WritableImage faza2=new WritableImage(width,height);
            PixelWriter phas=faza2.getPixelWriter();
            for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
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
             for(int y=0;y<height;y++){
                for(int x=0;x<width;x++){
                    Color color=pixelReader.getColor(x,y);
                    Color col;
                        double r=color.getRed();
                        double g=color.getGreen();
                        double b=color.getBlue();
                        double test=(r+g+b)/3;
                        test=test*256;
                    if(test>=tresh){
                    phas.setColor(x,y,Color.WHITE);
                    col=Color.rgb(0, 0, 0);
                    }
                    else{
                    phas.setColor(x,y,Color.BLACK);
                    col=Color.rgb(1, 1, 1);
                    }
                    writer.setColor(x,y,col);
                }
            }
            phase2.setImage(faza2);
            Testing testing=new Testing(wImage);
            wImage=testing.prze();
            PixelReader px=wImage.getPixelReader();
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            for(int y=1;y<height-1;y++){
                for(int x=width-1;x>0;x--){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            for(int x=1;x<width-1;x++){
                for(int y=height-1;y>0;y--){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            for(int x=1;x<width-1;x++){
                for(int y=0;y<height-1;y++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    if(r==1){
                        writer.setColor(x,y,Color.RED);
                        bad++;
                        break;
                    }
                }
            }
            long[] test=new long[5];
            for(int y=1;y<height-1;y++){
                for(int x=1;x<width-1;x++){
                    int argb = px.getArgb(x, y);
                    int r = (0xff & (argb >> 16));
                    int g = (0xff & (argb >> 8));
                    int b = (0xff & argb);
                    Color col;
                    int kol=0;
                    if(r==1){
                        for(int Y=y-1;Y<=y+1;Y++){
                            for(int X=x-1;X<=x+1;X++){
                                if(x!=X && y!=Y){
                                    int ar=px.getArgb(X, Y);
                                    int R=(0xff & (ar >> 16));
                                    if(R>0){
                                        kol++;
                                    }
                                }
                            }
                        }
                        test[kol]++;
                        col=Color.BLACK;
                    }
                    else if(r>1){
                        col=Color.RED;
                    }
                    else{col=Color.rgb(255, 255, 255);}
                    writer.setColor(x,y,col);
                }}
            
            if(check[2]==0){
                check=test;
            }
            else{
                double ile=0;
                double ile2=0;
                double result;
                for(int i =0;i<5;i++){
                    ile+=check[i];
                    ile2+=test[i];
                }
                if(ile2>ile){
                    double rozy=ile2-ile;
                    ile2-=rozy*2;
                }
                result=ile2/ile;
                result=result*100;
                int odp=(int) result;
                String str=odp+"%";
                procent.setText(str);
            }
            String str=bad+" usunietych minucji";
            zle.setText(str);
            str=test[1]+" krawedzi";
            kr.setText(str);
            str=test[4]+" skrzyrzowań";
            krz.setText(str);
            str=test[3]+" rozgałęzień";
            roz.setText(str);
            Po.setImage(wImage); 
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(imageView, phase2, Po);
        HBox buttons= new HBox();
        buttons.getChildren().addAll(phas1,phas2,phas3,phas4,btnDo);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, textSrc, buttons, hBox,procent,zle,kr,roz,krz);

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
class Testing{
        public WritableImage koniec;
    Testing(WritableImage co){
        koniec=co;
    }
    public WritableImage prze(){
        while(changed){
            changed=false;
            koniec=p0(koniec);
            for(int i = 1;i<=5;i++){
                koniec=p1to5(koniec,i);
            }
            koniec=p6(koniec);
        }
        koniec=pix(koniec);
        return koniec;
    }
    public int Weigth(PixelReader pix, int x, int y){
            int weight = 0;
            int pixel=pix.getArgb(x-1, y);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=1;
            }
            pixel=pix.getArgb(x-1, y+1);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=2;
            }
            pixel=pix.getArgb(x, y+1);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=4;
            }
            pixel=pix.getArgb(x+1, y+1);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=8;
            }
            pixel=pix.getArgb(x+1, y);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=16;
            }
            pixel=pix.getArgb(x+1, y-1);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=32;
            }pixel=pix.getArgb(x, y-1);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=64;
            }
            pixel=pix.getArgb(x-1, y-1);
            if((0xff & (pixel >> 16))>0)
            {
                weight+=128;
            }
            return weight;
        }
    public WritableImage p0(WritableImage test){
        PixelReader read=test.getPixelReader();
        PixelWriter write=test.getPixelWriter();
        int argb;
        for(int j=1;j<test.getHeight()-1;j++)
        {
            for(int i=1;i<test.getWidth()-1;i++){
                argb=read.getArgb(i, j);
                if((0xff & (argb >> 16))>0){
                    if(contain(Weigth(read,i,j),0)){
                        Color col=Color.rgb(2, 2, 2);
                        write.setColor(i, j, col);
                    }
                }
            }
        }
    return test;
    }
    public WritableImage p1to5(WritableImage test,int ph){
        PixelReader read=test.getPixelReader();
        PixelWriter write=test.getPixelWriter();
        Color col=Color.rgb(0, 0, 0);
        int argb;
         for(int j=1;j<test.getHeight()-1;j++)
        {
            for(int i=1;i<test.getWidth()-1;i++)
            {
                argb=read.getArgb(i, j);
                if((0xff & (argb >> 16))>0){
                    if (contain(Weigth(read,i,j),ph))
                        {
                            write.setColor(i, j, col);
                            changed = true;
                        }
                }
            }
        }
        return test;
    }
    public WritableImage p6(WritableImage test){
        PixelReader read=test.getPixelReader();
        PixelWriter write=test.getPixelWriter();
        Color col=Color.rgb(1, 1, 1);
        for(int j=1;j<test.getHeight()-1;j++)
        {
            for(int i=1;i<test.getWidth()-1;i++)
            {
                int argb=read.getArgb(i, j);
                if((0xff & (argb >> 16))>0){
                    write.setColor(i, j, col);
                }
            }
        }
        return test;
    }
    public WritableImage pix(WritableImage test){
        PixelReader read=test.getPixelReader();
        PixelWriter write=test.getPixelWriter();
        Color col=Color.rgb(0, 0, 0);
        for(int j=1;j<test.getHeight()-1;j++)
        {
            for(int i=1;i<test.getWidth()-1;i++)
            {
                int argb=read.getArgb(i, j);
                if((0xff & (argb >> 16))>0){
                    if(contain(Weigth(read,i,j),6)){
                        write.setColor(i, j, col);
                    }
                }
            }
        }
        return test;
    }
    public boolean contain(int test,int i){
        boolean odp=false;
        int[] spr=new int[]{};
        switch(i){
            case 0:{
                spr=A0;
                break;
            }
            case 1:{
                spr=A1;
                break;
            }
            case 2:{
                spr=A2;
                break;
            }
            case 3:{
                spr=A3;
                break;
            }
            case 4:{
                spr=A4;
                break;
            }
            case 5:{
                spr=A5;
                break;
            }
            case 6:{
                spr=Apxl;
                break;
            }
        }
        for(int j=0;j<=spr.length-1;j++){
            if(test==spr[j]){
                odp=true;
            }
        }
        return odp;
    }
    }
    

}