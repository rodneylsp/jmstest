package com.jmstest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private static Configuration confiuracao;

    private static Properties props;

    public Configuration(){
        try {
            props = new Properties();
            //props.load(new FileInputStream("./config.properties"));
            props.load(Configuration.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static Configuration getInstance(){
//        if(confiuracao == null){
//            confiuracao = new Configuration();
//        }
//        return confiuracao;
//    }

    public static String getConnectionFactory(){
        return props.getProperty("jms.connection.factory");
    }

    public static String getQueue(){
        return props.getProperty("jms.queue");
    }

}
