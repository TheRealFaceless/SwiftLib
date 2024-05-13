package dev.faceless.swiftlib.lib.util;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

/**
 * The symbols within the quotation marks should not be used in the PDC as they're
 * used as delimiters in the pdc string (":", ";", "`")
 *
 * @author Faceless
 */
public final class PersistentDataContainerAdapter {

    private final PersistentDataContainer container;
    private final NamespacedKey key;
    private String pdcString;

    public PersistentDataContainerAdapter(PersistentDataContainer container, NamespacedKey key) {
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
        String syntax = key + ":" + value + ";";
        pdcString = pdcString + syntax;
    }

    /**
     * @param key key to search for [case-insensitive]
     * @return value if found, else null
     */
    public String getString(String key) {
        String[] keyValuePair = pdcString.split(";");

        for(String s : keyValuePair)  {
            String[] pairs = s.split(":");
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

        String[] keyValuePair = pdcString.split(";");
        for (String pair : keyValuePair) {
            System.out.println(pair);
            String[] keyValue = pair.split(":");
            if (!keyValue[0].equalsIgnoreCase(key)) {
                updatedPdc.append(pair).append(";");
            }
        }
        pdcString = updatedPdc.toString();
    }

    public boolean hasKey(String key) {
        String[] keyValuePair = pdcString.split(";");
        for (String pair : keyValuePair) {
            String[] keyValue = pair.split(":");
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

        String[] keyValuePair = pdcString.split(";");
        for (String pair : keyValuePair) {
            String[] keyValue = pair.split(":");

            if (keyValue.length == 2 && keyValue[0].equalsIgnoreCase(key)) updatedPdc.append(key).append(":").append(newValue).append(";");
            else updatedPdc.append(pair).append(";");
        }
        pdcString = updatedPdc.toString();
    }

    public void build() {
        container.set(key, PersistentDataType.STRING, pdcString);
    }
}
