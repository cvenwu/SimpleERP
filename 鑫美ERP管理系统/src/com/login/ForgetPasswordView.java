package com.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.resource.spi.work.HintsContext;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月12日 下午10:13:29
		 * TODO 忘记密码：用户需要输入用户名和自己的身份证号码进行检查，通过之后对密码进行重置，然后进去ERP之后修改
		 * 				需要判断用户是否为员工以及是否在职,并且在输入前身份证号码要使用正则表达式进行匹配
		 */
public class ForgetPasswordView extends JDialog{
	
	private JLabel username_Label = new JLabel("用户名");
	private JTextField username_Field = new JTextField();
	private JLabel idcard_Label = new JLabel("身份证号码");
	private JTextField idcard_Field = new JTextField();
	private JButton reset_Button = new JButton("重置");
	
	//数据库
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "1018222wxw";
	

	public ForgetPasswordView() {
		// TODO Auto-generated constructor stub
		this.setTitle("忘记密码");
		this.setSize(400, 240);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setModal(true);
		this.setResizable(false);
		
		username_Label.setBounds(50, 40, 100, 30);
		username_Field.setBounds(180, 40, 150, 30);
		idcard_Label.setBounds(50, 100, 100, 30);
		idcard_Field.setBounds(180, 100, 150, 30);
		reset_Button.setBounds(190, 160, 100, 30);
		
		reset_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String username = username_Field.getText();
				if("".equals(username)){
					JOptionPane.showMessageDialog(null, "用户名不能为空");
					return;
				}
				
				
				//身份证号码正则匹配
				final String id = idcard_Field.getText();
				String regex = "^\\d{17}[0-9xX]$";
				Pattern pattern = Pattern.compile(regex);
				Matcher match = pattern.matcher(id);
				if(!match.find()){
					JOptionPane.showMessageDialog(null, "请检查身份证号码格式是否正确");
					return;
				}
				
				
				try{
					Class.forName("com.mysql.jdbc.Driver");
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "select e.eId from employee e, userinfo u WHERE e.eStatus = '1' and e.eCard = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, id);
						ResultSet rs = ps.executeQuery();
						if(rs.next())
						{
							String sql2 = "UPDATE  userinfo SET uPass = '123456' where uName = ?";
							ps = conn.prepareStatement(sql2);
							ps.setObject(1, username);
							int n = ps.executeUpdate();
							if(n <= 0){
								JOptionPane.showMessageDialog(null, "您的用户名输入错误");
								return;
							}
							JOptionPane.showMessageDialog(null, "您的密码已被重置为123456，为了安全起见，请您进入系统后进行修改");
							ForgetPasswordView.this.dispose();
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查数据库是否正常运转");
					e1.printStackTrace();
				}
			}
		});
		
		
		this.add(username_Label);
		this.add(username_Field);
		this.add(idcard_Label);
		this.add(idcard_Field);
		this.add(reset_Button);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new ForgetPasswordView();
	}
}
