package events;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class HelloEvent extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        String[] args = e.getMessage().getContentRaw().split(" ");
        String name = e.getMember().getUser().getName(); //erhalte den namen des Users, der die Nachricht abgeschickt hat
        for (int q = 0; q < args.length; q++){
            if (args[0].equalsIgnoreCase("hi")) {//check, ob das 1. Wort in der Nachricht "hi" ist
                if(!e.getMember().getUser().isBot()){ //check, ob der Absender der Nachricht ein bot ist
                    e.getChannel().sendMessage("hi "+ name + ", ich bin pod der bot!").queue(); //sendet die Nachricht an den channel, aus dem der user "hi" geschrieben hat
                }
            }else if (args[q].equalsIgnoreCase("Pod")) {//falls ein Wort in der nachricht "pod" ist, postet der Bot ein bild
                if(!e.getMember().getUser().isBot()) { //check, ob der Absender der Nachricht ein bot ist
                    e.getChannel().sendMessage("https://i.redd.it/am99x9mu3qt21.jpg").queue();
                }
            }
        }

    }
}
