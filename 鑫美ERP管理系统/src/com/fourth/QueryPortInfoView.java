package com.fourth;

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
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
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

			/**
			 * 
			 * @author SiVan
			 * @time 2017年4月17日 上午11:53:37
			 * TODO	花盆详情查询
			 */
public class QueryPortInfoView extends JDialog{

	//设置scrollpane 顺便设置布局方式，这里先是设置垂直布局，然后设置水平布局
//	private JScrollPane scrollpane = new JScrollPane(
//			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
//
//	//添加表格，同时设置表格内容不可编辑
//	private JTable table = new JTable(){
//		public boolean isCellEditable(int row, int column) {	
//			return false;
//		};
//	};
//	
//	private JButton update_Button = new JButton("修改");
//	private JButton del_Button = new JButton("删除");
//	
//	//数据库
//	private String url = "jdbc:mysql://localhost:3306/erp";
//	private String user = "root";
//	private String password = "yourpassword";
//	
//	Vector<String> header;
//	final Vector<Vector<String>> dataVector;
//	
//	public QueryPortInfoView() {
//		// TODO Auto-generated constructor stub
//		this.setTitle("花盆详情查询");
//		this.setSize(800, 700);
//		this.setResizable(false);
//		this.setModal(false);
//		this.setLayout(null);
//		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//		this.setLocationRelativeTo(null);
//		scrollpane.setBounds(50, 50, 700, 500);
//		update_Button.setBounds(200, 600, 80, 20);
//		del_Button.setBounds(400, 600, 80, 20);
//		//表头
//		header = new Vector<String>();
//		header.add("花盆编号");
//		header.add("花盆类别");
//		header.add("供应商");
//		header.add("花盆名称");
//		header.add("花盆价格");
//		header.add("花盆产地");
//		header.add("花盆库存数量");
//		header.add("花盆状态");
//		
//		//表格内容居中
//        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
//        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//        table.setDefaultRenderer(Object.class, renderer);
//		
//        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
//		//存储一条一条的表数据
//		dataVector = new Vector<Vector<String>>();
//		
//		//查数据库，讲数据库中的数据上传到Vector集合中
//		try{
//			Class.forName("com.mysql.jdbc.Driver");
//			try(
//				Connection conn = DriverManager.getConnection(url, user, password);
//					){
//				String sql = "select p.piId, pc.pcClassify, s.sName, p.piName, p.piPrice, p.piMaking, p.piNumber, p.piStatus from "
//						+ "portinfo p, portClassify pc, supplier s where p.pcId = pc.pcId and p.sId = s.sId and  p.piStatus = \"1\"";
//				PreparedStatement ps = conn.prepareStatement(sql);
//				ResultSet rs = ps.executeQuery();
//				String piId = null;
//				String pcId = null;
//				String sId = null;
//				String piName = null;
//				String piPrice = null;
//				String piMaking = null;
//				String piNumber = null;
//				String piStatus = null;
//				while(rs.next())
//				{
//					piId = rs.getString(1);
//					pcId = rs.getString(2);
//					sId = rs.getString(3);
//					piName = rs.getString(4);
//					piPrice = rs.getString(5);
//					piMaking = rs.getString(6);
//					piNumber = rs.getString(7);
//					piStatus = rs.getString(8);
//					Vector<String> data = new Vector<String>();
//					data.add(piId);
//					data.add(pcId);
//					data.add(sId);
//					data.add(piName);
//					data.add(piPrice);
//					data.add(piMaking);
//					data.add(piNumber);
//					if("1".equals(piStatus))
//					{
//						data.add("在售");
//					}else{
//						data.add("下架");
//					}
//					dataVector.add(data);
//				}
//			}
//		}catch(Exception e1){
//			e1.printStackTrace();
//		}
//		
//		
//		
//		
//		final DefaultTableModel df = new DefaultTableModel(dataVector, header);
//		table.setModel(df);
//		scrollpane.getViewport().add(table);
//		//设置表头不可重新排序
//		table.getTableHeader().setReorderingAllowed(false);
//		FitTableColumns(table);
//		
//		//添加事件监听器：
////		del_Button.addActionListener(new ActionListener() {
////			
////			@Override
////			public void actionPerformed(ActionEvent e) {
////				// TODO Auto-generated method stub
////				int row = table.getSelectedRow();	//选中第一行是0，没选中是-1
////				if(row == -1)
////				{
////					JOptionPane.showMessageDialog(null, "请选中先");
////					return;
////				}
////				String piId =(String) table.getValueAt(row, 0);
////				try{
////					Class.forName("com.mysql.jdbc.Driver");
////					try(
////						Connection conn = DriverManager.getConnection(url, user, password);
////							){
////						String sql = "update  portinfo set piStatus = \"0\" where piId = ?";
////						PreparedStatement ps = conn.prepareStatement(sql);
////						ps.setObject(1, piId);
////						int n = ps.executeUpdate();
////						if(n > 0)
////							JOptionPane.showMessageDialog(null, "删除成功");
////						else{
////							JOptionPane.showMessageDialog(null, "删除失败");
////							return;
////						}
////						df.removeRow(row);
////						table.setModel(df);
////					}
////				}catch(Exception e1){
////					e1.printStackTrace();
////				}
////				
////			}
////		});
//			
//		/**
//		 * 修改的操作，需要弹出一个对话框进行修改
//		 */
////		update_Button.addActionListener(new ActionListener() {
////			
////			@Override
////			public void actionPerformed(ActionEvent e) {
////				// TODO Auto-generated method stub
////				int row = table.getSelectedRow();
////				if(row == -1)
////				{
////					JOptionPane.showMessageDialog(null, "请选中先");
////					return;
////				}
////				String piId = (String) table.getValueAt(row, 0);
////				
////				new PortInfoUpdate(table, piId, row, header);
////				
////			}
////		});
//		
//		this.add(scrollpane);
////		this.add(update_Button);
////		this.add(del_Button);
//		this.setVisible(true);
//	}
//	
//	public static void FitTableColumns(JTable myTable){
//		  JTableHeader header = myTable.getTableHeader();
//		     int rowCount = myTable.getRowCount();
//		     Enumeration columns = myTable.getColumnModel().getColumns();
//		     while(columns.hasMoreElements()){
//		         TableColumn column = (TableColumn)columns.nextElement();
//		         int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
//		         int width = (int)myTable.getTableHeader().getDefaultRenderer()
//		                 .getTableCellRendererComponent(myTable, column.getIdentifier()
//		                         , false, false, -1, col).getPreferredSize().getWidth();
//		         for(int row = 0; row<rowCount; row++){
//		             int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
//		               myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
//		             width = Math.max(width, preferedWidth);
//		         }
//		         header.setResizingColumn(column); // 此行很重要
//		         column.setWidth(width+myTable.getIntercellSpacing().width);
//		     }
//	}
//	
//		public static void main(String[] args) {
//			new QueryPortInfoView();
//		}
//	
//}
	
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
                	QueryPortInfoView frame = new QueryPortInfoView();
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
    public QueryPortInfoView() {
       
    	header = new Vector<String>(); 
    	header.add("花盆编号");
		header.add("花盆类别");
		header.add("供应商");
		header.add("花盆名称");
		header.add("花盆价格");
		header.add("花盆产地");
		header.add("花盆库存数量");
		header.add("花盆状态");
		data = new Vector<Vector<String>>();
		dataVector = new Vector<Vector<String>>();
		text = new JTextField();					//显示当前页数
		try{
			text.setText("1");
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select p.piId, pc.pcClassify, s.sName, p.piName, p.piPrice, p.piMaking, p.piNumber, p.piStatus from "
						+ "portinfo p, portClassify pc, supplier s where p.pcId = pc.pcId and p.sId = s.sId and  p.piStatus = \"1\"";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				String piId = null;
				String pcId = null;
				String sId = null;
				String piName = null;
				String piPrice = null;
				String piMaking = null;
				String piNumber = null;
				String piStatus = null;
				while(rs.next())
				{
					n++;
					piId = rs.getString(1);
					pcId = rs.getString(2);
					sId = rs.getString(3);
					piName = rs.getString(4);
					piPrice = rs.getString(5);
					piMaking = rs.getString(6);
					piNumber = rs.getString(7);
					piStatus = rs.getString(8);
					Vector<String> data1 = new Vector<String>();
					data1.add(piId);
					data1.add(pcId);
					data1.add(sId);
					data1.add(piName);
					data1.add(piPrice);
					data1.add(piMaking);
					data1.add(piNumber);
					if("1".equals(piStatus))
					{
						data1.add("在售");
					}else{
						data1.add("下架");
					}
					dataVector.add(data1);
				}
			}
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
			e1.printStackTrace();
		}
		
		System.out.println(n);
		if(n / 15 >= 1){
			for (int i = 0; i < 15; i++) {
				data.add(dataVector.get(i));
			}
			System.out.println("q");
		}else{
			for (int i = 0; i < n % 15; i++) {
				data.add(dataVector.get(i));
			}
			System.out.println("s");
		}
		
		System.out.println(data.isEmpty());
		DefaultTableModel model = new DefaultTableModel(data,header);
		table.setModel(model);
		
		textField = new JTextField();
        textField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				sorter.setRowFilter(RowFilter.regexFilter(textField.getText()));
			}
		});
		
		
		sorter.setModel(model);
        table.setRowSorter(sorter);
        
        
       
        setTitle("商品信息");
        this.setSize(920, 650);
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
        	       table.getColumnModel().getColumn(0).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        	        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(5).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(7).setPreferredWidth(110);
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
        	       table.getColumnModel().getColumn(0).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        	        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(5).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(7).setPreferredWidth(110);
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
        	       table.getColumnModel().getColumn(0).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        	        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(5).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(7).setPreferredWidth(110);
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
        	       table.getColumnModel().getColumn(0).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        	        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(5).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(7).setPreferredWidth(110);
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
        	       table.getColumnModel().getColumn(0).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        	        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        	        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        	        table.getColumnModel().getColumn(5).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        	        table.getColumnModel().getColumn(7).setPreferredWidth(110);
        	}
		});
        first.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        previous.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        next.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        last.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        page.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        text.setColumns(5);
        buttonPanel.add(first);
        buttonPanel.add(previous);
        buttonPanel.add(page);
        buttonPanel.add(text);
        buttonPanel.add(next);
        buttonPanel.add(last);
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(90);
        table.getColumnModel().getColumn(1).setPreferredWidth(90);
        table.getColumnModel().getColumn(2).setPreferredWidth(170);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(110);
        table.getColumnModel().getColumn(6).setPreferredWidth(110);
        table.getColumnModel().getColumn(7).setPreferredWidth(110);
    	
    	table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(30);
        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        table.setDefaultRenderer(Object.class, renderer);
        
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        scrollPane.setViewportView(table);
        this.setVisible(true);
    }
    
   
    
}

