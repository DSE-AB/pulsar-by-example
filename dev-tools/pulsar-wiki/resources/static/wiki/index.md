%% syntaxHighlight install=true
Content
%%

%%% prettify install=true
Content
%%%

# The Pulsar Wiki Service
[Another page](somepage.md)
[Code Example 1](code/example-1.md)
Hey!

# Tests

## sourceRef Plugin
%%% sourceRef file=../dev-tools/pulsar-wiki/src/se/dse/pulsar/devtools/wiki/api/Wiki.java
.
%%%

# syntaxHighlight
%%% syntaxHighlight brush=js file=../dev-tools/pulsar-wiki/src/se/dse/pulsar/devtools/wiki/api/Wiki.java
MyPlugInIdContent
...
...
%%%

# prettify
%%% prettify lang=java file=../dev-tools/pulsar-wiki/src/se/dse/pulsar/devtools/wiki/api/Wiki.java
...
%%%

# Include
{{include src="https://raw.github.com/vmg/redcarpet/4c14d0875163890e553897efcceb7480aa34f8e9/README.markdown"}}


## UML
%% yuml style=nofunky scale=120 format=svg
[Customer]<>-orders*>[Order] 
[Order]++-0..*>[LineItem]
[Order]-[note:Aggregate root.]
[Car]<0..*-0..*>[Elefant]
%%

## Sequence
%% sequence style=modern-blue
title Authentication Sequence

Alice->Bob: Authentication Request
note right of Bob: Bob thinks about it
Bob->Alice: Authentication Response
%%
