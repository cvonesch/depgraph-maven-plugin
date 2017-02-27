/*
 * Copyright (c) 2014 - 2016 by Stefan Ferstl <st.ferstl@gmail.com>
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
package com.github.ferstl.depgraph.dependency;

import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.project.MavenProject;
import com.github.ferstl.depgraph.graph.GraphBuilder;

/**
 * A graph factory that creates a graph from a non multi-module project.
 */
public class SimpleGraphFactory implements GraphFactory {

  private final MavenGraphAdapter mavenGraphAdapter;
  private final ArtifactFilter globalFilter;
  private final GraphBuilder<DependencyNode> graphBuilder;

  public SimpleGraphFactory(MavenGraphAdapter mavenGraphAdapter, ArtifactFilter globalFilter, GraphBuilder<DependencyNode> graphBuilder) {
    this.mavenGraphAdapter = mavenGraphAdapter;
    this.globalFilter = globalFilter;
    this.graphBuilder = graphBuilder;
  }

  @Override
  public String createGraph(MavenProject project) {
    this.graphBuilder.graphName(project.getArtifactId());
    this.mavenGraphAdapter.buildDependencyGraph(project, this.globalFilter, this.graphBuilder);
    return this.graphBuilder.toString();
  }

}
