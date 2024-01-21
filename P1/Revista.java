public class Revista extends Item {
    String org;
    int vol, nro;

    public Revista(String titulo, String org, int vol, int nro, int ano) {
        super(titulo, ano);
        this.org = org;
        this.vol = vol;
        this.nro = nro;
    }

    public String getOrg() { return this.org; }

    public int getVol() { return this.vol; }

    public int getNro() { return this.nro; }
}
