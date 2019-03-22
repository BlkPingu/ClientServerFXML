package backend.storage.administration;

import backend.enums.Hazard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import backend.storage.cargo.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {

    private LiquidCargo liquidCargo;
    private BoxedCargo boxedCargo;
    private Warehouse warehouse;
    private MixedDryLiquid mixedDryLiquid;
    private MixedDryLiquidBoxed mixedDryLiquidBoxed;
    private LiquidCargo noHazardCargo;
    private LiquidCargo oneHazardCargo;
    private BoxedCargo twoHazardCargo;
    private DryCargo threeHazardCargo;
    private MixedDryLiquid allHazardCargo;


    @BeforeEach
    void setUp(){

        //One warehouse required for method calls
        warehouse = new Warehouse(10, 5000);

        //4 Hazard Collections required with and without entries
        Collection<Hazard> noHazard = Collections.emptyList();
        Collection<Hazard> oneHazard = Collections.singletonList(Hazard.explosive);
        Collection<Hazard> twoHazard = Arrays.asList(Hazard.explosive, Hazard.flammable);
        Collection<Hazard> threeHazard = Arrays.asList(Hazard.explosive, Hazard.flammable, Hazard.radioactive);
        Collection<Hazard> allHazard = Arrays.asList(Hazard.explosive, Hazard.flammable, Hazard.radioactive, Hazard.toxic);

        //Minimum of 2 Customers required to check for existing customers
        Customer customerCargo1 = new Customer("C1");
        Customer customerCargo2 = new Customer("C2");

        //backend.storage.backend.storage.cargo with different all hazard options
        noHazardCargo = new LiquidCargo(5, customerCargo1, noHazard, true);
        oneHazardCargo = new LiquidCargo(5, customerCargo1, oneHazard, true);
        twoHazardCargo = new BoxedCargo(10, customerCargo2, twoHazard, true);
        threeHazardCargo = new DryCargo(5, customerCargo2, threeHazard, true);
        allHazardCargo = new MixedDryLiquid(10, customerCargo2, allHazard, false, true);

        //3 different Cargos types required for size, customer and hazard checks
        liquidCargo = new LiquidCargo(5, customerCargo1, oneHazard, true);
        boxedCargo = new BoxedCargo(100, customerCargo2, noHazard, true);
        mixedDryLiquid = new MixedDryLiquid(10, customerCargo2, twoHazard, false, true);
        mixedDryLiquidBoxed = new MixedDryLiquidBoxed(10, customerCargo2, allHazard, false, true,false);
    }


    @Test
    void newCargo(){
        warehouse.newCargo(liquidCargo);

        assertEquals(1, warehouse.allCargo.size());
        assertTrue(warehouse.allCargo.values().contains(liquidCargo));
    }


    @Test
    void newCargo2(){
        warehouse.newCargo(boxedCargo);

        assertNotNull(warehouse.allCargo);
        assertFalse(warehouse.allCargo.values().contains(boxedCargo));
    }

    @Test
    void newCargo3() {
        warehouse.volumeStored = 5000;

        warehouse.newCargo(liquidCargo);

        assertEquals(0, warehouse.allCargo.size());
        assertFalse(warehouse.allCargo.values().contains(liquidCargo));
    }

    @Test
    void newCargo4() {
        warehouse.newCargo(null);
        assertTrue(warehouse.allCargo.isEmpty());
    }


    @Test
    void deleteCargo() {
        warehouse.newCargo(liquidCargo);
        System.out.println(warehouse.allCargo);

        warehouse.deleteCargo(0);

        assertTrue(warehouse.allCargo.isEmpty());
        assertEquals(0, warehouse.volumeStored);

    }

    @Test
    void deleteCargo2() {

        warehouse.newCargo(liquidCargo);
        warehouse.newCargo(mixedDryLiquid);

        warehouse.deleteCargo(0);

        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquid));
        assertTrue(warehouse.cargoUnitData.containsKey(mixedDryLiquid));
        assertEquals(10, warehouse.volumeStored);
    }

    @Test
    void deleteCargo3() {

        warehouse.newCargo(liquidCargo);
        warehouse.newCargo(mixedDryLiquid);

        warehouse.deleteCargo(null);

        assertTrue(warehouse.allCargo.containsValue(liquidCargo));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquid));

        assertTrue(warehouse.cargoUnitData.containsKey(liquidCargo));
        assertTrue(warehouse.cargoUnitData.containsKey(mixedDryLiquid));
        assertEquals(15, warehouse.volumeStored);
    }

    @Test
    void deleteCargo4() {


        warehouse.deleteCargo(null);
        assertTrue(warehouse.allCargo.isEmpty());
        assertTrue(warehouse.cargoUnitData.isEmpty());
        assertEquals(0, warehouse.volumeStored);
    }

    @Test
    void deleteCargo5() {

        assertTrue(warehouse.allCargo.isEmpty());

        warehouse.deleteCargo(1);

        assertTrue(warehouse.allCargo.isEmpty());
        assertTrue(warehouse.cargoUnitData.isEmpty());
        assertEquals(0, warehouse.volumeStored);
    }

    @Test
    void getCustomersCargoAmount() {
        warehouse.newCargo(liquidCargo); //owned by Customer C1
        warehouse.newCargo(mixedDryLiquidBoxed); //owned by Customer C2
        assertTrue(warehouse.allCargo.containsValue(liquidCargo));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquidBoxed));
        warehouse.getCustomersCargoAmount("C1");
    }

    @Test
    void getCustomersCargoAmount2() {
        warehouse.newCargo(liquidCargo); //owned by Customer C1
        warehouse.newCargo(mixedDryLiquidBoxed); //owned by Customer C2
        assertTrue(warehouse.allCargo.containsValue(liquidCargo));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquidBoxed));
        warehouse.getCustomersCargoAmount(null);
    }

    @Test
    void getCustomersCargoAmount3() {
        warehouse.newCargo(liquidCargo); //owned by Customer C1
        assertTrue(warehouse.allCargo.containsValue(liquidCargo));
        warehouse.getCustomersCargoAmount("C2");
    }

    @Test
    void getCustomersCargoAmount4() {
        warehouse.newCargo(liquidCargo); //owned by Customer C1
        warehouse.newCargo(mixedDryLiquidBoxed); //owned by Customer C2
        warehouse.newCargo(mixedDryLiquid); //owned by Customer C2
        assertTrue(warehouse.allCargo.containsValue(liquidCargo));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquidBoxed));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquid));
        warehouse.getCustomersCargoAmount("C2");
    }

    @Test
    void getCustomersCargoAmount5() {
        warehouse.newCargo(liquidCargo); //owned by Customer C1
        warehouse.newCargo(mixedDryLiquidBoxed); //owned by Customer C2
        warehouse.newCargo(mixedDryLiquid); //owned by Customer C2
        assertTrue(warehouse.allCargo.containsValue(liquidCargo));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquidBoxed));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquid));
        warehouse.getCustomersCargoAmount("C3");
    }

    @Test
    void printCargoByType() {
        // 2 mixedDryLiquid Cargo in backend.storage
        warehouse.newCargo(mixedDryLiquid);
        warehouse.newCargo(mixedDryLiquid);

        // 1 mixedDryLiquidBoxed backend.storage.backend.storage.cargo in backend.storage
        warehouse.newCargo(mixedDryLiquidBoxed);

        // 2 liquid Cargo in backend.storage
        warehouse.newCargo(liquidCargo);
        warehouse.newCargo(liquidCargo);

        assertTrue(warehouse.allCargo.containsValue(liquidCargo));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquidBoxed));
        assertTrue(warehouse.allCargo.containsValue(mixedDryLiquid));

        //print
        warehouse.printCargoByType();
    }

    @Test
    void printCargoByType2() {

        // 1 liquid Cargo in backend.storage
        warehouse.newCargo(liquidCargo);

        assertTrue(warehouse.allCargo.containsValue(liquidCargo));

        //print
        warehouse.printCargoByType();
    }

    @Test
    void printCargoByType3() {
        //No backend.storage.backend.storage.cargo in backend.storage to sort
        assertTrue(warehouse.allCargo.isEmpty());

        //print
        warehouse.printCargoByType();
    }

    @Test
    void printCargoByType4() {
        //No backend.storage.backend.storage.cargo in backend.storage to sort
        assertTrue(warehouse.allCargo.isEmpty());

        warehouse.newCargo(null);

        //print
        warehouse.printCargoByType();
    }

    @Test
    public void getHazardsInStorage() {

        //No Hazards
        warehouse.newCargo(noHazardCargo);

        //One Hazard
        warehouse.newCargo(oneHazardCargo);

        //two Hazards
        warehouse.newCargo(twoHazardCargo);

        //two Hazards
        warehouse.newCargo(threeHazardCargo);

        //All Hazards
        warehouse.newCargo(allHazardCargo);

        warehouse.getHazardsInStorage();
    }

    @Test
    public void getHazardsInStorage2() {

        //Has No Hazards
        warehouse.newCargo(noHazardCargo);

        //One Hazard
        warehouse.newCargo(oneHazardCargo);

        //two Hazards
        warehouse.newCargo(twoHazardCargo);

        warehouse.getHazardsInStorage();
    }

    @Test
    public void getHazardsInStorage3() {
        //No Hazards
        warehouse.newCargo(noHazardCargo);

        warehouse.getHazardsInStorage();
    }

    @Test
    public void getHazardsInStorage4() {

        assertTrue(warehouse.allCargo.isEmpty());

        //No Hazards
        warehouse.newCargo(null);

        warehouse.getHazardsInStorage();
    }

    @Test
    public void getHazardsNotInStorage() {

        //No Hazards
        warehouse.newCargo(noHazardCargo);

        //One Hazard
        warehouse.newCargo(oneHazardCargo);

        //two Hazards
        warehouse.newCargo(twoHazardCargo);

        //three Hazards
        warehouse.newCargo(threeHazardCargo);

        //All Hazards
        warehouse.newCargo(allHazardCargo);

        warehouse.getHazardsNotInStorage();
    }

    @Test
    public void getHazardsNotInStorage2() {
        //One Hazard
        warehouse.newCargo(oneHazardCargo);

        //two Hazards
        warehouse.newCargo(twoHazardCargo);


        warehouse.getHazardsNotInStorage();
    }

    @Test
    public void getHazardsNotInStorage3() {
        warehouse.getHazardsNotInStorage();
    }

    @Test
    public void getHazardsNotInStorage4() {
        assertTrue(warehouse.allCargo.isEmpty());
        //Has No Hazards
        warehouse.newCargo(null);

        warehouse.getHazardsNotInStorage();
    }

    @Test
    void storeCargoFromAdministration() {
    }

    @Test
    void getOldestCargoInWarehouse() {
    }

    @Test
    void getAllCargoWithPosition() {
    }

    @Test
    void hasNoSpace() {
    }

    @Test
    void getKeyByValueFromCargo() {
    }

    @Test
    void getKeyByValueForServer() {
    }
}