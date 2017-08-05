package com.first;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.pojo.Customer;
import com.pojo.Suppplier;
import com.pojo.Zone;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月27日 上午8:03:24
		 * TODO 修改和删除客户
		 */
public class UpdateAndDeleteCustomerPanel extends JPanel{
	
	private JLabel customerId = new JLabel("客户编号");
	private JLabel customerName = new JLabel("名称");
	private JLabel customerZone = new JLabel("区域");
	private JLabel customerPhone = new JLabel("联系方式");
	private JLabel customerAddress = new JLabel("地址");
	private JLabel customerWeChat = new JLabel("微信");
	private JLabel customer_Choose = new JLabel("请选择客户");
	
	private JTextField id_Field = new JTextField();
	private JTextField phone_Field = new JTextField();
	private JTextField address_Field = new JTextField();
	private JTextField name_Field = new JTextField();
	private JTextField wechat_Field = new JTextField();
	private JComboBox<Zone> zone = new JComboBox<Zone>();
	private JComboBox<Customer> customerChoose = new JComboBox<Customer>();
	
	private JButton update = new JButton("修改");
	private JButton delete = new JButton("删除");
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "1018222wxw";
	public UpdateAndDeleteCustomerPanel() {
		// TODO Auto-generated constructor stub
		id_Field.setEditable(false);
		this.setLayout(null);
		customerId.setBounds(30, 30, 80, 30);
		id_Field.setBounds(130, 30, 250, 30);
		customerName.setBounds(30, 90, 80, 30);
		name_Field.setBounds(130, 90, 250, 30);
		customerZone.setBounds(30, 150, 80, 30);
		zone.setBounds(130, 150, 250, 30);
		customerPhone.setBounds(30, 210, 80, 30);
		phone_Field.setBounds(130, 210, 250, 30);
		customerAddress.setBounds(30, 270, 80, 30);
		address_Field.setBounds(130, 270, 250, 30);
		customerWeChat.setBounds(30, 330, 80, 30);
		wechat_Field.setBounds(130, 330, 250, 30);
		customer_Choose.setBounds(30, 390, 80, 30);
		customerChoose.setBounds(130, 390, 250, 30);
		update.setBounds(130, 450, 80, 30);
		delete.setBounds(350, 450, 80, 30);
		
		try{
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql2 = "select c.cId,z.zId,z.zName,c.cName,c.cPhone,c.cWeChat,c.cAddress from customer c, zone z where c.zId = z.zId";
				PreparedStatement ps = conn.prepareStatement(sql2);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					Customer customer = new Customer();
					Zone zone = new Zone();
					customer.setCustomerId(rs.getString(1));
					zone.setZone(rs.getString(3));
					zone.setZoneId(rs.getString(2));
					customer.setCustomerName(rs.getString(4));
					customer.setCustomerPhone(rs.getString(5));
					customer.setCustomerWeChat(rs.getString(6));
					customer.setCustomerAddress(rs.getString(7));
					customerChoose.addItem(customer);
					this.zone.addItem(zone);
				}
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
		//设置第一次使用时候的默认值
		Customer customer = (Customer) customerChoose.getSelectedItem();
		id_Field.setText(customer.getCustomerId());
		phone_Field.setText(customer.getCustomerPhone());
		address_Field.setText(customer.getCustomerAddress());
		name_Field.setText(customer.getCustomerName());
		wechat_Field.setText(customer.getCustomerWeChat());
		Zone zone1 = (Zone) zone.getSelectedItem();
		zone1.setZone(zone1.getZone());
		zone1.setZoneId(zone1.getZoneId());
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Customer customer = (Customer) customerChoose.getSelectedItem();
				String name = name_Field.getText();
				String id = id_Field.getText();
				String zone1 = ((Zone) zone.getSelectedItem()).getZone();
				String phone = phone_Field.getText();
				String address = address_Field.getText();
				String wechat = wechat_Field.getText();
				if("".equals(name)){
					JOptionPane.showMessageDialog(null, "客户名称不能为空");
					return;
				}
				
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "update customer set zId = ?, cName = ?, cPhone = ?, cWeChat = ?, cAddress = ? where cId = ? and cStatus = \"1\"";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, ((Zone)zone.getSelectedItem()).getZoneId());
						ps.setObject(2, name);
						ps.setObject(3, phone);
						ps.setObject(4, wechat);
						ps.setObject(5, address);
						ps.setObject(6, customer.getCustomerId());
						int n = ps.executeUpdate();
						String cId = customer.getCustomerId();
						if(n > 0){
							JOptionPane.showMessageDialog(null, "修改成功");
							customerChoose.removeItem(customerChoose.getSelectedItem());
							String sql1 = "select z.zId,c.cName,c.cPhone,c.cWeChat,c.cAddress,z.zName from customer c, zone z where cId = ? and c.cId = z.zId";
							PreparedStatement ps1 = conn.prepareStatement(sql1);
							ps1.setObject(1, cId);
							ResultSet rs = ps1.executeQuery();
							while(rs.next()){
								Customer su1 = new Customer();
								su1.setCustomerAddress(rs.getString(5));
								su1.setCustomerName(rs.getString(2));
								su1.setCustomerPhone(rs.getString(3));
								su1.setCustomerWeChat(rs.getString(4));
								su1.setCustomerZid(rs.getString(1));
								su1.setCustomerId(cId);
								su1.setCustomerZoneName(rs.getString(6));
								customerChoose.addItem(su1);
							}
						}else{
							JOptionPane.showMessageDialog(null, "请检查数据库服务是否正常开启");
							return;
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
				
				
				
			}
		});
		
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Customer customer = (Customer) customerChoose.getSelectedItem();
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "update customer set cStatus = \"0\" where cId = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, customer.getCustomerId());
						int n = ps.executeUpdate();
						if(n > 0){
							JOptionPane.showMessageDialog(null, "删除成功");
							customerChoose.removeItem(customerChoose.getSelectedItem());
							try{
								customerChoose.removeItem(customerChoose.getSelectedItem());
							}catch(Exception e2){
								JOptionPane.showMessageDialog(null, "您现在已经没有花盆种类，请及时添加");
								return;
							}
						}else{
							JOptionPane.showMessageDialog(null, "请检查数据库服务是否正常开启");
							return;
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		
		
		this.add(delete);
		this.add(update);
		id_Field.setEditable(false);
		this.add(customerChoose);
		this.add(customer_Choose);
		this.add(wechat_Field);
		this.add(customerWeChat);
		this.add(address_Field);
		this.add(customerAddress);
		this.add(phone_Field);
		this.add(customerPhone);
		this.add(zone);
		this.add(customerZone);
		this.add(name_Field);
		this.add(customerName);
		this.add(id_Field);
		this.add(customerId);
		this.setVisible(true);
	}
	
	
	
}
