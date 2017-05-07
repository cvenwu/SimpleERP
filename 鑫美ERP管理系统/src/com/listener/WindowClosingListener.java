package com.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

public class WindowClosingListener extends WindowAdapter{

	
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		int i = JOptionPane.showConfirmDialog(null, "您确定要退出么？", "退出", JOptionPane.YES_NO_OPTION);
		if(i == 0)
		{
			JOptionPane.showMessageDialog(null, "您已成功退出该系统");
			System.exit(0);
		}
			
	}
	
	
}
