package com.text.editor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Editor extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JEditorPane j;
	private Container c; 
	private JPopupMenu pum;

	private final Integer WIDTH = 400;
	private final Integer HEIGHT = 200;

	public Editor() {
		super("eVo Editor Text");

		c = getContentPane();
		j = new JEditorPane();

		//////////////////////////////////////////////////////////////////
		/*
		 * File Menu
		 */
		/////////////////////////////////////////////////////////////////
		JMenuBar mb = new JMenuBar();
		JMenu mn = new JMenu("File");
		JMenuItem n = new JMenuItem("New");
		JMenuItem op = new JMenuItem("Open");
		JMenuItem saveas = new JMenuItem("Save As");
		JMenuItem exit = new JMenuItem("Exit");

		n.addActionListener(this);
		op.addActionListener(this);
		saveas.addActionListener(this);
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});

		mn.add(n);
		mn.add(op);
		mn.add(saveas);
		mn.add(exit);

		mb.add(mn);

		//////////////////////////////////////////////////////////////////
		/*
		 * Edit Menu
		 */
		/////////////////////////////////////////////////////////////////
		JMenu ed = new JMenu("Edit");
		JMenuItem copy = new JMenuItem("Copy");
		JMenuItem cut = new JMenuItem("Cut");
		JMenuItem paste = new JMenuItem("Paste");

		copy.addActionListener(this);
		cut.addActionListener(this);
		paste.addActionListener(this);

		ed.add(copy);
		ed.add(cut);
		ed.add(paste);

		mb.add(ed);

		//////////////////////////////////////////////////////////////////
		/*
		 * Pop up Menu created which will be displayed once a user right click
		 * on the editor.
		 * 
		 * The pop up menu will have the following options: copy, cut, paste and
		 * change color of the text.
		 */
		/////////////////////////////////////////////////////////////////
		pum = new JPopupMenu();

		JMenuItem pumcopy = new JMenuItem("Copy");
		JMenuItem pumcut = new JMenuItem("Cut");
		JMenuItem pumpaste = new JMenuItem("Paste");
		JMenuItem changecolor = new JMenuItem("Change Color");

		pum.add(pumcopy);
		pum.add(pumcut);
		pum.add(pumpaste);
		pum.add(changecolor);

		pumcopy.addActionListener(this);
		pumcut.addActionListener(this);
		pumpaste.addActionListener(this);
		changecolor.addActionListener(this);

		j.add(pum);

		//////////////////////////////////////////////////////////////////
		/*
		 * On mouse click we want to show the pop up menu, so we obtain the
		 * (x,y) position of the mouse click and show the pop up menu on those
		 * coordinates
		 */
		/////////////////////////////////////////////////////////////////
		j.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.isPopupTrigger()) {
					pum.show(j, e.getX(), e.getY());
				}
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.isPopupTrigger()) {
					pum.show(j, e.getX(), e.getY());
				}
			}
		});

		//////////////////////////////////////////////////////////////////
		/*
		 * Set Window
		 */
		/////////////////////////////////////////////////////////////////
		setJMenuBar(mb);
		c.add(new JScrollPane(j), BorderLayout.CENTER);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		// TODO Auto-generated method stub

		if (ae.getActionCommand().equals("Open")) {
			JFileChooser choose = new JFileChooser();
			choose.setCurrentDirectory(new File("D:/MyOwnTextEditor/Files"));

			if (choose.showOpenDialog(this) != JFileChooser.CANCEL_OPTION) {
				try {
					URL u = choose.getSelectedFile().toURI().toURL();
					j.setPage(u);
				} catch (IOException e) {
					// TODO: handle exception
				}
			}
		} else {
			if (ae.getActionCommand().equals("New")) {
				j.setText("");
			} else {
				if (ae.getActionCommand().equals("Save As")) {
					JFileChooser choose = new JFileChooser();
					choose.setCurrentDirectory(new File("D:/MyOwnTextEditor/Files"));
					choose.showSaveDialog(this);

					try {
						FileWriter fw = new FileWriter(choose.getSelectedFile());
						fw.write(j.getText());
						fw.close();
					} catch (IOException e) {
						// TODO: handle exception
					}
				} else {
					if (ae.getActionCommand().equals("Copy")) {
						j.copy();
					} else {
						if (ae.getActionCommand().equals("Paste")) {
							j.paste();
						} else {
							if (ae.getActionCommand().equals("Cut")) {
								j.cut();
							} else {
								if (ae.getActionCommand().equals("Change Color")) {
									Color col = JColorChooser.showDialog(j, "Choose a color", Color.red);
									j.setCaretColor(col);
									j.setForeground(col);
								}
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Editor();
			}
		});
	}
}
