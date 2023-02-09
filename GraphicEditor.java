import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate; 
import javafx.scene.transform.Scale; 
import javafx.scene.transform.Translate; 
import javafx.stage.Stage;

public class GraphicEditor extends Application {
    public static String shapeType = "";
    public static String color = "";

    public static Rectangle customRectangle = new Rectangle(50, 50, 100, 75);
    private double firstX = 0;
    private double firstY = 0;
    private double secondX=0;
    private double secondY=0;

    public void start(Stage mainStage) throws Exception {
        customRectangle.setFill(Color.BLUE);
        customRectangle.setStroke(Color.BLACK); 
        BorderPane mainPane = new BorderPane();
        HBox wejscie=new HBox();
        HBox przyciski=new HBox();
        HBox centerPane = new HBox();
        Button transform=new Button("Transform");
        Button rotate=new Button("Rotate");
        Button scale=new Button("Scale");
        VBox VBOX =new VBox();
        TextField x=new TextField("0");
        TextField y=new TextField("0");
        TextField ang=new TextField("0");
        Label la=new Label(" Podaj Kąt lub skalę");
        Label lx=new Label("Podaj X");
        Label ly=new Label("Podaj Y");
        Label type=new Label();
        centerPane.prefWidthProperty().bind(mainPane.widthProperty());
        centerPane.prefHeightProperty().bind(mainPane.heightProperty());

        AnchorPane anchorPane = new AnchorPane();

        anchorPane.prefWidthProperty().bind(centerPane.widthProperty());
        anchorPane.prefHeightProperty().bind(centerPane.heightProperty());

        centerPane.getChildren().add(anchorPane);
        mainPane.setCenter(centerPane);
        wejscie.getChildren().addAll(lx,x,ly,y,la,ang);
        przyciski.getChildren().addAll(transform,rotate,scale, type);
        VBOX.getChildren().addAll(wejscie,przyciski,centerPane);
        mainPane.setCenter(VBOX);
        transform.setOnAction((ActionEvent event)->{
            shapeType="transform";
            type.setText(shapeType);
            int x1=Integer.parseInt(x.getText());
            int y1=Integer.parseInt(y.getText());
            if(x1!=0){
                moveRectangleVec(x1,y1,anchorPane);
            }
        });
        rotate.setOnAction((ActionEvent event)->{
            shapeType="rotate";
            type.setText(shapeType);
            int x1=Integer.parseInt(x.getText());
            int y1=Integer.parseInt(y.getText());
            double deg=Double.parseDouble(ang.getText());
            if(x1!=0){
                rotateRectangleText(x1,y1,deg,anchorPane);
            }
        });
        scale.setOnAction((ActionEvent event)->{
            shapeType="scale";
            type.setText(shapeType);
            int x1=Integer.parseInt(x.getText());
            int y1=Integer.parseInt(y.getText());
            double deg=Double.parseDouble(ang.getText());
            if(x1!=0){
                scaleRectangleText(x1,y1,deg,anchorPane);
            }
        });
        centerPane.addEventFilter(MouseEvent.ANY, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (event.getEventType() == MouseEvent.MOUSE_PRESSED && shapeType != "") {
                    firstX = event.getX();
                    firstY = event.getY();
                    switch (shapeType) {
                    case "transform":
                        moveRectangleMouse(firstX,firstY,anchorPane);
                        break;
                    case "rotate":
                        break;
                    case "scale":
                        break;
                    }
                }
                if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                    secondX = event.getX();
                    secondY = event.getY();
                    switch (shapeType) {
                    case "transform":
                        moveRectangleMouse(secondX,secondY,anchorPane);
                        break;
                    case "rotate":
                        rotateRectangleMouse(secondX,secondY, anchorPane);
                        break;
                    case "scale":
                        scaleRectangleMouse(secondX,secondY, anchorPane);
                    break;
                    }
                }
            }
        });
        anchorPane.getChildren().add(customRectangle);
        Scene scene = new Scene(mainPane, 600, 400);
        mainStage.setScene(scene);
        mainStage.show();
    }
    public void moveRectangleVec(double x, double y, AnchorPane canvasGroup){
      Translate translate = new Translate();
      translate.setX(x); 
      translate.setY(y); 
      translate.setZ(0);
      customRectangle.getTransforms().add(translate);
    }
    public void moveRectangleMouse(double x, double y, AnchorPane canvasGroup){
        customRectangle.setX(x);
        customRectangle.setY(y);
    }
    public void rotateRectangleText(double x, double y, double deg, AnchorPane canvasGroup){
        Rotate rotate=new Rotate();
        rotate.setPivotX(x); 
        rotate.setPivotY(y); 
        rotate.setAngle(deg);
        customRectangle.getTransforms().add(rotate);
    }
    public void scaleRectangleText(double x, double y, double deg, AnchorPane canvasGroup){
        Scale scale=new Scale();
        scale.setX(deg);
        scale.setY(deg);
        scale.setPivotX(x);
        scale.setPivotY(y);
        customRectangle.getTransforms().add(scale);
    }
    public void rotateRectangleMouse(double x,double y, AnchorPane canvasGroup){
        Rotate rotate=new Rotate();
        rotate.setPivotX(firstX); 
        rotate.setPivotY(firstY);
        double deg=(x-firstX)+(y-firstY);
        if(deg<0){
            deg+=360;
        }
        rotate.setAngle(deg);
        customRectangle.getTransforms().add(rotate);
    }
    public void scaleRectangleMouse(double x,double y, AnchorPane canvasGroup){
        Scale rotate=new Scale();
        rotate.setPivotX(firstX); 
        rotate.setPivotY(firstY);
        rotate.setX(x/firstX);
        rotate.setY(y/firstY);
        customRectangle.getTransforms().add(rotate);
    }
    public static void main(String[] args) {
        launch(args);
    }
}