import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable{ //��¼�γ̹�����Ϣ����
	String date;//���淢��������
	Date date1;//Date���͵Ĺ��淢��������
	String content;//�������ϸ����
	String speaker;//����ķ�����
	String title;//����ı���
	boolean state;//������Ķ�״̬�����Ķ�Ϊtrue��δ�Ķ�Ϊfalse
	//�հ׹��췽��
	Message(){
	}
	//���������úͻ�ȡ�ķ���
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
	//���л����̸�д�ĺ���
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
