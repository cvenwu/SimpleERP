package com.main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.view.LogInView;
import com.view.MainView;
import com.view.MainView1;

		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月9日 下午7:02:40
		 * TODO    修改密码
		 * 			1.修改之前判断了密码强弱以及密码长度位数
		 * 			2.修改时判断新旧密码是否重复，以及新密码与确认密码是否相等
		 * 			3.遗留问题：修改成功后，如何关闭当前的，进行重启
		 */
public class UpdatePasswordView extends JDialog{
	
	private JLabel oldpassword_Label = new JLabel("原始密码");
	private JLabel newpassword_Label = new JLabel("新密码");
	private JLabel repeatpassword_Label = new JLabel("确认密码");
	private JLabel newCheck_Label = new JLabel();
	private JLabel repeatCheck_Label = new JLabel();
	
	private JTextField oldpassword_Field = new JPasswordField();
	private JTextField newpassword_Field = new JPasswordField();
	private JTextField repeatpassword_Field = new JPasswordField();
	
	private JButton update_Button = new JButton("修改");

	//数据库
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "1018222wxw";
	public UpdatePasswordView(final String username,final MainView main) {
		// TODO Auto-generated constructor stub
		this.setTitle("修改密码");
		this.setModal(true);
		this.setSize(400, 350);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		oldpassword_Label.setBounds(50, 50, 80, 35);
		oldpassword_Field.setBounds(150, 50, 180, 35);
		newpassword_Label.setBounds(50, 120, 80, 35);
		newpassword_Field.setBounds(150, 120, 180, 35);
		newCheck_Label.setBounds(360, 120, 30, 35);
		repeatpassword_Label.setBounds(50, 190, 80, 35);
		repeatpassword_Field.setBounds(150, 190, 180, 35);
		repeatCheck_Label.setBounds(360, 190, 30, 35);
		update_Button.setBounds(250, 260, 80, 35);
		
		this.add(oldpassword_Label);
		this.add(oldpassword_Field);
		this.add(newpassword_Label);
		this.add(newpassword_Field);
		this.add(newCheck_Label);
		this.add(repeatpassword_Label);
		this.add(repeatpassword_Field);
		this.add(repeatCheck_Label);
		this.add(update_Button);
		
		//添加键盘监听器事件
		newpassword_Field.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				String newpassword = newpassword_Field.getText();
				
				//密码长度小于6位
				if(newpassword.length() < 6){
					newCheck_Label.setText("×");
					return;
				}
				
				//如果长度大于6位，则进行密码强弱的判断
				int num = 0;
				int character = 0;
				int other = 0;
				
				for (int i = 0; i < newpassword.length(); i++) {
					char ch = newpassword.charAt(i);
					if(ch >= '0' && ch <= '9')
						num = 1;
					else if((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z'))
						character = 1;
					else
						other = 1;
				}
				int result = other + num + character;
				if( result == 1 )
					newCheck_Label.setText("弱");
				else if(result == 2)
					newCheck_Label.setText("中");
				else
					newCheck_Label.setText("强");
				
			}
			
		});
		
		
		//添加确认密码的键盘事件
		repeatpassword_Field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				String repeatpassword = repeatpassword_Field.getText();
				String newpassword = newpassword_Field.getText();
				if(repeatpassword.equals(newpassword))
					repeatCheck_Label.setText("√");
				else
					repeatCheck_Label.setText("×");
			}
		});
		
		//添加按钮监听器事件
		update_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String oldpassword = oldpassword_Field.getText();
				String newpassword = newpassword_Field.getText();
				String repeatpassword = repeatpassword_Field.getText();
				
				//首先判断密码长度是否小于等于6位数字，小于则×，并且终止，否则√
				if(newpassword.length() < 6 )
				{
					newCheck_Label.setText("×");
					JOptionPane.showMessageDialog(null, "密码长度不能小于6位");
					return;							//因为长度小于等于6位，没必要进行判断了
				}
				
				
				if(!repeatpassword.equals(newpassword))
				{
					JOptionPane.showMessageDialog(null, "新密码与确认密码不相同，请稍后再试！");
					return;
				}
				
				if(oldpassword.equals(newpassword))
				{
					JOptionPane.showMessageDialog(null, "原始密码与新密码相同，修改无效！");
					return;
				}
				try{
					Class.forName("com.mysql.jdbc.Driver"); 
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "update userinfo set uPass = ? where uPass = ? and uName = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, newpassword);
						ps.setObject(2, oldpassword);
						ps.setObject(3, username);
						int n = ps.executeUpdate();
						if(n > 0)
						{
							JOptionPane.showMessageDialog(null, "修改成功");
							UpdatePasswordView.this.dispose();
							main.dispose();
							new LogInView();
						}else{
							JOptionPane.showMessageDialog(null, "修改失败，请稍后再试！");
							return;
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查数据库是否正常运转");
					e1.printStackTrace();
				}
			}
		});
		this.setVisible(true) ;
	}
}
