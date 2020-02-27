import java.io.*;
import java.util.*;

public class Input {
    public static String s;
    public Input() {
        //读取文件
        BufferedReader br = null;
        StringBuffer sb = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("Input.txt"))); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        s= new String(sb); //StringBuffer ==> String
        //arr_context=s.toCharArray();
        //for(int i=0;i<arr_context.length;i++){
        //    System.out.print(arr_context[i]);
        //}
    }
}

