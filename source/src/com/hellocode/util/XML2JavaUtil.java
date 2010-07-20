package com.hellocode.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import com.hellocode.service.Config;
import com.hellocode.service.RunTime;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XML2JavaUtil {
        
        public static void java2XML(String xmlFile, Config cfg) throws Exception {
                XStream xs = new XStream();
                File file = new File(xmlFile);
                FileOutputStream fs = new FileOutputStream(file.getAbsoluteFile());
                Writer write = new OutputStreamWriter(fs);
                write.write("Hello, world\n");
                xs.toXML(cfg, fs);              
                System.out.println("ok3"+xmlFile);
        }

        public static Config XML2Java(String xmlFile) throws FileNotFoundException {
                XStream reverse = new XStream(new DomDriver());
                Config cfg = new Config();
                FileInputStream fis = new FileInputStream(xmlFile);
                reverse.fromXML(fis, cfg);
                // fis.close();
                System.out.println(cfg.toString());
                return cfg;
        }
        public static void main(String[] args) throws Exception {
        	RunTime.CONFIG.disk_main="testok";
        	RunTime.CONFIG.disk_other.add("testok");
        	RunTime.CONFIG.disk_other.add("e");
        	RunTime.CONFIG.disk_other.add("5");
        	XML2JavaUtil.java2XML("D:\\kankan\\mead.txt", RunTime.CONFIG);
        	
		}
}