package dé;
public  class FaceSoleil {
    public nombreSoleil;
    public FaceSoleil(int n) {
        assert (n==1||n==2);
        this.nombreSoleil=n;
    }
    void appliquer(Joueur J){
        J.addSoleil(nombreSoleil);
    }
}
