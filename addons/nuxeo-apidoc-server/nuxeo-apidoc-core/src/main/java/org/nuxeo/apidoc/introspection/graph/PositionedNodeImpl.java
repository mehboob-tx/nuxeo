/*
 * (C) Copyright 2020 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Anahide Tchertchian
 */
package org.nuxeo.apidoc.introspection.graph;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Node implentation with positioning information.
 *
 * @since 11.1
 */
@JsonIgnoreType
public class PositionedNodeImpl extends NodeImpl {

    protected int x = 0;

    protected int y = 0;

    protected int z = 0;

    public PositionedNodeImpl(@JsonProperty("id") String id, @JsonProperty("label") String label,
            @JsonProperty("weight") int weight, @JsonProperty("path") String path, @JsonProperty("type") String type,
            @JsonProperty("category") String category, @JsonProperty("color") String color) {
        super(id, label, weight, path, type, category, color);
    }

    public PositionedNodeImpl(@JsonProperty("id") String id, @JsonProperty("label") String label,
            @JsonProperty("weight") int weight, @JsonProperty("path") String path, @JsonProperty("type") String type,
            @JsonProperty("category") String category, @JsonProperty("color") String color, @JsonProperty("x") int x,
            @JsonProperty("y") int y, @JsonProperty("z") int z) {
        super(id, label, weight, path, type, category, color);
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

}