/**
 * Copyright (C) 2018-2021 Expedia, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.expediagroup.streamplatform.streamregistry.state;

import static com.expediagroup.streamplatform.streamregistry.state.model.event.Event.LOAD_COMPLETE;
import static lombok.AccessLevel.PACKAGE;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;

import com.expediagroup.streamplatform.streamregistry.state.model.Entity;
import com.expediagroup.streamplatform.streamregistry.state.model.event.Event;
import com.expediagroup.streamplatform.streamregistry.state.model.specification.Specification;

@RequiredArgsConstructor(access = PACKAGE)
public class DefaultEntityView implements EntityView {
  @NonNull private final EventReceiver receiver;
  @NonNull private final Map<Entity.Key<?>, StateValue> entities;
  @NonNull private final EntityViewUpdater updater;

  DefaultEntityView(EventReceiver receiver, Map<Entity.Key<?>, StateValue> entities) {
    this(receiver, entities, new EntityViewUpdater(entities));
  }

  public DefaultEntityView(EventReceiver receiver) {
    this(receiver, new HashMap<>());
  }

  @Override
  public CompletableFuture<Void> load(@NonNull EntityViewListener listener) {
    val future = new CompletableFuture<Void>();
    receiver.receive(new ReceiverListener(listener, future));
    return future;
  }

  @Override
  public CompletableFuture<Void> load() {
    return load(EntityViewListener.NULL);
  }

  @Override
  public <K extends Entity.Key<S>, S extends Specification> Optional<Entity<K, S>> get(K key) {
    return Optional.ofNullable(entities.get(key)).filter(it -> !it.deleted).map(value -> (Entity<K, S>) value.entity);
  }

  @Override
  public <K extends Entity.Key<S>, S extends Specification> Stream<Entity<K, S>> all(Class<K> keyClass) {
    return all(keyClass, false);
  }

  @Override
  public <K extends Entity.Key<S>, S extends Specification> Stream<Entity<K, S>> allDeleted(Class<K> keyClass) {
    return all(keyClass, true);
  }

  @Override
  public <K extends Entity.Key<S>, S extends Specification> Optional<Entity<K, S>> purgeDeleted(K key) {
    return updater.purge(key);
  }

  private <K extends Entity.Key<S>, S extends Specification> Stream<Entity<K, S>> all(Class<K> keyClass, boolean deleted) {
    return entities.values().stream()
      .filter(value -> deleted == value.deleted)
      .filter(x -> x.entity.getKey().getClass().equals(keyClass))
      .map(x -> (Entity<K, S>) x.entity);
  }

  @Getter // for testing
  @RequiredArgsConstructor
  class ReceiverListener implements EventReceiverListener {
    private final EntityViewListener listener;
    private final CompletableFuture<Void> future;

    @Override
    public <K extends Entity.Key<S>, S extends Specification> void onEvent(Event<K, S> event) {
      if (!future.isDone() && event == LOAD_COMPLETE) {
        future.complete(null);
      } else {
        val oldEntity = updater.update(event);
        if (future.isDone()) {
          listener.onEvent(oldEntity, event);
        }
      }
    }
  }
}
