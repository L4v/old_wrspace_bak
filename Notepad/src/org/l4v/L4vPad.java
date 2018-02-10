package org.l4v;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class L4vPad extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public void actionPerformed(ActionEvent a) {
		if (a.getSource() == this.closeFile) {
			this.dispose();
		} else if (a.getSource() == this.openFile) {
			JFileChooser file = new JFileChooser();
			int option = file.showOpenDialog(this);

			if (option == JFileChooser.APPROVE_OPTION) {
				this.textarea.setText("");
				try {
					Scanner scan = new Scanner(new FileReader(file.getSelectedFile().getPath()));
					while (scan.hasNext()) {
						this.textarea.append(scan.nextLine() + "\n");
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}else if(a.getSource() == this.saveFile){
			JFileChooser save = new JFileChooser();
			int option1 = save.showSaveDialog(this);
			if(option1 == JFileChooser.APPROVE_OPTION){
				try{
					
					BufferedWriter out = new BufferedWriter(new FileWriter(save.getSelectedFile().getPath()));
					out.write(this.textarea.getText());
					out.close();
				}catch (Exception x){
					System.out.println(x.getMessage());
				}
			}
		}
	}

	private TextArea textarea = new TextArea("", 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
	private MenuBar menubar = new MenuBar();
	private Menu file = new Menu();
	private MenuItem openFile = new MenuItem();
	private MenuItem saveFile = new MenuItem();
	private MenuItem closeFile = new MenuItem();

	public L4vPad() {
		this.setSize(500, 300);
		this.setTitle("L4vPivo's pad, text editor.");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.textarea.setFont(new Font("Arabic", Font.ITALIC, 12));
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(textarea);
		this.setMenuBar(menubar);
		this.menubar.add(this.file);
		this.file.setLabel("File");
		this.openFile.setLabel("Open");
		this.openFile.addActionListener(this);
		this.openFile.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		this.file.add(this.openFile);
		this.saveFile.setLabel("Save");
		this.saveFile.addActionListener(this);
		this.saveFile.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		this.file.add(this.saveFile);
		this.closeFile.setLabel("Close");
		this.closeFile.setShortcut(new MenuShortcut(KeyEvent.VK_F4, false));
		this.closeFile.addActionListener(this);
		this.file.add(this.closeFile);

	}
	
	public static void main(String args []){
		L4vPad aplikacija = new L4vPad();
		aplikacija.setVisible(true);
	}
	
}
