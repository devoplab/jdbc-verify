package com.nedzhang.util.jdbcverify;

import java.sql.SQLException;
import java.util.Scanner;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "jdbcverify", footer = "Copyright(c) 2018", description = "Verify jdbc connecton. Please ensure the jdbc driver jar in the path. You can put it in the \"lib\" folder next to jdbcverify.sh or change the jdbcverify.sh to include it in the path.")
public class App implements Runnable {

	public static void main(final String[] args) {

		final App app = new App();

		CommandLine.run(app, args);

	}

	@Option(names = { "-d",
			"--driver" }, required = true, description = "JDBC driver class name. The class name of the driver. example: oracle.jdbc.driver.OracleDriver")
	String driverClassName;

	@Option(names = { "-c",
			"--connection-string" }, required = true, description = "JDBC Connection string. example: jdbc:oracle:thin:@//localhost:1521/xe")
	String connectionString;

	@Option(names = { "-u", "--user" }, required = true, description = "database login user")
	String userName;

	@Option(names = { "-h", "--help" }, usageHelp = true, description = "display a help message")
	boolean helpRequested = false;

	private void probeDb(final Scanner reader, final String userName, final String password,
			final String connectionString, final String driverClassName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		System.out.println();

		// Below clock only works for Oracle DB. So it is commented out
		// System.out.printf("Validating DB Connectoin [%s]\n", this.connectionString);
		//
		// String dbSanityCheckSqlStatement = "select 0 from dual";
		//
		// DBEngine.execute(this.userName, password, this.connectionString,
		// this.driverClassName, dbSanityCheckSqlStatement, false);
		//
		// System.out.println("DB Connection Verified");

		while (true) {

			// Scanner reader = new Scanner(System.in);
			try {
				// Wait a little in case the console is printing exception stack
				Thread.sleep(1000);
			} catch (final InterruptedException e1) {
				// Do nothing
			}

			System.out.println("Please eneter sql statment: Quit or Exit to exit");
			System.out.print(':');

			String userSqlStatement = reader.nextLine();

			if (userSqlStatement != null && userSqlStatement.trim().endsWith(";")) {
				userSqlStatement = userSqlStatement.trim();
				userSqlStatement = userSqlStatement.substring(0, userSqlStatement.length() - 1);
			}

			if ("exit".equalsIgnoreCase(userSqlStatement) || "quit".equalsIgnoreCase(userSqlStatement)) {
				break;
			}

			try {
				DBEngine.execute(this.userName, password, this.connectionString, this.driverClassName, userSqlStatement,
						true);
			} catch (final Exception e) {
				System.err.println(e.getLocalizedMessage());
				e.printStackTrace();
			}

		}

		System.out.println("===============================================");
		System.out.println("Bye");

	}

	@Override
	public void run() {

		// String connectionString = "jdbc:oracle:thin:@//localhost:21521/ORCLSE2";
		// String user = "system";
		// String password = "OracleI83Se2";
		// String driverClassName = "oracle.jdbc.driver.OracleDriver";
		// String sqlStatement = "select * from help";

		System.out.println("*******************************************");
		System.out.println("*    JDBC Verify     ~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println("*******************************************");

		final Scanner reader = new Scanner(System.in); // Reading from System.in

		System.out.print(String.format("Please enter password [%s]:", this.userName));
		final String password = reader.nextLine(); // Scans the next token of the input as an int.
		// once finished

		try {
			probeDb(reader, this.userName, password, this.connectionString, this.driverClassName);
		} catch (final Exception e) {

			System.out.println();
			System.out.println("**********************************************************");
			e.printStackTrace();
		}

		reader.close();
	}
}

// @Option(names = { "-p", "--password" }, required = true, description =
// "Passphrase", interactive = true)
// String password;