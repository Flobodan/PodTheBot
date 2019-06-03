package commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfoCommand extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent e){
        String[] nachricht = e.getMessage().getContentRaw().split(" ");//macht aus der nachricht ein string array
        if(nachricht.length == 1 && nachricht[0].equalsIgnoreCase("$user")){
          e.getChannel().sendMessage("Um Informationen für einen Benutzer zu erhalten schreib $user [name]").queue();
        }else if (nachricht.length==2 && nachricht[0].equalsIgnoreCase("$user")){
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
            Date date = new Date();
            String userName = nachricht[1];
            User user = e.getGuild().getMembersByName(userName, true).get(0).getUser();


            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(userName +"'s info");//der text im titel
            eb.setColor(Color.RED);//farbe des userinfo fensters
            eb.addField("Name",user.getName(), true);//zeigt den namen des angefragten users an
            eb.addField("Onlinestatus: ", e.getGuild().getMembersByName(userName,true).get(0).getOnlineStatus().toString(), true);
            eb.setThumbnail(user.getAvatarUrl());//benutzt das profilbild des users als thumbnail
            eb.setFooter("Datum der Anfrage: "+formatter.format(date),e.getGuild().getIconUrl());
            e.getChannel().sendMessage(eb.build()).queue();//postet die userinfo in den chat, aus dem die anfrage kam
        }

    }
}
