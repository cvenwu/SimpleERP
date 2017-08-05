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

import com.pojo.PortClassifyInfo;
import com.pojo.Zone;

public class UpdateAndDeleteCustomerZone extends JPanel{

	private JLabel zoneId = new JLabel("客户区域编号");
	private JLabel zoneName = new JLabel("客户区域");
	private JLabel zoneLabel = new JLabel("请选择客户区域");
	
	private JComboBox<Zone> zone = new JComboBox<Zone>();
	
	private JTextField zoneId_Field = new JTextField();
	private JTextField zoneName_Filed = new JTextField();
	
	private JButton update = new JButton("修改");
	private JButton delete = new JButton("删除");
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "1018222wxw";
	public UpdateAndDeleteCustomerZone() {
		// TODO Auto-generated constructor stub
		this.setToolTipText("客户区域添加，修改和删除！");
		this.setLayout(null);
		zoneId.setBounds(35, 105, 100, 35);
		zoneId_Field.setBounds(155, 105, 220, 35);
		zoneName.setBounds(35, 35, 220, 35);
		zoneName_Filed.setBounds(155, 35, 220, 35);
		zoneLabel.setBounds(35, 175, 100, 35);
		zone.setBounds(155, 175, 220, 35);
		update.setBounds(60, 245, 80, 35);
		delete.setBounds(310, 245, 80, 35);
		
		zoneId_Field.setEditable(false);
		try{
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select zId,zName from zone where zStatus = \"1\"";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					Zone zone1 = new Zone();
					zone1.setZoneId(rs.getString(1));
					zone1.setZone(rs.getString(2));
					zone.addItem(zone1);
				}
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
		
		Zone zone1 = (Zone) zone.getSelectedItem();
		zoneId_Field.setText(zone1.getZoneId());
		zoneName_Filed.setText(zone1.getZone());
		
		
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int n = JOptionPane.showConfirmDialog(null, "请注意：删除客户区域之后，该客户区域所在的客户也将会一并删除！！！", "警告", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if(n == 0){
					try{
						try(
							Connection conn = DriverManager.getConnection(url, user, password);
								){
							String sql = "update zone set zStatus = \"0\" where zId = ?";
							PreparedStatement ps = conn.prepareStatement(sql);
							ps.setObject(1, ((Zone)zone.getSelectedItem()).getZoneId());
							int n1 = ps.executeUpdate();
							if(n1 > 0){
								JOptionPane.showMessageDialog(null, "删除成功");
								try{
									zone.removeItem(zone.getSelectedItem());
								}catch(Exception e2){
									JOptionPane.showMessageDialog(null, "您现在已经没有客户区域，请及时添加");
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
			}
		});
		
		
		zone.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Zone zone1 = (Zone) zone.getSelectedItem();
				zoneId_Field.setText(zone1.getZoneId());
				zoneName_Filed.setText(zone1.getZone());
			}
		});
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = zoneName_Filed.getText().trim();
				if("".equals(name)){
					JOptionPane.showMessageDialog(null, "对不起，客户区域名称不能为空！");
					return;
				}
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "update zone set zName = ? where zId = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, name);
						String id = ((Zone)zone.getSelectedItem()).getZoneId();
						ps.setObject(2, id);
						int n1 = ps.executeUpdate();
						if(n1 > 0){
							JOptionPane.showMessageDialog(null, "修改成功");
							try{
								zone.removeItem(zone.getSelectedItem());
							}catch(Exception e2){
								JOptionPane.showMessageDialog(null, "您现在已经没有花盆种类，请及时添加");
								return;
							}
							Zone zone2 = new Zone();
							zone2.setZone(name);
							zone2.setZoneId(id);
							zone.addItem(zone2);
							Zone zone3 = (Zone) zone.getSelectedItem();
							zoneId_Field.setText(zone3.getZoneId());
							zoneName_Filed.setText(zone3.getZone());
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
		
		this.add(zoneId);
		this.add(zoneId_Field);
		this.add(zoneName);
		this.add(zoneName_Filed);
		this.add(zoneLabel);
		this.add(zone);
		this.add(update);
		this.add(delete);
		this.setVisible(true);
	}
}
