/**
 * Copyright 2012 Twitter, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mesalab.ua;

import com.mesalab.ua.dto.OS;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Operating System parser using ua-parser. Extracts OS information from user agent strings.
 *
 * @author Steve Jiang (@sjiang) &lt;gh at iamsteve com&gt;
 */
public class OSParser {

  private final List<OSPattern> patterns;

  public OSParser(List<OSPattern> patterns) {
    this.patterns = patterns;
  }

  /**
   * Constructs a thread-safe OSParser.
   */
  public static OSParser fromList(List<Map<String,String>> configList) {
    List<OSPattern> configPatterns = new ArrayList<OSPattern>();

    for (Map<String,String> configMap : configList) {
      configPatterns.add(OSParser.patternFromMap(configMap));
    }
    return new OSParser(new CopyOnWriteArrayList<>(configPatterns));
  }

  public OS parse(String agentString) {
    if (agentString == null) {
      return null;
    }

    OS os;
    for (OSPattern p : patterns) {
      if ((os = p.match(agentString)) != null) {
        return os;
      }
    }
    return new OS("Other", null, null);
  }

  protected static OSPattern patternFromMap(Map<String, String> configMap) {
    String regex = configMap.get("regex");
    if (regex == null) {
      throw new IllegalArgumentException("OS is missing regex");
    }

    return(new OSPattern(Pattern.compile(regex),
                         configMap.get("name_replacement"),
                         configMap.get("version_replacement"),
                         configMap.get("platform_replacement")));
  }

  protected static class OSPattern {
    private final Pattern pattern;
    private final String nameReplacement, versionReplacement, platformReplacement;

    public OSPattern(Pattern pattern, String nameReplacement, String versionReplacement, String platformReplacement) {
      this.pattern = pattern;
      this.nameReplacement = nameReplacement;
      this.versionReplacement = versionReplacement;
      this.platformReplacement = platformReplacement;
    }

    public OS match(String agentString) {
      String name = null, version = null, platform = null;
      Matcher matcher = pattern.matcher(agentString);

      if (!matcher.find()) {
        return null;
      }

      int groupCount = matcher.groupCount();

      if (nameReplacement != null) {
          if (groupCount >= 1) {
              name = Pattern.compile("(" + Pattern.quote("$1") + ")")
                              .matcher(nameReplacement)
                              .replaceAll(matcher.group(1));
          } else {
            name = nameReplacement;
          }
      } else if (groupCount >= 1) {
        name = matcher.group(1);
      } else {
        name = "Other";
      }

      if (versionReplacement != null) {
        version = getReplacement(matcher, versionReplacement);
      } else if (groupCount >= 2) {
        version = matcher.group(2);
      } else {
        version = "Other";
      }

      if (platformReplacement != null) {
        platform = getReplacement(matcher, platformReplacement);
      } else {
        platform = "Other";
      }

//      System.out.println("oa : "+pattern);
      return name == null ? null : new OS(name, version, platform);
    }

    private String getReplacement(Matcher matcher, String replacement) {
    	if (isBackReference(replacement)) {
    		int group = getGroup(replacement);
    		return matcher.group(group);
    	} else {
    		return replacement;
    	}
    }

    /**
     * Checks if the replacement is a backreference (i.e. $1, $2, $3, etc) to a capturing group in the regular expression.
     */
    private boolean isBackReference(String replacement) {
    	return replacement.startsWith("$");
    }

    /**
     * Extracts the group number from a backreference like $1, $2, $3, etc.
     */
    private int getGroup(String backReference) {
    	return Integer.valueOf(backReference.substring(1));
    }
  }
}
