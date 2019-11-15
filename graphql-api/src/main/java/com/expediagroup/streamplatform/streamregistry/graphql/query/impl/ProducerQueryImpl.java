/**
 * Copyright (C) 2018-2019 Expedia, Inc.
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
package com.expediagroup.streamplatform.streamregistry.graphql.query.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import com.expediagroup.streamplatform.streamregistry.core.services.ProducerService;
import com.expediagroup.streamplatform.streamregistry.graphql.filters.ProducerFilter;
import com.expediagroup.streamplatform.streamregistry.graphql.model.inputs.ProducerKeyInput;
import com.expediagroup.streamplatform.streamregistry.graphql.model.queries.ProducerKeyQuery;
import com.expediagroup.streamplatform.streamregistry.graphql.model.queries.SpecificationQuery;
import com.expediagroup.streamplatform.streamregistry.graphql.query.ProducerQuery;
import com.expediagroup.streamplatform.streamregistry.model.Producer;

@Component
@RequiredArgsConstructor
public class ProducerQueryImpl implements ProducerQuery {
  private final ProducerService producerService;

  @Override
  public Producer byKey(ProducerKeyInput key) {
    return producerService.read(key.asProducerKey()).get();
  }

  @Override
  public Iterable<Producer> byQuery(ProducerKeyQuery key, SpecificationQuery specification) {
    return producerService.findAll(new ProducerFilter(key, specification));
  }
}
