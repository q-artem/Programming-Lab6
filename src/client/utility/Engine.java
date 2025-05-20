package client.utility;

import client.commands.*;
import client.managers.CollectionManager;
import client.managers.CommandManager;
import client.managers.DumpManager;
import client.server.Client;
import common.utility.Console;
import common.utility.StandartConsole;

import java.io.IOException;

public class Engine {
    public void run(String[] args) throws IOException {
        Console console = new StandartConsole();

        if (args.length == 0) {
            console.println("Введите имя загружаемого файла как аргумент командной строки");
            System.exit(1);
        }

        var localClient = new Client();
        var dumpManager = new DumpManager(args[0], console, localClient);
        var collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.loadCollection()) {
            System.exit(1);
        }

        var commandManager = new CommandManager() {{
            register("help", new Help(console, this));
            register("add", new Add(console, collectionManager));  // additional command
            register("load", new Load(console, collectionManager));  // additional command
            register("info", new Info(console, collectionManager));
            register("show", new Show(console, collectionManager));
            register("insert", new Insert(console, collectionManager));
            register("update", new Update(console, collectionManager));
            register("remove_key", new RemoveKey(console, collectionManager));
            register("clear", new Clear(console, collectionManager));
            register("save", new Save(console, collectionManager));
            register("execute_script", new ExecuteScript(console));
            register("exit", new Exit(console));
            register("remove_greater", new RemoveGreater(console, collectionManager));
            register("remove_lower", new RemoveLower(console, collectionManager));
            register("replace_if_greater", new ReplaceIfGreater(console, collectionManager));
            register("sum_of_impact_speed", new SumOfImpactSpeed(console, collectionManager));
            register("filter_less_than_car", new FilterLessThanCar(console, collectionManager));
            register("print_field_descending_weapon_type", new PrintFieldDescendingWeaponType(console, collectionManager));
        }};
        commandManager.register("show_command_history", new ShowCommandHistory(console, commandManager));  // additional command

        new Runner(console, commandManager).interactiveMode();
    }
}
