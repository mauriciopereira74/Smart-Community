package House;

import java.time.LocalDate;

public class Bill {
    private Double totalCost;
    private Double consumo;
    private LocalDate emissionDate;
    private LocalDate startDate;
    private long period;

    private int numDevices;

    private String HouseOwner;

    public Bill(Double totalCost, Double consumo, LocalDate emissionDate, LocalDate startDate, String HouseOwner,
            long period) {
        this.totalCost = totalCost;
        this.consumo = consumo;
        this.emissionDate = emissionDate;
        this.startDate = startDate;
        this.HouseOwner = HouseOwner;
        this.period = period;
    }

    public Bill() {
        this.totalCost = 0.0;
        this.consumo = 0.0;
        this.emissionDate = LocalDate.now();
        this.startDate = LocalDate.now();
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public void setConsumo(Double consumo) {
        this.consumo = consumo;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setNumDevices(int numDevices) {
        this.numDevices = numDevices;
    }

    public void setHouseOwner(String houseOwner) {
        HouseOwner = houseOwner;
    }

    public double getConsumption() {
        return this.consumo;
    }

    @Override
    public String toString() {

        String result = "OwnerName : " + this.HouseOwner;
        result += "\nTotal Cost: " + this.totalCost;
        result += "\nTotal Consumption: " + this.consumo;
        result += "\n" + this.startDate + "---" + this.emissionDate;
        result += "\nPeriod: " + this.period + " Days";
        result += "\n------------------------------------------\n";
        return result;
    }
}
