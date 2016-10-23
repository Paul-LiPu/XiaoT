import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable{ //记录课程公告信息的类
	String date;//公告发布的日期
	Date date1;//Date类型的公告发布的日期
	String content;//公告的详细内容
	String speaker;//公告的发表人
	String title;//公告的标题
	boolean state;//公告的阅读状态，已阅读为true，未阅读为false
	//空白构造方法
	Message(){
	}
	//类数据设置和获取的方法
	void setContent(String content){
		this.content=content;
	}
	void setSpeaker(String speaker){
		this.speaker=speaker;
	}
	void setState(boolean state){
		this.state=state;
	}
	String getContent(){
		return this.content;
	}
	String getSpeaker(){
		return this.speaker;
	}
	boolean getState(){
		return this.state;
	}
	//序列化过程改写的函数
	private void writeObject(ObjectOutputStream out) throws Exception{
		out.writeObject(date);
		out.writeObject(date1);
		out.writeObject(content);
		out.writeObject(speaker);
		out.writeObject(title);
		out.writeObject(state);
	}
	private void readObject(ObjectInputStream in)throws Exception{
		this.date=(String)in.readObject();
		this.date1=(Date)in.readObject();
		this.content=(String)in.readObject();
		this.speaker=(String)in.readObject();
		this.title=(String)in.readObject();
		this.state=(boolean)in.readObject();
	}

}
