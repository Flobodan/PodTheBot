package music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {
    private static PlayerManager INSTANCE;
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;

    private PlayerManager(){
        this.musicManagers = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public synchronized GuildMusicManager getguildMusicManager(Guild guild){
        long guildID = guild.getIdLong();
        GuildMusicManager musicManager = musicManagers.get(guildID);

        if (musicManager == null){
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildID, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(TextChannel channel, String trackUrl){
        GuildMusicManager musicManager = getguildMusicManager(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Füge "+ track.getInfo().title+" der Playlist hinzu!").queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if(firstTrack==null){
                    firstTrack = playlist.getTracks().get(0);
                }
                channel.sendMessage("Füge " + firstTrack.getInfo().title + " der playlist hinzu"+" (Erster Song in der Playlist " + playlist.getName() + ")").queue();
                play(musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nichts gefunden unter dem link " + trackUrl).queue();

            }

            @Override
            public void loadFailed(FriendlyException e) {
                channel.sendMessage("Konnte den Song nicht abspielen: " + e.getMessage()).queue();
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track){
        musicManager.scheduler.queue(track);
    }

    public void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getguildMusicManager(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Song wurde skipped!").queue();
    }


    public static synchronized PlayerManager getInstance() {
        if (INSTANCE == null){
            INSTANCE = new PlayerManager();
        }
        return  INSTANCE;
    }
}
