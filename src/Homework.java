import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Homework implements Serializable{//记录课程作业的类
	String title;//课程作业的标题
	String comment;//课程作业的具体要求
	String deadline;//课程作业的截止日期
	Date deadline1;//课程作业的截止日期
	boolean state;//课程作业的提交状态，已经提交为true,否则为false
	//数据的修改和获取
	void setTitle(String title){
		this.title=title;
	}
	void setComment(String comment){
		this.comment=comment;
	}
	void setState(boolean state){
		this.state=state;
	}
	String getTitle(){
		return this.title;
	}
	String getComment(){
		return this.comment;
	}
	boolean getState(){
		return this.state;
	}
	//为控制序列化重写的函数；
	private void writeObject(ObjectOutputStream out) throws Exception{
		out.writeObject(title);
		out.writeObject(comment);
		out.writeObject(deadline);
		out.writeObject(deadline1);
		out.writeObject(state);
	}
	private void readObject(ObjectInputStream in)throws Exception{
		this.title=(String)in.readObject();
		this.comment=(String)in.readObject();
		this.deadline=(String)in.readObject();
		this.deadline1=(Date)in.readObject();
		this.state=(boolean)in.readObject();
	}

}
