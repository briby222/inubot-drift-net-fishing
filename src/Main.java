import org.rspeer.commons.ArrayUtils;
import org.rspeer.commons.StopWatch;
import org.rspeer.commons.logging.Log;
import org.rspeer.event.Subscribe;
import org.rspeer.game.component.tdi.Skill;
import org.rspeer.game.event.ChatMessageEvent;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskScript;
import org.rspeer.game.script.meta.ScriptMeta;
import org.rspeer.game.script.meta.paint.PaintBinding;
import org.rspeer.game.script.meta.paint.PaintScheme;

import java.util.function.Supplier;

@ScriptMeta(name = "Drift Nets", developer = "nigga420", paint = PaintScheme.class, regions = -3)

public class Main extends TaskScript {

    @PaintBinding("Runtime")
    private final StopWatch watch = StopWatch.start();
    @PaintBinding("Last task")
    private final Supplier<String> task = () -> getManager().getLastTaskName();
    @PaintBinding(value = "Caught fish", rate = true)
    private int caught = 0;
    @PaintBinding("Experience")
    private final Skill hunter = Skill.HUNTER;
    @PaintBinding("Experience")
    private final Skill fishing = Skill.FISHING;

    @Override
    public Class<? extends Task>[] tasks() {
        return ArrayUtils.getTypeSafeArray(
                StaminaTask.class,
                UnbankNetsTask.class,
                SetupNetsTask.class,
                RemoveNetsTask.class,
                PokeFishTask.class
        );
    }

    @Subscribe
    public void notify(ChatMessageEvent event) {
        String message = event.getContents();
        if (message.contains("You caught some fish")) {
            caught++;
        }
    }
}