
public class Capturer implements Runnable{//���֮����Ϊ�˶��̵߳�ץȡ��ҳ���ݣ���û�дﵽʹ��Ŀ�ģ���ʱ��������޹ء�
	htmlKit kit;
	Course course;
	Capturer(htmlKit kit,Course course){
		this.kit=kit;
		this.course=course;
	}
	public void run(){
		try{
			System.out.println(course.CourseName+"�߳̿�ʼ");
		kit.CaptureHomework(course);
		kit.CaptureFile(course);
		kit.CaptureMessage(course);
		System.out.println(course.CourseName+"�߳̽���");
		}
		catch(Exception e){
	}
	}

}
