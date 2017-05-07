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
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.gjt.mm.mysql.Driver;

import com.first.UpdateAndDeleteEmployeePanel;
import com.plugin.Chooser;
import com.pojo.PortClassifyInfo;
import com.pojo.PortInfo;
import com.pojo.Suppplier;
/**			
		 * 
		 * @author SiVan
		 * @time 2017年4月23日 下午7:13:18
		 * TODO	进行采购登记，因为采购登记需要我们操作一张进货表和一张花盆详情表，而且两个sql语句必须同时执行或者同时不执行，所以我们采用事务
		 */
public class PurchaseManage extends JPanel {
	
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	
	
	private JLabel supplierName_Label = new JLabel("供应商名称");
	private JLabel linkMan_Label = new JLabel("联  系  人");
	private JLabel time_Label = new JLabel("进  货  时  间");
	
	private JComboBox<Suppplier> supplierName_box = new JComboBox<Suppplier>();
	private JTextField linkMan_Field = new JTextField();
	private JTextField time_Field = new JTextField();
	
	private JButton add = new JButton("添加");
	private JButton addInventory = new JButton("入库");
	private JButton deleButton = new JButton("删除");
	private JScrollPane scrollpane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private JTable table = new JTable(){
		public boolean isCellEditable(int row, int column) {
			return false;
		};
	};
	static Vector<Vector<String>> dataVector = new  Vector<Vector<String>>();;
	public PurchaseManage() {
		// TODO Auto-generated constructor stub
		
		supplierName_Label.setBounds(50, 30, 80, 35);
		supplierName_box.setBounds(140, 30, 160, 35);
		linkMan_Label.setBounds(350, 30, 80, 35);
		linkMan_Field.setBounds(440, 30, 120, 35);
		time_Label.setBounds(580, 30, 80, 35);
		time_Field.setBounds(680, 30, 120, 35);
		scrollpane.setBounds(65, 90, 760, 500);
		add.setBounds(400, 610, 100, 20);
		addInventory.setBounds(550, 610, 100, 20);
		deleButton.setBounds(250, 610, 100, 20);
		final Vector<String> header = new Vector<String>();
		header.add("花盆编号");
		header.add("花盆种类");
		header.add("花盆名称");
		header.add("数量");
		header.add("单个进价");
		header.add("花盆产地");
		
		
		//执行添加进货产品操作
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AddPortDialog(PurchaseManage.this,  table,  header);
			}
		});
		
		//执行入库JDBC操作，使用事物进行操作
		addInventory.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				
				String date = time_Field.getText().trim();
				Date date1 = null;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					date1 = sdf.parse(date);
				} catch (ParseException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				Connection conn = null;
				try{
					conn = DriverManager.getConnection(url, user, password);
					conn.setAutoCommit(false);
					String sql = "insert into purchase(piId,pCount,pPrice,pDate,pStatus) values(?,?,?,?,\"1\")";
					PreparedStatement ps = conn.prepareStatement(sql);
					int count = table.getRowCount();
					for(int i = 0; i < count; i++){
						ps.setObject(1, table.getValueAt(i, 0));
						ps.setObject(2, table.getValueAt(i, 3));
						ps.setObject(3, table.getValueAt(i, 4));
						ps.setObject(4, date1);
						ps.addBatch();
					}
					ps.executeBatch();
					
					String sql2 = "select piNumber from portinfo where piId = ?";
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					
					int[] number = new int[count];
					for(int i = 0; i < count; i++){
						ps2.setObject(1, table.getValueAt(i, 0));
						ResultSet rs = ps2.executeQuery();
						while(rs.next()){
							try{
								number[i] = Integer.parseInt(rs.getString(1));
							}catch(Exception e2){
								JOptionPane.showMessageDialog(null, "请检查数据库的数据格式是否正确");
								e2.printStackTrace();
							}
						}
					}
					
					String sql3 = "update  portinfo set piNumber = ? where piId = ?";
					ps = conn.prepareStatement(sql3);
					for(int i = 0; i < count; i++){
						ps.setObject(1, (Integer)table.getValueAt(i, 3) + number[i]);
						ps.setObject(2, table.getValueAt(i, 0));
						System.out.println("添加批处理语句" + (i + 1) + "条语句");
						ps.addBatch();
					}
					ps.executeBatch();
					System.out.println("sssssss");
					conn.commit();
				}catch(Exception e1){
					try {
						conn.rollback();
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					e1.printStackTrace();
				}finally{
					try {
						conn.setAutoCommit(true);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
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
		
		
		supplierName_box.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Suppplier su = (Suppplier) supplierName_box.getSelectedItem();
				linkMan_Field.setText(su.getSupplierLinkMan());
			}
		});
		
		deleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				DefaultTableModel df = new DefaultTableModel(PurchaseManage.dataVector, header);
				df.removeRow(row);
				table.setModel(df);
			}
		});
		try{
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select sId,sName,sLinkman,sPhone from supplier";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					Suppplier su = new Suppplier();
					su.setSupplierId(rs.getString(1));
					su.setSupplierName(rs.getString(2));
					su.setSupplierLinkMan(rs.getString(3));
					su.setSupplierPhone(rs.getString(4));
					supplierName_box.addItem(su);
				}
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启！");
			e1.printStackTrace();
		}
		//设置第一次使用的值
		Suppplier su = (Suppplier) supplierName_box.getSelectedItem();
		linkMan_Field.setText(su.getSupplierLinkMan());
		//存放表数据
		DefaultTableModel model = new DefaultTableModel(dataVector, header);
		//设置表格内容居中
        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
		table.setModel(model);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);
		
        
		Chooser chooser = Chooser.getInstance();
		chooser.register(time_Field);
		this.add(supplierName_Label);
		this.add(linkMan_Label);
		this.add(time_Label);
		this.add(supplierName_box);
		this.add(linkMan_Field);
		this.add(time_Field);
		this.add(add);
//		this.add(update);
		this.add(addInventory);
		scrollpane.getViewport().add(table);
		this.add(deleButton);
		this.add(scrollpane);
		this.setLayout(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new PurchaseManage();
	}
}

