package com.titans.serialport.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;

import org.apache.log4j.Logger;

import com.eltima.components.ui.DatePicker;
import com.titans.serialport.costant.SysParamConst;
import com.titans.serialport.manage.SocketManager;
import com.titans.serialport.utils.BatchTool;
import com.titans.serialport.utils.DBUtils;
import com.titans.serialport.utils.DateUtils;
import com.titans.serialport.utils.FileUtils;
import com.titans.serialport.utils.MessageParseUtil;
import com.titans.serialport.utils.MyUtils;
import com.titans.serialport.utils.TableUtils;

public class NetFrame extends JFrame implements ActionListener {
	
	private static Logger logger = Logger.getLogger(NetFrame.class);
	public static HashMap<String, Object> viewParams = MainFrame.paramPenelMap.get(SysParamConst.TAB2);
	public Queue<String> pessisData = new ConcurrentLinkedQueue<String>();
	
	
	JButton searchButton = new JButton("查询");

	JButton connButton = new JButton("连接");

	JTextField ipInput = null;

	JTextField portInput = new JTextField("8080");

	// 时间选择器
	DatePicker datepick = DateUtils.getDatePicker(270, 10, 150, 30);

	DatePicker datepick1 = DateUtils.getDatePicker(450, 10, 150, 30);
	SocketManager sm  = null ;
	boolean shutFlag = false ;
	
	public void initData() {
		sm = new SocketManager();
		/*int availProcess = Runtime.getRuntime().availableProcessors();
		availProcess = 1 ;
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(availProcess);
		for(int i = 0 ; i < availProcess ; i++ ){
			fixedThreadPool.submit(new netDataShowThread(viewParams, sm));
		}*/
		//System.out.println(sm.queue.size());
		viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_OPEN);
		viewParams.put(SysParamConst.QUERY_PACKET_TEXT, SysParamConst.QUERY_CLOSE);
	}

	public void initNetComponents() {
		JPanel queryPanel = new JPanel();
		MainFrame.mainPanelSocket.add(queryPanel, BorderLayout.NORTH);
		// 查询面板使用流式布局
		FlowLayout flQueryPanel = (FlowLayout) queryPanel.getLayout();
		flQueryPanel.setAlignment(FlowLayout.LEFT);
		queryPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		JButton btnNewButton_1 = new JButton("滚动");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_OPEN);
			}
		});
		JButton btnNewButton_2 = new JButton("暂停");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_CLOSE);
			}
		});
		JLabel label = new JLabel("时间范围：");
		label.setFont(new Font("宋体", Font.PLAIN, 12));
		label.setForeground(Color.BLACK);
		JLabel label1 = new JLabel("—");
		label1.setFont(new Font("宋体", Font.PLAIN, 12));
		label1.setForeground(Color.BLACK);
		searchButton.setBounds(609, 14, 60, 23);
		searchButton.addActionListener(this);
		JButton button_clear = new JButton("清除");
		button_clear.setBounds(886, 14, 60, 23);
		button_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextPane dataView = (JTextPane)viewParams.get(SysParamConst.PARSE_JSON_VIEW);
				dataView.setText(null);
			}
		});
		JButton exportData = new JButton("导出");
		exportData.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				logger.debug("开始导出");
				String[] headName = { "报文时间", "数据" };
				String[] colName = { "ColDate", "ColPacket" };
				Date startDate = (Date) datepick.getValue() ;
				Date endDate = (Date) datepick1.getValue() ;
				String startStr = MyUtils.formatDateStr_param(startDate) ;
				String endStr = MyUtils.formatDateStr_param(endDate) ;
				List<Map<String,String>> queryResults = DBUtils.queryDatas("net",startStr,endStr);
				FileUtils.exportFactory(queryPanel, exportData, "net_" + MyUtils.formatDateStr(),
						queryResults == null ? new ArrayList() : queryResults ,headName,colName);
				logger.debug("导出结束");
			}
		});
		queryPanel.add(label);
		queryPanel.add(datepick);
		queryPanel.add(label1);
		queryPanel.add(datepick1);
		queryPanel.add(searchButton);
		JComboBox protocolChoice = new JComboBox();
		protocolChoice.addItem("内网通信协议V2.12");
		JLabel label_2 = new JLabel("规约：");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("宋体", Font.PLAIN, 12));
		queryPanel.add(label_2);
		queryPanel.add(protocolChoice);
		queryPanel.add(button_clear);
		queryPanel.add(btnNewButton_1);
		queryPanel.add(btnNewButton_2);
		queryPanel.add(exportData);
		// 网络面板
		JPanel paramsPanel = new JPanel();
		JLabel ipPortLabel = new JLabel("IP");
		JLabel portLabel = new JLabel("端口");
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		ipInput = new JTextField(addr.getHostAddress());
		// 串口面板设置
		paramsPanel.setBorder(BorderFactory.createTitledBorder("参数配置"));
		// 串口面板---各组件设置
		paramsPanel.add(ipInput);
		ipPortLabel.setForeground(Color.gray);
		paramsPanel.add(ipPortLabel);
		portLabel.setForeground(Color.gray);
		paramsPanel.add(portLabel);
		paramsPanel.add(portInput);
		connButton.setActionCommand("连接");
		connButton.addActionListener(this);
		paramsPanel.add(connButton);
		ipInput.setToolTipText("请输入网络接收IP地址");
		portInput.setToolTipText("请输入网络接收IP地址");
		
		//设置布局
		GroupLayout glParamsPanel = new GroupLayout(paramsPanel);
		glParamsPanel
				.setHorizontalGroup(glParamsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glParamsPanel.createSequentialGroup().addGroup(glParamsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(ipPortLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(portLabel, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addGroup(glParamsPanel.createSequentialGroup().addGap(31)
										.addGroup(glParamsPanel.createParallelGroup(Alignment.LEADING)
												.addComponent(ipInput, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
												.addComponent(portInput, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)))
								.addGroup(glParamsPanel.createSequentialGroup().addGap(80).addComponent(connButton,
										GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)))
								.addGap(10)));
		glParamsPanel.setVerticalGroup(glParamsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glParamsPanel.createSequentialGroup().addGap(10)
						.addGroup(glParamsPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(ipInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(glParamsPanel.createSequentialGroup().addGap(3).addComponent(ipPortLabel,
										GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
						.addGap(10)
						.addGroup(glParamsPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(glParamsPanel.createSequentialGroup().addGap(3).addComponent(portLabel,
										GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
								.addComponent(portInput, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(22).addComponent(connButton).addGap(362)));
		paramsPanel.setLayout(glParamsPanel);
		
		// 未解析的数据列表
		String[] columnNames = { "时间", "内容" };
		int tableWidth[] = { 150, 853 };
		Vector<String> columns = TableUtils.buildTableModel(columnNames);
		Vector<Vector<String>> rowdatas = new Vector<Vector<String>>();
		DefaultTableModel tableModel = new DefaultTableModel(rowdatas, columns);
		JTable dataTable = TableUtils.buildTable(tableModel);
		JScrollPane scrollDataView = new JScrollPane();
		/* 数据区域的风格 */
		dataTable.setColumnModel(TableUtils.getColumn(dataTable, tableWidth));
		scrollDataView.setViewportView(dataTable);
		ListSelectionModel cellSelectionModel = dataTable.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel.addListSelectionListener(new ListSelectionListener(){  
            public void valueChanged(ListSelectionEvent e)  
            {  
            	if(e.getValueIsAdjusting()){
            		return;
            	}
                int row = dataTable.getSelectedRow();  
                if(row != -1)  
                {  
                	JTable dataTable = (JTable)viewParams.get(SysParamConst.DATA_TABLE);
    				String message = dataTable.getValueAt(dataTable.getSelectedRow(),1).toString();
    				if(message!=null&&!"".equals(message)){
    					List<String> result = MessageParseUtil.socketParase(message.trim()) ;
    					if(!result.isEmpty()){
    						JTextPane dataView = (JTextPane)viewParams.get(SysParamConst.PARSE_JSON_VIEW);
    						dataView.setText(null);
    						for(String temp : result){
    							data_show_parse(temp);
    						}
    					}
    				}
                }  
            }  
        }); 
		
		// 解析数据面板
		JTextPane dataView = new JTextPane();
		dataView.setFocusable(false);
		Style def = dataView.getStyledDocument().addStyle(null, null);
		Style normal = dataView.addStyle(SysParamConst.STYLE_NORMAL, def);
		Style s = dataView.addStyle(SysParamConst.STYLE_RED, normal);
		dataView.setParagraphAttributes(normal, true);
		JScrollPane scrollDataViewJson = new JScrollPane();
		scrollDataViewJson.setViewportView(dataView);
		
		
		// 转换后区
		JPanel convertPanel = new JPanel();
		convertPanel.setLayout(new BorderLayout(0, 0));
		convertPanel.add(paramsPanel, BorderLayout.WEST);
		convertPanel.add(scrollDataViewJson, BorderLayout.CENTER);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(500);
		// 加入上下分隔条
		MainFrame.mainPanelSocket.add(splitPane, BorderLayout.CENTER);
		// 上面为来源报文区
		splitPane.setLeftComponent(scrollDataView);
		// 下面为解析后报文
		splitPane.setRightComponent(convertPanel);
		// 放入容器
		viewParams.put(SysParamConst.CONN_BUTTON, connButton);
		viewParams.put(SysParamConst.NET_PARAM_PANEL, paramsPanel);
		viewParams.put(SysParamConst.QUERY_PANEL, queryPanel);
		viewParams.put(SysParamConst.PARSE_JSON_VIEW_PANEL, scrollDataViewJson);
		viewParams.put(SysParamConst.SCROLL_DATAVIEW, scrollDataView);
		viewParams.put(SysParamConst.DATA_TABLE, dataTable);
		viewParams.put(SysParamConst.TABLE_MODEL, tableModel);
		viewParams.put(SysParamConst.PARSE_JSON_VIEW, dataView);
	}

	
	public void openSocket(){
		shutFlag = false ;
		String portStr = portInput.getText();
		int port = Integer.parseInt(portStr);
		sm.startThread(); 
		sm.openServer(port,sm);
		System.out.println("start");
		new Thread(new netDataShowThread(viewParams, sm)).start();
		new Thread(new dataPersisShowThread()).start();
		System.out.println("end");
		viewParams.remove(SysParamConst.CONN_BUTTON_STATUS) ;
		viewParams.put(SysParamConst.CONN_BUTTON_STATUS, SysParamConst.BT_CLOSE);
		viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_OPEN) ;
		JButton connButton = (JButton)viewParams.get(SysParamConst.CONN_BUTTON) ;
		connButton.setActionCommand(SysParamConst.BT_CLOSE);
		connButton.setText(SysParamConst.BT_CLOSE);
	}
	
	public void closeSocket(){
		viewParams.remove(SysParamConst.CONN_BUTTON_STATUS) ;
		viewParams.put(SysParamConst.CONN_BUTTON_STATUS, SysParamConst.BT_CONN) ;
		JButton connButton = (JButton)viewParams.get(SysParamConst.CONN_BUTTON) ;
		connButton.setActionCommand(SysParamConst.BT_CONN);
		connButton.setText(SysParamConst.BT_CONN);
		sm.stopThread();
		shutFlag = true ;
		
	}
	public static void closeButton(){
		viewParams.remove(SysParamConst.CONN_BUTTON_STATUS) ;
		viewParams.put(SysParamConst.CONN_BUTTON_STATUS, SysParamConst.BT_CONN) ;
		JButton connButton = (JButton)viewParams.get(SysParamConst.CONN_BUTTON) ;
		connButton.setActionCommand(SysParamConst.BT_CONN);
		connButton.setText(SysParamConst.BT_CONN);
	}
	

	private class netDataShowThread implements Runnable {

		SocketManager sm = null ;
		HashMap<String, Object>   viewParams  = null ;
		
		public netDataShowThread(HashMap<String, Object> viewParams,SocketManager sm ) {
			this.viewParams = viewParams;
			this.sm = sm ;
		}
	
		public void run() {
			
			while(true){
				if(!sm.queue.isEmpty()){
					//System.out.println("queue---"+sm.queue.size());
					String dataPacket = sm.queue.poll() ;
					String dateStr = MyUtils.formatDateStr_random(MyUtils.formatDateStr_ss())  ;
					if(SysParamConst.SHOW_OPEN.equals((String) viewParams.get(SysParamConst.SHOW_PACKET_TEXT))){
						dataShow(dateStr,dataPacket);
					}
					if(null != dataPacket && !"".equals(dataPacket)){
						pessisData.offer(SysParamConst.getNetSql(MyUtils.formatDateStr_all(dateStr), dataPacket));
					}

				}
				if(sm.queue.isEmpty()&&shutFlag){
					break ;
				}
			}
		}
	}
	
	
	private class dataPersisShowThread implements Runnable{
		String batchSql[] = new String[500];
		int tag = 0 ;
		
		
		public void stopFalg(){
			shutFlag = true ;
		}
		@Override
		public void run() {
			while(true){
				if(!pessisData.isEmpty()){
					batchSql[tag++] = pessisData.poll();
					//System.out.println("tag--"+tag);
					
				}
				if ((tag != 0 && tag / 500 != 0)||shutFlag) {
					new BatchTool().exucteUpdate(batchSql);
					batchSql =  new String[500];// reset
					tag = 0;// reset
				}
				if(shutFlag){
					break ;
				}
			}
			
		}
		
	}

	/**
	 * 数据打印面板
	 * 
	 */
	private void dataShow(String dateText,String text) {
		
		if(null == text || "".equals(text)){
			return ;
		}
		JTable dataTable = (JTable)viewParams.get(SysParamConst.DATA_TABLE);
		DefaultTableModel tableModel = (DefaultTableModel)viewParams.get(SysParamConst.TABLE_MODEL);
		
	
		int rowCount = dataTable.getRowCount();
		if(rowCount ==10000){
			tableModel.setRowCount( 0 );
			//tableModel.removeRow(0);
		}
		Vector<String> newRow = new Vector<String>();
		newRow.add(dateText);
		newRow.add(text);
		tableModel.addRow(newRow);
		dataTable.setModel(tableModel);
		dataTable.setRowHeight(25);
		dataTable.scrollRectToVisible(dataTable.getCellRect(rowCount, 0, true));
	
	}
	
	public void  data_show_parse(String text){
		if(null == text || "".equals(text)){
			return ;
		}
		text = MyUtils.data_show_format(text);
		JTextPane dataView = (JTextPane)viewParams.get(SysParamConst.PARSE_JSON_VIEW);
		Document document = dataView.getDocument();
		StringBuilder builderData = new StringBuilder();
		builderData.setLength(0);
		builderData.append(text).append("\r\n");
		try {
			dataView.getDocument().insertString(document.getLength(), builderData.toString(),
					dataView.getStyle("normal"));
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		dataView.setCaretPosition(document.getLength());
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton connButton = (JButton)viewParams.get(SysParamConst.CONN_BUTTON) ;
		if(e.getSource() == connButton){
			if(SysParamConst.BT_CONN.equals(e.getActionCommand())){
				openSocket();
			}else if(SysParamConst.BT_CLOSE.equals(e.getActionCommand())){
				closeSocket();
			}
		}else if(e.getSource() == searchButton){   
			Date startDate = (Date) datepick.getValue() ;
			Date endDate = (Date) datepick1.getValue() ;
			String startStr = MyUtils.formatDateStr_param(startDate) ;
			String endStr = MyUtils.formatDateStr_param(endDate) ;
			List<Map<String,String>> queryResults = DBUtils.queryDatas("net",startStr,endStr);
			viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_CLOSE) ;
			JTable dataTable = (JTable)viewParams.get(SysParamConst.DATA_TABLE);
			DefaultTableModel tableModel = (DefaultTableModel)viewParams.get(SysParamConst.TABLE_MODEL);
			tableModel.setRowCount(0);
			dataTable.setModel(tableModel);
			dataTable.setRowHeight(25);
			for(Map<String,String> map:queryResults){
				dataShow(map.get("ColDate"), map.get("ColPacket"));
			}
			
		}
		
	}
}
