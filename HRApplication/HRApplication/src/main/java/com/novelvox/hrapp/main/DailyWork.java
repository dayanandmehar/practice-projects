package com.novelvox.hrapp.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * DAILY WORK FORM
 *
 */

class DailyWork extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 1L;
	JTextField tt1, tt2;
	JLabel ll1, ll2;
	JButton bb1, bb2;

	DailyWork() {

		setLayout(null);
		setBackground(Color.CYAN);

		ll1 = new JLabel("Number:");
		ll2 = new JLabel("Square:");

		tt1 = new JTextField();
		tt2 = new JTextField();

		bb1 = new JButton("CLICK");
		bb1.addActionListener(this);
		bb2 = new JButton("Exit");
		bb2.addActionListener(this);

		add(ll1);
		add(ll2);
		add(tt1);
		add(tt2);
		add(bb1);
		add(bb2);

		ll1.setBounds(50, 50, 60, 30);
		ll2.setBounds(50, 100, 60, 30);
		tt1.setBounds(120, 50, 60, 30);
		tt2.setBounds(120, 100, 60, 30);
		bb1.setBounds(90, 150, 100, 30);
		bb2.setBounds(200, 150, 100, 30);

	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == bb1) {
			int n = Integer.parseInt(tt1.getText());
			int s = n * n * n;
			tt2.setText("" + s);
		}

		if (ae.getSource() == bb2) {
			System.exit(0);
		}

	}
}

