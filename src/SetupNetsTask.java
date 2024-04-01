import org.rspeer.game.adapter.scene.SceneObject;
import org.rspeer.game.query.results.SceneNodeQueryResults;
import org.rspeer.game.scene.SceneObjects;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

@TaskDescriptor(
        name = "Setting up nets",
        blocking = true,
        blockIfSleeping = true
)

public class SetupNetsTask extends Task {

    @Override
    public boolean execute() {

        SceneNodeQueryResults<SceneObject> anchors = SceneObjects.query().names("Drift net anchors").actions("Set up").results();

        if (anchors.isEmpty()) {
            return false;
        }

        anchors.nearest().interact("Set up");

        return true;
    }
}
