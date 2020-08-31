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

import com.alibaba.fastjson.JSON;
import com.mesalab.ua.dto.Device;
import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Device parser using ua-parser regexes. Extracts device information from user agent strings.
 *
 * @author Steve Jiang (@sjiang) &lt;gh at iamsteve com&gt;
 */
public class DeviceParser {

    private final List<DevicePattern> patterns;

    public DeviceParser(List<DevicePattern> patterns) {
        this.patterns = patterns;
    }

    public Device parse(String agentString) {
        if (agentString == null) {
            return null;
        }

        Device device;
        for (DevicePattern p : patterns) {
            if ((device = p.match(agentString)) != null) {
                return device;
            }
        }

        return new Device("Other", null, null, null);
    }

    /**
     * Constructs a thread-safe DeviceParser.
     */
    public static DeviceParser fromList(List<Map<String, String>> configList) {
        List<DevicePattern> configPatterns = new ArrayList<DevicePattern>();
        for (Map<String, String> configMap : configList) {
            configPatterns.add(DeviceParser.patternFromMap(configMap));
        }
        return new DeviceParser(new CopyOnWriteArrayList<>(configPatterns));
    }

    protected static DevicePattern patternFromMap(Map<String, String> configMap) {
        String regex = configMap.get("regex");
        if (regex == null) {
            throw new IllegalArgumentException("Device is missing regex");
        }
        Pattern pattern = "i".equals(configMap.get("regex_flag")) // no ohter flags used (by now) 
                ? Pattern.compile(regex, Pattern.CASE_INSENSITIVE) : Pattern.compile(regex);
        return new DevicePattern(pattern, configMap.get("name_replacement"), configMap.get("brand_replacement"), configMap.get("model_replacement"), configMap.get("type_replacement"));
    }

    protected static class DevicePattern {
        private static final Pattern SUBSTITUTIONS_PATTERN = Pattern.compile("\\$\\d");
        private final Pattern pattern;
        private final String nameReplacement, brandReplacement, modelReplacement, typeReplacement;

        public DevicePattern(Pattern pattern, String nameReplacement, String brandReplacement, String modelReplacement, String typeReplacement) {
            this.pattern = pattern;
            this.nameReplacement = nameReplacement;
            this.brandReplacement = brandReplacement;
            this.modelReplacement = modelReplacement;
            this.typeReplacement = typeReplacement;
        }


        public Device match(String agentString) {
            String name = null, brand = null, model = null, type = null;
            Matcher matcher = pattern.matcher(agentString);
            if (!matcher.find()) {
                return null;
            }

            int groupCount = matcher.groupCount();

            name = getReplaceResult(model, matcher, groupCount, nameReplacement);
            if (brandReplacement != null) {
                brand = brandReplacement;
            } else {
                brand = "Other";
            }

            model = getReplaceResult(model, matcher, groupCount, modelReplacement);

            if (typeReplacement == null) {
                type = "Other";
            } else {
                type = Utils.getReplacement(matcher, typeReplacement);
            }
            System.out.println("pattern+: "+pattern);
            return name == null ? null : new Device(name, brand, model, type);
        }

        private String getReplaceResult(String model, Matcher matcher, int groupCount, String replacementString) {
            if (replacementString != null) {
                if (replacementString.contains("$")) {
                    model = replacementString;
                    for (String substitution : getSubstitutions(replacementString)) {
                        int i = Integer.parseInt(substitution.substring(1));
                        String replacement = matcher.groupCount() >= i && matcher.group(i) != null
                                ? Matcher.quoteReplacement(matcher.group(i)) : "";
                        model = model.replaceFirst("\\" + substitution, replacement);
                    }
                    model = model.trim();
                } else {
                    model = replacementString;
                }
            } else if (groupCount >= 1) {
                model = matcher.group(1);
            }
            return model;
        }

        private List<String> getSubstitutions(String nameReplacement) {
            Matcher matcher = SUBSTITUTIONS_PATTERN.matcher(nameReplacement);
            List<String> substitutions = new ArrayList<String>();
            while (matcher.find()) {
                substitutions.add(matcher.group());
            }
            return substitutions;
        }

    }

}