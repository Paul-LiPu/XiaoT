import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;


public class Notifer{//如果检测到更新，可以通过构建此类形成弹窗，并在其中的文本域内描述更新内容
	JFrame f;//主面板
	String WEST=SpringLayout.WEST,NORTH=SpringLayout.NORTH,EAST=SpringLayout.EAST,SOUTH=SpringLayout.SOUTH;//与SpringLayout相关的控制参数
	JTextArea textarea;//文本域
	Notifer(){//构造参数
		f=new JFrame();
		f.setSize(new Dimension(400,210));//设置主面板大小
		Container pane=f.getContentPane();
		//下面在设置文本域的属性
		textarea=new JTextArea("");
		textarea.setEditable(false);
		textarea.setLineWrap(true);
		Dimension size=new Dimension(400,200);
		//将文本域放入滚动条面板
		JScrollPane scroll=new JScrollPane(textarea);
		//设置好文本域和滚动条面板的大小
		textarea.setPreferredSize(size);
		scroll.setPreferredSize(size);
		//下面向主面板添加了部件，并且通过SpringLayout进行布局控制
		SpringLayout layout=new SpringLayout();
		f.setLayout(layout);
		f.add(textarea);
		layout.putConstraint(NORTH, scroll, 5,NORTH, pane);
		layout.putConstraint(WEST, scroll, 3, WEST, pane);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(false);
	}
	

}