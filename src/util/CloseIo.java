package util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by quchwe on 2016/4/19 0019.
 */
public class CloseIo {
    public static void closeIo(Closeable ...io){
        for (Closeable temp:io){
            if (temp!=null){
                try {
                    temp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
