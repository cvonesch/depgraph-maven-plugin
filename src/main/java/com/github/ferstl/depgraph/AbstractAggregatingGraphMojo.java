/*
 * Copyright (c) 2014 - 2017 the original author or authors.
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
 */
package com.github.ferstl.depgraph;

import java.util.Collection;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import com.github.ferstl.depgraph.dependency.SubProjectSupplier;

public abstract class AbstractAggregatingGraphMojo extends AbstractGraphMojo {

  /**
   * Merge dependencies that occur in multiple scopes into one graph node instead of having a node per scope.
   *
   * @since 2.0.0
   */
  @Parameter(property = "mergeScopes", defaultValue = "false")
  boolean mergeScopes;

  /**
   * Omit all edges that are already reachable via a different path in the dependency graph. This will prefer dependencies
   * of modules that are higher in the reactor build order and thus reflect the architecture of the application better.
   *
   * @since 2.3.0
   */
  @Parameter(property = "reduceEdges", defaultValue = "true")
  boolean reduceEdges;

  @Component
  MavenSession mavenSession;

  SubProjectSupplier createReactorOrderSubProjectSupplier() {
    return new SubProjectSupplier() {

      @Override
      public Collection<MavenProject> getSubProjects(MavenProject parent) {
        return AbstractAggregatingGraphMojo.this.mavenSession.getProjectDependencyGraph().getDownstreamProjects(parent, true);
      }
    };
  }
}
