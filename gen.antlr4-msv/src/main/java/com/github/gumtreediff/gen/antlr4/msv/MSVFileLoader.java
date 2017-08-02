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

import com.github.gumtreediff.gen.antlr4.msv.MSVParser.EscapedStringContext;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.antlr.v4.misc.OrderedHashMap;

public class MSVFileLoader extends MSVParserBaseListener {

    public static final Map<String,String> props = new OrderedHashMap<String, String>();
    private int lastDepth = -1;

    public void exitMsvLine(MSVParser.MsvLineContext ctx) {

        int depth  =  Integer.parseInt(ctx.GraphNodeDepth().getText().trim());
        LOG.info("Depth: " + depth);
        List<EscapedStringContext> values = ctx.escapedString();
        if(values != null){
            LOG.info("Value: ");
            if(values.size() == 1){
                LOG.info(values.toString());
            }else if(values.size() == 0){
                LOG.info("EMPTY!!");
            }else {
                LOG.info(values.get(0).getText());
            }
            LOG.info("SIZE:" + values.size());
        }


//        props.put(id, value);

    }
    private static final Logger LOG = Logger.getLogger(MSVFileLoader.class.getName());



}
