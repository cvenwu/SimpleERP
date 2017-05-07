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
	/**
	 * 
	 * @author SiVan
	 * @time 2017年4月24日 上午10:23:32
	 * TODO	花盆分类修改和删除
	 */
public class UpdateAndDeletePortClassifyPanel extends JPanel{

	private JLabel classifyName = new JLabel("花盆分类编号");
	private JLabel classifyId = new JLabel("花盆分类");
	private JLabel classifyLabel = new JLabel("请选择花盆分类");
	
	private JComboBox<PortClassifyInfo> classifyBox = new JComboBox<PortClassifyInfo>();
	
	private JTextField classifyName_Field = new JTextField();
	private JTextField classifyId_Field = new JTextField();
	
	private JButton update = new JButton("修改");
	private JButton delete = new JButton("删除");
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	public UpdateAndDeletePortClassifyPanel() {
		// TODO Auto-generated constructor stub
		this.setToolTipText("花盆分类添加，修改和删除！");
		this.setLayout(null);
		classifyName.setBounds(35, 105, 100, 35);
		classifyName_Field.setBounds(155, 105, 220, 35);
		classifyId.setBounds(35, 35, 220, 35);
		classifyId_Field.setBounds(155, 35, 220, 35);
		classifyLabel.setBounds(35, 175, 100, 35);
		classifyBox.setBounds(155, 175, 220, 35);
		update.setBounds(60, 245, 80, 35);
		delete.setBounds(310, 245, 80, 35);
		
		classifyId_Field.setEditable(false);
		try{
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select pcId,pcClassify from portclassify where pcStatus = \"1\"";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					PortClassifyInfo portclassify = new PortClassifyInfo();
					portclassify.setClassifyId(rs.getString(1));
					portclassify.setClassifyName(rs.getString(2));
					classifyBox.addItem(portclassify);
				}
			}
		}catch(Exception e1){
			e1.printStackTrace();
		}
		
		
		PortClassifyInfo portclassify = (PortClassifyInfo) classifyBox.getSelectedItem();
		classifyName_Field.setText(portclassify.getClassifyName());
		classifyId_Field.setText(portclassify.getClassifyId());
		
		
		
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				int n = JOptionPane.showConfirmDialog(null, "请注意：删除花盆种类之后，对应的花盆也将会一并删除！！！", "警告", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if(n == 0){
					try{
						try(
							Connection conn = DriverManager.getConnection(url, user, password);
								){
							String sql = "update portclassify set pcStatus = \"0\" where pcId = ?";
							PreparedStatement ps = conn.prepareStatement(sql);
							ps.setObject(1, ((PortClassifyInfo)classifyBox.getSelectedItem()).getClassifyId());
							int n1 = ps.executeUpdate();
							if(n1 > 0){
								JOptionPane.showMessageDialog(null, "删除成功");
								try{
									classifyBox.removeItem(classifyBox.getSelectedItem());
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
			}
		});
		
		
		classifyBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PortClassifyInfo portclassify = (PortClassifyInfo) classifyBox.getSelectedItem();
				classifyName_Field.setText(portclassify.getClassifyName());
				classifyId_Field.setText(portclassify.getClassifyId());
			}
		});
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = classifyName_Field.getText().trim();
				if("".equals(name)){
					JOptionPane.showMessageDialog(null, "对不起，花盆名称不能为空！");
					return;
				}
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "update portclassify set pcClassify = ? where pcId = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, name);
						String id = ((PortClassifyInfo)classifyBox.getSelectedItem()).getClassifyId();
						ps.setObject(2, ((PortClassifyInfo)classifyBox.getSelectedItem()).getClassifyId());
						int n1 = ps.executeUpdate();
						if(n1 > 0){
							JOptionPane.showMessageDialog(null, "修改成功");
							try{
								classifyBox.removeItem(classifyBox.getSelectedItem());
							}catch(Exception e2){
								JOptionPane.showMessageDialog(null, "您现在已经没有花盆种类，请及时添加");
								return;
							}
							PortClassifyInfo classify = new PortClassifyInfo();
							classify.setClassifyId(id);
							classify.setClassifyName(name);
							classifyBox.addItem(classify);
							PortClassifyInfo portclassify = (PortClassifyInfo) classifyBox.getSelectedItem();
							classifyName_Field.setText(portclassify.getClassifyName());
							classifyId_Field.setText(portclassify.getClassifyId());
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
		this.add(classifyName);
		this.add(classifyName_Field);
		this.add(classifyId);
		this.add(classifyId_Field);
		this.add(classifyLabel);
		this.add(classifyBox);
		this.add(update);
		this.add(delete);
		this.setVisible(true);
	}
}
