import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.text.html.HTMLDocument.Iterator;


public class User implements Serializable{//记录用户数据的类，主要记录课程公告，课程文件，课程作业三类数据
	String userName;//用户名
	String password;//用户密码
	ArrayList<Course> courses;//课程数据以数组形式储存
	String saveDir;//课程文件下载的根目录地址
	User(){//空构造方法
	}
	User(String userName, String password){//主要用到的构造方法，其中对课程数组进行了初始化
		this.userName=userName;
		this.password=password;
		//courseDir="";
		courses=new ArrayList<Course>();
		saveDir="";
	}
	//下面都是一些获取该类中参数和修改该类中参数的函数
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
	//下面是重写的writeObject和readObject方法，主要用于控制序列化的执行过程，不过貌似不是很成功，暂时还不能成功使用
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
