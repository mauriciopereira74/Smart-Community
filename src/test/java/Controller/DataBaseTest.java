package Controller;

import EnergySeller.EnergySeller;
import House.House;
import SmartDevice.SmartBulb;
import SmartDevice.SmartDevice;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import static org.junit.Assert.*;

public class DataBaseTest {
    @Test
    public void DataBaseTest() throws IOException, ClassNotFoundException {
        DataBase database = new DataBase("database/logs.txt",false);


    }
}