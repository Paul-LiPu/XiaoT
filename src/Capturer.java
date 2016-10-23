
public class Capturer implements Runnable{//设计之初是为了多线程地抓取网页内容，还没有达到使用目的，暂时与程序功能无关。
	htmlKit kit;
	Course course;
	Capturer(htmlKit kit,Course course){
		this.kit=kit;
		this.course=course;
	}
	public void run(){
		try{
			System.out.println(course.CourseName+"线程开始");
		kit.CaptureHomework(course);
		kit.CaptureFile(course);
		kit.CaptureMessage(course);
		System.out.println(course.CourseName+"线程结束");
		}
		catch(Exception e){
	}
	}

}
