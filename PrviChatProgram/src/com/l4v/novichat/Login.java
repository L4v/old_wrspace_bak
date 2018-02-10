package com.l4v.novichat;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textNaziv;
	private JTextField textAdresa;
	private JLabel lblAdress;
	private JTextField textPort;
	private JLabel lblPort;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		setResizable(false);
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(200, 224);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textNaziv = new JTextField();
		textNaziv.setBounds(32, 27, 130, 20);
		contentPane.add(textNaziv);
		textNaziv.setColumns(10);

		JLabel lblName = new JLabel("Name:");
		lblName.setBounds(74, 13, 46, 14);
		contentPane.add(lblName);

		textAdresa = new JTextField();
		textAdresa.setBounds(32, 72, 130, 20);
		contentPane.add(textAdresa);
		textAdresa.setColumns(10);

		lblAdress = new JLabel("Adress:");
		lblAdress.setBounds(74, 58, 46, 14);
		contentPane.add(lblAdress);

		textPort = new JTextField();
		textPort.setColumns(10);
		textPort.setBounds(32, 116, 130, 20);
		contentPane.add(textPort);

		lblPort = new JLabel("Port:");
		lblPort.setBounds(74, 103, 46, 14);
		contentPane.add(lblPort);

		JButton btnNewButton = new JButton("Login!");
		btnNewButton.setBounds(52, 158, 89, 23);
		contentPane.add(btnNewButton);
	}
}
