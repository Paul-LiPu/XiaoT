import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

public class Downloader extends Thread {//���ؿγ��ļ����࣬������ɶ��߳�����
	private HttpClient httpclient;
	private MyFile myFile;
	private String saveDir;

	public Downloader(HttpClient httpclient,MyFile file,String saveDir) {
		this.httpclient = httpclient;
		this.myFile=file;
		this.saveDir=saveDir;
	}

	public static String getFileName(HttpResponse response) {//��ȡ�ļ����ķ�����ͨ��GET�γ��ļ����������ַ�õ�
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
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
		return filename;
	}

	public void run() {//��һ���ļ���������
		try {
			HttpGet courseWareFile = new HttpGet(myFile.url);
			HttpResponse response = httpclient.execute(courseWareFile);
			String filename = Downloader.getFileName(response);
			File file = new File( saveDir+ "/"+filename);
			if (!file.exists()) {
				FileOutputStream out = new FileOutputStream(file);
				InputStream in = response.getEntity().getContent();
				byte buffer[] = new byte[32768];
				int len = 0;
				while ((len = in.read(buffer)) != -1) {//�û��������������Ч��
					out.write(buffer, 0, len);
				}
				in.close();
				out.close();
				System.out.println(myFile.fileName + " �������.");
			} else {
				System.out.println(myFile.fileName + " ������.");
			}
			EntityUtils.consume(response.getEntity());//����HttpResponse.
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
	}
}