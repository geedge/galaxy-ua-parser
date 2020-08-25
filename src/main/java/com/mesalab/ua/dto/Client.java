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
 * User Agent parsed data class
 *
 * @author Steve Jiang (@sjiang) &lt;gh at iamsteve com&gt;
 */
public class Client {
    public final String name, version, type, engine, engineVersion;

    public Client(String name, String version, String type, String engine, String engineVersion) {
        this.name = name;
        this.version = version;
        this.type = type;
        this.engine = engine;
        this.engineVersion = engineVersion;
    }

    public static Client fromMap(Map<String, String> m) {
        return new Client(m.get("name"), m.get("version"), m.get("type"), m.get("engine"), m.get("engineVersion"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Client)) {
            return false;
        }

        Client o = (Client) other;
        return ((this.name != null && this.name.equals(o.name)) || this.name == o.name) &&
                ((this.version != null && this.version.equals(o.version)) || this.version == o.version) &&
                ((this.type != null && this.type.equals(o.type)) || this.type == o.type) &&
                ((this.engine != null && this.engine.equals(o.engine)) || this.engine == o.engine) &&
                ((this.engineVersion != null && this.engineVersion.equals(o.engineVersion)) || this.engineVersion == o.engineVersion);
    }

    @Override
    public int hashCode() {
        int h = name == null ? 0 : name.hashCode();
        h += version == null ? 0 : version.hashCode();
        h += type == null ? 0 : type.hashCode();
        h += engine == null ? 0 : engine.hashCode();
        h += engineVersion == null ? 0 : engineVersion.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return String.format("{\"name\": %s, \"version\": %s, \"type\": %s, \"engine\": %s, \"engineVersion\": %s}",
                name == null ? Constants.EMPTY_STRING : '"' + name + '"',
                version == null ? Constants.EMPTY_STRING : '"' + version + '"',
                type == null ? Constants.EMPTY_STRING : '"' + type + '"',
                engine == null ? Constants.EMPTY_STRING : '"' + engine + '"',
                engineVersion == null ? Constants.EMPTY_STRING : '"' + engineVersion + '"');
    }

}