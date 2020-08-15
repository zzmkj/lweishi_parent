package com.lweishi.repair.config;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OkUserConfig implements java.io.Serializable {
    private static final long serialVersionUID = 1801219483748426473L;

    @JsonView(OkUser.Views.Profile.class)
    private String property;

    @JsonView(OkUser.Views.Profile.class)
    private String name;

    @JsonView(OkUser.Views.Profile.class)
    private Set<String> vals = new HashSet<String>();

    @JsonView(OkUser.Views.Profile.class)
    private Set<String> options = new HashSet<String>();

    @JsonView(OkUser.Views.Profile.class)
    private Set<String> defvals = new HashSet<String>();

    @JsonView(OkUser.Views.Profile.class)
    private String type;

    public OkUserConfig(String property, String name, Set<String> options, Set<String> defvals, String type) {
        this.property = property;
        this.name = name;
        this.options = options;
        this.defvals = defvals;
        this.type = type;
    }

    public boolean check(String option) {
        for (String val : this.getVals()) {
            if (StringUtils.equals(option, val)) {
                return true;
            }
        }
        return false;
    }
}