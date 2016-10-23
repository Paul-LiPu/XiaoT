import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;


public class Course implements Serializable{//存储课程数据的类
	String CourseName;//课程名称
	String CourseId;//课程Id在登录网页的时候比较有用
	ArrayList<Message> messages;//课程公告
	ArrayList<MyFile> myFiles;//课程文件
	ArrayList<Homework> homeworks;//课程作业
	int homework,message,file;//未提交的课程作业数，未读公告数，未下载文件数
	//构造函数
	Course(){
		messages=new ArrayList<Message>();
		myFiles=new ArrayList<MyFile>();
		homeworks=new ArrayList<Homework>();
	}
	//数据获取和修改
	void addMessage(Message message){
		this.messages.add(message);
		
	}
	void addMyFile(MyFile myFile){
		this.myFiles.add(myFile);
	}
	void addHomework(Homework homework){
		this.homeworks.add(homework);
	}
	//控制序列化进行重写的函数
	private void writeObject(ObjectOutputStream out) throws Exception{
		out.writeObject(CourseName);
		out.writeObject(CourseId);
		out.writeObject(homework);
		out.writeObject(message);
		out.writeObject(file);
		int sizeM=messages.size();
		int sizeF=myFiles.size();
		int sizeH=homeworks.size();
		out.writeObject(sizeM);
		out.writeObject(sizeF);
		out.writeObject(sizeH);
		int i=0;
		for(i=0;i<sizeM;i++){
			out.writeObject(messages.get(i));
		}
		for(i=0;i<sizeF;i++){
			out.writeObject(myFiles.get(i));
		}
		for(i=0;i<sizeH;i++){
			out.writeObject(homeworks.get(i));
		}
		
	}
	private void readObject(ObjectInputStream in)throws Exception{
		this.CourseName=(String)in.readObject();
		this.CourseId=(String)in.readObject();
		this.homework=(int)in.readObject();
		this.message=(int)in.readObject();
		this.file=(int)in.readObject();
		int sizeM=(int)in.readObject();
		int sizeF=(int)in.readObject();
		int sizeH=(int)in.readObject();
		int i=0;
		ArrayList<Message> messages1=new ArrayList<Message>();
		ArrayList<MyFile> myFiles1=new ArrayList<MyFile>();
		ArrayList<Homework> homeworks1=new ArrayList<Homework>();
		for(i=0;i<sizeM;i++){
			Message MyMessage=new Message();
			MyMessage=(Message)in.readObject();
			messages1.add(MyMessage);
		}
		for(i=0;i<sizeF;i++){
			MyFile MyMyFile=new MyFile();
			MyMyFile=(MyFile)in.readObject();
			myFiles1.add(MyMyFile);
		}
		for(i=0;i<sizeH;i++){
			Homework MyHomework=new Homework();
			MyHomework=(Homework)in.readObject();
			homeworks1.add(MyHomework);
		}
		this.messages=messages1;
		this.myFiles=myFiles1;
		this.homeworks=homeworks1;
	}

}
