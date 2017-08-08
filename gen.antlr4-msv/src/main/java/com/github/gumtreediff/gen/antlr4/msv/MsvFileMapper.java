/*
Copyright 2017 Svante Schubert

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.github.gumtreediff.gen.antlr4.msv;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import org.antlr.v4.misc.OrderedHashMap;
import org.antlr.v4.runtime.tree.TerminalNode;

public class MsvFileMapper extends MSVParserBaseListener {

    public static final Map<String, String> props = new OrderedHashMap<String, String>();
    private int lastDepth = -1;

    private final Map<Integer, String> lastVertexOnGraphLevel = new HashMap<Integer, String>();
    private String parentName = null;
    private int vertixCount = 0;
    private Properties vertexProps = null;
    private Properties edgeProps = null;
    private String outputPath = null;

    MsvFileMapper(String outputFilePath) {
        super();
        outputPath = outputFilePath;
        // Start properties files
        vertexProps = new Properties();
        edgeProps = new Properties();
    }

    public void exitMsvLine(MSVParser.MsvLineContext ctx) {
        String vertexName = null;
        int depth = Integer.parseInt(ctx.GraphNodeDepth().getText());

        // node name
        vertexName = "v" + vertixCount;

        // Save the node as last node of this level
        lastVertexOnGraphLevel.put(depth, vertexName);

        // if we jump to a previous node..
        if (depth < lastDepth) {
            // receive the previous parent
            parentName = lastVertexOnGraphLevel.get(depth);
        }

        // Restore last note of that level as parent
        // Create relation to parent vertix
        String label = ctx.GraphNodeType().getText();

//        Vertex marko = graph.addVertex(T.label, "person", T.id, 1, "name", "marko", "age", 29);
//        marko.addEdge("knows", vadas, T.id, 7, "weight", 0.5f);
//
        String value = null;
        List<TerminalNode> values = ctx.EscapedString();
        if (values != null && values.size() == 1) {
            String escapedValue = values.get(0).getText();
            value = escapedValue.substring(1, escapedValue.length() - 1);
            vertexProps.setProperty(vertexName, label + ',' + value);
            if (parentName != null) {
                edgeProps.setProperty(vertexName, parentName);
            }
            parentName = vertexName;
            vertixCount++;

        } else if (values != null & !values.isEmpty()) {
            // MultiElement (Attribute as well?)
            // 36: ELEMENT "text:page-count", "text:paragraph-count", "text:word-count", "text:character-count", "text:table-count", "text:image-count", "text:object-count",

            vertexProps.setProperty(vertexName, label);
            String vertexParentChoice = vertexName;
            vertexProps.setProperty(vertexParentChoice, "CHOICE");
            String vertexChoice = null;
            for (TerminalNode t : values) {
                vertixCount++;
                vertexChoice = "v" + vertixCount;
                String escapedValue = t.getText();
                value = escapedValue.substring(1, escapedValue.length() - 1);
                vertexProps.setProperty(vertexChoice, label + ',' + value);
                edgeProps.setProperty(vertexChoice, vertexParentChoice);
            }
            parentName = vertexName;
        }

    }

    private static final Logger LOG = Logger.getLogger(MsvFileMapper.class.getName());

    public void close() {
        try {

            // Write properties files.
            vertexProps.store(new FileOutputStream(outputPath + "vertex.properties"), null);
            edgeProps.store(new FileOutputStream(outputPath + "edge.properties"), null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
