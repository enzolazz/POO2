public class Revista extends Item {
    private String org;
    private int vol, nro, ano;

    public Revista(String titulo, String org, int vol, int nro, int ano) {
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

    public String descricao() {
        return "Revista: " + titulo + ", " + org + ", "
                + vol + ", " + nro + ", " + ano + '\n';
    }
}
