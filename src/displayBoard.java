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


public class displayBoard extends TimerTask implements ActionListener,ListSelectionListener{//展示网络学堂信息的主面板类
	//构成主面板的Swing组件
	static JFrame f = null;
	JScrollPane coursePane,messagePane,homeworkPane,filePane,detailPane;
	JTable courseTable,messageTable,homeworkTable,fileTable;
	JButton updateButton,hideButton,setDirButton,downAllButton,downOneButton,logOffButton;
	JTextArea detailShow;
	//验证登录的状态
	static boolean login = false;
	//数据，工具和参数设置
	User user;
	htmlKit Kit;
	int courseRow,messageRow,homeworkRow,fileRow;
	static String WEST=SpringLayout.WEST,NORTH=SpringLayout.NORTH,EAST=SpringLayout.EAST,SOUTH=SpringLayout.SOUTH;
	//引入Login面板，可以对其进行操作
	Login loginPane;

	public displayBoard(User user,Login loginPane) throws Exception {//初始化数据容器和工具类htmlKit
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

	public void showBoard(boolean visible,int second) throws Exception {//显示面板
		if(login)
			Kit.CaptureCourse(user);
		addTimer(second);
		JFrame.setDefaultLookAndFeelDecorated(true);
		f=new JFrame("小T助手");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addComponentsToPane(f.getContentPane());
		//if(check)
		//Data.serializeUser(user);
		f.pack();
		f.setVisible(visible);
	}
	void addComponentsToPane(Container pane){//向主面板中加入各种部件,并进行分布控制
		f.setPreferredSize(new Dimension(830,730));
		JLabel CourseLabel=new JLabel("我的课程");
		JLabel HomeworkLabel=new JLabel("作业");
		JLabel MessageLabel=new JLabel("通告");
		JLabel FileLabel=new JLabel("课件");
		JLabel setLabel=new JLabel("软件设置");
		JLabel InfoLabel=new JLabel("详细信息");
		updateButton=new JButton("更新");
		hideButton=new JButton("隐藏页面");
		setDirButton=new JButton("设置存储路径");
		downAllButton=new JButton("下载所有文件");
		downOneButton=new JButton("下载文件");
		logOffButton=new JButton("退出登录");
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
    public void valueChanged(ListSelectionEvent e){//响应列表被选择的时间
    	if(e.getValueIsAdjusting()){
    		ListSelectionModel model= (ListSelectionModel)e.getSource();
    		if(e.getSource().equals(courseTable.getSelectionModel())){//通过判断时间段额来源，来判断执行的方式。如果点击课程表格，会更新课件，公告，作业三个表格的内容
    			downOneButton.setVisible(false);
    			courseRow=model.getMaxSelectionIndex();
    			Course currentCourse=user.courses.get(courseRow);
    			System.out.println("valueChange执行");
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
    		if(e.getSource().equals(homeworkTable.getSelectionModel())){//如果点击作业表格，会在文本域中显示作业详细信息
    			downOneButton.setVisible(false);
    			homeworkRow=model.getMaxSelectionIndex();
    			Homework currentHomework=user.courses.get(courseRow).homeworks.get(homeworkRow);
    			
    			detailShow.setText("");
    			detailShow.append("课程作业：\t"+currentHomework.title+"\n");
    			detailShow.append("课程状态：\t");
    			if(currentHomework.state)
    				detailShow.append("已经提交\n");
    			else detailShow.append("尚未提交\n");
    			detailShow.append("截止日期：\t"+currentHomework.deadline+"\n");
    			detailShow.append("详细说明：\n"+currentHomework.comment);
    		}
    		if(e.getSource().equals(fileTable.getSelectionModel())){//如果点击文件表格，会在文本域中显示详细信息，并让文件下载按钮显现
    			downOneButton.setVisible(true);
    			fileRow=model.getMaxSelectionIndex();
    			MyFile currentFile=user.courses.get(courseRow).myFiles.get(fileRow);
    			detailShow.setText("");
    			detailShow.append("课程文件：\t"+currentFile.fileName+"\n");
    			detailShow.append("阅读状态：\t");
    			if(currentFile.state)
    				detailShow.append("已经下载\n");
    			else detailShow.append("尚未下载\n");
    			detailShow.append("文件大小：\t"+currentFile.size+"\n");
    			detailShow.append("上传时间：\t"+currentFile.date);
    		}
    		if(e.getSource().equals(messageTable.getSelectionModel())){//如果点击公告表格，会在文本域中显示详细信息
    			downOneButton.setVisible(false);
    			messageRow=model.getMaxSelectionIndex();
    			Message currentMessage=user.courses.get(courseRow).messages.get(messageRow);
    			detailShow.setText("");
    			detailShow.append("课程公告：\t"+currentMessage.title+"\n");
    			detailShow.append("阅读状态：\t");
    			if(currentMessage.state)
    				detailShow.append("已经阅读\n");
    			else detailShow.append("尚未阅读\n");
    			detailShow.append("发布时间\t"+currentMessage.date+"\n");
    			detailShow.append("详细信息：\n"+currentMessage.content);
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
    void addTimer(int second){//向程序中添加计时器
    	Timer timer=new Timer();
    	timer.scheduleAtFixedRate(this, second*1000, 600000);
    	
    }
	public void run(){//计时器到达指定时间时，执行的指令。可以开始进行更新检查，如果出现更新，就会更新表格，并弹出提示
		htmlKit Kit1=new htmlKit();
		int i1,i2,i3;
		try{
		System.out.println("开始检查");
		boolean flag=false;
		ArrayList<String> report=new ArrayList<String>();
		//System.out.println("计时器执行");
		User checkUser=new User(user.userName,user.password);
		//displayBoard board=new displayBoard(checkUser,loginPane);
		boolean judge=Kit.CheckLogin(checkUser);
		//System.out.println(judge);
		if(judge){
			//System.out.println("开始抓取");
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
						report.add(check.CourseName+" 作业  有更新： ");
						flag=true;
					}
					if(i2>0){
						report.add(check.CourseName+" 通告  有更新： ");
						flag=true;
					}
					if(i3>0){
						report.add(check.CourseName+" 课件  有更新： ");
						flag=true;
					}
				//}
		}
		}
		else flag=true;
		System.out.println("检查结束");
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
	//这是一个测试函数，与程序功能无关
	void diffUser(User user){
		MyFile newH=new MyFile();
		newH.date=user.courses.get(1).myFiles.get(1).date;
		newH.fileName=user.courses.get(1).myFiles.get(1).fileName;
		newH.size=user.courses.get(1).myFiles.get(1).size;
		newH.state=user.courses.get(1).myFiles.get(1).state;
		newH.url=user.courses.get(1).myFiles.get(1).url;
		user.courses.get(2).addMyFile(newH);
	}
	//通过内部类建立TableModel，来对table内容进行修改
	DefaultTableModel MyTableModel(Object[][]tableData,Object[] columnTitle){
        DefaultTableModel model=new DefaultTableModel(tableData ,columnTitle){
          	 public boolean isCellEditable(int row, int column)
               {
                   return false;
               }
        };
        return model;
	}
	//返回不同表格的题头
	String[] CourseTitle(){
		String[] title={"课程","未交作业","未读消息","新文件"};
		return title; 
	}
	String[] HomeworkTitle(){
		String[] title={"作业","截止日期","状态"};
		return title; 
	}
	String[] MessageTitle(){
		String[] title={"公告","发布日期","发布者"};
		return title; 
	}
	String[] FileTitle(){
		String[] title={"课件","发布日期","文件容量","状态"};
		return title; 
	}
	//返回不同表格的表格数据
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
			else tabelData[i][2]="未提交";
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
				tabelData[i][3]="未下载";
			}
		}
		return tabelData;
	}
	//通过文字对表格的列宽进行修改，使更美观
    public void resizeTable(boolean bool,Container jsp,JTable jg_table) {
        Dimension containerwidth = null;
        if (!bool) {
            //初始化时，父容器大小为首选大小，实际大小为0
            containerwidth = jsp.getPreferredSize();
        } else {
            //界面显示后，如果父容器大小改变，使用实际大小而不是首选大小
            containerwidth = jsp.getSize();
        }
        //计算表格总体宽度 getTable().
        int allwidth = jg_table.getIntercellSpacing().width;
        for (int j = 0; j < jg_table.getColumnCount(); j++) {
            //计算该列中最长的宽度
            int max = 0;
            for (int i = 0; i < jg_table.getRowCount(); i++) {
                int width = jg_table.getCellRenderer(i, j).getTableCellRendererComponent
                  (jg_table, jg_table.getValueAt(i, j), false,
                  false, i, j).getPreferredSize().width;
                if (width > max) {
                    max = width;
                }
            }
            //计算表头的宽度
            int headerwidth = jg_table.getTableHeader().
              getDefaultRenderer().getTableCellRendererComponent(
                      jg_table, jg_table.getColumnModel().
              getColumn(j).getIdentifier(), false, false,
              -1, j).getPreferredSize().width;
            //列宽至少应为列头宽度
            max += headerwidth;
            //设置列宽
            jg_table.getColumnModel().
              getColumn(j).setPreferredWidth(max);
            //给表格的整体宽度赋值，记得要加上单元格之间的线条宽度1个像素
            allwidth += max + jg_table.getIntercellSpacing().width;
        }
        allwidth += jg_table.getIntercellSpacing().width;
        //如果表格实际宽度大小父容器的宽度，则需要我们手动适应；否则让表格自适应
        if (allwidth > containerwidth.width) {
            jg_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        } else {
            jg_table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        }
}
    void updateData() throws Exception{//更新数据
    	user.clearData();
		login=Kit.CheckLogin(user);
		if(login){
			detailShow.setText("");
			detailShow.append("正在进行数据更新……\n");
			Kit.CaptureCourse(user);
			detailShow.append("数据更新完毕\n");
		}else{
			detailShow.setText("");
			detailShow.append("用户登录失败,请重新登录\n");
		}
    }
    void updateUI(){//更新表格中显示的数据
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

	public void actionPerformed(ActionEvent e){//对ActionLisner进行相应，主要根据不同的按钮来源确定响应事件
		String cmd=e.getActionCommand();
		if(cmd=="更新"){//点击更新按钮
			updateUI();
		}
		if(cmd=="隐藏页面"){//点击隐藏页面按钮
			f.setVisible(false);
		}
		if(cmd=="下载所有文件"){//点击下载所有文件按钮，下载所有文件
			Iterator<Course> courseIterater=user.courses.iterator();
			while(courseIterater.hasNext()){
				Course currentCourse=courseIterater.next();
				String courseDir=user.saveDir+"/"+currentCourse.CourseName;
				File courseDir1=new File(courseDir);
				if (!courseDir1.isDirectory()) {
					courseDir1.mkdir();
				}
				Iterator<MyFile> courseWaresIterator=currentCourse.myFiles.iterator();
				System.out.println("下载中 " + currentCourse.CourseName + "......");
				while (courseWaresIterator.hasNext()) {
					MyFile currentFile=courseWaresIterator.next();
					Downloader thread = new Downloader(Kit.client,currentFile,courseDir );
					thread.start();
				}
			}
		}
		if(cmd=="设置存储路径"){//点击设置存储路径按钮，设置根路径
			JFileChooser jc = new JFileChooser();
			jc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jc.setDialogTitle("文件位置");
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
		if(cmd=="下载文件"){//点击下载文件按钮，下载当前选择的文件
			Course currentCourse=user.courses.get(courseRow);
			MyFile currentFile=currentCourse.myFiles.get(fileRow);
			File courseDir=new File(user.saveDir+"/"+currentCourse.CourseName);
			if (!courseDir.isDirectory()) {
				courseDir.mkdir();
			}
			Downloader thread = new Downloader(Kit.client,currentFile,user.saveDir+"/"+currentCourse.CourseName);
			thread.start();
		}
		if(cmd=="退出登录"){//点击退出登录按钮，退出展示面板，进入登录面板
			user.clearData();
			f.setVisible(false);
			loginPane.d.setVisible(true);
		}
		}

}
