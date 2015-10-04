/**
 * 
 */
package com.web;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

/**
 * 原始碼框架
 * @author jeff
 * @date 2015/10/04
 */
public class ViewSourceFrame extends JFrame implements ActionListener {
	/**
	 * default
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel panel1;
	private JPanel panel2;
	private Border border1;
	
	private JButton closebtn;
	private JButton savebtn;
	private JScrollPane jScrollPanel;
	private JTextArea jTextArea1;
	
	private String htmlSource;
	
	/**
	 * Constructor
	 */
	public ViewSourceFrame(String htmlSource) {
		this.initViewFrameObject();
		
		this.htmlSource = htmlSource;
		/*
		 * enableEvents() :
		 * 啟用由傳遞給此元件的指定事件遮罩碼參數所定義的事件。 
		 * 將某個事件型別的偵聽器添加到該元件時，自動啟用該事件型別。 
		 * 此方法只能由 Component 的子類別調用，該子類別需要將指定的事件型別傳遞給 processEvent，而不管是否已註冊了偵聽器。
		 * 
		 * AWTEvent.WINDOW_EVENT_MASK = 用於選擇視窗事件的事件遮罩碼。
		 */
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		
		setSize(600, 500);
		setTitle("原始碼");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		
		contentPane.setLayout(new BorderLayout());
		
		closebtn.addActionListener(this);
		savebtn.addActionListener(this);
		
		jScrollPanel.getViewport().add(jTextArea1, null);
		
		border1 = BorderFactory.createEmptyBorder(4, 4, 4, 4);
		
		panel1.setLayout(new BorderLayout());
		panel1.setBorder(border1);
		panel1.add(jScrollPanel, BorderLayout.CENTER);
		
		contentPane.add(panel1, BorderLayout.CENTER);
		
		panel2.add(savebtn);
		panel2.add(closebtn);
		
		contentPane.add(panel2, BorderLayout.SOUTH);
		
		this.jTextArea1.setEditable(true);
		this.jTextArea1.setText(htmlSource);
		//將游標置於文字的第0個字元上
		this.jTextArea1.setCaretPosition(0);
	}
	
	private void initViewFrameObject() {
		panel1 = new JPanel();
		panel2 = new JPanel();
		closebtn = new JButton("退出");
		savebtn = new JButton("儲存");
		jScrollPanel = new JScrollPane();
		jTextArea1 = new JTextArea();
		
		contentPane = (JPanel)getContentPane();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == closebtn) {
			dispose();
		} else if(e.getSource() == savebtn) {
			JFileChooser fc = new JFileChooser();
			int rtnValue = fc.showSaveDialog(ViewSourceFrame.this);
			System.out.println("showSaveDialog = " + rtnValue);
			File file = fc.getSelectedFile();
			FileWriter fw = null;
			try {
				fw = new FileWriter(file);
				fw.write(jTextArea1.getText());
			} catch (IOException e1) {
				e1.printStackTrace(System.err);
			} finally {
					try {
						if(fw != null)
							fw.close();
					} catch (IOException e1) {
						e1.printStackTrace(System.out);
					}
			}
		}
		
	}
	
}
