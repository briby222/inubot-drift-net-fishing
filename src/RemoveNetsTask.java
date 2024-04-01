import org.rspeer.game.adapter.component.InterfaceComponent;
import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.component.Interfaces;
import org.rspeer.game.query.results.SceneNodeQueryResults;
import org.rspeer.game.scene.SceneObjects;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(
        name = "Tearing down nets",
        blocking = true,
        blockIfSleeping = true
)

public class RemoveNetsTask extends Task {

    @Override
    public boolean execute() {

        InterfaceComponent discardButton = Interfaces.getDirect(607, 6);
        if (discardButton != null && discardButton.isVisible()) {
            discardButton.interact("Discard all");
            return true;
        }

        SceneNodeQueryResults<SceneObject> nets = SceneObjects.query().names("Drift net (full)").actions("Harvest").results();

        if (nets.isEmpty()) {
            return false;
        }

        nets.nearest().interact("Harvest");

        return true;
    }
}
