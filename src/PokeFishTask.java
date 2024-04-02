import com.google.inject.Singleton;
import org.rspeer.commons.logging.Log;
import org.rspeer.commons.math.Distance;
import org.rspeer.event.Subscribe;
import org.rspeer.game.Game;
import org.rspeer.game.adapter.scene.Npc;
import org.rspeer.game.combat.Combat;
import org.rspeer.game.event.ChatMessageEvent;
import org.rspeer.game.query.results.SceneNodeQueryResults;
import org.rspeer.game.scene.Npcs;
import org.rspeer.game.scene.Players;
import org.rspeer.game.script.Task;
import org.rspeer.game.script.TaskDescriptor;

import java.util.HashMap;
import java.util.Map;

@TaskDescriptor(
        name = "Poking fish",
        blocking = true,
        blockIfSleeping = true,
        register = true
)

@Singleton
public class PokeFishTask extends Task {

    private final Map<Integer, Integer> pokedFish = new HashMap<Integer, Integer>();
    private final String POKE_FISH_MESSAGE  = "You prod at the shoal of fish";

    private int target = -1;

    @Subscribe
    public void notify(ChatMessageEvent event) {
        if (event.getType() != ChatMessageEvent.Type.FILTERED) {
            return;
        }

        if (!event.getContents().contains(POKE_FISH_MESSAGE)) {
            return;
        }

        if (target == -1) {
            Log.fine("Message appeared but we have no target");
            return;
        }

        pokedFish.put(target, Game.getTick());
        pokedFish.entrySet().removeIf(entry -> entry.getValue() < Game.getTick() - 20);
    }

    public boolean isFishPokeable(Npc fish) {
        return !pokedFish.containsKey(fish.getIndex());
    }

    @Override
    public boolean execute() {

        if (Players.self().getTarget() != null) {
            return false;
        }

        SceneNodeQueryResults<Npc> fish = Npcs.query().names("Fish shoal").filter(this::isFishPokeable).results();

        if (fish.isEmpty()) {
            return false;
        }

        Npc nearest = fish.nearest(Distance.MANHATTAN);
        if (nearest == null) {
            return false;
        }

        if (Combat.getSpecialEnergy() == 100) {
            Combat.toggleSpecial(true);
        }

        target = nearest.getIndex();

        nearest.interact("Chase");
        return true;
    }
}
