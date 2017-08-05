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

import com.pojo.PortClassifyInfo;
import com.pojo.PortInfo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月23日 下午8:16:58
		 * TODO	添加进货单商品的对话框界面，花盆名称不可以为空
		 * 对于同一个花盆编号，我们不可以重复添加，只可以添加一次
		 */
public class AddPortDialog extends JDialog{

	private JLabel portId_Label = new JLabel("花盆编号");
	private JLabel portClassify_Label = new JLabel("花盆种类");
	private JLabel portName_Label = new JLabel("花盆名称");
	private JLabel portCount_Label = new JLabel("花盆数量");
	private JLabel portPrice_Label = new JLabel("单价");
	private JLabel portMaking_Label = new JLabel("花盆产地");
	
	private JTextField portName_Field = new JTextField();
	private JTextField portCount_Field = new JTextField();
	private JTextField portPrice_Field = new JTextField();
	private JTextField portMaking_Field = new JTextField();
	private JComboBox<PortInfo> portId_Box = new JComboBox<PortInfo>();
	private JComboBox<PortClassifyInfo> portClassify_Box = new JComboBox<PortClassifyInfo>();
	private JButton button  = new JButton("添加");
	
	
	
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "1018222wxw";
	public AddPortDialog(PurchaseManage addPurchasePanel, final JTable table, final Vector<String> header) {
		// TODO Auto-generated constructor stub
		this.setTitle("添加进货花盆");
		this.setSize(350, 420);
		this.setModal(true);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		portId_Label.setBounds(30, 30, 90, 30);
		portId_Box.setBounds(130, 30, 180, 30);
		portClassify_Label.setBounds(30, 80, 90, 30);
		portClassify_Box.setBounds(130, 80, 180, 30);
		portName_Label.setBounds(30, 130, 90, 30);
		portName_Field.setBounds(130, 130, 180, 30);
		portName_Field.setHorizontalAlignment(JTextField.CENTER);
		portCount_Label.setBounds(30, 180, 90, 30);
		portCount_Field.setBounds(130, 180, 180, 30);
		portCount_Field.setHorizontalAlignment(JTextField.CENTER);
		portPrice_Label.setBounds(30, 230, 90, 30);
		portPrice_Field.setBounds(130, 230, 180, 30);
		portPrice_Field.setHorizontalAlignment(JTextField.CENTER);
		portMaking_Label.setBounds(30, 280, 90, 30);
		portMaking_Field.setBounds(130, 280, 180, 30);
		portMaking_Field.setHorizontalAlignment(JTextField.CENTER);
		button.setBounds(220, 335, 90, 30);
		
		portName_Field.setEditable(false);
		
		
		try{
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql1 = "select pi.piId,pc.pcClassify,s.sName,pi.piName,pi.piPrice, pi.piMaking,pi.piNumber,s.sId,pc.pcId from portInfo pi, portClassify pc, supplier s where pi.sId = s.sId and pi.pcId = pc.pcId ";
				PreparedStatement ps1 = conn.prepareStatement(sql1);
				ResultSet rs1 = ps1.executeQuery();
				while(rs1.next()){
					PortInfo port = new PortInfo();
					port.setPortId(rs1.getString(1));
					port.setPortClassify(rs1.getString(2));
					port.setPortName(rs1.getString(4));
					port.setPortNumber(rs1.getString(7));
					port.setPortPrice(rs1.getString(5));
					port.setPortSupplier(rs1.getString(3));
					port.setPortMaking(rs1.getString(6));
					port.setPortSupplierId(rs1.getString(8));
					port.setPortClassifyId(rs1.getString(9));
					portId_Box.addItem(port);
				}
				
				PortInfo port = (PortInfo) portId_Box.getSelectedItem();
				PortClassifyInfo classify = new PortClassifyInfo();
				classify.setClassifyName(port.getPortClassify());
				portName_Field.setText(port.getPortName());
				portClassify_Box.addItem(classify);
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启！");
			e1.printStackTrace();
		}
		
		
		portId_Box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PortInfo port = (PortInfo) portId_Box.getSelectedItem();
				try{
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "select pc.pcClassify from portinfo pi, portClassify pc where pi.piId = ? and pc.pcId = pi.pcId";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, port.getPortId());
						ResultSet rs = ps.executeQuery();
						portClassify_Box.removeAllItems();
						while(rs.next()){
							PortClassifyInfo classify = new PortClassifyInfo();
							classify.setClassifyName(rs.getString(1));
							portClassify_Box.addItem(classify);
							portName_Field.setText(port.getPortName());
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启！");
					e1.printStackTrace();
				}
			}
		});
		
		portClassify_Box.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PortInfo port = (PortInfo) portId_Box.getSelectedItem();
				PortClassifyInfo classify = (PortClassifyInfo) portClassify_Box.getSelectedItem();
				try{
					
				}catch(Exception e1){
					e1.printStackTrace();
				}
				
			}
		});
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PortInfo port = (PortInfo) portId_Box.getSelectedItem();
				PortClassifyInfo classify = (PortClassifyInfo) portClassify_Box.getSelectedItem();
				String portId = port.getPortId();
				String portClassify = classify.getClassifyName();
				String portName = portName_Field.getText().trim();
				int portCount = 0;
				double portPrice = 0;
				try{
					portCount = Integer.parseInt(portCount_Field.getText().trim());
					portPrice = Double.parseDouble(portPrice_Field.getText().trim());
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "输入非法！");
					e1.printStackTrace();
				}
				
				String portMaking = portMaking_Field.getText().trim();
				
				if("".equals(portId)){
					JOptionPane.showMessageDialog(null, "花盆编号不能为空!");
					return;
				}
				
				if("".equals(portClassify)){
					JOptionPane.showMessageDialog(null, "花盆种类不能为空!");
					return;
				}
//				if("".equals(portName)){
//					JOptionPane.showMessageDialog(null, "花盆名称不能为空!");
//					return;
//				}
				if("".equals(portCount)){
					JOptionPane.showMessageDialog(null, "进货数量不能为空!");
					return;
				}
				if("".equals(portPrice)){
					JOptionPane.showMessageDialog(null, "花盆进价不能为空!");
					return;
				}
				if("".equals(portMaking)){
					JOptionPane.showMessageDialog(null, "花盆产地不能为空!");
					return;
				}
				
				
				//对于同一个花盆编号，我们不可以重复添加，只可以添加一次
				int count = table.getRowCount();
				for(int i = 0; i< count; i++){
					if(portId.equals(table.getValueAt(i, 0)))
					{
						JOptionPane.showMessageDialog(null, "很抱歉，对于同一编号的花盆，只可以添加一次,如果需要继续添加，请删除对应编号的花盆后，再进行添加");
						return;
					}
				}
				Vector vector = new Vector();
				vector.add(portId);
				vector.add(portClassify);
				vector.add(portName);
				vector.add(portCount);
				vector.add(portPrice);
				vector.add(portMaking);
				PurchaseManage.dataVector.add(vector);
				DefaultTableModel tableModel = new DefaultTableModel(PurchaseManage.dataVector,header);
				table.setModel(tableModel);
				AddPortDialog.this.dispose();
			}
		});
		
		
		
		
		this.add(portId_Label);
		this.add(portId_Box);
		this.add(portClassify_Label);
		this.add(portClassify_Box);
		this.add(portName_Label);
		this.add(portName_Field);
		this.add(portCount_Label);
		this.add(portCount_Field);
		this.add(portPrice_Label);
		this.add(portPrice_Field);
		this.add(portMaking_Label);
		this.add(portMaking_Field);
		this.add(button);
		this.setVisible(true);
	}
	
	
//	public static Vector<String> getNewPortInfo(){
//		return vector;
//	}
	
}
