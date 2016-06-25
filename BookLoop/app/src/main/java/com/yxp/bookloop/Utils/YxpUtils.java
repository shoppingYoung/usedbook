package com.yxp.bookloop.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class YxpUtils {
    public static String sendDataByPost(String... params){
        try {
            URL url = new URL(params[0]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStream out = conn.getOutputStream();
            String content = params[1]+"="+params[2]+"&"+params[3]+"="+params[4]+"&"+params[5]+"="+params[6];
            out.write(content.getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String str;
            StringBuffer sb = new StringBuffer();
            while ( (str=reader.readLine())!=null ){
                sb.append(str);
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
