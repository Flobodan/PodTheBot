package commands;

import music.PlayerManager;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.AudioManager;

public class handler extends ListenerAdapter {

    public void onGuildMessageReceived (GuildMessageReceivedEvent e){
        if (e.getAuthor().isBot()){

        }
        String[] nachricht = e.getMessage().getContentRaw().split(" ");
        TextChannel channel = e.getChannel();
        //mit !join betritt der Bot den Voicechannel, in dem der user sich befindet
        if(nachricht[0].equalsIgnoreCase("!join")){

            if(!e.getGuild().getSelfMember().hasPermission(channel, Permission.VOICE_CONNECT)){
                channel.sendMessage("I do not have permissions to join a voice channel!").queue();
            }
            VoiceChannel connectedChannel = e.getMember().getVoiceState().getChannel();
            if(connectedChannel==null){
                channel.sendMessage("Du muss in einem Voicechannel sein, damit ich beitreten kann!").queue();
            }
            AudioManager audioManager = e.getGuild().getAudioManager();
            if(((AudioManager) audioManager).isAttemptingToConnect()){
                channel.sendMessage("Ich versuche bereits zu connecten, bitte warte einen Moment").queue();
            }
            audioManager.openAudioConnection(connectedChannel);
            channel.sendMessage("Ich bin drin!").queue();

        }
        //mit !leave verlässt der Bot den voicechannel
        else if(nachricht[0].equalsIgnoreCase("!leave")){
            e.getGuild().getAudioManager().closeAudioConnection();
            channel.sendMessage("Bruder muss los!").queue();
        }//!play musiklink um musik der playlist hinzuzufügen
        else if((nachricht.length==2 && nachricht[0].equalsIgnoreCase("!play"))){
            PlayerManager manager = PlayerManager.getInstance();
            manager.loadAndPlay(e.getChannel(), nachricht[1]);
            manager.getguildMusicManager(e.getGuild()).player.setVolume(10);
        }//!skip um den momentanen song zu überspringen
        else if(nachricht[0].equalsIgnoreCase("!skip")){
            PlayerManager manager = PlayerManager.getInstance();
            manager.skipTrack(e.getChannel());
        }
    }
}
