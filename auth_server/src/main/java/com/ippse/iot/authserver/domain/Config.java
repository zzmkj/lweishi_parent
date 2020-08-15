package com.ippse.iot.authserver.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.StringUtils;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Config implements java.io.Serializable {
    private static final long serialVersionUID = 1801219483748426473L;

    private String property;
    private String name;
    private Set<String> vals = new HashSet<String>();
    private Set<String> options = new HashSet<String>();
    private Set<String> defvals = new HashSet<String>();
    private String type;

    public Config(String property, String name, Set<String> options, Set<String> defvals, String type) {
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