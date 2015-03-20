import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.methods.*;




public class Banking implements Strategy{

	private SceneObject[] booth = SceneObjects.getNearest(2213);
	
	/***************************************************************************************************************************************/

	@Override
	public boolean activate() {
		if (booth != null && Inventory.getCount(USCooker.rawID) == 0) {
			System.out.println("Banking...");
			return true;
		}
		System.out.println("Bank check passed...");
		return false;

	}
	
	/***************************************************************************************************************************************/

	@Override
	public void execute() {


		if(booth != null) {
			System.out.println("Found booth...");
			booth[0].interact(0);
			Time.sleep(3000);
			
			//deposit
			
			if (Game.getOpenInterfaceId() == 5292 && Inventory.isFull() && Inventory.containts(USCooker.cookID + 2) || Inventory.containts(USCooker.cookID) && !Inventory.containts(USCooker.rawID)) {
				System.out.println("Depositing...");
				if (Inventory.containts(USCooker.burnID) || Inventory.containts(USCooker.cookID)) {
					USCooker.cookCount += Inventory.getCount(USCooker.cookID);
					Bank.depositAllExcept(USCooker.rawID);
					
				}
			}
			
			//withdraw
			if (Game.getOpenInterfaceId() == 5292 && !Inventory.isFull()) {
				System.out.println("Withdrawing...");
				Bank.withdraw(USCooker.rawID, 28, 100);
				Time.sleep(new SleepCondition() {
					@Override
					public boolean isValid() {
						return Inventory.getCount(USCooker.rawID) > 0;
					}
				}, 3000);
			}
		}
		//Close bank
		Menu.sendAction(200,0,5384,6,5698,1);
	}

}