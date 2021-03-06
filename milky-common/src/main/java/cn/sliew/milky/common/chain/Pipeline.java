package cn.sliew.milky.common.chain;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

public interface Pipeline<K, V> {

    /**
     * Inserts a {@link PipelineProcess} at the first position of this pipeline.
     *
     * @param name    the name of the command to insert first
     * @param command the command to insert first
     * @throws IllegalArgumentException if there's an entry with the same name already in the pipeline
     * @throws NullPointerException     if the specified command is {@code null}
     */
    Pipeline<K, V> addFirst(String name, Command<K, V> command);

    /**
     * Appends a {@link PipelineProcess} at the last position of this pipeline.
     *
     * @param name    the name of the command to append
     * @param command the command to append
     * @throws IllegalArgumentException if there's an entry with the same name already in the pipeline
     * @throws NullPointerException     if the specified command is {@code null}
     */
    Pipeline<K, V> addLast(String name, Command<K, V> command);

    /**
     * Inserts a {@link Command} before an existing command of this pipeline.
     *
     * @param baseName the name of the existing command
     * @param name     the name of the command to insert before
     * @param command  the command to insert before
     * @throws NoSuchElementException   if there's no such entry with the specified {@code baseName}
     * @throws IllegalArgumentException if there's an entry with the same name already in the pipeline
     * @throws NullPointerException     if the specified baseName or command is {@code null}
     */
    Pipeline<K, V> addBefore(String baseName, String name, Command<K, V> command);

    /**
     * Inserts a {@link Command} after an existing command of this pipeline.
     *
     * @param baseName the name of the existing command
     * @param name     the name of the command to insert after
     * @param command  the command to insert after
     * @throws NoSuchElementException   if there's no such entry with the specified {@code baseName}
     * @throws IllegalArgumentException if there's an entry with the same name already in the pipeline
     * @throws NullPointerException     if the specified baseName or command is {@code null}
     */
    Pipeline<K, V> addAfter(String baseName, String name, Command<K, V> command);

    /**
     * Removes the specified {@link Command} from this pipeline.
     *
     * @param command the {@link PipelineProcess} to remove
     * @throws NoSuchElementException if there's no such command in this pipeline
     * @throws NullPointerException   if the specified command is {@code null}
     */
    Pipeline<K, V> remove(Command<K, V> command);

    /**
     * Removes the {@link Command} with the specified name from this pipeline.
     *
     * @param name the name under which the {@link Command} was stored.
     * @return the removed command
     * @throws NoSuchElementException if there's no such command with the specified name in this pipeline
     * @throws NullPointerException   if the specified name is {@code null}
     */
    Command<K, V> remove(String name);

    /**
     * Removes the {@link Command} of the specified type from this pipeline.
     *
     * @param commandType the type of the command
     * @return the removed command
     * @throws NoSuchElementException if there's no such command of the specified type in this pipeline
     * @throws NullPointerException   if the specified command type is {@code null}
     */
    Command<K, V> remove(Class commandType);

    /**
     * Removes the first {@link Command} in this pipeline.
     *
     * @return the removed command
     * @throws NoSuchElementException if this pipeline is empty
     */
    Command<K, V> removeFirst();

    /**
     * Removes the last {@link Command} in this pipeline.
     *
     * @return the removed command
     * @throws NoSuchElementException if this pipeline is empty
     */
    Command<K, V> removeLast();

    /**
     * Returns the first {@link Command} in this pipeline.
     *
     * @return the first command.  {@code null} if this pipeline is empty.
     */
    Command<K, V> first();

    /**
     * Returns the last {@link Command} in this pipeline.
     *
     * @return the last command.  {@code null} if this pipeline is empty.
     */
    Command<K, V> last();

    /**
     * Returns the {@link Command} with the specified name in this pipeline.
     *
     * @return the command with the specified name.
     * {@code null} if there's no such command in this pipeline.
     */
    Command<K, V> get(String name);

    /**
     * Returns the {@link Command} of the specified type in this pipeline.
     *
     * @return the command of the specified command type.
     * {@code null} if there's no such command in this pipeline.
     */
    <T extends Command<K, V>> T get(Class<T> commandType);

    /**
     * Returns the context of the first {@link Command} in this pipeline.
     *
     * @return the context of the first command.  {@code null} if this pipeline is empty.
     */
    PipelineProcess<K, V> firstContext();

    /**
     * Returns the context of the last {@link Command} in this pipeline.
     *
     * @return the context of the last command.  {@code null} if this pipeline is empty.
     */
    PipelineProcess<K, V> lastContext();

    /**
     * Returns the context object of the specified {@link Command} in this pipeline.
     *
     * @return the context object of the specified command.
     * {@code null} if there's no such command in this pipeline.
     */
    PipelineProcess<K, V> context(Command<K, V> command);

    /**
     * Returns the context object of the {@link Command} with the
     * specified name in this pipeline.
     *
     * @return the context object of the command with the specified name.
     * {@code null} if there's no such command in this pipeline.
     */
    PipelineProcess<K, V> context(String name);

    /**
     * Returns the context object of the {@link Command} of the
     * specified type in this pipeline.
     *
     * @return the context object of the command of the specified type.
     * {@code null} if there's no such command in this pipeline.
     */
    PipelineProcess<K, V> context(Class<? extends Command> commandType);

    /**
     * Returns the {@link List} of the command names.
     */
    List<String> names();

    /**
     * Converts this pipeline into an ordered {@link Map} whose keys are
     * command names and whose values are commands.
     */
    Map<String, Command<K, V>> toMap();

    Pipeline fireEvent(Context<K, V> context, CompletableFuture<?> future);

    Pipeline fireExceptionCaught(Context<K, V> context, Throwable cause, CompletableFuture<?> future);
}