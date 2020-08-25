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
 * Operating System parsed data class
 *
 * @author Steve Jiang (@sjiang) &lt;gh at iamsteve com&gt;
 */
public class OS {
    public final String name, version;
    public String platform;

    public OS(String name, String version, String platform) {
        this.name = name;
        this.version = version;
        this.platform = platform;
    }

    public static OS fromMap(Map<String, String> m) {
        return new OS(m.get("name"), m.get("version"), m.get("platform"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {return true;}
        if (!(other instanceof OS)) {return false;}

        OS o = (OS) other;
        return ((this.name != null && this.name.equals(o.name)) || this.name == o.name) &&
                ((this.version != null && this.version.equals(o.version)) || this.version == o.version) &&
                ((this.platform != null && this.platform.equals(o.platform)) || this.platform == o.platform);
    }

    @Override
    public int hashCode() {
        int h = name == null ? 0 : name.hashCode();
        h += version == null ? 0 : version.hashCode();
        h += platform == null ? 0 : platform.hashCode();
        return h;
    }

    @Override
    public String toString() {
        return String.format("{\"name\": %s, \"version\": %s, \"platform\": %s}",
                name == null ? Constants.EMPTY_STRING : '"' + name + '"',
                version == null ? Constants.EMPTY_STRING : '"' + version + '"',
                platform == null ? Constants.EMPTY_STRING : '"' + platform + '"');
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}