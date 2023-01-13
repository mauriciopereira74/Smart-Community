package Controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import EnergySeller.EnergySeller;
import House.House;
import SmartDevice.SmartBulb;
import SmartDevice.SmartCamera;
import SmartDevice.SmartDevice;
import SmartDevice.SmartSpeaker;

public class DataBase implements Serializable {
    private List<House> houses;
    private List<EnergySeller> sellers;
    private int idCount;
    // Constructors

    /**
     * The "empty" constructor creates a new database whit no info.
     */
    public DataBase() {
        this.houses = new ArrayList<>();
    }

    /**
     * The "parametric" constructor creates a database, given another the
     * parameters.
     */
    public DataBase(List<House> house, List<EnergySeller> seller) {

        this.houses = new ArrayList<>(houses);
        this.sellers = new ArrayList<>(sellers);
    }

    /**
     * The "copy paste" constructor creates a database, given another database.
     */
    public DataBase(DataBase dataBase) {

        this.houses = new ArrayList<>(dataBase.houses);
        this.sellers = new ArrayList<>(dataBase.sellers);

    }

    /**
     * Loads a Database on a binary file
     */
    public DataBase(String filepath, boolean binary) throws IOException, ClassNotFoundException {
        /*
         * if (binary) {
         * ObjectInputStream is = new ObjectInputStream(new FileInputStream(filepath));
         * DataBase temp = new DataBase((DataBase) is.readObject());
         * DataBase temp2 = new DataBase((DataBase) is.readObject());
         * this.seller = temp.getSeller();
         * this.house = temp2.getHouse();
         * is.close();
         * }
         */

        this.sellers = new ArrayList<>();
        this.houses = new ArrayList<>();

        File file = new File(filepath);
        Scanner readFile = new Scanner(file);
        String line;
        House house = null;
        Map<String, List<Integer>> div = null;
        Map<Integer, SmartDevice> devices = null;
        String divName = null;
        this.idCount = 1;

        /* Random State */

        while (readFile.hasNextLine()) {
            boolean state = new Random().nextBoolean();
            SmartDevice.State s = state ? SmartDevice.State.ON : SmartDevice.State.OFF;
            line = readFile.nextLine();
            String[] linhaPartida = line.split(":");
            // System.out.println(linhaPartida[0]);
            switch (linhaPartida[0]) {
                case "Fornecedor":
                    sellers.add(new EnergySeller(linhaPartida[1]));
                    break;
                case "Casa":
                    if (house != null) {
                        house.setLocations(new HashMap<>(div));
                        house.setDevices(new HashMap<>(devices));
                        this.houses.add(new House(house));
                    }
                    div = new HashMap<>();
                    devices = new HashMap<>();
                    String[] nextComma = linhaPartida[1].split(",");
                    House newHouse = new House();
                    newHouse.setOwnerName(nextComma[0]);
                    newHouse.setNif(nextComma[1]);
                    this.sellers.stream().filter(e -> e.getEnergySeller().equals(nextComma[2])).findAny()
                            .ifPresent(seller -> newHouse.setSeller(seller));
                    house = new House(newHouse);
                    break;
                case "Divisao":
                    div.put(linhaPartida[1], new ArrayList<>());
                    divName = linhaPartida[1];
                    break;
                case "SmartBulb":
                    String[] nextCommaBulb = linhaPartida[1].split(",");
                    div.get(divName).add(idCount);
                    SmartBulb.Tonality ton;
                    if (nextCommaBulb[0].equals("Neutral")) ton = SmartBulb.Tonality.Neutral;
                    else if (nextCommaBulb[0].equals("Cold")) ton = SmartBulb.Tonality.Cold;
                    else ton = SmartBulb.Tonality.Warm;

                    devices.put(idCount, new SmartBulb(idCount, s, Integer.parseInt(nextCommaBulb[1]),
                            Double.parseDouble(nextCommaBulb[2]), ton));
                    idCount++;
                    break;
                case "SmartCamera":
                    String[] nextCommaCamera = linhaPartida[1].split(",");
                    div.get(divName).add(idCount);
                    String[] cameraResolution = nextCommaCamera[0].split("x");
                    int cameraHeight = Integer.parseInt(cameraResolution[0].substring(1));
                    int cameraWidth = Integer
                            .parseInt(cameraResolution[1].substring(0, cameraResolution[1].length() - 1));
                    // System.out.println(cameraWidth);
                    devices.put(idCount,
                            new SmartCamera(idCount, s, new SmartCamera.CameraResolution(cameraHeight, cameraWidth),
                                    Integer.parseInt(nextCommaCamera[1]), Double.parseDouble(nextCommaCamera[2])));
                    idCount++;
                    break;
                case "SmartSpeaker":
                    String[] nextCommaSpeaker = linhaPartida[1].split(",");
                    div.get(divName).add(idCount);
                    String marca = nextCommaSpeaker[1];
                    // System.out.println(marca);
                    devices.put(idCount, new SmartSpeaker(idCount, s, Integer.parseInt(nextCommaSpeaker[0]),
                            nextCommaSpeaker[1], nextCommaSpeaker[2], Double.parseDouble(nextCommaSpeaker[3])));
                    idCount++;
            }

        }
    }

    /*
     * public List<House> getHouse(){
     * return house.stream().collect(Collectors.toList(House::getDevices, House ::
     * clone));
     * }
     * 
     * public void setTeams(List<House> house) {
     * this.house = house.stream().collect(Collectors.toList(House::getDevices,
     * House::clone));
     * }
     * /*
     * public List<EnergySeller> getSeller(){
     * return
     * seller.stream().collect(Collectors.toList(EnergySeller::getEnergySeller,
     * EnergySeller :: clone));
     * }
     * 
     * public void setTeams(List<EnergySeller> seller) {
     * this.seller =
     * seller.stream().collect(Collectors.toList(EnergySeller::getEnergySeller,
     * EnergySeller :: clone));
     * }
     */

    public List<House> getHouses() {
        return new ArrayList<>(this.houses);
    }

    /** Verifies if house exists */
    public boolean houseExists(String ownerName) {
        return houses.stream().anyMatch(x -> x.getOwnerName().equals(ownerName));
    }

    public boolean sellerExists(String sellerName) {
        return sellers.stream().anyMatch(x -> x.getEnergySeller().equals(sellerName));
    }

    public boolean deviceExists(String houseName, int DeviceId) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = new House(h);
                break;
            }
        }
        return house.getDevices().containsKey(DeviceId);
    }

    public boolean roomExists(String houseName, String room) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = new House(h);
                break;
            }
        }
        return house.hasRoom(room);
    }

    public void addDevice(String houseOwner, SmartDevice s, String location) {
        for (House house : this.houses) {
            if (house.getOwnerName().equals(houseOwner)) {
                house.addDevice(s);
                house.addToRoom(location, s.getId());
                break;
            }
        }
    }

    // Adds a room to the given house
    public void addRoom(String houseOwner, String room) {
        for (House house : this.houses) {
            if (house.getOwnerName().equals(houseOwner)) {
                house.addRoom(room);
                break;
            }
        }
    }

    // changes seller
    public void changeSeller(String houseOwner, String sellerName) {
        EnergySeller newSeller = null;
        for (EnergySeller seller : sellers) {
            if (seller.getEnergySeller().equals(sellerName)) {
                newSeller = seller;
                break;
            }
        }
        for (House house : this.houses) {
            if (house.getOwnerName().equals(houseOwner)) {
                house.setSeller(newSeller);
                break;
            }
        }
    }

    public int getidCount() {
        return this.idCount;
    }

    public void setidCount(int idCount) {
        this.idCount = idCount;
    }

    public House getHouse(String houseOwner) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseOwner)) {
                house = new House(h);
                break;
            }
        }
        return house;
    }

    public void setOnDevice(String houseName, int id) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = h;
            }
        }
        house.setDeviceOn(id);
    }

    public void setOnAll(String houseName, String room) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = h;
            }
        }
        house.setAllOn(room);
    }

    public void setOffAll(String houseName, String room) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = h;
            }
        }
        house.setAllOff(room);
    }

    public boolean existDevice(String houseName, int id) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = h;
            }
        }
        return house.existDev(id);
    }
    public void removeDev(String houseName,int id) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = h;
            }
        }
        assert house != null;
        house.removeDevice(id);
    }
    public void setOffDevice(String houseName, int id) {
        House house = null;
        for (House h : this.houses) {
            if (h.getOwnerName().equals(houseName)) {
                house = h;
            }
        }
        house.setDeviceOff(id);
    }

    public String housesTostring() {
        StringBuilder result = new StringBuilder();
        for (House house : this.houses) {
            result.append(house.toString());
        }
        return result.toString();
    }

    public String sellersToString() {
        StringBuilder result = new StringBuilder();
        for (EnergySeller seller : sellers) {
            result.append(seller.toString()).append("\n");
        }
        return result.toString();
    }

    public String devicesToString(String houseName) {
        House newhouse = null;
        for (House house : this.houses) {
            if (house.getOwnerName().equals(houseName)) {
                newhouse = house;
            }
        }
        StringBuilder result = new StringBuilder();
        assert newhouse != null;
        for (SmartDevice device : newhouse.getDevices().values()) {
            result.append(device.toString()).append("\n");
        }
        return result.toString();

    }
}

