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

import com.mesalab.ua.Constants;

import java.util.Map;

/**
 * Device parsed data class
 *
 * @author Steve Jiang (@sjiang) &lt;gh at iamsteve com&gt;
 */
public class Device {
    public final String type, model, brand;

    public Device(String type, String model, String brand) {
        this.type = type;
        this.model = model;
        this.brand = brand;
    }

    public static Device fromMap(Map<String, String> m) {
        return new Device(m.get("type"), m.get("model"), m.get("brand"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Device)) {
            return false;
        }

        Device o = (Device) other;
        return ((this.type != null && this.type.equals(o.type)) || this.type == o.type) &&
                ((this.model != null && this.model.equals(o.model)) || this.model == o.model) &&
                ((this.brand != null && this.brand.equals(o.brand)) || this.brand == o.brand);
    }

    @Override
    public int hashCode() {
        int h = type == null ? 0 : type.hashCode();
        h += model == null ? 0 : model.hashCode();
        h += brand == null ? 0 : brand.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return String.format("{\"type\": %s,\"model\": %s,\"brand\": %s}",
                type == null ? Constants.EMPTY_STRING : '"' + type + '"',
                model == null ? Constants.EMPTY_STRING : '"' + model + '"',
                brand == null ? Constants.EMPTY_STRING : '"' + brand + '"');
    }
}