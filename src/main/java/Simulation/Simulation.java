package Simulation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Controller.DataBase;
import House.Bill;
import House.House;

public class Simulation {
    /* Class Variables */
    private DataBase database;
    private List<Bill> bills; // Structure with nif and respective houses;
    private LocalDate currentDate;

    public Simulation(DataBase database) {
        this.database = database;
        this.bills = new ArrayList<>();
        currentDate = LocalDate.now();
    }

    /* Getters/Setters */

    /* Class Methods */
    public void simulate(long days) {
        List<House> houses = database.getHouses();
        // long daysDifference = ChronoUnit.DAYS.between(this.currentDate, jumpTo);

        for (House house : houses) {

            bills.add(
                    new Bill(house.calculateBill(days), house.consumptionDev(), this.currentDate.plusDays(days),
                            this.currentDate,
                            house.getOwnerName(), days));

        }
        this.currentDate = this.currentDate.plusDays(days);
    }

    public void simulateOne(long days, String houseOwner) {
        House house = database.getHouse(houseOwner);
        bills.add(
                new Bill(house.calculateBill(days), house.consumptionDev(), this.currentDate.plusDays(days),
                        this.currentDate,
                        house.getOwnerName(), days));
    }

    public String mostConsumption() {
        double result = -1.0;
        Bill b = null;
        for (Bill bill : this.bills) {
            if (bill.getConsumption() > result) {
                b = bill;
                result = bill.getConsumption();
            }
        }
        if (b == null)
            return "Simulation not done yet!";
        return b.toString();
    }

    /* Common Methods */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Bill bill : bills) {
            result.append(bill.toString()).append("\n");
        }
        return result.toString();
    }

}
