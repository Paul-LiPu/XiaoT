import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;


public class Notifer{//�����⵽���£�����ͨ�����������γɵ������������е��ı�����������������
	JFrame f;//�����
	String WEST=SpringLayout.WEST,NORTH=SpringLayout.NORTH,EAST=SpringLayout.EAST,SOUTH=SpringLayout.SOUTH;//��SpringLayout��صĿ��Ʋ���
	JTextArea textarea;//�ı���
	Notifer(){//�������
		f=new JFrame();
		f.setSize(new Dimension(400,210));//����������С
		Container pane=f.getContentPane();
		//�����������ı��������
		textarea=new JTextArea("");
		textarea.setEditable(false);
		textarea.setLineWrap(true);
		Dimension size=new Dimension(400,200);
		//���ı��������������
		JScrollPane scroll=new JScrollPane(textarea);
		//���ú��ı���͹��������Ĵ�С
		textarea.setPreferredSize(size);
		scroll.setPreferredSize(size);
		//���������������˲���������ͨ��SpringLayout���в��ֿ���
		SpringLayout layout=new SpringLayout();
		f.setLayout(layout);
		f.add(textarea);
		layout.putConstraint(NORTH, scroll, 5,NORTH, pane);
		layout.putConstraint(WEST, scroll, 3, WEST, pane);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setVisible(false);
	}
	

}