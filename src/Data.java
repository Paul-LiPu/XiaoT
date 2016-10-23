import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Data {//主要是对用户的登录信息的进行储存，涉及数据库，并不是很必要，只是练手用的
	public static void initialize() throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:MyThu.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("CREATE TABLE user(userid VARCHAR(20),userPass VARCHAR(20),saveDir VARCHAR(300))");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}
	public static void insert(String username, String userpass, String path)
			throws ClassNotFoundException {
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:MyThu.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			/*statement.executeUpdate("insert into user values('" + username
					+ "', '" + Data.sha1(userpass) + "')");*/
			statement.executeUpdate("insert into user values('" + username
					+ "', '" + userpass + "', '" + path + "')");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	public static String[] verify() throws ClassNotFoundException {
		String[] user = new String[2];
		user[0] = "";
		user[1] = "";
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:MyThu.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);

			ResultSet result = statement.executeQuery("select * from user");
			while (result.next()) {
				user[0] = result.getString("userid");
				user[1] = result.getString("userpass");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		return user;
	}
	public static void clear() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");

		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:MyThu.db");
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);
			statement.executeUpdate("Drop Table user");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			try {
				if (connection != null)
					connection.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
			}
		}
		
	}
/*	*public static void serializeUser(User user){
		try{
		ObjectOutputStream oss=new ObjectOutputStream(new FileOutputStream("userData"));
		oss.writeObject(user);
		oss.close();
		}
		catch(Exception e){
			System.out.println("Output: "+e);
		}
	}
	public static boolean loadUser(User user){
		try{
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("userData"));
		user=(User)(ois.readObject());
		ois.close();
		return true;
		}
		catch(Exception e){
			System.out.println("Input: ");
			e.printStackTrace();
			return false;
		}
	}
*/
}