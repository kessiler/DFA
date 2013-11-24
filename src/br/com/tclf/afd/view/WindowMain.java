package br.com.tclf.afd.view;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 10/11/13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public class WindowMain extends JFrame {

    private JPanel contentPane;
    public WindowMain() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        setMinimumSize(new Dimension(450, 500));
        setLayout(new BorderLayout());
        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createTitledBorder("Visualização do AFD"));
        contentPane.setLayout(new BorderLayout(0, 0));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
    }
}
