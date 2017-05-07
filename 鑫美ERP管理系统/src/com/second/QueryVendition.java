package com.second;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.pojo.EmpInfo;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月27日 上午10:18:14
		 * TODO	实现销售登记
		 */
public class QueryVendition extends JDialog {
//	private String url = "jdbc:mysql://localhost:3306/erp";
//	private String user = "root";
//	private String password = "yourpassword";
//	
//	
//	private JLabel customerId = new JLabel("客户编号");
//	private JLabel portInfoId = new JLabel("花盆编号");
//	private JLabel employeeId = new JLabel("员工编号");
//	private JLabel countLabel = new JLabel("数量");
//	private JLabel priceLabel = new JLabel("单个售价");
//	private JLabel date = new JLabel("售出日期");
//	
//	private JTextField customerId_Field = new JTextField();
//	private JTextField portInfoId_Field= new JTextField();
//	private JComboBox<EmpInfo> employeeId_Field = new JComboBox<EmpInfo>();
//	private JTextField countLabel_Field = new JTextField();
//	private JTextField priceLabel_Field = new JTextField();
//	private JTextField date_Field = new JTextField();
//	
//	
//	
//	private JButton button  = new JButton("登记");
//	
//	
//	
//	public AddVenditionPanel() {
//		// TODO Auto-generated constructor stub
//		this.setTitle("销售登记");
//		this.setSize(500, 400);
//		this.setLayout(null);
//		this.setLocationRelativeTo(null);
//		try{
//			try(
//				Connection conn = DriverManager.getConnection(url, user, password);
//					){
//				String sql = "select eId, eName from employee where eStatus = \"1\"";
//				PreparedStatement ps = conn.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery();
//				while(rs.next()){
//					EmpInfo emp = new EmpInfo();
//					emp.setId(rs.getString(1));
//					emp.setName(rs.getString(2));
//					employeeId_Field.addItem(emp);
//				}
//			}
//			
//		}catch(Exception e1){
//			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
//			e1.printStackTrace();
//		}
//		
//		button.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				public void actionPerformed(ActionEvent e) {
//					// TODO Auto-generated method stub
//					String name = name_Field.getText();
//					String card = card_Field.getText();
//					String tel = tel_Field.getText();
//					String date = hiredate_Field.getText();
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					Date empHireDate = null;
//					try {
//						empHireDate = sdf.parse(date);
//					} catch (ParseException e2) {
//						// TODO Auto-generated catch block
//						e2.printStackTrace();
//					}
//					
//					//首先检查用户名和密码是否为空
//					if(name.equals("")){
//						JOptionPane.showConfirmDialog(null, "姓名不能为空", "错误", 
//								JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//					if(card.equals("")){
//						JOptionPane.showConfirmDialog(null, "身份证号码不能为空", "错误", 
//								JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//					if(tel.equals("")){
//						JOptionPane.showConfirmDialog(null, "联系方式不能为空", "错误", 
//								JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//					if(date.equals("")){
//						JOptionPane.showConfirmDialog(null, "入职日期不能为空", "错误", 
//								JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
//						return;
//					}
//					
//					//执行JDBC
//					try{
//						Class.forName("com.mysql.jdbc.Driver");
//						try(
//							Connection conn = DriverManager.getConnection(Url, User, Password);
//								){
//							String sql = "INSERT INTO employee(eName, eCard, ePhone, eHiredate, eStatus) VALUES(?, ?, ?, ?, ?)";
//							PreparedStatement ps = conn.prepareStatement(sql);
//							ps.setObject(1, name);
//							ps.setObject(2, card);
//							ps.setObject(3, tel);
//							ps.setObject(4, empHireDate);
//							ps.setObject(5, "1");
//							int n = ps.executeUpdate();
//							if(n <= 0){
//								JOptionPane.showMessageDialog(null, "添加失败，请稍后再试");
//								return;
//							}else{
//								JOptionPane.showMessageDialog(null, "添加成功");
//								name_Field.setText("");
//								card_Field.setText("");
//								tel_Field.setText("");
//								hiredate_Field.setText("");
//							}
//						}
//					}catch(Exception e1){
//						e1.printStackTrace();
//						JOptionPane.showConfirmDialog(null, "请检查身份证号码是否正确，身份证号码不允许重复", "错误", 
//								JOptionPane.YES_OPTION, JOptionPane.ERROR_MESSAGE);
//									}
//					}
//		});
//		
//		this.setVisible(true);
//	}
//	
//	public static void main(String[] args) {
//		
//	}
	
	
//	//设置scrollpane 顺便设置布局方式，这里先是设置垂直布局，然后设置水平布局
//			private JScrollPane scrollpane = new JScrollPane(
//					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//
//			//添加表格，同时设置表格内容不可编辑
//			private JTable table = new JTable(){
//				public boolean isCellEditable(int row, int column) {	
//					return false;
//				};
//			};
//			
//			private JButton update_Button = new JButton("修改");
//			private JButton del_Button = new JButton("删除");
//			
//			//数据库
//			private String url = "jdbc:mysql://localhost:3306/erp";
//			private String user = "root";
//			private String password = "yourpassword";
//			
//			Vector<String> header;
//			final Vector<Vector<String>> dataVector;
//			
//			public AddVenditionPanel() {
//				// TODO Auto-generated constructor stub
//				this.setTitle("销售查询");
//				this.setSize(700, 700);
//				this.setResizable(false);
//				this.setModal(false);
//				this.setLayout(null);
//				this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				this.setLocationRelativeTo(null);
//				scrollpane.setBounds(40, 20, 600, 600);
//				update_Button.setBounds(200, 630, 80, 20);
//				del_Button.setBounds(400, 630, 80, 20);
//				//表头
//				header = new Vector<String>();
//				header.add("销售表编号");
//				header.add("客户名称");
//				header.add("售出花盆编号");
//				header.add("销售人");
//				header.add("售出花盆数量");
//				header.add("花盆标价");
//				header.add("售出花盆单价");
//				header.add("售出花盆总价");
//				header.add("出货时间");
//				
//				//表格内容居中
//		        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
//		        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//		        table.setDefaultRenderer(Object.class, renderer);
//				
//				//存储一条一条的表数据
//				dataVector = new Vector<Vector<String>>();
//				
//				//查数据库，讲数据库中的数据上传到Vector集合中
//				try{
//					Class.forName("com.mysql.jdbc.Driver");
//					try(
//						Connection conn = DriverManager.getConnection(url, user, password);
//							){
//						String sql = "select v.vId, c.cName, pi.piId, e.eId, v.vCount, pi.piPrice, v.vPrice, (v.vCount * v.vPrice) as price,  "
//								+ "vDate from vendition v, customer c, portInfo pi, employee e where c.cId = v.cId and pi.piId = v.piId and e.eId = v.eId and v.vStatus = \"1\"";
//						PreparedStatement ps = conn.prepareStatement(sql);
//						ResultSet rs = ps.executeQuery();
//						String vId = null;
//						String cId = null;
//						String piId = null;
//						String eId = null;
//						String vCount = null;
//						String vPrice = null;
//						String price = null;
//						String date = null;
//						String allPrice = null;
//						while(rs.next())
//						{
//							vId = rs.getString(1);
//							cId = rs.getString(2);
//							piId = rs.getString(3);
//							eId = rs.getString(4);
//							vCount = rs.getString(5);
//							vPrice = rs.getString(6);
//							price = rs.getString(7);
//							allPrice = rs.getString(8);
//							date = rs.getString(9);
//							Vector<String> data = new Vector<String>();
//							data.add(vId);
//							data.add(cId);
//							data.add(piId);
//							data.add(eId);
//							data.add(vCount);
//							data.add(vPrice);
//							data.add(price);
//							data.add(allPrice);
//							data.add(date);
//							dataVector.add(data);
//						}
//					}
//				}catch(Exception e1){
//					e1.printStackTrace();
//				}
//				
//				
//				
//				
//				
//				final DefaultTableModel df = new DefaultTableModel(dataVector, header);
//				table.setModel(df);
//				scrollpane.getViewport().add(table);
//				//设置表头不可重新排序
//				table.getTableHeader().setReorderingAllowed(false);
//				FitTableColumns(table);
//				
//				//添加事件监听器：
//				del_Button.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						int row = table.getSelectedRow();	//选中第一行是0，没选中是-1
//						if(row == -1)
//						{
//							JOptionPane.showMessageDialog(null, "请选中先");
//							return;
//						}
//						String vId =(String) table.getValueAt(row, 0);
//						try{
//							Class.forName("com.mysql.jdbc.Driver");
//							try(
//								Connection conn = DriverManager.getConnection(url, user, password);
//									){
//								String sql = "update  vendition set vStatus = '0' where vId = ?";
//								PreparedStatement ps = conn.prepareStatement(sql);
//								ps.setObject(1, vId);
//								int n = ps.executeUpdate();
//								if(n > 0)
//									JOptionPane.showMessageDialog(null, "删除成功");
//								else{
//									JOptionPane.showMessageDialog(null, "删除失败");
//									return;
//								}
//								df.removeRow(row);
//								table.setModel(df);
//							}
//						}catch(Exception e1){
//							e1.printStackTrace();
//						}
//						
//					}
//				});
//					
//				/**
//				 * 修改的操作，需要弹出一个对话框进行修改
//				 */
//				update_Button.addActionListener(new ActionListener() {
//					
//					@Override
//					public void actionPerformed(ActionEvent e) {
//						// TODO Auto-generated method stub
//						int row = table.getSelectedRow();
//						if(row == -1)
//						{
//							JOptionPane.showMessageDialog(null, "请选中先");
//							return;
//						}
//						String empId = (String) table.getValueAt(row, 0);
//						String empName = (String) table.getValueAt(row, 1);
//						
////						new VenditionUpdate(table, empId, row, header);
//						
//					}
//				});
//				
//				this.add(scrollpane);
//				this.add(update_Button);
//				this.add(del_Button);
//				this.setVisible(true);
//			}
//			
//			public static void FitTableColumns(JTable myTable){
//				  JTableHeader header = myTable.getTableHeader();
//				     int rowCount = myTable.getRowCount();
//				     Enumeration columns = myTable.getColumnModel().getColumns();
//				     while(columns.hasMoreElements()){
//				         TableColumn column = (TableColumn)columns.nextElement();
//				         int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
//				         int width = (int)myTable.getTableHeader().getDefaultRenderer()
//				                 .getTableCellRendererComponent(myTable, column.getIdentifier()
//				                         , false, false, -1, col).getPreferredSize().getWidth();
//				         for(int row = 0; row<rowCount; row++){
//				             int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
//				               myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
//				             width = Math.max(width, preferedWidth);
//				         }
//				         header.setResizingColumn(column); // 此行很重要
//				         column.setWidth(width+myTable.getIntercellSpacing().width);
//				     }
//			}
//			
//			public static void main(String[] args) {
//				new AddVenditionPanel();
//			}
	
			private static final long serialVersionUID = -3619887890741475524L;
		    private JPanel contentPane;
		    private JTable table  = new JTable(){
				public boolean isCellEditable(int row, int column) {	
					return false;
				};
			};
		    private JTextField textField;
		    private TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();;
		    
		    Vector<String> header ;
			private String url = "jdbc:mysql://localhost:3306/erp";
			private String user = "root";
			private String password = "yourpassword";
			Vector<Vector<String>> dataVector;		//存放所有数据
			JTextField text;						//显示当前页数
			Vector<Vector<String>> data;			//存放所要显示的每一页的数据
			int n = 0;									//得到数据总数
		    /**
		     * Launch the application.
		     */
		    public static void main(String[] args) {
		        try {
		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		        } catch (Throwable e) {
		            e.printStackTrace();
		        }
		        EventQueue.invokeLater(new Runnable() {
		            public void run() {
		                try {
		                	QueryVendition frame = new QueryVendition();
		                    frame.setVisible(true);
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }
		        });
		    }
		    
		    /**
		     * Create the frame.
		     */
		    public QueryVendition() {
		       
		    	header = new Vector<String>(); 
				header.add("销售表编号");
				header.add("客户名称");
				header.add("花盆编号");
				header.add("花盆名称");
				header.add("销售人");
				header.add("售出花盆数量");
				header.add("花盆标价");
				header.add("售出花盆单价");
				header.add("售出花盆总价");
				header.add("出货时间");
				
				
				
				data = new Vector<Vector<String>>();
				dataVector = new Vector<Vector<String>>();
				text = new JTextField();					//显示当前页数
				try{
					text.setText("1");
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "select v.vId, c.cName, pi.piId,pi.piName, e.eName, v.vCount, pi.piPrice, v.vPrice, (v.vCount * v.vPrice) as price,  "
								+ "vDate from vendition v, customer c, portInfo pi, employee e where c.cId = v.cId and pi.piId = v.piId and e.eId = v.eId and v.vStatus = \"1\" order by vDate desc";
						PreparedStatement ps = conn.prepareStatement(sql);
						ResultSet rs = ps.executeQuery();
						String vId = null;
						String cId = null;
						String piId = null;
						String eName = null;
						String vCount = null;
						String vPrice = null;
						String price = null;
						String date = null;
						String allPrice = null;
						String name = null;
						while(rs.next())
						{
							n++;
							vId = rs.getString(1);
							cId = rs.getString(2);
							piId = rs.getString(3);
							name = rs.getString(4);
							eName = rs.getString(5);
							vCount = rs.getString(6);
							vPrice = rs.getString(7);
							price = rs.getString(8);
							allPrice = rs.getString(9);
							date = rs.getString(10);
							Vector<String> data1 = new Vector<String>();
							data1.add(vId);
							data1.add(cId);
							data1.add(piId);
							data1.add(name);
							data1.add(eName);
							data1.add(vCount);
							data1.add(vPrice);
							data1.add(price);
							data1.add(allPrice);
							data1.add(date);
							dataVector.add(data1);
						}
					}
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
					e1.printStackTrace();
				}
				
				if(n / 15 >= 1){
					for (int i = 0; i < 15; i++) {
						data.add(dataVector.get(i));
					}
				}else{
					for (int i = 0; i < n % 15; i++) {
						data.add(dataVector.get(i));
					}
					
				}
				
				final DefaultTableModel model = new DefaultTableModel(data,header);
				table.setModel(model);
				sorter.setModel(model);
		        table.setRowSorter(sorter);
		        
		        
				textField = new JTextField();
		        textField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
				textField.addKeyListener(new KeyAdapter() {
					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						sorter.setRowFilter(RowFilter.regexFilter(textField.getText()));
					}
				});
				
				
				
		       
		        setTitle("销售查询");
		        this.setSize(1100, 650);
		        this.setLocationRelativeTo(null);
		        contentPane = new JPanel();
		        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		        contentPane.setLayout(new BorderLayout(0, 0));
		        setContentPane(contentPane);
		        
		        JPanel panel = new JPanel();
		        contentPane.add(panel, BorderLayout.NORTH);
		        
		        JLabel label = new JLabel("\u5173\u952E\u5B57\uFF1A");
		        label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		        panel.add(label);
		        
		        
		        panel.add(textField);
		        textField.setColumns(20);
		        
		        JPanel buttonPanel = new JPanel();
		        contentPane.add(buttonPanel, BorderLayout.SOUTH);
		        
		        JButton button = new JButton("修改");
		        JButton first = new JButton("首页");
		        JButton previous = new JButton("上一页");
		        JButton next = new JButton("下一页");
		        JButton last = new JButton("尾页");
		        JLabel page = new JLabel("当前页数:");
		        
		        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		        
		        first.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						text.setText("1");
						data.removeAllElements();
						if(n / 15 >= 1){
							for (int i = 0; i < 15; i++) {
								data.add(dataVector.get(i));
							}
						}else{
							for (int i = 0; i < n%15; i++) {
								data.add(dataVector.get(i));
							}
						}
		        		DefaultTableModel model = new DefaultTableModel(data,header);
		        		table.setModel(model);
					}
				});
		        
		        previous.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						String page = text.getText();
						if("1".equals(page)){
							JOptionPane.showMessageDialog(null, "对不起，现在已经是首页了");
							return;
						}
						int k = Integer.parseInt(page);
						text.setText(k - 1 + "");
						data.removeAllElements();
						for (int i = (k - 2) * 15; i < (k - 2) * 5 + 15; i++) {
							data.add(dataVector.get(i));
						}
		        		DefaultTableModel model = new DefaultTableModel(data,header);
		        		table.setModel(model);
					}
				});
		        
		        next.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int page = Integer.parseInt(text.getText());
						int k = n % 15;
						if(k == 0){
							if(page == n / 15){
								JOptionPane.showMessageDialog(null, "已经是最后一页了");
								return;
							}
						}else{
							if(page == n / 15 + 1)
							{
								JOptionPane.showMessageDialog(null, "已经是最后一页了");
								return;
							}
							if(!(page == n / 15 )){
								k = 15;
							}
							text.setText(page + 1 + "");
						}
						data.removeAllElements();
						for (int i = page * 15; i < page * 15 +  k; i++) {
							data.add(dataVector.get(i));
						}
						DefaultTableModel model = new DefaultTableModel(data,header);
		        		table.setModel(model);
					}
				});
		        
		        last.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int k = n % 15;
						data.removeAllElements();
						for(int i = n - k ; i <= n - 1; i++){
							data.add(dataVector.get(i));
						}
						DefaultTableModel model = new DefaultTableModel(data,header);
		        		table.setModel(model);
					}
				});
		        
		        text.addKeyListener(new KeyAdapter() {
		        	@Override
		        	public void keyReleased(KeyEvent e) {
		        		// TODO Auto-generated method stub
		        		int k = n % 15;
		        		int page = 0;
		        		try{
		        			 page = Integer.parseInt(text.getText());
		        		}catch(Exception e1){
		        			JOptionPane.showMessageDialog(null, "请注意页数格式");
		        			e1.printStackTrace();
		        			return;
		        		}
		        		if(page < 1){
		        			JOptionPane.showMessageDialog(null, "页数非法");
		        			return;
		        		}
		        		if(k == 0){
		        			if( page > n/15){
		        				JOptionPane.showMessageDialog(null, "页数非法");
		        				return;
		        			}
		        			k = 15;
		        		}else{
		        			if( page > n/15 + 1){
		        				JOptionPane.showMessageDialog(null, "页数非法");
		        				return;
		        			}
		        			if( page < n/15 + 1){
		        				k = 15;
		        			}
		        			
		        		}
						data.removeAllElements();
						for(int i = (page - 1) * 15 ; i < (page - 1) * 15 + k; i++){
							data.add(dataVector.get(i));
						}
						DefaultTableModel model = new DefaultTableModel(data,header);
		        		table.setModel(model);
		        	}
				});
		        first.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		        previous.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		        next.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		        last.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		        page.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		        text.setColumns(5);
		        
		        
		        JButton add = new JButton("添加");
		        JButton delete = new JButton("删除");
		        JButton update = new JButton("修改");
		        buttonPanel.add(first);
		        buttonPanel.add(previous);
		        buttonPanel.add(page);
		        buttonPanel.add(text);
		        buttonPanel.add(next);
		        buttonPanel.add(last);
		        buttonPanel.add(add);
		        buttonPanel.add(update);
		        buttonPanel.add(delete);
		        JScrollPane scrollPane = new JScrollPane();
		        contentPane.add(scrollPane, BorderLayout.CENTER);
		        
		        add.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						new AddVenditionDialog(QueryVendition.this);
						
					}
				});
		    	
		        
		        update.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int raw = table.getSelectedRow();
						String portId = (String) table.getValueAt(raw, 2);
						new UpdateVendition(portId, table, data, header, raw, QueryVendition.this);
					}
				});
		        
		        delete.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						int row = table.getSelectedRow();	//选中第一行是0，没选中是-1
						if(row == -1)
						{
							JOptionPane.showMessageDialog(null, "请选中先");
							return;
						}
						String vId =(String) table.getValueAt(row, 0);
						try{
							Class.forName("com.mysql.jdbc.Driver");
							try(
								Connection conn = DriverManager.getConnection(url, user, password);
									){
								String sql = "update  vendition set vStatus = '0' where vId = ?";
								PreparedStatement ps = conn.prepareStatement(sql);
								ps.setObject(1, vId);
								int n = ps.executeUpdate();
								if(n > 0){
									JOptionPane.showMessageDialog(null, "删除成功");
									QueryVendition.this.dispose();
									new QueryVendition();
								}else{
									JOptionPane.showMessageDialog(null, "删除失败");
									return;
								}
								model.removeRow(row);
								table.setModel(model);
							}
						}catch(Exception e1){
							e1.printStackTrace();
						}
					}
				});
		    	table.getTableHeader().setReorderingAllowed(false);
		        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		        table.setRowHeight(30);
		        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
		        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		        table.setDefaultRenderer(Object.class, renderer);
		        
		        JTableHeader header1 = table.getTableHeader();
		        header1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		        header1.setPreferredSize(new Dimension(header1.getWidth(), 35));
		        scrollPane.setViewportView(table);
		        this.setVisible(true);
		    }
		    
		   
}