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
    public final String name, brand, model, type;

    public Device(String name, String brand, String model, String type) {
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.type = type;
    }

    public static Device fromMap(Map<String, String> m) {
        return new Device(m.get("name"), m.get("brand"), m.get("model"), m.get("type"));
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
        return ((this.name != null && this.name.equals(o.name)) || this.name == o.name) &&
                ((this.model != null && this.model.equals(o.model)) || this.model == o.model) &&
                ((this.brand != null && this.brand.equals(o.brand)) || this.brand == o.brand)&&
        ((this.type != null && this.type.equals(o.type)) || this.type == o.type) ;
    }

    @Override
    public int hashCode() {
        int h = name == null ? 0 : name.hashCode();
        h += brand == null ? 0 : brand.hashCode();
        h += model == null ? 0 : model.hashCode();
        h += type == null ? 0 : type.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return String.format("{\"name\": %s,\"brand\": %s,\"model\": %s,\"type\": %s}",
                name == null ? Constants.EMPTY_STRING : '"' + name + '"',
                brand == null ? Constants.EMPTY_STRING : '"' + brand + '"',
                model == null ? Constants.EMPTY_STRING : '"' + model + '"',
                type == null ? Constants.EMPTY_STRING : '"' + type + '"');
    }
}