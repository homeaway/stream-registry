/**
 * Copyright (C) 2016-2019 Expedia Inc.
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

package com.expediagroup.streamplatform.streamregistry.app.mutation;

import static com.expediagroup.streamplatform.streamregistry.app.StateHelper.maintainState;

import com.expediagroup.streamplatform.streamregistry.app.inputs.ConsumerKeyInput;
import com.expediagroup.streamplatform.streamregistry.app.inputs.SpecificationInput;
import com.expediagroup.streamplatform.streamregistry.app.inputs.StatusInput;
import com.expediagroup.streamplatform.streamregistry.core.services.Services;
import com.expediagroup.streamplatform.streamregistry.model.Consumer;

public class ConsumerMutation {

  private Services services;

  public ConsumerMutation(Services services) {
    this.services = services;
  }

  public Consumer insert(ConsumerKeyInput key, SpecificationInput specification) {
    return services.getConsumerService().create(asConsumer(key, specification)).get();
  }

  public Consumer update(ConsumerKeyInput key, SpecificationInput specification) {
    return services.getConsumerService().update(asConsumer(key, specification)).get();
  }

  public Consumer upsert(ConsumerKeyInput key, SpecificationInput specification) {
    return services.getConsumerService().upsert(asConsumer(key, specification)).get();
  }

  private Consumer asConsumer(ConsumerKeyInput key, SpecificationInput specification) {
    Consumer consumer = new Consumer();
    consumer.setKey(key.asConsumerKey());
    consumer.setSpecification(specification.asSpecification());
    maintainState(consumer, services.getConsumerService().read(consumer.getKey()));
    return consumer;
  }

  public Boolean delete(ConsumerKeyInput key) {
    throw new UnsupportedOperationException("delete");
  }

  public Consumer updateStatus(ConsumerKeyInput key, StatusInput status) {
    Consumer consumer = services.getConsumerService().read(key.asConsumerKey()).get();
    consumer.setStatus(status.asStatus());
    return services.getConsumerService().update(consumer).get();
  }
}

