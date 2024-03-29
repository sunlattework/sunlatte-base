/**
 * This code has been programmed by Ailakks.
 * Please, leave this note and give me credits
 * in any project in which it is used. Have a nice day!
 *
 * @author : Ailakks
 * @mailto : hola@ailakks.com
 * @created : 11/06/2023
 */

package cache.caffeine;

import cache.CacheModel;
import cache.IObjectCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.function.Consumer;

public class CaffeineObjectCache<T extends CacheModel> implements IObjectCache<T> {
    private final Cache<String, T> cache;

    public CaffeineObjectCache() {
        this.cache = Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(Duration.of(120, ChronoUnit.SECONDS))
                .softValues()
                .build();
    }

    @Override
    public Optional<T> get(String id) {
        return Optional.ofNullable(this.cache.getIfPresent(id));
    }

    @Override
    public boolean isPresent(String id) {
        return Optional.ofNullable(this.cache.getIfPresent(id)).isPresent();
    }

    @Override
    public void ifPresent(String id, Consumer<T> consumer) {
        get(id).ifPresent(consumer);
    }

    @Override
    public void put(T object) {
        this.cache.put(object.getId(), object);
    }

    @Override
    public void invalidate(String id) {
        this.cache.invalidate(id);
    }

    @Override
    public void invalidateAll() {
        this.cache.invalidateAll();
    }

    @Override
    public void list() {
        this.cache.asMap();
    }

    @Override
    public long estimatedSize() {
        return this.cache.estimatedSize();
    }
}
