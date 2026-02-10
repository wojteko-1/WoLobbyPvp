package pl.wojteko.woLobbyPvp.compat;

import org.bukkit.Bukkit;

public class VersionManager {

    private static String version;

    public static void init() {
        String v = Bukkit.getBukkitVersion();
        version = v.split("-")[0]; // np 1.20.1
    }

    public static String getVersion() {
        return version;
    }

    public static boolean isAtLeast(int major, int minor) {
        String[] parts = version.split("\\.");
        int vMajor = Integer.parseInt(parts[0]);
        int vMinor = Integer.parseInt(parts[1]);
        return vMajor > major || (vMajor == major && vMinor >= minor);
    }
}
