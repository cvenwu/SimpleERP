package com.fourth;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import com.plugin.Chooser;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月17日 下午8:07:35
		 * TODO	查询货物所属厂家
		 */
public class QueryPortBelongs extends JDialog{

	private JLabel port_Label = new JLabel("货物编号");
	private JTextField port_Field = new JTextField();
	
	
	private JScrollPane scrollpane = new JScrollPane(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	//添加表格，同时设置表格内容不可编辑
	private JTable table = new JTable(){
		public boolean isCellEditable(int row, int column) {	
			return false;
		};
	};
	
	private JButton query_Button = new JButton("查询");
	
	//数据库
	private String url = "jdbc:mysql://localhost:3306/erp";
	private String user = "root";
	private String password = "yourpassword";
	
	Vector<String> header;
	final Vector<Vector<String>> dataVector;
	
	public QueryPortBelongs() {
		// TODO Auto-generated constructor stub
		this.setTitle("货物所属查询");
		this.setSize(1000, 500);
		this.setResizable(false);
		this.setModal(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		scrollpane.setBounds(25, 50, 950, 400);
		port_Label.setBounds(60, 20, 80, 30);
		port_Field.setBounds(130, 20, 150, 30);
		query_Button.setBounds(310, 20, 80, 30);
		
		//表头
		header = new Vector<String>();
		header.add("花盆编号");
		header.add("花盆名称");
		header.add("花盆所属供应商");
		header.add("供应商联系人");
		header.add("地址");
		header.add("电话");
		header.add("银行账号");
		header.add("供应商微信号");
		//表格内容居中
        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        
		//存储一条一条的表数据
		dataVector = new Vector<Vector<String>>();
		
		query_Button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//查数据库，讲数据库中的数据上传到Vector集合中
				
				try{
					Class.forName("com.mysql.jdbc.Driver");
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						//where v.vDate = ? and pi.piId = v.piId and pi.pCID = pc.pcId
						String sql = "select p.piId,p.piName, s.sName, s.sLinkman, s.sAddress, s.sPhone, s.sBankAc,"
								+ "s.sWeChat from portInfo p, supplier s where p.sId = s.sId and p.piId = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, port_Field.getText());
						ResultSet rs = ps.executeQuery();
						String piId = null;
						String name = null;
						String sName = null;
						String sLinkman = null;
						String sAddress = null;
						String sPhone = null;
						String sBankAc = null;
						String sWeChat = null;
						//这里使用了个if判断，如果选择日期没有出现有销售记录的情况
						if(!rs.next()){
							dataVector.removeAllElements();
							DefaultTableModel df = new DefaultTableModel(dataVector, header);
							table.setModel(df);
							JOptionPane.showMessageDialog(null, "很抱歉的通知您，没有找到该编号对应厂家的记录");
							return;
						}else{
							piId = rs.getString(1);
							name = rs.getString(2);
							sName = rs.getString(3);
							sLinkman = rs.getString(4);
							sAddress = rs.getString(5);
							sPhone = rs.getString(6);
							sBankAc = rs.getString(7);
							sWeChat = rs.getString(8);
							Vector<String> data = new Vector<String>();
							data.add(piId);
							data.add(name);
							data.add(sName);
							data.add(sLinkman);
							data.add(sAddress);
							data.add(sPhone);
							data.add(sBankAc);
							data.add(sWeChat);
							dataVector.add(data);
							DefaultTableModel df = new DefaultTableModel(dataVector, header);
							table.setModel(df);
						}
						while(rs.next()){
							piId = rs.getString(1);
							name = rs.getString(2);
							sName = rs.getString(3);
							sLinkman = rs.getString(4);
							sAddress = rs.getString(5);
							sPhone = rs.getString(6);
							sBankAc = rs.getString(7);
							sWeChat = rs.getString(8);
							Vector<String> data = new Vector<String>();
							data.add(piId);
							data.add(name);
							data.add(sName);
							data.add(sLinkman);
							data.add(sAddress);
							data.add(sPhone);
							data.add(sBankAc);
							data.add(sWeChat);
							dataVector.add(data);
							DefaultTableModel df = new DefaultTableModel(dataVector, header);
							table.setModel(df);
						}
					}
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		
		table.setRowHeight(30);
		//设置表头不可重新排序
		table.getTableHeader().setReorderingAllowed(false);
		FitTableColumns(table);
		scrollpane.getViewport().add(table);
		this.add(port_Field);
		this.add(port_Label);
		this.add(query_Button);
		this.add(scrollpane);
		this.setVisible(true);
	}
	
	public static void FitTableColumns(JTable myTable){
		  JTableHeader header = myTable.getTableHeader();
		     int rowCount = myTable.getRowCount();
		     Enumeration columns = myTable.getColumnModel().getColumns();
		     while(columns.hasMoreElements()){
		         TableColumn column = (TableColumn)columns.nextElement();
		         int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
		         int width = (int)myTable.getTableHeader().getDefaultRenderer()
		                 .getTableCellRendererComponent(myTable, column.getIdentifier()
		                         , false, false, -1, col).getPreferredSize().getWidth();
		         for(int row = 0; row<rowCount; row++){
		             int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
		               myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
		             width = Math.max(width, preferedWidth);
		         }
		         header.setResizingColumn(column); // 此行很重要
		         column.setWidth(width+myTable.getIntercellSpacing().width);
		     }
	}
	
	public static void main(String[] args) {
		new QueryPortBelongs();
	}
}
