package me.pm.commandspy.events;
import me.pm.commandspy.Main;
import me.pm.commandspy.utils.DiscordWebhook;
import me.pm.commandspy.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class CommandEvent implements Listener, CommandExecutor {
    private Main plugin;

    public CommandEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerCommandPreprocessEvent e) throws IOException {
        String command = e.getMessage();
        Player p = e.getPlayer();
        if(command.length() != 0) {
            String command1 = command.split(" ")[0].replace("/", "");
            if(plugin.getConfig().getStringList("Commands").contains(command1)) {
                if(p.hasPermission("pm.commandspy.log")) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    DiscordWebhook webhook = new DiscordWebhook(plugin.getConfig().getString("WEBHOOK_URL"));
                    webhook.addEmbed(new DiscordWebhook.EmbedObject()
                    .setColor(Color.decode(plugin.getConfig().getString("COLOR")))
                            .addField("Nick:", p.getName(), false)
                            .addField("Komenda:", command, false)
                            .addField("Tryb:", plugin.getConfig().getString("SERVER"), false)
                    .setFooter(dtf.format(now), "https://cdn.discordapp.com/avatars/645343317873262603/f3878a11e2cd642aa89ca2959800dfb0.png?size=256"));
                    webhook.execute();
                }
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(command.getName().equalsIgnoreCase("commandspyreload")) {
            if(commandSender.hasPermission("pm.commandspy.reload")) {
                plugin.reloadConfig();
                commandSender.sendMessage(Utils.color("&8[&5AppleMC&8] &fPrzeladowano config!"));
                return true;
            }
        } else if(command.getName().equalsIgnoreCase("broadcastdiscord")) {
            if(commandSender.hasPermission("pm.commandspy.broadcast")) {
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }

                String hehs = sb.toString().trim();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy | HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                DiscordWebhook webhook = new DiscordWebhook(plugin.getConfig().getString("WEBHOOK2_URL"));
                webhook.addEmbed(new DiscordWebhook.EmbedObject()
                        .setColor(Color.decode(plugin.getConfig().getString("COLOR2")))
                        .addField("WIADOMOŚĆ", hehs, false)
                        .setFooter(dtf.format(now), "https://cdn.discordapp.com/avatars/645343317873262603/f3878a11e2cd642aa89ca2959800dfb0.png?size=256"));
                try {
                    webhook.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                commandSender.sendMessage(Utils.color("&8[&5AppleMC&8] &fWysłano wiadomość!"));
                return true;
            }
        }
        return false;
    }
}