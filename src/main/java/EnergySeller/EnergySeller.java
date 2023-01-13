package EnergySeller;

import java.util.Random;

public class EnergySeller {
    private String energySeller;
    private Double priceKw = 0.20; // -> EnergySeller and the price of the Kw
    Random rand = new Random();

    private Double tax = 0.10;

    // Empty Constructor
    public EnergySeller() {
        this.energySeller = " ";
        this.priceKw = (0.0);
        this.tax = (0.0);
    }

    @Override
    public String toString() {
        return "------------------------EnergySeller---------------\n" +
                "Name= " + energySeller + '\n' +
                "priceKw=" + priceKw + ", tax=" + tax + "\n";
    }

    public EnergySeller(String energySeller) {
        this.energySeller = energySeller;

    }

    public EnergySeller(EnergySeller seller) {
        this.energySeller = seller.getEnergySeller();

    }

    public Double getTax() {
        return this.tax;
    }

    public Double getPriceKw() {
        return priceKw;
    }

    public String getEnergySeller() {
        return energySeller;
    }

    public void setEnergySeller(String energySeller) {
        this.energySeller = energySeller;
    }

    public EnergySeller clone() {
        return new EnergySeller(this);
    }

    public double RandomTax() {
        int r = new Random().nextInt(3);
        switch (r) {
            case 0:
                this.tax = tax + 0.06;
            case 1:
                this.tax = tax + 0.02;
            case 2:
                this.tax = tax + 0.04;
            default:
                this.tax = tax;
        }
        return getTax();
    }

    public double RandomPriceKw() {
        int r = new Random().nextInt(3);
        switch (r) {
            case 0:
                this.priceKw = priceKw + 0.07;
            case 1:
                this.priceKw = priceKw + 0.02;
            case 2:
                this.priceKw = priceKw + 0.04;
            default:
                this.priceKw = priceKw;
        }
        return getPriceKw();
    }

}