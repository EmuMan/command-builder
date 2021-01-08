package io.github.emuman.commandbuilder;

import io.github.emuman.commandbuilder.exceptions.CommandStructureException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Arrays;
import java.util.Map;

public class OfflinePlayerNode extends NodeBase{

    public OfflinePlayerNode(String name) {
        super(name);
    }

    @Override
    public void onExecute(String[] args, Map<String, Object> values, CommandTraceLog traceLog) throws CommandStructureException {
        if (args.length == 0) {
            addTraceLogData(traceLog, CommandTraceLog.ReturnCode.MISSING_ARGUMENT, null);
            return;
        }
        String playerName = args[0];
        // Deprecated because usernames no longer allow persistent storage, but we don't really care about that,
        // so this is probably okay.
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        // TODO: Figure out how to distinguish players that exist from those that do not, since getOfflinePlayer never returns null
//        if (offlinePlayer.) {
//            addTraceLogData(traceLog, CommandTraceLog.ReturnCode.INVALID_ARGUMENT, playerName);
//            return;
//        }
        addTraceLogData(traceLog, CommandTraceLog.ReturnCode.SUCCESS, playerName);
        values.put(getName(), offlinePlayer);
        if (getNodes().size() == 0) {
            throw new CommandStructureException("PlayerNode must point towards one other node");
        }
        getNodes().get(0).onExecute(Arrays.copyOfRange(args, 1, args.length), values, traceLog);
    }

    @Override
    public NodeBase createCopy(String name) {
        return new PlayerNode(name);
    }

    public void addTraceLogData(CommandTraceLog traceLog, CommandTraceLog.ReturnCode code, String choice) {
        if (code == CommandTraceLog.ReturnCode.SUCCESS) {
            traceLog.addTrace(choice);
        } else {
            traceLog.addTrace("<" + getName() + ">"); // TODO: Maybe put incorrect player name if invalid?
            traceLog.setReturnCode(code);
        }
    }

}