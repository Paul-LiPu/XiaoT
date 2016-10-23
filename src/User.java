import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;


public class User implements Serializable{//��¼�û����ݵ��࣬��Ҫ��¼�γ̹��棬�γ��ļ����γ���ҵ��������
	String userName;//�û���
	String password;//�û�����
	ArrayList<Course> courses;//�γ�������������ʽ����
	String saveDir;//�γ��ļ����صĸ�Ŀ¼��ַ
	User(){//�չ��췽��
	}
	User(String userName, String password){//��Ҫ�õ��Ĺ��췽�������жԿγ���������˳�ʼ��
		this.userName=userName;
		this.password=password;
		//courseDir="";
		courses=new ArrayList<Course>();
		saveDir="";
	}
	//���涼��һЩ��ȡ�����в������޸ĸ����в����ĺ���
	void setUserName(String name){
		this.userName=name;
	}
	void setPassword(String password){
		this.password=password;
	}
	void addCourse(Course course){
		courses.add(course);
	}
	String getUserName(){
		return userName;
	}
	String getPassword(){
		return password;
	}
	ArrayList<Course> getCourses(){
		return courses;
	}
	void clearData(){
		courses=new ArrayList<Course>();
	}
	//��������д��writeObject��readObject��������Ҫ���ڿ������л���ִ�й��̣�����ò�Ʋ��Ǻܳɹ�����ʱ�����ܳɹ�ʹ��
	private void writeObject(ObjectOutputStream out) throws Exception{
		out.writeObject(userName);
		out.writeObject(password);
		out.writeObject(saveDir);
		int size=courses.size();
		out.writeObject(size);
		for(int i=0;i<size;i++){
			out.writeObject(courses.get(i));
		}
		
	}
	private void readObject(ObjectInputStream in)throws Exception{
		this.userName=(String)in.readObject();
		this.password=(String)in.readObject();
		this.saveDir=(String)in.readObject();
		int size=(int)in.readObject();
		ArrayList<Course> courses1=new ArrayList<Course>();
		for(int i=0;i<size;i++){
			Course MyCourse=new Course();
			MyCourse=(Course)in.readObject();
			courses1.add(MyCourse);
		}
		this.courses=courses1;
	}

}
