/*
 * MainFrame.java
 *
 * Created on 2016.8.19
 */
package com.titans.serialport.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.titans.serialport.costant.SysParamConst;
import com.titans.serialport.test.MainFrameTmp;
import com.titans.serialport.utils.DBUtils;
import com.titans.serialport.utils.MyUtils;

/**
 * 主界面
 * 
 * @author liw
 */
public class MainFrame extends JFrame implements ActionListener {
	
	 private static Logger logger = Logger.getLogger(MainFrame.class);
	/**
	 * 程序界面宽度
	 */
	public static final int WIDTH = 1180;

	/**
	 * 程序界面高度
	 */
	public static final int HEIGHT = 740;

	// 数据库连接
	public static Connection conn = null;

	// 各界面参数容器
	public static Map<String, HashMap<String, Object>> paramPenelMap = new HashMap<String, HashMap<String, Object>>();

	// tab面板
	private JPanel tabPanel = new JPanel();

	// com主面板
	public static JPanel mainPanelCom = new JPanel();

	// socket主面板
	public static JPanel mainPanelSocket = new JPanel();

	// local主面板
	public static JPanel mainPanelLocal = new JPanel();
	
	// dbImport主面板
	public static JPanel mainPanelDbImport = new JPanel();

	JButton comButton = new JButton("COM");

	JButton netButton = new JButton("SOCKET");

	JButton localButton = new JButton("离线报文");
	
	JButton dbImportButton = new JButton("离线DB");

	// 父容器
	public static Container container = null;

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {

			public void run() {
				new MainFrame().setVisible(true);
			}
		});
	}

	/**
	 * 初始化数据库连接
	 * 
	 */
	public MainFrame() {
		// 设置图标
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrameTmp.class.getResource("/com/titans/resource/dataGramTool_16.png")));
		initDataBase();
		initView();
		initComponents();
		initComPanel();
	}

	public void initComPanel() {
		ComFrame comFrame = new ComFrame();
		comFrame.initData();
		comFrame.initComponents();
	}

	public void initLocPanel() {
		LocFrame locFrame = new LocFrame();
		locFrame.initLocComponents();
	}

	public void initNetPanel() {
		NetFrame netFrame = new NetFrame();
		netFrame.initData();
		netFrame.initNetComponents();
		// svn测试
	}
	
	public void initDBimport() {
		DBImportFrame dbImportFrame = new DBImportFrame();
		dbImportFrame.initDBImportComponents();
	}

	public void initDataBase() {
		logger.debug("数据库初始化");
		String dataBaseName = MyUtils.formatDateStr_num();
		DBUtils.GetDbConnect(dataBaseName);
		DBUtils.createTable();
		logger.debug("数据库初始化完毕");
	}

	private void initView() {
		// 关闭程序
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		// 禁止窗口最大化
		// setResizable(true);
		// 设置程序窗口居中显示
		Point p = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		// 还原时原始大小
		setBounds(p.x - WIDTH / 2, p.y - HEIGHT / 2, WIDTH, HEIGHT);
		container = (Container) this.getContentPane();
		setTitle("报文解析工具V1.0");
		// 默认最大化
		setExtendedState(JFrame.MAXIMIZED_BOTH);
	}

	private void initComponents() {
		// 设置串口列表面板样式
		comButton.setActionCommand("COM");
		comButton.setFont(new Font("宋体", Font.BOLD, 14));
		comButton.setToolTipText("通过COM口采集  1、内网CAN【TCU对执行、TCU对监控】数据,2、TCU上平台数据");
		comButton.addActionListener(this);
		netButton.setActionCommand("SOCKET");
		netButton.setFont(new Font("宋体", Font.BOLD, 14));
		netButton.addActionListener(this);
		netButton.setToolTipText("通过TCP/IP方式采集内网CAN【TCU对执行、TCU对监控】数据");
		localButton.setActionCommand("离线报文");
		localButton.setFont(new Font("宋体", Font.BOLD, 14));
		localButton.addActionListener(this);
		localButton.setToolTipText("通过导入【TCU对执行、TCU监控】CSV后缀报文文件");
		dbImportButton.setActionCommand("离线db");
		dbImportButton.setFont(new Font("宋体", Font.BOLD, 14));
		dbImportButton.addActionListener(this);
		dbImportButton.setToolTipText("通过导入db文件,查看数据");
		
		tabPanel.add(comButton);
		tabPanel.add(netButton);
		tabPanel.add(localButton);
		tabPanel.add(dbImportButton);
		tabPanel.setBorder(new TitledBorder(null, "\u62A5\u6587\u6765\u6E90", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// 加入容器
		container.add(tabPanel, BorderLayout.WEST);
		container.add(mainPanelCom, BorderLayout.CENTER);
		mainPanelCom.setLayout(new BorderLayout(0, 0));
		// 使用分组布局
		GroupLayout glComPanel = new GroupLayout(tabPanel);
		glComPanel.setHorizontalGroup(glComPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glComPanel.createSequentialGroup().addGap(5)
						.addGroup(glComPanel.createParallelGroup(Alignment.LEADING).addComponent(comButton)
								.addComponent(netButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE).addComponent(
										localButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE).addComponent(
												dbImportButton, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE))
						.addGap(15)));
		glComPanel.setVerticalGroup(glComPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glComPanel.createSequentialGroup().addGap(5).addComponent(comButton).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(netButton).addPreferredGap(ComponentPlacement.RELATED).addComponent(localButton).addPreferredGap(ComponentPlacement.RELATED).addComponent(dbImportButton).addGap(672)));
		glComPanel.linkSize(SwingConstants.HORIZONTAL, new Component[] { comButton, netButton, localButton,dbImportButton });
		tabPanel.setLayout(glComPanel);
		paramPenelMap.put(SysParamConst.TAB1, new HashMap<String, Object>());
		paramPenelMap.put(SysParamConst.TAB2, new HashMap<String, Object>());
		paramPenelMap.put(SysParamConst.TAB3, new HashMap<String, Object>());
		paramPenelMap.put(SysParamConst.TAB4, new HashMap<String, Object>());
	}

	public void addComs() {
		HashMap<String, Object> tab1 = paramPenelMap.get(SysParamConst.TAB1);
		if (tab1.isEmpty()) {
			initComPanel();
		} else {
			mainPanelCom.updateUI();
			mainPanelCom.setVisible(true);
		}
	}

	public void removeComs() {
		HashMap<String, Object> tab1 = paramPenelMap.get(SysParamConst.TAB1);
		if (!tab1.isEmpty()) {
			mainPanelCom.setVisible(false);
		}
	}

	public void addNet() {
		HashMap<String, Object> tab2 = paramPenelMap.get(SysParamConst.TAB2);
		if (tab2.isEmpty()) {
			container.add(mainPanelSocket, BorderLayout.CENTER);
			mainPanelSocket.setLayout(new BorderLayout(0, 0));
			initNetPanel();
		} else {
			mainPanelSocket.updateUI();
			mainPanelSocket.setVisible(true);
		}
	}

	public void removeNet() {
		HashMap<String, Object> tab2 = paramPenelMap.get(SysParamConst.TAB2);
		if (!tab2.isEmpty()) {
			mainPanelSocket.setVisible(false);
		}
	}

	public void addLoc() {
		HashMap<String, Object> tab3 = paramPenelMap.get(SysParamConst.TAB3);
		if (tab3.isEmpty()) {
			container.add(mainPanelLocal, BorderLayout.CENTER);
			mainPanelLocal.setLayout(new BorderLayout(0, 0));
			initLocPanel();
		} else {
			mainPanelLocal.updateUI();
			mainPanelLocal.setVisible(true);
		}
	}

	public void removeLoc() {
		HashMap<String, Object> tab3 = paramPenelMap.get(SysParamConst.TAB3);
		if (!tab3.isEmpty()) {
			mainPanelLocal.setVisible(false);
		}
	}
	
	public void addDbImport() {
		HashMap<String, Object> tab4 = paramPenelMap.get(SysParamConst.TAB4);
		if (tab4.isEmpty()) {
			container.add(mainPanelDbImport, BorderLayout.CENTER);
			mainPanelDbImport.setLayout(new BorderLayout(0, 0));
			initDBimport();
		} else {
			mainPanelDbImport.updateUI();
			mainPanelDbImport.setVisible(true);
		}
	}

	public void removeDbImport() {
		HashMap<String, Object> tab4 = paramPenelMap.get(SysParamConst.TAB4);
		if (!tab4.isEmpty()) {
			mainPanelDbImport.setVisible(false);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == netButton) {
			removeComs();
			removeLoc();
			removeDbImport();
			addNet();
		} else if (e.getSource() == comButton) {
			removeNet();
			removeLoc();
			removeDbImport();
			addComs();
		} else if (e.getSource() == localButton) {
			removeComs();
			removeNet();
			removeDbImport();
			addLoc();
		}else if (e.getSource() == dbImportButton){
			removeComs();
			removeNet();
			removeLoc();
			addDbImport();
		}
	}
}