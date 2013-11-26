package br.com.tclf.afd.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Created with IntelliJ IDEA.
 * User: KESSILER
 * Date: 10/11/13
 * Time: 15:13
 * To change this template use File | Settings | File Templates.
 */
public class WindowPanel extends JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WindowPanel() {
        setBorder(BorderFactory.createTitledBorder("Visualização do AFD"));
        setLayout(new BorderLayout(0, 0));
        setBackground(Color.WHITE);
    }
}
