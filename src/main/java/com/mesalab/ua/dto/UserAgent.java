/**
 * Copyright 2012 Twitter, Inc
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mesalab.ua.dto;

/**
 * Collection of parsed data for a given user agent string consisting of UserAgent, OS, Device
 *
 * @author Steve Jiang (@sjiang) &lt;gh at iamsteve com&gt;
 */
public class UserAgent {
    public final Client client;
    public final OS os;
    public final Device device;

    public final String category;

    public final Long lastVisit;
    public final String userAgent;


    public UserAgent(Client client, OS os, Device device, String category, Long lastVisit, String userAgent) {
        this.client = client;
        this.os = os;
        this.device = device;
        this.category = category;
        this.lastVisit = lastVisit;
        this.userAgent = userAgent;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserAgent)) {
            return false;
        }

        UserAgent o = (UserAgent) other;
        return ((this.client != null && this.client.equals(o.client)) || this.client == o.client) &&
                ((this.os != null && this.os.equals(o.os)) || this.os == o.os)&&
                ((this.device != null && this.device.equals(o.device)) || this.device == o.device) &&
                ((this.category != null && this.category.equals(o.category)) || this.category == o.category) &&
                ((this.lastVisit != null && this.lastVisit.equals(o.lastVisit)) || this.lastVisit == o.lastVisit) &&
                ((this.userAgent != null && this.userAgent.equals(o.userAgent)) || this.userAgent == o.userAgent) ;
    }

    @Override
    public int hashCode() {
        int h = client == null ? 0 : client.hashCode();
        h += os == null ? 0 : os.hashCode();
        h += device == null ? 0 : device.hashCode();
        h += category == null ? 0 : category.hashCode();
        h += lastVisit == null ? 0 : lastVisit.hashCode();
        h += userAgent == null ? 0 : userAgent.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return String.format("{\"client\": %s, \"os\": %s, \"device\": %s, \"category\": %s, \"lastVisit\": %s, \"userAgent\": %s}",
                client, os, device, category, lastVisit, userAgent);
    }

}