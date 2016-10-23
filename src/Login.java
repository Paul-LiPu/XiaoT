import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Login implements ActionListener {//���õ�¼ҳ����࣬���Զ�һЩ������¼���Ӧ
	//������ͼ���û�����ʱ�õ���һЩ����
	private static final boolean shouldFill = true;
	private static final boolean shouldWeightX = false;
	//����е���Ҫ���
	JDialog d;//�����
	JTextField userid;//�û�������������
	JPasswordField userpass;//�û��������������
	JButton confirm, concel;//ȷ�����˳��İ�ť��ȷ���İ�ť��Ӧ��¼���˳��İ�ť��Ӧ�رճ���
	Container dialogPane;//�������������
	JRadioButton remember,direct;//ѡ��ť���ֱ��Ǽ�������İ�ť��������ҪdisplayBoard�İ�ť
	//��¼����
	public static String name;//�ռ�������û���
	public static String pass;//�ռ�������û�����
	//�������ֱ��Ӧ����ѡ��ť��״̬
	public static boolean rememberPass = false,background=false;
	//����������UNCHECKTIME��֮�������Զ����£�֮���10���Ӹ���һ��
	int CHECKTIME=3,UNCHECKTIME=180;
	public Login() throws Exception {
		Data.initialize();//�������ݿ���
		String[] user = Data.verify();//��ȡ�洢���û������룬���û�У�user[0]Ϊ��
		//������ʼ״̬��ͬ�ĵ�¼���
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
		d.setTitle("СT: �������û���������");
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
		this.dialogPane.add(new JLabel("�û���"), c);

		this.userid = new JTextField(20);
		this.userid.setText(user);
		c.gridx = 1;
		c.gridy = 0;
		this.dialogPane.add(this.userid, c);
		c.gridx = 0;
		c.gridy = 1;
		this.dialogPane.add(new JLabel("��  ��"), c);
		this.userpass = new JPasswordField(20);
		this.userpass.setText(password);
		c.gridx = 1;
		c.gridy = 1;
		this.dialogPane.add(this.userpass, c);

		remember = new JRadioButton("��ס����");
		c.gridx = 1;
		c.gridy = 2;
		if(rememberpass){
			remember.setSelected(true);
		}
		this.dialogPane.add(remember, c);
		direct = new JRadioButton("ֱ�Ӻ�̨����");
		c.gridx = 0;
		c.gridy = 2;
		if(background){  //����ֱ�Ӻ�̨���а�ť�ĳ�ʼѡ��״̬
			direct.setSelected(true);
		}
		this.dialogPane.add(direct, c);

		this.confirm = new JButton("ȷ��");
		this.concel = new JButton("�˳�");
		c.gridx = 0;
		c.gridy = 3;
		this.dialogPane.add(this.concel, c);
		c.gridx = 1;
		c.gridy = 3;
		this.dialogPane.add(this.confirm, c);
		//Ϊ��ť��ע��������
		remember.addActionListener(this);
		direct.addActionListener(this);
		this.confirm.addActionListener(this);//
		this.concel.addActionListener(this);//
		this.d.setBounds(0, 0, 300, 200);
		this.d.getRootPane().setDefaultButton(confirm);
		this.d.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {//��Ӧ������
		String cmd = e.getActionCommand();//��ȡ��Դ��ť��ע������
		if (e.getActionCommand().equals("��ס����")) {
			// System.out.println("�Ǻ�");
			rememberPass = this.remember.isSelected(); //��������ס���밴ť���ͽ���������޸�Ϊ��ť��ѡ��״̬
		}
		if (e.getActionCommand().equals("ֱ�Ӻ�̨����")) {
			// System.out.println("�Ǻ�");
			background=direct.isSelected();//������ֱ�Ӻ�̨���а�ť���ͽ���������޸�Ϊ��ť��ѡ��״̬
		}
		if (cmd.equals("ȷ��")) {//���ȷ��֮�󣬿��Խ��е�¼��֤�������¼��֤ͨ�������Խ���չʾ���displayBoard,����ѡ��ť��ѡ�����ѡ��չʾ������ʾ�������
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
					JOptionPane.showMessageDialog(this.d, "�û������������",
							"����", JOptionPane.WARNING_MESSAGE);
					// userid.setText("");
					userpass.setText("");
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return;
		}
		if (cmd.equals("�˳�")) {//�������˳���ť���������
			System.exit(0);
		}
	}
}