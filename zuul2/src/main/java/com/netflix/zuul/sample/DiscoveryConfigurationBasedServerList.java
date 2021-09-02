/*
 *
 * Copyright 2013 Netflix, Inc.
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
 *
 */

package com.netflix.zuul.sample;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractServerList;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;
import java.util.List;

public class DiscoveryConfigurationBasedServerList extends AbstractServerList<DiscoveryEnabledServer> {

    private IClientConfig clientConfig;

    @Override
    public List<DiscoveryEnabledServer> getInitialListOfServers() {
        return getUpdatedListOfServers();
    }

    @Override
    public List<DiscoveryEnabledServer> getUpdatedListOfServers() {
        String listOfServers = clientConfig.get(CommonClientConfigKey.ListOfServers);
        return derive(listOfServers);
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
        this.clientConfig = clientConfig;
    }

    protected List<DiscoveryEnabledServer> derive(String value) {
        List<DiscoveryEnabledServer> list = Lists.newArrayList();
        if (!Strings.isNullOrEmpty(value)) {
            for (String s : value.split(",")) {
                DiscoveryEnabledServer discoveryEnabledServer = new DiscoveryEnabledServer(
                    InstanceInfo.Builder
                        .newBuilder().setInstanceId(s.trim())
                        .setAppName("api").setHostName(s.trim().split(":")[0])
                        .setPort(Integer.parseInt(s.trim().split(":")[1]))
                        .build(), false, false);
                list.add(discoveryEnabledServer);
            }
        }
        return list;
    }
}