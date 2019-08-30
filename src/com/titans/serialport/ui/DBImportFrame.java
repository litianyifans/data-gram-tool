package com.titans.serialport.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
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
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import org.apache.log4j.Logger;

import com.eltima.components.ui.DatePicker;
import com.titans.serialport.costant.SysParamConst;
import com.titans.serialport.utils.DBUtils;
import com.titans.serialport.utils.DateUtils;
import com.titans.serialport.utils.FileUtils;
import com.titans.serialport.utils.MessageElianUtil;
import com.titans.serialport.utils.MessageParseUtil;
import com.titans.serialport.utils.MyUtils;
import com.titans.serialport.utils.TableUtils;

public class DBImportFrame extends JFrame implements ActionListener {

	private static Logger logger = Logger.getLogger(DBImportFrame.class);
	public static HashMap<String, Object> viewParams = MainFrame.paramPenelMap.get(SysParamConst.TAB4);

	JButton bt_import = new JButton("打开");
	JButton bt_clear = new JButton("清除");
	JComboBox tabNameChoice = new JComboBox();
	JTextField dbNameText = new JTextField(10);
	JTextField pageNumText = new JTextField(4);
	
	JTextPane dataView = new JTextPane();

	JButton bt_headPage = new JButton("首页");
	JButton bt_endPage = new JButton("尾页");
	JButton bt_prePage = new JButton("上一页");
	JButton bt_nextPage = new JButton("下一页");
	
	
	
	JTextField stepNumText = new JTextField(4);
	JButton bt_step = new JButton("跳转到");
	
	// 时间选择器
	DatePicker datepick = DateUtils.getDatePicker(270, 10, 150, 30);
	JLabel separateLabel = new JLabel("—");
	DatePicker datepick1 = DateUtils.getDatePicker(450, 10, 150, 30);
	JButton searchButton = new JButton("查询");
	
	String filepath = "" ;
	String imFileName = "" ;
	private int currentPage = 1 ;
	private int countPage = 1 ;
	String tabName = "com";

	// 未解析的数据列表
	String[] columnNames = { "时间", "数据" };

	int tableWidth[] = { 150, 853 };

	Vector<String> columns = TableUtils.buildTableModel(columnNames);

	Vector<Vector<String>> rowdatas = new Vector<Vector<String>>();

	DefaultTableModel tableModel = new DefaultTableModel(rowdatas, columns);

	JTable dataTable = TableUtils.buildTable(tableModel);

	JScrollPane scrollDataView = new JScrollPane();

	List<Map<String, String>> results = new ArrayList<Map<String, String>>();

	public void initDBImportComponents() {
		JPanel panel = new JPanel();
		MainFrame.mainPanelDbImport.add(panel, BorderLayout.NORTH);
		// 查询面板使用流式布局
		FlowLayout flQueryPanel = (FlowLayout) panel.getLayout();
		flQueryPanel.setAlignment(FlowLayout.LEFT);
		panel.setBorder(
				new TitledBorder(null, "\u67E5\u8BE2\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tabNameChoice.addItem("COM");
		tabNameChoice.addItem("SOCKET");
		tabNameChoice.setSelectedItem("COM");
		tabNameChoice.addActionListener(this);
		dbNameText.setEditable(false);
		pageNumText.setEditable(false);
		pageNumText.setText(currentPage+"");
		JLabel label_2 = new JLabel("数据文件：");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("宋体", Font.PLAIN, 12));
		bt_import.addActionListener(this);
		bt_clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dataView.setText(null);
			}
		});
		

		bt_headPage.addActionListener(this);
		bt_prePage.addActionListener(this);
		bt_nextPage.addActionListener(this);
		bt_endPage.addActionListener(this);
		bt_step.addActionListener(this);
		panel.add(bt_import);
		panel.add(label_2);
		panel.add(dbNameText);
		panel.add(tabNameChoice);
		panel.add(bt_clear);
		panel.add(datepick);
		panel.add(separateLabel);
		panel.add(datepick1);
		panel.add(searchButton);
		panel.add(bt_headPage);
		panel.add(bt_prePage);
		panel.add(pageNumText);
		panel.add(bt_nextPage);
		panel.add(bt_endPage);
		panel.add(stepNumText);
		panel.add(bt_step);
		
		/* 数据区域的风格 */
		dataTable.setColumnModel(TableUtils.getColumn(dataTable, tableWidth));
		scrollDataView.setViewportView(dataTable);
		ListSelectionModel cellSelectionModel = dataTable.getSelectionModel();
		cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		cellSelectionModel.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting()) {
					return;
				}
				int row = dataTable.getSelectedRow();
				if (row != -1) {
					String message = dataTable.getValueAt(dataTable.getSelectedRow(),1).toString();
    				List<String> result = null ;
    				if(message!=null&&!"".equals(message)){
    					if(tabName.equals("com")){
    						result = MessageElianUtil.comParase(message.trim()) ;
    					}else if(tabName.equals("net")){
    						result = MessageParseUtil.socketParase(message.trim());
    					}
    					if(!result.isEmpty()){
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

		dataView.setFocusable(false);
		Style def = dataView.getStyledDocument().addStyle(null, null);
		Style normal = dataView.addStyle(SysParamConst.STYLE_NORMAL, def);
		Style s = dataView.addStyle(SysParamConst.STYLE_RED, normal);
		dataView.setParagraphAttributes(normal, true);
		JScrollPane scrollDataViewJson = new JScrollPane();
		scrollDataViewJson.setViewportView(dataView);
		
		// 上下分隔条
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(500);
		// 加入上下分隔条
		MainFrame.mainPanelDbImport.add(splitPane, BorderLayout.CENTER);
		// 上面为来源报文区
		splitPane.setLeftComponent(scrollDataView);
		// 下面为解析后报文
		splitPane.setRightComponent(scrollDataViewJson);
		viewParams.put(SysParamConst.QUERY_PANEL, panel);
		viewParams.put(SysParamConst.PARSE_JSON_VIEW_PANEL, scrollDataViewJson);
		viewParams.put(SysParamConst.SCROLL_DATAVIEW, scrollDataView);
	}

	/**
	 * 数据打印面板
	 * 
	 */
	private void dataShow(String dateText, String text) {

		if (null == text || "".equals(text)) {
			return;
		}

		int rowCount = dataTable.getRowCount();
		Vector<String> newRow = new Vector<String>();
		newRow.add(dateText);
		newRow.add(text);
		tableModel.addRow(newRow);
		dataTable.setModel(tableModel);
		dataTable.setRowHeight(25);
		dataTable.scrollRectToVisible(dataTable.getCellRect(rowCount, 0, true));
	}

	public void data_show_parse(String text) {
		if (null == text || "".equals(text)) {
			return;
		}
		text = MyUtils.data_show_format(text);
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
	
	private class FileTypeFile extends FileFilter{

		public String getDescription() {
			return "*.db";  
		}
		public boolean accept(File file) {  
	        return file.isDirectory() || file.getName().endsWith(".db") ;  // 仅显
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()== bt_headPage) {
			currentPage = 1 ;
		}
		if (e.getSource()== bt_prePage) {
			if (currentPage <= 1) {
				currentPage = 1 ;
			}else{
				currentPage -=  1 ;
			}
		}

		if (e.getSource()== bt_nextPage) {
			if (currentPage < countPage) {
				currentPage +=  1 ;
			} else {
				currentPage = countPage;
				
			}
		}

		if (e.getSource()== bt_endPage) {
			currentPage = countPage ;
		}
		
		if(e.getSource()== bt_import){
			JFileChooser chooser = new JFileChooser(); // 设置选择器
			chooser.setFileFilter(new FileTypeFile());
			int returnVal = chooser.showOpenDialog(bt_import);
			if (returnVal == JFileChooser.APPROVE_OPTION) { // 如果符合文件类型
				filepath = chooser.getSelectedFile().getName(); // 获取绝对路径
				dbNameText.setText(filepath);
				File selectFile = chooser.getSelectedFile().getAbsoluteFile();
				imFileName  = MyUtils.formatDateStr_num()+"_"+filepath ; 
				FileUtils.copyFile(selectFile, new File(imFileName));
				
				String count = DBUtils.queryDataOfCountNum(DBUtils.getDbImportConnection(imFileName),tabName) ;
				if(count!=null){
					countPage = Integer.parseInt(count) ;
				}
			}	
		}
		if(e.getSource()== tabNameChoice){
			if (tabNameChoice.getSelectedItem().equals("COM")) {
				tabName = "com";
			} else if (tabNameChoice.getSelectedItem().equals("SOCKET")) {
				tabName = "net";
			}
			if("".equals(dbNameText.getText().trim())||dbNameText.getText().trim() ==null){
				return ;
			}
			String count = DBUtils.queryDataOfCountNum(DBUtils.getDbImportConnection(imFileName),tabName) ;
			if(count!=null){
				countPage = Integer.parseInt(count) ;
			}
		}
		if(e.getSource()== bt_step){
			int step = Integer.parseInt(stepNumText.getText().trim());
			if(step > countPage){
				currentPage = countPage ;
			}else if(step < 1){
				currentPage = 1 ;
			}else{
				currentPage = step ;
			}
		}
		if("".equals(imFileName)||imFileName ==null){
			return ;
		}
		
		pageNumText.setText(currentPage+"");
		tableModel.setRowCount(0);
		dataTable.setModel(tableModel);
		dataTable.setRowHeight(25);
		List<Map<String, String>> results = DBUtils.queryDatas(DBUtils.getDbImportConnection(imFileName),
				tabName,currentPage);
		if (!results.isEmpty()) {
			for (Map<String, String> data : results) {
				dataShow(data.get("ColDate"), data.get("ColPacket"));
			}
			results = null;
		} else {
			logger.error("读取的文件没有数据");
		}
	}
}
