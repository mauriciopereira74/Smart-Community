package SmartDevice;

public class SmartBulb extends SmartDevice {
    private double dimension;
    private double consumption;
    private Tonality ton;

    public enum Tonality {
        Neutral,
        Warm,
        Cold
    }

    public SmartBulb() {
        super();
        this.consumption = 0;
        this.ton = Tonality.Neutral;
    }

    public SmartBulb(int id, State state, int dimension, double consumption, Tonality ton) {
        super(id,state);
        this.dimension = dimension;
        this.consumption = consumption;
        this.ton = ton;
    }

    public SmartBulb(SmartBulb bulb) {
        super(bulb);
        this.dimension = bulb.getDimension();
        this.consumption = bulb.getConsumption();
        this.ton = bulb.getTon();
    }

    public double getDimension() {
        return dimension;
    }

    public Tonality getTon() {
        return ton;
    }

    public void setTon(Tonality ton) {
        this.ton = ton;
    }

    public Double getConsumption() {

        if (this.getState() == State.ON) {
            if (getTon() == Tonality.Neutral)
                return 0.5 * consumption;
            else if (getTon() == Tonality.Cold)
                return 0.1 * consumption;
            else
                return consumption;
        }
        else return 0.0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        SmartBulb smartBulb = (SmartBulb) o;
        return Double.compare(smartBulb.dimension, dimension) == 0
                && Double.compare(smartBulb.consumption, consumption) == 0 && ton == smartBulb.ton;
    }

    @Override
    public SmartBulb clone() {
        return new SmartBulb(this);
    }

    /** The number that represents a position */
    public Integer representedBy() {
        return 2;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SmartBulb {");
        sb.append(super.toString());
        sb.append(" Tonality -> ").append(this.ton).append(",");
        sb.append(" Dimension -> ").append(this.dimension).append(",");
        sb.append(" Consumption -> ").append(this.consumption).append(" }");
        return sb.toString();
    }

}
