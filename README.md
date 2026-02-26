<div><h1 style="line-height: 40.6px;">ENG<br>⚔️ WoLobbyPvp – Complete Lobby Combat System</h1><p style="font-weight: 400;"><strong>WoLobbyPvp</strong>&nbsp;is a lightweight and powerful tool for server owners that turns boring lobby wait times into exciting fun. The plugin allows players to dynamically enter PvP mode using a single item, without the risk of losing inventory or damaging the lobby.</p><hr style="font-weight: 400;"><h3>🚀 How does it work? (Plugin Mechanics)</h3><p style="font-weight: 400;">The plugin was designed with intuitiveness in mind. It requires no commands from the player:</p><ol style="font-weight: 400;"><li><p><strong>Automatic Start:</strong>&nbsp;Upon joining the server, the player receives a sword in the slot specified in the configuration.</p></li><li><p><strong>Smart Activation:</strong>&nbsp;Combat doesn't start immediately. Once the sword is selected, a configurable&nbsp;<strong>Warmup</strong>&nbsp;begins, displayed as an elegant&nbsp;<strong>Title</strong>&nbsp;in the center of the screen.</p></li><li><p><strong>Combat Armor:</strong>&nbsp;With a single click (<strong>LMB</strong>), the player summons an armor set. The plugin intelligently saves the player's current outfit (e.g., skins or decorative armor) and restores it immediately after the fight ends.</p></li><li><p><strong>Lobby Safety:</strong>&nbsp;PvP only works between players who have the mode active. People without the sword in hand are completely safe and cannot be attacked.</p></li><li><p><strong>No Durability Loss:</strong>&nbsp;All items used within the plugin are indestructible (<strong>Infinite Durability</strong>).</p></li></ol><hr style="font-weight: 400;"><h3>⚙️ Configuration Guide (config.yml)</h3><p style="font-weight: 400;">You can customize every element of the plugin to fit your needs:</p><div style="font-weight: 400;"><div>YAML</div><div><div><pre># ==========================================<br># &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;woLobbyPvp - Configuration<br># ==========================================<br><br><br><br># Waiting time (in seconds) from the moment the sword is held to full PvP activation.<br># The code uses this value in the onSlotChange method to start the countdown.<br>warmup-seconds: 3<br><br><br><br># The item the player will automatically receive upon joining the server.<br># They must hold it in their hand to activate combat mode.<br>activator-item:<br>&nbsp; # Hotbar slot (1-9) where the player will receive the sword.<br>&nbsp; slot: 1<br>&nbsp; # Item material (use names from Spigot documentation, e.g., DIAMOND_SWORD).<br>&nbsp; material: WOODEN_SWORD<br>&nbsp; # Display name (supports &amp; colors). Checked in the isPvPItem method.<br>&nbsp; name: "&amp;c&amp;lChallenge Sword"<br>&nbsp; # Item lore - adds atmosphere and informs the player about the rules.<br>&nbsp; lore:<br>&nbsp; &nbsp; - "&amp;7Status: &amp;eLobby PvP"<br>&nbsp; &nbsp; - ""<br>&nbsp; &nbsp; - "&amp;fHold this sword to fight!"<br>&nbsp; &nbsp; - "&amp;fClick &amp;6LMB&amp;f to equip armor."<br>&nbsp; &nbsp; - ""<br>&nbsp; &nbsp; - "&amp;7Note: Putting the sword away disables PvP mode."<br><br><br><br># The armor set that appears on the player after clicking the Left Mouse Button (LMB).<br># These items are created dynamically by the createItem function in the code.<br>armor:<br>&nbsp; helmet:<br>&nbsp; &nbsp; material: IRON_HELMET<br>&nbsp; &nbsp; name: "&amp;cHelmet"<br>&nbsp; chestplate:<br>&nbsp; &nbsp; material: IRON_CHESTPLATE<br>&nbsp; &nbsp; name: "&amp;cChestplate"<br>&nbsp; leggings:<br>&nbsp; &nbsp; material: IRON_LEGGINGS<br>&nbsp; &nbsp; name: "&amp;cLeggings"<br>&nbsp; boots:<br>&nbsp; &nbsp; material: IRON_BOOTS<br>&nbsp; &nbsp; name: "&amp;cBoots"<br><br><br><br># All text and messages displayed to the player while using the plugin.<br>messages:<br>&nbsp; # Displayed as a Title and in chat when the countdown ends.<br>&nbsp; pvp-enabled: "&amp;a&amp;lFIGHT! &amp;7PvP mode has been activated."<br>&nbsp; # Information shown after putting the sword away or leaving the server.<br>&nbsp; pvp-disabled: "&amp;c&amp;lEND! &amp;7PvP mode has been disabled."<br>&nbsp; # Main Title text during the countdown.<br>&nbsp; warmup-title: "&amp;eGet ready..."<br>&nbsp; # Title Subtitle - %seconds% is automatically replaced by the remaining time in the code.<br>&nbsp; warmup-subtitle: "&amp;7Combat starts in: &amp;c%seconds%s"<br>&nbsp; # Message after clicking LMB when the armor is equipped.<br>  armor-equipped: "&amp;a&amp;oYour armor has been equipped!"<code>
</code></pre></div></div></div><hr style="font-weight: 400;"><h3>🛠️ Technical Info and Permissions</h3><p style="font-weight: 400;">The plugin is optimized and does not strain the server CPU.</p><p style="font-weight: 400;"><strong>Permissions:</strong></p><ul style="font-weight: 400;"><li><p><code>wolobbypvp.admin</code>&nbsp;– Access to the&nbsp;<code>/wolobbypvp reload</code>&nbsp;command (reloads configuration without restarting the server).</p></li></ul><p style="font-weight: 400;"><strong>Technical Details:</strong></p><ul style="font-weight: 400;"><li><p><strong>Versions:</strong>&nbsp;Supports versions 1.18.2 and up (thanks to the implemented VersionManager).</p></li><li><p><strong>Security:</strong>&nbsp;Uses advanced UUID mapping to store saved armor, preventing errors even if a player abruptly leaves the server.</p></li><li><p><strong>Restrictions:</strong>&nbsp;The plugin automatically blocks dropping PvP items (<code>DropItem</code>) and manually removing armor from armor slots.</p></li></ul><hr style="font-weight: 400;"><h3>❓ FAQ - Frequently Asked Questions</h3><ul style="font-weight: 400;"><li><p><strong>Do players lose their items upon death?</strong>&nbsp;No. The plugin blocks item drops and automatically respawns the player with their original inventory restored.</p></li><li><p><strong>Can I change the sword slot?</strong>&nbsp;Yes, in the&nbsp;<code>activator-item.slot</code>&nbsp;section of the config, you can set any slot from 1 to 9.</p></li><li><p><strong>Does the plugin conflict with other lobby plugins?</strong>&nbsp;The plugin works independently. If another plugin grants effects on join, WoLobbyPvp will simply add its items to the designated slots.</p></li></ul><hr style="font-weight: 400;"><h3>🆘 Support and Help</h3><p style="font-weight: 400;">Having trouble with configuration? Found a bug?</p><ul style="font-weight: 400;"><li><p><strong>Discord:</strong>&nbsp;<a href="https://discordapp.com/users/788117068829425715" target="_blank" rel="nofollow noopener">wojteko</a></p></li><li><p><strong>Bugs:</strong>&nbsp;Please report via private message on SpigotMC. Do not use the review section for bug reports!</p></li></ul><hr><p><em>Project Author: <strong>wojteko</strong></em></p><hr></div>

# PL  
⚔️ WoLobbyPvp – Kompletny System Walki w Lobby

**WoLobbyPvp** to lekkie i potężne narzędzie dla właścicieli serwerów, które zamienia nudne oczekiwanie w lobby w ekscytującą zabawę. Plugin pozwala graczom na dynamiczne przechodzenie w tryb PvP za pomocą jednego przedmiotu, bez ryzyka utraty ekwipunku czy niszczenia lobby.

 

***

### 🚀 Jak to działa? (Mechanika Pluginu)

Plugin został zaprojektowany z myślą o intuicyjności. Nie wymaga od gracza wpisywania żadnych komend:

1.  **Automatyczny Start:** Gracz po wejściu na serwer otrzymuje miecz na określonym w konfiguracji slocie.
    
2.  **Inteligentna Aktywacja:** Walka nie zaczyna się od razu. Po wybraniu miecza następuje konfigurowalne odliczanie (Warmup), wyświetlane jako elegancki **Title** na środku ekranu.
    
3.  **Zbroja Bojowa:** Jednym kliknięciem (**LPM**) gracz przywołuje zestaw zbroi. Plugin inteligentnie zapisuje obecne ubranie gracza (np. skiny lub zbroje ozdobne) i przywraca je natychmiast po zakończeniu walki.
    
4.  **Bezpieczeństwo Lobby:** PvP działa tylko między graczami, którzy mają aktywny tryb. Osoby bez miecza w ręku są całkowicie bezpieczne i nie mogą być atakowane.
    
5.  **Brak Zużycia:** Wszystkie przedmioty używane w ramach pluginu są niezniszczalne (**Infinite Durability**).
    

***

⚙️ Przewodnik po Konfiguracji (config.yml)

Każdy element pluginu możesz dostosować do własnych potrzeb:

<div><div>YAML</div><div><div><div><pre># ==========================================<br><br># &nbsp; &nbsp; &nbsp; &nbsp;woLobbyPvp - Konfiguracja<br><br># ==========================================<br><br># Czas oczekiwania (w sekundach) od momentu wzięcia miecza do ręki do pełnej aktywacji PvP.<br><br># Kod używa tej wartości w metodzie onSlotChange do uruchomienia odliczania.<br><br>warmup-seconds: 3<br><br># Przedmiot, który gracz otrzyma automatycznie po wejściu na serwer.<br><br># Musi trzymać go w ręce, aby aktywować tryb walki.<br><br>activator-item:<br><br>&nbsp; # Slot w pasku szybkiego wyboru (1-9), na który gracz otrzyma miecz.<br><br>&nbsp; slot: 1<br><br>&nbsp; # Materiał przedmiotu (używaj nazw z dokumentacji Spigota, np. DIAMOND_SWORD).<br><br>&nbsp; material: WOODEN_SWORD<br><br>&nbsp; # Nazwa wyświetlana (obsługuje kolory &amp;). Sprawdzana w metodzie isPvPItem.<br><br>&nbsp; name: "&amp;c&amp;lMiecz Wyzwania"<br><br>&nbsp; # Opis przedmiotu - dodaje klimatu i informuje gracza o zasadach.<br><br>&nbsp; lore:<br><br>&nbsp; &nbsp; - "&amp;7Status: &amp;eLobby PvP"<br><br>&nbsp; &nbsp; - ""<br><br>&nbsp; &nbsp; - "&amp;fTrzymaj ten miecz, aby walczyć!"<br><br>&nbsp; &nbsp; - "&amp;fKliknij &amp;6LPM&amp;f, aby ubrać zbroję."<br><br>&nbsp; &nbsp; - ""<br><br>&nbsp; &nbsp; - "&amp;7Uwaga: Schowanie miecza wyłącza tryb PvP."<br><br># Zestaw zbroi, który pojawia się na graczu po kliknięciu Lewym Przyciskiem Myszy (LPM).<br><br># Przedmioty te są tworzone dynamicznie przez funkcję createItem w kodzie.<br><br>armor:<br><br>&nbsp; helmet:<br><br>&nbsp; &nbsp; material: IRON_HELMET<br><br>&nbsp; &nbsp; name: "&amp;cHełm"<br><br>&nbsp; chestplate:<br><br>&nbsp; &nbsp; material: IRON_CHESTPLATE<br><br>&nbsp; &nbsp; name: "&amp;cKlata"<br><br>&nbsp; leggings:<br><br>&nbsp; &nbsp; material: IRON_LEGGINGS<br><br>&nbsp; &nbsp; name: "&amp;cSpodnie"<br><br>&nbsp; boots:<br><br>&nbsp; &nbsp; material: IRON_BOOTS<br><br>&nbsp; &nbsp; name: "&amp;cButy"<br><br># Wszystkie napisy i komunikaty, które widzi gracz podczas korzystania z pluginu.<br><br>messages:<br><br>&nbsp; # Wyświetlane jako Title i na czacie w momencie zakończenia odliczania.<br><br>&nbsp; pvp-enabled: "&amp;a&amp;lWALKA! &amp;7Tryb PvP został aktywowany."<br><br>&nbsp; # Informacja po schowaniu miecza lub wyjściu z serwera.<br><br>&nbsp; pvp-disabled: "&amp;c&amp;lKONIEC! &amp;7Tryb PvP został wyłączony."<br><br>&nbsp; # Główny napis Title podczas odliczania.<br><br>&nbsp; warmup-title: "&amp;ePrzygotuj się..."<br><br>&nbsp; # Podnapis Title - %seconds% jest automatycznie zamieniane na czas pozostały w kodzie.<br><br>&nbsp; warmup-subtitle: "&amp;7Walka zacznie się za: &amp;c%seconds%s"<br><br>&nbsp; # Komunikat po kliknięciu LPM, gdy zbroja wskoczy na swoje miejsce.<br><br>&nbsp; armor-equipped: "&amp;a&amp;oTwoja zbroja została założona!"</pre></div><h6><code></code></h6></div></div></div>

***

### 🛠️ Informacje Techniczne i Uprawnienia

Plugin jest zoptymalizowany i nie obciąża procesora serwera.

**Uprawnienia (Permissions):**

*   `wolobbypvp.admin` – Dostęp do komendy `/wolobbypvp reload` (przeładowanie konfiguracji bez restartu serwera).
    

**Szczegóły techniczne:**

*   **Wersje:** Wspiera wersje od 1.18.2 wzwyż (dzięki zaimplementowanemu VersionManager).
    
*   **Bezpieczeństwo:** Wykorzystuje zaawansowane mapowanie UUID do przechowywania zapisanych pancerzy, co zapobiega błędom nawet przy nagłym wyjściu gracza z serwera.
    
*   **Blokady:** Plugin automatycznie blokuje wyrzucanie przedmiotów PvP (`DropItem`) oraz ręczne zdejmowanie zbroi z slotów pancerza.
    

***

### ❓ FAQ - Najczęściej Zadawane Pytania

*   **Czy gracze tracą swoje itemy po śmierci?** Nie. Plugin blokuje wypadanie przedmiotów (Drop) i automatycznie respawnuje gracza z przywróconym ekwipunkiem podstawowym.
    
*   **Czy mogę zmienić slot miecza?** Tak, w sekcji `activator-item.slot` w configu możesz ustawić dowolne miejsce od 1 do 9.
    
*   **Czy plugin koliduje z innymi pluginami na lobby?** Plugin działa niezależnie. Jeśli inny plugin nadaje efekty przy wejściu, WoLobbyPvp po prostu doda swoje przedmioty na wskazane sloty.
    

***

### 🆘 Wsparcie i Pomoc

Masz problem z konfiguracją? Znalazłeś błąd?

*   **Discord:** [wojteko](https://discordapp.com/users/788117068829425715)
    
*   **Błędy:** Proszę zgłaszać poprzez wiadomość prywatną na SpigotMC. Nie używaj sekcji recenzji do zgłaszania błędów!
    

***

_Autor projektu: **wojteko**_
