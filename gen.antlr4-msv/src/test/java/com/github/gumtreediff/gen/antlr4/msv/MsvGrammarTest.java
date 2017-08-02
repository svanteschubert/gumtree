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
 * The given test file pairs are being used as input for the tree generator. All
 * serializers are being used is used to dump the created graphs. In addition,
 * an edit script showing the difference of the pair is created. Finally, the
 * new output files from '/build/created-test-files' are being compared with
 * references from 'src\test\resources\references'.
 */
public class MsvGrammarTest extends TreeComparisonBase {

    /**
     * <b>IMPORTANT</b>
     * When adding new test pairs to the array, the new test output from the
     * output folder: '/build/created-test-files' needs to be copied manually to
     * the reference folder: 'src\test\resources\references
     */
    private static final String[][] testCouplesMSV = new String[][]{ // {"odf12.msv", "odf12b.msv"}
    };

    private static final String GENERATE_MSV_FILE = "odf_mini.msv";

    public MsvGrammarTest() {
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
            input = CharStreams.fromFileName(TEST_INPUT_PATH + GENERATE_MSV_FILE);
        } catch (IOException ex) {
            Logger.getLogger(MsvGrammarTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        MSVLexer lexer = new MSVLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MSVParser parser = new MSVParser(tokens);
        ParseTree tree = parser.msvFile();

        // create a standard ANTLR parse tree walker
        ParseTreeWalker walker = new ParseTreeWalker();
        // create listener then feed to walker
        MSVFileLoader loader = new MSVFileLoader();
        walker.walk(loader, tree);        // walk parse tree
    }

    @Test
    @Override
    public void allReferencesWithTestResultComparison() {
    }
}
