package model;

public class Lek {

	private int id, ilosc;
	private String nazwaLeku, moc, postac, opakowania, substancjaCzynna, ulotka;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIlosc() {
		return ilosc;
	}

	public void setIlosc(int ilosc) {
		this.ilosc = ilosc;
	}

	public String getNazwaLeku() {
		return nazwaLeku;
	}

	public void setNazwaLeku(String nazwaLeku) {
		this.nazwaLeku = nazwaLeku;
	}

	public String getMoc() {
		return moc;
	}

	public void setMoc(String moc) {
		this.moc = moc;
	}

	public String getPostac() {
		return postac;
	}

	public void setPostac(String postac) {
		this.postac = postac;
	}

	public String getOpakowania() {
		return opakowania;
	}

	public void setOpakowania(String opakowania) {
		this.opakowania = opakowania;
	}

	public String getSubstancjaCzynna() {
		return substancjaCzynna;
	}

	public void setSubstancjaCzynna(String substancjaCzynna) {
		this.substancjaCzynna = substancjaCzynna;
	}

	public String getUlotka() {
		return ulotka;
	}

	public void setUlotka(String ulotka) {
		this.ulotka = ulotka;
	}

	public Lek(int id, String nazwaLeku, String moc, String postac, String opakowania, String substancjaCzynna,
			int ilosc, String ulotka) {
		this.id = id;
		this.nazwaLeku = nazwaLeku;
		this.moc = moc;
		this.postac = postac;
		this.opakowania = opakowania;
		this.substancjaCzynna = substancjaCzynna;
		this.ilosc = ilosc;
		this.ulotka = ulotka;
	}

	@Override
	public String toString() {
		return "Lek [id=" + id + ", ilosc=" + ilosc + ", nazwaLeku=" + nazwaLeku + ", moc=" + moc + ", postac=" + postac
				+ ", opakowania=" + opakowania + ", substancjaCzynna=" + substancjaCzynna + ", ulotka=" + ulotka + "]";
	}

}
