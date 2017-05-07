package com.second;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.plugin.Chooser;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月27日 下午11:54:34
		 * TODO	进货修改
		 */
public class UpdatePurchase extends JDialog{

	private JLabel port = new JLabel("花盆价格");
	private JLabel portNumber = new JLabel("花盆数量");
	private JLabel dateLabel = new JLabel("采购日期");
	private JTextField port_Field = new JTextField();
	private JTextField number = new JTextField();
	private JTextField date_Field = new JTextField();
	private JButton button = new JButton("修改");
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	int number1 = 0;
	public UpdatePurchase(final String portId1, final JTable table, final Vector<Vector<String>> data, 
			final Vector<String> header, final int raw, final QueryPurchase query) {
		// TODO Auto-generated constructor stub
		this.setTitle("采购修改");
		this.setSize(380, 280);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		port.setBounds(30, 30, 80, 30);
		port_Field.setBounds(140, 30, 180, 30);
		portNumber.setBounds(30, 90, 80, 30);
		number.setBounds(140, 90, 180, 30);
		dateLabel.setBounds(30, 150, 80, 30);
		date_Field.setBounds(140, 150, 180, 30);
		button.setBounds(210, 210, 80, 30);
		
		
		
		Chooser chooser = Chooser.getInstance();
		chooser.register(date_Field);
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
					JOptionPane.showMessageDialog(null, "花盆价格不可以为空");
					return;
				}
				int number3;
				double price;
				try{
					number3 = Integer.parseInt(number2);
					price = Double.parseDouble(portid);
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "输入非法");
					return;
				}
				
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sq = "select piNumber from portinfo where piId = ?";
						PreparedStatement p = conn.prepareStatement(sq);
						p.setObject(1, portId1);
						ResultSet rs = p.executeQuery();
						String number4 = null;
						while(rs.next()){
							number4 = rs.getString(1);
						}
						
						
						String sql2 = "update purchase set pCount = ?, pPrice = ? where pId = ? and pStatus = \"1\"";
						PreparedStatement ps2 = conn.prepareStatement(sql2);
						ps2.setObject(1, number2);
						ps2.setObject(2, price);
						ps2.setObject(3, table.getValueAt(raw, 0));
						int n2 = ps2.executeUpdate();
						if(n2 <= 0 ){
							JOptionPane.showMessageDialog(null, "修改失败，请稍后再试");
							return;
						}
						System.out.println(number4);
						String sql = "update portinfo  set piNumber = ? where piId = ? ";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, Integer.parseInt(number4) + Integer.parseInt(number2)) ;
						System.out.println(number2);
						ps.setObject(2, portId1);
						int n = ps.executeUpdate();
						if(n <= 0 ){
							JOptionPane.showMessageDialog(null, "修改失败，请稍后再试");
							return;
						}else{
							JOptionPane.showMessageDialog(null, "修改成功");
							UpdatePurchase.this.dispose();
							query.dispose();
							new QueryPurchase();
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
					e1.printStackTrace();
				}
			}
		});
		
		this.add(port);
		this.add(port_Field);
		this.add(portNumber);
		this.add(number);
		this.add(button);
		this.add(dateLabel);
		this.add(date_Field);
		this.setVisible(true);
	}
	
	
	
}

