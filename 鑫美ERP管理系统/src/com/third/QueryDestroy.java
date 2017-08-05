package com.third;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.fourth.QueryCustomerZone;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月24日 下午10:15:12
		 * TODO	货损查询，并在此基础上进行修改
		 */
public class QueryDestroy extends JDialog{
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
	private String password = "1018222wxw";
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
                	QueryDestroy frame = new QueryDestroy();
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
    public QueryDestroy() {
       
    	header = new Vector<String>(); 
		header.add("货损编号");
		header.add("花盆编号");
		header.add("花盆种类");
		header.add("花盆名称");
		header.add("损坏时节");
		header.add("损坏时间");
		header.add("处理状态");
		data = new Vector<Vector<String>>();
		dataVector = new Vector<Vector<String>>();
		text = new JTextField();					//显示当前页数
		try{
			text.setText("1");
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select d.dId, d.piId, pc.pcClassify, p.piName, d.dWay, d.dTime, d.dStatus from destroy d,portinfo p,portClassify pc where d.piId = p.piId and p.pcId = pc.pcId order by dTime desc";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					n++;
					Vector<String> vector = new Vector<String>();
					vector.add(rs.getString(1));
					vector.add(rs.getString(2));
					vector.add(rs.getString(3));
					vector.add(rs.getString(4));
					vector.add("0".equals(rs.getString(5)) ? "出货" : "进货");
					vector.add(rs.getString(6));
					
					if("0".equals(rs.getString(7))){
						vector.add("待处理");
					}else if("1".equals(rs.getString(7))){
						vector.add("已修复");
					}else{
						vector.add("废弃");
					}
					dataVector.add(vector);
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
			for (int i = 0; i < n%15; i++) {
				data.add(dataVector.get(i));
			}
		}
		
		//设置表头内容居中
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		
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
        
        
       
        setTitle("区域查询");
        this.setSize(800, 650);
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
        JButton update = new JButton("修改");
        
        
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
					for (int i = 0; i < n % 15; i++) {
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
        buttonPanel.add(first);
        buttonPanel.add(previous);
        buttonPanel.add(page);
        buttonPanel.add(text);
        buttonPanel.add(next);
        buttonPanel.add(last);
        buttonPanel.add(update);
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if(row < 0){
					JOptionPane.showMessageDialog(null, "请选中先");
					return;
				}
            	String calssify = (String) table.getValueAt(row, 2);
            	String name = (String) table.getValueAt(row, 3);
            	String portId = (String) table.getValueAt(row, 1);
            	new UpdateDestroy(table, row, header, calssify, name, portId);
			}
		});
    	
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
        
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    
   
    
}
//     * 
//     */
//    private static final long serialVersionUID = -3619887890741475524L;
//    private JPanel contentPane;
//    private JTable table;
//    private JTextField textField;
//    private TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();;
//    
//    Vector<String> header ;
//	private String url = "jdbc:mysql://localhost:3306/erp";
//	private String user = "root";
//	private String password = "yourpassword";
//    /**
//     * Launch the application.
//     */
//    public static void main(String[] args) {
//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                	QueryDestroy frame = new QueryDestroy();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//    
//    /**
//     * Create the frame.
//     */
//    public QueryDestroy() {
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowActivated(WindowEvent e) {
//            	header = new Vector<String>(); 
//        		header.add("货损编号");
//        		header.add("花盆编号");
//        		header.add("花盆种类");
//        		header.add("花盆名称");
//        		header.add("损坏时节");
//        		header.add("损坏时间");
//        		header.add("处理状态");
//        		
//        		
//        		Vector<Vector<String>> dataVector = new Vector<Vector<String>>();
//        		
//        		try{
//        			try(
//        				Connection conn = DriverManager.getConnection(url, user, password);
//        					){
//        				String sql = "select d.dId, d.piId, pc.pcClassify, p.piName, d.dWay, d.dTime, d.dStatus from destroy d,portinfo p,portClassify pc where d.piId = p.piId and p.pcId = pc.pcId";
//        				PreparedStatement ps = conn.prepareStatement(sql);
//        				ResultSet rs = ps.executeQuery();
//        				while(rs.next()){
//        					Vector<String> vector = new Vector<String>();
//        					vector.add(rs.getString(1));
//        					vector.add(rs.getString(2));
//        					vector.add(rs.getString(3));
//        					vector.add(rs.getString(4));
//        					vector.add("0".equals(rs.getString(5)) ? "出货" : "进货");
//        					vector.add(rs.getString(6));
//        					
//        					if("0".equals(rs.getString(7))){
//        						vector.add("待处理");
//        					}else if("1".equals(rs.getString(7))){
//        						vector.add("已修复");
//        					}else{
//        						vector.add("废弃");
//        					}
//        					
//        					dataVector.add(vector);
//        				}
//        			}
//        		}catch(Exception e1){
//        			JOptionPane.showMessageDialog(null, "请检查数据库服务是否开启");
//        			e1.printStackTrace();
//        		}
//        		DefaultTableModel model = new DefaultTableModel(dataVector,header);
//        		table.setModel(model);
//        		textField.addKeyListener(new KeyAdapter() {
//        			@Override
//        			public void keyReleased(KeyEvent e) {
//        				// TODO Auto-generated method stub
//        				sorter.setRowFilter(RowFilter.regexFilter(textField.getText()));
//        			}
//        		});
//        		sorter.setModel(model);
//                table.setRowSorter(sorter);
//            }
//        });
//        setTitle("更新货损");
//        this.setSize(800, 600);
//        this.setLocationRelativeTo(null);
//        contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPane.setLayout(new BorderLayout(0, 0));
//        setContentPane(contentPane);
//        
//        JPanel panel = new JPanel();
//        contentPane.add(panel, BorderLayout.NORTH);
//        
//        JLabel label = new JLabel("\u5173\u952E\u5B57\uFF1A");
//        label.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//        panel.add(label);
//        
//        textField = new JTextField();
//        textField.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//        panel.add(textField);
//        textField.setColumns(20);
//        
//        JPanel buttonPanel = new JPanel();
//        contentPane.add(buttonPanel, BorderLayout.SOUTH);
//        
//        JButton button = new JButton("修改");
//        button.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//            	int row = table.getSelectedRow();
//            	String calssify = (String) table.getValueAt(row, 2);
//            	String name = (String) table.getValueAt(row, 3);
//            	String portId = (String) table.getValueAt(row, 1);
//            	new UpdateDestroy(table, row, header, calssify, name, portId);
//            }
//        });
//        
//        button.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//        buttonPanel.add(button);
//        
//        JScrollPane scrollPane = new JScrollPane();
//        contentPane.add(scrollPane, BorderLayout.CENTER);
//        
//        table = new JTable(){
//    		public boolean isCellEditable(int row, int column) {	
//    			return false;
//    		};
//    	};
//    	
//    	table.getTableHeader().setReorderingAllowed(false);
//        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
//        table.setRowHeight(30);
//        DefaultTableCellRenderer renderer=new DefaultTableCellRenderer();
//        renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
//        table.setDefaultRenderer(Object.class, renderer);
//        
//        JTableHeader header = table.getTableHeader();
//        header.setFont(new Font("微软雅黑", Font.PLAIN, 16));
//        header.setPreferredSize(new Dimension(header.getWidth(), 35));
//        scrollPane.setViewportView(table);
//        
//        this.setVisible(true);
//    }
//    
//    
   
    
