package application;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;

public class SqliteDB {
	Connection c = null;
	Statement stmt = null;

	SqliteDB() {
		// polaczenie z baza danych
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:database.db");
			System.out.println("Udane polaczenie z baza danych");
			c.close();

		} catch (Exception e) {
			System.out.println("Blad:" + e.getMessage());
		}
		// createTables();	
	}
	

//public boolean createTables()  {
//    String createCzytelnicy = "CREATE TABLE IF NOT EXISTS czytelnicy (id_czytelnika INTEGER PRIMARY KEY AUTOINCREMENT, imie varchar(255), nazwisko varchar(255), pesel int)";
//    String createKsiazki = "CREATE TABLE IF NOT EXISTS ksiazki (id_ksiazki INTEGER PRIMARY KEY AUTOINCREMENT, tytul varchar(255), autor varchar(255))";
//    String createWypozyczenia = "CREATE TABLE IF NOT EXISTS wypozyczenia (id_wypozycz INTEGER PRIMARY KEY AUTOINCREMENT, id_czytelnika int, id_ksiazki int)";
//    try {
//        stmt.execute(createCzytelnicy);
//        stmt.execute(createKsiazki);
//        stmt.execute(createWypozyczenia);
//    } catch (Exception e) {
//        System.err.println("Blad przy tworzeniu tabeli");
//        e.printStackTrace();
//        return false;
//    }
//    return true;
//}
}