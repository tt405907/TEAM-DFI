package dé;
public  class FaceOr {
    private int nombreOr;
    public void FaceOr(int n) {
        assert (n==1||n==3||n=4||n=6);
        this.nombreOr=n;
    }
    void appliquer(Joueur J){
        J.addOr(nombreOr);
    }
}
