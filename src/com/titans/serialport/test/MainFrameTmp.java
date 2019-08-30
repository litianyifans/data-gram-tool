package com.titans.serialport.test;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

public class MainFrameTmp extends JFrame {

	private JTextField textField;

	private JTextField textField_1;

	public MainFrameTmp() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrameTmp.class.getResource("/com/titans/resource/dataGramTool_16.png")));
		setAlwaysOnTop(true);
		setBackground(SystemColor.textHighlight);
		setForeground(SystemColor.textHighlight);
		setTitle("报文解析工具");
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// 1、数据来源面板
		JPanel comPanel = new JPanel();
		comPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u62A5\u6587\u6765\u6E90", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(comPanel, BorderLayout.WEST);
		JButton comButton = new JButton("com");
		JButton netButton = new JButton("socket");
		JButton localButton = new JButton("离线报文");
		GroupLayout glComPanel = new GroupLayout(comPanel);
		glComPanel.setHorizontalGroup(glComPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glComPanel.createSequentialGroup().addGap(5)
						.addGroup(glComPanel.createParallelGroup(Alignment.LEADING).addComponent(comButton)
								.addComponent(netButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE).addComponent(
										localButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
						.addGap(15)));
		glComPanel.setVerticalGroup(glComPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glComPanel.createSequentialGroup().addGap(5).addComponent(comButton).addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(netButton).addPreferredGap(ComponentPlacement.RELATED).addComponent(localButton).addGap(672)));
		glComPanel.linkSize(SwingConstants.HORIZONTAL, new Component[] { comButton, netButton, localButton });
		comPanel.setLayout(glComPanel);
		// 2、主面板(查询条件、报文显示)
		JPanel mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(new BorderLayout(0, 0));
		// 2.1查询面板
		JPanel queryPanel = new JPanel();
		FlowLayout fl_queryPanel = (FlowLayout) queryPanel.getLayout();
		// fl_queryPanel.setAlignment(FlowLayout.LEFT);
		queryPanel.setBorder(new TitledBorder(null, "\u67E5\u8BE2\u6761\u4EF6", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		queryPanel.setBackground(SystemColor.control);
		mainPanel.add(queryPanel, BorderLayout.NORTH);
		JLabel lblNewLabel = new JLabel("时间范围：");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		queryPanel.add(lblNewLabel);
		textField = new JTextField();
		queryPanel.add(textField);
		textField.setColumns(10);
		textField_1 = new JTextField();
		queryPanel.add(textField_1);
		textField_1.setColumns(10);
		JButton btnNewButton_3 = new JButton("查询");
		queryPanel.add(btnNewButton_3);
		JLabel lblNewLabel_1 = new JLabel("规约版本：");
		queryPanel.add(lblNewLabel_1);
		JComboBox comboBox = new JComboBox();
		queryPanel.add(comboBox);
		JButton btnNewButton_4 = new JButton("解析");
		queryPanel.add(btnNewButton_4);
		JButton btnNewButton_5 = new JButton("清空");
		queryPanel.add(btnNewButton_5);
		JButton btnNewButton_6 = new JButton("导出");
		queryPanel.add(btnNewButton_6);
		JButton btnNewButton_7 = new JButton("暂定/继续");
		queryPanel.add(btnNewButton_7);
		// 2.2报文显示面板
		JPanel gramPanel = new JPanel();
		gramPanel.setBackground(Color.WHITE);
		mainPanel.add(gramPanel, BorderLayout.CENTER);
		gramPanel.setLayout(new BorderLayout(0, 0));
		// 上下分隔条
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setOneTouchExpandable(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		gramPanel.add(splitPane);
		splitPane.setDividerLocation(600);
		// 2.2.1来源报文
		JPanel srcGramPanel = new JPanel();
		splitPane.setLeftComponent(srcGramPanel);
		// 2.2.2转换后报文
		JPanel convertPanel = new JPanel();
		splitPane.setRightComponent(convertPanel);
		convertPanel.setLayout(new BorderLayout(0, 0));
		// 参数设置
		JPanel serialPortPanel = new JPanel();
		serialPortPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u53C2\u6570\u8BBE\u7F6E",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		convertPanel.add(serialPortPanel, BorderLayout.WEST);
		JLabel serialPortLabel = new JLabel("串  口：");
		JComboBox baudrateChoice = new JComboBox();
		JLabel baudrateLabel = new JLabel("波特率：");
		JComboBox serialPortChoice = new JComboBox();
		JButton connButton = new JButton("打开/关闭");
		connButton.setVerticalAlignment(SwingConstants.BOTTOM);
		GroupLayout gl_serialPortPanel = new GroupLayout(serialPortPanel);
		gl_serialPortPanel.setHorizontalGroup(gl_serialPortPanel.createParallelGroup(Alignment.LEADING).addGroup(gl_serialPortPanel
				.createSequentialGroup()
				.addGroup(gl_serialPortPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_serialPortPanel.createSequentialGroup().addGap(5).addComponent(serialPortLabel))
						.addComponent(baudrateLabel))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_serialPortPanel.createParallelGroup(Alignment.LEADING).addComponent(baudrateChoice, 0, 106, Short.MAX_VALUE)
						.addComponent(serialPortChoice, 0, 106, Short.MAX_VALUE))
				.addContainerGap()).addGroup(Alignment.TRAILING,
						gl_serialPortPanel.createSequentialGroup().addContainerGap(80, Short.MAX_VALUE).addComponent(connButton)));
		gl_serialPortPanel.setVerticalGroup(gl_serialPortPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_serialPortPanel.createSequentialGroup()
						.addGroup(gl_serialPortPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_serialPortPanel.createSequentialGroup().addGap(8).addComponent(serialPortLabel))
								.addGroup(gl_serialPortPanel.createSequentialGroup().addGap(5).addComponent(baudrateChoice,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGap(23)
						.addGroup(gl_serialPortPanel.createParallelGroup(Alignment.BASELINE).addComponent(baudrateLabel).addComponent(
								serialPortChoice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(connButton)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		serialPortPanel.setLayout(gl_serialPortPanel);
		JPanel converContPanel = new JPanel();
		convertPanel.add(converContPanel);
		converContPanel.setLayout(new CardLayout(0, 0));
		// 报文转换后显示
		JTextPane textPane = new JTextPane();
		textPane.setForeground(SystemColor.textHighlight);
		converContPanel.add(textPane, "name_26153501776376");
	}

	public static void main(String[] args) {
		MainFrameTmp frame = new MainFrameTmp();
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// 通过调用GraphicsEnvironment的getDefaultScreenDevice方法获得当前的屏幕设备了
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		// 全屏设置
		gd.setFullScreenWindow(frame);
	}
}
