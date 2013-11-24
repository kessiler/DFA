package br.com.tclf.afd;

import br.com.tclf.afd.controller.ApplicationController;
import java.awt.EventQueue;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 02/11/13
 * Time: 01:09
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new ApplicationController();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
