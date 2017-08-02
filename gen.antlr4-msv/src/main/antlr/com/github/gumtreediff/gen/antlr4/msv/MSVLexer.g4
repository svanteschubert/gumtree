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

/** MSV lexer */
lexer grammar MSVLexer;

@header {
package com.github.gumtreediff.gen.antlr4.msv;
}

GraphNodeDepth  :   DIGIT+
                ;

GraphNodeType   : 'ATTRIBUTE'
            | 'CHOICE'
            | 'DATA'
            | 'ELEMENT'
            | 'EPSILON'
            | 'INTERLEAVE'
            | 'LIST'
            | 'MIXED'
            | 'ONEOREMORE' // the misspelling comes from the MSV sources
            | 'REF'
            | 'SEQUENCE'
            | 'STRING'
            | 'VALUE'
            ;

COLON       : ':'
            ;


COMMA       : ','
            ;

DOUBLE_QUOTE : '"'
             ;

SINGLE_QUOTE : '\''
             ;

S           :   [ \t];


NEWLINE     :   '\r'? '\n' ; // return newlines to parser


DoubleQuotedValue : ~[<DOUBLEQUOTE]*
                  ;

SingleQuotedValue : ~[<SINGLEQUOTE]*
                  ;

DIGIT       :   [0-9] ;