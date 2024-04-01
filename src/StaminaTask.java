import org.rspeer.game.component.Inventories;
import org.rspeer.game.component.Item;
import org.rspeer.game.movement.Movement;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(
        name = "Drinking stamina potion"
)

public class StaminaTask extends Task {

    @Override
    public boolean execute() {

        if (Movement.getRunEnergy() < 30) {
            Item pog = Inventories.backpack().query().nameContains("Stamina potion").results().first();
            if (pog != null) {
                pog.interact("Drink");
            }
        }

        return false;
    }
}
