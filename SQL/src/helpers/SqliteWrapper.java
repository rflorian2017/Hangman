package helpers;

import constants.ApplicationConstants;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SqliteWrapper {
	/**
	 * @return a Connection to a SQLITE db
	 */
	String url = ApplicationConstants.SQLITE;

	private Connection connect() {
		//link to db

		Connection conn = null; //create a connection to the SQL db

		try {
			conn = ApplicationConstants.MYSQL_SELECTED ?
					DriverManager.getConnection(ApplicationConstants.MYSQL,
							ApplicationConstants.USER,
							ApplicationConstants.PWD) :
								DriverManager.getConnection(ApplicationConstants.SQLITE);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return conn;
	}

	public void insert(String name, int age, int salary) {
		String sql = "INSERT INTO employees (name, age, salary)" +
				" VALUES(?,?,?)";
		try {
			Connection conn = this.connect();
			PreparedStatement preparedStatement =
					conn.prepareStatement(sql);

			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, age);
			preparedStatement.setInt(3, salary);

			preparedStatement.executeUpdate();

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}

	}

	public HashMap<Integer, String> selectAllCategories() {
		String sql = "SELECT * FROM " + ApplicationConstants.CATEGORIES_TABLE;
		HashMap<Integer, String> categoriesHashMap = new HashMap<Integer, String>();
		try {
			Connection conn = this.connect();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				categoriesHashMap.put(resultSet.getInt(ApplicationConstants.CATEGORIES_TABLE_COLUMN_ID), 
						resultSet.getString(ApplicationConstants.CATEGORIES_TABLE_COLUMN_NAME) 
						);
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return categoriesHashMap;
	}



	public List<String> getAllColumns(String tableName) {
		List<String> columns = new ArrayList<>();
		String toReturn = "";
		try {
			Connection conn = this.connect();
			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getColumns(null, null, tableName, null);
			while (rs.next()) {

				columns.add(rs.getString(4));
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return columns;
	}

	public List<String> getAllTables() {

		List<String> tables = new ArrayList<>();
		String toReturn = "";
		try {
			Connection conn = this.connect();
			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, null, new String[]{"TABLE"});
			while (rs.next()) {

				tables.add(rs.getString(3));
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return tables;
	}



	public HashMap<Integer, Integer> getAllDepartmentsEmployeesMappings() {
		String sql = "SELECT * FROM dept_empl";
		HashMap<Integer, Integer> mapp = new HashMap<>();

		try {
			Connection conn = this.connect();
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {
				mapp.put(resultSet.getInt("id_empl"),
						resultSet.getInt("id_dept"));
			}

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return mapp;
	}

	public void deleteQuery(String criteria, String value) {
		String sql = "DELETE FROM " +
				ApplicationConstants.CATEGORIES_TABLE +
				" WHERE " + criteria + " = ?";

		Connection connection = this.connect();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			switch (criteria) {
			case "ID":
				preparedStatement.setInt(1, Integer.parseInt(value));
				break;
			case "Name":
				preparedStatement.setString(1, value);
				break;
			case "age":
				preparedStatement.setInt(1, Integer.parseInt(value));
				break;
			case "salary":
				preparedStatement.setInt(1, Integer.parseInt(value));
				break;
			}
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private void createCategoryTable() {
		String sqlString = "CREATE TABLE IF NOT EXISTS "+ ApplicationConstants.CATEGORIES_TABLE +
				" ("
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"NAME TEXT UNIQUE NOT NULL"
				+ ")";
		try {
			Connection conn = this.connect();
			Statement statement = conn.createStatement();
			statement.execute(sqlString);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	private void createWordsTable() {
		String sqlString = "CREATE TABLE IF NOT EXISTS "+ ApplicationConstants.WORDS_TABLE +
				" ("
				+ "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
				"NAME TEXT UNIQUE NOT NULL,"+
				"HINT TEXT,"+
				"CATEGORY_ID UNIQUE NOT NULL"
				+ ")";
		try {
			Connection conn = this.connect();
			Statement statement = conn.createStatement();
			statement.execute(sqlString);

		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public void createTables() {
		createCategoryTable();
		createWordsTable();
	}

	/**
	 * 
	 * @param categoryName
	 * Will run a sql INSERT into categories (name) VALUES("aaascs")
	 */
	public void insertCategory(String categoryName) throws SQLException {
		String sql = "INSERT INTO " + ApplicationConstants.CATEGORIES_TABLE + 
				" (" + ApplicationConstants.CATEGORIES_TABLE_COLUMN_NAME + ")" +
				" VALUES(?)";
		Connection conn = this.connect();
		PreparedStatement preparedStatement =
				conn.prepareStatement(sql);

		preparedStatement.setString(1, categoryName);

		preparedStatement.executeUpdate();

	}
}