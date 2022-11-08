package com.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AddressBook {
	Connection connection;

	private Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Drivers loaded!!");
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/address_book_service?allowPublicKeyRetrieval=true&useSSL=false",
					"root", "root");
			System.out.println("connection Established!!");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public List<Contacts> retrieveData() {
		ResultSet resultSet = null;
		List<Contacts> addressBookList = new ArrayList<Contacts>();
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from address_book_db;");
			int count = 0;
			while (resultSet.next()) {
				Contacts contactInfo = new Contacts();
				contactInfo.setFirstName(resultSet.getString("firstName"));
				contactInfo.setLastName(resultSet.getString("lastname"));
				contactInfo.setAddress(resultSet.getString("address"));
				contactInfo.setCity(resultSet.getString("city"));
				contactInfo.setState(resultSet.getString("state"));
				contactInfo.setZip(resultSet.getInt("zipcode"));
				contactInfo.setPhoneNumber(resultSet.getString("mobilenum"));
				contactInfo.setEmailId(resultSet.getString("email"));
				contactInfo.setDateAdded(resultSet.getDate("dateadded").toLocalDate());
				contactInfo.setBookName(resultSet.getString("bookName"));

				addressBookList.add(contactInfo);
			}
		} catch (SQLException e) {
			System.out.println(e);
		}
		return addressBookList;

	}
}
