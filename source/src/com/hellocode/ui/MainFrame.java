/*
 * MainFrame.java
 *
 * Created on __DATE__, __TIME__
 */

package com.hellocode.ui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.hellocode.main.PodCast;
import com.hellocode.model.JDomPodCastURL;
import com.hellocode.model.MediaItem;
import com.hellocode.service.DownLoad;
import com.hellocode.service.FileSyn;
import com.hellocode.service.RunTime;
import com.hellocode.ui.mead.MeadProgress;
import com.hellocode.util.Util;
import com.hellocode.util.XML2JavaUtil;

/**
 * 
 * @author __USER__
 */
public class MainFrame extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	/** Creates new form MainFrame */
	public MainFrame() {
		initComponents();
		try {
			this.init();
		} catch (Exception e) {
			RunTime.resetAll();
			// TODO warning here...
			this.init();
		}
	}

	private DefaultTreeModel model;
	public MeadProgress progress;
	public MeadProgress copygress;

	private void init() {
		this.lb_msg.setFont(new java.awt.Font(RunTime.font_name, 1, 14));
		this.lb_info.setFont(new java.awt.Font(RunTime.font_name, 1, 14));
		this.ck_use_proxy.setFont(new java.awt.Font(RunTime.font_name, 1, 14));

		this.progress = new MeadProgress(1, 10, 10, 20, 300, "没有下载任务");
		jPanel1.add(this.progress,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, -1,
						-1));

		this.progress.setDone("没有下载任务");
		this.progress.setVisible(true);

		this.copygress = new MeadProgress(1, 10, 10, 20, 400, "没有下载任务");
		jPanel3.add(copygress,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 150, -1,
						-1));
		this.copygress.setDone("没有文件拷贝");
		this.copygress.setVisible(true);

		this.jTree1.setRootVisible(false);
		// this.jTree1.setToolTipText("10new");
		this.jTree1.setExpandsSelectedPaths(true);
		TreeNode root = recoverUI();
		model = new DefaultTreeModel(root);

		this.jTree1.setModel(model);
		this.jTree1.setEditable(true);
		this.expandAll(this.jTree1, new TreePath(this.root), true);

		this.txt_main_disk.setText(RunTime.CONFIG.disk_main);
		this.list_disk.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = RunTime.CONFIG.disk_other
					.toArray(new String[RunTime.CONFIG.disk_other.size()]);

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		this.list_disk.updateUI();

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {

				// System.exit(0);
				Util.print("OUT...");
				int a = 0;
				if (RunTime.synchronizing || DownLoad.job_count != 0) {
					a += JOptionPane.showConfirmDialog(null,
							"文件下载或正在同步，确定关闭吗？", "information",
							JOptionPane.YES_NO_OPTION);
					if (a == 0) {
						for (Thread t : DownLoad.threads) {
							if (t.isAlive()) {
								t.interrupt();
							}
						}
						RunTime.destroy();
						System.exit(0); // 关闭
					}
				} else {
					RunTime.destroy();
					System.exit(0);
				}
				// if (DownLoad.job_count != 0) {
				// a += JOptionPane.showConfirmDialog(null, "文件正在同步，确定关闭吗？",
				// "information", JOptionPane.YES_NO_OPTION);
				// if (a == 0) {
				// System.exit(0); // 关闭
				// }
				// }

			}
		}

		);

	}

	/**
	 * 完全展开或关闭一个树,用于递规执行
	 * 
	 * @param tree
	 *            JTree
	 * @param parent
	 *            父节点
	 * @param expand
	 *            为true则表示展开树,否则为关闭整棵树
	 */
	private void expandAll(JTree tree, TreePath parent, boolean expand) {
		// Traverse children
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}

		// Expansion or collapse must be done bottom-up
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jTabbedPane1 = new javax.swing.JTabbedPane();
		jPanel1 = new javax.swing.JPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree();
		btn_add_au = new javax.swing.JButton();
		btn_refresh_all = new javax.swing.JButton();
		jScrollPane3 = new javax.swing.JScrollPane();
		detail = new javax.swing.JTable();
		lb_info = new javax.swing.JLabel();
		btn_download = new javax.swing.JButton();
		ck_select_all = new javax.swing.JCheckBox();
		jPanel3 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		txt_main_disk = new javax.swing.JTextPane();
		jLabel2 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jButton1 = new javax.swing.JButton();
		jButton2 = new javax.swing.JButton();
		jScrollPane4 = new javax.swing.JScrollPane();
		list_disk = new javax.swing.JList();
		jButton3 = new javax.swing.JButton();
		jButton4 = new javax.swing.JButton();
		ck_down_load = new javax.swing.JCheckBox();
		jButton5 = new javax.swing.JButton();
		ck_use_proxy = new javax.swing.JCheckBox();
		lb_msg = new javax.swing.JLabel();
		tx_proxy_user = new javax.swing.JTextField();
		tx_proxy_ip = new javax.swing.JTextField();
		tx_proxy_pswd = new javax.swing.JTextField();
		tx_proxy_port = new javax.swing.JTextField();
		lb_msg1 = new javax.swing.JLabel();
		lb_msg2 = new javax.swing.JLabel();
		lb_msg3 = new javax.swing.JLabel();
		lb_msg4 = new javax.swing.JLabel();
		ck_auto_check_disk1 = new javax.swing.JCheckBox();
		lb_msg5 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jTabbedPane1.setDoubleBuffered(true);

		jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jTree1.setToolTipText("right click to refresh or delete");
		jTree1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				jTree1MouseReleased(evt);
			}
		});
		jTree1
				.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
					public void valueChanged(
							javax.swing.event.TreeSelectionEvent evt) {
						jTree1ValueChanged(evt);
					}
				});
		jScrollPane2.setViewportView(jTree1);

		jPanel1.add(jScrollPane2,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 140,
						340));

		btn_add_au.setFont(new java.awt.Font("Tahoma", 1, 11));
		btn_add_au.setForeground(new java.awt.Color(51, 102, 255));
		btn_add_au.setText("Add Feed");
		btn_add_au.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_add_auActionPerformed(evt);
			}
		});
		jPanel1.add(btn_add_au,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 120,
						30));

		btn_refresh_all.setFont(new java.awt.Font("Tahoma", 1, 11));
		btn_refresh_all.setForeground(new java.awt.Color(51, 102, 255));
		btn_refresh_all.setText("Refresh Feed");
		btn_refresh_all.setToolTipText("refresh all feeds");
		btn_refresh_all.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_refresh_allActionPerformed(evt);
			}
		});
		jPanel1.add(btn_refresh_all,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 130,
						30));

		detail.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {
				{ null, null, null, null }, { null, null, null, null },
				{ null, null, null, null }, { null, null, null, null } },
				new String[] { "title", "time duration", "subtitle",
						"description" }));
		detail.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				detailMousePressed(evt);
			}
		});
		jScrollPane3.setViewportView(detail);

		jPanel1.add(jScrollPane3,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, 610,
						310));

		lb_info.setFont(new java.awt.Font("Tahoma", 1, 11));
		lb_info.setForeground(new java.awt.Color(204, 0, 0));
		lb_info
				.setText("Welcome! Add your feed, refresh, select & download media....   Enjoy it!");
		jPanel1.add(lb_info, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				260, 50, 530, 30));

		btn_download.setFont(new java.awt.Font("Tahoma", 1, 12));
		btn_download.setForeground(new java.awt.Color(51, 51, 255));
		btn_download.setText("DownLoad Item");
		btn_download.setToolTipText("download the selected items");
		btn_download.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_downloadActionPerformed(evt);
			}
		});
		jPanel1.add(btn_download,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 150,
						30));

		ck_select_all.setFont(new java.awt.Font("Tahoma", 1, 11));
		ck_select_all.setForeground(new java.awt.Color(51, 51, 255));
		ck_select_all.setText("Select All");
		ck_select_all.setToolTipText("select them in table");
		ck_select_all.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ck_select_allActionPerformed(evt);
			}
		});
		jPanel1.add(ck_select_all,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, -1,
						30));

		jTabbedPane1.addTab("Pod Cast", jPanel1);

		jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jScrollPane1.setViewportView(txt_main_disk);

		jPanel3.add(jScrollPane1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 440,
						70));

		jLabel2.setText("Other Disk");
		jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				10, 200, -1, 20));

		jLabel4.setText("Main Disk");
		jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				20, 70, 60, 20));

		jButton1.setFont(new java.awt.Font("Tahoma", 1, 11));
		jButton1.setForeground(new java.awt.Color(51, 102, 255));
		jButton1.setText("Apply Now!");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jPanel3.add(jButton1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 353,
						110, 30));

		jButton2.setFont(new java.awt.Font("Tahoma", 1, 14));
		jButton2.setForeground(new java.awt.Color(51, 51, 255));
		jButton2.setText("Synchronization");
		jButton2.setToolTipText("copy files");
		jButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton2ActionPerformed(evt);
			}
		});
		jPanel3.add(jButton2,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 13, 170,
						40));

		list_disk.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
					"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane4.setViewportView(list_disk);

		jPanel3.add(jScrollPane4,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 450,
						90));

		jButton3.setFont(new java.awt.Font("Tahoma", 1, 11));
		jButton3.setForeground(new java.awt.Color(51, 102, 255));
		jButton3.setText("Select File");
		jButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton3ActionPerformed(evt);
			}
		});
		jPanel3.add(jButton3,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 110,
						-1));

		jButton4.setFont(new java.awt.Font("Tahoma", 1, 11));
		jButton4.setForeground(new java.awt.Color(51, 102, 255));
		jButton4.setText("Add File");
		jButton4.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton4ActionPerformed(evt);
			}
		});
		jPanel3.add(jButton4,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 200,
						110, -1));

		ck_down_load.setSelected(true);
		ck_down_load.setText("Auto Down Load Media When Startup!(useless now)");
		jPanel3.add(ck_down_load,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 310, -1,
						30));

		jButton5.setFont(new java.awt.Font("Tahoma", 1, 11));
		jButton5.setForeground(new java.awt.Color(51, 102, 255));
		jButton5.setText("Delete");
		jButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton5ActionPerformed(evt);
			}
		});
		jPanel3.add(jButton5,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230,
						110, -1));

		ck_use_proxy.setFont(new java.awt.Font("Tahoma", 1, 11));
		ck_use_proxy.setForeground(new java.awt.Color(255, 0, 51));
		ck_use_proxy.setSelected(true);
		ck_use_proxy.setText("Use Proxy");
		ck_use_proxy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ck_use_proxyActionPerformed(evt);
			}
		});
		jPanel3.add(ck_use_proxy,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 420,
						120, 30));

		lb_msg.setFont(new java.awt.Font("Tahoma", 1, 14));
		lb_msg.setForeground(new java.awt.Color(153, 0, 51));
		lb_msg.setText("Select other protable device, then sync them...");
		jPanel3.add(lb_msg, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				320, 20, 250, 30));

		tx_proxy_user.setText("                           ");
		tx_proxy_user.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				tx_proxy_userActionPerformed(evt);
			}
		});
		jPanel3.add(tx_proxy_user,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 390, 80,
						-1));

		tx_proxy_ip.setText("127.0.0.1");
		jPanel3.add(tx_proxy_ip,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 80,
						-1));

		tx_proxy_pswd.setText("           ");
		jPanel3.add(tx_proxy_pswd,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 390, 70,
						-1));

		tx_proxy_port.setText("7070");
		jPanel3.add(tx_proxy_port,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 360, 70,
						-1));

		lb_msg1.setText("Proxy Port");
		jPanel3.add(lb_msg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				260, 360, 60, 30));

		lb_msg2.setText("UserName");
		jPanel3.add(lb_msg2, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				70, 390, 60, 30));

		lb_msg3.setFont(new java.awt.Font("Tahoma", 1, 12));
		lb_msg3.setForeground(new java.awt.Color(0, 0, 255));
		lb_msg3.setText("Hello PodCast @2010  laiqinyi@gmail.com");
		jPanel3.add(lb_msg3, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				430, 410, 350, 30));

		lb_msg4.setText("Proxy IP");
		jPanel3.add(lb_msg4, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				80, 360, 60, 30));

		ck_auto_check_disk1.setSelected(true);
		ck_auto_check_disk1.setText("Auto Check Plug-in Disk(not work now)");
		jPanel3.add(ck_auto_check_disk1,
				new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1,
						30));

		lb_msg5.setText("Password");
		jPanel3.add(lb_msg5, new org.netbeans.lib.awtextra.AbsoluteConstraints(
				260, 390, 50, 30));

		jTabbedPane1.addTab("Sychronize", jPanel3);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addContainerGap().addComponent(
						jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE,
						818, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(jTabbedPane1,
						javax.swing.GroupLayout.PREFERRED_SIZE, 482,
						javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jTree1MouseReleased(java.awt.event.MouseEvent e) {
		// 判断是否为右键
		if (e.isPopupTrigger()) {
			JTree tree = (JTree) e.getComponent();
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			final DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.jTree1
					.getLastSelectedPathComponent();

			if (node == null) {
				this.lb_info.setText("未选择tree上的节点node");
				return;
			}
			// 选中该节点
			tree.setSelectionPath(path);

			// 判断是否为根节点
			if (path.getParentPath() != null) {
				JMenuItem del = new JMenuItem("删除");
				JMenuItem fresh = new JMenuItem("刷新");

				JPopupMenu popupMenu1;
				popupMenu1 = new JPopupMenu();
				popupMenu1.add(fresh);
				popupMenu1.add(del);
				del.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						MainFrame.this.delFeed();
					}
				});
				fresh.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						RunTime.refreshOne(node.toString());
					}
				});
				// 弹出右键菜单
				popupMenu1.show(tree, e.getX(), e.getY());
			}
		}
	}

	private void delFeed() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.jTree1
				.getLastSelectedPathComponent();

		if (node == null) {
			this.lb_info.setText("未选择tree上的节点node");
			return;
		}

		int a = JOptionPane.showConfirmDialog(null, node.toString()
				+ "  确定 删除吗？", "information", JOptionPane.YES_NO_OPTION);
		if (a == 0) {
			if (node.getParent() != null && node.getParent() != this.root)
				this.model.removeNodeFromParent(node);

			JDomPodCastURL url = RunTime.findFeedByName(node.toString());
			RunTime.CONFIG.feed_au.remove(url);
			RunTime.selectedFileName = "";
		}

		return;
	}

	private void ck_select_allActionPerformed(java.awt.event.ActionEvent evt) {
		if (RunTime.selectedFileName.equalsIgnoreCase("")
				|| RunTime.selectedFileName == null) {
			return;
		}
		if (this.ck_select_all.isSelected()) {
			for (int i = 0; i < this.detail.getRowCount(); i++) {
				JCheckBox bool = (JCheckBox) this.detail.getModel().getValueAt(
						i, 0);
				bool.setSelected(true);
			}
		} else {
			for (int i = 0; i < this.detail.getRowCount(); i++) {
				JCheckBox bool = (JCheckBox) this.detail.getModel().getValueAt(
						i, 0);
				bool.setSelected(false);
			}
		}
		this.detail.updateUI();
	}

	protected void tx_proxy_userActionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub

	}

	void ck_use_proxyActionPerformed(java.awt.event.ActionEvent evt) {
		if (this.ck_use_proxy.isSelected()) {
			RunTime.CONFIG.proxy_host = this.tx_proxy_ip.getText();
			RunTime.CONFIG.proxy_port = this.tx_proxy_port.getText();
			RunTime.CONFIG.proxy_name = this.tx_proxy_user.getText();
			RunTime.CONFIG.proxy_pswd = this.tx_proxy_pswd.getText();
			RunTime.CONFIG.setProxy();
			this.testProxyUI();
		} else {
			this.lb_msg.setText("取消代理设置！");
			this.ck_use_proxy.setText("没有使用代理");
			RunTime.CONFIG.removeProxy();
			this.testProxyUI();
		}

	}

	void testProxyUI() {

		if (RunTime.CONFIG.proxy_enabel && RunTime.CONFIG.testProxy()) {
			this.lb_msg.setText("代理设置成功！");
			this.ck_use_proxy.setText("正在使用代理");
		} else {
			this.lb_msg.setText("代理设置失败！");
			RunTime.CONFIG.removeProxy();
			this.ck_use_proxy.setSelected(false);
			this.ck_use_proxy.setText("没有使用代理");
		}
	}

	private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void detailMousePressed(MouseEvent evt) {
		// TODO Auto-generated method stub

	}

	// download file
	private void btn_downloadActionPerformed(java.awt.event.ActionEvent evt) {
		// ThreadLocal<ArrayList<String>> files = new
		// ThreadLocal<ArrayList<String>>();
		ArrayList<String> files = new ArrayList<String>();
		try {
			String tmp = "TMP";
			for (int i = 0; i < this.detail.getRowCount(); i++) {
				JCheckBox bool = (JCheckBox) this.detail.getModel().getValueAt(
						i, 0);
				if (bool.isSelected()) {

					tmp += this.detail.getModel().getValueAt(i, 1).toString();
					Util.print(bool.getText() + " ==bool:"
							+ bool.isSelected());
					files.add(bool.getText());
				}
			}
			if (files.size() == 0) {
				PodCast.main.progress.setDone("没选择文件");
				return;
			}
			PodCast.main.progress.setDoing(0);
			// PodCast.main.lb_info.setText("正在下载,请稍候...");
		} catch (Exception e) {
			PodCast.main.lb_info.setText("你没选择文件，或者选择左边树上的feed");
			return;
		}
		new DownLoad().Process(files, RunTime.selectedFileName);
		Util.print("downloading...");
	}

	private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
		JFileChooser fDialog = new JFileChooser();
		fDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fDialog.showOpenDialog(this);
		String path = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			path = fDialog.getSelectedFile().getAbsolutePath();
			Util.print(path + "mead");
		}
		// Util.print(path+"mead");
		// this.txt_main_disk.setText(path);
		if (path.equalsIgnoreCase("") || path == null) {

		} else {
			RunTime.CONFIG.disk_other.add(path);
		}
		this.list_disk.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = RunTime.CONFIG.disk_other
					.toArray(new String[RunTime.CONFIG.disk_other.size()]);

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		this.list_disk.updateUI();

	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {
		JFileChooser fDialog = new JFileChooser();
		fDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = fDialog.showOpenDialog(this);
		String path = RunTime.CONFIG.disk_main;
		if (result == JFileChooser.APPROVE_OPTION) {
			path = fDialog.getSelectedFile().getAbsolutePath();
			Util.print(path + "  ##");
		}
		// Util.print(path+"mead");
		this.txt_main_disk.setText(path);
		RunTime.CONFIG.disk_main = path;
	}

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
		if (DownLoad.job_count != 0) {
			this.lb_msg.setText("下载未结束，无法同步");
			return;
		}
		if (RunTime.CONFIG.disk_other.size() == 0) {
			this.lb_msg.setText("没有添加其它文件夹，无法同步！请添加other disk");
			return;
		}
		if (RunTime.synchronizing) {
			this.lb_msg.setText("线程冲突，无法同步文件");
			return;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				RunTime.synchronizing = true;
				try {
					MainFrame.this.lb_msg.setText("正在同步文件...");
					FileSyn file = new FileSyn();
					file.Process();
					PodCast.main.copygress.setDone("全部文件同步完成");
					MainFrame.this.lb_msg.setText("文件同步已经完成");
				} catch (Exception e) {
					RunTime.synchronizing = false;
				} finally {
					RunTime.synchronizing = false;
				}
				RunTime.synchronizing = false;
			}
		}).start();

	}

	private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
		if (this.list_disk.getSelectedIndex() < 0
				|| this.list_disk.getSelectedValue() == null
				|| RunTime.synchronizing) {
			return;
		}
		RunTime.CONFIG.disk_other.remove(this.list_disk.getSelectedValue());
		this.list_disk.setModel(new javax.swing.AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = RunTime.CONFIG.disk_other
					.toArray(new String[RunTime.CONFIG.disk_other.size()]);

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		this.list_disk.updateUI();

	}

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		try {
			XML2JavaUtil.java2XML("mead.xml", RunTime.CONFIG);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	// refresh tabel
	private void jTree1ValueChanged(javax.swing.event.TreeSelectionEvent evt) {
		this.ck_select_all.setSelected(false);
		if (RunTime.refreshing) {
			this.lb_info.setText("正在刷新feed，不要急哦");
		}
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) this.jTree1
				.getLastSelectedPathComponent();
		if (node == null || node == this.audio) {
			return;
		}
		RunTime.selectedFileName = node.toString();
		this.updateTable();
	}

	private void updateTable() {

		JDomPodCastURL pod = RunTime.findFeedByName(RunTime.selectedFileName);
		if (pod == null) {
			return;
		}

		DefaultTableModel model = new javax.swing.table.DefaultTableModel();
		model.setColumnCount(7);
		model.setColumnIdentifiers(new String[] { "Select", "Title", "Author",
				"PubTime", "Duration", "Size", "TYPE" });
		for (MediaItem m : pod.getMedias()) {
			model.addRow(new Object[] { new JCheckBox(m.getENCLOSURE_URL()),
					m.getTitle(), m.getAuthor(), m.getPubData(),
					m.getDuration(), Util.transSize(m.getENCLOSURE_Length()),
					m.getENCLOSURE_TYPE() });
		}

		this.detail.setModel(model);
		this.detail.getColumn("Select").setCellEditor(
				new CheckButtonEditor(new JCheckBox()));
		this.detail.getColumn("Select").setCellRenderer(new CheckBoxRenderer());
		this.detail.setEnabled(true);
		this.detail.addMouseListener(new MouseAdapter() {
		});
		this.detail.updateUI();

		this.lb_info.setText("最新的feed媒体!");
	}

	private class CheckBoxRenderer implements TableCellRenderer {

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (value == null)
				return null;
			return (Component) value;
		}

	}

	private class CheckButtonEditor extends DefaultCellEditor implements
			ItemListener {
		private static final long serialVersionUID = 1L;
		private JCheckBox button;

		public CheckButtonEditor(JCheckBox checkBox) {
			super(checkBox);
		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			if (value == null)
				return null;
			button = (JCheckBox) value;
			button.addItemListener(this);
			return (Component) value;
		}

		public Object getCellEditorValue() {
			button.removeItemListener(this);
			return button;
		}

		public void itemStateChanged(ItemEvent e) {
			super.fireEditingStopped();
		}
	}

	private void btn_refresh_allActionPerformed(java.awt.event.ActionEvent evt) {
		this.lb_info.setText("正在刷新feeds");
		RunTime.refreshAll();
		this.updateTable();
	}

	private void btn_add_auActionPerformed(java.awt.event.ActionEvent evt) {

		String label = "";
		String url = "";
		String comment = "";
		JTextField tf1 = new JTextField(10);
		JTextField tf2 = new JTextField(10);
		JTextArea ta = new JTextArea(5, 10);
		JScrollPane sp = new JScrollPane(ta);

		String v_url = "http://podcasts.voanews.com/podcastxml_local.cfm?id=1491";
		String v_name = "voa5min";
		if (!RunTime.CONFIG.first_time_use) {
			v_url = "";
			v_name = "";
		}
		tf1.setText(v_name);

		tf2.setText(v_url);

		int r = JOptionPane.showConfirmDialog(null, new Object[] {
				"Feed Name: ", tf1, "Feed URL: ", tf2, "Comment: ", sp },
				"Add New Feed", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE);

		if (r == JOptionPane.OK_OPTION) {
			label = tf1.getText();
			url = tf2.getText();
			comment = ta.getText();
		} else {
			return;
		}
		//
		JDomPodCastURL feed = new JDomPodCastURL(url);
		feed.setName(label);
		feed.setCommnet(comment);
		if (RunTime.findFeedByName(label) != null) {
			this.lb_info.setText("重复的feed名称，请修改");
			return;
		}
		RunTime.CONFIG.feed_au.add(feed);
		this.lb_info.setText("成功添加feed,并且完成了更新");
		//

		DefaultMutableTreeNode node = new DefaultMutableTreeNode(feed.getName());

		// node.setUserObject(feed);
		this.model.insertNodeInto(node, this.audio, this.audio.getChildCount());

		this.expandAll(this.jTree1, new TreePath(this.root), true);
		feed.reFreshURL();
		Util.print("已经更新Feed");
	}

	private DefaultMutableTreeNode root = new DefaultMutableTreeNode("ALL ");
	private DefaultMutableTreeNode audio = new DefaultMutableTreeNode("Audio ");
	private DefaultMutableTreeNode tv = new DefaultMutableTreeNode("TV ");

	private TreeNode recoverUI() {

		DefaultMutableTreeNode s1 = new DefaultMutableTreeNode("simple1 ");
		for (JDomPodCastURL pod : RunTime.CONFIG.feed_au) {
			s1 = new DefaultMutableTreeNode(pod.getName());
			audio.add(s1);
		}
		s1 = new DefaultMutableTreeNode("simple2 ");
		tv.add(s1);
		root.add(audio);
		// root.add(tv);
		if (RunTime.CONFIG.proxy_enabel) {
			this.ck_use_proxy.setSelected(true);
			this.tx_proxy_ip.setText(RunTime.CONFIG.proxy_host);
			this.tx_proxy_port.setText(RunTime.CONFIG.proxy_port);
			this.tx_proxy_user.setText(RunTime.CONFIG.proxy_name);
			this.tx_proxy_pswd.setText(RunTime.CONFIG.proxy_pswd);
			RunTime.CONFIG.setProxy();
			this.testProxyUI();
		} else {
			this.ck_use_proxy.setSelected(false);
			this.ck_use_proxy.setText("未使用代理");
		}
		return root;
	}

	public void windowClosing(WindowEvent e) {

	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton btn_add_au;
	private javax.swing.JButton btn_download;
	private javax.swing.JButton btn_refresh_all;
	private javax.swing.JCheckBox ck_auto_check_disk1;
	private javax.swing.JCheckBox ck_down_load;
	private javax.swing.JCheckBox ck_select_all;
	private javax.swing.JCheckBox ck_use_proxy;
	private javax.swing.JTable detail;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JButton jButton3;
	private javax.swing.JButton jButton4;
	private javax.swing.JButton jButton5;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JScrollPane jScrollPane4;
	private javax.swing.JTabbedPane jTabbedPane1;
	private javax.swing.JTree jTree1;
	public javax.swing.JLabel lb_info;
	public javax.swing.JLabel lb_msg;
	public javax.swing.JLabel lb_msg1;
	public javax.swing.JLabel lb_msg2;
	public javax.swing.JLabel lb_msg3;
	public javax.swing.JLabel lb_msg4;
	public javax.swing.JLabel lb_msg5;
	private javax.swing.JList list_disk;
	private javax.swing.JTextField tx_proxy_ip;
	private javax.swing.JTextField tx_proxy_port;
	private javax.swing.JTextField tx_proxy_pswd;
	private javax.swing.JTextField tx_proxy_user;
	private javax.swing.JTextPane txt_main_disk;
	// End of variables declaration//GEN-END:variables

}