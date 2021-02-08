package io.github.emuman.commandbuilder;

import io.github.emuman.commandbuilder.exceptions.CommandStructureException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class NodeBase implements TabCompleter {

    private String name;
    private final List<NodeBase> nodes;

    public NodeBase(String name) {
        this.name = name;
        this.nodes = new ArrayList<>();
    }

    /**
     *
     *
     * @param args the arguments to pass into the command tree
     * @param values the values that have been collected
     * @return the tracelog containing information about execution
     */
    public CommandTraceLog run(CommandSender sender, String[] args, Map<String, Object> values)
            throws CommandStructureException {
        CommandTraceLog traceLog = new CommandTraceLog();
        onExecute(sender, args, values, traceLog);
        return traceLog;
    }

    public CommandTraceLog run(CommandSender sender, String[] args) throws CommandStructureException {
        return run(sender, args, new HashMap<>());
    }


    protected abstract void onExecute(CommandSender sender, String[] args, Map<String, Object> values, CommandTraceLog traceLog)
            throws CommandStructureException;

    public abstract NodeBase createCopy(String name);

    public NodeBase createCopy() {
        return createCopy(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NodeBase> getNodes() {
        return nodes;
    }

    public NodeBase addNode(NodeBase node) {
        nodes.add(node);
        return this;
    }

    public String toString() {
        return name;
    }

}
