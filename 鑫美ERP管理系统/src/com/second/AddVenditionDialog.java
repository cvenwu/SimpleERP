package com.second;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.plugin.Chooser;
import com.pojo.Customer;
import com.pojo.EmpInfo;
import com.pojo.PortInfo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月27日 上午11:39:30
		 * TODO	添加销售
		 */
public class AddVenditionDialog extends JDialog{

	
	
	private JLabel customerName = new JLabel("客户名称");
	private JLabel portId = new JLabel("售出花盆编号");
	private JLabel employee = new JLabel("销售人");
	private JLabel count = new JLabel("数量");
	private JLabel singlePrice = new JLabel("花盆标价");
	private JLabel price = new JLabel("售出单价");
	private JLabel Price = new JLabel("售出总价");
	private JLabel date = new JLabel("出货时间");
	
	
	private JTextField count_Field = new JTextField();
	private JTextField singlePrice_Field = new JTextField();
	private JTextField price_Field = new JTextField();
	private JTextField Price_Field = new JTextField();
	private JTextField date_Field = new JTextField();
	
	private JComboBox<Customer> customerName_Field = new JComboBox<Customer>();
	private JComboBox<PortInfo> portId_Field = new JComboBox<PortInfo>();
	private JComboBox<EmpInfo> employee_Field = new JComboBox<EmpInfo>();
	
	private JButton button =  new JButton("添加");
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	public AddVenditionDialog(final QueryVendition query) {
		// TODO Auto-generated constructor stub
		this.setTitle("添加销售");
		this.setLayout(null);
		this.setSize(680, 300);
		this.setLocationRelativeTo(null);
		
		Chooser choose = Chooser.getInstance();
		choose.register(date_Field);
		
		customerName.setBounds(30, 30, 80, 30);
		customerName_Field.setBounds(130, 30, 180, 30);
		portId.setBounds(30, 90, 80, 30);
		portId_Field.setBounds(130, 90, 180, 30);
		employee.setBounds(30, 150, 80, 30);
		employee_Field.setBounds(130, 150, 180, 30);
		count.setBounds(330, 90, 80, 30);
		count_Field.setBounds(430, 90, 180, 30);
		singlePrice.setBounds(330, 150, 80, 30);
		singlePrice_Field.setBounds(430, 150, 180, 30);
		price.setBounds(330, 30, 80, 30);
		price_Field.setBounds(430, 30, 180, 30);
		Price.setBounds(330, 90, 80, 30);
		Price_Field.setBounds(430, 90, 180, 30);
		date.setBounds(330, 150, 80, 30);
		date_Field.setBounds(430, 150, 180, 30);
		button.setBounds(360, 210, 80, 30);
		this.add(customerName);
		this.add(customerName_Field);
		this.add(employee_Field);
		this.add(employee);
		this.add(portId);
		this.add(portId_Field);
		this.add(count);
		this.add(count_Field);
//		this.add(singlePrice);
//		this.add(singlePrice_Field);
		this.add(price);
		this.add(price_Field);
//		this.add(Price);
//		this.add(Price_Field);
		this.add(date);
		this.add(date_Field);
		this.add(button);
		
		
		try{
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select eId,eName from employee where eStatus = \"1\"";
				String sql2 = "select cId,cName from customer where cStatus = \"1\"";
				String sql3 = "select piId,piNumber,piPrice from portinfo where piStatus = \"1\"";
				PreparedStatement ps = conn.prepareStatement(sql);
				PreparedStatement ps1 = conn.prepareStatement(sql2);
				PreparedStatement ps2 = conn.prepareStatement(sql3);
				ResultSet rs = ps.executeQuery();
				ResultSet rs1 = ps1.executeQuery();
				ResultSet rs2 = ps2.executeQuery();
				while(rs.next()){
					EmpInfo emp = new EmpInfo();
					emp.setId(rs.getString(1));
					emp.setName(rs.getString(2));
					employee_Field.addItem(emp);
				}
				while(rs1.next()){
					Customer customer = new Customer();
					customer.setCustomerId(rs1.getString(1));
					customer.setCustomerName(rs1.getString(2));
					customerName_Field.addItem(customer);
								}
				while(rs2.next()){
					PortInfo port = new PortInfo();
					port.setPortId(rs2.getString(1));
					port.setPortNumber(rs2.getString(2));
					port.setPortPrice(rs2.getString(3));
					portId_Field.addItem(port);
				}
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "请检查数据库服务是否正常运转");
			e1.printStackTrace();
		}
		
		
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int portCount = 0;
				double singlePrice = 0;
				double price = 0;
				double Price = 0;
				int number = Integer.parseInt(((PortInfo)portId_Field.getSelectedItem()).getPortNumber());
				try{ 
					price = Double.parseDouble(price_Field.getText().trim());
					portCount = Integer.parseInt(count_Field.getText().trim());
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "输入非法！");
					e1.printStackTrace();
				}
				
				if(portCount - number > 0 ){
					JOptionPane.showMessageDialog(null, "很抱歉，没有足够的库存");
					return;
				}
				Date date = null;
				SimpleDateFormat date1 = new SimpleDateFormat("yyy-MM-dd");
				try {
					date = date1.parse(date_Field.getText());
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "请输入合法的日期格式");
				}
				
				Connection conn = null;
				try{
						conn = DriverManager.getConnection(url, user, password);
						conn.setAutoCommit(false);
						String sql = "insert into vendition(cId,piId,eId,vCount,vPrice,vDate,vStatus) values(?,"
								+ "?,?,?,?,?,\"1\")";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, ((Customer)customerName_Field.getSelectedItem()).getCustomerId());
						ps.setObject(2, ((PortInfo)portId_Field.getSelectedItem()).getPortId());
						ps.setObject(3, ((EmpInfo)employee_Field.getSelectedItem()).getId());
						ps.setObject(4, portCount);
						ps.setObject(5, price);
						ps.setObject(6, date);
						ps.executeUpdate();
						
						
						String sql2 = "update portinfo set piNumber = ? where piId = ?";
						ps = conn.prepareStatement(sql2);
						ps.setObject(1, Integer.parseInt(((PortInfo)portId_Field.getSelectedItem()).getPortNumber()) - portCount);
						ps.setObject(2, ((PortInfo)portId_Field.getSelectedItem()).getPortId());
						ps.executeUpdate();
						
						
						//使用事务进行操作
						/*
						PreparedStatement ps = conn.prepareStatement(sql);
						
						int n = ps.executeUpdate();
						if(n <= 0){
							JOptionPane.showMessageDialog(null, "添加失败");
							return;
						}
						String sql2 = "update portinfo set piNumber = ? where piId = ?";
						PreparedStatement ps1 = conn.prepareStatement(sql2);
						ps1.setObject(1, Integer.parseInt(((PortInfo)portId_Field.getSelectedItem()).getPortNumber()) - portCount);
						ps1.setObject(2, ((PortInfo)portId_Field.getSelectedItem()).getPortId());
						int n1 = ps1.executeUpdate();
						if(n1 > 0){
							JOptionPane.showMessageDialog(null, "添加成功！");
							AddVenditionDialog.this.dispose();
							query.dispose();
							new QueryVendition();
						}else{
							JOptionPane.showMessageDialog(null, "添加失败！");
							return;
						}*/
						conn.commit();
						conn.setAutoCommit(true);
						JOptionPane.showMessageDialog(null, "添加成功！");
						AddVenditionDialog.this.dispose();
						query.dispose();
						new QueryVendition();
				}catch(Exception e1){
					try {
						conn.rollback();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "请检查数据库服务是否正常运转");
					e1.printStackTrace();
				}finally{
					try {
						conn.setAutoCommit(true);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						conn.close();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		this.setVisible(true);
	}
	
	
	
	
}
