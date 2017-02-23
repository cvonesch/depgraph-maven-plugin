package com.github.ferstl.depgraph.graph;


import com.github.ferstl.depgraph.dot.DotBuilder;
import com.github.ferstl.depgraph.dot.DotGraphFormatter;
import com.github.ferstl.depgraph.graph.style.StyleConfiguration;

public class DotGraphStyleConfigurer implements GraphStyleConfigurer {

  private final StyleConfiguration styleConfiguration;
  private boolean showGroupId;
  private boolean showArtifactId;
  private boolean showVersionsOnNodes;
  private boolean showVersionOnEdges;

  public DotGraphStyleConfigurer(StyleConfiguration styleConfiguration) {
    this.styleConfiguration = styleConfiguration;
  }

  @Override
  public GraphStyleConfigurer showGroupIds(boolean showGroupId) {
    this.showGroupId = showGroupId;
    return this;
  }

  @Override
  public GraphStyleConfigurer showArtifactIds(boolean showArtifactId) {
    this.showArtifactId = showArtifactId;
    return this;
  }

  @Override
  public GraphStyleConfigurer showVersionsOnNodes(boolean showVersionsOnNodes) {
    this.showVersionsOnNodes = showVersionsOnNodes;
    return this;
  }

  @Override
  public GraphStyleConfigurer showVersionsOnEdges(boolean showVersionOnEdges) {
    this.showVersionOnEdges = showVersionOnEdges;
    return this;
  }

  @Override
  public DotBuilder<GraphNode> configure(DotBuilder<GraphNode> graphBuilder) {
    DependencyNodeNameRenderer nodeNameRenderer = new DependencyNodeNameRenderer(this.showGroupId, this.showArtifactId, this.showVersionsOnNodes, this.styleConfiguration);
    DependencyEdgeRenderer edgeRenderer = new DependencyEdgeRenderer(this.showVersionOnEdges, this.styleConfiguration);

    return graphBuilder
        .graphFormatter(new DotGraphFormatter(this.styleConfiguration.defaultNodeAttributes(), this.styleConfiguration.defaultEdgeAttributes()))
        .useNodeNameRenderer(nodeNameRenderer)
        .useEdgeRenderer(edgeRenderer);
  }
}
