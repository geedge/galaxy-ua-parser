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

package com.mesalab.ua;

import com.mesalab.ua.dto.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User Agent parser using ua-parser regexes
 *
 * @author Steve Jiang (@sjiang) &lt;gh at iamsteve com&gt;
 */
public class ClientParser {

    private final List<UAPattern> patterns;

    public ClientParser(List<UAPattern> patterns) {
        this.patterns = patterns;
    }

    /**
     * Constructs a thread-safe UserAgentParser
     */
    public static ClientParser fromList(List<Map<String, String>> configList) {
        List<UAPattern> configPatterns = new ArrayList<UAPattern>();

        for (Map<String, String> configMap : configList) {
            configPatterns.add(ClientParser.patternFromMap(configMap));
        }
        return new ClientParser(new CopyOnWriteArrayList<>(configPatterns));
    }

    public Client parse(String agentString) {
        if (agentString == null) {
            return null;
        }

        Client agent;
        for (UAPattern p : patterns) {
            //用p的正则去匹配ua
            if ((agent = p.match(agentString)) != null) {
                return agent;
            }
        }
        return new Client("Other", null, null, null, null);
    }

    protected static UAPattern patternFromMap(Map<String, String> configMap) {
        String regex = configMap.get("regex");
        if (regex == null) {
            throw new IllegalArgumentException("User agent is missing regex");
        }

        return (new UAPattern(Pattern.compile(regex),
                configMap.get("name_replacement"),
                configMap.get("version_replacement"),
                configMap.get("type_replacement"),
                configMap.get("engine_replacement"),
                configMap.get("engine_version_replacement")));
    }

    protected static class UAPattern {
        private final Pattern pattern;
        private final String nameReplacement, versionReplacement, typeReplacement, engineReplacement, engineVersionReplacement;

        public UAPattern(Pattern pattern, String nameReplacement, String versionReplacement, String typeReplacement, String engineReplacement, String engineVersionReplacement) {
            this.pattern = pattern;
            this.nameReplacement = nameReplacement;
            this.versionReplacement = versionReplacement;
            this.typeReplacement = typeReplacement;
            this.engineReplacement = engineReplacement;
            this.engineVersionReplacement = engineVersionReplacement;
        }

        public Client match(String agentString) {
            String name = null, version = null, type = null, engine = null, engineVersion = null;
            Matcher matcher = pattern.matcher(agentString);

            if (!matcher.find()) {
                return null;
            }

            if (nameReplacement != null) {
                name = Utils.getReplacement(matcher, nameReplacement);
            } else {
                name = nameReplacement;
            }

            if (versionReplacement != null) {
                version = Utils.getReplacement(matcher, versionReplacement);
            } else {
                version = versionReplacement;
            }

            if (typeReplacement != null) {
                type = Utils.getReplacement(matcher, typeReplacement);
            } else {
                type = "Other";
            }

            if (engineReplacement != null) {
                engine = Utils.getReplacement(matcher, engineReplacement);
            } else {
                engine = "Other";
            }

            if (engineVersionReplacement != null) {
                engineVersion = Utils.getReplacement(matcher, engineVersionReplacement);
            } else {
                engineVersion = "Other";
            }

            return name == null ? null : new Client(name, version, type, engine, engineVersion);
        }

        private boolean isBlank(String value) {
            return value == null || value.isEmpty();
        }
    }
}
