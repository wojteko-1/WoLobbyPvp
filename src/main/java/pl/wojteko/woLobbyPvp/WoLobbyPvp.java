package pl.wojteko.woLobbyPvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import pl.wojteko.woLobbyPvp.compat.VersionManager;

import java.util.*;

public class WoLobbyPvp extends JavaPlugin implements Listener, CommandExecutor {

    private final Set<UUID> pvpActive = new HashSet<>();
    private final Map<UUID, Integer> warmupTasks = new HashMap<>();
    private final Map<UUID, ItemStack[]> savedArmor = new HashMap<>();

    @Override
    public void onEnable() {
        VersionManager.init();
        saveDefaultConfig();

        String pluginVersion = getDescription().getVersion();
        String serverVersion = VersionManager.getVersion();
        String author = String.join(", ", getDescription().getAuthors());
        String website = getDescription().getWebsite();

        getLogger().info("========================================");
        getLogger().info("   woLobbyPvp - Plugin zainicjowany!");
        getLogger().info("   Autor: " + (author.isEmpty() ? "wojteko" : author));
        getLogger().info("   Wersja pluginu: " + pluginVersion);
        getLogger().info("   Wykryta wersja Serwera: " + serverVersion);
        if (website != null && !website.isEmpty()) {
            getLogger().info("   Strona: " + website);
        }
        getLogger().info("========================================");

        getServer().getPluginManager().registerEvents(this, this);
        getCommand("wolobbypvp").setExecutor(this);

        for(Player p : Bukkit.getOnlinePlayers()) {
            applyLobbyEffects(p);
            giveActivatorItem(p);
        }
    }

    @Override
    public void onDisable() {
        for (UUID uuid : savedArmor.keySet()) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) restoreArmor(p);
        }
        pvpActive.clear();
        savedArmor.clear();
    }

    // --- LOGIKA POMOCNICZA ---

    private void giveActivatorItem(Player p) {
        ItemStack item = createItem("activator-item");
        ItemMeta meta = item.getItemMeta();
        if (meta != null && getConfig().contains("activator-item.lore")) {
            List<String> lore = new ArrayList<>();
            for (String line : getConfig().getStringList("activator-item.lore")) {
                lore.add(ChatColor.translateAlternateColorCodes('&', line));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
        }

        int slot = getConfig().getInt("activator-item.slot", 1) - 1;
        if (slot < 0 || slot > 8) slot = 0;

        p.getInventory().setItem(slot, item);
    }

    private void applyLobbyEffects(Player p) {
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 255, false, false));
    }

    private void removeLobbyEffects(Player p) {
        p.removePotionEffect(PotionEffectType.WEAKNESS);
    }

    private boolean isPvPItem(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) return false;
        String materialName = getConfig().getString("activator-item.material", "WOODEN_SWORD");
        if (item.getType() != Material.valueOf(materialName)) return false;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return false;

        String configName = ChatColor.translateAlternateColorCodes('&', getConfig().getString("activator-item.name", ""));
        return meta.getDisplayName().equals(configName);
    }

    private void enablePvP(Player p) {
        pvpActive.add(p.getUniqueId());
        removeLobbyEffects(p);

        String title = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.pvp-enabled"));
        p.sendTitle(title, "", 10, 40, 10);
        p.sendMessage(title);
    }

    private void disablePvP(Player p) {
        UUID uid = p.getUniqueId();
        if (warmupTasks.containsKey(uid)) {
            Bukkit.getScheduler().cancelTask(warmupTasks.get(uid));
            warmupTasks.remove(uid);
        }
        boolean wasActive = pvpActive.contains(uid);
        pvpActive.remove(uid);
        if (savedArmor.containsKey(uid)) restoreArmor(p);
        applyLobbyEffects(p);
        if (wasActive) {
            String msg = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.pvp-disabled"));
            p.sendMessage(msg);
            p.sendTitle("", msg, 10, 40, 10);
        }
    }

    private void restoreArmor(Player p) {
        if (savedArmor.containsKey(p.getUniqueId())) {
            p.getInventory().setArmorContents(savedArmor.get(p.getUniqueId()));
            savedArmor.remove(p.getUniqueId());
        } else {
            p.getInventory().setArmorContents(null);
        }
    }

    private ItemStack createItem(String path) {
        Material mat = Material.valueOf(getConfig().getString(path + ".material", "STONE"));
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', getConfig().getString(path + ".name", "")));
            // BLOKADA ZUŻYWANIA (META)
            meta.setUnbreakable(true);
            item.setItemMeta(meta);
        }
        return item;
    }

    // --- LISTENERY ---

    // BLOKADA ZUŻYWANIA (EVENT GLOBALNY)
    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        applyLobbyEffects(p);
        giveActivatorItem(p);
        p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        p.setFoodLevel(20);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        disablePvP(e.getPlayer());
    }

    @EventHandler
    public void onSlotChange(PlayerItemHeldEvent e) {
        Player p = e.getPlayer();
        ItemStack newItem = p.getInventory().getItem(e.getNewSlot());

        if (isPvPItem(newItem)) {
            if (!pvpActive.contains(p.getUniqueId()) && !warmupTasks.containsKey(p.getUniqueId())) {
                int seconds = getConfig().getInt("warmup-seconds", 3);
                BukkitRunnable task = new BukkitRunnable() {
                    int timeLeft = seconds;
                    @Override
                    public void run() {
                        if (!p.isOnline() || !isPvPItem(p.getInventory().getItemInMainHand())) {
                            this.cancel();
                            warmupTasks.remove(p.getUniqueId());
                            return;
                        }
                        if (timeLeft > 0) {
                            String title = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.warmup-title"));
                            String sub = ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.warmup-subtitle"))
                                    .replace("%seconds%", String.valueOf(timeLeft));
                            p.sendTitle(title, sub, 0, 25, 5);
                            timeLeft--;
                        } else {
                            enablePvP(p);
                            warmupTasks.remove(p.getUniqueId());
                            this.cancel();
                        }
                    }
                };
                int taskId = task.runTaskTimer(this, 0L, 20L).getTaskId();
                warmupTasks.put(p.getUniqueId(), taskId);
            }
        } else {
            disablePvP(p);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player p = e.getPlayer();
            if (isPvPItem(p.getInventory().getItemInMainHand())) {
                if (!savedArmor.containsKey(p.getUniqueId())) {
                    savedArmor.put(p.getUniqueId(), p.getInventory().getArmorContents());
                    ItemStack[] pvpArmor = new ItemStack[4];
                    pvpArmor[0] = createItem("armor.boots");
                    pvpArmor[1] = createItem("armor.leggings");
                    pvpArmor[2] = createItem("armor.chestplate");
                    pvpArmor[3] = createItem("armor.helmet");
                    p.getInventory().setArmorContents(pvpArmor);
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.armor-equipped")));
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
        Player victim = (Player) e.getEntity();
        Player attacker = (Player) e.getDamager();
        if (!pvpActive.contains(victim.getUniqueId()) || !pvpActive.contains(attacker.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        if (pvpActive.contains(p.getUniqueId())) {
            e.getDrops().clear();
            e.setDroppedExp(0);
            Bukkit.getScheduler().runTaskLater(this, () -> {
                p.spigot().respawn();
                p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                disablePvP(p);
                giveActivatorItem(p);
            }, 2L);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (pvpActive.contains(e.getPlayer().getUniqueId()) || isPvPItem(e.getItemDrop().getItemStack())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            Player p = (Player) e.getWhoClicked();
            if (savedArmor.containsKey(p.getUniqueId()) && e.getSlotType() == InventoryType.SlotType.ARMOR) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        e.setCancelled(true);
        e.setFoodLevel(20);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("wolobbypvp.admin")) {
            reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Config przeladowany!");
            return true;
        }
        return false;
    }
}