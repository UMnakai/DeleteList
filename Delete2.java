package gui;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.event.*;

public class Delete2 extends JFrame implements ActionListener {

	private JList list;
	private DefaultListModel model;

	public static void main(String[] args) {
		Delete2 test = new Delete2("DeleteList");

		/* 終了処理を変更 */
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		test.setBounds(10, 10, 250, 160);
		test.setVisible(true);
	}

	Delete2(String title) {
		setTitle(title);

		model = new DefaultListModel();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost/TDL?serverTimezone=JST", "root",
					"root");
			Statement st = con.createStatement();

			String sql = "SELECT Task FROM Tasks WHERE InputDay=current_date";
			ResultSet rs = st.executeQuery(sql);

			while (rs.next()) {
				model.addElement(rs.getString("Task"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "データベースに接続できません", "接続エラー", JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		}

		list = new JList(model);

		JScrollPane sp = new JScrollPane();
		sp.getViewport().setView(list);
		sp.setPreferredSize(new Dimension(200, 80));

		JPanel p = new JPanel();
		p.add(sp);

		getContentPane().add(p, BorderLayout.CENTER);

		JButton RestrButton = new JButton("Restr");
		RestrButton.addActionListener(this);
		RestrButton.setActionCommand("RestrButton");

		JButton DeleteButton = new JButton("Del");
		DeleteButton.addActionListener(this);
		DeleteButton.setActionCommand("DeleteButton");

		JButton ReturnButton = new JButton("Return");
		ReturnButton.addActionListener(this);
		ReturnButton.setActionCommand("ReturnButton");

		JPanel p2 = new JPanel();
		p2.add(RestrButton);
		p2.add(DeleteButton);
		p2.add(ReturnButton);

		getContentPane().add(p2, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();

		if (actionCommand.equals("RestrButton")) {
			if (!list.isSelectionEmpty()) {
				int index = list.getSelectedIndex();
				model.remove(index);
			}
		} else if (actionCommand.equals("DeleteButton")) {
			if (!list.isSelectionEmpty()) {
				int minIndex = list.getMinSelectionIndex();
				int maxIndex = list.getMaxSelectionIndex();
				model.removeRange(minIndex, maxIndex);
			}
		} else if (actionCommand.equals("ReturnButton")) {
			System.exit(0);
		} else {
			return;
			// DeleteDialog todoDialog = new DeleteDialog();
			// deletedialog.setVisible(true);
			// setVisible(false);
		}
	}
}
