package SmartDevice;

import java.util.Objects;

public class SmartCamera extends SmartDevice{
    public record CameraResolution (int height, int width) {
    }
    private CameraResolution resolution;
    private int size;


    private double consumption;

    public SmartCamera () {
        this.resolution = new CameraResolution(0,0);
        this.size = 0;
    }

    public SmartCamera (Integer id, State state,CameraResolution resolution, int size, Double consumption) {
        super(id,state);
        this.resolution = new CameraResolution(resolution.height,resolution.width);
        this.size = size;
        this.consumption = consumption;
    }

    public SmartCamera (SmartCamera dev) {
        super(dev);
        this.resolution =dev.getResolution();
        this.size = dev.getSize();
        this.consumption = dev.getConsumption();
    }

    public CameraResolution getResolution() {
        return resolution;
    }

    public int getSize() {
        return size;
    }

    public void setResolution(CameraResolution resolution) {
        this.resolution = resolution;
    }

    public void setSize(int size) {
        this.size = size;
    }


    public void setConsumption(double consumption) {
        this.consumption = consumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartCamera that = (SmartCamera) o;
        return size == that.size && Objects.equals(resolution, that.resolution);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SmartCamera { ");
        sb.append(super.toString());
        sb.append(" Resolution -> " ).append(this.resolution).append(",");
        sb.append(" Size -> ").append(size).append(",");
        sb.append(" Consumption -> ").append(consumption).append(" }");
        return sb.toString();
    }
    @Override
    public SmartCamera clone () {
        return new SmartCamera(this);
    }

    /** The number that represents a position */
    public Integer representedBy() {
        return 1;
    }

    public Double getConsumption(){
        if(this.getState() == State.ON) return this.resolution.width *this.resolution.height * size*consumption;
        else return 0.0;
    }

}