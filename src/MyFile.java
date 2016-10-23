import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class MyFile implements Serializable{//存储课件数据的类
	String url;//课件的下载地址
	String fileName;//课件的文件名
	boolean state;//课件的下载状态
	String date;//课件上传的日期
	String size;//课件的大小
	MyFile(){	//空白构造方法
	}
	//下面是类数据的获取和修改方法
	String getURL(){
		return url;
	}
	String getFileName(){
		return fileName;
	}
	boolean getState(){
		return state;
	}
	void setURL(String url){
		this.url=url;
	}
	void setFileName(String fileName){
		this.fileName=fileName;
	}
	void setState(boolean state){
		this.state=state;
	}
	//下面是控制序列化过程所需重写的函数
	private void writeObject(ObjectOutputStream out) throws Exception{
		out.writeObject(url);
		out.writeObject(fileName);
		out.writeObject(state);
		out.writeObject(date);
		out.writeObject(size);
	}
	private void readObject(ObjectInputStream in)throws Exception{
		this.url=(String)in.readObject();
		this.fileName=(String)in.readObject();
		this.state=(boolean)in.readObject();
		this.date=(String)in.readObject();
		this.size=(String)in.readObject();
		
	}

}
