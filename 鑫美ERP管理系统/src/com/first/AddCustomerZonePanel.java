package com.first;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddCustomerZonePanel extends JPanel{

	private JLabel name_Label = new JLabel("客户区域名称");
	private JTextField name_Field = new JTextField();
	private JButton addButton = new JButton("添加");
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	public AddCustomerZonePanel() {
		// TODO Auto-generated constructor stub
		this.setLayout(null);
		name_Label.setBounds(50, 150, 100, 35);
		name_Field.setBounds(170, 150, 220, 35);
		name_Field.setHorizontalAlignment(JTextField.CENTER);
		addButton.setBounds(170, 255, 100, 35);
		
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String classifyName = name_Field.getText().trim();
				if("".equals(classifyName)){
					JOptionPane.showMessageDialog(null, "客户区域名称不能为空");
					return;
				}
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "select zName from zone";
						String sql1 = "insert into zone(zName,zStatus) values (?,\"1\")";
						PreparedStatement ps = conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery();
						while(rs.next()){
							if(classifyName.equals(rs.getString(1))){
								JOptionPane.showMessageDialog(null, "添加失败，该客户区域已经存在！");
								return;
							}
						}
						PreparedStatement ps1 = conn.prepareStatement(sql1);
						ps1.setObject(1, classifyName);
						int n = ps1.executeUpdate();
						if(n <= 0){
							JOptionPane.showMessageDialog(null, "添加失败，请检查数据库服务是否开启！");
							return;
						}else{
							JOptionPane.showMessageDialog(null, "添加成功！");
							name_Field.setText("");
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		
		this.add(name_Label);
		this.add(name_Field);
		this.add(addButton);
		this.setVisible(true);
	}
}

