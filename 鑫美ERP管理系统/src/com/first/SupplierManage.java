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
/**
		 * 
		 * @author SiVan
		 * @time 2017年4月26日 下午10:11:17
		 * TODO	供应商添加
		 */
public class SupplierManage extends JPanel{

	private JLabel supplier_Name = new JLabel("名称");
	private JLabel supplier_LinkMan = new JLabel("联系人");
	private JLabel supplier_Address = new JLabel("地址");
	private JLabel supplier_Phone = new JLabel("联系方式");
	private JLabel supplier_BankAc = new JLabel("银行账号");
	private JLabel supplier_WeChat = new JLabel("微信号");
	private JTextField name_Field = new JTextField();
	private JTextField linkman_Field = new JTextField();
	private JTextField address_Field = new JTextField();
	private JTextField phone_Field = new JTextField();
	private JTextField bankAc_Field = new JTextField();
	private JTextField wechat_Field = new JTextField();
	
	private JButton button = new JButton("添加");
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "1018222wxw";
	public SupplierManage() {
		// TODO Auto-generated constructor stub
		this.setLayout(null);
		supplier_Name.setBounds(30, 30, 80, 30);
		name_Field.setBounds(130, 30, 250, 30);
		supplier_LinkMan.setBounds(30, 90, 80, 30);
		linkman_Field.setBounds(130, 90, 250, 30);
		supplier_Address.setBounds(30, 150, 80, 30);
		address_Field.setBounds(130, 150, 250, 30);
		supplier_Phone.setBounds(30, 210, 80, 30);
		phone_Field.setBounds(130, 210, 250, 30);
		supplier_BankAc.setBounds(30, 270, 80, 30);
		bankAc_Field.setBounds(130, 270, 250, 30);
		button.setBounds(200, 390, 80, 30);
		wechat_Field.setBounds(130, 330, 250, 30);
		supplier_WeChat.setBounds(30, 330, 80, 30);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = name_Field.getText();
				String linkman = linkman_Field.getText();
				String address = address_Field.getText();
				String phone = phone_Field.getText();
				String bank = bankAc_Field.getText();
				String wechat = wechat_Field.getText().trim();
				
				if("".equals(name)){
					JOptionPane.showMessageDialog(null, "姓名不能为空，请重新输入");
					return;
				}
				if("".equals(address)){
					JOptionPane.showMessageDialog(null, "供应商地址不能为空，请重新输入");
					return;
				}
				if("".equals(linkman)){
					JOptionPane.showMessageDialog(null, "联系人不能为空，请重新输入");
					return;
				}
				if("".equals(phone)){
					JOptionPane.showMessageDialog(null, "联系方式不能为空，请重新输入");
					return;
				}
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "insert into supplier(sName, sLinkman, sAddress, sPhone, sBankAc, sWeChat, sStatus) values (?,?,?,?,?,?,\"1\")";
		 				PreparedStatement ps  = conn.prepareStatement(sql);
		 				ps.setObject(1, name);
		 				ps.setObject(2, linkman);
		 				ps.setObject(3, address);
		 				ps.setObject(4, phone);
		 				ps.setObject(5, bank);
		 				ps.setObject(6, wechat);
		 				int n = ps.executeUpdate();
		 				if(n <= 0 ){
		 					JOptionPane.showMessageDialog(null, "请检查数据库服务是否正常运转！");
		 					return;
		 				}else{
		 					JOptionPane.showMessageDialog(null, "添加成功！");
		 					linkman_Field.setText("");
		 					name_Field.setText("");
		 					phone_Field.setText("");
		 					bankAc_Field.setText("");
		 					address_Field.setText("");
		 					wechat_Field.setText("");
		 				}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		this.add(name_Field);
		this.add(supplier_Name);
		this.add(supplier_LinkMan);
		this.add(linkman_Field);
		this.add(supplier_Address);
		this.add(address_Field);
		this.add(supplier_Phone);
		this.add(phone_Field);
		this.add(supplier_BankAc);
		this.add(bankAc_Field);
		this.add(button);
		this.add(wechat_Field);
		this.add(supplier_WeChat);
		this.setVisible(true);
	}
}
