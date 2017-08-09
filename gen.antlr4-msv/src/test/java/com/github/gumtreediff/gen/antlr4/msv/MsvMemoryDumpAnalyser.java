/*
 * This file is part of GumTree.
 *
 * GumTree is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GumTree is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GumTree.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2017 Svante Schubert <svante.schubert gmail com>
 */
package com.github.gumtreediff.gen.antlr4.msv;

import com.github.gumtreediff.gen.TreeGenerator;
import com.github.gumtreediff.gen.antlr4.TreeComparisonBase;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.junit.Test;

/**
 * This is the first of two classes, that executed in sequence create a GraphML
 * representation of the OpenDocument Schema.

 * This first class provides the data for the later GraphML creation by the
 * GraphMLCreation class.
 * The data is extracted from a memory dump of the MultiSchemaValdiator, which
 * had prior parsed the ODF 1.2 Relax NG schema.
 * The RelaxNG is not provided as XML, but as memory dump Multi-Schema Validator
 * memory dump to reduce the complexity from RelaxNG XML schema.
 * from the ODF RelaxNG schema is being parsed by an ANTLR 4 parser, extracting
 * data into two property files for vertices and edges.
 */
public class MsvMemoryDumpAnalyser extends TreeComparisonBase {

    /**
     * <b>IMPORTANT</b>
     * When adding new test pairs to the array, the new test output from the
     * output folder: '/build/created-test-files' needs to be copied manually to
     * the reference folder: 'src\test\resources\references
     */
    private static final String[][] testCouplesMSV = new String[][]{ // {"odf12.msv", "odf12b.msv"}
    };

    private static final String UPCOMING_MSV_FILE = "odf12.msv"; // "odf12.msv"; // "odf_mini.msv";
    static final String OUTPUT_PATH = "build"  + File.separatorChar;

    public MsvMemoryDumpAnalyser() {
        super(testCouplesMSV);
    }

    @Override
    protected TreeGenerator getTreeGenerator() {
        return new MsvTreeGenerator();
    }

    @Test
    public void MsvFileLoaderTest() {
        File outputDir = new File(TEST_OUTPUT_PATH);
        outputDir.mkdir();

        CharStream input = null;
        try {
            input = CharStreams.fromFileName(TEST_INPUT_PATH + UPCOMING_MSV_FILE);
        } catch (IOException ex) {
            Logger.getLogger(MsvMemoryDumpAnalyser.class.getName()).log(Level.SEVERE, null, ex);
        }

        MSVLexer lexer = new MSVLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MSVParser parser = new MSVParser(tokens);
        ParseTree tree = parser.msvFile();

        // create a standard ANTLR parse tree walker
        ParseTreeWalker walker = new ParseTreeWalker();
        // create listener then feed to walker
        MsvFileMapper loader = new MsvFileMapper(OUTPUT_PATH);
        walker.walk(loader, tree);        // walk parse tree
        loader.close();
    }

    @Test
    @Override
    public void allReferencesWithTestResultComparison() {
    }
}
