package com.titans.serialport.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import com.titans.serialport.costant.SysParamConst;
import com.titans.serialport.utils.FileUtils;
import com.titans.serialport.utils.MessageParseUtil;
import com.titans.serialport.utils.MyUtils;
import com.titans.serialport.utils.TableUtils;

public class LocFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -4207334354803859106L;
	private static Logger logger = Logger.getLogger(LocFrame.class);
	public static HashMap<String, Object> viewParams = MainFrame.paramPenelMap.get(SysParamConst.TAB3);
	JButton bt_import = new JButton("导入");
	JButton button_clear = new JButton("清除");
	JComboBox<String> protocolChoice = new JComboBox<String>();
	JTextPane dataView = new JTextPane();
	// 未解析的数据列表
	String[] columnNames = { "帧ID", "数据" };
	int tableWidth[] = { 150, 853 };
	Vector<String> columns = TableUtils.buildTableModel(columnNames);
	Vector<Vector<String>> rowdatas = new Vector<Vector<String>>();
	DefaultTableModel tableModel = new DefaultTableModel(rowdatas, columns);
	JTable dataTable = TableUtils.buildTable(tableModel);
	JScrollPane scrollDataView = new JScrollPane();
	List<Map<String, String>> results  = new ArrayList<Map<String, String>>() ;
	public void initLocComponents() {
		JPanel panel = new JPanel();
		MainFrame.mainPanelLocal.add(panel, BorderLayout.NORTH);
		// 查询面板使用流式布局
		FlowLayout flQueryPanel = (FlowLayout) panel.getLayout();
		flQueryPanel.setAlignment(FlowLayout.LEFT);
		panel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		protocolChoice.addItem("内网通信协议V2.12");
		bt_import.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser(); // 设置选择器
				int returnVal = chooser.showOpenDialog(bt_import);
				if (returnVal == JFileChooser.APPROVE_OPTION) { // 如果符合文件类型
					String filepath = chooser.getSelectedFile().getAbsolutePath(); // 获取绝对路径
					List<String> results = FileUtils.readFile(filepath, MainFrame.conn,false);
					if (!results.isEmpty()) {
						for (String data : results) {
							String[] dataObj = data.split(",") ;
							dataShow(dataObj[0], dataObj[1]);
						}
					}else{
						logger.error("读取的文件没有数据");
					}
				}
			}
		});

		button_clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dataView.setText(null);
			}
		});

		panel.add(bt_import);


		JLabel label_2 = new JLabel("规约：");
		label_2.setForeground(Color.BLACK);
		label_2.setFont(new Font("宋体", Font.PLAIN, 12));
		panel.add(label_2);
		panel.add(protocolChoice);
		panel.add(button_clear);
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
                if(row != -1){
                	String pointerId = dataTable.getValueAt(dataTable.getSelectedRow(),0).toString();
    				String data = dataTable.getValueAt(dataTable.getSelectedRow(),1).toString();
    				String message = ("88 "+MyUtils.toSplitStringHex(pointerId)+data).toUpperCase() ;
    				if(message!=null&&!"".equals(message)){
    					List<String> result = MessageParseUtil.socketParase(message.trim()) ;
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
		MainFrame.mainPanelLocal.add(splitPane, BorderLayout.CENTER);
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
	private void dataShow(String dateText,String text) {
		if(null == text || "".equals(text)){
			return ;
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
	
	
	public void  data_show_parse(String text){
		if(null == text || "".equals(text)){
			return ;
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
	@Override
	public void actionPerformed(ActionEvent e) {
	}
}
