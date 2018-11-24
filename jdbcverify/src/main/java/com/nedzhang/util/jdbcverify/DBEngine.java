package com.nedzhang.util.jdbcverify;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class DBEngine {

	public static void execute(final String user, final String password, final String jdbcConnectoinString,
			final String driverClassName, final String sqlStatement, final boolean printOutput)
			throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Connection conn = null;

		try {

			conn = DriverManager.getConnection(jdbcConnectoinString, user, password);

			// System.out.println("Connecting to " + jdbcConnectoinString);
			// System.out.println();
			System.out.print('<');
			Class.forName(driverClassName).newInstance();

			System.out.print(" .");

			// System.out.println("Executing Query");
			final Statement st = conn.createStatement();

			final ResultSet rs = st.executeQuery(sqlStatement);
			System.out.print(" -");

			// System.out.println("Got REsults");

			if (printOutput) {

				final ResultSetMetaData rsmd = rs.getMetaData();

				final int columnsNumber = rsmd.getColumnCount();

				System.out.println();
				System.out.println("=================================================================================");
				System.out.print("|");

				for (int i = 0; i < columnsNumber; i++) {
					System.out.print(rsmd.getColumnName(i + 1) + "|");
				}
				System.out.println();
				System.out.println("---------------------------------------------------------------------------------");

				while (rs.next()) {
					System.out.print('|');
					for (int i = 0; i < columnsNumber; i++) {

						System.out.print(rs.getString(i + 1) + "|"); // Print one element of a row

					}
					System.out.println();
				}

				System.out.println("=================================================================================");
			}

			System.out.print(" *");

		} finally {
			if (conn != null) {
				conn.close();
				System.out.println(" >");
			}
		}

	}

}
