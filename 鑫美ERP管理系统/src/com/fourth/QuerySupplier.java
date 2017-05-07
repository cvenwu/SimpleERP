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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
		/**
		 * 
		 * @author SiVan
		 * @time 2017年4月26日 下午10:23:25
		 * TODO	查询供应商
		 */
public class QuerySupplier extends JDialog{
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
                	QuerySupplier frame = new QuerySupplier();
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
    public QuerySupplier() {
       
    	header = new Vector<String>(); 
		header.add("编号");
		header.add("名称");
		header.add("联系人");
		header.add("地址");
		header.add("联系方式");
		header.add("银行账号");
		header.add("微信账号");
		data = new Vector<Vector<String>>();
		dataVector = new Vector<Vector<String>>();
		text = new JTextField();					//显示当前页数
		try{
			text.setText("1");
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select sId, sName, sLinkman, sAddress, sPhone, sBankAc, sWeChat from supplier where sStatus =  \"1\" ";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				while(rs.next()){
					n++;
					Vector<String> vector = new Vector<String>();
					vector.add(rs.getString(1));
					vector.add(rs.getString(2));
					vector.add(rs.getString(3));
					vector.add(rs.getString(4));
					vector.add(rs.getString(5));
					vector.add(rs.getString(6));
					vector.add(rs.getString(7));
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
        
        
       
        setTitle("供应商查询");
        this.setSize(900, 650);
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
        
        JButton first = new JButton("首页");
        JButton previous = new JButton("上一页");
        JButton next = new JButton("下一页");
        JButton last = new JButton("尾页");
        JLabel page = new JLabel("当前页数:");
        JButton button = new JButton("修改");
        JButton delete = new JButton("删除");
        
        
        
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
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(1).setPreferredWidth(170);
                table.getColumnModel().getColumn(2).setPreferredWidth(90);
                table.getColumnModel().getColumn(3).setPreferredWidth(170);
                table.getColumnModel().getColumn(4).setPreferredWidth(120);
                table.getColumnModel().getColumn(5).setPreferredWidth(160);
                table.getColumnModel().getColumn(6).setPreferredWidth(140);
        		
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
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(1).setPreferredWidth(170);
                table.getColumnModel().getColumn(2).setPreferredWidth(90);
                table.getColumnModel().getColumn(3).setPreferredWidth(170);
                table.getColumnModel().getColumn(4).setPreferredWidth(120);
                table.getColumnModel().getColumn(5).setPreferredWidth(160);
                table.getColumnModel().getColumn(6).setPreferredWidth(140);
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
				System.out.println(k);
				data.removeAllElements();
				for (int i = page * 15; i < page * 15 +  k; i++) {
					data.add(dataVector.get(i));
				}
				DefaultTableModel model = new DefaultTableModel(data,header);
        		table.setModel(model);
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(1).setPreferredWidth(170);
                table.getColumnModel().getColumn(2).setPreferredWidth(90);
                table.getColumnModel().getColumn(3).setPreferredWidth(170);
                table.getColumnModel().getColumn(4).setPreferredWidth(120);
                table.getColumnModel().getColumn(5).setPreferredWidth(160);
                table.getColumnModel().getColumn(6).setPreferredWidth(140);
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
                table.getColumnModel().getColumn(0).setPreferredWidth(50);
                table.getColumnModel().getColumn(1).setPreferredWidth(170);
                table.getColumnModel().getColumn(2).setPreferredWidth(90);
                table.getColumnModel().getColumn(3).setPreferredWidth(170);
                table.getColumnModel().getColumn(4).setPreferredWidth(120);
                table.getColumnModel().getColumn(5).setPreferredWidth(160);
                table.getColumnModel().getColumn(6).setPreferredWidth(140);
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
        JScrollPane scrollPane = new JScrollPane();
        contentPane.add(scrollPane, BorderLayout.CENTER);
        
        
        
        
        
    	
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
        
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(90);
        table.getColumnModel().getColumn(3).setPreferredWidth(170);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(160);
        table.getColumnModel().getColumn(6).setPreferredWidth(140);
        this.setVisible(true);
    }
    
    
   
    
}

