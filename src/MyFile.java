import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class MyFile implements Serializable{//�洢�μ����ݵ���
	String url;//�μ������ص�ַ
	String fileName;//�μ����ļ���
	boolean state;//�μ�������״̬
	String date;//�μ��ϴ�������
	String size;//�μ��Ĵ�С
	MyFile(){	//�հ׹��췽��
	}
	//�����������ݵĻ�ȡ���޸ķ���
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
	//�����ǿ������л�����������д�ĺ���
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
