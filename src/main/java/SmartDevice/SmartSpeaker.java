package SmartDevice;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

/**
 * This class represents a SmartSpeaker device
 * The comments are mainly written in English.
 * */
public class SmartSpeaker extends SmartDevice{
    public static final int MAX = 100; //volume máximo

    private int volume;
    private String channel;

    private String speakerBrand;
    private Double consumption;

    /**
     * Constructor for objects of class SmartSpeaker
     */
    public SmartSpeaker() {
        // initialise instance variables
        super();
        this.volume = 0;
        this.channel = "";
        this.speakerBrand = "";
    }

    public SmartSpeaker(Integer id, State state, int volume, String channel, String speakerBrand,Double consumption) {
        super(id, state);
        this.volume = volume;
        this.channel = channel;
        this.speakerBrand = speakerBrand;
        this.consumption= consumption;
    }
    public SmartSpeaker(SmartSpeaker dev) {
        super(dev);
        this.speakerBrand = dev.getSpeakerBrand();
        this.channel = dev.getChannel();
        this.volume = dev.getVolume();
        this.consumption = dev.getConsumption();
    }

    public Double getConsumption() {
        if(this.getState() == State.ON) return volume*0.1*consumption;
        else return 0.0;
    }

    public void volumeUp() {
        if (this.volume<MAX) this.volume++;
    }

    public void volumeDown() {
        if (this.volume>0) this.volume--;
    }

    public int getVolume()
    {
        return this.volume;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("SmartSpeaker {");
        sb.append(super.toString());
        sb.append(" Volume -> ").append(this.volume).append(",");
        sb.append(" Channel -> ").append(this.channel).append(",");
        sb.append(" SpeakerBrand -> ").append(this.speakerBrand).append(",");
        sb.append(" Consumption -> ").append(this.consumption).append(" }");
        return sb.toString();
    }

    public String getChannel()
    {
        return this.channel;
    }
    public String getSpeakerBrand(){
        return this.speakerBrand;
    }

    public void setChannel(String c)
    {
        this.channel = c;
    }
    public Integer representedBy() {
        return 1;}
    @Override
    public SmartSpeaker clone () {
        return new SmartSpeaker(this);
    }

}
