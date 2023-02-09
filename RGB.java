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

public class RGB extends Application {
    @Override
    public void start(Stage primaryStage) {

        Label labelInfo = new Label();
        labelInfo.setText(
                "java.version: " + System.getProperty("java.version") + "\n"
                + "javafx.runtime.version: " + System.getProperty("javafx.runtime.version")
        );

        TextField textR = new TextField();
        textR.setText("255");
        TextField textG = new TextField();
        textG.setText("255");
        TextField textB = new TextField();
        textB.setText("255");
        TextField textC = new TextField();
        textC.setText("0");
        TextField textM = new TextField();
        textM.setText("0");
        TextField textY = new TextField();
        textY.setText("0");
        TextField textK = new TextField();
        textK.setText("0");
        Button btnDo = new Button("Do CMYK");
        Button CtR = new Button("Do RGB");
        ImageView imageView = new ImageView();
        ImageView Po=new ImageView();

        btnDo.setOnAction((ActionEvent event) -> {

            int width=500;
            int height=500;
            float r,g,b,c,m,y,k;
            r=Float.parseFloat(textR.getText());
            g=Float.parseFloat(textG.getText());
            b=Float.parseFloat(textB.getText());
            WritableImage wImage=new WritableImage(width,height);
            PixelWriter writer=wImage.getPixelWriter();
            for(int X=1;X<height-1;X++){
                for(int x=1;x<width-1;x++){
                    Color col=Color.rgb((int)r,(int)g,(int)b);
                    writer.setColor(x, X, col);
                }
            }
            float R,G,B;
            R=r/255;
            G=g/255;
            B=b/255;
            k=1-Math.max(B, Math.max(R, G));
            c=(1-R-k)/(1-k);
            m=(1-G-k)/(1-k);
            y=(1-B-k)/(1-k);
            textC.setText(Float.toString(c));
            textM.setText(Float.toString(m));
            textY.setText(Float.toString(y));
            textK.setText(Float.toString(k));
            imageView.setImage(wImage);
        });
        CtR.setOnAction((ActionEvent handler)->{
            int width=500;
            int height=500;
            float r,g,b,c,m,y,k;
            c=Float.parseFloat(textC.getText());
            m=Float.parseFloat(textM.getText());
            y=Float.parseFloat(textY.getText());
            k=Float.parseFloat(textK.getText());
            r=255*(1-c)*(1-k);
            g=255*(1-m)*(1-k);
            b=255*(1-y)*(1-k);
            WritableImage wImage=new WritableImage(width,height);
            PixelWriter writer=wImage.getPixelWriter();
            for(int X=1;X<height-1;X++){
                for(int x=1;x<width-1;x++){
                    Color col=Color.rgb((int)r,(int)g,(int)b);
                    writer.setColor(x, X, col);
                }
            }
            textR.setText(Float.toString(r));
            textG.setText(Float.toString(g));
            textB.setText(Float.toString(b));
            imageView.setImage(wImage);
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(textR, textG,textB, btnDo);
        HBox dol = new HBox();
        dol.getChildren().addAll(textC, textM,textY,textK,CtR);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(labelInfo, hBox, imageView,dol);

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