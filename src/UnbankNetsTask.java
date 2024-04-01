import org.rspeer.commons.logging.Log;
import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.component.Inventories;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.scene.SceneObjects;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(
        name = "Unbanking nets",
        blocking = true,
        blockIfSleeping = true
)

public class UnbankNetsTask extends Task {

    @Override
    public boolean execute() {

        if (Inventories.backpack().contains(x -> x.names("Drift net").results())) {
            return false;
        }

        InterfaceComponent net = Interfaces.getDirect(309,5, 0);

        if (net == null || !net.isVisible()) {
            SceneObject woman = SceneObjects.query().ids(31843).results().nearest(); // Annette

            if (woman != null) {
                Log.fine(woman.getName());
                woman.interact("Nets");
            }

            return true;
        }

        net.interact("Withdraw-All");

        return true;
    }
}
