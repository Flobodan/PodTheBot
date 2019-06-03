package commands;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
//Diese Funktion teilt einem User den Discord Rang "test" zu
public class grantrole extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] nachricht = e.getMessage().getContentRaw().split(" ");//macht aus der nachricht ein string array
        //Check, ob die Nachricht "$grantrole" enthält
        if (nachricht.length == 1 && nachricht[0].equalsIgnoreCase("$grantrole")) {
           //Fügt dem User den Rang "test" hinzu
            e.getGuild().getController().addRolesToMember(e.getMember(), e.getJDA().getRolesByName("test", true)).complete();
        }
    }
}
