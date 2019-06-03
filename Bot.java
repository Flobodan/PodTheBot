import commands.UserInfoCommand;
import commands.grantrole;
import commands.handler;
import events.HelloEvent;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Bot {

    public static void main(String args[]) throws Exception{

        JDA jda = new JDABuilder("TOKEN HIER REIN").build();

        jda.addEventListener(new HelloEvent());
        jda.addEventListener(new UserInfoCommand());
        jda.addEventListener(new grantrole());
        jda.addEventListener(new handler());

    }
}
