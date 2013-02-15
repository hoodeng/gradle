/*
 * Copyright 2013 the original author or authors.
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
package org.gradle.plugins.ide.idea.model

import org.gradle.api.Incubating

/**
 * A project-level IDEA library.
 */
@Incubating
class ProjectLibrary extends Library {
    /**
     * The name of the library.
     */
    String name

    void addToNode(Node parentNode) {
        def builder = new NodeBuilder()

        def attributes = [name: name]

        def library = builder.library(attributes) {
            CLASSES {
                for (path in classes) {
                    root(url: path.url)
                }
            }
            JAVADOC {
                for (path in javadoc) {
                    root(url: path.url)
                }
            }
            SOURCES {
                for (path in sources) {
                    root(url: path.url)
                }
            }
            for (dir in jarDirectories) {
                jarDirectory(url: dir.path.url, recursive: dir.recursive)
            }
        }

        parentNode.append(library)
    }

    boolean equals(Object obj) {
        if (this.is(obj)) {
            return true
        }
        if (!(obj instanceof ProjectLibrary)) {
            return false
        }

        ProjectLibrary that = (ProjectLibrary) obj

        if (classes != that.classes) {
            return false
        }
        if (jarDirectories != that.jarDirectories) {
            return false
        }
        if (javadoc != that.javadoc) {
            return false
        }
        if (name != that.name) {
            return false
        }
        if (sources != that.sources) {
            return false
        }

        return true
    }

    int hashCode() {
        int result
        result = name.hashCode()
        result = 31 * result + classes.hashCode()
        result = 31 * result + jarDirectories.hashCode()
        result = 31 * result + javadoc.hashCode()
        result = 31 * result + sources.hashCode()
        return result
    }
}
