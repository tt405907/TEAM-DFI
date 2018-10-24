package dé;
public  class FaceVictoire {
    private int nombreVictoire;
    public void FaceOr(int n) {
        assert (n==2||n==3||n=4);
        this.nombreVictoire=n;
    }
    void appliquer(Joueur J){
        J.addVictoire(nombreVictoire);
    }
}
