public class Magazine extends Item {
    private String org;
    private int vol, nro, ano;

    public Magazine(String titulo, String org, int vol, int nro, int ano) {
        super(titulo);
        this.org = org;
        this.vol = vol;
        this.nro = nro;
        this.ano = ano;
    }

    public String getOrg() { return org; }

    public int getVol() { return vol; }

    public int getNro() { return nro; }

    public int getAno() { return ano; }

    public String description() {
        return "Revista: " + titulo + ", " + org + ", "
                + vol + ", " + nro + ", " + ano + '\n';
    }
}
