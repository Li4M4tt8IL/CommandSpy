package me.pm.commandspy.utils;

import org.bukkit.Bukkit;

public class Utils {
    public static String color(String msg) {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&')
                coloredMsg += '§';
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }

    public static void sendConsole(String s) {
        Bukkit.getConsoleSender().sendMessage(color(s));
    }
}
