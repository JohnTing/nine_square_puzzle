/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package javafxapplication2;
import java.util.Random;
/**
 *
 * @author JohnTing
 */
public class AiClass {
    // w = 0, a = 1, s = 2, d = 3
    
    public static int work(int[] locateBox, int[] indexBox)
    {
        int dir = 0;
        switch(locateBox[0])
        {
            case 0: dir = 3; break;
            case 1: dir = 3; break;
            case 2: dir = 2; break; 
            case 3: dir = 0; break; 
            case 4: dir = 0; break; 
            case 5: dir = 2; break; 
            case 6: dir = 0; break; 
            case 7: dir = 1; break; 
            case 8: dir = 1; break; 
            default: dir = 0;
        }
        System.out.printf("%d:%d\n", locateBox[0], dir);

        return dir;
    }
};
