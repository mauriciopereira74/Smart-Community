package SmartDevice;


import House.House;

import java.io.Serializable;
import java.util.Random;


public abstract class SmartDevice implements Comparable<SmartDevice>, Serializable {
    private int id;
    private State state;

    public enum State {
        OFF,
        ON
    }

    /** O construtor vazio cria um Device (inútil) e sem informação. */
    public SmartDevice () {
        this.state = State.OFF;
        this.id = 0;
    }
    public SmartDevice (String id) {
        this.state = State.OFF;
    }
    /** O construtor "Sub-Standard" cria um device (útil),
     * com toda a informação e inicia com o modo que lhe é passado. */
    public SmartDevice (int id, State state) {
        this.id = id;
        this.state = state;
    }

    public SmartDevice (SmartDevice dev) {
        this.id= dev.getId();
        this.state= dev.getState();
    }

    public int getId() {
        return id;
    }

    public State getState() {
        return state;
    }

    public void setOn() {
        this.state = State.ON; }

    public void setOff() {
        this.state = State.OFF;}

    public void setState() {
        this.state = state;
    }
    /** Basic equals method - Same id implies same device */
    public boolean equals(Object obj){
        if (this == obj) return true;

        if ((obj == null)) return false;

        SmartDevice p = (SmartDevice) obj;
        return this.id == (p.getId()) && this.state.equals(p.getState());
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Id -> ").append(this.id).append(",");
        sb.append(" State -> ").append(this.state).append(",");
        return sb.toString();
    }

    /** The number that represents a position */
    public abstract Integer representedBy();
    public abstract SmartDevice clone();

    public Double getConsumption() {
        return 0.0;
    }
    public int compareTo(SmartDevice otherDevices) {
        return Integer.compare(representedBy(), otherDevices.representedBy());
    }


}
