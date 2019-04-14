package shaggydev.models;

public class MDS {
    private static MDS ourInstance = new MDS();

    public static MDS getInstance() {
        return ourInstance;
    }

    private MDS() {
    }
}
