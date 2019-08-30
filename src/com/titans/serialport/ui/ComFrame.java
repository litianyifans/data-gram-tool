package com.titans.serialport.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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
import com.titans.serialport.exception.NoSuchPort;
import com.titans.serialport.exception.NotASerialPort;
import com.titans.serialport.exception.PortInUse;
import com.titans.serialport.exception.SerialPortParameterFailure;
import com.titans.serialport.exception.TooManyListeners;
import com.titans.serialport.manage.SerialPortManager;
import com.titans.serialport.utils.DBUtils;
import com.titans.serialport.utils.DateUtils;
import com.titans.serialport.utils.DialogShowUtils;
import com.titans.serialport.utils.FileUtils;
import com.titans.serialport.utils.MessageElianUtil;
import com.titans.serialport.utils.MyUtils;
import com.titans.serialport.utils.TableUtils;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ComFrame extends JFrame implements ActionListener {
	private static Logger logger = Logger.getLogger(ComFrame.class);
	public ComFrame() {
	}

	public static HashMap<String, Object> viewParams = MainFrame.paramPenelMap.get(SysParamConst.TAB1);

	// 报文监听容器
	public Map<String, SerialPort> serials = new HashMap<String, SerialPort>();

	JButton btnNewButton_1 = new JButton("导入");


	JButton searchButton = new JButton("查询");


	JButton button_clear = new JButton("清除");

	JButton exportData = new JButton("导出");

	JComboBox protocolChoice = new JComboBox();

	

	// 时间选择器
	DatePicker datepick = DateUtils.getDatePicker(270, 10, 150, 30);
	JLabel separateLabel = new JLabel("—");

	DatePicker datepick1 = DateUtils.getDatePicker(450, 10, 150, 30);

	/**
	 * 正常的风格
	 */
	private final String STYLE_NORMAL = "normal";

	/**
	 * 字体为红色
	 */
	private final String STYLE_RED = "red";

	/**
	 * 初始化界面数据到容器
	 */
	@SuppressWarnings("unchecked")
	public void initData() {
		// com容器
		HashMap<String, Object> comMap = new HashMap<String, Object>();
		// 初始化com按钮数据
		viewParams.put(SysParamConst.CONN_BUTTON_STATUS, SysParamConst.BT_CONN);
		// 初始化串口列表
		List<String> commList = null;
		commList = SerialPortManager.findPort();
		if (commList == null || commList.size() < 1) {
			DialogShowUtils.warningMessage("没有搜索到有效串口！");
		} else {
			List<String> coms = new ArrayList<String>();
			for (String com : commList) {
				coms.add(com);
			}
			viewParams.put(SysParamConst.COMS, coms);
			viewParams.put(SysParamConst.SELECT_COM, coms.get(0));
		}
		// 初始波特率列表
		viewParams.put(SysParamConst.BURDS, SysParamConst.initBautrates());
		viewParams.put(SysParamConst.SELECT_BURD, "9600");
		viewParams.put(SysParamConst.SELECT_PROTOCOL, SysParamConst.PROTOCOL_EL_NAME);
		viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_OPEN);
		viewParams.put(SysParamConst.QUERY_PACKET_TEXT, SysParamConst.QUERY_CLOSE);
	}

	public void initComponents() {
		JButton startButton = new JButton("滚动");
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_OPEN);
			}
		});
		JButton stopButton = new JButton("暂停");
		stopButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_CLOSE);
			}
		});
		stopButton.setBounds(138, 14, 64, 23);
		JLabel timeLabel = new JLabel("时间范围：");
		timeLabel.setFont(new Font("宋体", Font.PLAIN, 12));
		timeLabel.setForeground(Color.BLACK);
		separateLabel.setFont(new Font("宋体", Font.PLAIN, 12));
		separateLabel.setForeground(Color.BLACK);
		searchButton.addActionListener(this);
		button_clear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JTextPane dataView = (JTextPane)viewParams.get(SysParamConst.PARSE_JSON_VIEW);
				dataView.setText(null);
			}
		});
		JPanel queryPanel = new JPanel();
		MainFrame.mainPanelCom.add(queryPanel, BorderLayout.NORTH);
		exportData.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				logger.debug("开始导出");
				JTable dataTable = (JTable)viewParams.get(SysParamConst.DATA_TABLE);
				String[] headName = { "报文时间", "数据" };
				String[] colName = { "ColDate", "ColPacket" };
				Date startDate = (Date) datepick.getValue();
				Date endDate = (Date) datepick1.getValue();
				String startStr = MyUtils.formatDateStr_param(startDate);
				String endStr = MyUtils.formatDateStr_param(endDate);
				List<Map<String, String>> queryResults = DBUtils.queryDatas( "com", startStr, endStr);
				FileUtils.exportFactory(queryPanel, exportData, "com_" + MyUtils.formatDateStr_num(),
						queryResults == null ? new ArrayList() : queryResults,headName,colName);
				logger.debug("导出结束");
			}
		});
		JLabel ruleLable = new JLabel("规约：");
		ruleLable.setForeground(Color.BLACK);
		ruleLable.setFont(new Font("宋体", Font.PLAIN, 12));
		protocolChoice.setFocusable(false);
		protocolChoice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					viewParams.remove(SysParamConst.SELECT_PROTOCOL);
					viewParams.put(SysParamConst.SELECT_PROTOCOL, e.getItem());
				}
			}
		});
		
		
		FlowLayout flQueryPanel = (FlowLayout) queryPanel.getLayout();
		flQueryPanel.setAlignment(FlowLayout.LEFT);
		queryPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		queryPanel.add(timeLabel);
		queryPanel.add(datepick);
		queryPanel.add(separateLabel);
		queryPanel.add(datepick1);
		queryPanel.add(searchButton);
		queryPanel.add(ruleLable);
		queryPanel.add(protocolChoice);
	
		queryPanel.add(button_clear);
		queryPanel.add(startButton);
		queryPanel.add(stopButton);
		queryPanel.add(exportData);
		// 串口设置面板
		JPanel serialPortPanel = new JPanel();
		JLabel serialPortLabel = new JLabel("串口");
		serialPortLabel.setForeground(Color.gray);
		serialPortPanel.add(serialPortLabel);
		JLabel baudrateLabel = new JLabel("波特率");
		JComboBox baudrateChoice = new JComboBox();
		JComboBox serialPortChoice = new JComboBox();
		// 串口面板设置
		serialPortPanel.setBorder(BorderFactory.createTitledBorder("串口设置"));
		// 串口面板---各组件设置
		serialPortChoice.setFocusable(false);
		serialPortChoice.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					viewParams.remove(SysParamConst.SELECT_COM);
					viewParams.put(SysParamConst.SELECT_COM, e.getItem());
				}
			}
		});
		serialPortPanel.add(serialPortChoice);
		baudrateLabel.setForeground(Color.gray);
		serialPortPanel.add(baudrateLabel);
		baudrateChoice.setFocusable(false);
		baudrateChoice.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					viewParams.remove(SysParamConst.SELECT_BURD);
					viewParams.put(SysParamConst.SELECT_BURD, e.getItem());
				}
			}
		});
		
		serialPortPanel.add(baudrateChoice);
		String connButtonName = (String) viewParams.get(SysParamConst.CONN_BUTTON_STATUS);
		JButton connButton = new JButton(connButtonName);
		connButton.setActionCommand(connButtonName);
		connButton.addActionListener(this);
		serialPortPanel.add(connButton);
		// 串口面板
		GroupLayout glSerialPortPanel = new GroupLayout(serialPortPanel);
		glSerialPortPanel.setHorizontalGroup(glSerialPortPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(
						glSerialPortPanel.createSequentialGroup().addGap(10)
								.addGroup(glSerialPortPanel.createParallelGroup(Alignment.LEADING)
										.addGroup(glSerialPortPanel.createSequentialGroup().addGap(50).addComponent(serialPortChoice,
												GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))
										.addComponent(serialPortLabel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
				.addGroup(glSerialPortPanel.createSequentialGroup().addGap(10)
						.addGroup(glSerialPortPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(baudrateLabel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
								.addGroup(glSerialPortPanel.createSequentialGroup().addGap(50).addComponent(baudrateChoice,
										GroupLayout.PREFERRED_SIZE, 114, GroupLayout.PREFERRED_SIZE))))
				.addGroup(glSerialPortPanel.createSequentialGroup().addGap(94).addComponent(connButton, GroupLayout.PREFERRED_SIZE, 80,
						GroupLayout.PREFERRED_SIZE)));
		glSerialPortPanel.setVerticalGroup(glSerialPortPanel.createParallelGroup(Alignment.LEADING).addGroup(glSerialPortPanel
				.createSequentialGroup().addGap(19)
				.addGroup(glSerialPortPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(serialPortChoice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(glSerialPortPanel.createSequentialGroup().addGap(3).addComponent(serialPortLabel)))
				.addGap(13)
				.addGroup(glSerialPortPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(glSerialPortPanel.createSequentialGroup().addGap(3).addComponent(baudrateLabel))
						.addComponent(baudrateChoice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(20).addComponent(connButton)));
		serialPortPanel.setLayout(glSerialPortPanel);
		// 初始化选择项数据
		SysParamConst.changeJBox(SysParamConst.initBautrates(), baudrateChoice);
		if(viewParams.containsKey(SysParamConst.COMS)){
			SysParamConst.changeJBox((List<String>) viewParams.get(SysParamConst.COMS), serialPortChoice);
		}
		SysParamConst.changeJBox(SysParamConst.initProtocolNames(), protocolChoice);
		
		// 解析前
		String[] columnNames = { "时间", "内容" };
		int tableWidth[] = { 150, 853 };
		Vector<String> columns = TableUtils.buildTableModel(columnNames);
		Vector<Vector<String>> rowdatas = new Vector<Vector<String>>();
		DefaultTableModel tableModel = new DefaultTableModel(rowdatas, columns);
		JTable dataTable = TableUtils.buildTable(tableModel);
		JScrollPane scrollDataView = new JScrollPane();
		/* 数据区域的风格 */
		scrollDataView.setBounds(168, 69, 1006, 317);
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
                if(row != -1){  
                	JTable dataTable = (JTable)viewParams.get(SysParamConst.DATA_TABLE);
    				String message = dataTable.getValueAt(dataTable.getSelectedRow(),1).toString();
    				List<String> result = null ;
    				if(message!=null&&!"".equals(message)){
    					String protocolName = (String) viewParams.get(SysParamConst.SELECT_PROTOCOL);
    					if(protocolName.equals(SysParamConst.PROTOCOL_EL_NAME)){
    						result = MessageElianUtil.comParase(message.trim()) ;
    					}
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
		
		
		// 解析后(json数据显示面板)
		JTextPane dataView = new JTextPane();
		dataView.setFocusable(false);
		Style def = dataView.getStyledDocument().addStyle(null, null);
		Style normal = dataView.addStyle(STYLE_NORMAL, def);
		Style s = dataView.addStyle(STYLE_RED, normal);
		dataView.setParagraphAttributes(normal, true);
		JScrollPane scrollDataViewJson = new JScrollPane();
		scrollDataViewJson.setViewportView(dataView);
		// 转换后区
		JPanel convertPanel = new JPanel();
		convertPanel.setLayout(new BorderLayout(0, 0));
		convertPanel.add(serialPortPanel, BorderLayout.WEST);
		convertPanel.add(scrollDataViewJson, BorderLayout.CENTER);
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(500);
		// 加入上下分隔条
		MainFrame.mainPanelCom.add(splitPane, BorderLayout.CENTER);
		// 上面为来源报文区
		splitPane.setLeftComponent(scrollDataView);
		// 下面为解析后报文
		splitPane.setRightComponent(convertPanel);
		// 放入容器
		viewParams.put(SysParamConst.CONN_BUTTON, connButton);
		viewParams.put(SysParamConst.SERIAL_PORT_PANEL, serialPortPanel);
		viewParams.put(SysParamConst.SERIAL_PORT_LABEL, serialPortLabel);
		viewParams.put(SysParamConst.BAUDRATE_LABEL, baudrateLabel);
		viewParams.put(SysParamConst.BAUDRATE_CHOICE, baudrateChoice);
		viewParams.put(SysParamConst.SERIALPORT_CHOICE, serialPortChoice);
		viewParams.put(SysParamConst.PARSE_JSON_VIEW, dataView);
		viewParams.put(SysParamConst.PARSE_JSON_VIEW_PANEL, scrollDataViewJson);
		viewParams.put(SysParamConst.SCROLL_DATAVIEW, scrollDataView);
		viewParams.put(SysParamConst.DATA_TABLE, dataTable);
		viewParams.put(SysParamConst.TABLE_MODEL, tableModel);
		viewParams.put(SysParamConst.ROW_DATAS, rowdatas);
		viewParams.put(SysParamConst.COLUMNS, columns);
		viewParams.put(SysParamConst.TABLE_WIDTH, tableWidth);
		viewParams.put(SysParamConst.QUERY_PANEL, queryPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton connButton = (JButton) viewParams.get(SysParamConst.CONN_BUTTON);
		if (e.getSource() == connButton) {
			if (SysParamConst.BT_CONN.equals(e.getActionCommand())) {
				openSerialPort();
			} else if (SysParamConst.BT_CLOSE.equals(e.getActionCommand())) {
				closeSerialPort();
			}
		} else if (e.getSource() == searchButton) {
			Date startDate = (Date) datepick.getValue();
			Date endDate = (Date) datepick1.getValue();
			String startStr = MyUtils.formatDateStr_param(startDate);
			String endStr = MyUtils.formatDateStr_param(endDate);
			List<Map<String, String>> queryResults = DBUtils.queryDatas( "com", startStr, endStr);
			viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_CLOSE);
			JTable dataTable = (JTable) viewParams.get(SysParamConst.DATA_TABLE);
			DefaultTableModel tableModel = (DefaultTableModel) viewParams.get(SysParamConst.TABLE_MODEL);
			tableModel.setRowCount(0);
			dataTable.setModel(tableModel);
			dataTable.setRowHeight(25);
			for (Map<String, String> map : queryResults) {
				dataShow(map.get("ColDate"), map.get("ColPacket"));
			}
		}
	}

	/**
	 * 打开串口
	 * 
	 */
	private void openSerialPort() {
		viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_OPEN);
		// 获取串口名称
		String commName = (String) viewParams.get(SysParamConst.SELECT_COM);
		// 获取波特率
		int baudrate = Integer.parseInt((String) viewParams.get(SysParamConst.SELECT_BURD));
		SerialPort serialport = null;
		// 检查串口名称是否获取正确
		if (commName == null || commName.equals("")) {
			DialogShowUtils.warningMessage("没有搜索到有效串口！");
		} else {
			try {
				serialport = SerialPortManager.openPort(commName, baudrate);
				if (serialport != null) {
					// 放入串口列表
					serials.put(commName, serialport);
					viewParams.remove(SysParamConst.CONN_BUTTON_STATUS);
					viewParams.put(SysParamConst.CONN_BUTTON_STATUS, SysParamConst.BT_CLOSE);
					JButton connButton = (JButton) viewParams.get(SysParamConst.CONN_BUTTON);
					connButton.setActionCommand(SysParamConst.BT_CLOSE);
					connButton.setText(SysParamConst.BT_CLOSE);
				}
			} catch (SerialPortParameterFailure e) {
				e.printStackTrace();
			} catch (NotASerialPort e) {
				e.printStackTrace();
			} catch (NoSuchPort e) {
				e.printStackTrace();
			} catch (PortInUse e) {
				e.printStackTrace();
				DialogShowUtils.warningMessage("串口已被占用！");
			}
		}
		try {
			SerialPortManager.addListener(serialport,new SerialListener(commName));
		} catch (TooManyListeners e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 关闭串口
	 * 
	 */
	private void closeSerialPort() {
		viewParams.put(SysParamConst.SHOW_PACKET_TEXT, SysParamConst.SHOW_CLOSE);
		String comPortName = (String) viewParams.get(SysParamConst.SELECT_COM);
		SerialPort serialPort = serials.get(comPortName);
		SerialPortManager.closePort(serialPort);
		serials.remove(comPortName);
		viewParams.remove(SysParamConst.CONN_BUTTON_STATUS);
		viewParams.put(SysParamConst.CONN_BUTTON_STATUS, SysParamConst.BT_CONN);
		JButton connButton = (JButton) viewParams.get(SysParamConst.CONN_BUTTON);
		connButton.setActionCommand(SysParamConst.BT_CONN);
		connButton.setText(SysParamConst.BT_CONN);
	}
	
	
	/**
	 * 数据监听
	 * 
	 */
	private class SerialListener implements SerialPortEventListener {

		private SerialPort serialPort;

		private String commport;

		public SerialListener(String commName) {
			this.commport = commName;
			this.serialPort = serials.get(commName);
		}

		/**
		 * 处理监控到的串口事件
		 */
		public void serialEvent(SerialPortEvent serialPortEvent) {
			switch (serialPortEvent.getEventType()) {
			case SerialPortEvent.BI: // 10 通讯中断
				DialogShowUtils.errorMessage("与串口设备通讯中断");
				break;
			case SerialPortEvent.OE: // 7 溢位（溢出）错误
			case SerialPortEvent.FE: // 9 帧错误
			case SerialPortEvent.PE: // 8 奇偶校验错误
			case SerialPortEvent.CD: // 6 载波检测
			case SerialPortEvent.CTS: // 3 清除待发送数据
			case SerialPortEvent.DSR: // 4 待发送数据准备好了
			case SerialPortEvent.RI: // 5 振铃指示
			case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 输出缓冲区已清空
				break;
			case SerialPortEvent.DATA_AVAILABLE: // 1 串口存在可用数据
				byte[] data = null;
				try {
					String pattern = "6\\s1.+f\\s2";
					if (serialPort == null) {
						DialogShowUtils.errorMessage("串口对象为空！监听失败！");
					} else {
						// 读取串口数据
						data = SerialPortManager.readFromPort(serialPort);
						for(String protocol: SysParamConst.getProtocolList()){
							String dataPacket = MyUtils.regText(
							  		MyUtils.hex2Str(
								  			MyUtils.byteArray2HexString(data, data.length, false)
								  		),protocol) ;
							String dateStr = MyUtils.formatDateStr_random(MyUtils.formatDateStr_ss())  ;
							if(null != dataPacket && !"".equals(dataPacket)){
								if(SysParamConst.SHOW_OPEN.equals((String) viewParams.get(SysParamConst.SHOW_PACKET_TEXT))){
									dataShow(dateStr,dataPacket);
								}	
								DBUtils.insertData("com",MyUtils.formatDateStr_all(dateStr),dataPacket);
							}
						}
					}
				} catch (Exception e) {
					DialogShowUtils.errorMessage(e.toString());
					// 发生读取错误时显示错误信息后退出系统
					System.exit(0);
				}
				break;
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
		Vector<String> newRow = new Vector<String>();
		newRow.add(dateText);
		newRow.add(text);
		tableModel.addRow(newRow);
		
		//设置最大显示长度 
		if(rowCount==10000){
			tableModel.setRowCount( 0 );
		}
		dataTable.setModel(tableModel);
		dataTable.setRowHeight(25);
		dataTable.scrollRectToVisible(dataTable.getCellRect(rowCount, 0, true));
		Map<String, String> dataObj = new HashMap<String, String>();
		dataObj.put("ColDate", dateText);
		dataObj.put("ColPacket", text);
	
		
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
	
}
