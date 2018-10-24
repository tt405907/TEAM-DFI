package dé;
public  class FaceLune {
    private int nombreLune;
    public void FaceLune(int n) {
        assert (n==1||n==2);
        this.nombreLune=n;
    }
    void appliquer(Joueur J){
        J.addLune(nombreLune);
    }
}
