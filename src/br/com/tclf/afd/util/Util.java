package br.com.tclf.afd.util;

import br.com.tclf.afd.model.AFD;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 10/11/13
 * Time: 13:33
 * To change this template use File | Settings | File Templates.
 */
public class Util {
    public static void writeFile(String pathFile, String content) throws IOException {
        File file = new File(pathFile);
        FileWriter fw = new FileWriter(file);
        fw.write(content);
        fw.flush();
        fw.close();
    }
}
