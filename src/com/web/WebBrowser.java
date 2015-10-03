/**
 * 
 */
package com.web;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * WebBrowser 主程式<br>
 * Extends JFrame and implements HyperlinkListener,ActionListener
 * @author jeff
 * @date 2015/09/30
 */
public class WebBrowser extends JFrame implements HyperlinkListener, ActionListener {
	/**
	 * serialVersionUID = 1L
	 */
	private static final long serialVersionUID = 1L;
	
	//建立工具欄來顯示位址欄
	private JToolBar bar = null;
	
	//建立網頁顯示介面
	private JTextField jurl = null;
	/**
	 * 可編輯各種內容的文本元件。<br>
	 * text/plain : 純文本 (default)<br>
	 * text/html : HTML 文本<br>
	 * text/rtf : RTF 文本
	 */
	private JEditorPane jEditorPane1 = null;
	private JScrollPane scrollPane = null;
	
	private JFileChooser chooser = null;
	private JFileChooser chooser1 = null;
	private String htmlSource = null;
	
	private JWindow window = null;
	
	private JButton button2 = null;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	//建立功能表欄
	private JMenuBar jMenuBar1 = null;
	//建立功能表組
	private JMenu fileMenu = null;
	//建立功能表項
	private JMenuItem saveAsItem = null;
	private JMenuItem exitItem = null;
	
	private JMenu editMenu = null;
	private JMenuItem backItem = null;
	private JMenuItem forwardItem = null;
	
	private JMenu viewMenu = null;
	private JMenuItem fullscreenItem = null;
	private JMenuItem sourceItem = null;
	private JMenuItem reloadItem = null;
	
	//建立工具欄
	private JToolBar toolBar = null;
	//建立工具欄中的按鈕元件
	private JButton picSave = null;
	private JButton picBack = null;
	private JButton picForward = null;
	private JButton picView = null;
	private JButton picExit = null;
	
	private JLabel label = null;
	private JButton button = null;
	/**
	 * Box.createHorizontalBox()<br>
	 * 創建一個從左到右顯示其元件的 Box。
	 */
	Box address = Box.createHorizontalBox();
	
	//存放歷史網址
	private ArrayList<String> history = null;
	//整數，表示歷史網址的訪問順序
	private int historyIndex;
	
	/**
	 * Constructor<br>
	 * 1.設定title=網頁瀏灠器<br>
	 * 2.各式事件監聽器<br>
	 * 3.按鈕快捷鍵<br>
	 * 4.設置大小<br>
	 * 5.工具欄中新增按鈕元件<br>
	 */
	public WebBrowser() {
		this.initBrowserObject();
		this.addHotKey();
		this.addEvent();
		
		setTitle("網頁瀏灠器");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//將功能表項saveAsItme加入到功能表組fileMenu中
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();	//隔離線
		fileMenu.add(exitItem);
		
		editMenu.add(backItem);
		editMenu.add(forwardItem);
		
		viewMenu.add(fullscreenItem);
		viewMenu.add(sourceItem);
		viewMenu.addSeparator();
		viewMenu.add(reloadItem);
		
		Container contentPane = getContentPane();
		
		//設置scrollPane大小
		scrollPane.setPreferredSize(new Dimension(100, 500));
		
		contentPane.add(scrollPane, BorderLayout.SOUTH);
		
		//工具欄
		toolBar.add(picSave);
		toolBar.addSeparator();
		toolBar.add(picBack);
		toolBar.add(picForward);
		toolBar.addSeparator();
		toolBar.add(picView);
		toolBar.addSeparator();
		
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		//功能表欄
		jMenuBar1.add(fileMenu);
		jMenuBar1.add(editMenu);
		jMenuBar1.add(viewMenu);
		
		setJMenuBar(jMenuBar1);
		
		//Box - Bar(網址列功能)
		address.add(label);
		address.add(jurl);
		address.add(button);
		bar.add(address);
		
		contentPane.add(bar, BorderLayout.CENTER);
	}
	
	/**
	 * 初始化Browser上的物件
	 */
	private void initBrowserObject() {
		//建立工具欄來顯示位址欄
		bar = new JToolBar();
		//建立網頁顯示介面
		jurl = new JTextField(60);
		jEditorPane1 = new JEditorPane();
		scrollPane = new JScrollPane(jEditorPane1);
		
		chooser = new JFileChooser();
		chooser1 = new JFileChooser();
		
		window = new JWindow(WebBrowser.this);
		
		button2 = new JButton("窗口還原");
		//建立功能表欄
		jMenuBar1 = new JMenuBar();
		//建立功能表組
		fileMenu = new JMenu("文件(F)");
		//建立功能表項
		saveAsItem = new JMenuItem("另存為(S)...");
		exitItem = new JMenuItem("退出(Q)");
		
		editMenu = new JMenu("編輯(E)");
		backItem = new JMenuItem("後退(B)");
		forwardItem = new JMenuItem("前進(D)");
		
		viewMenu = new JMenu("視圖(V)");
		fullscreenItem = new JMenuItem("全屏(U)");
		sourceItem = new JMenuItem("查看源碼(C)");
		reloadItem = new JMenuItem("重載(R)");
		
		//建立工具欄
		toolBar = new JToolBar();
		//建立工具欄中的按鈕元件
		picSave = new JButton("另存為");
		picBack = new JButton("後退");
		picForward = new JButton("前進");
		picView = new JButton("檢視源始碼");
		picExit = new JButton("離開");
		
		label = new JLabel("網址");
		button = new JButton("轉向");
		
		history = new ArrayList<String>();
	}
	
	/**
	 * 針對各元件加上特定Event
	 */
	private void addEvent() {
		//為jEditorPane1新增事件偵聽
		jEditorPane1.addHyperlinkListener(this);
		
		//按鈕、Menu事件監聽
		saveAsItem.addActionListener(this);
		exitItem.addActionListener(this);
		backItem.addActionListener(this);
		forwardItem.addActionListener(this);
		fullscreenItem.addActionListener(this);
		sourceItem.addActionListener(this);
		reloadItem.addActionListener(this);
		
		picSave.addActionListener(this);
		picBack.addActionListener(this);
		picForward.addActionListener(this);
		picView.addActionListener(this);
		picExit.addActionListener(this);
		
		button.addActionListener(this);
		jurl.addActionListener(this);
	}
	
	/**
	 * 針對各元件加上熱鍵<br>
	 * ALT + hotKey<br>
	 * CTRL + hotKey
	 */
	private void addHotKey() {
		//建立功能表組 hot key = F
		fileMenu.setMnemonic('F');
		// Exception:setAccelerator() is not defined for JMenu.  Use setMnemonic() instead.
		//fileMenu.setAccelerator(
		//		KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_MASK));
		
		//建立功能表項:另存為(S)/退出(Q)
		saveAsItem.setMnemonic('S');
		//另存為 = Ctrl + S
		saveAsItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		exitItem.setMnemonic('Q');
		exitItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		
		//編輯(E)
		editMenu.setMnemonic('E');
		//editMenu.setAccelerator(
		//		KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		//後退(B)
		backItem.setMnemonic('B');
		backItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		//前進(D)
		forwardItem.setMnemonic('D');
		forwardItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		
		//視圖(V)
		viewMenu.setMnemonic('V');
		//全屏(U)
		fullscreenItem.setMnemonic('U');
		fullscreenItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_MASK));
		//查看源碼(C)
		sourceItem.setMnemonic('C');
		sourceItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		//重載(R)
		reloadItem.setMnemonic('R');
		reloadItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_MASK));
	}
	
	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		System.out.println("HyperLink = " + e.getSource());
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String url = "";
		Object sourceObj = e.getSource();
		System.out.println("Action = " + sourceObj);
		//點擊轉向按鈕
		if(sourceObj == button) {
			this.redirectBtn();
		} //輸入網址後，Enter
		else if(sourceObj == jurl) {
			this.pressEnterUrlTxtField();
		} //另存為...
		else if(sourceObj == picSave || sourceObj == saveAsItem) {
			url = jurl.getText().toString().trim();
			if(url.trim().length() > 0 &&
					!(url.startsWith("http://") || url.startsWith("https://")))
			{
				url = "http://" + url;
			}
			if(url.trim().length() > 0) {
				//保存檔
				saveFile(url);
			} else {
				JOptionPane.showMessageDialog(WebBrowser.this, "請輸入網址", "網頁瀏覽器",
						JOptionPane.ERROR_MESSAGE);
			}
		} //退出
		else if(sourceObj == exitItem || sourceObj == picExit) {
			System.exit(0);
		} //後退 back
		else if(sourceObj == backItem || sourceObj == picBack) {
			historyIndex--;
			if(historyIndex < 0)
				historyIndex = 0;
			//url = jurl.getText();
			//獲得history中本網址之前訪問的網址
			url = history.get(historyIndex);
			if(url == null || url.trim().length() == 0)
				url = jurl.getText();
			//重整jEditorPane1內容
			try {
				jEditorPane1.setPage(url);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			jurl.setText(url);
			jEditorPane1.setEditable(false);
			jEditorPane1.revalidate();
		} //前進 forward
		else if(sourceObj == forwardItem || sourceObj == picForward) {
			historyIndex++;
			if(historyIndex >= history.size())
				historyIndex = history.size() - 1;
			//url = jurl.getText();
			url = history.get(historyIndex);
			if(url == null || url.trim().length() == 0)
				url = jurl.getText();
			//重整jEditorPane1內容
			try {
				jEditorPane1.setPage(url);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			jurl.setText(url);
			jEditorPane1.setEditable(false);
			jEditorPane1.revalidate();
		} //全屏
		else if(sourceObj == fullscreenItem) {
			boolean btnFlag = true;
			//取得螢幕大小
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			
			Container content = window.getContentPane();
			content.add(bar, "North");
			content.add(scrollPane, "Center");
			
			//button2為"全屏"點擊後還原按鈕
			if(btnFlag) {
				bar.add(button2);
			}
			//button2添加事件
			button2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					WebBrowser.this.setEnabled(true);
					
					window.remove(bar);
					window.remove(toolBar);
					window.remove(scrollPane);
					window.setVisible(false);
					
					scrollPane.setPreferredSize(new Dimension(100, 500));
					getContentPane().add(scrollPane, BorderLayout.SOUTH);
					getContentPane().add(bar, BorderLayout.CENTER);
					getContentPane().add(toolBar, BorderLayout.NORTH);
					
					bar.remove(button2);
					pack();
				}
			});
			window.setSize(size);
			window.setVisible(true);
		} //查看原始檔案
		else if(sourceObj == sourceItem || sourceObj == picView) {
			System.out.println("★★★查看原始檔案★★★");
		} //重載
		else if(sourceObj == reloadItem) {
			System.out.println("☆☆☆重載☆☆☆");
		}
	}
	
	/**
	 * 重新導向按鈕
	 */
	private void redirectBtn() {
		String url = jurl.getText();
		//檢查URL
		//url不為空，且以http:// 開頭
		if(url != null && url.trim().length() > 0 &&
				(url.startsWith("http://") || url.startsWith("https://"))) {
			try {
				//jEditorPane1元件顯示url的連結內容
				jEditorPane1.setPage(url);
				//將url的內容新增到ArrayList物件history中
				history.add(url);
				//historyIndex的數值設為history對象的長度-1
				historyIndex = history.size() - 1;
				//將編輯狀態置為非編輯狀態(jEditorPane1.setEditable(false))
				jEditorPane1.setEditable(false);
				//重新佈局
				/*
				 * 推遲的自動佈局
				 * 當屬性值更改，從而影響此元件的大小、位置或內部佈局時，就對此元件自動調用此方法。
				 * 這種自動更新不同於 AWT，因為通常來說，程序不再需要調用 validate 來獲得要更新的 GUI 的內容。
				 */
				jEditorPane1.revalidate();
			} catch(Exception e) {
				//如果連結失敗，則彈出選擇對話視窗方塊"無法打開搜索頁"
				JOptionPane.showMessageDialog(WebBrowser.this, "無法打開搜索頁", "網頁瀏覽器",
						JOptionPane.ERROR_MESSAGE);
			}
		} //url 不為空，但亦不以http:// 開頭
		else if(url.trim().length() > 0 &&
				!(url.startsWith("http://") || url.startsWith("https://"))) {
			//加上http://
			url = "http://" + url;
			try {
				//動作同IF Block
				jEditorPane1.setPage(url);
				history.add(url);
				historyIndex = history.size() - 1;
				jEditorPane1.setEditable(false);
				jEditorPane1.revalidate();
			} catch(Exception e) {
				//如果連結失敗，則彈出選擇對話視窗方塊"無法打開搜索頁"
				JOptionPane.showMessageDialog(WebBrowser.this, "無法打開搜索頁", "網頁瀏覽器",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			//url為空
			JOptionPane.showMessageDialog(WebBrowser.this, "請輸入網址", "網頁瀏覽器",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * 於URL輸入欄中，Press Enter
	 */
	private void pressEnterUrlTxtField() {
		//TODO 動作均同(抽出)
		this.redirectBtn();
	}
	
	/**
	 * 將傳入的URL其內容儲存至指定檔案上
	 * @param url
	 */
	private void saveFile(final String url) {
		final String linesep = System.getProperty("line.separator");
		System.out.println("System.getProperty('line.separator') = " + linesep);
		chooser1.setCurrentDirectory(new File("."));
		chooser1.setDialogType(JFileChooser.SAVE_DIALOG);
		chooser1.setDialogTitle("另存為...");
		//APPROVE_OPTION = 選擇確認（yes、ok）後返回該值。(如果不是yes、ok就return，不往下做)
		//System.out.println("APPROVE_OPTION = " +
		//		(chooser1.showSaveDialog(this) != JFileChooser.APPROVE_OPTION));
		if(chooser1.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		this.repaint();
		//Create new Thread to do Save Action.
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					URL source = new URL(url);
					InputStream in = new BufferedInputStream(source.openStream());
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					
					File fileName = chooser1.getSelectedFile();
					FileWriter out = new FileWriter(fileName);
					BufferedWriter bw = new BufferedWriter(out);
					String line;
					while((line = br.readLine()) != null) {
						bw.write(line);
						bw.newLine();
					}
					bw.flush();
					bw.close();
					out.close();
					
					String dMessage = url + " 已被保存至" + linesep + fileName.getAbsolutePath();
					JOptionPane.showMessageDialog(null, dMessage, "另存為",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "另存動作 Exception",
							JOptionPane.ERROR_MESSAGE);
				} catch (IOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "另存動作 Exception",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		thread.start();
	}
	
	/**
     * 創建並顯示GUI。 出於執行緒安全的考慮，
     * 這個方法在事件調用執行緒中調用。
     */
    private static void createAndShowGUI() {
    	try {
			UIManager.setLookAndFeel(
					/* UIManager.getCrossPlatformLookAndFeelClassName() :
					 * 返回實作預設的跨平臺外觀 -- Java Look and Feel (JLF) -- 的 LookAndFeel 類別的名稱。
					 * 可通過設置 swing.crossplatformlaf 系統屬性覆寫此值。
					 */
					UIManager.getCrossPlatformLookAndFeelClassName()
					//UIManager.getSystemLookAndFeelClassName()
					);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
    	WebBrowser web = new WebBrowser();
    	web.pack();
    	web.setVisible(true);
    }
	
	/**
	 * 生成一個IE物件
	 * @param args
	 */
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
