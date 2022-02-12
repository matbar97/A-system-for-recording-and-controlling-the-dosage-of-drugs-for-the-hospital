package model;

public class Pacjent {

	private String pesel, imie, nazwisko, plec, nazwaLeku, dawka;
	private int wzrost, waga, wiek, liczbaDni, numerZalecenia;

	public Pacjent(int numerZalecenia, String pesel, String imie, String nazwisko, String plec, int wzrost, int waga,
			String nazwaLeku, String dawka, int wiek, int liczbaDni) {
		this.numerZalecenia = numerZalecenia;
		this.pesel = pesel;
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.plec = plec;
		this.wzrost = wzrost;
		this.waga = waga;
		this.nazwaLeku = nazwaLeku;
		this.dawka = dawka;
		this.wiek = wiek;
		this.liczbaDni = liczbaDni;
	}

	public int getNumerZalecenia() {
		return numerZalecenia;
	}

	public void setNumerZalecenia(int numerZalecenia) {
		this.numerZalecenia = numerZalecenia;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getPlec() {
		return plec;
	}

	public void setPlec(String plec) {
		this.plec = plec;
	}

	public String getNazwaLeku() {
		return nazwaLeku;
	}

	public void setNazwaLeku(String nazwaLeku) {
		this.nazwaLeku = nazwaLeku;
	}

	public String getDawka() {
		return dawka;
	}

	public void setDawka(String dawka) {
		this.dawka = dawka;
	}

	public int getWzrost() {
		return wzrost;
	}

	public void setWzrost(int wzrost) {
		this.wzrost = wzrost;
	}

	public int getWaga() {
		return waga;
	}

	public void setWaga(int waga) {
		this.waga = waga;
	}

	public int getWiek() {
		return wiek;
	}

	public void setWiek(int wiek) {
		this.wiek = wiek;
	}

	public int getliczbaDni() {
		return liczbaDni;
	}

	public void setliczbaDni(int liczbaDni) {
		this.liczbaDni = liczbaDni;
	}

	@Override
	public String toString() {
		return "Pacjent [pesel=" + pesel + ", imie=" + imie + ", nazwisko=" + nazwisko + ", plec=" + plec
				+ ", nazwaLeku=" + nazwaLeku + ", dawka=" + dawka + ", wzrost=" + wzrost + ", waga=" + waga + ", wiek="
				+ wiek + ", liczbaDni=" + liczbaDni + ", numerZalecenia=" + numerZalecenia + "]";
	}




}
