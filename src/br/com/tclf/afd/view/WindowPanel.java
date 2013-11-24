package br.com.tclf.afd.view;

import br.com.tclf.afd.model.State;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 10/11/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class WindowPanel extends JPanel {

    public WindowPanel() {
        setBorder(BorderFactory.createTitledBorder("Visualização do AFD"));
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);
    }
}
