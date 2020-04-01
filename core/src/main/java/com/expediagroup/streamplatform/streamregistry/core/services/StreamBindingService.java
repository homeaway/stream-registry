/**
 * Copyright (C) 2018-2020 Expedia, Inc.
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
package com.expediagroup.streamplatform.streamregistry.core.services;

import static com.expediagroup.streamplatform.streamregistry.DataToModel.convertToModel;
import static com.expediagroup.streamplatform.streamregistry.ModelToData.convertToData;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.expediagroup.streamplatform.streamregistry.core.events.EventType;
import com.expediagroup.streamplatform.streamregistry.core.events.NotificationEventEmitter;
import com.expediagroup.streamplatform.streamregistry.core.handlers.HandlerService;
import com.expediagroup.streamplatform.streamregistry.core.repositories.StreamBindingRepository;
import com.expediagroup.streamplatform.streamregistry.core.validators.StreamBindingValidator;
import com.expediagroup.streamplatform.streamregistry.model.StreamBinding;
import com.expediagroup.streamplatform.streamregistry.model.keys.StreamBindingKey;

@Component
@RequiredArgsConstructor
public class StreamBindingService {
  private final HandlerService handlerService;
  private final StreamBindingValidator streamBindingValidator;
  private final StreamBindingRepository streamBindingRepository;
  private final NotificationEventEmitter<StreamBinding> streamBindingServiceEventEmitter;

  public Optional<StreamBinding> create(StreamBinding streamBinding) throws ValidationException {
    var data = convertToData(streamBinding);
    if (streamBindingRepository.findById(data.getKey()).isPresent()) {
      throw new ValidationException("Can't create because it already exists");
    }
    streamBindingValidator.validateForCreate(streamBinding);
    data.setSpecification(handlerService.handleInsert(convertToData(streamBinding)));
    StreamBinding out = convertToModel(streamBindingRepository.save(data));
    streamBindingServiceEventEmitter.emitEventOnProcessedEntity(EventType.CREATE, out);
    return Optional.ofNullable(out);
  }

  public Optional<StreamBinding> read(StreamBindingKey key) {
    var data = streamBindingRepository.findById(convertToData(key));
    return data.isPresent() ? Optional.of(convertToModel(data.get())) : Optional.empty();
  }

  public Iterable<StreamBinding> readAll() {
    ArrayList out = new ArrayList();
    for (var streamBinding : streamBindingRepository.findAll()) {
      out.add(convertToModel(streamBinding));
    }
    return out;
  }

  public Optional<StreamBinding> update(StreamBinding streamBinding) throws ValidationException {
    var streamBindingData = convertToData(streamBinding);
    var existing = streamBindingRepository.findById(streamBindingData.getKey());
    if (!existing.isPresent()) {
      throw new ValidationException("Can't update because it doesn't exist");
    }
    streamBindingValidator.validateForUpdate(streamBinding, convertToModel(existing.get()));
    streamBindingData.setSpecification(handlerService.handleInsert(streamBindingData));
    StreamBinding out = convertToModel(streamBindingRepository.save(streamBindingData));
    streamBindingServiceEventEmitter.emitEventOnProcessedEntity(EventType.UPDATE, out);
    return Optional.ofNullable(out);
  }

  public Optional<StreamBinding> upsert(StreamBinding streamBinding) throws ValidationException {
    return !streamBindingRepository.findById(convertToData(streamBinding).getKey()).isPresent() ?
        create(streamBinding) :
        update(streamBinding);
  }

  public void delete(StreamBinding streamBinding) {
    throw new UnsupportedOperationException();
  }

  public Iterable<StreamBinding> findAll(Predicate<StreamBinding> filter) {
    return streamBindingRepository.findAll().stream().map(d -> convertToModel(d)).filter(filter).collect(toList());
  }

  public boolean exists(StreamBindingKey key) {
    return read(key).isEmpty();
  }

  @Deprecated
  public void validateStreamBindingExists(StreamBindingKey key) {
    if (!exists(key)) {
      throw new ValidationException("StreamBinding does not exist");
    }
  }
}
