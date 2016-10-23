import javax.swing.UIManager;


public class Main {//存放主函数的类
	public static void main(String[] args) throws Exception {//主函数
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//外观和感觉的设置
		new Login();//建立登录页面，后面通过响应时间完成功能
	}
}
