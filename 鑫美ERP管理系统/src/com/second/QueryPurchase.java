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
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

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
public class QueryPurchase extends JDialog {
	
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
                	QueryPurchase frame = new QueryPurchase();
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
    public QueryPurchase() {
       
    	header = new Vector<String>(); 
		header.add("进货编号");
		header.add("供应商名称");
		header.add("花盆编号");
		header.add("花盆名称");
		header.add("联系人");
		header.add("数量");
		header.add("单价");
		header.add("总价");
		header.add("进货时间");
		
		data = new Vector<Vector<String>>();
		dataVector = new Vector<Vector<String>>();
		text = new JTextField();					//显示当前页数
		try{
			text.setText("1");
			try(
				Connection conn = DriverManager.getConnection(url, user, password);
					){
				String sql = "select p.pId, s.sName,p.piId,pi.piName, s.sLinkman, p.pCount, p.pPrice, (p.pCount * p.pPrice) "
						+ "as price,  p.pDate from purchase p, supplier s,portInfo pi where p.piId = pi.piId and p.pStatus = \"1\" and pi.sId = s.sId order by pDate desc";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				String pId = null;
				String supplierName = null;
				String piId = null;
				String piName = null;
				String pCount = null;
				String pPrice = null;
				String price = null;
				String pDate = null;
				String name = null;
				while(rs.next())
				{
					n++;
					pId = rs.getString(1);
					supplierName = rs.getString(2);
					piId = rs.getString(3);
					name = rs.getString(4);
					piName = rs.getString(5);
					pCount = rs.getString(6);
					pPrice = rs.getString(7);
					price = rs.getString(8);
					pDate = rs.getString(9);
					Vector<String> data = new Vector<String>();
					data.add(pId);
					data.add(supplierName);
					data.add(piId);
					data.add(name);
					data.add(piName);
					data.add(pCount);
					data.add(pPrice);
					data.add(price);
					data.add(pDate);
					dataVector.add(data);
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
		
		
		
       
        setTitle("进货登记");
        this.setSize(1000, 650);
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
				new AddPurchaseDialog(QueryPurchase.this);
			}
		});
    	
        
        update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int raw = table.getSelectedRow();
				if(raw < 0){
					JOptionPane.showMessageDialog(null, "请选中先");
					return;
				}
				String portId = (String) table.getValueAt(raw, 2);
				new UpdatePurchase(portId, table, data, header, raw, QueryPurchase.this);
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
				String pId =(String) table.getValueAt(row, 0);
				try{
					Class.forName("com.mysql.jdbc.Driver");
					try(
						Connection conn = DriverManager.getConnection(url, user, password);
							){
						String sql = "update  purchase set pStatus = \"0\" where pId = ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setObject(1, pId);
						int n = ps.executeUpdate();
						if(n > 0){
							JOptionPane.showMessageDialog(null, "删除成功");
							QueryPurchase.this.dispose();
							new QueryPurchase();
						}else{
							JOptionPane.showMessageDialog(null, "删除失败，请稍后再试");
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
