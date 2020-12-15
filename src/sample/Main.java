package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.ini4j.*;

import java.io.FileOutputStream;
import java.io.FileReader;

public class Main extends Application {
    private Ini aRead;
    private Ini aWright;
    public Scene scene;
    private double width;
    private double height;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Matsak Ivan 10701219");
        scene=new Scene(root);
        aRead=new Ini();
        aRead.load(new FileReader("src/sample/settings.ini"));
        width=aRead.get("scene","width",double.class);
        height=aRead.get("scene","height",double.class);
        primaryStage.setWidth(width);
        primaryStage.setHeight(height);


        primaryStage.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        aWright=new Ini();
                        aWright.put("scene","width",primaryStage.getWidth());
                        aWright.put("scene","height",primaryStage.getHeight());
                        aWright.put("numbers","list",Controller.getList());
                        try{
                            aWright.store(new FileOutputStream("src/sample/settings.ini"));
                        }catch (Exception e){

                        }

                    }
                });
            }
        });


        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
