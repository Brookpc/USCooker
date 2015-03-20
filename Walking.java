
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;


public class Walking implements Strategy {
	
	static SceneObject booth = SceneObjects.getClosest(2213);
	static SceneObject stove = SceneObjects.getClosest(2278);
	static Tile bankSpot = new Tile(2586,3418);
	static Tile stoveSpot = new Tile(2615,3397);
	
	/***************************************************************************************************************************************/
	
	@Override
	public boolean activate() {
		
		if (Inventory.isFull() && Inventory.getCount(USCooker.rawID) == 28 && stoveSpot.distanceTo() > 6) {
			System.out.println("Wait...");
			if (stove == null || stoveSpot.distanceTo() > 6) {
			return true;
			}
		} else if (Inventory.isFull() && Inventory.containts(USCooker.burnID) || Inventory.containts(USCooker.cookID) && bankSpot.distanceTo() > 4) {
			if (booth == null || bankSpot.distanceTo() > 6) {
			System.out.println("Wait");
			return true;
			}
		}
		System.out.println("Walk check passed...");
		return false;
	}
	
	/***************************************************************************************************************************************/

	@Override
	public void execute() {
		if (Inventory.isFull() && Inventory.containts(USCooker.burnID) || Inventory.containts(USCooker.cookID)) {
			System.out.println("Walking...");
			
			if (bankSpot.distanceTo() > 3) {
				
				System.out.println("Walking to Bank...");
				bankSpot.walkTo();
				Time.sleep(5000);
				
			}
			
		} else if (Inventory.isFull() && Inventory.getCount(USCooker.rawID) <= 28 && stoveSpot.distanceTo() > 3) {
			System.out.println("Walking...");
			
			if (stoveSpot.distanceTo() > 3) {
				
				System.out.println("Walking to range...");
				stoveSpot.walkTo();
				Time.sleep(2000);
				
			}
		}
		
	}

}
