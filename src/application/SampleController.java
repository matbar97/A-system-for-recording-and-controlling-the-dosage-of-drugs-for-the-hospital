// Klasa kontrolujaca okno glowne programu

package application;

import java.awt.Button;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.TextFields;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Lek;
import model.Pacjent;
import model.CheckPesel;

public class SampleController implements Initializable {

	Szpital b = new Szpital();

	List<Pacjent> lpacjenci = b.selectPacjenci();
	ObservableList<Pacjent> pacjenci = FXCollections.observableArrayList(lpacjenci);
	List<Lek> lleki = b.selectLeki();
	ObservableList<Lek> leki = FXCollections.observableArrayList(lleki);
	@FXML
	private Label label;
	@FXML
	TableView<Pacjent> table;
	@FXML
	TableView<Lek> table2;

	@FXML
	private TableColumn numerZalecenia;
	@FXML
	private TableColumn peselCol;
	@FXML
	private TableColumn imieCol;
	@FXML
	private TableColumn nazwiskoCol;
	@FXML
	private TableColumn plecCol;
	@FXML
	private TableColumn wzrostCol;
	@FXML
	private TableColumn wagaCol;
	@FXML
	private TableColumn lekCol;
	@FXML
	private TableColumn dawkaCol;
	@FXML
	private TableColumn wiekCol;

	@FXML
	private TableColumn idCol;
	@FXML
	private TableColumn iloscCol;
	@FXML
	private TableColumn nazwaLekuCol;
	@FXML
	private TableColumn mocCol;
	@FXML
	private TableColumn postacCol;
	@FXML
	private TableColumn opakowaniaCol;
	@FXML
	private TableColumn substancjaCzynnaCol;
	@FXML
	private TableColumn ulotkaCol;

	@FXML
	Button addBtn;
	@FXML
	Button deleteBtn;
	@FXML
	Button updateBtn;

	@FXML
	TextField addPesel;
	@FXML
	TextField addImie;
	@FXML
	TextField addNazwisko;
	@FXML
	TextField addPlec;
	@FXML
	TextField addWzrost;
	@FXML
	TextField addWaga;
	@FXML
	TextField addLek;
	@FXML
	TextField addDawka;
	@FXML
	TextField addWiek;
	@FXML
	TextField addLiczbaDni;
	@FXML
	TextField addIloscTabletek;
	@FXML
	TextField getNazwaLeku;
	static String tempPesel;

	@FXML
	public void addNewPacjent(ActionEvent event) {

		if (isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Blad danych");
			alert.setHeaderText("Blad dodawania zalecenia");
			String s = "Wprowadz wszystkie dane";
			alert.setContentText(s);
			alert.show();
		}

		else {
			int zuzycie = Integer.parseInt(addDawka.getText()) * Integer.parseInt(addLiczbaDni.getText());
			String name_temp = addLek.getText();

			int opakowania = Integer.parseInt((leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku()))
					.findAny().orElse(null).getOpakowania()));
			int stanObecny = (leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku())).findAny().orElse(null)
					.getIlosc());
			int dawkaObecna = Integer.parseInt(
					leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku())).findAny().orElse(null).getMoc());

			try {
				CheckPesel c = new CheckPesel(addPesel.getText());
				if (c.isValid() == false) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Blad danych");
					alert.setHeaderText("Blad dodawania ");
					String s = "Podano nieprawidlowy nr pesel";
					alert.setContentText(s);
					alert.show();
					return;
				} else if (!isAlpha(addImie.getText()) || !isAlpha(addNazwisko.getText()) || !isAlpha(addPlec.getText())
						|| isAlpha(addWzrost.getText()) || isAlpha(addWaga.getText()) || isAlpha(addWiek.getText())
						|| isAlpha(addDawka.getText()) || isAlpha(addLiczbaDni.getText())
						|| isAlpha(addIloscTabletek.getText())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Blad danych");
					alert.setHeaderText("Blad dodawania ");
					String s = "Podano nieprawidlowe dane. Wprowadz poprawne dane dla ";
					alert.setContentText(s);
					alert.show();
					return;

				} else {

					if (((opakowania * stanObecny * dawkaObecna) - zuzycie) / (dawkaObecna * opakowania) < 0) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Ostrzezenie");
						alert.setHeaderText("Alarm magazynu");
						String s = "Opakowan jest zbyt malo, by przypisac je pacjentowi!";
						alert.setContentText(s);
						alert.show();
						return;
					} else if (((opakowania * stanObecny * dawkaObecna) - zuzycie) / (dawkaObecna * opakowania) < 20) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Ostrze¿enie");
						alert.setHeaderText("Alarm magazynu");
						String s = "W magazynie pozosta³o mniej ni¿ 20 opakowan! Zamow leki, aby uratowac pacjentow!";
						alert.setContentText(s);
						alert.show();
						Pacjent p = new Pacjent(pacjenci.size() + 1, addPesel.getText(), addImie.getText(),
								addNazwisko.getText(), addPlec.getText(), Integer.parseInt(addWzrost.getText()),
								Integer.parseInt(addWaga.getText()), addLek.getText(), addDawka.getText(),
								Integer.parseInt(addWiek.getText()), Integer.parseInt(addLiczbaDni.getText()));

						b.insertPacjent(pacjenci.size() + 1, addPesel.getText(), addImie.getText(),
								addNazwisko.getText(), addPlec.getText(), Integer.parseInt(addWzrost.getText()),
								Integer.parseInt(addWaga.getText()), addLek.getText(), addDawka.getText(),
								Integer.parseInt(addWiek.getText()), Integer.parseInt(addLiczbaDni.getText()));

						pacjenci.add(p);

						table.setItems(this.pacjenci);
						Alert alert1 = new Alert(AlertType.INFORMATION);
						alert1.setTitle("Dodawanie zalecenia");
						alert1.setHeaderText("Pomyslnie dodano  do bazy");
						String s1 = "Zalecenie dodane pomyslnie";
						alert1.setContentText(s1);
						alert1.show();
						table.refresh();

						leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku())).findAny().orElse(null)
								.setIlosc(((opakowania * stanObecny * dawkaObecna) - zuzycie)
										/ (dawkaObecna * opakowania));
						b.updateLek(name_temp,
								((opakowania * stanObecny * dawkaObecna) - zuzycie) / (dawkaObecna * opakowania));
						table2.refresh();
						addPesel.clear();
						addImie.clear();
						addNazwisko.clear();
						addPlec.clear();
						addWzrost.clear();
						addWaga.clear();
						addLek.clear();
						addDawka.clear();
						addWiek.clear();
						addLiczbaDni.clear();
					} else {
						Pacjent p = new Pacjent(pacjenci.size() + 1, addPesel.getText(), addImie.getText(),
								addNazwisko.getText(), addPlec.getText(), Integer.parseInt(addWzrost.getText()),
								Integer.parseInt(addWaga.getText()), addLek.getText(), addDawka.getText(),
								Integer.parseInt(addWiek.getText()), Integer.parseInt(addLiczbaDni.getText()));

						b.insertPacjent(pacjenci.size() + 1, addPesel.getText(), addImie.getText(),
								addNazwisko.getText(), addPlec.getText(), Integer.parseInt(addWzrost.getText()),
								Integer.parseInt(addWaga.getText()), addLek.getText(), addDawka.getText(),
								Integer.parseInt(addWiek.getText()), Integer.parseInt(addLiczbaDni.getText()));

						pacjenci.add(p);

						table.setItems(this.pacjenci);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Dodawanie zalecenia");
						alert.setHeaderText("Pomyslnie dodano  do bazy");
						String s = "Zalecenie dodane pomyslnie";
						alert.setContentText(s);
						alert.show();
						table.refresh();

						leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku())).findAny().orElse(null)
								.setIlosc(((opakowania * stanObecny * dawkaObecna) - zuzycie)
										/ (dawkaObecna * opakowania));
						b.updateLek(name_temp,
								((opakowania * stanObecny * dawkaObecna) - zuzycie) / (dawkaObecna * opakowania));
						table2.refresh();
						addPesel.clear();
						addImie.clear();
						addNazwisko.clear();
						addPlec.clear();
						addWzrost.clear();
						addWaga.clear();
						addLek.clear();
						addDawka.clear();
						addWiek.clear();
						addLiczbaDni.clear();
					}

				}
			} catch (NullPointerException e) {
			}
		}
	}

	@FXML
	public void blockItems1() {
		addPesel.setDisable(false);
		addImie.setDisable(false);
		addNazwisko.setDisable(false);
		addPlec.setDisable(false);
		addWzrost.setDisable(false);
		addWaga.setDisable(false);
		addLek.setDisable(false);
		addDawka.setDisable(false);
		addWiek.setDisable(false);
		addLiczbaDni.setDisable(false);
		getNazwaLeku.setDisable(true);
		addIloscTabletek.setDisable(true);

	}

	@FXML
	public void blockItems2() {

		addPesel.setDisable(true);
		addImie.setDisable(true);
		addNazwisko.setDisable(true);
		addPlec.setDisable(true);
		addWzrost.setDisable(true);
		addWaga.setDisable(true);
		addLek.setDisable(true);
		addDawka.setDisable(true);
		addWiek.setDisable(true);
		addLiczbaDni.setDisable(true);
		addLiczbaDni.setDisable(true);
		getNazwaLeku.setDisable(false);
		addIloscTabletek.setDisable(false);

	}

	@FXML
	public void showOnClick() {
		try {

			Pacjent pacjent = (Pacjent) table.getSelectionModel().getSelectedItem();
			String query = "select*from Zalecenia";
			Szpital.prepStmt = Szpital.conn.prepareStatement(query);
			tempPesel = pacjent.getPesel();
			addPesel.setText(pacjent.getPesel());
			addImie.setText(pacjent.getImie());
			addNazwisko.setText(pacjent.getNazwisko());
			addPlec.setText(pacjent.getPlec());
			addWzrost.setText(String.valueOf(pacjent.getWzrost()));
			addWaga.setText(String.valueOf(pacjent.getWaga()));
			addLek.setText(pacjent.getNazwaLeku());
			addDawka.setText(pacjent.getDawka());
			addWiek.setText(String.valueOf(pacjent.getWiek()));
			addLiczbaDni.setText(String.valueOf(pacjent.getliczbaDni()));

			Szpital.prepStmt.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@FXML
	public void showOnClickLek() {
		try {

			Lek lek = (Lek) table2.getSelectionModel().getSelectedItem();
			String query = "select*from leki";
			Szpital.prepStmt = Szpital.conn.prepareStatement(query);
			getNazwaLeku.setText(lek.getNazwaLeku());
			addIloscTabletek.setText(String.valueOf(lek.getIlosc()));
			Szpital.prepStmt.close();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void loadDataBaseData() {
		String query = "select * from Zalecenia";

		try {
			ResultSet result = Szpital.stat.executeQuery("SELECT * FROM Zalecenia");

			addPesel.clear();
			addImie.clear();
			addNazwisko.clear();
			addPlec.clear();
			addWzrost.clear();
			addWaga.clear();
			addLek.clear();
			addDawka.clear();
			addWiek.clear();
			addLiczbaDni.clear();
			pacjenci.clear();
			addLiczbaDni.clear();

			Szpital.prepStmt = Szpital.conn.prepareStatement(query);
			result = Szpital.prepStmt.executeQuery();
			while (result.next()) {
				pacjenci.add(new Pacjent(result.getInt("numerZalecenia"), result.getString("pesel"),
						result.getString("imie"), result.getString("nazwisko"), result.getString("plec"),
						result.getInt("wzrost"), result.getInt("waga"), result.getString("nazwaLeku"),
						result.getString("dawka"), result.getInt("wiek"), result.getInt("liczbaDni")));
				table.setItems(pacjenci);
			}
			Szpital.prepStmt.close();
			result.close();

		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public void LoadLekiDataBase() {
		String query = "select * from leki";

		try {
			ResultSet result = Szpital.stat.executeQuery("SELECT * FROM leki");
			getNazwaLeku.clear();
			addIloscTabletek.clear();
			leki.clear();
			Szpital.prepStmt = Szpital.conn.prepareStatement(query);
			result = Szpital.prepStmt.executeQuery();
			while (result.next()) {
				leki.add(new Lek(result.getInt("id"), result.getString("nazwaLeku"), result.getString("moc"),
						result.getString("postac"), result.getString("opakowania"),
						result.getString("substancjaCzynna"), result.getInt("ilosc"), result.getString("ulotka")));
				table2.setItems(leki);
			}
			Szpital.prepStmt.close();
			result.close();
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@FXML
	public void deletePacjent() {
		if (isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Blad danych");
			alert.setHeaderText("Blad usuwania zalecenia");
			String s = "Nie wybrano pola do usuniecia";
			alert.setContentText(s);
			alert.show();
		} else {

			int nrZalecenia = 0;
			try {

				Pacjent pacjent = (Pacjent) table.getSelectionModel().getSelectedItem();
				String query = "delete from Zalecenia where numerZalecenia=?";
				Szpital.prepStmt = Szpital.conn.prepareStatement(query);
				nrZalecenia = pacjent.getNumerZalecenia();
				Szpital.prepStmt.setInt(1, nrZalecenia);
				Szpital.prepStmt.executeUpdate();
				Szpital.prepStmt.close();

			} catch (SQLException e) {
				System.out.println(e);
			}
			loadDataBaseData();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Usuwanie zalecenia");
			alert.setHeaderText("Usunieto  z bazy");
			String s = "Zalecenie usuniete pomyslnie";
			alert.setContentText(s);
			alert.show();
			table.refresh();
		}
	}

	@FXML
	public void updatePacjent() {
		if (isEmpty() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Blad danych");
			alert.setHeaderText("Blad aktualizacji");
			String s = "Zaznacz zalecenie, ktore chcesz zaktulizowac";
			alert.setContentText(s);
			alert.show();
		} else {
			Pacjent pacjent = table.getSelectionModel().getSelectedItem();
			String query = "update Zalecenia set pesel=?, imie=?, nazwisko=?, plec=?, wzrost=?, waga=?, nazwaLeku=?, dawka=?, "
					+ "wiek=?, liczbaDni=? where numerZalecenia='" + pacjent.getNumerZalecenia() + "'";
			try {
				CheckPesel c = new CheckPesel(addPesel.getText());

				if (!c.isValid()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Blad danych");
					alert.setHeaderText("Blad edycji ");
					String s = "Podano nieprawidlowy nr pesel";
					alert.setContentText(s);
					alert.show();
					return;
				} else if (!isAlpha(addImie.getText()) || !isAlpha(addNazwisko.getText()) || !isAlpha(addPlec.getText())
						|| isAlpha(addWzrost.getText()) || isAlpha(addWaga.getText()) || isAlpha(addWiek.getText())
						|| isAlpha(addDawka.getText()) || isAlpha(addLiczbaDni.getText())
						|| isAlpha(addIloscTabletek.getText())) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Blad danych");
					alert.setHeaderText("Blad aktualizacji ");
					String s = "Podano nieprawidlowe dane ";
					alert.setContentText(s);
					alert.show();
					return;

				} else {
					Szpital.prepStmt = Szpital.conn.prepareStatement(query);
					Szpital.prepStmt.setString(1, addPesel.getText());
					Szpital.prepStmt.setString(2, addImie.getText());
					Szpital.prepStmt.setString(3, addNazwisko.getText());
					Szpital.prepStmt.setString(4, addPlec.getText());
					Szpital.prepStmt.setString(5, addWzrost.getText());
					Szpital.prepStmt.setString(6, addWaga.getText());
					Szpital.prepStmt.setString(7, addLek.getText());
					Szpital.prepStmt.setString(8, addDawka.getText());
					Szpital.prepStmt.setString(9, addWiek.getText());
					Szpital.prepStmt.setString(10, addLiczbaDni.getText());

					Szpital.prepStmt.execute();
					Szpital.prepStmt.close();

					int zuzycie = Integer.parseInt(addDawka.getText()) * Integer.parseInt(addLiczbaDni.getText());
					String name_temp = addLek.getText();

					int opakowania = Integer.parseInt((leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku()))
							.findAny().orElse(null).getOpakowania()));
					int stanObecny = (leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku())).findAny()
							.orElse(null).getIlosc());
					int dawkaObecna = Integer.parseInt(leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku()))
							.findAny().orElse(null).getMoc());

					if (((opakowania * stanObecny * dawkaObecna) - zuzycie) / (dawkaObecna * opakowania) < 0) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Ostrzezenie");
						alert.setHeaderText("Alarm magazynu");
						String s = "Opakowan jest zbyt malo, by przypisac je pacjentowi!";
						alert.setContentText(s);
						alert.show();
						return;
					} else if (((opakowania * stanObecny * dawkaObecna) - zuzycie) / (dawkaObecna * opakowania) < 20) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Ostrze¿enie");
						alert.setHeaderText("Alarm magazynu");
						String s = "W magazynie pozosta³o mniej ni¿ 20 opakowan! Zamów leki, aby uratowaæ pacjentów!";
						alert.setContentText(s);
						alert.show();
						return;
					} else {
						leki.stream().filter(Lek -> name_temp.equals(Lek.getNazwaLeku())).findAny().orElse(null)
								.setIlosc(((opakowania * stanObecny * dawkaObecna) - zuzycie)
										/ (dawkaObecna * opakowania));
						b.updateLek(name_temp,
								((opakowania * stanObecny * dawkaObecna) - zuzycie) / (dawkaObecna * opakowania));

						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Aktualizacja danych");
						alert.setHeaderText("Zalecenie zaktualizowane pomyslnie");
						String s = "Zalecenie zaktualizowano pomyslnie";
						alert.setContentText(s);
						alert.show();

						table2.refresh();
						loadDataBaseData();
					}
				}

			} catch (SQLException e) {
				System.out.println(e);
			}
		}
	}

	@FXML
	public void updateLeki() {
		if (isEmpty2() == true) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Blad danych");
			alert.setHeaderText("Blad aktualizacji");
			String s = "Zaznacz lek, ktore chcesz zaktulizowac";
			alert.setContentText(s);
			alert.show();
		} else if (Integer.parseInt(addIloscTabletek.getText()) > 0) {
			b.updateLek(getNazwaLeku.getText(), Integer.parseInt(addIloscTabletek.getText()));
			leki.stream().filter(Lek -> getNazwaLeku.getText().equals(Lek.getNazwaLeku())).findAny().orElse(null)
					.setIlosc(Integer.parseInt(addIloscTabletek.getText()));
			table2.refresh();
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Aktualizacja leku");
			alert.setHeaderText("Stan leku na magazynie zaktualizowany pomyslnie");
			String s = "Stan leku zaktualizowany pomyslnie";
			alert.setContentText(s);
			alert.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Blad danych");
			alert.setHeaderText("Blad aktualizacji");
			String s = "Wprowadz dodatnia ilosc opakowan!";
			alert.setContentText(s);
			alert.show();
			return;
		}

	}

	public boolean isAlpha(String name) {
		return name.matches("[a-zA-Z]+");
	}

	public boolean isEmpty() {
		if ((addPesel.getText().isEmpty() == true) || (addImie.getText().isEmpty() == true)
				|| (addNazwisko.getText().isEmpty() == true) || (addPlec.getText().isEmpty() == true)
				|| (addWaga.getText().isEmpty() == true) || (addWzrost.getText().isEmpty() == true)
				|| (addLek.getText().isEmpty() == true) || (addLiczbaDni.getText().isEmpty() == true)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean isEmpty2() {
		if (addIloscTabletek.getText().isEmpty() == true || getNazwaLeku.getText().isEmpty() == true) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
// TODO

		peselCol.setMinWidth(100);
		imieCol.setMinWidth(100);
		nazwiskoCol.setMinWidth(100);
		plecCol.setMinWidth(50);
		wzrostCol.setMinWidth(100);
		wagaCol.setMinWidth(100);
		lekCol.setMinWidth(100);
		dawkaCol.setMinWidth(100);
		wiekCol.setMinWidth(100);

		table2.getItems().setAll(leki);
		iloscCol.setMinWidth(100);
		nazwaLekuCol.setMinWidth(100);
		mocCol.setMinWidth(100);
		postacCol.setMinWidth(100);
		opakowaniaCol.setMinWidth(100);
		substancjaCzynnaCol.setMinWidth(100);
		ulotkaCol.setMinWidth(100);
		numerZalecenia.setMinWidth(100);
		idCol.setMinWidth(100);
		dawkaCol.setMinWidth(100);
		table2.getItems().setAll(this.leki);
		idCol.setCellValueFactory(new PropertyValueFactory("id"));
		iloscCol.setCellValueFactory(new PropertyValueFactory("ilosc"));
		nazwaLekuCol.setCellValueFactory(new PropertyValueFactory("nazwaLeku"));
		mocCol.setCellValueFactory(new PropertyValueFactory("moc"));
		postacCol.setCellValueFactory(new PropertyValueFactory("postac"));
		opakowaniaCol.setCellValueFactory(new PropertyValueFactory("opakowania"));
		substancjaCzynnaCol.setCellValueFactory(new PropertyValueFactory("substancjaCzynna"));
		ulotkaCol.setCellValueFactory(new PropertyValueFactory("ulotka"));

		table.getItems().setAll(this.pacjenci);
		imieCol.setCellValueFactory(new PropertyValueFactory("imie"));
		nazwiskoCol.setCellValueFactory(new PropertyValueFactory("nazwisko"));
		plecCol.setCellValueFactory(new PropertyValueFactory("plec"));
		peselCol.setCellValueFactory(new PropertyValueFactory("pesel"));
		wzrostCol.setCellValueFactory(new PropertyValueFactory("wzrost"));
		wagaCol.setCellValueFactory(new PropertyValueFactory("waga"));
		lekCol.setCellValueFactory(new PropertyValueFactory("nazwaLeku"));
		dawkaCol.setCellValueFactory(new PropertyValueFactory("dawka"));
		wiekCol.setCellValueFactory(new PropertyValueFactory("wiek"));
		numerZalecenia.setCellValueFactory(new PropertyValueFactory("numerZalecenia"));
		table.refresh();
		TextFields.bindAutoCompletion(addLek, leki.stream().map(Lek::getNazwaLeku).collect(Collectors.toList()));

	}

}