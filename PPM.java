package javaapplication1;
import java.io.*;
import javafx.application.Application;
import javafx.scene.Scene;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class PPM extends Application{
    public static String def="D:/PPM/ppm-test-03-p6.ppm";
    public static String end="D:/PPM/result.jpeg";
    public void start(Stage mainStage){
        ImageView imageView = new ImageView();
        TextField textSrc = new TextField();
        textSrc.setText(def);
        TextField textDes = new TextField();
        textDes.setText(end);
        Button btn=new Button("Czytaj i konwertuj");
        btn.setOnAction((ActionEvent event) ->{
            String in=textSrc.getText();
            String out=textDes.getText();
            try{Image img=read(in,out);
                imageView.setImage(img);
            }catch(IOException ax){
                
            }
            
        });
        HBox hBox = new HBox();
        hBox.getChildren().addAll(textSrc, textDes,btn);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox, imageView);

        StackPane root = new StackPane();
        root.getChildren().add(vBox);

        Scene scene = new Scene(root, 1000, 500);

        mainStage.setTitle("Program PPM");
        mainStage.show();
    }
static public BufferedImage ppmm(int width, int height, int maxcolval, byte[] data){
        if(maxcolval<256){
            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            int r,g,b,k=0,pixel;
            if(maxcolval==255){                                      // don't scale
                for(int y=0;y<height;y++){
                    for(int x=0;(x<width)&&((k+3)<data.length);x++){
                        r=data[k++] & 0xFF;
                        g=data[k++] & 0xFF;
                        b=data[k++] & 0xFF;
                        pixel=0xFF000000+(r<<16)+(g<<8)+b;
                        image.setRGB(x,y,pixel);
                    }
                }
            }
            else{
                for(int y=0;y<height;y++){
                    for(int x=0;(x<width)&&((k+3)<data.length);x++){
                        r=data[k++] & 0xFF;r=((r*255)+(maxcolval>>1))/maxcolval;  // scale to 0..255 range
                        g=data[k++] & 0xFF;g=((g*255)+(maxcolval>>1))/maxcolval;
                        b=data[k++] & 0xFF;b=((b*255)+(maxcolval>>1))/maxcolval;
                        pixel=0xFF000000+(r<<16)+(g<<8)+b;
                        image.setRGB(x,y,pixel);
                    }
                }
            }
            return image;
        }
        else{


            BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
            int r,g,b,k=0,pixel;
            for(int y=0;y<height;y++){
                for(int x=0;(x<width)&&((k+6)<data.length);x++){
                    r=(data[k++] & 0xFF)|((data[k++] & 0xFF)<<8);r=((r*255)+(maxcolval>>1))/maxcolval;  // scale to 0..255 range
                    g=(data[k++] & 0xFF)|((data[k++] & 0xFF)<<8);g=((g*255)+(maxcolval>>1))/maxcolval;
                    b=(data[k++] & 0xFF)|((data[k++] & 0xFF)<<8);b=((b*255)+(maxcolval>>1))/maxcolval;
                    pixel=0xFF000000+(r<<16)+(g<<8)+b;
                    image.setRGB(x,y,pixel);
                }
            }
            return image;
        }
    }
	
	public static void main(String[] args) throws Exception{
            launch(args);
	}
        public static Image read(String text,String endin) throws FileNotFoundException,IOException,NumberFormatException{
                byte bytes[] = null; 
                int height = 0;
                int width = 0;
                int range=0;
                char buffer;
                String id=new String();
                String dim=new String();
                int count = 0;
                String filename=text;
                File f = new File(filename);
                FileInputStream isr=new FileInputStream(f);
                boolean weird=false;
                do{
                    buffer=(char)isr.read();
                    id=id+buffer;
                    count++;
                }while(buffer!='\n' && buffer!=' ');
                if(id.charAt(0)=='P'){
                    buffer=(char)isr.read();
                    count++;
                    if(buffer=='#'){
                        do{
                            buffer=(char)isr.read();
                            count++;
                        }while(buffer!='\n');
                        count++;
                        buffer=(char)isr.read();
                    }
                    do{
                        dim=dim+buffer;
                        buffer=(char)isr.read();
                        count++;
                    }while(buffer!='\n' && buffer!=' ');
                    width=Integer.parseInt(dim);
                    dim=new String();
                    buffer=(char)isr.read();
                    count++;
                    if(buffer=='#'){
                        do{
                            buffer=(char)isr.read();
                            count++;
                        }while(buffer!='\n');
                        count++;
                        buffer=(char)isr.read();
                    }
                    do{
                        dim=dim+buffer;
                        buffer=(char)isr.read();
                        count++;
                    }while(buffer!='\n' && buffer!=' ');
                    height=Integer.parseInt(dim);
                    dim=new String();
                    buffer=(char)isr.read();
                    count++;
                    if(buffer=='#'){
                        do{
                            buffer=(char)isr.read();
                            count++;
                        }while(buffer!='\n');
                        count++;
                        buffer=(char)isr.read();
                    }
                    do{
                        dim=dim+buffer;
                        buffer=(char)isr.read();
                        count++;
                    }while(buffer!='\n' && buffer!=' ');
                    range=Integer.parseInt(dim);
                    if(buffer=='#'){
                        do{
                            buffer=(char)isr.read();
                            count++;
                        }while(buffer!='\n');
                        count++;
                        buffer=(char)isr.read();
                    }
                    bytes=new byte[height*width*3];
                    if((height*width+count*2)<f.length())weird=true;
                    if(id.charAt(1)=='6'){
                        if(!weird)isr.read(bytes,0,height*width*3);
                        else{
                            int v1=0;
                            int v2=0;
                            int v3=0;
                            for(int i=0;i<height*width*3;i+=3){
                                v1=isr.read();
                                v2=isr.read();
                                v3=isr.read();
                                bytes[i]=(byte)(v1&0xFF);
                                bytes[i+1]=(byte)(v2&0xFF);
                                bytes[i+2]=(byte)(v3&0xFF);
                            }
                        }
                    }
                    if(id.charAt(1)=='3'){
                        int i = 0;
                        for(i=0;i<width*height*3;i+=3){
                            dim=new String();
                            do{
                                buffer=(char)isr.read();
                                if(buffer!='\n' && buffer!=' '){
                                    dim=dim+buffer;
                                }
                            }while(buffer!='\n' && buffer!=' ');
                            bytes[i]=(byte)(Integer.parseInt(dim)& 0xFF);
                            dim=new String();
                            buffer=(char)isr.read();
                            do{
                                if(buffer!='\n' && buffer!=' '){
                                    dim=dim+buffer;
                                }
                                buffer=(char)isr.read();
                            }while(buffer!='\n' && buffer!=' ');
                            bytes[i+1]=(byte)(Integer.parseInt(dim)& 0xFF);
                            dim=new String();
                            buffer=(char)isr.read();
                            do{
                                if(buffer!='\n' && buffer!=' '){
                                    dim=dim+buffer;
                                }
                                buffer=(char)isr.read();
                            }while(buffer!='\n' && buffer!=' ');
                            bytes[i+2]=(byte)(Integer.parseInt(dim)& 0xFF);
                        }
                    }
		BufferedImage image = ppmm(height,width,range,bytes);
                Image show=SwingFXUtils.toFXImage(image, null);
                var outputfile=new File(endin);
                ImageIO.write(image,"jpg",outputfile);
                return show;
                }
                else{
                    width=height=0;
                    bytes=new byte[0];
                    throw new NumberFormatException("Wrong Header Information");
                }
        }
	
	
	
}