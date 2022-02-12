package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import model.Pacjent;
import model.Lek;

public class Szpital {

	public static final String DRIVER = "org.sqlite.JDBC";
	public static final String DB_URL = "jdbc:sqlite:database.db";
	static PreparedStatement prepStmt;
	static Connection conn;
	static Statement stat;

	public Szpital() {
		try {
			Class.forName(Szpital.DRIVER);
		} catch (ClassNotFoundException e) {
			System.err.println("Brak sterownika JDBC");
			e.printStackTrace();
		}

		try {
			conn = DriverManager.getConnection(DB_URL);
			stat = conn.createStatement();
		} catch (SQLException e) {
			System.err.println("Problem z otwarciem polaczenia");
			e.printStackTrace();
		}
		createTables();
	}

	public boolean createTables() {
		String createPacjent = "CREATE TABLE IF NOT EXISTS Zalecenia (numerZalecenia INTEGER PRIMARY KEY AUTOINCREMENT, imie varchar(255), nazwisko varchar(255), pesel int, plec String, wiek int, wzrost int, waga int, nazwaLeku String, dawka String, liczbaDni int)";
		String createLek = "CREATE TABLE IF NOT EXISTS leki (id_leku INTEGER PRIMARY KEY AUTOINCREMENT, id int, nazwaLeku String , moc String, postac String, opakowania String, substancjaCzynna String, ilosc int, ulotka String)";
		try {
			stat.execute(createPacjent);
			stat.execute(createLek);

		} catch (SQLException e) {
			System.err.println("Blad przy tworzeniu tabeli");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertPacjent(int numerZalecenia, String pesel, String imie, String nazwisko, String plec,
			int wzrost, int waga, String nazwaLeku, String dawka, int wiek, int liczbaDni) {
		try {
			prepStmt = conn.prepareStatement("insert into Zalecenia values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			prepStmt.setInt(1, numerZalecenia);
			prepStmt.setString(2, pesel);
			prepStmt.setString(3, imie);
			prepStmt.setString(4, nazwisko);
			prepStmt.setString(5, plec);
			prepStmt.setInt(6, wzrost);
			prepStmt.setInt(7, waga);
			prepStmt.setString(8, nazwaLeku);
			prepStmt.setString(9, dawka);
			prepStmt.setInt(10, wiek);
			prepStmt.setInt(11, liczbaDni);

			prepStmt.execute();
		} catch (SQLException e) {
			System.err.println("Blad przy wstawianiu pacjenta");
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean insertLek(int id, String nazwaLeku, String moc, String postac, String opakowania,
			String substancjaCzynna, int ilosc, String ulotka) {
		try {
			prepStmt = conn.prepareStatement("insert into Leki values (?, ?, ?, ?, ?, ?, ?, ?);");
			prepStmt.setInt(1, id);
			prepStmt.setString(2, nazwaLeku);
			prepStmt.setString(3, moc);
			prepStmt.setString(4, postac);
			prepStmt.setString(5, opakowania);
			prepStmt.setString(6, substancjaCzynna);
			prepStmt.setInt(7, ilosc);
			prepStmt.setString(8, ulotka);

			prepStmt.execute();
		} catch (SQLException e) {
			System.err.println("Blad przy wstawianiu leku");
			return false;
		}
		return true;
	}

	public boolean updateLek(String nazwaLeku2, int ilosc) {
		String sql = "UPDATE Leki SET ilosc = ? where nazwaLeku = '" + nazwaLeku2 + "' ";
		try {
			prepStmt = conn.prepareStatement(sql);

			prepStmt.setInt(1, ilosc);

			prepStmt.execute();
		} catch (SQLException e) {
			System.err.println("Blad przy wstawianiu leku");
			return false;
		}
		return true;
	}

	public List<Pacjent> selectPacjenci() {
		List<Pacjent> pacjenci = new LinkedList<Pacjent>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM Zalecenia");
			String pesel;
			int wzrost, waga, wiek, liczbaDni, numerZalecenia;
			String imie, nazwisko, plec, nazwaLeku, dawka;

			while (result.next()) {
				numerZalecenia = result.getInt("numerZalecenia");
				pesel = result.getString("pesel");
				imie = result.getString("imie");
				nazwisko = result.getString("nazwisko");
				plec = result.getString("plec");
				nazwaLeku = result.getString("nazwaLeku");
				dawka = result.getString("dawka");
				wzrost = result.getInt("wzrost");
				waga = result.getInt("waga");
				wiek = result.getInt("wiek");
				liczbaDni = result.getInt("liczbaDni");

				pacjenci.add(new Pacjent(numerZalecenia, pesel, imie, nazwisko, plec, wzrost, waga, nazwaLeku, dawka,
						wiek, liczbaDni));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return pacjenci;
	}

	public List<Lek> selectLeki() {
		List<Lek> leki = new LinkedList<Lek>();
		try {
			ResultSet result = stat.executeQuery("SELECT * FROM leki");
			int id, ilosc;
			String nazwaLeku, moc, postac, opakowania, substancjaCzynna, ulotka;
			while (result.next()) {
				id = result.getInt("id");
				ilosc = result.getInt("ilosc");
				nazwaLeku = result.getString("nazwaLeku");
				moc = result.getString("moc");
				postac = result.getString("postac");
				opakowania = result.getString("opakowania");
				substancjaCzynna = result.getString("substancjaCzynna");
				ulotka = result.getString("ulotka");

				leki.add(new Lek(id, nazwaLeku, moc, postac, opakowania, substancjaCzynna, ilosc, ulotka));

			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return leki;
	}

	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("Problem z zamknieciem polaczenia");
			e.printStackTrace();
		}
	}
}