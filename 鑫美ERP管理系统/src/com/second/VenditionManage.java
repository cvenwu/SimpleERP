package com.second;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.pojo.Customer;
import com.pojo.EmpInfo;
import com.pojo.Suppplier;


	/**
	 * 
	 * @author SiVan
	 * @time 2017年5月2日 下午4:40:36
	 * TODO	销售管理，采用事务进行操作
	 */
public class VenditionManage extends JPanel{

	private JLabel customer_Label = new JLabel("请选择客户");
	private JComboBox<Customer> customer_Box = new JComboBox<Customer>();
	private JLabel date_Label = new JLabel("销售日期");
	private JTextField date_Field = new JTextField();
	private JLabel employee_Label = new JLabel("销售人");
	private JComboBox<EmpInfo> employee_Box = new JComboBox<EmpInfo>();
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "1018222wxw";
	
	private JScrollPane scrollpane = new JScrollPane();
	private JTable table = new JTable();
	
	
	private JButton add = new JButton("添加");
	private JButton commit = new JButton("登记");
	private JButton delete_Button = new JButton("删除");
	
	public VenditionManage() {
		// TODO Auto-generated constructor stub
		
		this.setLayout(null);
		customer_Label.setBounds(50, 30, 80, 35);
		customer_Box.setBounds(140, 30, 160, 35);
		employee_Label.setBounds(350, 30, 80, 35);
		employee_Box.setBounds(440, 30, 120, 35);
		date_Label.setBounds(580, 30, 80, 35);
		date_Field.setBounds(680, 30, 120, 35);
		scrollpane.setBounds(65, 90, 760, 500);
		add.setBounds(400, 610, 100, 35);
		commit.setBounds(550, 610, 100, 35);
		delete_Button.setBounds(250, 610, 100, 35);
		
		
		
		Vector<String> header = new  Vector<String>();
		header.add("花盆编号");
		header.add("花盆种类");
		header.add("花盆名称");
		header.add("数量");
		header.add("单个售价");
		
		try(
			Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select eId, eName from employee where eStatus = \"1\"";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					EmpInfo emp = new EmpInfo();
					emp.setId(rs.getString(1));
					emp.setName(rs.getString(2));
					employee_Box.addItem(emp);
				}
				
				String sql2 = "select ";
				ps = conn.prepareStatement(sql2);
				rs = ps.executeQuery();
				while(rs.next()){
					
				}
				
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启！");
			e1.printStackTrace();
		}
		
		
		this.setVisible(true);
	}
	
	
}
