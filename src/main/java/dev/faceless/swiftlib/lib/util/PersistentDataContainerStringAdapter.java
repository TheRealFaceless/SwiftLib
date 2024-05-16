package dev.faceless.swiftlib.lib.util;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The symbols within the quotation marks should not be used in the PDC as they're
 * used as delimiters in the pdc string ("\", "~", "`")
 *
 * @author Faceless
 */
@SuppressWarnings("unused")
public final class PersistentDataContainerStringAdapter {

    private final PersistentDataContainer container;
    private final NamespacedKey key;
    private String pdcString;

    private static final String KEY_VALUE_PAIR_DELIMITER = "\\|";
    private static final String KEY_VALUE_PAIR_DELIMITER2 = "|";
    private static final String KEY_VALUE_DELIMITER = "~";
    private static final String LIST_ITEM_DELIMITER = "`";

    public PersistentDataContainerStringAdapter(PersistentDataContainer container, NamespacedKey key) {
        if(container.get(key, PersistentDataType.STRING) == null) {
            container.set(key, PersistentDataType.STRING, "");
        }
        this.pdcString = container.get(key, PersistentDataType.STRING);
        this.container = container;
        this.key = key;
    }

    /**
     * Using this overrides any existing value under the same key
     * @param key key for value to be stored
     * @param value value to be stored
     */
    public void addString(String key, String value) {
        if(hasKey(key)) {
            updateString(key, value);
            return;
        }
        String syntax = key + KEY_VALUE_DELIMITER + value + KEY_VALUE_PAIR_DELIMITER2;
        pdcString = pdcString + syntax;
    }

    /**
     * @param key key to search for [case-insensitive]
     * @return value if found, else null
     */
    public String getString(String key) {
        String[] keyValuePair = pdcString.split(KEY_VALUE_PAIR_DELIMITER);

        for(String s : keyValuePair)  {
            String[] pairs = s.split(KEY_VALUE_DELIMITER);
            if(pairs[0].equalsIgnoreCase(key)) return pairs[1];
        }
        return null;
    }

    /**
     *
     * @param key key to search for and remove as well as its
     *            corresponding value
     */
    public void remove(String key) {
        StringBuilder updatedPdc = new StringBuilder();

        String[] keyValuePair = pdcString.split(KEY_VALUE_PAIR_DELIMITER);
        for (String pair : keyValuePair) {
            String[] keyValue = pair.split(KEY_VALUE_DELIMITER);
            if (!keyValue[0].equalsIgnoreCase(key)) {
                updatedPdc.append(pair).append(KEY_VALUE_PAIR_DELIMITER2);
            }
        }
        pdcString = updatedPdc.toString();
    }

    public boolean hasKey(String key) {
        String[] keyValuePair = pdcString.split(KEY_VALUE_PAIR_DELIMITER);
        for (String pair : keyValuePair) {
            String[] keyValue = pair.split(KEY_VALUE_DELIMITER);
            if (keyValue.length == 2 && keyValue[0].equalsIgnoreCase(key)) return true;
        }
        return false;
    }

    /**
     * This should almost never be used but has no implications if used
     * @param key key to update
     * @param newValue new value to update key
     */
    public void updateString(String key, String newValue) {
        StringBuilder updatedPdc = new StringBuilder();

        String[] keyValuePair = pdcString.split(KEY_VALUE_PAIR_DELIMITER);
        for (String pair : keyValuePair) {
            String[] keyValue = pair.split(KEY_VALUE_DELIMITER);

            if (keyValue.length == 2 && keyValue[0].equalsIgnoreCase(key)) updatedPdc.append(key).append(KEY_VALUE_DELIMITER).append(newValue).append(KEY_VALUE_PAIR_DELIMITER2);
            else updatedPdc.append(pair).append(KEY_VALUE_PAIR_DELIMITER2);
        }
        pdcString = updatedPdc.toString();
    }

    public void addStringList(String key, List<String> values) {
        StringBuilder listString = new StringBuilder();
        for (String value : values) {
            listString.append(value).append(LIST_ITEM_DELIMITER);
        }
        addString(key, listString.toString());
    }

    public List<String> getStringList(String key) {
        List<String> values = new ArrayList<>();
        String listString = getString(key);
        if (listString != null && !listString.isEmpty()) {
            String[] splitValues = listString.split(LIST_ITEM_DELIMITER);
            values.addAll(Arrays.asList(splitValues));
        }
        return values;
    }

    public void build() {
        container.set(key, PersistentDataType.STRING, pdcString);
    }
}

