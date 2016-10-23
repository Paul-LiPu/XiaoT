import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SpringLayout;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.PoolingClientConnectionManager;


public class displayBoard extends TimerTask implements ActionListener,ListSelectionListener{//չʾ����ѧ����Ϣ���������
	//����������Swing���
	static JFrame f = null;
	JScrollPane coursePane,messagePane,homeworkPane,filePane,detailPane;
	JTable courseTable,messageTable,homeworkTable,fileTable;
	JButton updateButton,hideButton,setDirButton,downAllButton,downOneButton,logOffButton;
	JTextArea detailShow;
	//��֤��¼��״̬
	static boolean login = false;
	//���ݣ����ߺͲ�������
	User user;
	htmlKit Kit;
	int courseRow,messageRow,homeworkRow,fileRow;
	static String WEST=SpringLayout.WEST,NORTH=SpringLayout.NORTH,EAST=SpringLayout.EAST,SOUTH=SpringLayout.SOUTH;
	//����Login��壬���Զ�����в���
	Login loginPane;

	public displayBoard(User user,Login loginPane) throws Exception {//��ʼ�����������͹�����htmlKit
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 80, PlainSocketFactory
				.getSocketFactory()));
		ClientConnectionManager cm = new PoolingClientConnectionManager(
				schemeRegistry);
		this.user=user;
		this.loginPane=loginPane;
		Kit=new htmlKit();
		login=Kit.CheckLogin(user);
	}

	public void showBoard(boolean visible,int second) throws Exception {//��ʾ���
		if(login)
			Kit.CaptureCourse(user);
		addTimer(second);
		JFrame.setDefaultLookAndFeelDecorated(true);
		f=new JFrame("СT����");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(f.getContentPane());
		//if(check)
		//Data.serializeUser(user);
		f.pack();
		f.setVisible(visible);
	}
	void addComponentsToPane(Container pane){//��������м�����ֲ���,�����зֲ�����
		f.setPreferredSize(new Dimension(830,730));
		JLabel CourseLabel=new JLabel("�ҵĿγ�");
		JLabel HomeworkLabel=new JLabel("��ҵ");
		JLabel MessageLabel=new JLabel("ͨ��");
		JLabel FileLabel=new JLabel("�μ�");
		JLabel setLabel=new JLabel("�������");
		JLabel InfoLabel=new JLabel("��ϸ��Ϣ");
		updateButton=new JButton("����");
		hideButton=new JButton("����ҳ��");
		setDirButton=new JButton("���ô洢·��");
		downAllButton=new JButton("���������ļ�");
		downOneButton=new JButton("�����ļ�");
		logOffButton=new JButton("�˳���¼");
		String[][] blank={};
		courseTable=new JTable(MyTableModel(CourseData(user),CourseTitle()));
		messageTable=new JTable(MyTableModel(blank,MessageTitle()));
		homeworkTable=new JTable(MyTableModel(blank,MessageTitle()));
		fileTable=new JTable(MyTableModel(blank,FileTitle()));
		coursePane=new JScrollPane(courseTable);
		messagePane=new JScrollPane(messageTable);
		homeworkPane=new JScrollPane(homeworkTable);
		filePane=new JScrollPane(fileTable);
		detailShow=new JTextArea();
		detailPane=new JScrollPane(detailShow);
		resizeTable(true,coursePane,courseTable);
		resizeTable(true,filePane,fileTable);
		resizeTable(true,homeworkPane,homeworkTable);
		resizeTable(true,messagePane,messageTable);
		Dimension tableSize=new Dimension(400,200);
		Dimension buttonSize=new Dimension(198,65);
		coursePane.setPreferredSize(tableSize);
		messagePane.setPreferredSize(tableSize);
		homeworkPane.setPreferredSize(tableSize);
		filePane.setPreferredSize(tableSize);
		detailPane.setPreferredSize(tableSize);
		updateButton.setPreferredSize(buttonSize);
		hideButton.setPreferredSize(buttonSize);
		setDirButton.setPreferredSize(buttonSize);
		downAllButton.setPreferredSize(buttonSize);
		downOneButton.setPreferredSize(buttonSize);
		logOffButton.setPreferredSize(buttonSize);
		detailShow.setEditable(false);
		detailShow.setPreferredSize(tableSize);
		detailShow.setLineWrap(true);
		
		courseTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		messageTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fileTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		homeworkTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		/*courseTable.addMouseListener(this);
		messageTable.addMouseListener(this);
		homeworkTable.addMouseListener(this);
		fileTable.addMouseListener(this);
		*/
		courseTable.getSelectionModel().addListSelectionListener(this);
		messageTable.getSelectionModel().addListSelectionListener(this);
		homeworkTable.getSelectionModel().addListSelectionListener(this);
		fileTable.getSelectionModel().addListSelectionListener(this);
		
		hideButton.addActionListener(this);
		setDirButton.addActionListener(this);
		downAllButton.addActionListener(this);
		updateButton.addActionListener(this);
		downOneButton.addActionListener(this);
		logOffButton.addActionListener(this);
		
		
		f.add(coursePane);
		f.add(messagePane);
		f.add(homeworkPane);
		f.add(filePane);
		f.add(detailPane);
		f.add(CourseLabel);
		f.add(MessageLabel);
		f.add(HomeworkLabel);
		f.add(FileLabel);
		f.add(setLabel);
		f.add(InfoLabel);
		f.add(updateButton);
		f.add(hideButton);
		f.add(setDirButton);
		f.add(downAllButton);
		f.add(logOffButton);
		f.add(downOneButton);
		downOneButton.setVisible(false);
		
		SpringLayout layout=new SpringLayout();
		f.setLayout(layout);
		layout.putConstraint(WEST, CourseLabel, 5, WEST, pane);
		layout.putConstraint(NORTH, CourseLabel, 5, NORTH, pane);
		layout.putConstraint(WEST, coursePane, 0, WEST, CourseLabel);
		layout.putConstraint(NORTH, coursePane, 5, SOUTH, CourseLabel);
		layout.putConstraint(WEST, MessageLabel, 5, EAST,coursePane );
		layout.putConstraint(NORTH, MessageLabel, 0, NORTH, CourseLabel);
		layout.putConstraint(NORTH, messagePane, 5, SOUTH, MessageLabel);
		layout.putConstraint(WEST, messagePane, 0, WEST, MessageLabel);
		layout.putConstraint(NORTH, FileLabel, 5, SOUTH, messagePane);
		layout.putConstraint(WEST, FileLabel, 0, WEST, CourseLabel);
		layout.putConstraint(WEST, filePane, 0, WEST, FileLabel);
		layout.putConstraint(NORTH, filePane, 5, SOUTH, FileLabel);
		layout.putConstraint(WEST, HomeworkLabel, 0,WEST, MessageLabel );
		layout.putConstraint(NORTH, HomeworkLabel, 5, SOUTH, messagePane);
		layout.putConstraint(WEST, homeworkPane, 0, WEST, HomeworkLabel);
		layout.putConstraint(NORTH, homeworkPane, 5, SOUTH, HomeworkLabel);
		layout.putConstraint(WEST, InfoLabel, 0, WEST, HomeworkLabel);
		layout.putConstraint(NORTH, InfoLabel, 5, SOUTH, homeworkPane);
		layout.putConstraint(WEST, detailPane, 0, WEST, InfoLabel);
		layout.putConstraint(NORTH, detailPane, 5, SOUTH, InfoLabel);
		layout.putConstraint(WEST, setLabel, 0, WEST, CourseLabel);
		layout.putConstraint(NORTH, setLabel, 5, SOUTH, filePane);
		layout.putConstraint(WEST, updateButton, 0, WEST, setLabel);
		layout.putConstraint(NORTH, updateButton, 5, SOUTH, setLabel);
		layout.putConstraint(WEST, hideButton, 0, WEST, updateButton);
		layout.putConstraint(NORTH, hideButton, 2, SOUTH, updateButton);
		layout.putConstraint(WEST, setDirButton ,2,EAST, updateButton );
		layout.putConstraint( NORTH, setDirButton, 0,NORTH, updateButton);
		layout.putConstraint(WEST, downAllButton, 0, WEST, setDirButton);
		layout.putConstraint(NORTH, downAllButton, 2, SOUTH, setDirButton);
		layout.putConstraint(WEST, logOffButton, 0,WEST, hideButton);
		layout.putConstraint(NORTH, logOffButton, 2,SOUTH, hideButton);
		layout.putConstraint(WEST, downOneButton, 2,EAST, logOffButton);
		layout.putConstraint(SOUTH, downOneButton, 0,SOUTH, logOffButton);
		
	}
    public void valueChanged(ListSelectionEvent e){//��Ӧ�б�ѡ���ʱ��
    	if(e.getValueIsAdjusting()){
    		ListSelectionModel model= (ListSelectionModel)e.getSource();
    		if(e.getSource().equals(courseTable.getSelectionModel())){//ͨ���ж�ʱ��ζ���Դ�����ж�ִ�еķ�ʽ���������γ̱�񣬻���¿μ������棬��ҵ������������
    			downOneButton.setVisible(false);
    			courseRow=model.getMaxSelectionIndex();
    			Course currentCourse=user.courses.get(courseRow);
    			System.out.println("valueChangeִ��");
    			messageTable.setModel(MyTableModel(MessageData(currentCourse),MessageTitle()));
    			homeworkTable.setModel(MyTableModel(HomeworkData(currentCourse),HomeworkTitle()));
    			fileTable.setModel(MyTableModel(FileData(currentCourse),FileTitle()));
    			resizeTable(true,filePane,fileTable);
    			resizeTable(true,homeworkPane,homeworkTable);
    			resizeTable(true,messagePane,messageTable);
    			messageTable.repaint();
    			messageTable.updateUI();
    			homeworkTable.repaint();
    			homeworkTable.updateUI();
    			fileTable.repaint();
    			fileTable.updateUI();
    		
    		}
    		if(e.getSource().equals(homeworkTable.getSelectionModel())){//��������ҵ��񣬻����ı�������ʾ��ҵ��ϸ��Ϣ
    			downOneButton.setVisible(false);
    			homeworkRow=model.getMaxSelectionIndex();
    			Homework currentHomework=user.courses.get(courseRow).homeworks.get(homeworkRow);
    			
    			detailShow.setText("");
    			detailShow.append("�γ���ҵ��\t"+currentHomework.title+"\n");
    			detailShow.append("�γ�״̬��\t");
    			if(currentHomework.state)
    				detailShow.append("�Ѿ��ύ\n");
    			else detailShow.append("��δ�ύ\n");
    			detailShow.append("��ֹ���ڣ�\t"+currentHomework.deadline+"\n");
    			detailShow.append("��ϸ˵����\n"+currentHomework.comment);
    		}
    		if(e.getSource().equals(fileTable.getSelectionModel())){//�������ļ���񣬻����ı�������ʾ��ϸ��Ϣ�������ļ����ذ�ť����
    			downOneButton.setVisible(true);
    			fileRow=model.getMaxSelectionIndex();
    			MyFile currentFile=user.courses.get(courseRow).myFiles.get(fileRow);
    			detailShow.setText("");
    			detailShow.append("�γ��ļ���\t"+currentFile.fileName+"\n");
    			detailShow.append("�Ķ�״̬��\t");
    			if(currentFile.state)
    				detailShow.append("�Ѿ�����\n");
    			else detailShow.append("��δ����\n");
    			detailShow.append("�ļ���С��\t"+currentFile.size+"\n");
    			detailShow.append("�ϴ�ʱ�䣺\t"+currentFile.date);
    		}
    		if(e.getSource().equals(messageTable.getSelectionModel())){//�����������񣬻����ı�������ʾ��ϸ��Ϣ
    			downOneButton.setVisible(false);
    			messageRow=model.getMaxSelectionIndex();
    			Message currentMessage=user.courses.get(courseRow).messages.get(messageRow);
    			detailShow.setText("");
    			detailShow.append("�γ̹��棺\t"+currentMessage.title+"\n");
    			detailShow.append("�Ķ�״̬��\t");
    			if(currentMessage.state)
    				detailShow.append("�Ѿ��Ķ�\n");
    			else detailShow.append("��δ�Ķ�\n");
    			detailShow.append("����ʱ��\t"+currentMessage.date+"\n");
    			detailShow.append("��ϸ��Ϣ��\n"+currentMessage.content);
    		}
    	}
    }
 /*   public void mouseClicked(MouseEvent e){
    	if(e.getSource()==courseTable){
    		courseRow=courseTable.rowAtPoint(e.getPoint());
    		System.out.println(courseRow);
    	}
    	if(e.getSource()==homeworkTable)
    		homeworkRow=homeworkTable.rowAtPoint(e.getPoint());
    	if(e.getSource()==fileTable)
    		fileRow=fileTable.rowAtPoint(e.getPoint());
    	if(e.getSource()==messageTable)
    		messageRow=messageTable.rowAtPoint(e.getPoint());
    }
    */
    void addTimer(int second){//���������Ӽ�ʱ��
    	Timer timer=new Timer();
    	timer.scheduleAtFixedRate(this, second*1000, 600000);
    	
    }
	public void run(){//��ʱ������ָ��ʱ��ʱ��ִ�е�ָ����Կ�ʼ���и��¼�飬������ָ��£��ͻ���±�񣬲�������ʾ
		htmlKit Kit1=new htmlKit();
		int i1,i2,i3;
		try{
		System.out.println("��ʼ���");
		boolean flag=false;
		ArrayList<String> report=new ArrayList<String>();
		//System.out.println("��ʱ��ִ��");
		User checkUser=new User(user.userName,user.password);
		//displayBoard board=new displayBoard(checkUser,loginPane);
		boolean judge=Kit.CheckLogin(checkUser);
		//System.out.println(judge);
		if(judge){
			//System.out.println("��ʼץȡ");
			//System.out.println(checkUser.courses.size());
			Kit.CaptureCourse(checkUser);
		}
		//diffUser(checkUser);
		//System.out.println(checkUser.courses.size());
		//System.out.println(user.courses.size());
		if(user!=null){
		Iterator<Course> checkIterator=checkUser.courses.iterator();
		Iterator<Course> originalIterator=user.courses.iterator();
		while(checkIterator.hasNext()){
			Course check=checkIterator.next();
			Course original=originalIterator.next();
			//System.out.println(check.CourseName);
			//System.out.println(original.CourseName);
				//if(original.CourseName.equals(check.CourseName)){
					i1=check.homeworks.size()-original.homeworks.size();
					i2=check.messages.size()-original.messages.size();
					i3=check.myFiles.size()-original.myFiles.size();
					if(i1>0){
						report.add(check.CourseName+" ��ҵ  �и��£� ");
						flag=true;
					}
					if(i2>0){
						report.add(check.CourseName+" ͨ��  �и��£� ");
						flag=true;
					}
					if(i3>0){
						report.add(check.CourseName+" �μ�  �и��£� ");
						flag=true;
					}
				//}
		}
		}
		else flag=true;
		System.out.println("������");
		System.out.println(flag);
		if(flag){
			user=checkUser;
			Notifer note=new Notifer();
			note.textarea.setText("");
			Iterator<String> reportIterator=report.iterator();
			while(reportIterator.hasNext()){
				note.textarea.append(reportIterator.next()+"\n");
			}
			
			String[][] blank={};
	    	courseTable.setModel(MyTableModel(CourseData(user),CourseTitle()));
			messageTable.setModel(MyTableModel(blank,MessageTitle()));
			homeworkTable.setModel(MyTableModel(blank,MessageTitle()));
			fileTable.setModel(MyTableModel(blank,FileTitle()));
			resizeTable(true,coursePane,courseTable);
			courseTable.repaint();
			courseTable.updateUI();
			messageTable.repaint();
			messageTable.updateUI();
			homeworkTable.repaint();
			homeworkTable.updateUI();
			fileTable.repaint();
			fileTable.updateUI();
			f.setVisible(true);
			note.f.setVisible(true);
			//Data.serializeUser(user);
		}
		}
		catch(Exception e){
			
		}
	}
	//����һ�����Ժ�������������޹�
	void diffUser(User user){
		MyFile newH=new MyFile();
		newH.date=user.courses.get(1).myFiles.get(1).date;
		newH.fileName=user.courses.get(1).myFiles.get(1).fileName;
		newH.size=user.courses.get(1).myFiles.get(1).size;
		newH.state=user.courses.get(1).myFiles.get(1).state;
		newH.url=user.courses.get(1).myFiles.get(1).url;
		user.courses.get(2).addMyFile(newH);
	}
	//ͨ���ڲ��ཨ��TableModel������table���ݽ����޸�
	DefaultTableModel MyTableModel(Object[][]tableData,Object[] columnTitle){
        DefaultTableModel model=new DefaultTableModel(tableData ,columnTitle){
          	 public boolean isCellEditable(int row, int column)
               {
                   return false;
               }
        };
        return model;
	}
	//���ز�ͬ������ͷ
	String[] CourseTitle(){
		String[] title={"�γ�","δ����ҵ","δ����Ϣ","���ļ�"};
		return title; 
	}
	String[] HomeworkTitle(){
		String[] title={"��ҵ","��ֹ����","״̬"};
		return title; 
	}
	String[] MessageTitle(){
		String[] title={"����","��������","������"};
		return title; 
	}
	String[] FileTitle(){
		String[] title={"�μ�","��������","�ļ�����","״̬"};
		return title; 
	}
	//���ز�ͬ���ı������
	String[][] CourseData(User user){
		int tabelLength=user.courses.size();
		String[][] tabelData=new String[tabelLength][];
		for(int i=0;i<tabelLength;i++){
			Course myCourse=user.courses.get(i);
			tabelData[i]=new String[4];
			tabelData[i][0]=myCourse.CourseName;
			tabelData[i][1]=""+myCourse.homework;
			tabelData[i][2]=""+myCourse.message;
			tabelData[i][3]=""+myCourse.file;
		}
		return tabelData;
	}
	String[][] HomeworkData(Course course){
		int tabelLength=course.homeworks.size();
		String[][] tabelData=new String[tabelLength][];
		for(int i=0;i<tabelLength;i++){
			Homework myHomework=course.homeworks.get(i);
			tabelData[i]=new String[3];
			tabelData[i][0]=myHomework.title;
			tabelData[i][1]=myHomework.deadline;
			if(myHomework.state)
				tabelData[i][2]="";
			else tabelData[i][2]="δ�ύ";
		}
		return tabelData;
	}
	String[][] MessageData(Course course){
		int tabelLength=course.messages.size();
		String[][] tabelData=new String[tabelLength][];
		for(int i=0;i<tabelLength;i++){
			Message myMessage=course.messages.get(i);
			tabelData[i]=new String[3];
			tabelData[i][0]=myMessage.title;
			tabelData[i][1]=myMessage.date;
			tabelData[i][2]=myMessage.speaker;
		}
		return tabelData;
	}
	String[][] FileData(Course course){
		int tabelLength=course.myFiles.size();
		String[][] tabelData=new String[tabelLength][];
		for(int i=0;i<tabelLength;i++){
			MyFile myFile=course.myFiles.get(i);
			tabelData[i]=new String[4];
			tabelData[i][0]=myFile.fileName;
			tabelData[i][1]=myFile.date;
			tabelData[i][2]=myFile.size;
			if(myFile.state){
				tabelData[i][3]="";
			}else{
				tabelData[i][3]="δ����";
			}
		}
		return tabelData;
	}
	//ͨ�����ֶԱ����п�����޸ģ�ʹ������
    public void resizeTable(boolean bool,Container jsp,JTable jg_table) {
        Dimension containerwidth = null;
        if (!bool) {
            //��ʼ��ʱ����������СΪ��ѡ��С��ʵ�ʴ�СΪ0
            containerwidth = jsp.getPreferredSize();
        } else {
            //������ʾ�������������С�ı䣬ʹ��ʵ�ʴ�С��������ѡ��С
            containerwidth = jsp.getSize();
        }
        //������������ getTable().
        int allwidth = jg_table.getIntercellSpacing().width;
        for (int j = 0; j < jg_table.getColumnCount(); j++) {
            //�����������Ŀ��
            int max = 0;
            for (int i = 0; i < jg_table.getRowCount(); i++) {
                int width = jg_table.getCellRenderer(i, j).getTableCellRendererComponent
                  (jg_table, jg_table.getValueAt(i, j), false,
                  false, i, j).getPreferredSize().width;
                if (width > max) {
                    max = width;
                }
            }
            //�����ͷ�Ŀ��
            int headerwidth = jg_table.getTableHeader().
              getDefaultRenderer().getTableCellRendererComponent(
                      jg_table, jg_table.getColumnModel().
              getColumn(j).getIdentifier(), false, false,
              -1, j).getPreferredSize().width;
            //�п�����ӦΪ��ͷ���
            max += headerwidth;
            //�����п�
            jg_table.getColumnModel().
              getColumn(j).setPreferredWidth(max);
            //�����������ȸ�ֵ���ǵ�Ҫ���ϵ�Ԫ��֮����������1������
            allwidth += max + jg_table.getIntercellSpacing().width;
        }
        allwidth += jg_table.getIntercellSpacing().width;
        //������ʵ�ʿ�ȴ�С�������Ŀ�ȣ�����Ҫ�����ֶ���Ӧ�������ñ������Ӧ
        if (allwidth > containerwidth.width) {
            jg_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            jg_table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
}
    void updateData() throws Exception{//��������
    	user.clearData();
		login=Kit.CheckLogin(user);
		if(login){
			detailShow.setText("");
			detailShow.append("���ڽ������ݸ��¡���\n");
			Kit.CaptureCourse(user);
			detailShow.append("���ݸ������\n");
		}else{
			detailShow.setText("");
			detailShow.append("�û���¼ʧ��,�����µ�¼\n");
		}
    }
    void updateUI(){//���±������ʾ������
    	try{
    	updateData();
    	String[][] blank={};
    	courseTable.setModel(MyTableModel(CourseData(user),CourseTitle()));
		messageTable.setModel(MyTableModel(blank,MessageTitle()));
		homeworkTable.setModel(MyTableModel(blank,MessageTitle()));
		fileTable.setModel(MyTableModel(blank,FileTitle()));
		courseTable.repaint();
		courseTable.updateUI();
		messageTable.repaint();
		messageTable.updateUI();
		homeworkTable.repaint();
		homeworkTable.updateUI();
		fileTable.repaint();
		fileTable.updateUI();
    	}
    	catch(Exception e){
    		
    	}
    }

	public void actionPerformed(ActionEvent e){//��ActionLisner������Ӧ����Ҫ���ݲ�ͬ�İ�ť��Դȷ����Ӧ�¼�
		String cmd=e.getActionCommand();
		if(cmd=="����"){//������°�ť
			updateUI();
		}
		if(cmd=="����ҳ��"){//�������ҳ�水ť
			f.setVisible(false);
		}
		if(cmd=="���������ļ�"){//������������ļ���ť�����������ļ�
			Iterator<Course> courseIterater=user.courses.iterator();
			while(courseIterater.hasNext()){
				Course currentCourse=courseIterater.next();
				String courseDir=user.saveDir+"/"+currentCourse.CourseName;
				File courseDir1=new File(courseDir);
				if (!courseDir1.isDirectory()) {
					courseDir1.mkdir();
				}
				Iterator<MyFile> courseWaresIterator=currentCourse.myFiles.iterator();
				System.out.println("������ " + currentCourse.CourseName + "......");
				while (courseWaresIterator.hasNext()) {
					MyFile currentFile=courseWaresIterator.next();
					Downloader thread = new Downloader(Kit.client,currentFile,courseDir );
					thread.start();
				}
			}
		}
		if(cmd=="���ô洢·��"){//������ô洢·����ť�����ø�·��
			JFileChooser jc = new JFileChooser();
			jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jc.setDialogTitle("�ļ�λ��");
			int state = jc.showOpenDialog(null);
			if (state == 1) {
				return;
			} else {
				File folder = jc.getSelectedFile();
				user.saveDir = folder.getAbsolutePath();
				if (Login.rememberPass == true) {
					try {
						Data.insert(Login.name, Login.pass, user.saveDir);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		if(cmd=="�����ļ�"){//��������ļ���ť�����ص�ǰѡ����ļ�
			Course currentCourse=user.courses.get(courseRow);
			MyFile currentFile=currentCourse.myFiles.get(fileRow);
			File courseDir=new File(user.saveDir+"/"+currentCourse.CourseName);
			if (!courseDir.isDirectory()) {
				courseDir.mkdir();
			}
			Downloader thread = new Downloader(Kit.client,currentFile,user.saveDir+"/"+currentCourse.CourseName);
			thread.start();
		}
		if(cmd=="�˳���¼"){//����˳���¼��ť���˳�չʾ��壬�����¼���
			user.clearData();
			f.setVisible(false);
			loginPane.d.setVisible(true);
		}
		}

}
