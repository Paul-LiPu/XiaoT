import javax.swing.UIManager;


public class Main {//�������������
	public static void main(String[] args) throws Exception {//������
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//��ۺ͸о�������
		new Login();//������¼ҳ�棬����ͨ����Ӧʱ����ɹ���
	}
}
