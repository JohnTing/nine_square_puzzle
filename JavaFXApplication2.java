/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package javafxapplication2;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

// import javafxapplication2.AiClass;
// import AiClass; 
/**
 *
 * @author JohnTing
 */
public class JavaFXApplication2 extends Application {
    
    // �Ω󲾰ʳt��
    int movespeed = 30;
    // �Ω�۰ʳt��
    int workspeed = 200;
    
    // ��e��m
    // ���ӧ��locateBox[0]�A��� x, y 
    // int x = 0, y = 0;
    
    ImageView[] imageViewBox = new ImageView[9];
    
    // ��index ��������l�b value
    int[] locateBox = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    // ��index ��l�������O value
    int[] indexBox = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    //int moveRecord = 0;
    
    // �b�o��ϥ�AiClass �i����򪺲���
    // �^�k���۰ʰ���
    Timeline timework = new Timeline(new KeyFrame(
        Duration.millis(workspeed),
        ae -> timeline_work()));
    public void timeline_work()
    {
        boolean end = true;
        for(int i = 0 ;i < 9;i++)
        {
            if(locateBox[i] != i)
            {
                end = false;
                break;
            }
        }
        if(end)
        {
            timework.stop();
        }
        else
        {
            int dir = AiClass.work(locateBox, indexBox);
            move(dir);
        }
    }
    
    // �b�o��i��ʺA������
    // ���짹����|�۰ʰ���
    Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(movespeed),
        ae -> timeline_move()));
    public void timeline_move()
    {
        int maxspeed = 8;
        boolean ismove = false;
        for (int i = 0;i < 9;i++)
        {
            double glx = imageViewBox[i].getLayoutX();
            double gly = imageViewBox[i].getLayoutY();

            double movex = 64 * (locateBox[i] % 3) - glx;
            movex = Math.max(-maxspeed, Math.min(maxspeed, movex));

            double movey = 64 * (locateBox[i] / 3) - gly;
            movey = Math.max(-maxspeed, Math.min(maxspeed, movey));

            if(movex != 0 || movey != 0)
            {
                movex += glx;
                movey += gly;
                //System.out.printf("%f %f\n", movex, movey);
                ismove = true;
                imageViewBox[i].relocate(movex, movey);
            }
        }
        if(!ismove)
            timeline.stop();
        
    }
    
    
    @Override
    public void start(Stage primaryStage) {

		// ��J�Ʀr�]�w�L��������
        TextField resetseed = new TextField ();
        resetseed.relocate(64*4, 0);
        resetseed.setPromptText("input set seed (integer)");
        resetseed.setText("0");
        
        // ��e���ʪ��Ʀr
        TextField moveseed = new TextField ();
        moveseed.relocate(64*4, 0);
        moveseed.setPromptText("onput seed");
        moveseed.setText("0");
        
        for (int i = 0;i < 9;i++)
        {
            imageViewBox[i] = new ImageView("file:./img/a" + (i) + ".png");
            imageViewBox[i].relocate(64 * (i%3), 64 * (i/3));
        }
        
        // Set ���s�A���� resetseed �]�w�L��
        Button btn_random = new Button("set");
        btn_random.relocate(64*3, 0);
        btn_random.setOnAction((ActionEvent event) -> {
            String value = resetseed.getText();
            int seed = 0;
            //moveRecord = 0;
            try {  
                seed = Integer.parseInt(value);  
            } catch (NumberFormatException e) { 
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("�п�J��Integer �d�򪺼Ʀr�C");
                alert.showAndWait();
         }
            seedMove(seed);
        });
        // Next ���s�A���� AiClass ���ʤ@�B
        Button btn_next = new Button("Nent");
        btn_next.relocate(64*3, 32*1);
        btn_next.setOnAction((ActionEvent event) -> {
            int dir = AiClass.work(locateBox, indexBox);
            move(dir);
        });
        // Start ���s�A���� AiClass ���򲾰�(�Ұ�timework)
        //  
        Button btn_start = new Button("Start");
        btn_start.relocate(64*3, 32*2);
        btn_start.setOnAction((ActionEvent event) -> {
            timework.setCycleCount(Animation.INDEFINITE);
            timework.play();
        });
        // ���� ���s
        Button btn_stop = new Button("Stop");
        btn_stop.relocate(64*3, 32*3);
        btn_stop.setOnAction((ActionEvent event) -> {
            timework.stop();
        });
        
        // �]�w���O
        Pane canvas = new Pane();
        canvas.getChildren().addAll(imageViewBox);
        canvas.getChildren().add(btn_random);
        canvas.getChildren().add(btn_next);
        canvas.getChildren().add(btn_start);
        canvas.getChildren().add(btn_stop);
        canvas.getChildren().add(resetseed);
        
        StackPane root = new StackPane();
        root.getChildren().add(canvas);
        
        Scene scene = new Scene(root, 300, 250);
        
        // ���� KEY_PRESSED �ƥ�
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent key) -> {
            if (key.getCode() == KeyCode.ESCAPE) {
                System.out.println("Key Pressed: " + key.getCode());
            }
            //System.out.println("x = " + x + ", y = " + y);
            KeyCode keyCode = key.getCode();
            System.out.println(keyCode.toString());
            
            /*
            if (keyCode == KeyCode.W)
                RecordKeyMove(0);
            if (keyCode == KeyCode.A)
                RecordKeyMove(1);
            if (keyCode == KeyCode.S)
                RecordKeyMove(2);
            if (keyCode == KeyCode.D)
                RecordKeyMove(3);
            */
            if (keyCode == KeyCode.W)
                move(0);
            if (keyCode == KeyCode.A)
                move(1);
            if (keyCode == KeyCode.S)
                move(2);
            if (keyCode == KeyCode.D)
                move(3);
            
            
        });
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /*
    public void RecordKeyMove(int dir)
    {
    	int[] a = moveDirList(locateBox[0]);
    	
    	for(int i = 0;i < a.length;i++)
    	{
    		if(dir == a[i])
    		{
    			moveRecord *= a.length;
    			moveRecord += i;
    			System.out.printf("%d %d\n", i, moveRecord);
    			return;
    		}
    	}
    }
    */
    
    
    
    // �b�o�̥Ѥ�V�M�w����Ӧ�l�洫
    public void move(int dir)
    {
        if(dir == 0 && locateBox[0]/3 != 0)
            exchange(locateBox[0] -3 , locateBox[0]);
        if(dir == 1 && locateBox[0]%3 != 0)
            exchange(locateBox[0] -1 , locateBox[0]);
        if(dir == 2 && locateBox[0]/3 != 2)
            exchange(locateBox[0] +3 , locateBox[0]);
        if(dir == 3 && locateBox[0]%3 != 2)
            exchange(locateBox[0] +1 , locateBox[0]);
        
        /*
        if (dir == 0 && y-1 >= 0)
        {
            exchange(x, y, x, y-1);
            y--;
        }
        if (dir == 1 && x-1 >= 0)
        {
            exchange(x, y, x-1, y);
            x--;
        }
        if (dir == 2 && y+1 < 3)
        {
            exchange(x, y, x, y+1);
            y++;
        }
        if (dir == 3 && x+1 < 3)
        {
            exchange(x, y, x+1, y);
            x++;
        }*/
    }
    private void seedMove(int seed)
    {
        locateBox = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        indexBox = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        //x = 0;
        //y = 0;
        while(seed > 0)
        {
            int[] dirList = moveDirList(locateBox[0]);
            int temp = seed % (dirList.length);
            
            move(dirList[temp]);
            
            System.out.printf("%d��%d  %d\n", dirList.length, seed, temp);
            
            seed /= dirList.length;
            
            
            
        }
        //x = locateBox[0] % 3;
        //y = locateBox[0] / 3;
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    // �b���Ӧ�l�i�H�����Ӥ�V����
    private int[] moveDirList(int place)
    {
        switch (place)
        {
            case 0: return new int[]{2, 3};
            case 1: return new int[]{1, 2, 3};
            case 2: return new int[]{1, 2};
            case 3: return new int[]{0, 2, 3};
            case 4: return new int[]{0, 1, 2, 3};
            case 5: return new int[]{0, 1, 2};
            case 6: return new int[]{0, 3};
            case 7: return new int[]{0, 1, 3};
            case 8: return new int[]{0, 1};
        }
        return new int[]{};
    }
    
    // public void exchange(int x1, int y1, int x2, int y2)
    // {
    //    int a = x1 + y1 *3;
    //    int b = x2 + y2 *3;
    public void exchange(int a, int b)
     {
        int ta = indexBox[a];
        int tb = indexBox[b];
        indexBox[a] = tb;
        indexBox[b] = ta;
        
        int temp = locateBox[ta];
        locateBox[ta] = locateBox[tb] ;
        locateBox[tb] = temp;
        /*
        ImageView temp = imageViewBox[x1+y1*3];
        imageViewBox[x1+y1*3] = imageViewBox[x2+y2*3];
        imageViewBox[x2+y2*3] = temp;*/
        //System.out.printf("%d %d \n", a, b);
        
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
