package dk.ku.di.dms.vms.modb.common.data_structure;

/**
 * Utility class to help with the identification and ordering of objects in lists or tree
 * Used in filters params and query plan tree
 * @param id Identifier
 * @param <T> Type
 */
public record IdentifiableNode<T>(int id, T object) {}