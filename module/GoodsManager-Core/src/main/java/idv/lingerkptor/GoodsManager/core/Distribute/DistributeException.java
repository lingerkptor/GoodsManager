package idv.lingerkptor.GoodsManager.core.Distribute;

public class DistributeException extends Exception {
    private Location location;

    public DistributeException(String message) {
        super(message);
    }

    public DistributeException(Throwable cause) {
        super(cause);
    }

    public DistributeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DistributeException(Location location, String message) {
        super(message);
        this.location = location;
    }

}
