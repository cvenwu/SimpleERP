package com.first;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JTextField;

import com.mysql.jdbc.EscapeTokenizer;
import com.pojo.EmpInfo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月23日 下午2:21:14
		 * TODO	员工信息修改与删除
		 */
public class UpdateAndDeleteEmployeePanel extends JPanel{
	private JLabel eName_Label = new JLabel("员工姓名");
	private JLabel eCard_Label = new JLabel("员工身份证号码");
	private JLabel ePhone_Label = new JLabel("员工联系电话");
	private JLabel eHireDate_Label = new JLabel("员工入职日期");
	private JLabel eNameSelect_Label = new JLabel("请选择员工姓名");
	
	private JTextField eName_Field = new JTextField();
	private JTextField eCard_Field = new JTextField();
	private JTextField ePhone_Field = new JTextField();
	private JTextField eHireDate_Field = new JTextField();
	
	public static JComboBox<EmpInfo> eNameSelect = new JComboBox<EmpInfo>();
	private JButton update_Button = new JButton("修改");
	private JButton delete_Button = new JButton("删除");
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	
	
 	public UpdateAndDeleteEmployeePanel() {							
		// TODO Auto-generated constructor stub
 		this.setLayout(null);
 		eName_Label.setBounds(50, 30, 120, 30);
 		eCard_Label.setBounds(50, 80, 120, 30);
 		ePhone_Label.setBounds(50, 130, 120, 30);
 		eHireDate_Label.setBounds(50, 180, 120, 30);
 		eNameSelect_Label.setBounds(50, 230, 120, 30);
 		
 		eName_Field.setBounds(200, 30, 200, 30);
 		eCard_Field.setBounds(200, 80, 200, 30);
 		ePhone_Field.setBounds(200, 130, 200, 30);
 		eHireDate_Field.setBounds(200, 180, 200, 30);
 		
 		eNameSelect.setBounds(200, 230, 200, 30);
 		update_Button.setBounds(240, 280, 60, 30);
 		delete_Button.setBounds(310, 280, 60, 30);
 		
 		try{
 			try(
 				Connection conn = DriverManager.getConnection(url, user, password);
 					){
	 				String sql = "select eId,eName,eCard,ePhone,eHiredate from employee where eStatus = \"1\"";
	 				PreparedStatement ps  = conn.prepareStatement(sql);
	 				ResultSet rs = ps.executeQuery();
	 				//避免每次都会出现相同员工的情况
	 				try{
 						eNameSelect.removeAllItems();
 					}catch(Exception e1){
 						
 					}
	 				while(rs.next()){
	 					EmpInfo emp = new EmpInfo();
	 					emp.setId(rs.getString(1));
	 					emp.setName(rs.getString(2));
	 					emp.setCard(rs.getString(3));
	 					emp.setPhone(rs.getString(4));
	 					emp.setHiredate(rs.getString(5));
	 					eNameSelect.addItem(emp);
	 					
 				}
	 				
 			}
 		}catch(Exception e1){
 			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
 			e1.printStackTrace();
 		}
 		
 		
 		
 		
 		//设置JCOMBObox的监听事件
 		//缺点：对于同名的员工，只能按顺序显示第一个员工的顺序
 		eNameSelect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EmpInfo emp = (EmpInfo) eNameSelect.getSelectedItem();
				eCard_Field.setText(emp.getCard());
				eHireDate_Field.setText(emp.getHiredate());
				eName_Field.setText(emp.getName());
				ePhone_Field.setText(emp.getPhone());
			}
		});
 		
 		
 		update_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				EmpInfo emp = (EmpInfo) eNameSelect.getSelectedItem();
				String oldCard = emp.getCard();
				String name = eName_Field.getText().trim();
				String card = eCard_Field.getText().trim();
				String tel = ePhone_Field.getText().trim();
				String date = eHireDate_Field.getText().trim();
				int i = 0;
				while(oldCard.equals(eNameSelect.getItemAt(i))){
					
				}
				
				try{
		 			try(
		 				Connection conn = DriverManager.getConnection(url, user, password);
		 					){
		 				String sql = "update employee set eName = ?, eCard = ?, ePhone = ?, eHiredate = ? "
		 						+ "where eStatus = \"1\" and eId = ?";
		 				PreparedStatement ps  = conn.prepareStatement(sql);
		 				ps.setObject(1, name);
		 				ps.setObject(2, card);
		 				ps.setObject(3, tel);
		 				ps.setObject(4, date);
		 				ps.setObject(5, emp.getId());
		 				int n = ps.executeUpdate();
		 				if(n <= 0){
		 					JOptionPane.showMessageDialog(null, "修改失败，请稍后再试");
		 					return;
		 				}else{
		 					String id = ((EmpInfo) eNameSelect.getSelectedItem()).getId();
		 					JOptionPane.showMessageDialog(null, "修改成功");
		 					try{
		 						eNameSelect.removeItem(eNameSelect.getSelectedItem());
							}catch(Exception e2){
								JOptionPane.showMessageDialog(null, "您现在已经没有员工，请及时添加");
								return;
							}
		 					EmpInfo emp2 = new EmpInfo();
		 					emp2.setCard(card);
		 					emp2.setHiredate(date);
		 					emp2.setId(id);
		 					emp2.setName(name);
		 					emp2.setPhone(tel);
		 					eNameSelect.addItem(emp2);
		 					EmpInfo emp1 = (EmpInfo)eNameSelect.getSelectedItem();
		 			 		eCard_Field.setText(emp1.getCard());
		 			 		eHireDate_Field.setText(emp1.getHiredate());
		 			 		ePhone_Field.setText(emp1.getPhone());
		 			 		eName_Field.setText(emp1.getName());
		 				}
		 			}
		 		}catch(Exception e1){
		 			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
		 			e1.printStackTrace();
		 		}
			}
		});
 		
 		
 		delete_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String card = eCard_Field.getText();
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "update employee set eStatus = \"0\" where eCard = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, card);
						int n = ps.executeUpdate();
						if(n <= 0){
							JOptionPane.showMessageDialog(null, "删除失败，请稍后再试");
							return;
						}else{
							JOptionPane.showMessageDialog(null, "删除成功");
							try{
								eNameSelect.removeItem(eNameSelect.getSelectedItem());
							}catch(Exception e2){
								e2.printStackTrace();
								JOptionPane.showMessageDialog(null, "请用户及时添加员工");
								return;
							}
							EmpInfo emp = (EmpInfo) eNameSelect.getSelectedItem();
							eCard_Field.setText(emp.getCard());
							eHireDate_Field.setText(emp.getHiredate());
							eName_Field.setText(emp.getName());
							ePhone_Field.setText(emp.getPhone());
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查身份号码是否与已有的员工重复！");
					e1.printStackTrace();
				}
			}
		});
				
 		
 		this.add(eName_Label);
 		this.add(eCard_Label);
 		this.add(ePhone_Label);
 		this.add(eHireDate_Label);
 		this.add(eNameSelect_Label);
 		this.add(eName_Field);
 		this.add(eCard_Field);
 		this.add(ePhone_Field);
 		this.add(eHireDate_Field);
 		this.add(eNameSelect);
 		this.add(update_Button);
 		this.add(delete_Button);
 		
 		
 		//设置第一次使用时候的默认值
		EmpInfo emp = (EmpInfo)eNameSelect.getSelectedItem();
 		eCard_Field.setText(emp.getCard());
 		eHireDate_Field.setText(emp.getHiredate());
 		ePhone_Field.setText(emp.getPhone());
 		eName_Field.setText(emp.getName());
 		
 		this.setVisible(true);
	}
 	
 	protected static Vector<String> addEmp(String eName,String eCard,String eHiredate,String ePhone){
 		Vector<String> vector = new Vector<String>();
 		vector.add(eName);
 		vector.add(eCard);
 		vector.add(eHiredate);
 		vector.add(ePhone);
		return vector;
 	}
}
