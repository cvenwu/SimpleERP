package com.second;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UpdateInventory extends JDialog{

		private JLabel port = new JLabel("花盆编号");
		private JLabel portNumber = new JLabel("花盆数量");

		private JTextField port_Field = new JTextField();
		private JTextField number = new JTextField();
		
		private JButton button = new JButton("修改");
		private String url = "jdbc:mysql://localhost:3306/erp";
		private String user = "root";
		private String password = "yourpassword";
		int number1 = 0;
		public UpdateInventory(String portId, final JTable table, final Vector<Vector<String>> data, 
				final Vector<String> header, final int raw) {
			// TODO Auto-generated constructor stub
			this.setTitle("修改库存");
			this.setSize(350, 230);
			this.setLayout(null);
			this.setLocationRelativeTo(null);
			
			port.setBounds(30, 30, 80, 30);
			port_Field.setBounds(140, 30, 180, 30);
			portNumber.setBounds(30, 90, 80, 30);
			number.setBounds(140, 90, 180, 30);
			button.setBounds(210, 150, 80, 30);
			
			port_Field.setText(portId);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String number2 = number.getText();
					String portid = port_Field.getText();
					if("".equals(number2)){
						JOptionPane.showMessageDialog(null, "请添加要往库存中添加的商品数量");
						return;
					}
					if("".equals(portid)){
						JOptionPane.showMessageDialog(null, "花盆编号不可以为空");
						return;
					}
					int number3;
					try{
						number3 = Integer.parseInt(number2);
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "输入非法");
						return;
					}
					
					try{
						try(
							Connection conn = DriverManager.getConnection(url, user, password);
								){
							String sql = "update portinfo  set piNumber = ? where piId = ? ";
							PreparedStatement ps = conn.prepareStatement(sql);
							ps.setObject(1, number3);
							ps.setObject(2, portid);
							int n = ps.executeUpdate();
							if(n <= 0 ){
								JOptionPane.showMessageDialog(null, "修改失败，请稍后再试");
								return;
							}else{
								JOptionPane.showMessageDialog(null, "修改成功");
								Vector<String> vector = new Vector<String>();
								data.removeElementAt(raw);
								vector.add(portid);
								vector.add((String)table.getValueAt(raw, 1));
								vector.add((String)table.getValueAt(raw, 2));
								vector.add(number3 + "");
								data.add(raw, vector);
								DefaultTableModel model = new DefaultTableModel(data,header);
								table.setModel(model);
								UpdateInventory.this.dispose();
							}
						}
					}catch(Exception e1){
						JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
						e1.printStackTrace();
					}
				}
			});
			
			port_Field.setEditable(false);
			this.add(port);
			this.add(port_Field);
			this.add(portNumber);
			this.add(number);
			this.add(button);
			this.setVisible(true);
		}
		
		
		public static void main(String[] args) {
			new AddInventoryDialog();
		}
		
	}

