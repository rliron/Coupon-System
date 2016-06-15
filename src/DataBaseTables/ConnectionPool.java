package DataBaseTables;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import exceptionClass.CouponException;

/** this class enables you to get a connection to the database
 */
public class ConnectionPool {
	private static String url = "jdbc:derby://localhost:1527/MyCouponData";
	private static String driverName = "org.apache.derby.jdbc.ClientDriver";
	public List<Connection> connections = new ArrayList<Connection>();
	Connection con;


	/** creating a singleton class 
	 * 
	 */
	private static ConnectionPool instance = new ConnectionPool();

	private ConnectionPool() {
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e1) {
			new CouponException(" Class problem, try again");
		}
		for(int i = 0; i<=4; i++){//setting a List with 5 connections 
			try {
				connections.add(con = DriverManager.getConnection(url));
			} catch (SQLException e) {
				new CouponException("Connection problem , try again");
			}	
		}
	
	}

	/** Method of getting singleton class
	 * @return instance of connection pool
	 */
	public static ConnectionPool getInstance() {
		return instance;
	}

	/**
	 * check if there is a connection, if not, puts it on wait, else give a
	 * connection and removes a connection from the list collection
	 * @return connection that we need to work with
	 */
	public synchronized Connection getConnection() {
			try {
				if (connections.size() == 0) {
					new CouponException("Connection pool is full , please wait");
					this.wait();
				}
			} catch (InterruptedException e) {
				new CouponException("Problem try again");
			}
	
		
		 Connection con = connections.get(0);
		 connections.remove(0);
		 
		return con;

	}

	/**
	 * when user done with the connection he returns it and the method notifies
	 * that there is a free connection for the next user
	 * @param con , a connection you want to return
	 */
	public synchronized void returnConnection(Connection con) {
		connections.add(con);
		if (this.connections.size() == 1)
			this.notify();
	}
	/**cleaning the collection and closing the connections
	 * @throws SQLException if there is a problem
	 */
	public void closeAllConnection() throws SQLException {
		connections.clear();
		System.out.println(connections.size());
		if(!con.isClosed()){
			con.close();
		}


	}
}

