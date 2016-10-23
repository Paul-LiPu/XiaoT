import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Login implements ActionListener {//设置登录页面的类，可以对一些组件的事件起反应
	//在设置图形用户界面时用到的一些参数
	private static final boolean shouldFill = true;
	private static final boolean shouldWeightX = false;
	//面板中的主要组件
	JDialog d;//主面板
	JTextField userid;//用户名的输入区域
	JPasswordField userpass;//用户密码的输入区域
	JButton confirm, concel;//确定和退出的按钮，确定的按钮响应登录，退出的按钮响应关闭程序
	Container dialogPane;//主面板的内容面板
	JRadioButton remember,direct;//选择按钮，分别是记忆密码的按钮，隐藏主要displayBoard的按钮
	//记录数据
	public static String name;//收集输入的用户名
	public static String pass;//收集输入的用户密码
	//参数，分别对应两个选择按钮的状态
	public static boolean rememberPass = false,background=false;
	//程序启动后，UNCHECKTIME秒之后会进行自动更新，之后会10分钟更新一次
	int CHECKTIME=3,UNCHECKTIME=180;
	public Login() throws Exception {
		Data.initialize();//设置数据库表格
		String[] user = Data.verify();//提取存储的用户名密码，如果没有，user[0]为空
		//建立初始状态不同的登录面板
		if (user[0].isEmpty()) {
			showGUI("","",rememberPass,background);
		} else {
			rememberPass=true;
			background=true;
			showGUI(user[0],user[1],rememberPass,background);
			/*d.setVisible(false);
			displayBoard board=new displayBoard(user1,this);
			board.showBoard(true, CHECKTIME, false);*/
		}
	}
	
	void showGUI(String user,String password,boolean rememberpass,boolean background){
		//waitDialog=new JDialog();
		this.d = new JDialog();
		d.setTitle("小T: 请输入用户名和密码");
		this.dialogPane = d.getContentPane();
		this.dialogPane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		if (shouldFill) {
			c.fill = GridBagConstraints.HORIZONTAL;
		}
		if (shouldWeightX) {
			c.weightx = 0.5;
		}
		c.gridx = 0;
		c.gridy = 0;
		this.dialogPane.add(new JLabel("用户名"), c);

		this.userid = new JTextField(20);
		this.userid.setText(user);
		c.gridx = 1;
		c.gridy = 0;
		this.dialogPane.add(this.userid, c);
		c.gridx = 0;
		c.gridy = 1;
		this.dialogPane.add(new JLabel("密  码"), c);
		this.userpass = new JPasswordField(20);
		this.userpass.setText(password);
		c.gridx = 1;
		c.gridy = 1;
		this.dialogPane.add(this.userpass, c);

		remember = new JRadioButton("记住密码");
		c.gridx = 1;
		c.gridy = 2;
		if(rememberpass){
			remember.setSelected(true);
		}
		this.dialogPane.add(remember, c);
		direct = new JRadioButton("直接后台运行");
		c.gridx = 0;
		c.gridy = 2;
		if(background){  //设置直接后台运行按钮的初始选择状态
			direct.setSelected(true);
		}
		this.dialogPane.add(direct, c);

		this.confirm = new JButton("确定");
		this.concel = new JButton("退出");
		c.gridx = 0;
		c.gridy = 3;
		this.dialogPane.add(this.concel, c);
		c.gridx = 1;
		c.gridy = 3;
		this.dialogPane.add(this.confirm, c);
		//为按钮们注册侦听器
		remember.addActionListener(this);
		direct.addActionListener(this);
		this.confirm.addActionListener(this);//
		this.concel.addActionListener(this);//
		this.d.setBounds(0, 0, 300, 200);
		this.d.getRootPane().setDefaultButton(confirm);
		this.d.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {//响应侦听器
		String cmd = e.getActionCommand();//获取来源按钮的注册名称
		if (e.getActionCommand().equals("记住密码")) {
			// System.out.println("呵呵");
			rememberPass = this.remember.isSelected(); //如果点击记住密码按钮，就将这个参数修改为按钮的选择状态
		}
		if (e.getActionCommand().equals("直接后台运行")) {
			// System.out.println("呵呵");
			background=direct.isSelected();//如果点击直接后台运行按钮，就将这个参数修改为按钮的选择状态
		}
		if (cmd.equals("确定")) {//点击确定之后，可以进行登录验证，如果登录验证通过，可以建立展示面板displayBoard,更具选择按钮的选择情况选择展示面板的显示隐藏情况
			Login.name = this.userid.getText();
			Login.pass = new String(this.userpass.getPassword());
			try {
				if(rememberPass){
					Data.insert(Login.name,Login.pass,"");
				}else{
					Data.clear();
				}
				User user=new User(this.name,this.pass);
				displayBoard board=new displayBoard(user,this);
				this.d.dispose();
				//showWaitDialog();
				if (board.login) {
					board.showBoard(!background,UNCHECKTIME);
				}
				else {
					JOptionPane.showMessageDialog(this.d, "用户名或密码错误",
							"错误", JOptionPane.WARNING_MESSAGE);
					// userid.setText("");
					userpass.setText("");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;
		}
		if (cmd.equals("退出")) {//如果点击退出按钮，程序结束
			System.exit(0);
		}
	}
}