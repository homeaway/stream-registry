/* Copyright (C) 2018-2019 Expedia, Inc.
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
package com.expediagroup.streamplatform.streamregistry.configuration;

import java.util.Map;

import javax.validation.Valid;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Details of the topic used as EventStore
 */
@Data
@Builder
public class EventStoreTopic {

    /**
     * name of the topic
     */
    @Valid
    @NonNull
    String name;

    /**
     * number of partitions
     */
    @Valid
    @NonNull
    int partitions;

    /**
     * number of replicas
     */
    @Valid
    @NonNull
    short replicationFactor;

    /**
     * properties of the topic
     */
    @Valid
    @NonNull
    Map<String, String> properties;
}