package com.first;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.plugin.Chooser;
import com.pojo.EmpInfo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月23日 下午1:45:40
		 * TODO	员工管理：员工添加，修改与删除
		 */

public class AddEmployeePanel extends JPanel {
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	
	private JLabel empName_Label = new JLabel("员工姓名");
	private JLabel empCard_Label = new JLabel("员工身份证号码");
	private JLabel empPhone_Label = new JLabel("员工联系方式");
	private JLabel empHireDate_Label = new JLabel("入职日期");
	
	private JTextField empName_Field = new JTextField();
	private JTextField empCard_Field = new JTextField();
	private JTextField empPhone_Field = new JTextField();
	private JTextField empHireDate_Field = new JTextField();
	
	private JButton addButton = new JButton("添加");
	public static int i = 0;
	static EmpInfo emp = new EmpInfo();
	public AddEmployeePanel() {
		// TODO Auto-generated constructor stub
		
		this.setLayout(null);
		empName_Label.setBounds(50, 50, 120, 30);
		empName_Field.setBounds(200, 50, 200, 30);
		empName_Field.setHorizontalAlignment(JTextField.CENTER);
		empCard_Label.setBounds(50, 100, 120, 30);
		empCard_Field.setBounds(200, 100, 200, 30);
		empCard_Field.setHorizontalAlignment(JTextField.CENTER);
		empPhone_Label.setBounds(50, 150, 120, 30);
		empPhone_Field.setBounds(200, 150, 200, 30);
		empPhone_Field.setHorizontalAlignment(JTextField.CENTER);
		empHireDate_Label.setBounds(50, 200, 120, 30);
		empHireDate_Field.setBounds(200, 200, 200, 30);
		addButton.setBounds(270, 260, 80, 30);
		
		Chooser chooser = Chooser.getInstance();
		chooser.register(empHireDate_Field);
		
		
		
		
		addButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String eName = empName_Field.getText().trim();
				String eCard = empCard_Field.getText().trim();
				String ePhone = empPhone_Field.getText().trim();
				String eHiredate = empHireDate_Field.getText().trim();
				
				
				if("".equals(eName)){
					JOptionPane.showMessageDialog(null, "姓名不能为空，请重新输入");
					return;
				}
				
				if("".equals(eCard)){
					JOptionPane.showMessageDialog(null, "身份证号码不能为空，请重新输入");
					return;
				}
				
				if("".equals(ePhone)){
					JOptionPane.showMessageDialog(null, "联系方式不能为空，请重新输入");
					return;
				}
				
				if("".equals(eHiredate)){
					JOptionPane.showMessageDialog(null, "入职日期不能为空，请重新输入");
					return;
				}
				
				//正则表达式验证身份证号码是否合法
				String regex = "^\\d{17}[0-9xX]$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(eCard);
				if(!matcher.find()){
					JOptionPane.showMessageDialog(null, "请检查身份证号码是否正确");
					return;
				}
				
				//正则表达式验证联系方式是否合法
				String regex1 = "^1[34578]\\d{9}$";
				Pattern pattern1 = Pattern.compile(regex1);
				Matcher matcher1 = pattern1.matcher(ePhone);
				if(!matcher1.find()){
					JOptionPane.showMessageDialog(null, "请检查联系方式是否正确");
					System.out.println(ePhone);
					return;
				}
				
				
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse(eHiredate);
				} catch (ParseException e2) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "请确定日期选择是否合法");
					e2.printStackTrace();
				}
				
				
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						//验证身份证号码是否重复
						String sql1 = "select eName from employee where eCard = ?";
						PreparedStatement ps1 = conn.prepareStatement(sql1);
						ps1.setObject(1, empCard_Field.getText().trim());
						ResultSet rs = ps1.executeQuery();
						if(rs.next()){
							JOptionPane.showMessageDialog(null, "相同身份证号码，无法继续添加");
							return;
						}else{
							String sql = "insert into employee(eName,eCard,ePhone,eHiredate,eStatus) values(?,?,?,?,\"1\")";
							PreparedStatement ps = conn.prepareStatement(sql);
							ps.setObject(1, eName);
							ps.setObject(2, eCard);
							ps.setObject(3, ePhone);
							ps.setObject(4, eHiredate);
							int n = ps.executeUpdate();
							if(n <= 0){
								JOptionPane.showMessageDialog(null, "添加失败，请稍后再试");
								return;
							}else{
								JOptionPane.showMessageDialog(null, "添加成功");
								i = 1;
								emp.setName(eName);
								emp.setCard(eCard);
								emp.setHiredate(eHiredate);
								emp.setPhone(ePhone);
								empName_Field.setText("");
								empCard_Field.setText("");
								empHireDate_Field.setText("");
								empPhone_Field.setText("");
							}
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查身份号码是否与已有的员工重复！");
					e1.printStackTrace();
				}
			}
		});
		
		
		this.add(empName_Label);
		this.add(empName_Field);
		this.add(empCard_Label);
		this.add(empCard_Field);
		this.add(empPhone_Label);
		this.add(empPhone_Field);
		this.add(empHireDate_Label);
		this.add(empHireDate_Field);
		this.add(addButton);
		this.setVisible(true);
	}
	
	public static EmpInfo getNewEmp(){
		return emp;
	}
	
	
}
