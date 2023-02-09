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
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;

public class Punkt extends Application {
    String defaultImage = "D:/Download/Tiphereth.png";
    int width;
    int height;
    @Override
    public void start(Stage primaryStage) {

        Label labelInfo = new Label();
        labelInfo.setText(
                "java.version: " + System.getProperty("java.version") + "\n"
                + "javafx.runtime.version: " + System.getProperty("javafx.runtime.version")
        );
        Label labR=new Label(" R: ");
        Label labG=new Label(" G: ");
        Label labB=new Label(" B: ");
        Label labBri=new Label(" Oswietlenie: ");
        TextField textR = new TextField();
        textR.setText("0");
        TextField textG = new TextField();
        textG.setText("0");
        TextField textB = new TextField();
        textB.setText("0");
        TextField textBri = new TextField();
        textBri.setText("1");
        TextField dest=new TextField();
        dest.setText(defaultImage);
        Button get=new Button("Czytaj Obraz");
        Button btnAdd = new Button("Dodaj");
        Button btnSub = new Button("Odejmij");
        Button btnMul = new Button("Mnoz");
        Button btnDiv = new Button("Dziel");
        Button btnDim= new Button("Swiatlo");
        ImageView imageView = new ImageView();
        get.setOnAction((ActionEvent event)->{
            File imageFile = new File(defaultImage);
            String fileLocation=imageFile.toURI().toString();
            Image fxImage=new Image(fileLocation);
            height=(int)fxImage.getHeight();
            width=(int)fxImage.getWidth();
            imageView.setImage(fxImage);
        });
        btnAdd.setOnAction((ActionEvent event) -> {
            int r,g,b;
            r=Integer.parseInt(textR.getText());
            g=Integer.parseInt(textG.getText());
            b=Integer.parseInt(textB.getText());
            int R[]=new int[256];
            int G[]=new int[256];
            int B[]=new int[256];
            Image image=imageView.getImage();
            for(int i=0;i<256;i++){
                R[i]=i+r;
                G[i]=i+g;
                B[i]=i+b;
                if(R[i]>255){
                    R[i]=255;
                }
                if(G[i]>255){
                    G[i]=255;
                }
                if(B[i]>255){
                    B[i]=255;
                }
            }
            WritableImage wImage=new WritableImage(width,height);
            PixelWriter writer=wImage.getPixelWriter();
            PixelReader read=image.getPixelReader();
            for(int y=1;y<=height-1;y++){
                for(int x=1;x<=width-1;x++){
                    int argb = read.getArgb(x, y);
                    r = (0xff & (argb >> 16));
                    g = (0xff & (argb >> 8));
                    b = (0xff & argb);
                    r=R[r];
                    g=G[g];
                    b=B[b];
                    Color col=Color.rgb(r, g, b);
                    writer.setColor(x, y, col);
                }
            }
            imageView.setImage(wImage);
        });
        btnSub.setOnAction((ActionEvent event) -> {
            int r,g,b;
            r=Integer.parseInt(textR.getText());
            g=Integer.parseInt(textG.getText());
            b=Integer.parseInt(textB.getText());
            int R[]=new int[256];
            int G[]=new int[256];
            int B[]=new int[256];
            Image image=imageView.getImage();
            for(int i=0;i<256;i++){
                R[i]=i-r;
                G[i]=i-g;
                B[i]=i-b;
                if(R[i]<0){
                    R[i]=0;
                }
                if(G[i]<0){
                    G[i]=0;
                }
                if(B[i]<0){
                    B[i]=0;
                }
                
            }
            WritableImage wImage=new WritableImage(width,height);
            PixelWriter writer=wImage.getPixelWriter();
            PixelReader read=image.getPixelReader();
            for(int y=1;y<=height-1;y++){
                for(int x=1;x<=width-1;x++){
                    int argb = read.getArgb(x, y);
                    r = (0xff & (argb >> 16));
                    g = (0xff & (argb >> 8));
                    b = (0xff & argb);
                    r=R[r];
                    g=G[g];
                    b=B[b];
                    Color col=Color.rgb(r, g, b);
                    writer.setColor(x, y, col);
                }
            }
            imageView.setImage(wImage);
        });
        btnMul.setOnAction((ActionEvent event) -> {
            int r,g,b;
            r=Integer.parseInt(textR.getText());
            g=Integer.parseInt(textG.getText());
            b=Integer.parseInt(textB.getText());
            int R[]=new int[256];
            int G[]=new int[256];
            int B[]=new int[256];
            Image image=imageView.getImage();
            for(int i=0;i<256;i++){
                R[i]=i*r;
                G[i]=i*g;
                B[i]=i*b;
                if(R[i]<0){
                    R[i]=0;
                }
                if(G[i]<0){
                    G[i]=0;
                }
                if(B[i]<0){
                    B[i]=0;
                }
                if(R[i]>255){
                    R[i]=255;
                }
                if(G[i]>255){
                    G[i]=255;
                }
                if(B[i]>255){
                    B[i]=255;
                }
            }
            WritableImage wImage=new WritableImage(width,height);
            PixelWriter writer=wImage.getPixelWriter();
            PixelReader read=image.getPixelReader();
            for(int y=1;y<=height-1;y++){
                for(int x=1;x<=width-1;x++){
                    int argb = read.getArgb(x, y);
                    r = (0xff & (argb >> 16));
                    g = (0xff & (argb >> 8));
                    b = (0xff & argb);
                    r=R[r];
                    g=G[g];
                    b=B[b];
                    Color col=Color.rgb(r, g, b);
                    writer.setColor(x, y, col);
                }
            }
            imageView.setImage(wImage);
        });
        btnDiv.setOnAction((ActionEvent event) -> {
            int r,g,b;
            r=Integer.parseInt(textR.getText());
            g=Integer.parseInt(textG.getText());
            b=Integer.parseInt(textB.getText());
            int R[]=new int[256];
            int G[]=new int[256];
            int B[]=new int[256];
            Image image=imageView.getImage();
            for(int i=0;i<256;i++){
                if(r!=0){
                    R[i]=i/r;
                    if(R[i]<0){
                    R[i]=0;
                    }
                    if(R[i]>255){
                    R[i]=255;
                    }
                }
                else{R[i]=i;}
                if(g!=0){
                    G[i]=i/g;
                    if(G[i]<0){
                    G[i]=0;
                    }
                    if(G[i]>255){
                    G[i]=255;
                    }
                }
                else{G[i]=i;}
                if(b!=0){
                B[i]=i/b;
                if(B[i]<0){
                    B[i]=0;
                }
                if(B[i]>255){
                    B[i]=255;
                }}else{B[i]=i;}
            }
            WritableImage wImage=new WritableImage(width,height);
            PixelWriter writer=wImage.getPixelWriter();
            PixelReader read=image.getPixelReader();
            for(int y=1;y<=height-1;y++){
                for(int x=1;x<=width-1;x++){
                    int argb = read.getArgb(x, y);
                    r = (0xff & (argb >> 16));
                    g = (0xff & (argb >> 8));
                    b = (0xff & argb);
                    r=R[r];
                    g=G[g];
                    b=B[b];
                    Color col=Color.rgb(r, g, b);
                    writer.setColor(x, y, col);
                }
            }
            imageView.setImage(wImage);
        });
        btnDim.setOnAction((ActionEvent event) -> {
            float light=Float.parseFloat(textBri.getText());
            Image image=imageView.getImage();
            WritableImage wImage=new WritableImage(width,height);
            PixelWriter writer=wImage.getPixelWriter();
            PixelReader read=image.getPixelReader();
                for(int y=1;y<=height-1;y++){
                    for(int x=1;x<=width-1;x++){
                        int argb = read.getArgb(x, y);
                        float r = (0xff & (argb >> 16));
                        float g = (0xff & (argb >> 8));
                        float b = (0xff & argb);
                        if(light>1){
                            float R=(255-r)*(light-1);
                            float G=(255-g)*(light-1);
                            float B=(255-b)*(light-1);
                            r=r+R;
                            g=g+G;
                            b=b+B;
                        }
                        else{
                            r=r*light;
                            g=g*light;
                            b=b*light;
                        }
                            if(r>255)r=255;
                            if(g>255)g=255;
                            if(b>255)b=255;
                        Color col=Color.rgb((int)r, (int)g, (int)b);
                        writer.setColor(x, y, col);
                    }
                }
            imageView.setImage(wImage);
        });
        HBox co=new HBox();
        co.getChildren().addAll(dest,get);
        HBox podaj = new HBox();
        podaj.getChildren().addAll(labR, textR, labG, textG,labB, textB,labBri, textBri);
        HBox przyciski=new HBox();
        przyciski.getChildren().addAll(btnAdd,btnSub,btnMul,btnDiv,btnDim);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo,co,podaj,przyciski,imageView);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("Kolory");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

   

}