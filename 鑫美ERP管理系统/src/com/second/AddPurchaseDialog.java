package com.second;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import com.pojo.PortClassifyInfo;
import com.pojo.PortInfo;
import com.pojo.Suppplier;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月27日 下午8:00:23
		 * TODO	添加进货
		 */
public class AddPurchaseDialog extends JDialog{
	

	private JLabel supplierName = new JLabel("供应商名称");
	private JLabel portId = new JLabel("花盆编号");
	private JLabel linkman = new JLabel("联系人");
	private JLabel count = new JLabel("数量");
	private JLabel price = new JLabel("单价");
	private JLabel date = new JLabel("时间");
	
	
	private JComboBox<Suppplier> supplierName_Field = new JComboBox<Suppplier>();
	private JComboBox<PortInfo> portId_Field = new JComboBox<PortInfo>();
	private JTextField linkman_Field = new JTextField();
	private JTextField count_Field = new JTextField();
	private JTextField price_Field = new JTextField();
	private JTextField date_Field = new JTextField();
	
	private JLabel name_Label = new JLabel("名称");
	
	private JTextField textField = new JTextField();
	private JButton button =  new JButton("添加");
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	public AddPurchaseDialog(final QueryPurchase query) {
		// TODO Auto-generated constructor stub
		this.setTitle("添加采购");
		this.setLayout(null);
		this.setSize(680, 380);
		this.setLocationRelativeTo(null);
		
		Chooser choose = Chooser.getInstance();
		choose.register(date_Field);
		linkman_Field.setEditable(false);
		supplierName.setBounds(30, 30, 80, 30);
		supplierName_Field.setBounds(130, 30, 180, 30);
		portId.setBounds(30, 90, 80, 30);
		portId_Field.setBounds(130, 90, 180, 30);
		linkman.setBounds(30, 150, 80, 30);
		linkman_Field.setBounds(130, 150, 180, 30);
		count.setBounds(30, 210, 80, 30);
		count_Field.setBounds(130, 210, 180, 30);
		name_Label.setBounds(330, 90, 80, 30);
		textField.setBounds(430, 90, 180, 30);
		price.setBounds(330, 150, 80, 30);
		price_Field.setBounds(430, 150, 180, 30);
		date.setBounds(330, 210, 80, 30);
		date_Field.setBounds(430, 210, 180, 30);
		button.setBounds(360, 270, 80, 30);
		this.add(supplierName);
		this.add(supplierName_Field);
		this.add(portId);
		this.add(portId_Field);
		this.add(linkman);
		this.add(linkman_Field);
		this.add(count);
		this.add(count_Field);
		this.add(price);
		this.add(price_Field);
		this.add(price);
		this.add(price_Field);
		this.add(date);
		this.add(date_Field);
		this.add(button);
//		this.add(name_Label);
//		this.add(textField);
		
		
		try{
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql2 = "select sId,sName,sLinkman from supplier where sStatus = \"1\"";
				String sql3 = "select piId,piNumber,piPrice,piName from portinfo where piStatus = \"1\"";
				PreparedStatement ps1 = conn.prepareStatement(sql2);
				PreparedStatement ps2 = conn.prepareStatement(sql3);
				ResultSet rs1 = ps1.executeQuery();
				ResultSet rs2 = ps2.executeQuery();
				while(rs1.next()){
					Suppplier supplier = new Suppplier();
					supplier.setSupplierId(rs1.getString(1));
					supplier.setSupplierName(rs1.getString(2));
					supplier.setSupplierLinkMan(rs1.getString(3));
					supplierName_Field.addItem(supplier);
								}
				while(rs2.next()){
					PortInfo port = new PortInfo();
					port.setPortId(rs2.getString(1));
					port.setPortNumber(rs2.getString(2));
					port.setPortPrice(rs2.getString(3));
					port.setPortName(rs2.getString(4));
					portId_Field.addItem(port);
				}
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "请检查数据库服务是否正常运转");
			e1.printStackTrace();
		}
		
		//设置第一次使用的值
		Suppplier supplier = (Suppplier)supplierName_Field.getSelectedItem();
		linkman_Field.setText(supplier.getSupplierLinkMan());
		
		supplierName_Field.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				Suppplier supplier = (Suppplier)supplierName_Field.getSelectedItem();
				linkman_Field.setText(supplier.getSupplierLinkMan());
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int portCount = 0;
				double price = 0;
				int number = Integer.parseInt(((PortInfo)portId_Field.getSelectedItem()).getPortNumber());
				try{ 
					price = Double.parseDouble(price_Field.getText().trim());
					portCount = Integer.parseInt(count_Field.getText().trim());
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "输入非法！");
					e1.printStackTrace();
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
				
				
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "insert into purchase(piId,pCount,pPrice,pDate,pStatus) values(?,"
								+ "?,?,?,\"1\")";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, ((PortInfo)portId_Field.getSelectedItem()).getPortId());
						ps.setObject(2, portCount);
						ps.setObject(3, price);
						ps.setObject(4, date);
						int n = ps.executeUpdate();
						if(n <= 0){
							JOptionPane.showMessageDialog(null, "添加失败");
							return;
						}
						String sql2 = "update portinfo set piNumber = ? where piId = ?";
						PreparedStatement ps1 = conn.prepareStatement(sql2);
						ps1.setObject(1, Integer.parseInt(((PortInfo)portId_Field.getSelectedItem()).getPortNumber()) + portCount);
						ps1.setObject(2, ((PortInfo)portId_Field.getSelectedItem()).getPortId());
						int n1 = ps1.executeUpdate();
						if(n1 > 0){
							JOptionPane.showMessageDialog(null, "添加成功！");
							AddPurchaseDialog.this.dispose();
							query.dispose();
							new QueryPurchase();
						}else{
							JOptionPane.showMessageDialog(null, "添加失败！");
							return;
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查数据库服务是否正常运转");
					e1.printStackTrace();
				}
			}
		});
		this.setVisible(true);
	}
	
	
}
