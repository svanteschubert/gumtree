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

/** MSV parser derived from ANTLR v4 ref guide book XML example */
parser grammar MSVParser;

options { tokenVocab = MSVLexer; }

@header {
package  com.github.gumtreediff.gen.antlr4.msv;
}

/** A Multi-Schema Validator (MSV)  memory dump file of some XML grammar consists of one or more lines*/
msvFile   :   msvLine+ EOF
          ;


/** Each line of a MSV file memory dump of its internal graph respesentation provides information for a graph node */
msvLine   :   GraphNodeDepth COLON S GraphNodeType (S EscapedString COMMA? )*  NEWLINE
          ;




