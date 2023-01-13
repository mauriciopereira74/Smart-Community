package Controller;

import java.io.IOException;
import java.util.Scanner;

import House.House;
import Simulation.Simulation;
import SmartDevice.SmartBulb;
import SmartDevice.SmartCamera;
import SmartDevice.SmartDevice;
import SmartDevice.SmartSpeaker;
import View.ReaderWriter;
import View.View;

public class Controller {

    public static DataBase loadDatabase() {
        DataBase dataBase;
        Scanner user_input = new Scanner(System.in);
        /* database load code */
        View.askDatabase(1);
        String dataAnswer = user_input.nextLine();
        if (dataAnswer.equals("custom")) {

            try {
                dataBase = new DataBase("database/CustomDatabase", true);
            } catch (IOException | ClassNotFoundException e) {
                View.exceptionPrinter(e);
                dataBase = loadDatabase();
            }
        } else if (dataAnswer.equals("external")) {
            View.askDatabase(2);
            dataAnswer = user_input.nextLine();
            boolean binary = dataAnswer.equals("y");
            View.askDatabase(3);
            String filepath = user_input.nextLine();

            try {
                dataBase = new DataBase("database/" + filepath, binary);
            } catch (IOException | ClassNotFoundException e) {
                View.exceptionPrinter(e);
                dataBase = loadDatabase();
            }
        } else {
            dataBase = loadDatabase();
        }

        return dataBase;
    }

    public static void run() {
        Scanner user_input = new Scanner(System.in);

        DataBase dataBase = Controller.loadDatabase();
        Simulation simulation = new Simulation(dataBase);
        while (true) {
            View.mainMenu();
            View.chooseaOption();
            int option = user_input.nextInt();
            switch (option) {
                case 1:
                    View.printer(dataBase.housesTostring());
                    String housename = ReaderWriter.getString("Please insert house owner's name: ");
                    while (!dataBase.houseExists(housename)) {
                        housename = ReaderWriter.getString("Please insert house owner's name: ");
                    }
                    Controller.houseController(housename, dataBase);
                    break;
                case 2:
                    View.printer(dataBase.sellersToString());
                case 3:
                    dataBase = loadDatabase();
                    break;
                case 5:
                    View.printer(dataBase.housesTostring());
                    ReaderWriter.pressEnterToContinue();
                    break;
                case 6:
                    SimulationController(dataBase, simulation);
                    break;
                case 7:
                    user_input.close();
                    System.exit(0);
                    break;
            }
        }
    }

    public static void houseController(String houseName, DataBase dataBase) {
        View.MenuHouse();
        int input = ReaderWriter.getInt("Please choose an option: ");
        House house = dataBase.getHouse(houseName);
        while (input != 10) {
            if (input == 1) {
                ReaderWriter.printString(house.getLocations().toString());
                String room = ReaderWriter.getString("Please insert the room in wich you want to add the device: ");
                while (!house.hasRoom(room)) {
                    ReaderWriter.getString(house.getLocations().toString());
                    room = ReaderWriter.getString("Please insert the room in wich you want to add the device: ");
                }
                SmartDevice dev = Controller.deviceController(dataBase);
                house.addDeviceRoom(room, dev.getId(), dev);
                ReaderWriter.printString("Device was given with the following id:" + dev.getId());
                ReaderWriter.pressEnterToContinue();
            } else if (input == 2) {
                String room = ReaderWriter.getString("Please insert room name: ");
                dataBase.addRoom(houseName, room);
            } else if (input == 3) {
                ReaderWriter.printString(dataBase.sellersToString());
                String seller = ReaderWriter.getString("Please insert seller name: ");
                while (!dataBase.sellerExists(seller)) {
                    seller = ReaderWriter.getString("Please insert seller name: ");
                }
                dataBase.changeSeller(houseName, seller);
                ReaderWriter.printString("Seller Changed!");
                ReaderWriter.pressEnterToContinue();
            } else if (input == 4) {
                dataBase.devicesToString(houseName);
                int deviceId = ReaderWriter.getInt("Please insert device id:");
                dataBase.setOnDevice(houseName, deviceId);

            } else if (input == 5) {
                dataBase.devicesToString(houseName);
                int deviceIdOff = ReaderWriter.getInt("Please insert device id: ");
                dataBase.setOffDevice(houseName, deviceIdOff);
            } else if (input == 6) {
                String room1 = ReaderWriter.getString("Please insert the room: ");
                while (!dataBase.roomExists(houseName, room1)) {
                    room1 = ReaderWriter.getString("Please insert the room: ");
                }
                dataBase.setOnAll(houseName, room1);
            } else if (input == 7) {
                String room = ReaderWriter.getString("Please insert the room: ");
                while (!dataBase.roomExists(houseName, room)) {
                    room = ReaderWriter.getString("Please insert the room: ");
                }
                dataBase.setOffAll(houseName, room);
            } else if (input == 8) {
                ReaderWriter.printString(house.getLocations().toString());
                String room = ReaderWriter.getString("Please insert the room: ");
                while (!dataBase.roomExists(houseName, room)) {
                    ReaderWriter.printString(house.getLocations().toString());
                    room = ReaderWriter.getString("Please insert the room: ");
                }
                ReaderWriter.printString(house.roomDevicesToString(room));
                ReaderWriter.pressEnterToContinue();
            } else if (input == 9) {
                //House house = dataBase.getHouse(houseName);
                ReaderWriter.printString(house.getLocations().toString());
                int id = ReaderWriter.getInt("Please insert the id of the SmartDevice you want to remove: ");
                while(!dataBase.existDevice(houseName,id)) {
                    ReaderWriter.getInt("Please insert the id of the SmartDevice you want to remove: ");
                }
                dataBase.removeDev(houseName,id);
            }

            View.MenuHouse();
            input = ReaderWriter.getInt("Please choose an option: ");
        }
    }

    public static SmartDevice deviceController(DataBase dataBase) {
        View.MenuDevices();
        int input = ReaderWriter.getInt("Please choose an option: ");
        SmartDevice dev = null;
            if (input == 1) {
                String str = ReaderWriter.getString(
                        "Please select the Volume,Channel,Speakerbrand and Consumption as the following example: \n40,RFM,Marshall,4.91 \n");
                String[] linhaPartida = str.split(",");
                int nome = ReaderWriter.getInt("How do you want to inicialize de device?\n1-On\n2-Off");
                SmartDevice.State state = nome == 1 ? SmartDevice.State.ON : SmartDevice.State.OFF;
                dev = new SmartSpeaker(dataBase.getidCount(), state, Integer.parseInt(linhaPartida[0]), linhaPartida[1],
                        linhaPartida[2], Double.parseDouble(linhaPartida[3]));
                dataBase.setidCount(dataBase.getidCount() + 1);
            }
            if (input == 2) {
                String str2 = ReaderWriter.getString(
                        "Please select the tonality(Neutral,Warm,Cold),dimension and Consumption as the following example: \nNeutral,8,0.16");
                String[] linhaPartida2 = str2.split(",");
                int nome2 = ReaderWriter.getInt("How do you want to inicialize de device?\n1-On\n2-Off");
                SmartDevice.State state2 = nome2 == 1 ? SmartDevice.State.ON : SmartDevice.State.OFF;
                SmartBulb.Tonality ton;
                if (linhaPartida2[0].equals("Neutral"))
                    ton = SmartBulb.Tonality.Neutral;
                else if (linhaPartida2[0].equals("Warm"))
                    ton = SmartBulb.Tonality.Warm;
                else
                    ton = SmartBulb.Tonality.Cold;
                dev = new SmartBulb(dataBase.getidCount(), state2, Integer.parseInt(linhaPartida2[1]),
                        Double.parseDouble(linhaPartida2[2]), ton);
            }
            if (input == 3) {
                String str3 = ReaderWriter.getString(
                        "Please select the Resolution,size and Consumption as the following example:  \n(1024x768),83,7.14");
                String[] linhaPartida3 = str3.split(",");
                String[] cameraResolution = linhaPartida3[0].split("x");
                int cameraHeight = Integer.parseInt(cameraResolution[0].substring(1));
                int cameraWidth = Integer
                        .parseInt(cameraResolution[1].substring(0, cameraResolution[1].length() - 1));
                int nome3 = ReaderWriter.getInt("How do you want to inicialize de device\n1-On\n2-Off");
                SmartDevice.State state3 = nome3 == 1 ? SmartDevice.State.ON : SmartDevice.State.OFF;
                dev = new SmartCamera(dataBase.getidCount(), state3,
                        new SmartCamera.CameraResolution(cameraHeight, cameraWidth), Integer.parseInt(linhaPartida3[1]),
                        Double.parseDouble(linhaPartida3[2]));
            }
        return dev;

    }

    public static void SimulationController(DataBase dataBase, Simulation s) {
        View.MenuSimulation();
        int input = ReaderWriter.getInt("Please choose an option: ");
        while (input != 6) {
            if (input == 1) {
                ReaderWriter.printString(dataBase.housesTostring());
                String houseOwner = ReaderWriter.getString("Please insert house owner's name: ");
                while (!dataBase.houseExists(houseOwner)) {
                    houseOwner = ReaderWriter.getString("Please insert house owner's name: ");
                }
                long days = ReaderWriter.getLong("Please insert the days:");
                s.simulateOne(days, houseOwner);
                ReaderWriter.printString(s.toString());
                ReaderWriter.pressEnterToContinue();
                break;
            } else if (input == 2) {
                long days = ReaderWriter.getLong("Please insert number of days:");
                s.simulate(days);
                View.printer(s.toString());
                ReaderWriter.pressEnterToContinue();
            } else if (input == 3) {

                ReaderWriter.printString("-------------Bill with most consumption------------\n" + s.mostConsumption());
                ReaderWriter.pressEnterToContinue();
            } else
            View.mainMenu();
            input = ReaderWriter.getInt("Please choose an option: ");
        }
    }
}