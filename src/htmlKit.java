import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class htmlKit {//������ҳץȡ�ĺ�������
	HttpClient client;//HttpClient����ִ��HTTPЭ��Ĳ���
	htmlKit(){
		//ע������Э��
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 80, PlainSocketFactory
				.getSocketFactory()));
		ClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);
		//��ʼ��HttpClient
		client=new DefaultHttpClient();
		
	}
	boolean CheckLogin(User user) throws Exception{//���û�����������е�¼��֤
		HttpPost httpPost = new HttpPost(
				"https://learn.tsinghua.edu.cn/MultiLanguage/lesson/teacher/loginteacher.jsp");
		List<NameValuePair> login = new ArrayList<NameValuePair>();
		login.add(new BasicNameValuePair("userid", user.userName));
		login.add(new BasicNameValuePair("userpass", user.password));
		login.add(new BasicNameValuePair("submit1", "��¼"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(login, "utf-8");
		httpPost.setEntity(entity);
		HttpResponse loginResponse = client.execute(httpPost);
		HttpEntity loginEntity = loginResponse.getEntity();
		if (loginEntity != null) {
			String loginResult = EntityUtils.toString(loginEntity);
			if (loginResult.indexOf("loginteacher_action.jsp") != -1) {
				EntityUtils.consume(loginEntity);
				return true;
			}
		}
		EntityUtils.consume(loginEntity);
		return false;
	}
	Document parseURL(String url) throws Exception{//ͨ��ִ��HttpGet������Jsoup���ǽ������Ϳ��Եõ����������ص��ļ�
		HttpGet httpGet=new HttpGet(url);
		HttpResponse response=client.execute(httpGet);
		String Page = EntityUtils.toString(response.getEntity());
		Document PageDOM = Jsoup.parse(Page);
		return PageDOM;
	}
	void CaptureFile(Course MyCourse)throws Exception{//ץȡ�γ��ļ���Ϣ
		Document courseWarePageDOM=parseURL("http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/download.jsp?course_id="+MyCourse.CourseId);
		//��HTML�Ĳ������Խ���ɸѡ
		Elements courseWares = courseWarePageDOM.select("a[href~=.*uploadFile.*]");
		Iterator<Element> courseWaresIterator = courseWares.iterator();
		Elements fileDate1 = courseWarePageDOM.select("tr.tr1 > td:eq(4),tr.tr2 > td:eq(4)");
		//Elements fileDate2 = courseWarePageDOM.select("tr.tr2 > td:eq(4)");
		Iterator<Element> fileDateIterator1=fileDate1.iterator();
		//Iterator<Element> fileDateIterator2=fileDate2.iterator();
		Elements fileSize1 = courseWarePageDOM.select("tr.tr1 > td:eq(3),tr.tr2 > td:eq(3)");
		//Elements fileSize2 = courseWarePageDOM.select("tr.tr2 > td:eq(3)");
		Iterator<Element> fileSizeIterator1=fileSize1.iterator();
		//Iterator<Element> fileSizeIterator2=fileSize2.iterator();
		int i=0;
		while(courseWaresIterator.hasNext()){
			i=i+1;
			MyFile file=new MyFile();
			Element courseWaresLink = courseWaresIterator.next();
			String courseWarePath = courseWaresLink.attr("href");
			file.url="http://learn.tsinghua.edu.cn"+courseWarePath;
			//System.out.println(file.url);
			file.fileName=courseWaresLink.text();
			//if(i%2==0){
				file.size=fileSizeIterator1.next().html();
				file.date=fileDateIterator1.next().html();
			/*}else{
				file.size=fileSizeIterator2.next().html();
				file.date=fileDateIterator2.next().html();
			}*/
			MyCourse.addMyFile(file);
		}
		return;
	}
	void  CaptureMessage(Course MyCourse)throws Exception{//�Կγ�ͨ�����ץȡ
		Document courseMessagePageDOM=parseURL("http://learn.tsinghua.edu.cn/MultiLanguage/public/bbs/getnoteid_student.jsp?course_id="+MyCourse.CourseId);
		Elements courseMessages = courseMessagePageDOM.select("a[href~=.*�γ̹���.*]");
		Iterator<Element> courseMessagesIterator=courseMessages.iterator();
		Elements messageDate1 = courseMessagePageDOM.select("tr.tr1 > td:eq(3),tr.tr2 > td:eq(3)");
		//Elements messageDate2 = courseMessagePageDOM.select("tr.tr2 > td:eq(3)");
		Iterator<Element> messageDateIterator1=messageDate1.iterator();
		//Iterator<Element> messageDateIterator2=messageDate2.iterator();
		Elements messageSpeaker1 = courseMessagePageDOM.select("tr.tr1 > td:eq(2),tr.tr2 > td:eq(2)");
		//Elements messageSpeaker2 = courseMessagePageDOM.select("tr.tr2 > td:eq(2)");
		Iterator<Element> messageSpeakerIterator1=messageSpeaker1.iterator();
		//Iterator<Element> messageSpeakerIterator2=messageSpeaker2.iterator();
		int i=0;
		while(courseMessagesIterator.hasNext()){
			i=i+1;
			Message MyMessage=new Message();
			Element message=courseMessagesIterator.next();
			String regEx = "\\d+";
			Pattern pattern = Pattern.compile(regEx);
			Matcher match = pattern.matcher(message.attr("href"));
			Document messageDOM;
			if(match.find()){
			 messageDOM=parseURL("http://learn.tsinghua.edu.cn/MultiLanguage/public/bbs/note_reply.jsp?bbs_type=%E8%AF%BE%E7%A8%8B%E5%85%AC%E5%91%8A&id="+match.group()+"&course_id="+MyCourse.CourseId);
			 Elements contents=messageDOM.select("td.tr_l2");
			 Element content=contents.get(1);
			 MyMessage.content=content.text();
			 }
			MyMessage.title=message.text();
			/*if(i%2==0){
				MyMessage.speaker=messageSpeakerIterator2.next().text();
				MyMessage.date=messageDateIterator2.next().text();
				MyMessage.date1=parseDate(null,MyMessage.date);
			}else{*/
				MyMessage.speaker=messageSpeakerIterator1.next().text();
				MyMessage.date=messageDateIterator1.next().text();
				MyMessage.date1=parseDate(null,MyMessage.date);
			MyCourse.addMessage(MyMessage);
		}
		return;
	}
	void CaptureHomework(Course MyCourse)throws Exception{//ץȡ�γ���ҵ
		Document courseHomeworkPageDOM=parseURL("http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/hom_wk_brw.jsp?course_id="+MyCourse.CourseId);
		Elements courseHomework = courseHomeworkPageDOM.select("a[href~=.*rec_id.*]");
		Iterator<Element> courseHomeworkIterator=courseHomework.iterator();
		Elements homeworkDate1 = courseHomeworkPageDOM.select("tr.tr1 > td:eq(2),tr.tr2 > td:eq(2)");
		//Elements homeworkDate2 = courseHomeworkPageDOM.select("tr.tr2 > td:eq(2)");
		Iterator<Element> homeworkDateIterator1=homeworkDate1.iterator();
		//Iterator<Element> homeworkDateIterator2=homeworkDate2.iterator();
		Elements homeworkState1 = courseHomeworkPageDOM.select("tr.tr1 > td:eq(3),tr.tr2 > td:eq(3)");
		//Elements homeworkState2 = courseHomeworkPageDOM.select("tr.tr2 > td:eq(3)");
		Iterator<Element> homeworkStateIterator1=homeworkState1.iterator();
		//Iterator<Element> homeworkStateIterator2=homeworkState2.iterator();
		int i=0;
		while(courseHomeworkIterator.hasNext()){
			i=i+1;
			Homework MyHomework=new Homework();
			Element homework=courseHomeworkIterator.next();
			Document homeworkDOM;
			homeworkDOM=parseURL("http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/"+homework.attr("href"));
			Elements contents=homeworkDOM.select("td.tr_2");
			Element content=contents.get(1);
			Element title=contents.get(0);
			MyHomework.comment=content.text();
			MyHomework.title=title.text();
			//if(i%2==0){
				MyHomework.deadline=homeworkDateIterator1.next().text();
				MyHomework.deadline1=parseDate(null,MyHomework.deadline);
				String state=homeworkStateIterator1.next().text();
				if(state.equals("�Ѿ��ύ"))
					MyHomework.state=true;
				else MyHomework.state=false;
			/*}else{
				MyHomework.deadline=homeworkDateIterator2.next().text();
				MyHomework.deadline1=parseDate(null,MyHomework.deadline);
				String state=homeworkStateIterator2.next().text();
				if(state.equals("�Ѿ��ύ"))
					MyHomework.state=true;
				else MyHomework.state=false;
			}*/
			MyCourse.addHomework(MyHomework);
		}
		//}
		return;
	}
	void CaptureCourse(User user) throws Exception{//ץȡ�γ���Ϣ����Ҫ����ǰ������ҳץȡ����
		Document coursePageDOM=parseURL("http://learn.tsinghua.edu.cn/MultiLanguage/lesson/student/MyCourse.jsp?language=cn");
		Elements courses = coursePageDOM.select("a[href~=.*course_locate.*]");
		Iterator<Element> coursesIterator = courses.iterator();
		Elements homeworkNum1 = coursePageDOM.select("tr.info_tr > td:eq(1),tr.info_tr2 > td:eq(1)");
		//Elements homeworkNum2 = coursePageDOM.select("tr.info_tr2 > td:eq(1)");
		Iterator<Element> homeworkNumIterator1=homeworkNum1.iterator();
		//Iterator<Element> homeworkNumIterator2=homeworkNum2.iterator();
		Elements messageNum1 = coursePageDOM.select("tr.info_tr > td:eq(2),tr.info_tr2 > td:eq(2)");
		//Elements messageNum2 = coursePageDOM.select("tr.info_tr2 > td:eq(2)");
		Iterator<Element> messageNumIterator1=messageNum1.iterator();
		//Iterator<Element> messageNumIterator2=messageNum2.iterator();
		Elements fileNum1 = coursePageDOM.select("tr.info_tr > td:eq(3),tr.info_tr2 > td:eq(3)");
		//Elements fileNum2 = coursePageDOM.select("tr.info_tr2 > td:eq(3)");
		Iterator<Element> fileNumIterator1=fileNum1.iterator();
		//Iterator<Element> fileNumIterator2=fileNum2.iterator();
		int i=0;
		while (coursesIterator.hasNext()) {
			i=i+1;
			String regEx = "\\d+";
			Pattern pattern = Pattern.compile(regEx);
			Element course = coursesIterator.next();
			Course MyCourse=new Course();
			MyCourse.CourseName = course.html();
			Matcher match = pattern.matcher(course.attr("href"));
			if (match.find()){
				MyCourse.CourseId=match.group();
				user.addCourse(MyCourse);
				//Capturer capture=new Capturer(this,MyCourse);
				//new Thread(capture,MyCourse.CourseName).start();
				//System.out.println(MyCourse.CourseName);
				CaptureHomework(MyCourse);
				CaptureFile(MyCourse);
				CaptureMessage(MyCourse);
				//if(i%2==0){
				MyCourse.homework=parseNum(homeworkNumIterator1.next().text());
				MyCourse.message=parseNum(messageNumIterator1.next().text());
				MyCourse.file=parseNum(fileNumIterator1.next().text());
				
				/*}else{
					MyCourse.homework=parseNum(homeworkNumIterator2.next().text());
					MyCourse.message=parseNum(messageNumIterator2.next().text());
					MyCourse.file=parseNum(fileNumIterator2.next().text());
				}*/
			}
		}
	}
	int parseNum(String str){//���ַ����н�������һ�����ִ�
		String regEx = "\\d+";
		Pattern pattern = Pattern.compile(regEx);
		Matcher match = pattern.matcher(str);
		if(match.find())
			return Integer.parseInt(match.group());
		else return -1;
	}
	public String getFileName(String url) throws Exception{//��ȡ�γ��ļ����ƣ�ͨ��GET�γ��ļ������ص�ַ�õ�
		HttpGet courseWareFile = new HttpGet(url);
		HttpResponse response = client.execute(courseWareFile);
		Header contentHeader = response.getFirstHeader("Content-Disposition");
		String filename = null;
		if (contentHeader != null) {
			HeaderElement[] values = contentHeader.getElements();
			if (values.length == 1) {
				NameValuePair param = values[0].getParameterByName("filename");
				if (param != null) {
					try {
						filename = param.getValue();
						byte[] tmp = filename.getBytes("ISO-8859-1");
						filename = new String(tmp, "GBK");
						/*attern pattern1=Pattern.compile(".*_");
						Pattern pattern2=Pattern.compile("\\..*");
						filename = new String(tmp, "GBK");
						Matcher match=pattern1.matcher(filename);
						Matcher match2=pattern2.matcher(filename);
						StringBuffer tmp2=new StringBuffer(match.group());
						tmp2.deleteCharAt(tmp2.length()-1);
						filename=new String(tmp2+match2.group());*/
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		EntityUtils.consumeQuietly(response.getEntity());
		return filename;
	}
	public  Date parseDate(String strFormat, String dateValue) {//���ַ�����ʽ��������Ϣת����Date��ʽ��������Ϣ
		if (dateValue == null)
			return null;

		if (strFormat == null)
			strFormat = "yyyy-MM-dd";

		SimpleDateFormat dateFormat = new SimpleDateFormat(strFormat);
		Date newDate = null;

		try {
			newDate = dateFormat.parse(dateValue);
		} catch (ParseException pe) {
			newDate = null;
		}

		return newDate;
	}
	

}
