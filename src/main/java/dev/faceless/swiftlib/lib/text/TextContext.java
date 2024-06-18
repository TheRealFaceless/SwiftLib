package dev.faceless.swiftlib.lib.text;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

import java.util.List;

@SuppressWarnings({"unused", "deprecation"})
public class TextContext {

    private final StringBuilder builder;

    public static final String BLACK = "black";
    public static final String DARK_BLUE = "dark_blue";
    public static final String DARK_GREEN = "dark_green";
    public static final String DARK_AQUA = "dark_aqua";
    public static final String DARK_RED = "dark_red";
    public static final String DARK_PURPLE = "dark_purple";
    public static final String GOLD = "gold";
    public static final String GRAY = "gray";
    public static final String DARK_GRAY = "dark_gray";
    public static final String BLUE = "blue";
    public static final String GREEN = "green";
    public static final String AQUA = "aqua";
    public static final String RED = "red";
    public static final String LIGHT_PURPLE = "light_purple";
    public static final String YELLOW = "yellow";
    public static final String WHITE = "white";
    public static final String RESET = "reset";

    public static final String CLICK_OPEN_URL = "open_url";
    public static final String CLICK_RUN_COMMAND = "run_command";
    public static final String CLICK_SUGGEST_COMMAND = "suggest_command";
    public static final String CLICK_CHANGE_PAGE = "change_page";

    public static final String HOVER_SHOW_TEXT = "show_text";
    public static final String HOVER_SHOW_ITEM = "show_item";
    public static final String HOVER_SHOW_ENTITY = "show_entity";

    public TextContext() {
        builder = new StringBuilder();
    }

    public static TextContext get() {
        return new TextContext();
    }

    public TextContext add(String txt, TextDecoration... decoration) {
        builder.append(parse(txt, decoration));
        return this;
    }

    public TextContext addColored(String color, String txt, TextDecoration... decorations) {
        String coloredTxt = "<" + color + ">" + txt + "</" + color + ">";
        builder.append(parse(coloredTxt, decorations));
        return this;
    }

    public TextContext addRainbow(String txt, float phase, TextDecoration... decorations) {
        String rainbowTxt = "<rainbow" + (phase != 0 ? ":" + phase : "") + ">" + txt + "</rainbow>";
        builder.append(parse(rainbowTxt, decorations));
        return this;
    }

    public TextContext addGradient(String txt, List<String> colors, float phase, TextDecoration... decorations) {
        StringBuilder gradient = new StringBuilder("<gradient:");
        for (String color : colors) {
            gradient.append(color).append(":");
        }
        gradient.deleteCharAt(gradient.length() - 1).append(phase != 0 ? ":" + phase : "").append(">").append(txt).append("</gradient>");
        builder.append(parse(gradient.toString(), decorations));
        return this;
    }

    public TextContext addTransition(String txt, List<String> colors, float phase, TextDecoration... decorations) {
        StringBuilder transition = new StringBuilder("<transition:");
        for (String color : colors) {
            transition.append(color).append(":");
        }
        transition.deleteCharAt(transition.length() - 1).append(phase != 0 ? ":" + phase : "").append(">").append(txt).append("</transition>");
        builder.append(parse(transition.toString(), decorations));
        return this;
    }

    public TextContext addRainbow(String txt, TextDecoration... decorations) {
        String rainbowTxt = "<rainbow>" + txt + "</rainbow>";
        builder.append(parse(rainbowTxt, decorations));
        return this;
    }

    public TextContext addGradient(String txt, List<String> colors, TextDecoration... decorations) {
        StringBuilder gradient = new StringBuilder("<gradient:");
        for (String color : colors) {
            gradient.append(color).append(":");
        }
        gradient.deleteCharAt(gradient.length() - 1).append(">").append(txt).append("</gradient>");
        builder.append(parse(gradient.toString(), decorations));
        return this;
    }

    public TextContext addTransition(String txt, List<String> colors, TextDecoration... decorations) {
        StringBuilder transition = new StringBuilder("<transition:");
        for (String color : colors) {
            transition.append(color).append(":");
        }
        transition.deleteCharAt(transition.length() - 1).append(">").append(txt).append("</transition>");
        builder.append(parse(transition.toString(), decorations));
        return this;
    }

    public TextContext addClick(String action, String actionValue, String txt, TextDecoration... decorations) {
        String clickTxt = "<click:" + action + ":" + actionValue + ">" + txt + "</click>";
        builder.append(parse(clickTxt, decorations));
        return this;
    }

    public TextContext addHover(String action, String actionValue, String txt, TextDecoration... decorations) {
        String hoverTxt = "<hover:" + action + ":" + actionValue + ">" + txt + "</hover>";
        builder.append(parse(hoverTxt, decorations));
        return this;
    }

    public TextContext addFont(String font, String txt, TextDecoration... decorations) {
        String fontTxt = "<font:" + font + ">" + txt + "</font>";
        builder.append(parse(fontTxt, decorations));
        return this;
    }

    public TextContext addTranslatable(String key, TextDecoration... decorations) {
        String translatableTxt = "<lang:" + key + ">";
        builder.append(parse(translatableTxt, decorations));
        return this;
    }

    public TextContext addKeybind(String key, TextDecoration... decorations) {
        String keybindTxt = "<key:" + key + ">";
        builder.append(parse(keybindTxt, decorations));
        return this;
    }

    public Component build() {
        return MiniMessage.miniMessage().deserialize(builder.toString());
    }

    public Component build(boolean italics) {
        return MiniMessage.miniMessage().deserialize(builder.toString()).decoration(TextDecoration.ITALIC, italics);
    }

    public String buildAsString() {
        return builder.toString();
    }

    private String parse(String txt, TextDecoration... decorations) {
        for (TextDecoration decoration : decorations) {
            txt = applyDecoration(decoration, txt);
        }
        return txt;
    }

    private String applyDecoration(TextDecoration decoration, String txt) {
        return switch (decoration) {
            case BOLD -> "<bold>" + txt + "</bold>";
            case ITALIC -> "<italic>" + txt + "</italic>";
            case UNDERLINED -> "<underlined>" + txt + "</underlined>";
            case STRIKETHROUGH -> "<strikethrough>" + txt + "</strikethrough>";
            case OBFUSCATED -> "<obfuscated>" + txt + "</obfuscated>";
        };
    }

    public static String componentToPlainText(Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    public static Component formatLegacy(String text) {
        return LegacyComponentSerializer.legacyAmpersand().deserialize(text);
    }

    public static String formatLegacy2(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String stripColorCodes(String input) {
        input = input.replaceAll("(?i)&[0-9A-FK-OR]", "");
        return input.replaceAll("(?i)§[0-9A-FK-OR]", "");
    }
}
