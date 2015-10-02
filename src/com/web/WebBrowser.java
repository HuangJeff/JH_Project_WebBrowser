/**
 * 
 */
package com.web;

import java.awt.Toolkit;
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
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * WebBrowser 主程式<br>
 * Extends JFrame and implements HyperlinkListener,ActionListener
 * @author jeff
 * @date 2015/09/30
 */
public class WebBrowser extends JFrame {
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
		
		setTitle("網頁瀏灠器");
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		add(bar);
		
		setSize(500, 500);
		
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
		saveAsItem = new JMenuItem("另存為(A)...");
		exitItem = new JMenuItem("退出(I)");
		
		editMenu = new JMenu("編輯(E)");
		backItem = new JMenuItem("後退");
		forwardItem = new JMenuItem("前進");
		
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
    	//web.pack();
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
