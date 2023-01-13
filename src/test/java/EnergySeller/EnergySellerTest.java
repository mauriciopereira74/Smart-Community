package EnergySeller;

import Exceptions.DeviceDoesNotExistException;
import Exceptions.RoomDoesNotExistException;
import House.House;
import SmartDevice.SmartDevice;
import SmartDevice.SmartSpeaker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EnergySellerTest {

    @Test
    public void getSellerConsumption() {
        Map<String,Double> list = new HashMap<>();
        list.put("Edp",5.3);
        list.put("Meo Energia",3.4);
        list.put("Endesa",2.9);
        list.put("Enat",1.9);
       // EnergySeller seller = new EnergySeller("Edp",list);
        //System.out.println(seller.getSellerConsumption("Ende"));
        for (Map.Entry<String,Double> entry : list.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }
    }
}
