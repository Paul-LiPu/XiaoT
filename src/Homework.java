import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Homework implements Serializable{//��¼�γ���ҵ����
	String title;//�γ���ҵ�ı���
	String comment;//�γ���ҵ�ľ���Ҫ��
	String deadline;//�γ���ҵ�Ľ�ֹ����
	Date deadline1;//�γ���ҵ�Ľ�ֹ����
	boolean state;//�γ���ҵ���ύ״̬���Ѿ��ύΪtrue,����Ϊfalse
	//���ݵ��޸ĺͻ�ȡ
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
	//Ϊ�������л���д�ĺ�����
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
